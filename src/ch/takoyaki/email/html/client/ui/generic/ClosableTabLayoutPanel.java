package ch.takoyaki.email.html.client.ui.generic;

import com.google.gwt.core.client.GWT;
import com.google.gwt.dom.client.Style.Unit;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.event.dom.client.KeyCodes;
import com.google.gwt.event.dom.client.KeyDownEvent;
import com.google.gwt.event.dom.client.KeyDownHandler;
import com.google.gwt.resources.client.ClientBundle;
import com.google.gwt.resources.client.ImageResource;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiFactory;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.user.client.Timer;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.FlowPanel;
import com.google.gwt.user.client.ui.HTML;
import com.google.gwt.user.client.ui.HasText;
import com.google.gwt.user.client.ui.InlineHTML;
import com.google.gwt.user.client.ui.IsWidget;
import com.google.gwt.user.client.ui.TextBox;
import com.google.gwt.user.client.ui.Widget;

public class ClosableTabLayoutPanel extends Composite implements CloseableTabs {

	public static interface Resources extends ClientBundle {
		public static final Resources INSTANCE = GWT.create(Resources.class);

		@Source("left.png")
		ImageResource left();

		@Source("right.png")
		ImageResource right();
	}

	@UiField
	Resources res;

	@UiFactory
	ScrolledTabLayoutPanel makeScrolledTabLayoutPanel() {
		return new ScrolledTabLayoutPanel(1.0, Unit.CM, res.left(), res.right());
	}

	@Override
	public String getStyleName() {
		return "ClosableTabLayoutPanel";
	}

	public static interface AddTabEventHandler {
		void onAdd(CloseableTabs l);
	}

	public static interface CloseTabEventHandler {
		void onClose(CloseableTabs l, int pos);

		void onCloseAll(CloseableTabs l);
	}

	public static interface RenameTabEventHandler {

		boolean onRename(CloseableTabs l, int pos, String previousName,
				String newName);

	}

	public static interface MarkTabEventHandler {
		void onMark(CloseableTabs l, int pos);
	}

	private AddTabEventHandler addTabEventHandler = null;

	private CloseTabEventHandler closeTabEventHandler = null;

	private RenameTabEventHandler renameTabEventHandler = null;

	private MarkTabEventHandler markTabEventHandler = null;

	private static ClosableTabLayoutPanelBinder uiBinder = GWT
			.create(ClosableTabLayoutPanelBinder.class);

	interface ClosableTabLayoutPanelBinder extends
			UiBinder<Widget, ClosableTabLayoutPanel> {
	}

	private ScrolledTabLayoutPanel getTab() {
		return (ScrolledTabLayoutPanel) getWidget();
	}

	public void setAddTabEventHandler(AddTabEventHandler addTabEventHandler) {
		this.addTabEventHandler = addTabEventHandler;
	}

	public void setCloseTabEventHandler(
			CloseTabEventHandler closeTabEventHandler) {
		this.closeTabEventHandler = closeTabEventHandler;
	}

	public void setRenameTabEventHandler(
			RenameTabEventHandler renameTabEventHandler) {
		this.renameTabEventHandler = renameTabEventHandler;
	}

	public void setMarkTabEventHandler(MarkTabEventHandler markTabEventHandler) {
		this.markTabEventHandler = markTabEventHandler;
	}

	public ClosableTabLayoutPanel() {
		super();
		initWidget(uiBinder.createAndBindUi(this));

		Widget empty = new HTML("");
		final FlowPanel add = new FlowPanel();
		add.add(new InlineHTML("+"));
		add.setStyleName("closable_tab");
		add.addStyleName(getStyleName());
		add.addStyleName("add");

		getTab().add(empty, add);

		add.addDomHandler(new ClickHandler() {
			@Override
			public void onClick(ClickEvent event) {
				if (null != addTabEventHandler) {
					addTabEventHandler.onAdd(ClosableTabLayoutPanel.this);
				}
				event.stopPropagation();
			}
		}, ClickEvent.getType());
	}

	private Widget newCloseButton(final IsWidget self) {
		Widget close = new InlineHTML("&nbsp;[x]");
		close.addStyleName(getStyleName());
		close.addStyleName("close");
		close.addDomHandler(new ClickHandler() {

			@Override
			public void onClick(ClickEvent event) {
				if (null != closeTabEventHandler) {
					closeTabEventHandler.onClose(ClosableTabLayoutPanel.this,
							getTab().getWidgetIndex(self));
				}
			}
		}, ClickEvent.getType());
		return close;
	}

	private void renameAction(final Widget content, final FlowPanel hpanel,
			final InlineHTML titlew, final Widget closeButton) {
		hpanel.clear();
		final TextBox tb = new TextBox();
		tb.addStyleName("tabrename");
		final String originalText = titlew.getText();
		tb.setText(originalText);

		tb.addKeyDownHandler(new KeyDownHandler() {

			@Override
			public void onKeyDown(KeyDownEvent event) {

				boolean enterPressed = KeyCodes.KEY_ENTER == event
						.getNativeEvent().getKeyCode();
				boolean escapePressed = KeyCodes.KEY_ESCAPE == event
						.getNativeEvent().getKeyCode();

				if (enterPressed) {
					if (renameTabEventHandler != null) {
						if (renameTabEventHandler.onRename(
								ClosableTabLayoutPanel.this, getTab()
										.getWidgetIndex(content), originalText,
								tb.getText())) {
							titlew.setText(tb.getText());
						} else {
							titlew.setHTML(originalText);
						}
					} else {
						titlew.setText(tb.getText());
					}
				}
				if (escapePressed) {
					titlew.setHTML(originalText);
				}

				if (enterPressed || escapePressed) {
					hpanel.clear();
					hpanel.add(titlew);
					hpanel.add(closeButton);
				}
			}
		});
		hpanel.add(tb);
		tb.selectAll();
		titlew.setHTML("");
	}

	public void closeSelected() {
		int selected = getTab().getSelectedIndex();
		if (selected != 0 && null != closeTabEventHandler) {
			closeTabEventHandler.onClose(ClosableTabLayoutPanel.this, selected);
		}
	}

	public void closeAll() {
		if (null != closeTabEventHandler) {
			closeTabEventHandler.onCloseAll(ClosableTabLayoutPanel.this);
		}
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * ch.takoyaki.email.html.client.AddableItems#add(com.google.gwt.user.client
	 * .ui.Widget, java.lang.String)
	 */
	@Override
	public void add(final Widget content, String title) {
		final FlowPanel hpanel = new FlowPanel();
		final InlineHTML titlew = new InlineHTML(title);

		final Widget closeButton = newCloseButton(content);

		titlew.addClickHandler(new ClickHandler() {
			private long last = System.currentTimeMillis();
			private Timer timer = new Timer() {

				@Override
				public void run() {

					if (isSelected()) {
						renameAction(content, hpanel, titlew, closeButton);
					}

				}
			};

			private boolean isSelected() {
				int selected = getTab().getSelectedIndex();
				return getTab().getWidgetIndex(content) == selected;
			}

			@Override
			public void onClick(ClickEvent event) {

				long now = System.currentTimeMillis();
				if ((now - last) < 250) { // double
					timer.cancel();
					event.stopPropagation();
					markTabEventHandler.onMark(ClosableTabLayoutPanel.this,
							getTab().getWidgetIndex(content));
				} else {
					if (isSelected()) {
						timer.schedule(250);
					}
				}
				last = now;

			}
		});

		hpanel.setStyleName("closable_tab");
		hpanel.add(titlew);
		hpanel.add(closeButton);

		getTab().add(content, hpanel);

		getTab().selectTab(getTab().getWidgetCount() - 1);
	}

	public int getCount() {
		return getTab().getWidgetCount();
	}

	public Widget getTabWidget(int pos) {

		return getTab().getTabWidget(pos);
	}

	public Widget getTabWidget(Widget child) {
		return getTab().getTabWidget(child);
	}

	public HasText getTabTitle(Widget child) {
		FlowPanel panel = (FlowPanel) getTab().getTabWidget(child);
		return (InlineHTML) panel.getWidget(0);
	}

	public void remove(int index) {
		getTab().remove(index);
	}

	public HasText getTabTitle(int pos) {
		return getTabTitle(getTab().getWidget(pos));
	}

	public Widget getWidget(int pos) {
		return getTab().getWidget(pos);
	}

}
