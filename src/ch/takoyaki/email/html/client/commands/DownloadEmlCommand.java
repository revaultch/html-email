package ch.takoyaki.email.html.client.commands;

import ch.takoyaki.email.html.client.ContentRenderer;
import ch.takoyaki.email.html.client.utils.Html;

import com.google.gwt.core.client.Scheduler.ScheduledCommand;
import com.google.gwt.user.client.Window;

public class DownloadEmlCommand implements ScheduledCommand {

	private final ContentRenderer contentRenderer;

	public DownloadEmlCommand(ContentRenderer contentRenderer) {
		this.contentRenderer = contentRenderer;
	}

	@Override
	public void execute() {
		String content = contentRenderer.getRenderedContent();
		if (content == null) {
			return;
		}
		content = wrapMime("Subject", content);
		String uri = Html.createDataUri(content, "message/rfc822", "UTF-8");
		Window.open(uri, "email.eml", "");
	}

	private native String wrapMime(String subject, String content) /*-{		
		return $wnd.wrapMime(subject, content);
	}-*/;

}
