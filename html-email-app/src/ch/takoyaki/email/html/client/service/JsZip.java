package ch.takoyaki.email.html.client.service;

import com.google.gwt.core.client.JavaScriptObject;

public class JsZip {

	public static final class JsZipWrapper extends JavaScriptObject {

		protected JsZipWrapper() {
		}

		private native String generateBase64() /*-{
			return this.generate({
				type : "base64"
			})
		}-*/;

		public native void addFileBase64(String name, String content) /*-{
			this.file(name, content, {
				base64 : true
			});
		}-*/;

		public native void addFile(String name, String content) /*-{
			this.file(name, content);
		}-*/;

		public native JsZipWrapper addFolder(String name) /*-{
			return this.folder(name);
		}-*/;
	}

	private final JsZipWrapper zip;

	public JsZip() {
		this.zip = newJsZipNative();
	}

	public void addFile(String name, String content) {
		zip.addFile(name, content);
	}


	public JsZipWrapper addFolder(String name) {
		return zip.addFolder(name);
	}
	
	public void addFileBase64(String name, String content) {
		zip.addFileBase64(name, content);
	}

	public String generateBase64() {
		return zip.generateBase64();
	}

	private static native JsZipWrapper newJsZipNative() /*-{
		return new $wnd.JSZip();
	}-*/;

}
