/*----------------------------------------------------------------------------\
|                              OperaXmlLoader                                 |
|                                                                             |
| This is a pre release and may not be redistributed.                         |
| Watch http://webfx.eae.net for the final version                            |
|                                                                             |
|-----------------------------------------------------------------------------|
|                         Created by Erik Arvidsson                           |
|                  (http://webfx.eae.net/contact.html#erik)                   |
|                      For WebFX (http://webfx.eae.net/)                      |
|-----------------------------------------------------------------------------|
| An onject that uses the MSXML XMLHTTP interface to load XML document.       |
| Only GET and asynchronous loading is supported
|-----------------------------------------------------------------------------|
|                   Copyright (c) 1999 - 2004 Erik Arvidsson                  |
|-----------------------------------------------------------------------------|
| This software is provided "as is", without warranty of any kind, express or |
| implied, including  but not limited  to the warranties of  merchantability, |
| fitness for a particular purpose and noninfringement. In no event shall the |
| authors or  copyright  holders be  liable for any claim,  damages or  other |
| liability, whether  in an  action of  contract, tort  or otherwise, arising |
| from,  out of  or in  connection with  the software or  the  use  or  other |
| dealings in the software.                                                   |
| - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - |
| This  software is  available under the  three different licenses  mentioned |
| below.  To use this software you must chose, and qualify, for one of those. |
| - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - |
| The WebFX Non-Commercial License          http://webfx.eae.net/license.html |
| Permits  anyone the right to use the  software in a  non-commercial context |
| free of charge.                                                             |
| - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - |
| The WebFX Commercial license           http://webfx.eae.net/commercial.html |
| Permits the  license holder the right to use  the software in a  commercial |
| context. Such license must be specifically obtained, however it's valid for |
| any number of  implementations of the licensed software.                    |
| - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - |
| GPL - The GNU General Public License    http://www.gnu.org/licenses/gpl.txt |
| Permits anyone the right to use and modify the software without limitations |
| as long as proper  credits are given  and the original  and modified source |
| code are included. Requires  that the final product, software derivate from |
| the original  source or any  software  utilizing a GPL  component, such  as |
| this, is also licensed under the GPL license.                               |
|-----------------------------------------------------------------------------|
| 2004-02-21 | Pre release distributed to a few selected tester               |
|-----------------------------------------------------------------------------|
| Dependencies: None                                                          |
|               Designed to be used with xmlextras.js for usage in XLoadTree2 |
|-----------------------------------------------------------------------------|
| Created 2003-??-?? | All changes are in the log above. | Updated 2004-02-21 |
\----------------------------------------------------------------------------*/


function OperaXmlLoader()
{
}

// always async
OperaXmlLoader.prototype.async = true;

OperaXmlLoader.prototype.open = function ( sMethod, sUri, bAsync )
{
	// only GET is supported
	if ( sMethod != "GET" )
		throw new Error( "OperaXmlLoader only supports GET" );
	if ( bAsync == false )
		throw new Error( "OperaXmlLoader only supports asynchronous loading" );

	this.method = sMethod;
	this.uri = sUri;
	this.loaded = false;
	this._loading = false;
};

// Note that this takes a String and not a Document
OperaXmlLoader.prototype.send = function ( oDoc )
{
	if ( oDoc != null )
		throw new Error( "OperaXmlLoader only supports asynchronous GET so null is the only supported argument to send" );

	this._createFrame();
	//if (this._frame.src == this.uri)
	//	this._frame.src = "";
	this._frame.src = this.uri;
	this._startStatePoll();
};

OperaXmlLoader.prototype._createFrame = function ()
{
	if ( this._frame != null )
		return;
	var f = document.createElement( "IFRAME" );
	f.style.position = "absolute";
	f.style.left = "-500px";
	f.style.top = "-500px";
	f.style.width = "5px";
	f.style.height = "5px";
	document.body.appendChild( f );
	this._frame = f;
};

OperaXmlLoader.prototype._startStatePoll = function ()
{
	this._loading = true;
	var oThis = this;
	this._pollInterval = window.setInterval( function () { oThis._oninterval(); }, 100 );
};

OperaXmlLoader.prototype._oninterval = function ()
{
	var doc = this._frame.contentDocument;
	if ( doc )
	{
		var newState = OperaXmlLoader.mapReadyState( doc.readyState );
		var changed = this.readyState != newState;
		this.readyState = newState;
		this.responseXML = doc;
		doc.parseError = this.getParseError();
		// this.status = 404;
		// this.statusText = "Not found";

		//if ( changed )
		//	alert( newState + "\n" + doc + "\n" + doc.documentElement );

		if ( doc.readyState == "complete" )
			this._endStatePoll();

		if ( changed && typeof this.onreadystatechange == "function" )
		{
			//alert( doc.readyState );
			this.onreadystatechange();
		}


	}
};

OperaXmlLoader.mapReadyState = function ( state )
{
	//alert( "mapReadyState: " + state );
	switch ( state )
	{
		case "uninitialized":
			return 0;
		case "loading":
			return 1;
		case "loaded":
			return 2;
		case "interactive":
			return 3;
		case "complete":
			return 4;
	}
	return state;
};

OperaXmlLoader.prototype._endStatePoll = function ()
{
	if ( this._pollInterval )
		window.clearInterval( this._pollInterval );
	this._loading = false;
	this.loaded = true;
};

OperaXmlLoader.prototype.getDocument = function ()
{
	if ( !this.loaded || this._loading )
		return null;
	var d = this._frame.contentDocument;
	var oThis = this;
	d.dispose = function () { oThis.dispose(); };
	return d;
};

OperaXmlLoader.prototype.getParseError = function ()
{
	return {
			errorCode:	0,
			filepos:	0,	// not supported
			line:		0,
			linepos:	0,
			reason:		"",
			srcText:	"",
			url:		this.uri
	};
};

OperaXmlLoader.prototype.dispose = function ()
{
	this._endStatePoll();
	if ( this._frame ) {
		this._frame.contentDocument.parseError = null;
		this._frame.contentDocument.dispose = null;
		this._frame.parentNode.removeChild( this._frame );
		this._frame = null;
	}
};

if ( /opera/i.test( navigator.userAgent ) )
{
	XmlHttp = function ()
	{
		return new OperaXmlLoader;
	}
	XmlHttp.create = function () { return new XmlHttp; };
}