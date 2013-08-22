package ch.takoyaki.email.html.client.ui.generic;

import com.google.gwt.user.client.ui.HasText;
import com.google.gwt.user.client.ui.IsWidget;

public interface TextEditor extends IsWidget, HasText {

	public interface ContentChangedHandler {
		void onChange(TextEditor editor);
	}

	void addChangeHandler(ContentChangedHandler handler);

	void addStyleName(String string);

	void setFileName(String title);

}
