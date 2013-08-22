package ch.takoyaki.email.html.client.ui.generic;

import com.google.gwt.user.client.ui.HasText;
import com.google.gwt.user.client.ui.IsWidget;
import com.google.gwt.user.client.ui.Widget;

public class CloseableTabsWrapper implements CloseableTabs {

	protected final CloseableTabs wrapped;

	public CloseableTabsWrapper(CloseableTabs w) {
		this.wrapped = w;
	}

	@Override
	public void add(IsWidget content, String title) {
		wrapped.add(content, title);
	}

	@Override
	public void closeSelected() {
		wrapped.closeSelected();
	}

	@Override
	public void closeAll() {
		wrapped.closeAll();
	}

	@Override
	public HasText getTabTitle(IsWidget ta) {
		return wrapped.getTabTitle(ta);
	}

	@Override
	public HasText getTabTitle(int pos) {
		return wrapped.getTabTitle(pos);
	}

	@Override
	public void remove(int pos) {
		wrapped.remove(pos);
	}

	@Override
	public int getCount() {
		return wrapped.getCount();
	}

	@Override
	public Widget getTabWidget(int pos) {
		return wrapped.getTabWidget(pos);
	}

	@Override
	public Widget getWidget(int pos) {
		return wrapped.getWidget(pos);
	}

}
