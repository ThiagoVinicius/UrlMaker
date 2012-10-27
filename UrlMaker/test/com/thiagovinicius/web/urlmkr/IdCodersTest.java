package com.thiagovinicius.web.urlmkr;

import static org.junit.Assert.*;

import java.util.Random;

import org.junit.Test;

import com.thiagovinicius.web.urlmkr.server.Coders;
import com.thiagovinicius.web.urlmkr.server.IdCoder;

public class IdCodersTest {

	@Test
	public void testCoders() {
		for (Coders c : Coders.values()) {
			runTestForCoder(c.coder);
		}
	}

	private static final long INPUTS[] = {
		Long.MAX_VALUE, Long.MIN_VALUE, 0L, 1L, 2L, -1L, -2L
	};
	private static final int RANDOM_INPUT_COUNT = 10000;
	private static final long RANDOM_INPUT_SEED = 1349617673000L;
	private void runTestForCoder(IdCoder coder) {
		for (long v : INPUTS) {
			String encoded = coder.encodeId(v);
			long decoded = coder.decodeId(encoded);
			assertEquals(v, decoded);
		}
		Random rand = new Random(RANDOM_INPUT_SEED);
		for (int i = 0; i < RANDOM_INPUT_COUNT; ++i) {
			long v = rand.nextLong();
			String encoded = coder.encodeId(v);
			long decoded = coder.decodeId(encoded);
			assertEquals(v, decoded);
		}
	}

}
