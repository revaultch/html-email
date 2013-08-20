function xmlToString(xmlData) {

	var xmlString;
	// IE
	if (window.ActiveXObject) {
		xmlString = xmlData.xml;
	}
	// code for Mozilla, Firefox, Opera, etc.
	else {
		xmlString = (new XMLSerializer()).serializeToString(xmlData);
	}
	return xmlString;
}

function stringtoXML(text) {
	if (window.ActiveXObject) {
		var doc = new ActiveXObject('Microsoft.XMLDOM');
		doc.async = 'false';
		doc.loadXML(text);
	} else {
		var parser = new DOMParser();
		var doc = parser.parseFromString(text, 'text/xml');
	}
	return doc;
}

function getIframeScrollTop(id) {
	try {
		return document.getElementById(id).contentWindow.document.documentElement.scrollTop;
	} catch (e) {
		console.log("Error " + e.message);
		return 0;
	}
}

function setIframeScrollTop(id, top) {
	try {
		document.getElementById(id).contentWindow.document.documentElement.scrollTop = top;
	} catch (e) {
		console.log("Error " + e.message);
	}
}

function getIframeContent(id) {
	try {
		var content = xmlToString(document.getElementById(id).contentDocument);
		return content;
	} catch (e) {
		console.log("Error " + e.message);
		return null;
	}
}

function setIframeContent(id, content) {
	try {
		document.getElementById(id).contentDocument.documentElement.innerHTML = content;
	} catch (e) {
		console.log("Error " + e.message);
	}
}

function utf8_encode(content) {
	return unescape(encodeURIComponent(content));
}

function quoted_printable_encode(str) {
	// + original by: Theriault
	// + improved by: Brett Zamir (http://brett-zamir.me)
	// + improved by: Theriault
	// * example 1: quoted_printable_encode('a=b=c');
	// * returns 1: 'a=3Db=3Dc'
	// * example 2: quoted_printable_encode('abc \r\n123 \r\n');
	// * returns 2: 'abc =20\r\n123 =20\r\n'
	// * example 3:
	// quoted_printable_encode('0123456789012345678901234567890123456789012345678901234567890123456789012345');
	// * returns 3:
	// '012345678901234567890123456789012345678901234567890123456789012345678901234=\r\n5'
	// RFC 2045: 6.7.2: Octets with decimal values of 33 through 60 (bang to
	// less-than) inclusive, and 62 through 126 (greater-than to tilde),
	// inclusive, MAY be represented as the US-ASCII characters
	// PHP does not encode any of the above; as does this function.
	// RFC 2045: 6.7.3: Octets with values of 9 and 32 MAY be represented as
	// US-ASCII TAB (HT) and SPACE characters, respectively, but MUST NOT be
	// so
	// represented at the end of an encoded line
	// PHP does not encode spaces (octet 32) except before a CRLF sequence
	// as
	// stated above. PHP always encodes tabs (octet 9). This function
	// replicates
	// PHP.
	// RFC 2045: 6.7.4: A line break in a text body, represented as a CRLF
	// sequence in the text canonical form, must be represented by a (RFC
	// 822)
	// line break
	// PHP does not encode a CRLF sequence, as does this function.
	// RFC 2045: 6.7.5: The Quoted-Printable encoding REQUIRES that encoded
	// lines be no more than 76 characters long. If longer lines are to be
	// encoded with the Quoted-Printable encoding, "soft" line breaks must
	// be
	// used.
	// PHP breaks lines greater than 76 characters; as does this function.
	var hexChars = [ '0', '1', '2', '3', '4', '5', '6', '7', '8', '9', 'A',
			'B', 'C', 'D', 'E', 'F' ], RFC2045Encode1IN = / \r\n|\r\n|[^!-<>-~ ]/gm, RFC2045Encode1OUT = function(
			sMatch) {
		// Encode space before CRLF sequence to prevent spaces from being
		// stripped
		// Keep hard line breaks intact; CRLF sequences
		if (sMatch.length > 1) {
			return sMatch.replace(' ', '=20');
		}
		// Encode matching character
		var chr = sMatch.charCodeAt(0);
		return '=' + hexChars[((chr >>> 4) & 15)] + hexChars[(chr & 15)];
	},
	// Split lines to 75 characters; the reason it's 75 and not 76 is
	// because
	// softline breaks are preceeded by an equal sign; which would be the
	// 76th
	// character.
	// However, if the last line/string was exactly 76 characters, then a
	// softline would not be needed. PHP currently softbreaks anyway; so
	// this
	// function replicates PHP.
	RFC2045Encode2IN = /.{1,72}(?!\r\n)[^=]{0,3}/g, RFC2045Encode2OUT = function(
			sMatch) {
		if (sMatch.substr(sMatch.length - 2) === '\r\n') {
			return sMatch;
		}
		return sMatch + '=\r\n';
	};
	str = str.replace(RFC2045Encode1IN, RFC2045Encode1OUT).replace(
			RFC2045Encode2IN, RFC2045Encode2OUT);
	// Strip last softline break
	return str.substr(0, str.length - 3);
}

function wrapMime(subject, content) {
	var delim = "--B_3459318006_46440069";
	var mail = "User-Agent: Microsoft-MacOutlook/14.3.6.130613\n"
			+ "Date: Wed, 14 Aug 2013 09:39:52 +0200\n"
			+ "Subject: "
			+ subject
			+ "\n"
			+ "From: newsletter@takoyaki.ch\n"
			+ "Message-ID: <CE3100E8.1%x@x.x>\n"
			+ "Mime-version: 1.0\n"
			+ "Content-type: multipart/alternative;\n"
			+ "	boundary=\"B_3459318006_46440069\"\n"
			+ "\n"
			+ "> This message is in MIME format. Since your mail reader does not understand\n"
			+ "this format, some or all of this message may not be legible.\n"
			+ "\n" + delim + "\n" + "Content-type: text/html;\n"
			+ "	charset=\"UTF-8\"\n"
			+ "Content-Disposition: inline;filename=newsletter.html\n"
			+ "Content-transfer-encoding: quoted-printable\n" + "\n";

	var result = mail + quoted_printable_encode(utf8_encode(content)) + "\n"
			+ delim + "--\n";
	return result;
}

function xslttransform(doc, xsl, dtype) {
	if (window.ActiveXObject) {
		ex = doc.transformNode(xsl);
		return dtype + xmlToString(ex);
	}
	// code for Mozilla, Firefox, Opera, etc.
	else if (document.implementation && document.implementation.createDocument) {
		try {
			xsltProcessor = new XSLTProcessor();
			xsltProcessor.importStylesheet(xsl);
			var transformed = xsltProcessor.transformToDocument(doc, document);
			return dtype + xmlToString(transformed);

		} catch (e) {
			console.log(e.message);
			return ""
		}

	} else {
		return "";
	}
}

function base64_encode(data) {
	// http://kevin.vanzonneveld.net
	// + original by: Tyler Akins (http://rumkin.com)
	// + improved by: Bayron Guevara
	// + improved by: Thunder.m
	// + improved by: Kevin van Zonneveld (http://kevin.vanzonneveld.net)
	// + bugfixed by: Pellentesque Malesuada
	// + improved by: Kevin van Zonneveld (http://kevin.vanzonneveld.net)
	// + improved by: Rafał Kukawski (http://kukawski.pl)
	// * example 1: base64_encode('Kevin van Zonneveld');
	// * returns 1: 'S2V2aW4gdmFuIFpvbm5ldmVsZA=='
	// mozilla has this native
	// - but breaks in 2.0.0.12!
	// if (typeof this.window['btoa'] === 'function') {
	// return btoa(data);
	// }
	var b64 = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789+/=";
	var o1, o2, o3, h1, h2, h3, h4, bits, i = 0, ac = 0, enc = "", tmp_arr = [];

	if (!data) {
		return data;
	}

	do { // pack three octets into four hexets
		o1 = data.charCodeAt(i++);
		o2 = data.charCodeAt(i++);
		o3 = data.charCodeAt(i++);

		bits = o1 << 16 | o2 << 8 | o3;

		h1 = bits >> 18 & 0x3f;
		h2 = bits >> 12 & 0x3f;
		h3 = bits >> 6 & 0x3f;
		h4 = bits & 0x3f;

		// use hexets to index into b64, and append result to encoded string
		tmp_arr[ac++] = b64.charAt(h1) + b64.charAt(h2) + b64.charAt(h3)
				+ b64.charAt(h4);
	} while (i < data.length);

	enc = tmp_arr.join('');

	var r = data.length % 3;

	return (r ? enc.slice(0, r - 3) : enc) + '==='.slice(r || 3);
}
