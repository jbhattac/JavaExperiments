package com.jb.java.collections;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.ListIterator;
import java.util.Map;

/*
 * 
 * java.util.ConcurrentModificationException is a very common exception when working with java 
 * collection classes. 
 * 
 * Java Collection classes are fail-fast, which means if the Collection will be changed while 
 * some thread is traversing over it using iterator, the iterator.next() will throw 
 * ConcurrentModificationException. Concurrent modification exception can come in case of 
 * multithreaded as well as single threaded java programming environment.
 * 
 *fail-fast property -
 *
 *Iterator fail-fast property checks for any modification in the structure of
 * the underlying collection every time we try to get the next element  if there are any 
 * modifications found, it throws ConcurrentModificationException. All the implementations of 
 * Iterator in Collection classes are fail-fast by design except the concurrent collection classes 
 * like ConcurrentHashMap and CopyOnWriteArrayList.
 * 
 *fail-fast and fail-safe
 * 
 * Iterator fail-safe property work with the clone of underlying collection
 * , hence it’s not affected by any modification in the collection. 
 * 
 * By design, all the collection classes in java.util package are fail-fast whereas collection 
 * classes in java.util.concurrent are fail-safe.
 * Fail-fast iterators throw ConcurrentModificationException whereas fail-safe iterator never throws
 * ConcurrentModificationException.
 * 
 */

public class TestConcurrentModificationException {

	public static void main(String[] args) {
		List<String> myList = new ArrayList<String>();

		myList.add("1");
		myList.add("2");
		myList.add("3");
		myList.add("4");
		myList.add("5");

		Iterator<String> it = myList.iterator();
		while (it.hasNext()) {
			String value = it.next();
			System.out.println("List Value:" + value);
			//if (value.equals("3")){
				//myList.remove(value);
				/*
				 * The above line throws the following exception
				 * 
				 * Exception in thread "main" List Value:3

				 *	java.util.ConcurrentModificationException
					at java.util.ArrayList$Itr.checkForComodification(Unknown Source)
					at java.util.ArrayList$Itr.next(Unknown Source)
					at com.jb.java.collections.TestConcurrentModificationException.main(TestConcurrentModificationException.java:50)
					
					
					From the output stack trace, its clear that the concurrent modification 
					exception is coming when we call iterator next() function. 
					Iterator checks for the modification, its implementation is present in 
					AbstractList class where an int variable modCount is defined. 
					modCount provides the number of times list size has been changed. 
					modCount value is used in every next() call to check for any modifications 
					in a function checkForComodification().
					
					
				 */
		//	}
		}
			
			System.out.println("List Value:----------------------------------");
			
			Map<String,String> myMap = new HashMap<String,String>();
			myMap.put("1", "1");
			myMap.put("2", "2");
			myMap.put("3", "3");

			Iterator<String> it1 = myMap.keySet().iterator();
			while(it1.hasNext()){
				String key = it1.next();
				System.out.println("Map Value:"+myMap.get(key));
				if(key.equals("2")){
					
					myMap.put("1","4");
					
					/*
					 * The above line will not give this concurrent modification exception
					 * Since we are updating the existing key value in the myMap, its size has not 
					 * been changed and we will not getting ConcurrentModificationException. 
					 *  
					 */
					
					
					//myMap.put("4", "4");
					
					/*
					 * The above line will  give this concurrent modification exception
					 * Since we are updating the existing key value in the myMap, its size will 
					 * change  and we will be getting ConcurrentModificationException. 
					 * 					 * 
					 */
				}
			}
				
				System.out.println("Map Value:----------------------------------");
				
				/*
				 * Avoid ConcurrentModificationException in multi-threaded environment
				 * You can convert the list to an array and then iterate on the array. 
				 * This approach works well for small or medium size list but if the list is large
				 *  then it will affect the performance a lot.
				 *  You can lock the list while iterating by putting it in a synchronized block.
				 *  This approach is not recommended because it will cease the benefits of 
				 *  multithreading.
				 *  
				 *  If you are using JDK1.5 or higher then you can use ConcurrentHashMap and 
				 *  CopyOnWriteArrayList  classes. This is the recommended approach to avoid concurrent 
				 *  modification exception. 
				 *  
				 *  As name suggest CopyOnWriteArrayList creates copy of underlying ArrayList 
				 *  with every mutation operation e.g. add or set. Normally CopyOnWriteArrayList 
				 *  is very expensive because it involves costly Array copy with every write 
				 *  operation but its very efficient if you have a List where Iteration outnumber 
				 *  mutation e.g. you mostly need to iterate the ArrayList and don't modify it 
				 *  too often.
				 *  
				 *  Iterator of CopyOnWriteArrayList is fail-safe and doesn't throw
				 *  ConcurrentModificationException even if underlying CopyOnWriteArrayList is 
				 *  modified once Iteration begins because Iterator is operating on separate copy 
				 *  of ArrayList.
				 *  Consequently all the updates made on CopyOnWriteArrayList is not available 
				 *  to Iterator.
				 *  
				 * 
				 * ConcurrentHashMap: It allows concurrent access to the map. Part of the map 
				 * called Segment (internal data structure) is only getting locked while adding or 
				 * updating the map. So ConcurrentHashMap allows concurrent threads to read the 
				 * value without locking at all. This data structure was introduced to improve 
				 * performance.
				 * 
				 * A ConcurrentHashMap is divided into number of segments, and for example 
				 * the default is 32 on initialization.
				 * A ConcurrentHashMap has internal final class called Segment 
				 * so we can say that ConcurrentHashMap is internally divided in segments of size 
				 * 32, so at max 32 threads can work at a time.
				 * It means each thread can work on a each segment during high concurrency
				 * and atmost 32 threads can operate at max which simply maintains 32 locks
				 * to guard each bucket of the ConcurrentHashMap.
				 * 
				 * 
				 */
				
				/*
				 * Avoid ConcurrentModificationException in single-threaded environment
				 * You can use the iterator remove() function to remove the object 
				 * from underlying collection object.  
				 * 
				 * 
				 * 
				 */

				ListIterator<String> it01 = myList.listIterator();
				while (it01.hasNext()) {
					
					if (it01.next().equals("3")){
						it01.remove();
						it01.add("93");
						
					}
					
				}
				
				for (String val : myList) {
					System.out.println("List Value:" + val);
				}
				
				/*
				 * You will get ConcurrentModificationException if you will try to modify the 
				 * structure of original list with subList.
				 * 
				 */
				
				List<String> subList = myList.subList(0, 2);
				
				System.out.println("My List "+myList +" ,Now the Sub List "+subList);
				
				myList.add("100");
				
				System.out.println("Now the Sub List will throw "+subList);
				
				/*
				 * 
				 *  ConcurrentModificationException if you will try to modify the structure of 
				 *  original list with subList. 
				 *  
				 *  All methods on returned list first check to see if the actual modCount of the 
				 *  backing list is equal to its expected value, and throw a 
				 *  ConcurrentModificationException if it is not.
				 *  
				 *  Exception in thread "main" java.util.ConcurrentModificationException
					at java.util.ArrayList$SubList.checkForComodification(Unknown Source)
					at java.util.ArrayList$SubList.listIterator(Unknown Source)
					at java.util.AbstractList.listIterator(Unknown Source)
					at java.util.ArrayList$SubList.iterator(Unknown Source)
					at java.util.AbstractCollection.toString(Unknown Source)
					at java.lang.String.valueOf(Unknown Source)
					at java.lang.StringBuilder.append(Unknown Source)
					at com.jb.java.collections.TestConcurrentModificationException.main(TestConcurrentModificationException.java:168)

				 *  
				 * 
				 */
				
				
			}
			
		

	

}
