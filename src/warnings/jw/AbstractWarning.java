/*
 * MIT License
 * 
 * Copyright (c) 2014 georges-stephan
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

/**
 * 
 * @author Georges Stephan
 *
 */
public abstract class AbstractWarning implements Warning {
	private int warningID;
	private long threadID;
	private String message;
	private StackTraceElement ste;
	
	@Override
	public String getMessage() {
		return message;
	}

	@Override
	public StackTraceElement getSource() {
		return ste;
	}

	@Override
	public void setSource(StackTraceElement ste) {
		this.ste=ste;
	}

	@Override
	public long getThreadID() {
		return threadID;
	}

	@Override
	public void setThreadID(long threadID) {
		this.threadID = threadID;
	}

	@Override
	public int getWarningID() {
		return warningID;
	}
	
	@Override
	public void setWarningID(int warningID) {
		this.warningID=warningID;
	}

	@Override
	public String toString() {
		return "AbstractWarnings [warningID=" + warningID + ", threadID="
				+ threadID + ", message=" + getMessage() + ", ste=" + ste + "]";
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((message == null) ? 0 : message.hashCode());
		result = prime * result + ((ste == null) ? 0 : ste.hashCode());
		result = prime * result + (int) (threadID ^ (threadID >>> 32));
		result = prime * result + warningID;
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		AbstractWarning other = (AbstractWarning) obj;
		if (message == null) {
			if (other.message != null)
				return false;
		} else if (!message.equals(other.message))
			return false;
		if (ste == null) {
			if (other.ste != null)
				return false;
		} else if (!ste.equals(other.ste))
			return false;
		if (threadID != other.threadID)
			return false;
		if (warningID != other.warningID)
			return false;
		return true;
	}

}
