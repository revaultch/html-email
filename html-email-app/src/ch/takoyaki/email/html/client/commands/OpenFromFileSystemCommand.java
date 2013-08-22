package ch.takoyaki.email.html.client.commands;

import ch.takoyaki.email.html.client.service.FileService;
import ch.takoyaki.email.html.client.ui.UploadDialog;
import ch.takoyaki.email.html.client.ui.generic.CloseableTabs;

import com.google.gwt.core.client.Scheduler.ScheduledCommand;

public class OpenFromFileSystemCommand implements ScheduledCommand {

	private final FileService fservice;
	private final CloseableTabs tab;

	public OpenFromFileSystemCommand(FileService fservice, CloseableTabs tab) {
		this.fservice = fservice;
		this.tab = tab;
	}

	@Override
	public void execute() {

		UploadDialog c = new UploadDialog(fservice, tab);
		c.show();

	}

}
