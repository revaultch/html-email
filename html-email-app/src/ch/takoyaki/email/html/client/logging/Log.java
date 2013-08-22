package ch.takoyaki.email.html.client.logging;

public final class Log {
	private Log() {
	}

	public static native void log(Object message) /*-{
		console.log("log: " + message);
	}-*/;

}
