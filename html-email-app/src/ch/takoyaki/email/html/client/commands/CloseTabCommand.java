/*******************************************************************************
 * Copyright (c) 2013 takoyaki.ch.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the GNU Public License v3.0
 * which accompanies this distribution, and is available at
 * http://www.gnu.org/licenses/gpl.html
 * 
 * Contributors:
 *     takoyaki.ch - Initial version
 ******************************************************************************/
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
