package uk.co.optimisticpanda.randmp3;

import static java.lang.String.format;

class Util {

	static void checkState(ThrowingSupplier<Boolean> expression, String template) {
		try {
			if (!expression.get()) {
				throw new IllegalStateException(template);
			}
		} catch (Exception e) {
			throw new IllegalStateException(format(template, e));
		}
	}

	@FunctionalInterface
	interface ThrowingSupplier<T> {
		T get() throws Exception;
	}

}
