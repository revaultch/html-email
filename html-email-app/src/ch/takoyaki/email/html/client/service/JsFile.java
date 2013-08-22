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
package ch.takoyaki.email.html.client.service;

import com.google.gwt.core.client.JavaScriptObject;

public  final class JsFile extends JavaScriptObject {
	protected JsFile() {
	}

	public native String getName() /*-{
		return this.name;
	}-*/;

	public native String getType() /*-{
		return this.type || 'n/a';
	}-*/;

	public native int getByteSize() /*-{
		return this.size;
	}-*/;

	public native String getLastModied() /*-{
		return this.lastModifiedDate ? this.lastModifiedDate
				.toLocaleDateString() : 'n/a';
	}-*/;
}

