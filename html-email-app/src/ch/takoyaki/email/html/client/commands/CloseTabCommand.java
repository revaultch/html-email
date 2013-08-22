package ch.takoyaki.email.html.client.commands;

import ch.takoyaki.email.html.client.ui.generic.CloseableTabs;

import com.google.gwt.core.client.Scheduler.ScheduledCommand;

public class CloseTabCommand implements ScheduledCommand {

	private final CloseableTabs tab;

	public CloseTabCommand(CloseableTabs tab) {
		this.tab = tab;
	}

	@Override
	public void execute() {
		tab.closeSelected();
	}

}
