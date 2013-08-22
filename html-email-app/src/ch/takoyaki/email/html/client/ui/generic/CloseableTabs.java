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

import com.google.gwt.user.client.ui.HasText;
import com.google.gwt.user.client.ui.IsWidget;
import com.google.gwt.user.client.ui.Widget;

public interface CloseableTabs {

	void add(IsWidget content, String title);

	void closeSelected();

	void closeAll();

	HasText getTabTitle(IsWidget ta);

	HasText getTabTitle(int pos);

	void remove(int pos);

	int getCount();

	Widget getTabWidget(int pos);

	Widget getWidget(int pos);
}
