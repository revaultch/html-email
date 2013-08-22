/*******************************************************************************
 * Copyright (c) 2013 takoyaki.ch.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the GNU Public License v3.0
 * which accompanies this distribution, and is available at
 * http://www.gnu.org/licenses/gpl.html
 * 
 * Contributors:
 *     takoyaki.ch - Initial version
 ******************************************************************************/
package ch.takoyaki.email.html.client.utils;

public final class Encoding {
	private Encoding() {
	}

	public static native String base64encode(String data) /*-{
		return $wnd.base64_encode(data);
	}-*/;

	public static native String utf8encode(String content) /*-{
		return $wnd.utf8_encode(content);
	}-*/;

}
