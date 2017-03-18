package com.jb.memory;

import java.lang.ref.SoftReference;
import java.lang.ref.WeakReference;

public class ReferencesDemo {
	
	/*
	 * There are 4 types of references in java
	 * 
	 * Strong reference
	 * Weak Reference
	 * Soft Reference
	 * Phantom Reference
	 */

	public static void main(String[] args) {
		//  Strong reference are ordinary references and are collected 
		// when there are no references pointing to the object in the heap.
		Object strong = new Object();
		
		// After dereferencing the a , it is not possiable to get the 
		// object in the heap it will be gcd.
		strong = null;
		System.out.println("Strong ref is "+strong);
		// weak references are references which are gcd when there are no more 
		// strong references pointing to the object . The collection in this 
		// case is eager.
		Object weak = new Object();
		WeakReference<Object> weakRef = new WeakReference<Object>(weak);
		weak = null;
		// we request the gc for collection
		System.gc();
		System.gc();
		Runtime.getRuntime().runFinalization();
		//we try to get the reference from the weak reference
		
		weak = weakRef.get();
		
		System.out.println("Weak ref is "+weak);
		
		// Soft reference are references that will be gcd when there are 
		// no more strong references and the jvm absolutely needs to reclaim 
		// some memory back. So in this case the collection is not eager, and that 
		//is the difference with the weak reference.
		
		Object soft = new Object();
		SoftReference<Object> softRef = new SoftReference<Object>(soft);
		
		soft = null;
		// we request the gc for collection
		System.gc();
		System.gc();
		Runtime.getRuntime().runFinalization();
		
		soft = softRef.get();
		
		//In this case we can get back the reference if the jvm absolutely do not ]
		// need memory.
		
		System.out.println("Soft ref is "+soft);
		
		//  Phantom Reference will be collected once there is no strong references 
		// Let us have another Example for that 
		
	

	}

}
