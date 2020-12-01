package com.ot.concurrent.aqs.list;

public interface List {
     int size();
     void add(Object o);
     void add(int i, Object e);
     Object get(int index);
     boolean isEmpty();
     boolean remove(Object o);
	 boolean contains(Object e);
}
