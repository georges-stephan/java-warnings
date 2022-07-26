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

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * 
 * @author Georges Stephan
 *
 */
public class WarningsRegister {
	private static int warningID = 0;
	private static HashMap<Long, ArrayList<Warning>> warningsMap = new HashMap<Long, ArrayList<Warning>>();

	public static void registerWarning(String message) {
		registerWarning(message, null, null);
	}

	public static void registerWarning(String message, Object... args) {
		registerWarning(message, args, null);
	}

	public static void registerWarning(String message, Object[] unnamedParameters,
			Map<String, Object> namedParameters) {

		long threadID = Thread.currentThread().getId();

		StackTraceElement[] elements = Thread.currentThread().getStackTrace();

		warningID++;

		DefaultWarning aw = new DefaultWarning(warningID, message, elements, "registerWarning", threadID,
				unnamedParameters,
				namedParameters);

		ArrayList<Warning> vw = warningsMap.get(threadID);
		if (vw == null) {
			vw = new ArrayList<Warning>();
			vw.add(aw);
			warningsMap.put(threadID, vw);
		} else {
			vw.add(aw);
		}
	}

	public static Warning[] getWarnings() {
		long threadID = Thread.currentThread().getId();

		ArrayList<Warning> vw = warningsMap.get(threadID);

		Warning[] warnArray = vw.stream()
				.filter(warn -> {
					return Arrays.stream(warn.getSource())
							.collect(Collectors.toList()).toArray().length > 0;
				})
				.map(w -> (Warning) w)
				.collect(Collectors.toList()).toArray(Warning[]::new);

		return warnArray;

	}

}
