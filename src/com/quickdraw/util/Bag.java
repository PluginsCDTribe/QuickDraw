package com.quickdraw.util;

public class Bag<K,V> {
private K k;
private V v;
public Bag(K k, V v) {
	this.k = k;
	this.v = v;
}
public void setValue(V v) {
	this.v = v;
}
public void setKey(K k) {
	this.k = k;
}
public K getKey() {
	return this.k;
}
public V getValue() {
	return this.v;
}
}
