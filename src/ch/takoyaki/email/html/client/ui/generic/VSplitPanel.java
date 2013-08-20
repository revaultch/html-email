package ch.takoyaki.email.html.client.ui.generic;

import com.google.gwt.core.client.GWT;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.SplitLayoutPanel;
import com.google.gwt.user.client.ui.Widget;

public class VSplitPanel extends Composite {

	private static VSplitPanelBinder uiBinder = GWT
			.create(VSplitPanelBinder.class);

	interface VSplitPanelBinder extends UiBinder<Widget, VSplitPanel> {
	}

	public VSplitPanel() {
		initWidget(uiBinder.createAndBindUi(this));
	}

	public void addNorth(Widget w, double size) {
		((SplitLayoutPanel) getWidget()).addNorth(w, size);
	}

	public void addSouth(Widget w) {
		((SplitLayoutPanel) getWidget()).add(w);
	}
}
