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
package ch.takoyaki.email.html.client.service;

import com.google.gwt.core.client.JavaScriptObject;

public class JsFileReader {
	public static interface LoadCompleteHandler {
		void onComplete(JsFile file, String content);

		void onError(JsFile file);
	}

	public static final class JsFileReaderWrapper extends JavaScriptObject {

		protected JsFileReaderWrapper() {
		}

		private native void readAsTextNative(JsFile file, LoadCompleteHandler h)/*-{
			this.onloadend = function(evt) {
				if (evt.target.readyState == FileReader.DONE) { // DONE == 2
					var content = evt.target.result;
					h.@ch.takoyaki.email.html.client.service.JsFileReader.LoadCompleteHandler::onComplete(Lch/takoyaki/email/html/client/service/JsFile;Ljava/lang/String;)(file,content);
				} else {
					h.@ch.takoyaki.email.html.client.service.JsFileReader.LoadCompleteHandler::onError(Lch/takoyaki/email/html/client/service/JsFile;)(file);
				}
			};
			this.onerror = function(evt) {
				h.@ch.takoyaki.email.html.client.service.JsFileReader.LoadCompleteHandler::onError(Lch/takoyaki/email/html/client/service/JsFile;)(file);
			}
			this.readAsText(file);
		}-*/;
	}

	private final JsFile file;
	private final JsFileReaderWrapper fileReader;

	public JsFileReader(JsFile file) {
		this.file = file;
		this.fileReader = newFileReaderNative();
	}

	private static native JsFileReaderWrapper newFileReaderNative() /*-{
		return new FileReader();
	}-*/;

	public void readAsText(LoadCompleteHandler h) {
		fileReader.readAsTextNative(file, h);
	}

}
