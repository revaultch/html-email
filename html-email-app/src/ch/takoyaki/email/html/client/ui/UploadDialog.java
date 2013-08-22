package ch.takoyaki.email.html.client.ui;

import ch.takoyaki.email.html.client.logging.Log;
import ch.takoyaki.email.html.client.service.FileService;
import ch.takoyaki.email.html.client.service.JsFile;
import ch.takoyaki.email.html.client.service.JsFileReader;
import ch.takoyaki.email.html.client.service.JsFileReader.LoadCompleteHandler;
import ch.takoyaki.email.html.client.service.JsFiles;
import ch.takoyaki.email.html.client.ui.generic.CloseableTabs;
import ch.takoyaki.email.html.client.ui.generic.UnOrderedList;
import ch.takoyaki.email.html.client.utils.StringFormatter;

import com.google.gwt.core.client.GWT;
import com.google.gwt.core.client.JavaScriptObject;
import com.google.gwt.event.dom.client.ChangeEvent;
import com.google.gwt.event.dom.client.ChangeHandler;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.uibinder.client.UiHandler;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.DialogBox;
import com.google.gwt.user.client.ui.FileUpload;
import com.google.gwt.user.client.ui.HTML;
import com.google.gwt.user.client.ui.InlineHTML;
import com.google.gwt.user.client.ui.RootPanel;
import com.google.gwt.user.client.ui.Widget;

public class UploadDialog extends Composite {

	private static String[] supportedTypes = new String[] { "text/html",
			"plain/text", "text/xml", "text/xsl" };

	private static UploadDialogUiBinder uiBinder = GWT
			.create(UploadDialogUiBinder.class);

	interface UploadDialogUiBinder extends UiBinder<Widget, UploadDialog> {
	}

	private final FileService fservice;
	private final CloseableTabs tab;

	private JsFiles files;

	@UiField
	FileUpload fileUpload;

	@UiField
	public UnOrderedList fileInfo;

	@UiField
	Button upload;

	public void hide() {
		RootPanel.get().remove(div);
		dialog.hide();
	}

	public void show() {
		RootPanel.get().add(div);
		dialog.show();
	}

	@UiField
	DialogBox dialog;

	private HTML div = new HTML("<div id=\"dialog_background\"></div>");

	public UploadDialog(FileService fservice, CloseableTabs tab) {
		this.fservice = fservice;
		this.tab = tab;
		initWidget(uiBinder.createAndBindUi(this));
		dialog.setText("Upload");
		dialog.center();
		upload.setEnabled(false);

		fileUpload.getElement().setAttribute("multiple", "true");
		fileUpload.addChangeHandler(new ChangeHandler() {

			@Override
			public void onChange(ChangeEvent event) {
				files = listFiles(event.getNativeEvent().getEventTarget()
						.cast());
				fileInfo.clear();
				boolean submitActive = true;
				for (int i = 0; i < files.length(); i++) {
					submitActive = submitActive
							&& !wouldOverwrite(files.get(i).getName())
							&& isSupportedType(files.get(i));
					fileInfo.add(createFileDesc(files.get(i)));
				}
				upload.setEnabled(submitActive);
			}
		});
	}

	@UiHandler("cancel")
	void cancel(ClickEvent e) {

		hide();
	}

	@UiHandler("upload")
	void upload(ClickEvent e) {
		for (int i = 0; i < files.length(); i++) {
			JsFileReader r = new JsFileReader(files.get(i));
			r.readAsText(new LoadCompleteHandler() {
				@Override
				public void onError(JsFile file) {
					Log.log("error occured loading " + file.getName());
				}

				@Override
				public void onComplete(JsFile file, String content) {
					fservice.store(file.getName(), content);
					tab.add(null, file.getName());
				}
			});
		}
		hide();
	}

	private native JsFiles listFiles(JavaScriptObject target) /*-{
		return target.files; // FileList object
	}-*/;

	private Widget createFileDesc(JsFile jsFile) {
		String fname = jsFile.getName();
		InlineHTML span = new InlineHTML(StringFormatter.format(
				"<strong>{0}</strong>({1}) - {2} bytes, last modified: {3}",
				fname, jsFile.getType(), jsFile.getByteSize(),
				jsFile.getLastModied()));
		if (wouldOverwrite(fname)) {
			span.addStyleName("overwrites");
		}
		if (!isSupportedType(jsFile)) {
			span.addStyleName("unsupported");
		}
		return span;
	}

	private boolean isSupportedType(JsFile file) {
		return file.getType().startsWith("text/")
				|| file.getType().equals("application/javascript")
				|| file.getType().equals("application/x-javascript");
	}

	private boolean wouldOverwrite(String fileName) {
		return fservice.list().contains(fileName);
	}

}
