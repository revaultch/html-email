package ch.takoyaki.email.html.client;

import ch.takoyaki.email.html.client.commands.CloseAllTabsCommand;
import ch.takoyaki.email.html.client.commands.CloseTabCommand;
import ch.takoyaki.email.html.client.commands.DownloadAsZipCommand;
import ch.takoyaki.email.html.client.commands.DownloadEmlCommand;
import ch.takoyaki.email.html.client.commands.DownloadHtmlCommand;
import ch.takoyaki.email.html.client.commands.OpenFromFileSystemCommand;
import ch.takoyaki.email.html.client.service.FileService;
import ch.takoyaki.email.html.client.ui.generic.CloseableTabs;

import com.google.gwt.user.client.ui.MenuBar;
import com.google.gwt.user.client.ui.Panel;

public class MenuBarBuilder {

	private final ContentRenderer contentRenderer;
	private final FileService fservice;
	private final CloseableTabs tab;

	private MenuBar menu;
	private Panel rootPanel;

	public MenuBarBuilder(FileService fservice,
			ContentRenderer contentRenderer, CloseableTabs tab) {
		this.fservice = fservice;
		this.contentRenderer = contentRenderer;
		this.tab = tab;
	}

	public void constructMenu() {

		MenuBar fileMenu = new MenuBar(true);
		fileMenu.addSeparator();
		fileMenu.addItem("Close", new CloseTabCommand(tab));
		fileMenu.addItem("Close all", new CloseAllTabsCommand(tab));
		fileMenu.addSeparator();
		fileMenu.addItem("Open from file system",
				new OpenFromFileSystemCommand(fservice, tab));
		fileMenu.addSeparator();
		fileMenu.addItem("Save as zip", new DownloadAsZipCommand(fservice));

		MenuBar exportMenu = new MenuBar(true);
		exportMenu.addItem("Single HTML", new DownloadHtmlCommand(
				contentRenderer));
		exportMenu.addItem("EML File", new DownloadEmlCommand(contentRenderer));

		menu = new MenuBar();
		menu.addSeparator();
		menu.addItem("File", fileMenu);
		menu.addSeparator();
		menu.addItem("Export", exportMenu);
		menu.addSeparator();

	}

	public void addRootPanel(Panel rootPanel) {
		this.rootPanel = rootPanel;
	}

	public void show() {
		rootPanel.add(menu);
	}

}
