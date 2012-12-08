package com.thiagovinicius.web.urlmkr.server;


public class FuCoder {

	public static class Coder implements IdCoder {

		@Override
		public String encodeId(long id) {
			return FuCoder.encode(id);
		}

		@Override
		public long decodeId(String encoded) {
			return FuCoder.decode(encoded);
		}

	}

	private static final char DIFFERENTIAL_HIGH = 0;
	private static final char DIFFERENTIAL_LOW = 1;
	private static final char DIFFERENTIAL_CHANGE[] = {
		DIFFERENTIAL_LOW,
		DIFFERENTIAL_HIGH };

	private static final char MAP_F_PART[] = {
		'F', 'f'
	};

	private static final char MAP_U_PART[] = {
		'U', 'u'
	};

	private static final char REV_MAP_F_PART[];
	private static final char REV_MAP_U_PART[];

	private static final char INVERT_CASE[];

	static {
		REV_MAP_F_PART = reverseMap(MAP_F_PART);
		REV_MAP_U_PART = reverseMap(MAP_U_PART);
		INVERT_CASE = createCaseInverter(Character.MAX_VALUE);
	}

	private static char[] reverseMap(char map[]) {
		char max = '\0';
		for (char i : map) {
			max = max < i ? i : max;
		}
		char result[] = new char[max+1];
		for (char i = 0; i < map.length; ++i) {
			result[map[i]] = i;
		}
		return result;
	}

	private static char[] createCaseInverter(char maxCode) {
		char result[] = new char[maxCode];
		for (char i = 0; i < result.length; ++i) {
			if (Character.isLowerCase(i)) {
				result[i] = Character.toUpperCase(i);
			} else if (Character.isUpperCase(i)) {
				result[i] = Character.toLowerCase(i);
			} else {
				result[i] = i;
			}
		}
		return result;
	}

	public static String encode(long id) {
		char bits[] = new char[Long.SIZE + 1];
		for (int i = 0; i < Long.SIZE; ++i) {
			bits[i+1] = (char) ((id >>> i) & 1L);
		}
		differentialEncode(bits);
		remap(bits, MAP_F_PART, 0, bits.length/2);
		remap(bits, MAP_U_PART, bits.length/2, bits.length);

		int upperFCount = countOccurrences(bits, 'F');
		int lowerFCount = countOccurrences(bits, 'f');
		if (lowerFCount > upperFCount) {
			//Choose the code that has the most uppercase Fs
			invertCase(bits);
		}

		return new String(bits);
	}

	private static void differentialEncode(char bits[]) {
		char currentLevel = DIFFERENTIAL_HIGH; //arbitrary
		bits[0] = currentLevel;
		for (int i = 1; i < bits.length; ++i) {
			if (bits[i] != 0) {
				currentLevel = DIFFERENTIAL_CHANGE[currentLevel];
			}
			bits[i] = currentLevel;
		}
	}

	private static void remap(char bits[], char map[], final int begin,
			final int end) {
		for(int i = begin; i < end; ++i) {
			bits[i] = map[bits[i]];
		}
	}

	/**
	 * As this is a differential code, you can invert case at will.
	 *
	 * Problem?
	 */
	private static void invertCase(char arr[]) {
		for (int i = 0; i < arr.length; ++i) {
			arr[i] = INVERT_CASE[arr[i]];
		}
	}

	private static int countOccurrences(char arr[], char c) {
		int result = 0;
		for (int i = 0; i < arr.length; ++i) {
			if (arr[i] == c) {
				++result;
			}
		}
		return result;
	}

	public static long decode(String encodedStr) {
		char encoded[] = encodedStr.toCharArray();
		long result = 0L;
		remap(encoded, REV_MAP_F_PART, 0, encoded.length/2);
		remap(encoded, REV_MAP_U_PART, encoded.length/2, encoded.length);
		differentialDecode(encoded);
		for (int i = encoded.length-1; i >= 1; --i) {
			result <<= 1;
			result |= (long)(encoded[i]) & 1L;
		}
		return result;
	}

	private static void differentialDecode(char encoded[]) {
		char currentLevel = encoded[0];
		for (int i = 1; i < encoded.length; ++i) {
			if (encoded[i] == currentLevel) {
				encoded[i] = 0;
			} else {
				currentLevel = DIFFERENTIAL_CHANGE[currentLevel];
				encoded[i] = 1;
			}
		}
	}

}
