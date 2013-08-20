package ch.takoyaki.email.html.client.ui.generic;

import com.google.gwt.dom.client.Element;
import com.google.gwt.event.dom.client.KeyDownEvent;
import com.google.gwt.event.dom.client.KeyDownHandler;
import com.google.gwt.user.client.ui.TextArea;

public class TextAreaWithTabkey extends TextArea {
	public static final KeyDownHandler DEFAULT_TEXTAREA_TAB_HANDLER = new KeyDownHandler() {
		@Override
		public final void onKeyDown(KeyDownEvent event) {
			if (event.getNativeKeyCode() == 9) {
				event.preventDefault();
				event.stopPropagation();
				final TextArea ta = (TextArea) event.getSource();
				final int index = ta.getCursorPos();
				final String text = ta.getText();
				ta.setText(text.substring(0, index) + "\t"
						+ text.substring(index));
				ta.setCursorPos(index + 1);
			}
		}
	};

	public TextAreaWithTabkey() {
		super();
		init();
	}

	public TextAreaWithTabkey(Element e) {
		super(e);
		init();
	}

	private void init() {
		this.addKeyDownHandler(DEFAULT_TEXTAREA_TAB_HANDLER);
	}
}
