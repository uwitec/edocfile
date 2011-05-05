/*----------------------------------------------------------------------------\
|                        XLoadTree 2 PRE RELEASE                              |
|                                                                             |
| This is a pre release and may not be redistributed.                         |
| Watch http://webfx.eae.net for the final version                            |
|                                                                             |
|-----------------------------------------------------------------------------|
|                   Created by Erik Arvidsson & Emil A Eklund                 |
|                  (http://webfx.eae.net/contact.html#erik)                   |
|                  (http://webfx.eae.net/contact.html#emil)                   |
|                      For WebFX (http://webfx.eae.net/)                      |
|-----------------------------------------------------------------------------|
| A tree menu system for IE 5.5+, Mozilla 1.4+, Opera 7.5+                    |
|-----------------------------------------------------------------------------|
|         Copyright (c) 1999 - 2004 Erik Arvidsson & Emil A Eklund            |
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
| Dependencies: xtree2.js Supplies the tree control                           |
|-----------------------------------------------------------------------------|
| Created 2003-??-?? | All changes are in the log above. | Updated 2004-02-21 |
\----------------------------------------------------------------------------*/


webFXTreeConfig.loadingText = "Loading...";


function WebFXLoadTree(sText, sXmlSrc, oAction) {
	WebFXTree.call(this, sText, oAction);

	// setup default property values
	this.src = sXmlSrc;
	this.loading = !sXmlSrc;
	this.loaded = !sXmlSrc;
	this.errorText = "";

	if (this.src) {
		/// add loading Item
		this._loadingItem = new WebFXTreeItem(webFXTreeConfig.loadingText);
		this.add( this._loadingItem );

		if ( this.getExpanded() )
			WebFXLoadTree.loadXmlDocument(this);
	}
}

WebFXLoadTree.prototype = new WebFXTree;

WebFXLoadTree.prototype.setExpanded = function (b) {
	WebFXTree.prototype.setExpanded.call(this, b);

	if ( this.src && b )
	{

		if (!this.loaded && !this.loading) {
			// load
			WebFXLoadTree.loadXmlDocument(this);
		}

	}
};
function WebFXLoadTreeItem(sText, sXmlSrc, oAction) {
	WebFXTree.call(this, sText, oAction);

// setup default property values
	this.src = sXmlSrc;
	this.loading = !sXmlSrc;
	this.loaded = !sXmlSrc;
	this.errorText = "";

	if (this.src) {
		/// add loading Item
		this._loadingItem = new WebFXTreeItem(webFXTreeConfig.loadingText);
		this.add( this._loadingItem );

		if ( this.getExpanded() )
			WebFXLoadTree.loadXmlDocument(this);
	}
}

WebFXLoadTreeItem.prototype = new WebFXTreeItem;

WebFXLoadTreeItem.prototype.setExpanded = function (b) {
	WebFXTreeItem.prototype.setExpanded.call(this, b);

	if ( this.src && b )
	{

		if (!this.loaded && !this.loading) {
			// load
			WebFXLoadTree.loadXmlDocument(this);
		}

	}
};





// reloads the src file if already loaded
WebFXLoadTree.prototype.reload =
WebFXLoadTreeItem.prototype.reload = function () {
	// if loading do nothing
	if (this.loaded) {
		var t = this.getTree();
		var expanded = this.getExpanded();
		var sr = t.getSuspendRedraw();
		t.setSuspendRedraw(true);

		// remove
		while (this.childNodes.length > 0)
			this.remove( this.childNodes[this.childNodes.length - 1] );

		this.loaded = false;

		this._loadingItem = new WebFXTreeItem(webFXTreeConfig.loadingText);
		this.add(this._loadingItem);

		if (expanded)
			this.setExpanded(true);

		t.setSuspendRedraw(sr);
		this.update();
	}
	else if (this.open && !this.loading)
		WebFXLoadTree.loadXmlDocument(this);
};



WebFXLoadTree.prototype.setSrc =
WebFXLoadTreeItem.prototype.setSrc = function (sSrc) {
	var oldSrc = this.src;
	if (sSrc == oldSrc) return;

	var expanded = this.getExpanded();

	// remove all
	this._callSuspended( function () {
		// remove
		while (this.childNodes.length > 0)
			this.remove( this.childNodes[this.childNodes.length - 1] );
	} );
	this.update();

	this.loaded = false;
	this.loading = false;
	if (this._loadingItem) {
		this._loadingItem.dispose();
		this._loadingItem = null;
	}
	this.src = sSrc;

	if (sSrc) {
		this._loadingItem = new WebFXTreeItem(webFXTreeConfig.loadingText);
		this.add( this._loadingItem );
	}

	this.setExpanded(expanded);
};

WebFXLoadTree.prototype.getSrc =
WebFXLoadTreeItem.prototype.getSrc = function () {
	return this.src;
};

WebFXLoadTree.prototype.dispose = function () {
	WebFXTree.prototype.dispose.call(this);
	if (this._xmlHttp) {
		if ( this._xmlHttp.dispose )
			this._xmlHttp.dispose();
		//this._xmlHttp.onreadystatechange = null;//new Function();
		//this._xmlHttp.abort();
		this._xmlHttp = null;
	}
};

WebFXLoadTreeItem.prototype.dispose = function () {
	WebFXTreeItem.prototype.dispose.call(this);
	if (this._xmlHttp) {
		if ( this._xmlHttp.dispose )
			this._xmlHttp.dispose();
		//this._xmlHttp.onreadystatechange = null;//new Function();
		//this._xmlHttp.abort();
		this._xmlHttp = null;
	}
};


// The path is divided by '/' and the item is identified by the text
WebFXLoadTree.prototype.openPath =
WebFXLoadTreeItem.prototype.openPath = function ( sPath, bSelect )
{
	/*
	alert( "XLoadTree openPath\n\n" +
		"text:\t" + this.getText() + "\n" +
		"path:\t" + sPath + "\n" +
		"bSelect:\t" + bSelect );
	*/

	// remove any old pending paths to open
	delete this._pathToOpen;
	//delete this._pathToOpenById;
	this._selectPathOnLoad = bSelect;

	if ( sPath == "" )
	{
		if ( bSelect )
			this.select();
		return;
	}

	var parts = sPath.split( "/" );
	var remainingPath = parts.slice(1).join("/");

	//if ( parts.length == 1 )
//		alert( remainingPath )

	if ( sPath.charAt(0) == "/" )
		this.getTree().openPath( remainingPath, bSelect );
	else
	{
		//alert( "Has chidlren?\n\n" + this.getText() + "\n" + this.hasChildren() + "\n" + this.childNodes );
		// open
		this.setExpanded( true );
		if ( this.loaded )
		{
			var parts = sPath.split( "/" );
			var ti = this.findChildByText( parts[0] );
			if ( !ti )
				throw "Could not find child node with text \"" + parts[0] + "\"";

			//alert( "calling openPath for " + ti.getText() );
			ti.openPath( remainingPath, bSelect );
		}
		else
		{
			this._pathToOpen = sPath;
		}

	}
};




// Opera has some serious attribute problems. We need to use getAttribute
// for certain attributes
WebFXLoadTree._attrs = ["text", "src", "action", "id", "target"];

WebFXLoadTree.createItemFromElement = function (oNode) {
	var jsAttrs = {};
	var domAttrs = oNode.attributes;

	var l = domAttrs.length;
	for (var i = 0; i < l; i++)
	{
		if ( domAttrs[i] == null )
		{
			//alert( "error reading attribute " + i );
			continue;
		}
		jsAttrs[domAttrs[i].nodeName] = domAttrs[i].nodeValue;
	}


	var name, val;
	for ( var i = 0; i < WebFXLoadTree._attrs.length; i++ )
	{
		name = WebFXLoadTree._attrs[i];
		value = oNode.getAttribute( name );
		if ( value )
			jsAttrs[name] = value;
	}

	var jsNode = new WebFXLoadTreeItem(jsAttrs.text, jsAttrs.src, jsAttrs.action);

	if (jsAttrs.target)
		jsNode.target = jsAttrs.target;
	if (jsAttrs.id)
		jsNode.setId(jsAttrs.id);
	if (jsAttrs.toolTip)
		jsNode.toolTip = jsAttrs.toolTip;
	if (jsAttrs.expanded)
		jsNode.setExpanded( jsAttrs.expanded != "false" );

	jsNode.attributes = jsAttrs;

	// go through childNodes
	var cs = oNode.childNodes;
	var l = cs.length;
	for (var i = 0; i < l; i++) {
		if (cs[i].tagName == "tree")
			jsNode.add( WebFXLoadTree.createItemFromElement(cs[i]) );
	}

	return jsNode;
};

WebFXLoadTree.loadXmlDocument = function (jsNode) {
	if (jsNode.loading || jsNode.loaded)
		return;
	jsNode.loading = true;
	var id = jsNode.getId();
	if ( webFXLoadTreeQueue._ie )
	{
	}
	else
	{
		jsNode._xmlHttp = XmlHttp.create();
		jsNode._xmlHttp.open("GET", jsNode.src, true);	// async
		jsNode._xmlHttp.onreadystatechange = new Function("WebFXLoadTree._onload(\"" + id + "\")");
	}

	// call in new thread to allow ui to update
	window.setTimeout("WebFXLoadTree._ontimeout(\"" + id + "\")", 10 );
	//WebFXLoadTree._ontimeout(id);
};

WebFXLoadTree._onload = function (sId) {
	var jsNode = webFXTreeHandler.all[sId];
	if ( jsNode._xmlHttp.readyState == 4 )
	{
		WebFXLoadTree.documentLoaded( jsNode );
		webFXLoadTreeQueue.remove( jsNode );
		if ( !webFXLoadTreeQueue._ie && jsNode._xmlHttp.dispose )
			jsNode._xmlHttp.dispose();
		jsNode._xmlHttp = null;
	}
};

WebFXLoadTree._ontimeout = function (sId) {
	var jsNode = webFXTreeHandler.all[sId];
	//jsNode._xmlHttp.send(null);
	webFXLoadTreeQueue.add( jsNode );
};



// Inserts an xml document as a subtree to the provided node
WebFXLoadTree.documentLoaded = function (jsNode)
{
	if (jsNode.loaded)
		return;

	jsNode.errorText = "";
	jsNode.loaded = true;
	jsNode.loading = false;

	var t = jsNode.getTree();
	var oldSuspend = t.getSuspendRedraw();
	t.setSuspendRedraw(true);

	var doc = jsNode._xmlHttp.responseXML;

	// check that the load of the xml file went well
	if( !doc || doc.parseError.errorCode != 0 || !doc.documentElement )
	{
		if (!doc || doc.parseError.errorCode == 0) {
			//alert(jsNode._xmlHttp.status + ", " + jsNode._xmlHttp.statusText);
			jsNode.errorText = "Error loading " + jsNode.src + " (" + jsNode._xmlHttp.status + ": " + jsNode._xmlHttp.statusText + ")";
		}
		else {
			jsNode.errorText = "Error loading " + jsNode.src + " (" + doc.parseError.reason + ")";
		}

		/*
		jsNode.errorText = jsNode.src + ", reason: " + (doc ? doc.parseError.reason : "NODOC") + ", doc: " + !(!doc) +
			", documentElement: " + !(doc ? !doc.documentElement : true) +
			", parseError: " + (doc ? doc.parseError.errorCode : "NODOC");
		*/
	}
	else
	{
		// there is one extra level of tree elements
		var root = doc.documentElement;

		// loop through all tree children
		var count = 0;
		var cs = root.childNodes;
		var l = cs.length;
		for (var i = 0; i < l; i++)
		{
			if (cs[i].tagName == "tree")
			{
				jsNode.add( WebFXLoadTree.createItemFromElement(cs[i]) );
				count++;
			}
		}

		// if no children we got an error
		if (count == 0) {
			//document.open();
			//document.write( doc.xml)
			//document.close();
			jsNode.errorText = "Error loading " + jsNode.src + " (???)";
		}
	}

	if (jsNode.errorText != "")
	{
		jsNode._loadingItem.icon = "jsplugin/xtree2b/images/exclamation.16.gif";
		jsNode._loadingItem.text = jsNode.errorText;
		jsNode._loadingItem.action = WebFXLoadTree._reloadParent;
		jsNode._loadingItem.toolTip = "Click to reload";

		t.setSuspendRedraw(oldSuspend);

		jsNode._loadingItem.update();
	}
	else
	{
		// remove dummy
		if (jsNode._loadingItem != null)
			jsNode.remove( jsNode._loadingItem );

		if ( jsNode._pathToOpen )
			jsNode.openPath( jsNode._pathToOpen, jsNode._selectPathOnLoad );

		t.setSuspendRedraw(oldSuspend);
		jsNode.update();
	}

	//jsNode._xmlHttp.abort();
	//jsNode._xmlHttp.onreadystatechange = null;//new Function ();
};

WebFXLoadTree._reloadParent = function () {
	this.getParent().reload();
};







var webFXLoadTreeQueue =  {
	_nodes: [],
	_ie:	/msie/i.test(navigator.userAgent),
	_opera:	/opera/i.test(navigator.userAgent),

	add: function (jsNode) {
		if (this._ie || this._opera) {
			this._nodes.push(jsNode);
			if (this._nodes.length == 1)
				this._send();
		}
		else {
			jsNode._xmlHttp.send(null);
		}
	},


	remove: function (jsNode) {
		if (this._ie || this._opera) {
			this._nodes.remove(jsNode);
			if (this._nodes.length > 0)
				this._send();
		}
	},

	// IE only
	_send:	function () {
		var id = this._nodes[0].getId();
		var jsNode = webFXTreeHandler.all[id];
		if ( this._ie )
		{
			if ( !this._xmlHttp )
				this._xmlHttp = XmlHttp.create();
			jsNode._xmlHttp = this._xmlHttp;
			jsNode._xmlHttp.open("GET", jsNode.src, true);	// async
			jsNode._xmlHttp.onreadystatechange = new Function("WebFXLoadTree._onload(\"" + id + "\")");
		}
		jsNode._xmlHttp.send(null);
		//window.setTimeout("webFXTreeHandler.all[\"" + id + "\"]._xmlHttp.send(null);", 100);
	}
};