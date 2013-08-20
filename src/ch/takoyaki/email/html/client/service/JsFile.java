package ch.takoyaki.email.html.client.service;

import com.google.gwt.core.client.JavaScriptObject;

public  final class JsFile extends JavaScriptObject {
	protected JsFile() {
	}

	public native String getName() /*-{
		return this.name;
	}-*/;

	public native String getType() /*-{
		return this.type || 'n/a';
	}-*/;

	public native int getByteSize() /*-{
		return this.size;
	}-*/;

	public native String getLastModied() /*-{
		return this.lastModifiedDate ? this.lastModifiedDate
				.toLocaleDateString() : 'n/a';
	}-*/;
}

