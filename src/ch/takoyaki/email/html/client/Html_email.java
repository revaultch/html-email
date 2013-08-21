package ch.takoyaki.email.html.client;

import ch.takoyaki.email.html.client.service.FileService;
import ch.takoyaki.email.html.client.service.FileServiceImpl;
import ch.takoyaki.email.html.client.ui.ConfirmDelete;
import ch.takoyaki.email.html.client.ui.ConfirmDelete.ConfirmedCallBack;
import ch.takoyaki.email.html.client.ui.generic.ClosableTabLayoutPanel;
import ch.takoyaki.email.html.client.ui.generic.ClosableTabLayoutPanel.AddTabEventHandler;
import ch.takoyaki.email.html.client.ui.generic.ClosableTabLayoutPanel.CloseTabEventHandler;
import ch.takoyaki.email.html.client.ui.generic.ClosableTabLayoutPanel.MarkTabEventHandler;
import ch.takoyaki.email.html.client.ui.generic.ClosableTabLayoutPanel.RenameTabEventHandler;
import ch.takoyaki.email.html.client.ui.generic.CloseableTabs;
import ch.takoyaki.email.html.client.ui.generic.CloseableTabsWrapper;
import ch.takoyaki.email.html.client.ui.generic.TextAreaWithTabkey;
import ch.takoyaki.email.html.client.ui.generic.VSplitPanel;

import com.google.gwt.core.client.EntryPoint;
import com.google.gwt.event.dom.client.KeyUpEvent;
import com.google.gwt.event.dom.client.KeyUpHandler;
import com.google.gwt.user.client.ui.HasText;
import com.google.gwt.user.client.ui.RootPanel;
import com.google.gwt.user.client.ui.TextArea;
import com.google.gwt.user.client.ui.VerticalPanel;
import com.google.gwt.user.client.ui.Widget;

/**
 * Entry point classes define <code>onModuleLoad()</code>.
 */
public class Html_email implements EntryPoint {

	private final FileService fservice = new FileServiceImpl();

	private void addTab(final CloseableTabs tablayout, String title) {
		final TextArea contentContainer = new TextAreaWithTabkey();
		tablayout.add(contentContainer, title);
	}

	private CloseableTabs createWrapper(ClosableTabLayoutPanel tabs,
			final PreviewUpdateTrigger trigger) {
		return new CloseableTabsWrapper(tabs) {
			@Override
			public void add(Widget widget, String title) {
				if (widget instanceof TextArea) {
					final TextArea contentContainer = (TextArea) widget;
					trigger.watchTextArea(contentContainer);
					contentContainer.addStyleName("editor");					
					wrapped.add(contentContainer, title);
					contentContainer.getElement().getParentElement().addClassName("editor_container");
					String retrieved = fservice.retrieve(title);
					if (retrieved != null) {
						contentContainer.setText(retrieved);
					} else {
						fservice.store(title, "");
					}
					contentContainer.addKeyUpHandler(new KeyUpHandler() {
						@Override
						public void onKeyUp(KeyUpEvent event) {
							String name = wrapped.getTabTitle(contentContainer)
									.getText();
							fservice.store(name, contentContainer.getText());
						}
					});
				}
			}
		};
	}

	private AddTabEventHandler createAddTabEventHandler(final CloseableTabs tab) {
		return new AddTabEventHandler() {
			@Override
			public void onAdd(CloseableTabs l) {
				String fname = fservice.getNewName();
				addTab(tab, fname);
			}
		};
	}

	private CloseTabEventHandler createCloseTabEventHandler(
			final RootPanel rootPanel) {
		return new CloseTabEventHandler() {
			@Override
			public void onClose(final CloseableTabs l, final int pos) {
				ConfirmDelete c = new ConfirmDelete(new ConfirmedCallBack() {
					@Override
					public void confirmed() {
						fservice.delete(l.getTabTitle(pos).getText());
						l.remove(pos);
					}
				});
				c.show();
			}

			@Override
			public void onCloseAll(final CloseableTabs l) {
				ConfirmDelete c = new ConfirmDelete(new ConfirmedCallBack() {
					@Override
					public void confirmed() {
						while (l.getCount() > 1) {
							fservice.delete(l.getTabTitle(1).getText());
							l.remove(1);
						}
					}
				});
				c.show();
			}

		};
	}

	private RenameTabEventHandler createRenameTabEventHAndler() {
		return new RenameTabEventHandler() {
			@Override
			public boolean onRename(CloseableTabs l, int pos,
					String previousName, String newName) {
				if (fservice.list().contains(newName)) {
					return false;
				}
				fservice.rename(previousName, newName);
				return true;
			}
		};
	}

	private MarkTabEventHandler createMarkTabEventHAndler(
			final CloseableTabs tab, final PreviewUpdateTrigger trigger) {
		return new MarkTabEventHandler() {
			@Override
			public void onMark(CloseableTabs l, int pos) {
				Widget tabWidget = tab.getTabWidget(pos);
				HasText text = tab.getTabTitle(pos);
				trigger.setPreviewTab(tabWidget, text);
			}
		};
	}

	private void openSavedTabs(CloseableTabs tab) {
		for (String fname : fservice.list()) {
			addTab(tab, fname);
		}
	}

	/**
	 * This is the entry point method.
	 */
	public void onModuleLoad() {
		final RootPanel rootPanel = RootPanel.get("root_container");
		RootPanel ns = RootPanel.get("notsupported");
		if (!fservice.isSupported()) {
			rootPanel.add(ns);
			return;
		}
		ns.setVisible(false);

		// preview
		HtmlPreview preview = new HtmlPreview(fservice);
		final PreviewUpdateTrigger trigger = new PreviewUpdateTrigger(preview);

		// edit tabs
		final ClosableTabLayoutPanel tabs = new ClosableTabLayoutPanel();
		CloseableTabs tabsw = createWrapper(tabs, trigger);
		tabs.setAddTabEventHandler(createAddTabEventHandler(tabsw));
		tabs.setCloseTabEventHandler(createCloseTabEventHandler(rootPanel));
		tabs.setRenameTabEventHandler(createRenameTabEventHAndler());
		tabs.setMarkTabEventHandler(createMarkTabEventHAndler(tabsw, trigger));
		openSavedTabs(tabsw);

		VerticalPanel north = new VerticalPanel();
		north.addStyleName("north");

		// menu
		MenuBarBuilder builder = new MenuBarBuilder(fservice, preview, tabsw);
		builder.addRootPanel(north);
		builder.constructMenu();
		builder.show();

		north.add(tabs);

		// vertical split between preview and edit
		VSplitPanel vsplit = new VSplitPanel();

		// wiring
		vsplit.addNorth(north, 400);
		vsplit.addSouth(preview);
		rootPanel.add(vsplit);

	}

}
