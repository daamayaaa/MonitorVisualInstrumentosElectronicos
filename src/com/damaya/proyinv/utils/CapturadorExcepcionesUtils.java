package com.damaya.proyinv.utils;

import java.io.PrintWriter;
import java.io.Serializable;
import java.io.StringWriter;
import java.io.Writer;

public final class CapturadorExcepcionesUtils implements Serializable {
	private static final long serialVersionUID = 1L;
	
	public String getStackTrace(Throwable aThrowable) {
		final Writer result = new StringWriter();
		final PrintWriter printWriter = new PrintWriter(result);
		aThrowable.printStackTrace(printWriter);
		return result.toString();
	}
	
	public String getCustomStackTrace(Throwable aThrowable) {
		final StringBuilder result = new StringBuilder("ERROR: ");
		result.append(aThrowable.toString());
		final String NEW_LINE = System.getProperty("line.separator");
		result.append(NEW_LINE);
		for (StackTraceElement element : aThrowable.getStackTrace()) {
			result.append(element);
			result.append(NEW_LINE);
		}
		return result.toString();
	}
}