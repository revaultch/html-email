/*******************************************************************************
 * Copyright (c) 2013 takoyaki.ch.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the GNU Public License v3.0
 * which accompanies this distribution, and is available at
 * http://www.gnu.org/licenses/gpl.html
 * 
 * Contributors:
 *     takoyaki.ch - Initial version
 ******************************************************************************/
package ch.takoyaki.email.html.client.utils;

import ch.takoyaki.email.html.client.service.FileService;

import com.google.gwt.regexp.shared.MatchResult;
import com.google.gwt.regexp.shared.RegExp;

public final class Html {
	private Html() {
	}

	public static String createDataUri(String data, String encoding,
			String charset) {
		return createDataUri(data, encoding, charset, true);
	}

	public static String createDataUri(String data, String encoding,
			String charset, boolean dataNeedsBase64Encoding) {
		if (charset == null) {
			charset = "UTF-8";
		}
		if (encoding == null) {
			encoding = "text/plain";
		}
		if (charset.equals("UTF-8")) {
			data = Encoding.utf8encode(data);
		}
		if (dataNeedsBase64Encoding) {
			data = Encoding.base64encode(data);
		}
		return "data:" + encoding + ";charset=" + charset + ";base64," + data;
	}

	public static String inlinecss(String content, FileService fService) {
	
		RegExp p = RegExp
				.compile("<link [^>]*href=\"([^\"]*)\"[^>]*/?>", "gmi");
	
		MatchResult matcher = p.exec(content);
		while (matcher != null) {
			String tag = matcher.getGroup(0);
			String href = matcher.getGroup(1);
			String cssContent = fService.retrieve(href);
			if (cssContent == null) {
				cssContent = "";
			}
			cssContent = "<style>" + cssContent + "</style>";
			content = content.replace(tag, cssContent);
			matcher = p.exec(content);
		}
		return content;
	}


}
