package com.thiagovinicius.web.urlmkr.server;

import java.util.concurrent.atomic.AtomicInteger;


public enum Coders {
	TROLL("Troll code", "/trol", new TrollCode.Coder(500, 500)),
	FUUU("FFFUUU code", "/FFFF", new FuCoder.Coder()),
	HEX("Hex code", "/0x", new HexCoder()),
	MORSE("Morse", "/...", new MorseCoder());

	public final String friendlyName;
	public final String handle;
	public final IdCoder coder;

	private Coders(String friendlyName, String handle, IdCoder coder) {
		this.friendlyName = friendlyName;
		this.handle = handle;
		this.coder = coder;
	}

	public static String getHandle(Class<?> coderClass) {
		for (Coders i : Coders.values()) {
			if (coderClass.isInstance(i.coder)) {
				return i.handle;
			}
		}
		return null;
	}

	public static IdCoder getCoder(String uri, AtomicInteger offset) {
		for (Coders i : Coders.values()) {
			if (uri.startsWith(i.handle)) {
				offset.set(i.handle.length());
				return i.coder;
			}
		}
		return null;
	}
}
