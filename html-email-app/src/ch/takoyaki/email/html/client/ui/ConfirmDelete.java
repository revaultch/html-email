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
package ch.takoyaki.email.html.client.ui;

import com.google.gwt.core.client.GWT;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.uibinder.client.UiHandler;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.DialogBox;
import com.google.gwt.user.client.ui.HTML;
import com.google.gwt.user.client.ui.RootPanel;
import com.google.gwt.user.client.ui.Widget;

public class ConfirmDelete extends Composite {

	public static interface ConfirmedCallBack {
		void confirmed();
	}

	private static ConfirmDeleteUiBinder uiBinder = GWT
			.create(ConfirmDeleteUiBinder.class);

	interface ConfirmDeleteUiBinder extends UiBinder<Widget, ConfirmDelete> {
	}

	private final ConfirmedCallBack confirmDelete;

	public ConfirmDelete(ConfirmedCallBack confirmDelete) {
		initWidget(uiBinder.createAndBindUi(this));
		this.confirmDelete = confirmDelete;
		dialog.center();
	}

	@UiHandler("yes")
	public void yes(ClickEvent e) {
		confirmDelete.confirmed();
		hide();
	}

	@UiHandler("cancel")
	public void cancel(ClickEvent e) {
		hide();
	}

	public void hide() {
		RootPanel.get().remove(div);
		dialog.hide();
	}
	public void show() {
		RootPanel.get().add(div);
		dialog.show();
	}

	@UiField
	DialogBox dialog;

	private HTML div = new HTML("<div id=\"dialog_background\"></div>");

}
