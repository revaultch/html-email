package ch.takoyaki.email.html.client.commands;

import ch.takoyaki.email.html.client.ContentRenderer;
import ch.takoyaki.email.html.client.service.FileService;
import ch.takoyaki.email.html.client.ui.generic.FileDownload;
import ch.takoyaki.email.html.client.utils.Postprocessing;

import com.google.gwt.core.client.Scheduler.ScheduledCommand;

public class DownloadHtmlCommand implements ScheduledCommand {

	private final ContentRenderer contentRenderer;
	private final FileService fservice;

	public DownloadHtmlCommand(ContentRenderer contentRenderer,
			FileService fservice) {
		this.contentRenderer = contentRenderer;
		this.fservice = fservice;
	}

	@Override
	public void execute() {
		String content = contentRenderer.getRenderedContent();
		if (content == null) {
			return;
		}
		content = Postprocessing.beforeSaveHtml(content, fservice);

		FileDownload.create(contentRenderer.getRenderedFileName() + ".html",
				content, "text/html", "UTF-8").trigger();
	}
}
