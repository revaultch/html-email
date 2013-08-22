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
