package ch.takoyaki.email.html.client.service;

import java.util.List;

public interface FileService {

	boolean isSupported();

	String getNewName();

	void store(String fname, String text);

	String retrieve(String fname);

	List<String> list();

	void rename(String previousName, String newName);

	void delete(String fname);

	String retrieveAllAsZipBase64();

}
