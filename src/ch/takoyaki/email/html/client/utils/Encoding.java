package ch.takoyaki.email.html.client.utils;

public final class Encoding {
	private Encoding() {
	}

	public static native String base64encode(String data) /*-{
		return $wnd.base64_encode(data);
	}-*/;

	public static native String utf8encode(String content) /*-{
		return $wnd.utf8_encode(content);
	}-*/;

}
