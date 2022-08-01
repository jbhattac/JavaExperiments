package com.jb.memory;

import java.lang.ref.PhantomReference;
import java.lang.ref.Reference;
import java.lang.ref.ReferenceQueue;

public class ReferenceQueueDemo {
	
	/*
	 * In this example I will try to get on with two things
	 * the ReferenceQueue and the PhanthomReferences .
	 * 
	 * Reference queues are queues to which registered reference objects are appended by the garbage
	 * collector after the objects reachability changes are detected.
	 * 
	 * When creating non-strong reference, specially with Phantom Reference objects we have the 
	 * option of passing a reference queue as a part of the Reference constructor. 
	 * With Phantom Reference it is mandatory.
	 * As seen from the above explanation, this reference 
	 * queue is a way for the GC to inform the program that a certain object is no longer reachable.
	 * 
	 * 
	 */

	public static void main(String[] args) {
		Object phantom = new Object();
		ReferenceQueue<Object> gcdQueue= new ReferenceQueue<Object>();// the ReferenceQueue
		// Let us create a PhantomReference of the stong ref
		PhantomReference<Object> phantomRef = new PhantomReference<Object>(phantom, gcdQueue);
		System.out.println("1. Inspect the Queue, is there any element "+gcdQueue.poll());
		System.out.println("2. Is the weak reference added to the ReferenceQ  ? "+phantomRef.isEnqueued());
		
		//Dereference the strong reference
		phantom= null;
		
		// we request the gc for collection.
		System.gc();
		System.gc();
		Runtime.getRuntime().runFinalization();
		
		phantom=phantomRef.get();
		//In this case we will not get back the reference.
		System.out.println("3. Phantom in the heap is  "+phantom);
		
		//However gc will enqueue the reference in the ReferenceQueue and we can do clean up there.
		
		System.out.println("4. Is the weak reference added to the ReferenceQ now  ? "+phantomRef.isEnqueued());
		
		Reference<? extends Object> phantomRecreate =gcdQueue.poll();	
		
		System.out.println("5. The phantom reference that is in the reference queue is "+phantomRecreate);
		
		System.out.println("6. The original object from the phantom object, in the heap is   "+phantomRecreate.get());
		
		/*
		 * As seen from above the Reference queue actually holds within it the WeakReference which lost its heap object to clean up. 
		 * The phantom reference does not have any association to the memory object. The get call above returns null. Unlike with finalize when we can 
		 * make the object alive again, with the ReferenceQ there is no way to reach the released java object. Reference Queues are just for References 
		 * that got freed by garbage collection. They cannot be used to make alive our objects again. They can only be used to notify our code about 
		 * the loss of memory objects referred to by these non- strong references.
		 * 
		 */
		

	}

}
