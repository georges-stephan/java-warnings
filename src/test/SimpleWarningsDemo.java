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

package test;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

import warnings.jw.Warning;
import warnings.jw.InMemoryWarningsRegister;

public class SimpleWarningsDemo {
	private ArrayList<Long> collectedWarningIDs = new ArrayList<>();

	public SimpleWarningsDemo() {
		doSomeWork1();
		doSomeWork2();
		doSomeWork3();
		doSomeWork4();

		long thisThreadID = Thread.currentThread().getId();

		System.out.println("This thread ID:" + thisThreadID);

		Optional<Warning[]> warnings = InMemoryWarningsRegister.getWarnings(thisThreadID);

		if (warnings.isPresent()) {
			Arrays.stream(warnings.get()).forEach(System.out::println);
		} else {
			System.out.println(String.format("No warnings found for thread %d.", thisThreadID));
		}

		System.out.println("Done.");
	}

	private void doSomeWork1() {
		// Do some work
	}

	private void doSomeWork2() {
		// Do some other work, and suddenly...
		// Ouch...
		collectedWarningIDs.add(InMemoryWarningsRegister.registerWarning("Ouch!"));
	}

	private void doSomeWork3() {
		// Do some work
		collectedWarningIDs.add(InMemoryWarningsRegister.registerWarning("Oh la la...", "Oh", "la", 22));
	}

	private void doSomeWork4() {
		// Do some work
		Map<String, Object> namedParams = new HashMap<>();
		namedParams.put("toul", 100);
		namedParams.put("3ard", 2);

		Object[] unNamedParams = { "Un", "Deux", "Trois" };

		collectedWarningIDs
				.add(InMemoryWarningsRegister.registerWarning("es demasiado lento", namedParams, unNamedParams));

	}

	public static void main(String[] args) {
		new SimpleWarningsDemo();
	}

}
