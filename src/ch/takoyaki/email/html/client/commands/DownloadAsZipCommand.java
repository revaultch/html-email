package ch.takoyaki.email.html.client.commands;

import ch.takoyaki.email.html.client.service.FileService;
import ch.takoyaki.email.html.client.utils.Html;

import com.google.gwt.core.client.Scheduler.ScheduledCommand;
import com.google.gwt.user.client.Window;

public class DownloadAsZipCommand implements ScheduledCommand {

	private final FileService fileService;

	public DownloadAsZipCommand(FileService fservice) {
		this.fileService = fservice;
	}

	@Override
	public void execute() {
		String content = fileService.retrieveAllAsZipBase64();
		String uri = Html.createDataUri(content, "application/zip", "UTF-8",
				false);
		Window.open(uri, "source.zip", "");
	}

}
