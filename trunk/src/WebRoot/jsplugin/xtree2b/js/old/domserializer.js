/*----------------------------------------------------------------------------\
|                        DomSerializer 2 PRE RELEASE                          |
|                                                                             |
| This is a pre release and may not be redistributed.                         |
| Watch http://webfx.eae.net for the final version                            |
|                                                                             |
|-----------------------------------------------------------------------------|
|                         Created by Erik Arvidsson                           |
|                  (http://webfx.eae.net/contact.html#erik)                   |
|                      For WebFX (http://webfx.eae.net/)                      |
|-----------------------------------------------------------------------------|
| A class for serializing DOM trees to XML. Can be used with Document or      |
| HTMLDocuemnt as defined by the W3C DOM                                      |
|-----------------------------------------------------------------------------|
|                 Copyright (c) 1999 - 2004 Erik Arvidsson                    |
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
|-----------------------------------------------------------------------------|
| Created 2003-??-?? | All changes are in the log above. | Updated 2004-02-21 |
\----------------------------------------------------------------------------*/

function DomSerializer(oNode) {
	this.node = oNode;
}

DomSerializer.prototype.toString = function () {
	// Try xml property (MSXML)
	var s = this.node.xml;
	if (typeof s == "string")
		return s;

	// try XMLSerializer (Mozilla)
	if (typeof XMLSerializer != "undefined") {
		return (new XMLSerializer()).serializeToString(this.node);
	}

	var sb = [];
	_appendNodeXml(this.node, sb);
	return sb.join("");
};

function _fixAttribute(s) {
	return String(s).replace(/\&/g, "&amp;").replace(/</g, "&lt;").replace(/\"/g, "&quot;");
}

function _fixText(s) {
	return String(s).replace(/\&/g, "&amp;").replace(/</g, "&lt;");
}

function _appendNodeXml(node, sb) {

	switch (node.nodeType) {
		case 1:	// ELEMENT

			var name = node.localName || node.nodeName;
			sb.push("<" + name);

			// attributes
			var attrs = node.attributes;
			var l = attrs.length;
			for (var i = 0; i < l; i++) {
				if (attrs[i].specified) {
					sb.push(" " + attrs[i].nodeName + "=\"" +
						_fixAttribute(attrs[i].nodeValue) + "\"");
				}
			}

			if (node.hasChildNodes()) {
				sb.push(">");

				// childNodes
				var cs = node.childNodes;
				l = cs.length;
				for (var i = 0; i < l; i++)
					_appendNodeXml(cs[i], sb);

				sb.push("</" + name + ">");
			}
			else
				sb.push("/>");

			break;

		case 3:	// TEXT
			sb.push( _fixText(node.nodeValue) );
			break;

		case 4:
			sb.push("<![CDA" + "TA[\n" + node.nodeValue + "\n]" + "]>");
			break;

		case 7:	// PROCESSING_INSTRUCTION_NODE
			sb.push("<?" + node.nodeName + node.nodeValue + "?>");
		  break;

		case 8:
			sb.push("<!--" + node.nodeValue + "-->");
			break;

		case 9:	// DOCUMENT
		case 11: // DOCUMENT_FRAGMENT
			// childNodes
			var cs = node.childNodes;
			l = cs.length;
			for (var i = 0; i < l; i++)
				_appendNodeXml(cs[i], sb);
			break;

		default:
			sb.push("<!--\nNot Supported:\n\n" + "nodeType: " + node.nodeType + "\nnodeName: " + node.nodeName + "\n-->");
	}
}