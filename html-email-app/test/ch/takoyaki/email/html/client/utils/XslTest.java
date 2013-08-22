package ch.takoyaki.email.html.client.utils;

import static org.junit.Assert.*;

import org.junit.Test;

public class XslTest extends Xsl {

	String str = "<!--\n"
			+ "Use this template to render as html with external css: \n"
			+ "<?xml-stylesheet type=\"text/xsl\" href=\"styles_online.xsl\"?>\n"
			+ "-->\n"
			+ "\n"
			+ "<!--\n"
			+ "Use this template to render as plain text:\n"
			+ "<?xml-stylesheet type=\"text/xsl\" href=\"styles_email_txt.xsl\"?>\n"
			+ "-->\n"
			+ "\n"
			+ "<!--\n"
			+ "Use this template to render for html email:\n"
			+ "<?xml-stylesheet type=\"text/xsl\" href=\"styles_email_html.xsl\"?>\n"
			+ "-->\n"
			+ "<newsletter>\n"
			+ " <language iso=\"DE\" /> <!-- change the language here FR,IT,EN -->";

	@Test
	public void testStripXmlComments() {

		assertEquals("\n\n\n\n\n<newsletter>\n <language iso=\"DE\" /> ",
				Xsl.stripXmlComments(str));

	}
}
