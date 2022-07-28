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

package warnings.jw.example;

import java.util.HashMap;
import java.util.Map;

import warnings.jw.DefaultWarning;

/**
 * This is an example of how a custom warning class could be modelled
 * 
 * @author Georges Stephan
 *
 */
public class SlowExecutionWarning extends DefaultWarning {

	public SlowExecutionWarning(String message) {
		super(message);
	}

	public SlowExecutionWarning(String message, Map<String, Object> namedParameters) {
		super(message, namedParameters, new Object[0]);
	}

	public SlowExecutionWarning(String message, Object[] unnamedParameters) {
		super(message, new HashMap<String, Object>(), unnamedParameters);
	}

	public SlowExecutionWarning(String message, Map<String, Object> namedParameters, Object... unnamedParameters) {
		super(message, namedParameters, unnamedParameters);
	}

}
