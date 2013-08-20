package ch.takoyaki.email.html.client.ui.generic;

import com.google.gwt.user.client.DOM;
import com.google.gwt.user.client.ui.ComplexPanel;
import com.google.gwt.user.client.ui.HasText;
import com.google.gwt.user.client.ui.Widget;

public class ListItem extends ComplexPanel implements HasText {
	public ListItem(Widget w) {
		setElement(DOM.createElement("LI"));
		add(w, getElement());
	}

	public String getText() {
		return DOM.getInnerText(getElement());
	}

	public void setText(String text) {
		DOM.setInnerText(getElement(), (text == null) ? "" : text);
	}

}