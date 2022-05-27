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

import java.util.HashMap;
import java.util.Vector;

/**
 * 
 * @author Georges Stephan
 *
 */
public class WarningsRegister {
	private static int warningID=0;
	private static HashMap<Long,Vector<Warning>> warningsMap = new HashMap<Long,Vector<Warning>>();
	
	public static void registerWarning(Warning warning) {
		long threadID = Thread.currentThread().getId();
		
		StackTraceElement[] elements = Thread.currentThread().getStackTrace();
		
		StackTraceElement ste = getInvokingElement(elements,"registerWarning");
		
		warning.setSource(ste);
		warning.setThreadID(threadID);
		
		warningID++;
		warning.setWarningID(warningID);
		
		Vector<Warning> vw = warningsMap.get(threadID);
		if(vw==null) {
			vw = new Vector<Warning>();
			vw.add(warning);
			warningsMap.put(threadID, vw);
		} else {
			vw.add(warning);
		}
	}
	
	private static StackTraceElement getInvokingElement(StackTraceElement[] elements,String methodName) {
		int invokingMethodIndex =-1;
		StackTraceElement ste = null;
		
		for(int i=0;i<elements.length;i++) {
			if(invokingMethodIndex==i) {
				ste=elements[i];
			} else if(elements[i].getMethodName().equals(methodName) 
					&& elements[i].getClassName().equals("com.machin.warnings.WarningsRegister")) {
				invokingMethodIndex=i;
				invokingMethodIndex++;
			}
		}
		
		return ste;
	}
	
	public static Warning[] getWarnings() {
		long threadID = Thread.currentThread().getId();
		
		StackTraceElement[] elements = Thread.currentThread().getStackTrace();		
		StackTraceElement ste = getInvokingElement(elements,"getWarnings");
		
		Vector<Warning> vw = warningsMap.get(threadID);
		Vector<Warning> vws = new Vector<Warning>();
		
		for(int i=0;i<vw.size();i++) {
			if(vw.elementAt(i).getSource().getMethodName().equals(ste.getMethodName())) { // TODO maybe add the package name
				vws.add(vw.elementAt(i));
				// TODO maybe remove warnings on retrieval ?
			}
		}
		
		return vw.toArray(new Warning[vws.size()]);

	}

}
