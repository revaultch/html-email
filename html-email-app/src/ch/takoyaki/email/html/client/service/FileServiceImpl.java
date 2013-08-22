package ch.takoyaki.email.html.client.service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import ch.takoyaki.email.html.client.logging.Log;
import ch.takoyaki.email.html.client.service.JsZip.JsZipWrapper;

import com.google.gwt.i18n.client.DateTimeFormat;
import com.google.gwt.json.client.JSONArray;
import com.google.gwt.json.client.JSONException;
import com.google.gwt.json.client.JSONParser;
import com.google.gwt.json.client.JSONString;
import com.google.gwt.storage.client.Storage;

public class FileServiceImpl implements FileService {

	private static final String FILE_LIST = "FILE_LIST";
	private static final String FILE_PREFIX = "__";
	private List<String> fListCache = fileList();

	private Storage s() {
		return Storage.getLocalStorageIfSupported();
	}

	private List<String> fileList() {

		List<String> list = new ArrayList<String>();
		try {
			if (s().getItem(FILE_LIST) != null) {
				JSONArray a = (JSONArray) JSONParser.parseStrict(s().getItem(
						FILE_LIST));
				for (int i = 0; i < a.size(); i++) {
					list.add(((JSONString) a.get(i)).stringValue());
				}
			}
		} catch (JSONException e) {
			Log.log("file list corrupted");
		}

		return list;
	}

	private void updateFileList(List<String> fListCache2) {
		JSONArray a = new JSONArray();
		for (int i = 0; i < fListCache2.size(); i++) {
			a.set(i, new JSONString(fListCache2.get(i)));
		}
		s().setItem(FILE_LIST, a.toString());
		fListCache = fileList();
	}

	@Override
	public String getNewName() {
		String nameBase = "Filename";
		String candidateName = nameBase;
		int i = 1;
		while (fListCache.contains(candidateName)) {
			candidateName = nameBase + i++;
		}
		return candidateName;
	}

	@Override
	public boolean isSupported() {
		return Storage.isSupported();

	}

	private String fileKey(String name) {
		return FILE_PREFIX + name;
	}

	@Override
	public void store(String fname, String text) {

		if (!fListCache.contains(fname)) {
			fListCache.add(fname);
			updateFileList(fListCache);
		}
		s().setItem(fileKey(fname), text);
	}

	@Override
	public String retrieve(String fname) {
		String content = s().getItem(fileKey(fname));
		if (content != null) {
			if (!fListCache.contains(fname)) {
				fListCache.add(fname);
				updateFileList(fListCache);
			}
		}
		return content;
	}

	@Override
	public List<String> list() {
		return new ArrayList<String>(fListCache);
	}

	@Override
	public void rename(String previousName, String newName) {
		String pkey = fileKey(previousName);
		String nkey = fileKey(newName);
		String item = s().getItem(pkey);

		s().setItem(nkey, item);
		int idx = fListCache.indexOf(previousName);
		fListCache.remove(idx);
		fListCache.add(idx, newName);
		updateFileList(fListCache);
		s().removeItem(pkey);
	}

	@Override
	public void delete(String fname) {
		s().removeItem(fileKey(fname));
		fListCache.remove(fname);
		updateFileList(fListCache);
	}

	public String retrieveAllAsZipBase64() {
		JsZip zip = new JsZip();
		String folderName = "source_"+DateTimeFormat.getFormat("yyyyMMddHHmmss").format(new Date());
		JsZipWrapper sourceFolder = zip.addFolder(folderName);
		for (String f : list()) {
			sourceFolder.addFile(f, retrieve(f));
		}
		return zip.generateBase64();
	};

}
