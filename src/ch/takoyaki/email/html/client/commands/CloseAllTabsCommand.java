package ch.takoyaki.email.html.client.commands;

import ch.takoyaki.email.html.client.ui.generic.CloseableTabs;

import com.google.gwt.core.client.Scheduler.ScheduledCommand;

public class CloseAllTabsCommand implements ScheduledCommand {

	private final CloseableTabs tab;

	public CloseAllTabsCommand(CloseableTabs tab) {
		this.tab = tab;
	}

	@Override
	public void execute() {
		tab.closeAll();
	}

}
