/**
 * 
 */
package com.flatironschool.javacs;

import java.util.List;
import java.util.Map;
import java.util.ArrayList;

/**
 * Implementation of a HashMap using a collection of MyLinearMap and
 * resizing when there are too many entries.
 * 
 * @author downey
 * @param <K>
 * @param <V>
 *
 */
public class MyHashMap<K, V> extends MyBetterMap<K, V> implements Map<K, V> {
	
	// average number of entries per map before we rehash
	protected static final double FACTOR = 1.0;
	protected int size = 0;

	@Override
	public V put(K key, V value) {
		MyLinearMap<K, V> map = chooseMap(key);
		size -= map.size();
		V oldValue = super.put(key, value);
		size += map.size();
		
		//System.out.println("Put " + key + " in " + map + " size now " + map.size());
		
		// check if the number of elements per map exceeds the threshold
		if (size() > maps.size() * FACTOR) {
			size = 0;
			rehash();
		}
		return oldValue;
	}

	@Override
	public V remove(Object key) {
		MyLinearMap<K, V> map = chooseMap(key);
		size -= map.size();
		V oldValue = map.remove(key);
		size += map.size();
		return oldValue;
	}

	@Override
	public void clear() {
		// clear the sub-maps
		super.clear();
		size = 0;
	}

	/**
	 * Doubles the number of maps and rehashes the existing entries.
	 */
	/**
	 * 
	 */
	protected void rehash() {
        // TODO: fill this in.
        // collect all the entries in the table
        List<Map.Entry<K, V>> entries = new ArrayList<Map.Entry<K, V>>();
        for (MyLinearMap<K, V> map: maps) {
        	entries.addAll(map.getEntries());
        }
        // resize the table
        makeMaps(maps.size() * 2);
        // put the entries back to the table
        for (Map.Entry<K, V> entry: entries) {
        	put(entry.getKey(), entry.getValue());
        }
	}

	@Override
	public int size() {
		return size;
	}

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		Map<String, Integer> map = new MyHashMap<String, Integer>();
		for (int i=0; i<10; i++) {
			map.put(new Integer(i).toString(), i);
		}
		Integer value = map.get("3");
		System.out.println(value);
	}
}
