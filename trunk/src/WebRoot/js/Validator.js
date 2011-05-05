
Validator = {Require:/.+/, Email:/^([a-zA-Z0-9._-])+@([a-zA-Z0-9_-]+\.)+([a-zA-Z0-9_-]{1,})$/, Phone:/\w/, Mobile:/\w/, Url:/^http:\/\/[A-Za-z0-9]+\.[A-Za-z0-9]+[\/=\?%\-&_~`@[\]\':+!]*([^<>\"\"])*$/, IdCard:"this.IsIdCard(value)", Currency:/^\d+(\.\d+)?$/, Number:/^\d+$/, Zip:/^[1-9]\d{5}$/, Ip:/^((\d|[1-9]\d|1\d\d|2[0-4]\d|25[0-5])\.){3}(\d|[1-9]\d|1\d\d|2[0-4]\d|25[0-5])$/, QQ:/^[1-9]\d{4,8}$/, Integer:/^[-\+]?\d+$/, Double:/^[-\+]?\d+(\.\d+)?$/, English:/^[A-Za-z]+$/, Chinese:/^[\u0391-\uFFE5]+$/, Username:/^[a-z]\w{3,}$/i, UnSafe:/^(([A-Z]*|[a-z]*|\d*|[-_\~!@#\$%\^&\*\.\(\)\[\]\{\}<>\?\\\/\'\"]*)|.{0,5})$|\s/, IsSafe:function (str) {
	return !this.UnSafe.test(str);
}, SafeString:"this.IsSafe(value)", Filter:"this.DoFilter(value, getAttribute('accept'))", Limit:"this.limit(value.length,getAttribute('min'),  getAttribute('max'))", LimitB:"this.limit(this.LenB(value), getAttribute('min'), getAttribute('max'))", Date:"this.IsDate(value, getAttribute('min'), getAttribute('format'))", Repeat:"value == document.getElementsByName(getAttribute('to'))[0].value", Range:"getAttribute('min') < (value|0) && (value|0) < getAttribute('max')", Compare:"this.compare(value,getAttribute('operator'),getAttribute('to'))", Custom:"this.Exec(value, getAttribute('regexp'))", Group:"this.MustChecked(getAttribute('name'), getAttribute('min'), getAttribute('max'))", ErrorItem:[document.forms[0]], ErrorMessage:["\u4ee5\u4e0b\u539f\u56e0\u5bfc\u81f4\u63d0\u4ea4\u5931\u8d25\uff1a\t\t\t\t"], Validate:function (theForm, mode) {
	var obj = theForm || event.srcElement;
	var count = obj.elements.length;
	this.ErrorMessage.length = 1;
	this.ErrorItem.length = 1;
	this.ErrorItem[0] = obj;
	for (var i = 0; i < count; i++) {
		with (obj.elements[i]) {
			var _dataType = getAttribute("dataType");
			var _msg = getAttribute("msg");
			if (_dataType == null || _dataType.length == 0) {
				continue;
			}
			this.ClearState(obj.elements[i]);
			if (getAttribute("require") == "false" && value == "") {
				continue;
			}
			var dts = _dataType.split("|");
			var ems = _msg.split("|");
			var errmes = "";
			for (var j = 0; j < dts.length; j++) {
				var curValidType = dts[j];
				var curErrorMsg = ems[j];
				switch (curValidType) {
				  case "IdCard":
				  case "Date":
				  case "Repeat":
				  case "Range":
				  case "Compare":
				  case "Custom":
				  case "Group":
				  case "Limit":
				  case "LimitB":
				  case "SafeString":
				  case "Filter":
					if (!eval(this[curValidType])) {
						errmes = errmes + curErrorMsg;
							//this.AddError(i, curErrorMsg);
					}
					break;
				  default:
					if (!this[curValidType].test(value)) {
						errmes = errmes + curErrorMsg;
							//this.AddError(i, curErrorMsg);
					}
					break;
				}
			}
			if (errmes != "") {
				this.AddError(i, errmes);
			}
		}
	}
	if (this.ErrorMessage.length > 1) {
		mode = mode || 1;
		var errCount = this.ErrorItem.length;
		switch (mode) {
		  case 2:
			for (var i = 1; i < errCount; i++) {
				this.ErrorItem[i].style.color = "red";
			}
		  case 1:
			alert(this.ErrorMessage.join("\n"));
			this.ErrorItem[1].focus();
			break;
		  case 3:
			for (var i = 1; i < errCount; i++) {
				try {
					var span = document.createElement("SPAN");
					span.id = "__ErrorMessagePanel";
					span.style.color = "red";
					this.ErrorItem[i].parentNode.appendChild(span);
					span.innerHTML = this.ErrorMessage[i].replace(/\d+:/, "*");
				}
				catch (e) {
					alert(e.description);
				}
			}
			
			// this.ErrorItem[1].focus();
				
			break;
		  default:
			alert(this.ErrorMessage.join("\n"));
			break;
		}
		return false;
	}
	return true;
}, limit:function (len, min, max) {
	min = min || 0;
	max = max || Number.MAX_VALUE;
	return min <= len && len <= max;
}, LenB:function (str) {
	return str.replace(/[^\x00-\xff]/g, "**").length;
}, ClearState:function (elem) {
	with (elem) {
		if (style.color == "red") {
			style.color = "";
		}
		var lastNode = parentNode.childNodes[parentNode.childNodes.length - 1];
		if (lastNode.id == "__ErrorMessagePanel") {
			parentNode.removeChild(lastNode);
		}
	}
}, AddError:function (index, str) {
	this.ErrorItem[this.ErrorItem.length] = this.ErrorItem[0].elements[index];
	this.ErrorMessage[this.ErrorMessage.length] = this.ErrorMessage.length + ":" + str;
}, Exec:function (op, reg) {
	return new RegExp(reg, "g").test(op);
}, compare:function (op1, operator, op2) {
	switch (operator) {
	  case "NotEqual":
		return (op1 != op2);
	  case "GreaterThan":
		return (op1 > op2);
	  case "GreaterThanEqual":
		return (op1 >= op2);
	  case "LessThan":
		return (op1 < op2);
	  case "LessThanEqual":
		return (op1 <= op2);
	  default:
		return (op1 == op2);
	}
}, MustChecked:function (name, min, max) {
	var groups = document.getElementsByName(name);
	var hasChecked = 0;
	min = min || 1;
	max = max || groups.length;
	for (var i = groups.length - 1; i >= 0; i--) {
		if (groups[i].checked) {
			hasChecked++;
		}
	}
	return min <= hasChecked && hasChecked <= max;
}, DoFilter:function (input, filter) {
	return new RegExp("^.+.(?=EXT)(EXT)$".replace(/EXT/g, filter.split(/\s*,\s*/).join("|")), "gi").test(input);
}, IsIdCard:function (idcard) {
	var area = {11:"\u5317\u4eac", 12:"\u5929\u6d25", 13:"\u6cb3\u5317", 14:"\u5c71\u897f", 15:"\u5185\u8499\u53e4", 21:"\u8fbd\u5b81", 22:"\u5409\u6797", 23:"\u9ed1\u9f99\u6c5f", 31:"\u4e0a\u6d77", 32:"\u6c5f\u82cf", 33:"\u6d59\u6c5f", 34:"\u5b89\u5fbd", 35:"\u798f\u5efa", 36:"\u6c5f\u897f", 37:"\u5c71\u4e1c", 41:"\u6cb3\u5357", 42:"\u6e56\u5317", 43:"\u6e56\u5357", 44:"\u5e7f\u4e1c", 45:"\u5e7f\u897f", 46:"\u6d77\u5357", 50:"\u91cd\u5e86", 51:"\u56db\u5ddd", 52:"\u8d35\u5dde", 53:"\u4e91\u5357", 54:"\u897f\u85cf", 61:"\u9655\u897f", 62:"\u7518\u8083", 63:"\u9752\u6d77", 64:"\u5b81\u590f", 65:"\u65b0\u7586", 71:"\u53f0\u6e7e", 81:"\u9999\u6e2f", 82:"\u6fb3\u95e8", 91:"\u56fd\u5916"};
	var idcard, Y, JYM;
	var S, M;
	var idcard_array = new Array();
	idcard_array = idcard.split("");
	if (area[parseInt(idcard.substr(0, 2))] == null) {
		return false;
	}
	switch (idcard.length) {
	  case 15:
		if ((parseInt(idcard.substr(6, 2)) + 1900) % 4 == 0 || ((parseInt(idcard.substr(6, 2)) + 1900) % 100 == 0 && (parseInt(idcard.substr(6, 2)) + 1900) % 4 == 0)) {
			ereg = /^[1-9][0-9]{5}[0-9]{2}((01|03|05|07|08|10|12)(0[1-9]|[1-2][0-9]|3[0-1])|(04|06|09|11)(0[1-9]|[1-2][0-9]|30)|02(0[1-9]|[1-2][0-9]))[0-9]{3}$/;//测试出生日期的合法性 
		} else {
			ereg = /^[1-9][0-9]{5}[0-9]{2}((01|03|05|07|08|10|12)(0[1-9]|[1-2][0-9]|3[0-1])|(04|06|09|11)(0[1-9]|[1-2][0-9]|30)|02(0[1-9]|1[0-9]|2[0-8]))[0-9]{3}$/;//测试出生日期的合法性 
		}
		if (ereg.test(idcard)) {
			return true;
		} else {
			return false;
		}
		break;
	  case 18:
		if (parseInt(idcard.substr(6, 4)) % 4 == 0 || (parseInt(idcard.substr(6, 4)) % 100 == 0 && parseInt(idcard.substr(6, 4)) % 4 == 0)) {
			ereg = /^[1-9][0-9]{5}19[0-9]{2}((01|03|05|07|08|10|12)(0[1-9]|[1-2][0-9]|3[0-1])|(04|06|09|11)(0[1-9]|[1-2][0-9]|30)|02(0[1-9]|[1-2][0-9]))[0-9]{3}[0-9Xx]$/;//闰年出生日期的合法性正则表达式 
		} else {
			ereg = /^[1-9][0-9]{5}19[0-9]{2}((01|03|05|07|08|10|12)(0[1-9]|[1-2][0-9]|3[0-1])|(04|06|09|11)(0[1-9]|[1-2][0-9]|30)|02(0[1-9]|1[0-9]|2[0-8]))[0-9]{3}[0-9Xx]$/;//平年出生日期的合法性正则表达式 
		}
		if (ereg.test(idcard)) {
			S = (parseInt(idcard_array[0]) + parseInt(idcard_array[10])) * 7 + (parseInt(idcard_array[1]) + parseInt(idcard_array[11])) * 9 + (parseInt(idcard_array[2]) + parseInt(idcard_array[12])) * 10 + (parseInt(idcard_array[3]) + parseInt(idcard_array[13])) * 5 + (parseInt(idcard_array[4]) + parseInt(idcard_array[14])) * 8 + (parseInt(idcard_array[5]) + parseInt(idcard_array[15])) * 4 + (parseInt(idcard_array[6]) + parseInt(idcard_array[16])) * 2 + parseInt(idcard_array[7]) * 1 + parseInt(idcard_array[8]) * 6 + parseInt(idcard_array[9]) * 3;
			Y = S % 11;
			M = "F";
			JYM = "10X98765432";
			M = JYM.substr(Y, 1);
			if (M == idcard_array[17]) {
				return true;
			} else {
				return false;
			}
		} else {
			return false;
		}
		break;
	  default:
		return false;
		break;
	}
}, IsDate:function (op, formatString) {
	formatString = formatString || "ymd";
	var m, year, month, day;
	switch (formatString) {
	  case "ymd":
		m = op.match(new RegExp("^((\\d{4})|(\\d{2}))([-./])(\\d{1,2})\\4(\\d{1,2})$"));
		if (m == null) {
			return false;
		}
		day = m[6];
		month = m[5] * 1;
		year = (m[2].length == 4) ? m[2] : GetFullYear(parseInt(m[3], 10));
		break;
	  case "dmy":
		m = op.match(new RegExp("^(\\d{1,2})([-./])(\\d{1,2})\\2((\\d{4})|(\\d{2}))$"));
		if (m == null) {
			return false;
		}
		day = m[1];
		month = m[3] * 1;
		year = (m[5].length == 4) ? m[5] : GetFullYear(parseInt(m[6], 10));
		break;
	  default:
		break;
	}
	if (!parseInt(month)) {
		return false;
	}
	month = month == 0 ? 12 : month;
	var date = new Date(year, month - 1, day);
	return (typeof (date) == "object" && year == date.getFullYear() && month == (date.getMonth() + 1) && day == date.getDate());
	function GetFullYear(y) {
		return ((y < 30 ? "20" : "19") + y) | 0;
	}
}};

