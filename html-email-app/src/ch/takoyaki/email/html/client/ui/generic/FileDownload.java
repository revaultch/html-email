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
package ch.takoyaki.email.html.client.ui.generic;

import ch.takoyaki.email.html.client.utils.Html;

import com.google.gwt.core.client.JavaScriptObject;
import com.google.gwt.user.client.DOM;
import com.google.gwt.user.client.Element;
import com.google.gwt.user.client.ui.RootPanel;

public class FileDownload {

	private final String fileName;
	private final String dataUri;

	private FileDownload(String fileName, String dataUri) {
		this.fileName = fileName;
		this.dataUri = dataUri;
	}

	public static FileDownload create(String fileName, String unencoded,
			String contentType, String charset) {
		String dataUri = Html.createDataUri(unencoded, contentType, charset);
		return new FileDownload(fileName, dataUri);
	}

	public static FileDownload createFromBase64(String fileName,
			String base64Encode, String contentType, String charset) {
		String dataUri = Html.createDataUri(base64Encode, contentType, charset,
				false);
		return new FileDownload(fileName, dataUri);
	}

	private Element createDomElement() {
		Element a = DOM.createElement("a");
		a.setAttribute("download", fileName);
		a.setAttribute("href", dataUri);
		a.setAttribute("style", "display:none;visibility:hidden;");
		return a;
	}

	private native void click(JavaScriptObject o) /*-{
		o.click();
	}-*/;

	public void trigger() {

		Element e = createDomElement();
		RootPanel.get().getElement().appendChild(e);
		click(e);

		RootPanel.get().getElement().removeChild(e);
	}
}
