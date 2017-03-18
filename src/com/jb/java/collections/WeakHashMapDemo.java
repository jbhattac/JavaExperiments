package com.jb.java.collections;

import java.util.Map.Entry;
import java.util.Set;
import java.util.WeakHashMap;

public class WeakHashMapDemo {
	
	/*
	 * Simple code to test the behavior of the weakHashMap.
	 * I have used asset , it is advised to run the code with
	 * -enableassertions as VM argument.
	 * This is bit surprising the size of the map changes as 
	 * per the needs of the java memory, and well known rules are not valid.
	 * 
	 */

	public static void main(String[] args) {
		Integer[] KEY_ARRAY = new Integer[100];
		Integer[] VALUE_ARRAY = new Integer[100];
		
		WeakHashMap<Integer, Integer> weakMap = new WeakHashMap<Integer, Integer>();
		
		for(int i=0;i<100;i++){
			KEY_ARRAY[i] = new Integer(i);
			VALUE_ARRAY[i] = new Integer(i);
			weakMap.put(KEY_ARRAY[i], VALUE_ARRAY[i]);
		}
		
	
		// Check the entry set has correct size & content
		Set<Entry<Integer,Integer>> entrySet = weakMap.entrySet();
		assert 100==entrySet.size();
		System.out.println("Assert 0: Correct number of entries returned the map contains 100 elements");
		
		// Dereference a single key, then try to
		// force a collection of the weak ref'd obj
		KEY_ARRAY[50] = null;
		int count = 0;
		do {
			System.gc();
			System.gc();
			Runtime.getRuntime().runFinalization();
			count++;
		} while (count <= 5 && entrySet.size() == 100);
		
		assert 100>entrySet.size();
		
		System.out.println("Assert 1: It seems to be incorrect number of entries in the map  "+entrySet.size());
		
		//Question 1: why the size of entrySet is 99?
		
		//Ans 1 : An entry in a WeakHashMap will automatically be removed when its key is no longer
		//       in ordinary use or referenced from other variables like the KEY_ARRAY.
		//       The presence of a mapping for a given key will not prevent the key from being 
		//       discarded by the garbage collector, that is, made finalizable, finalized, and 
		//       then reclaimed. When a key has been discarded its entry is effectively removed 
		//       from the map, so this class behaves somewhat differently from other Map 
		//       implementations.
		//       If the value is holding a reference to the key then in spite of dereferencing the
		//		 the key , it will not be removed.
		
		// Dereference a single value,do not mean the entry 
		// will be removed from the map.
		VALUE_ARRAY[51] = null;
		
		count = 0;
		do {
			System.gc();
			System.gc();
			Runtime.getRuntime().runFinalization();
			count++;
		} while (count <= 5 && entrySet.size() == 100);
		
		assert 99==entrySet.size():"The entry set is not reclaimed as in this case the key is a strong reference of the value";
			
	}

}
