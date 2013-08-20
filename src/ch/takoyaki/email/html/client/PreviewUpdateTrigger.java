package ch.takoyaki.email.html.client;

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
			t.cancel();
			t.schedule(2000);
		}
	};

	public void setWatched(Widget tabWidget, HasText title, TextArea ta) {
		if (watchedTab!=null && watchedTab == tabWidget) {
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
		registration = ta.addKeyUpHandler(keyupHandler);
		t.schedule(2000);
	}

}
