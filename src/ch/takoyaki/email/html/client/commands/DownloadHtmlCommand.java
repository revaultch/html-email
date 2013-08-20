package ch.takoyaki.email.html.client.commands;

import ch.takoyaki.email.html.client.ContentRenderer;
import ch.takoyaki.email.html.client.utils.Html;

import com.google.gwt.core.client.Scheduler.ScheduledCommand;
import com.google.gwt.user.client.Window;

public class DownloadHtmlCommand implements ScheduledCommand {

	private ContentRenderer contentRenderer;

	public DownloadHtmlCommand(ContentRenderer contentRenderer) {
		this.contentRenderer = contentRenderer;
	}

	@Override
	public void execute() {
		String content = contentRenderer.getRenderedContent();
		if (content == null) {
			return;
		}

		String uri = Html.createDataUri(content, "text/html", "UTF-8");
		Window.open(uri, "source.html", "");
	}
}
