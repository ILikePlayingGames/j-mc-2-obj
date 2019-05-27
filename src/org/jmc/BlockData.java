package org.jmc;

import java.util.AbstractMap;
import java.util.HashMap;

@SuppressWarnings("serial")
public class BlockData extends HashMap<String, String> {
	
	/**
	 * Sets id to "" empty string.
	 */
	public BlockData() {
		this.id = "";
	}
	
	public BlockData(String id) {
		this.id = id;
	}
	
	public String id;
	
	public boolean matchesMask(BlockData dataMask) {
		for (Entry<String, String> keyPair : dataMask.entrySet()) {
			if (!keyPair.getValue().equals(this.get(keyPair.getKey())))
				return false;
		}
		return true;
	}
	
	@Override
	public boolean equals(Object o) {
		if (super.equals(o) && o instanceof BlockData) {
			return this.id.equals(((BlockData)o).id);
		}
		return false;
	}
	
	/**
	 * @param other
	 * @return result of {@link AbstractMap#equals(Object)} ignoring block id
	 */
	public boolean equalData(BlockData other) {
		return super.equals(other);
	}
	
	/**
	 * @param key map key
	 * @return result of {@link Boolean#parseBoolean(String) parseBoolean} on value, will return false if key not found 
	 */
	public boolean getBool(String key) {
		if (this.containsKey(key)) {
			return Boolean.parseBoolean(this.get(key));
		} else {
			return false;
		}
	}
	
	/**
	 * @param key map key
	 * @return result of {@link Integer#parseInt(String) parseInt} on value, will return -1 if key not found 
	 */
	public int getInt(String key) {
		if (this.containsKey(key)) {
			return Integer.parseInt(this.get(key));
		} else {
			return -1;
		}
	}
	
	@Override
	public String toString() {
		return String.format("id=%s %s", id, super.toString());
	}
}
