package com.quickdraw.util;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

public class ListMap<K,V> implements Map<K,V>{
    private List<K> key = new ArrayList<>();
    private List<V> value = new ArrayList<>();
    private HashMap<Object, Object> collectionContainer = new HashMap<>();
	@Override
	public int size() {
		return key.size();
	}

	@Override
	public boolean isEmpty() {
		if (key.isEmpty() && value.isEmpty()) {
			return true;
		}
		return false;
	}

	@Override
	public boolean containsKey(Object key) {
		for (K k : this.key) {
			if (k.equals(key)) {
				return true;
			}
		}
		return false;
	}	
	@Override
	public V remove(Object key) {
		V target = null;
		int size = this.key.size();
		for (int i = 0;i < size;i++) {
			if (this.key.get(i).equals(key)) {
				target = this.value.get(i);
				this.key.remove(i);
				this.value.remove(i);
			}
		}
		return target;
	}

	@SuppressWarnings({ "unchecked", "rawtypes" })
	@Override
	public void putAll(Map m) {
		List key = new ArrayList<>();
		List value = new ArrayList<>();
		for (Object obj : m.keySet()) {
			key.add(obj);
		}
		for (Object obj : m.values()) {
			value.add(obj);
		}
		this.key = key;
		this.value = value;
	}

	@Override
	public void clear() {
		this.key = new ArrayList<>();
		this.value = new ArrayList<>();
	}

	@Override
	public Set<K> keySet() {
		Set<K> keySet = new HashSet<>();
		for (K key : this.key) {
			keySet.add(key);
		}
		return keySet;
	}
	@Deprecated
	@Override
	public Collection<V> values() {
		@SuppressWarnings("unchecked")
		Collection<V> collection = getEmptyCollection();
		collection.clear();
		for (V value : this.value) {
			collection.add(value);
		}
		return collection;
	}
    @SuppressWarnings({ "unchecked", "rawtypes" })
	@Deprecated
	@Override
	public Set entrySet() {
		return null;
	}

	@Override
	public boolean containsValue(Object value) {
		for (V v : this.value) {
			if (value.equals(v)) {
				return true;
			}
		}
		return false;
	}

	@Override
	public V get(Object key) {
		int size = this.key.size();
		for (int i = 0;i < size;i++) {
			if (this.key.get(i).equals(key)) {
				return this.value.get(i);
			}
		}
		return null;
	}

	@Override
	public V put(K key, V value) {
		this.key.add(key);
		this.value.add(value);
		return null;
	}
    public K getByValue(Object value) {
    	int size = this.value.size();
    	for (int i = 0;i < size;i++) {
			if (this.value.get(i).equals(value)) {
				return this.key.get(i);
			}
		}
    	return null;
    }
    public List<K> keyList() {
    	return this.key;
    }
    public List<V> valueList() {
    	return this.value;
    }
    @SuppressWarnings("rawtypes")
	public Collection getEmptyCollection() {
    	Collection empty = this.collectionContainer.values();
    	empty.clear();
    	return empty;
    }
    public K getKeyAt(int at) {
    	return this.key.get(at);
    }
    public V getValueAt(int at) {
    	return this.value.get(at);
    }
    public Bag<K,V> getLine(int at) {
    	return new Bag<K,V>(this.key.get(at), this.value.get(at));
    }
}
