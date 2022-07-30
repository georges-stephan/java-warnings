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

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantReadWriteLock;
import java.util.stream.Collectors;

/**
 * A collector of warnings which stores the warning in a HashMap in memory
 * 
 * @author Georges Stephan
 *
 */
public class InMemoryWarningsRegister {
	// Locking
	private static ReentrantReadWriteLock rwLock = new ReentrantReadWriteLock();
	private static Lock readLock = rwLock.readLock();
	private static Lock writeLock = rwLock.writeLock();
	// Others
	private static long warningID = 0L;
	private static Map<Long, HashMap<Long, Warning>> threadIDMapOfWarningIDWarningMap = new HashMap<Long, HashMap<Long, Warning>>();

	public static long registerWarning(String message) {
		return registerWarning(message, new HashMap<String, Object>(), new Object[0]);
	}

	public static long registerWarning(String message, Object... unnamedParameters) {
		return registerWarning(message, new HashMap<String, Object>(), unnamedParameters);
	}

	public static long registerWarning(String message, Map<String, Object> namedParameters,
			Object... unnamedParameters) {

		writeLock.lock();
		try {
			long threadID = Thread.currentThread().getId();
			StackTraceElement[] elements = Thread.currentThread().getStackTrace();

			warningID++;
			DefaultWarning aw = new DefaultWarning(warningID, message, elements, "registerWarning", threadID,
					namedParameters, unnamedParameters);

			HashMap<Long, Warning> warningIDWarningMap = threadIDMapOfWarningIDWarningMap.get(threadID);
			if (warningIDWarningMap == null) {
				warningIDWarningMap = new HashMap<Long, Warning>();
				warningIDWarningMap.put(warningID, aw);
			} else {
				warningIDWarningMap.put(warningID, aw);
			}
			threadIDMapOfWarningIDWarningMap.put(threadID, warningIDWarningMap);

			return warningID;
		} finally {
			writeLock.unlock();
		}
	}

	public static Optional<Warning[]> getWarnings(long threadID) {
		return getWarnings(threadID, -1L, false);
	}

	public static Optional<Warning[]> popWarnings(long threadID) {
		return getWarnings(threadID, -1L, true);
	}

	public static Optional<Warning[]> popWarnings(long threadID, long warningID) {
		return getWarnings(threadID, warningID, true);
	}

	public static Optional<Warning[]> getWarnings(long threadID, long warningID) {

		readLock.lock();
		try {
			Optional<Warning[]> aWarning = getWarnings(threadID, warningID, false);
			if (!aWarning.isPresent()) {
				return Optional.empty();
			}

			Warning[] warnings = aWarning.get();
			switch (warnings.length) {
				case 0:
					return Optional.empty();
				case 1:
					return Optional.of(warnings);
				default:
					throw new IllegalArgumentException(
							String.format("Too many (%i) warnings found for thread id: %l and warning id: %l",
									warnings.length, threadID, warningID));
			}
		} finally {
			readLock.unlock();
		}
	}

	private static Optional<Warning[]> getWarnings(long threadID, long warningID,
			boolean removeReturnedWarnings) {

		if (threadID < 0)
			throw new IllegalArgumentException("ThreadID should be a positive value, not " + threadID);

		HashMap<Long, Warning> mapOfWarningsPerThreadID = threadIDMapOfWarningIDWarningMap.get(threadID);

		if (mapOfWarningsPerThreadID == null) {
			// No warnings were registered with this thread id
			return Optional.empty();
		}

		if (warningID != -1) {
			// One thread ID and one warning ID will always return 1 warning
			if (threadIDMapOfWarningIDWarningMap.get(threadID) != null
					&& threadIDMapOfWarningIDWarningMap.get(threadID).get(warningID) != null) {
				Warning[] warnings = { threadIDMapOfWarningIDWarningMap.get(threadID).get(warningID) };
				if (removeReturnedWarnings) {
					writeLock.lock();
					try {
						threadIDMapOfWarningIDWarningMap.get(threadID).remove(warningID);
					} finally {
						writeLock.unlock();
					}
				}
				return Optional.ofNullable(warnings);
			}
		}

		// Return all warning for a thread ID
		Warning[] warnArray = mapOfWarningsPerThreadID.entrySet()
				.stream().map(m -> m.getValue())
				.collect(Collectors.toList()).toArray(Warning[]::new);

		if (removeReturnedWarnings) {
			writeLock.lock();
			try {
				Arrays
						.stream(warnArray)
						.forEach(a -> threadIDMapOfWarningIDWarningMap.get(threadID).remove(warningID));
			} finally {
				writeLock.unlock();
			}
		}

		return Optional.of(warnArray);

	}

}
