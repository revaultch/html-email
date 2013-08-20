package ch.takoyaki.email.html.client.ui.generic;

import com.google.gwt.user.client.ui.HasText;
import com.google.gwt.user.client.ui.Widget;

public interface CloseableTabs {

	void add(Widget content, String title);

	void closeSelected();

	void closeAll();

	HasText getTabTitle(Widget ta);

	HasText getTabTitle(int pos);

	void remove(int pos);

	int getCount();

	Widget getTabWidget(int pos);

	Widget getWidget(int pos);
}
