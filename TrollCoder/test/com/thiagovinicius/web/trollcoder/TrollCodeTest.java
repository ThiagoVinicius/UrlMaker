package com.thiagovinicius.web.trollcoder;

import static org.junit.Assert.*;

import java.util.Random;

import org.junit.Test;

import com.thiagovinicius.web.trollcoder.server.TrollCode;

public class TrollCodeTest {

	private static long INPUTS[] = {
		1L, 2L, 3L, Long.MAX_VALUE,
		1L, 1L, 1L, 1L
	};

	@Test
	public void testMinimalEncode() {
		for (long i : INPUTS) {
			char encoded[] = TrollCode.minimalLengthEncode(i);
			long decoded = TrollCode.decodeLong(encoded);
			assertEquals(i, decoded);
			System.out.println(encoded);
		}
	}
	
	@Test
	public void testEncode() {
		for (long i : INPUTS) {
			char encoded[] = TrollCode.encode(i, 2000, Integer.MAX_VALUE);
			long decoded = TrollCode.decodeLong(encoded);
			assertEquals(i, decoded);
			System.out.println(encoded);
		}
	}
	
	private static final int DATA_LENGTH = 9000000;
	private static final long RANDOM_SEED = 1349617673000L;
	@Test
	public void testPerformance() {
		byte data[] = new byte[DATA_LENGTH];
		Random rand = new Random(RANDOM_SEED);
		rand.nextBytes(data);
		long t0 = System.nanoTime();
		char encoded[] = TrollCode.minimalLengthEncode(data);
		long dt = System.nanoTime() - t0;
		System.out.printf("Encoding took: %dns\n", dt);
		t0 = System.nanoTime();
		byte decoded[] = TrollCode.decodeBytes(encoded);
		dt = System.nanoTime() - t0;
		System.out.printf("Decoding took: %dns\n", dt);
		assertArrayEquals(data, decoded);
	}
	
}
