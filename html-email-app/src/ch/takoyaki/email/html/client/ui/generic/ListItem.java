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
