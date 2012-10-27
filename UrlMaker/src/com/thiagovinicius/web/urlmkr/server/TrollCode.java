package com.thiagovinicius.web.urlmkr.server;

import java.io.CharArrayWriter;
import java.math.BigInteger;
import java.security.InvalidParameterException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Random;

public class TrollCode {
	
	public static class Coder implements IdCoder {

		public final int minLen, maxLen;

		public Coder() {
			this(0, Integer.MAX_VALUE);
		}

		public Coder(int minLen, int maxLen) {
			this.minLen = minLen;
			this.maxLen = maxLen;
		}

		@Override
		public String encodeId(long id) {
			return new String(TrollCode.encode(id, minLen, maxLen));
		}

		@Override
		public long decodeId(String encoded, int begin) {
			return TrollCode.decodeLong(encoded.substring(begin).toCharArray());
		}

	}

	private static final int CODED_BIT_ZERO_INDEX = 4; //oool
	private static final int CODED_BIT_ONE_INDEX = 5; //ooool
	private static final int CODED_PADDING_AVAILABLE[] = { 2, 3, 6, 7, 8, 9, 10};

	private static final String UNARY_CODES[] = {
		"", "l",  "ol",  "ool",  "oool",  "ooool",  "oooool",
		"ooooool",  "oooooool",  "ooooooool",  "oooooooool",
	};

	private TrollCode() {
	}

	public static char[] minimalLengthEncode(byte input[]) {
		return encode(input, 0, Integer.MAX_VALUE);
	}

	public static char[] minimalLengthEncode(long input) {
		return encode(input, 0, Integer.MAX_VALUE);
	}

	public static char[] encode(long input, int minLen, int maxLen) throws IndexOutOfBoundsException {
		byte data[] = new byte[Long.SIZE/8];
		for (int i = 0; i < data.length; ++i) {
			data[i] = (byte) ((input >> (Long.SIZE - ((i+1)*8))) & 0xff);
		}
		return encode(data, minLen, maxLen);
	}

	public static long decodeLong(char encoded[]) {
		byte data[] = decodeBytes(encoded);
		long result = 0L;
		for (int i = 0; i < data.length; ++i) {
			result |= ((long) (data[i]) & 0xff) << (Long.SIZE - ((i+1)*8));
		}
		return result;
	}

	public static char[] encode(byte input[], int minLen, int maxLen) throws IndexOutOfBoundsException {
		BigInteger bint = new BigInteger(input);
		//BitSet bset = new Bit
		int unaryEncoded[] = new int[input.length * Byte.SIZE];
		int unaryReprLength = 0;

		for (int i = 0; i < unaryEncoded.length; ++i) {
			int codeword = bint.testBit(unaryEncoded.length - i - 1) ?
					CODED_BIT_ONE_INDEX : CODED_BIT_ZERO_INDEX;
			unaryEncoded[i] = codeword;
			unaryReprLength += codeword;
		}

		if (unaryReprLength > maxLen) {
			throw new IndexOutOfBoundsException("Minimum output length for " +
					"given input is: " + unaryReprLength);
		}

		if (unaryReprLength < minLen) {
			int padding[] = randomPadding(minLen - unaryReprLength);
			int newUnaryEnc[] = new int[unaryEncoded.length + padding.length];
			List<Object> takeFrom = new ArrayList<Object>(newUnaryEnc.length);
			Object original = new Object();
			Object padded = new Object();
			takeFrom.addAll(Collections.nCopies(unaryEncoded.length, original));
			takeFrom.addAll(Collections.nCopies(padding.length, padded));
			Collections.shuffle(takeFrom);

			int paddedIndex = 0;
			int encodedIndex = 0;
			for (int i = 0; i < newUnaryEnc.length; ++i) {
				if (takeFrom.get(i) == original) {
					newUnaryEnc[i] = unaryEncoded[encodedIndex];
					++encodedIndex;
				} else {
					newUnaryEnc[i] = padding[paddedIndex];
					++paddedIndex;
				}
			}

			unaryEncoded = newUnaryEnc;
			unaryReprLength = 0;
			for (int i : unaryEncoded) {
				unaryReprLength += i;
			}
		}

		CharArrayWriter output = new CharArrayWriter(unaryReprLength);
		for (int i : unaryEncoded) {
			output.append(UNARY_CODES[i]);
		}
		return output.toCharArray();
	}

	private static int[] randomPadding(int reprLength) {
		int availableReprLength = reprLength;
		int maxPadIndex = CODED_PADDING_AVAILABLE.length-1;
		int maxPad = CODED_PADDING_AVAILABLE[maxPadIndex];
		final int minPad = CODED_PADDING_AVAILABLE[0];
		int unaryPadding[] = new int[(reprLength / minPad) + 1]; //worst case
		int unaryArraySize = 0;
		Random rand = new Random();

		while (availableReprLength > 0) {
			while (maxPad > availableReprLength && maxPad != minPad) {
				--maxPadIndex;
				maxPad = CODED_PADDING_AVAILABLE[maxPadIndex];
			}
			int nextPad = CODED_PADDING_AVAILABLE[rand.nextInt(maxPadIndex+1)];
			availableReprLength -= nextPad;
			if (availableReprLength >= minPad || availableReprLength == 0) {
				//we're good to go
				unaryPadding[unaryArraySize] = nextPad;
				++unaryArraySize;
			} else {
				//not so fast
				availableReprLength += nextPad;
			}
		}

		return Arrays.copyOf(unaryPadding, unaryArraySize);
	}

	public static byte[] decodeBytes(char encoded[]) {
		final int minCode = Math.min(CODED_BIT_ONE_INDEX, CODED_BIT_ZERO_INDEX);
		byte rawDecoded[] = new byte[((encoded.length / minCode) + 1) / Byte.SIZE];
		int rawDecodedSize = 0;

		for (int i = 0; i < encoded.length; ++i) {
			final int base = i;
			int j;
			for (j = 0; encoded[base+j] != 'l'; ++j) {
				if (encoded[base+j] != 'o') {
					throw new InvalidParameterException("Input is not valid trollcode");
				}
			}
			final int codewordLength = j+1;
			if (codewordLength == CODED_BIT_ZERO_INDEX) {
				++rawDecodedSize;
			} else if (codewordLength == CODED_BIT_ONE_INDEX) {
				rawDecoded[rawDecodedSize / Byte.SIZE] |= 0x80 >>> rawDecodedSize % Byte.SIZE;
				++rawDecodedSize;
			} //else it is a pad
			i += j;
		}

		return Arrays.copyOf(rawDecoded, rawDecodedSize/Byte.SIZE +
                (rawDecodedSize % Byte.SIZE != 0 ? 1 : 0));
	}
	
}
