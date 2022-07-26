/*
 * MIT License
 * 
 * Copyright (c) 2022 georges-stephan
 * 
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 * 
 * The above copyright notice and this permission notice shall be included in all
 * copies or substantial portions of the Software.
 * 
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE
 * SOFTWARE.
 * 
 */

package warnings.jw;

import java.io.Serializable;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

/**
 * 
 * @author Georges Stephan
 *
 */
public class DefaultWarning implements Warning, Serializable {
	private int warningID = -1; // undefined
	private long threadID = -1; // undefined
	private String message;
	private StackTraceElement[] stes;
	private String methodName;
	private Object[] unnamedParameters;
	private Map<String, Object> namedParameters = new HashMap<>();

	public DefaultWarning(int warningID, String message, StackTraceElement[] stes, String methodName, long threadID) {
		this.warningID = warningID;
		this.message = message;
		this.stes = stes;
		this.methodName = methodName;
		this.threadID = threadID;
	}

	public DefaultWarning(int warningID, String message, StackTraceElement[] stes, String methodName, long threadID,
			Object[] unnamedParameters,
			Map<String, Object> namedParameters) {
		this.warningID = warningID;
		this.message = message;
		this.stes = stes;
		this.methodName = methodName;
		this.threadID = threadID;
		this.unnamedParameters = unnamedParameters;
		if (namedParameters != null) {
			this.namedParameters = namedParameters;
		}
	}

	public String getMethodName() {
		return this.methodName;
	}

	@Override
	public String getMessage() {
		return message;
	}

	@Override
	public StackTraceElement[] getSource() {
		return stes;
	}

	@Override
	public long getThreadID() {
		return threadID;
	}

	@Override
	public Optional<Integer> getWarningID() {
		if (this.warningID < 0) {
			return Optional.empty();
		}
		return Optional.of(warningID);
	}

	@Override
	public Optional<Map<String, Object>> getNamedParameters() {
		if (this.namedParameters.size() < 1) {
			return Optional.empty();
		}
		return Optional.of(this.namedParameters);
	}

	@Override
	public Optional<Object[]> getUnnamedParameters() {
		return Optional.ofNullable(this.unnamedParameters);
	}

	@Override
	public String toString() {
		StringBuilder warningAsString = new StringBuilder();
		warningAsString.append(String.format("Warning message: %s.\r\n", message));
		warningAsString.append(String.format("ThreadID: %s, Warning ID: %s.", threadID, warningID));
		warningAsString.append("\r\n");
		if (namedParameters != null && namedParameters.size() > 0) {
			warningAsString.append("Named Parameters:\r\n");
			namedParameters.entrySet()
					.stream()
					.forEach(x -> {
						warningAsString.append("\t");
						warningAsString.append(x.getKey());
						warningAsString.append(":");
						warningAsString.append(x.getValue());
						warningAsString.append("\r\n");
					});
		}
		if (unnamedParameters != null && unnamedParameters.length > 0) {
			warningAsString.append("Unnamed parameters:\r\n");
			Arrays.stream(unnamedParameters)
					.filter(x -> x != null)
					.forEach(x -> {
						warningAsString.append("\t");
						warningAsString.append(x.toString());
						warningAsString.append("\r\n");
					});
		}

		if (stes != null && stes.length > 0) {
			warningAsString.append("Stack trace:\r\n");
			Arrays.stream(stes)
					.filter(x -> !x.toString().startsWith("java.base/java.lang.Thread.getStackTrace"))
					.forEach(x -> {
						warningAsString.append("\t");
						warningAsString.append(x.toString());
						warningAsString.append("\r\n");
					});
		}
		return warningAsString.toString();
	}
}
