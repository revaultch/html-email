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

import ch.takoyaki.email.html.client.ContentRenderer;
import ch.takoyaki.email.html.client.service.FileService;
import ch.takoyaki.email.html.client.ui.generic.FileDownload;
import ch.takoyaki.email.html.client.utils.Postprocessing;

import com.google.gwt.core.client.Scheduler.ScheduledCommand;

public class DownloadEmlCommand implements ScheduledCommand {

	private final ContentRenderer contentRenderer;
	private final FileService fservice;

	public DownloadEmlCommand(ContentRenderer contentRenderer,
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
		content = Postprocessing.beforeSaveEml(content, fservice);

		content = wrapMime("Subject", content);
		FileDownload.create(contentRenderer.getRenderedFileName() + ".eml",
				content, "message/rfc822", "UTF-8").trigger();
	}

	private native String wrapMime(String subject, String content) /*-{
		return $wnd.wrapMime(subject, content);
	}-*/;

}
