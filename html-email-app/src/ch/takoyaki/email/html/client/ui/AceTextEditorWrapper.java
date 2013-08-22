package ch.takoyaki.email.html.client.ui;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import ch.takoyaki.email.html.client.ui.generic.TextEditor;

import com.google.gwt.core.client.JavaScriptObject;
import com.google.gwt.event.logical.shared.AttachEvent;
import com.google.gwt.event.shared.HandlerRegistration;
import com.google.gwt.user.client.ui.Composite;

import edu.ycp.cs.dh.acegwt.client.ace.AceEditor;
import edu.ycp.cs.dh.acegwt.client.ace.AceEditorCallback;
import edu.ycp.cs.dh.acegwt.client.ace.AceEditorMode;
import edu.ycp.cs.dh.acegwt.client.ace.AceEditorTheme;

public class AceTextEditorWrapper extends Composite implements TextEditor {

	private final List<Ready> onReadyCallbacks = new LinkedList<AceTextEditorWrapper.Ready>();
	private final HandlerRegistration attachHandlerReg;

	public AceTextEditorWrapper(AceEditor ace) {
		initWidget(ace);
		attachHandlerReg = ace
				.addAttachHandler(new com.google.gwt.event.logical.shared.AttachEvent.Handler() {
					public void onAttachOrDetach(AttachEvent event) {
						attachHandlerReg.removeHandler();
						if (event.isAttached()) {
							AceTextEditorWrapper.this.getAce().startEditor();
							AceTextEditorWrapper.this.getAce().setTheme(
									AceEditorTheme.ECLIPSE);

							for (Ready r : onReadyCallbacks) {
								r.ready(AceTextEditorWrapper.this);
							}
							onReadyCallbacks.clear();
						}
					}
				});
	}

	private static interface Ready {
		void ready(TextEditor t);
	}

	private AceEditor getAce() {
		return (AceEditor) getWidget();
	}

	private void onReady(Ready cb) {
		if (isAttached()) {
			cb.ready(this);
		} else {
			onReadyCallbacks.add(cb);
		}
	}

	public AceTextEditorWrapper() {
		this(new AceEditor());
	}

	@Override
	public String getText() {
		return getAce().getText();
	}

	@Override
	public void setText(final String text) {
		onReady(new Ready() {
			@Override
			public void ready(TextEditor t) {
				((AceTextEditorWrapper) t).getAce().setText(text);
			}

		});
	}

	@Override
	public void setFileName(String fname) {
		if (fname == null) {
			return;
		}
		String extension = fname.substring(fname.lastIndexOf(".") + 1);
		final AceEditorMode mode = getAceEditorModeForExtension(extension);
		onReady(new Ready() {
			@Override
			public void ready(TextEditor t) {
				((AceTextEditorWrapper) t).getAce().setMode(mode);
			}
		});
	}

	private static final Map<String, AceEditorMode> MODE_MAPPING;
	static {
		MODE_MAPPING = new HashMap<String, AceEditorMode>();
		MODE_MAPPING.put("js", AceEditorMode.JAVASCRIPT);
		MODE_MAPPING.put("htm", AceEditorMode.HTML);
		MODE_MAPPING.put("html", AceEditorMode.HTML);
		MODE_MAPPING.put("xsl", AceEditorMode.XML);
		MODE_MAPPING.put("xslt", AceEditorMode.XML);
		MODE_MAPPING.put("xml", AceEditorMode.XML);
		MODE_MAPPING.put("java", AceEditorMode.JAVA);
		MODE_MAPPING.put("txt", AceEditorMode.TEXT);
	}

	private AceEditorMode getAceEditorModeForExtension(String extension) {

		AceEditorMode mode = MODE_MAPPING.get(extension);
		if (mode == null) {
			return AceEditorMode.TEXT;
		}
		return mode;
	}

	@Override
	public void addChangeHandler(final ContentChangedHandler handler) {
		onReady(new Ready() {
			@Override
			public void ready(TextEditor t) {
				((AceTextEditorWrapper) t).getAce().addOnChangeHandler(
						new AceEditorCallback() {
							@Override
							public void invokeAceCallback(JavaScriptObject obj) {
								handler.onChange(AceTextEditorWrapper.this);
							}
						});
			}

		});
	}

}
