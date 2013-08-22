package ch.takoyaki.email.html.client.commands;

import ch.takoyaki.email.html.client.service.FileService;
import ch.takoyaki.email.html.client.ui.generic.FileDownload;

import com.google.gwt.core.client.Scheduler.ScheduledCommand;

public class DownloadAsZipCommand implements ScheduledCommand {

	private final FileService fileService;

	public DownloadAsZipCommand(FileService fservice) {
		this.fileService = fservice;
	}

	@Override
	public void execute() {
		String content = fileService.retrieveAllAsZipBase64();

		FileDownload.createFromBase64("sources.zip", content,
				"application/zip", "UTF-8").trigger();

	}

}
