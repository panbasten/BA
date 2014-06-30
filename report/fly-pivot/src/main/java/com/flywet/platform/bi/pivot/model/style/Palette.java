package com.flywet.platform.bi.pivot.model.style;

import java.lang.ref.SoftReference;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import com.flywet.platform.bi.pivot.model.ICacheable;

public class Palette implements ICacheable {

	private static final long serialVersionUID = -5583839419776818756L;

	// 缓存颜色
	private static Map<String, SoftReference<Palette>> CACHE = new ConcurrentHashMap<String, SoftReference<Palette>>();

	private final Color[] colors;

	private final String _uuid;

	private Palette(Color[] colors) {
		this.colors = colors;

		this._uuid = createUUID(colors);
	}

	public static Palette getDefaultInstance() {
		return null;
	}

	public static Palette getInstance(Color[] colors) {
		if (colors == null || colors.length < 1) {
			return null;
		}

		String key = createUUID(colors);
		Palette p = matchCache(key);
		if (p == null) {
			p = new Palette(colors);
			putCache(key, p);
		}
		return p;
	}

	private static Palette matchCache(String key) {
		SoftReference<Palette> ref = CACHE.get(key);
		if (ref != null) {
			return ref.get();
		}
		return null;
	}

	private static void putCache(String key, Palette p) {
		CACHE.put(key, new SoftReference<Palette>(p));
	}

	public static void clearCache() {
		CACHE.clear();
	}

	private static String createUUID(Color[] colors) {
		String result = "";
		if (colors != null) {
			for (Color c : colors) {
				result = result + ((c != null) ? c.getUUID() : "") + ",";
			}
		}
		return result;
	}

	@Override
	public String getUUID() {
		return _uuid;
	}

	@Override
	public String toString() {
		StringBuffer bf = new StringBuffer();

		if (colors != null && colors.length > 0) {
			bf.append(colors[0].getRGBText());

			for (int i = 1; i < colors.length; i++) {
				if (colors[i] != null) {
					bf.append(",");
					bf.append(colors[i].getRGBText());
				}
			}
		}

		return bf.toString();
	}

	public Color[] getColors() {
		return colors;
	}

}
