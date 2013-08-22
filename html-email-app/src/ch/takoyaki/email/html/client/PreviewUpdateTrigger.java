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
package ch.takoyaki.email.html.client;

import ch.takoyaki.email.html.client.ui.generic.TextEditor;
import ch.takoyaki.email.html.client.ui.generic.TextEditor.ContentChangedHandler;

import com.google.gwt.event.shared.HandlerRegistration;
import com.google.gwt.user.client.Timer;
import com.google.gwt.user.client.ui.HasText;
import com.google.gwt.user.client.ui.Widget;

public class PreviewUpdateTrigger {

	private final HtmlPreview previewFrame;

	private Widget watchedTab = null;
	private HandlerRegistration registration = null;

	private HasText previewedTabName;

	public PreviewUpdateTrigger(HtmlPreview previewFrame) {
		this.previewFrame = previewFrame;

	}

	private final Timer t = new Timer() {
		@Override
		public void run() {
			if (previewedTabName != null) {
				previewFrame.preview(previewedTabName.getText());
			}
		}
	};
	private final ContentChangedHandler contentChangeHandler = new ContentChangedHandler() {
		@Override
		public void onChange(TextEditor te) {
			t.cancel();
			t.schedule(2000);
		}
	};

	public void watchTextArea(TextEditor ta) {
		ta.addChangeHandler(contentChangeHandler);
	}

	public void setPreviewTab(Widget tabWidget, HasText title) {
		if (watchedTab != null && watchedTab == tabWidget) {
			return;
		}
		tabWidget.addStyleName("previewed");

		if (watchedTab != null) {
			watchedTab.removeStyleName("previewed");
		}
		watchedTab = tabWidget;

		if (registration != null) {
			registration.removeHandler();
		}
		previewedTabName = title;
		t.schedule(2000);
	}

}
