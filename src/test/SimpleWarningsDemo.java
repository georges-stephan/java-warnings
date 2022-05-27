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

package test;

import warnings.jw.SimpleWarning;
import warnings.jw.SlowExecutionWarning;
import warnings.jw.Warning;
import warnings.jw.WarningsRegister;

public class SimpleWarningsDemo {
	
	public SimpleWarningsDemo() {
		doSomeWork1();
		doSomeWork2();
		doSomeWork3();
		doSomeWork4();
		
		Warning[] w = WarningsRegister.getWarnings();
		
		if(w==null || w.length<1) {
			System.out.println("No Warnings Found");
		} else {
			for(int i=0;i<w.length;i++) {
				System.out.println(w[i]);
			}			
		}
		
		System.out.println("Done.");
	}
	
	private void doSomeWork1() {
		// Do some work
	}
	
	private void doSomeWork2() {
		// Do some other work, and suddenly...
		// Ouch...
		WarningsRegister.registerWarning(new SimpleWarning("Achtung!"));
	}
	
	private void doSomeWork3() {
		// Do some work
	}
	
	private void doSomeWork4() {
		// Do some work
		WarningsRegister.registerWarning(new SlowExecutionWarning("es demasiado lento"));
		
	}

	public static void main(String[] args) {
		new SimpleWarningsDemo();
	}

}
