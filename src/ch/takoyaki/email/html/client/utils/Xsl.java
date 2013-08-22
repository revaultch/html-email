package ch.takoyaki.email.html.client.utils;

import ch.takoyaki.email.html.client.service.FileService;

import com.google.gwt.regexp.shared.MatchResult;
import com.google.gwt.regexp.shared.RegExp;

public class Xsl {

	private static String stripXmlComments(String content) {
		RegExp p = RegExp.compile("<!--[\\w\\W]*-->", "gmi");
		return p.replace(content, "");
	}

	public static String loadStyleSheetContent(String content,
			FileService fservice) {

		String type = "";
		String href = "";

		content = stripXmlComments(content);
		RegExp p = RegExp
				.compile("<\\?xml-stylesheet[^>]*type=\"([^\"]*)", "m");
		MatchResult r = p.exec(content);
		if (r != null) {
			type = r.getGroup(1);
		}
		p = RegExp.compile("<\\?xml-stylesheet[^>]*href=\"([^\"]*)", "m");
		r = p.exec(content);
		if (r != null) {
			href = r.getGroup(1);
		}

		if (!type.equals("text/xsl") || "".equals(href)) {
			return null;
		}

		String xsl = fservice.retrieve(href);

		xsl = inlineXslInclude(xsl, fservice);
		return xsl;
	}

	private static String inlineXslInclude(String xsl, FileService fService) {

		RegExp p = RegExp.compile(
				"<[^:]*:?include[^>]*href=\"([^\"]*)\"[^>]*/?>", "gmi");

		MatchResult matcher = p.exec(xsl);
		while (matcher != null) {
			String tag = matcher.getGroup(0);
			String href = matcher.getGroup(1);
			String xslinclude = fService.retrieve(href);
			if (xslinclude == null) {
				xslinclude = "";
			}
			xslinclude = xslIncludeStrip(xslinclude);
			xsl = xsl.replace(tag, xslinclude);
			matcher = p.exec(xsl);
		}
		return xsl;
	}

	private static String xslIncludeStrip(String xslinclude) {
		RegExp p = RegExp.compile(
				"<[^:]*:?stylesheet[^>]*>([\\w\\W]*)</[^:]*:?stylesheet>",
				"gmi");
		MatchResult m = p.exec(xslinclude);
		if (m != null) {
			return m.getGroup(1);
		}
		return "";
	}

	private static native String xslttransformNative(String content,
			String xsl) /*-{
		return $wnd.xslttransform($wnd.stringtoXML(content), $wnd
				.stringtoXML(xsl));
	}-*/;

	public static String xslttransform(String xsl, String content) {
		return xslttransformNative(content, xsl);
	}

	public static String removeStyleSheet(String content) {
		content = stripXmlComments(content);
		return RegExp.compile("^<\\?xml-stylesheet[^>]*\\?>", "gmi").replace(
				content, "");
	};

}
