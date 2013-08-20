package ch.takoyaki.email.html.client;

import com.google.gwt.event.dom.client.KeyCodes;
import com.google.gwt.event.dom.client.KeyUpEvent;
import com.google.gwt.event.dom.client.KeyUpHandler;
import com.google.gwt.event.shared.HandlerRegistration;
import com.google.gwt.user.client.Timer;
import com.google.gwt.user.client.ui.HasText;
import com.google.gwt.user.client.ui.TextArea;
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
	private final KeyUpHandler keyupHandler = new KeyUpHandler() {

		@Override
		public void onKeyUp(KeyUpEvent event) {
			switch (event.getNativeKeyCode()) {
			case KeyCodes.KEY_ALT:
			case KeyCodes.KEY_CTRL:
			case KeyCodes.KEY_DOWN:
			case KeyCodes.KEY_END:
			case KeyCodes.KEY_ESCAPE:
			case KeyCodes.KEY_HOME:
			case KeyCodes.KEY_LEFT:
			case KeyCodes.KEY_PAGEDOWN:
			case KeyCodes.KEY_PAGEUP:
			case KeyCodes.KEY_RIGHT:
			case KeyCodes.KEY_SHIFT:
			case KeyCodes.KEY_TAB:
			case KeyCodes.KEY_UP:
				break;
			default:
				t.cancel();
				t.schedule(2000);
			}

		}
	};

	public void watchTextArea(TextArea ta) {
		ta.addKeyUpHandler(keyupHandler);
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
