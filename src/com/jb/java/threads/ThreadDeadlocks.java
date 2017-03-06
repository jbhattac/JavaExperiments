package com.jb.java.threads;

public class ThreadDeadlocks {
	/*
	 * A deadlock is a situation where two or more thread are blocked for the same resource forever.
	 * Java thread dump is very helpful in analyzing bottlenecks in the application and 
	 * deadlock situations.
	 * 
	 * Multiple ways through which we can generate thread dump for a java program.
	 * 
	 *  VisualVM Profiler: If you are analyzing application for slowness, you must use a profiler.
	 *  We can generate thread dump for any process using VisualVM profiler very easily. 
	 *  You just need to right click on the running process and click on “Thread Dump” option to 
	 *  generate it.
	 *  
	 *  jstack: Java comes with jstack tool through which we can generate thread dump for 
	 *  a java process. 
	 *  This is a two step process.
		Find out the PID of the java process using ps -eaf | grep java command.
		
		Run jstack tool as jstack PID to generate the thread dump output to console,
		you can append thread dump output to file using command “jstack PID >> mydumps.tdump“
		 
		Java 8 has introduced jcmd utility. You should use this instead of jstack if you are on
		Java 8 or higher. Command to generate thread dump using jcmd is jcmd PID Thread.print
		  
		  
		The thread dump output clearly shows the deadlock situation and threads and resources 
		involved causing deadlock situation.

		For analyzing deadlock, we need to look out for the threads with state as BLOCKED and 
		then the resources it’s waiting to lock. 
		Every resource has a unique ID using which we can find which thread is already holding 
		the lock on the object. For example Thread “Thread-1” is waiting to lock 0x000000005749f000
		but it’s already locked by thread “Thread-0”.
		
		Avoid Nested Locks: This is the most common reason for deadlocks, avoid locking another 
		resource if you already hold one. It’s almost impossible to get deadlock situation if 
		you are working with only one object lock.
		
		Lock Only What is Required: You should acquire lock only on the resources you have to work 
		on.
		
		Avoid waiting indefinitely: You can get deadlock if two threads are waiting for each 
		other to finish indefinitely using thread join. If your thread has to wait for another 
		thread to finish, it’s always best to use join with maximum time you want to wait for 
		thread to finish.
		  
		The thread dump for running the following program below contains
		the following - : 
		  
		Thread Name: Name of the Thread
		  
		Thread Priority: Priority of the thread
		  
		Thread ID: Represents the unique ID of the Thread
		  
		Thread Status: Provides the current thread state, for example 
		            RUNNABLE, WAITING, BLOCKED. While analyzing deadlock look for 
		            the blocked threads and resources on which they are trying to acquire lock.
		            
		Thread callstack:   Provides the vital stack information for the thread.
		 					This is the place where we can see the locks obtained by Thread and 
		 					if it’s waiting for any lock.
		 					
		  2017-03-06 16:38:26
		Full thread dump Java HotSpot(TM) 64-Bit Server VM (25.111-b14 mixed mode):

"Thread-1" #11 prio=5 os_prio=0 tid=0x0000000056a19800 nid=0x2b90 waiting for monitor entry [0x000000005749f000]
   java.lang.Thread.State: BLOCKED (on object monitor)
	at com.jb.java.threads.ThreadDeadlocks.method1(ThreadDeadlocks.java:27)
	- waiting to lock <0x00000000ec569e30> (a java.lang.Object)
	at com.jb.java.threads.ThreadDeadlocks.method2(ThreadDeadlocks.java:50)
	- locked <0x00000000ec569e40> (a java.lang.Object)
	at com.jb.java.threads.ThreadDeadlocks.lambda$1(ThreadDeadlocks.java:15)
	at com.jb.java.threads.ThreadDeadlocks$$Lambda$2/471910020.run(Unknown Source)
	at java.lang.Thread.run(Unknown Source)

   Locked ownable synchronizers:
	- None

"Thread-0" #10 prio=5 os_prio=0 tid=0x0000000056a16800 nid=0x3198 waiting for monitor entry [0x00000000572cf000]
   java.lang.Thread.State: BLOCKED (on object monitor)
	at com.jb.java.threads.ThreadDeadlocks.method2(ThreadDeadlocks.java:42)
	- waiting to lock <0x00000000ec569e40> (a java.lang.Object)
	at com.jb.java.threads.ThreadDeadlocks.method1(ThreadDeadlocks.java:35)
	- locked <0x00000000ec569e30> (a java.lang.Object)
	at com.jb.java.threads.ThreadDeadlocks.lambda$0(ThreadDeadlocks.java:14)
	at com.jb.java.threads.ThreadDeadlocks$$Lambda$1/791452441.run(Unknown Source)
	at java.lang.Thread.run(Unknown Source)

   Locked ownable synchronizers:
	- None

"Service Thread" #9 daemon prio=9 os_prio=0 tid=0x000000005684f000 nid=0x21c8 runnable [0x0000000000000000]
   java.lang.Thread.State: RUNNABLE

   Locked ownable synchronizers:
	- None

"C1 CompilerThread2" #8 daemon prio=9 os_prio=2 tid=0x00000000567d3000 nid=0x17f8 waiting on condition [0x0000000000000000]
   java.lang.Thread.State: RUNNABLE

   Locked ownable synchronizers:
	- None

"C2 CompilerThread1" #7 daemon prio=9 os_prio=2 tid=0x000000005458a800 nid=0xda8 runnable [0x0000000000000000]
   java.lang.Thread.State: RUNNABLE

   Locked ownable synchronizers:
	- None

"C2 CompilerThread0" #6 daemon prio=9 os_prio=2 tid=0x0000000054586000 nid=0x407c waiting on condition [0x0000000000000000]
   java.lang.Thread.State: RUNNABLE

   Locked ownable synchronizers:
	- None

"Attach Listener" #5 daemon prio=5 os_prio=2 tid=0x0000000054584800 nid=0x21c0 waiting on condition [0x0000000000000000]
   java.lang.Thread.State: RUNNABLE

   Locked ownable synchronizers:
	- None

"Signal Dispatcher" #4 daemon prio=9 os_prio=2 tid=0x000000005458f000 nid=0xad4 runnable [0x0000000000000000]
   java.lang.Thread.State: RUNNABLE

   Locked ownable synchronizers:
	- None

"Finalizer" #3 daemon prio=8 os_prio=1 tid=0x000000005456e000 nid=0x3b94 in Object.wait() [0x000000005634f000]
   java.lang.Thread.State: WAITING (on object monitor)
	at java.lang.Object.wait(Native Method)
	- waiting on <0x00000000ec508e98> (a java.lang.ref.ReferenceQueue$Lock)
	at java.lang.ref.ReferenceQueue.remove(Unknown Source)
	- locked <0x00000000ec508e98> (a java.lang.ref.ReferenceQueue$Lock)
	at java.lang.ref.ReferenceQueue.remove(Unknown Source)
	at java.lang.ref.Finalizer$FinalizerThread.run(Unknown Source)

   Locked ownable synchronizers:
	- None

"Reference Handler" #2 daemon prio=10 os_prio=2 tid=0x0000000054525000 nid=0x39d0 in Object.wait() [0x0000000055fde000]
   java.lang.Thread.State: WAITING (on object monitor)
	at java.lang.Object.wait(Native Method)
	- waiting on <0x00000000ec506b40> (a java.lang.ref.Reference$Lock)
	at java.lang.Object.wait(Unknown Source)
	at java.lang.ref.Reference.tryHandlePending(Unknown Source)
	- locked <0x00000000ec506b40> (a java.lang.ref.Reference$Lock)
	at java.lang.ref.Reference$ReferenceHandler.run(Unknown Source)

   Locked ownable synchronizers:
	- None

"main" #1 prio=5 os_prio=0 tid=0x0000000002258000 nid=0x3250 in Object.wait() [0x000000000253f000]
   java.lang.Thread.State: WAITING (on object monitor)
	at java.lang.Object.wait(Native Method)
	- waiting on <0x00000000ec569fd0> (a java.lang.Thread)
	at java.lang.Thread.join(Unknown Source)
	- locked <0x00000000ec569fd0> (a java.lang.Thread)
	at java.lang.Thread.join(Unknown Source)
	at com.jb.java.threads.ThreadDeadlocks.main(ThreadDeadlocks.java:19)

   Locked ownable synchronizers:
	- None

"VM Thread" os_prio=2 tid=0x000000005451e000 nid=0x1d84 runnable 

"GC task thread#0 (ParallelGC)" os_prio=0 tid=0x000000000226c800 nid=0x36bc runnable 

"GC task thread#1 (ParallelGC)" os_prio=0 tid=0x000000000226e000 nid=0x43dc runnable 

"GC task thread#2 (ParallelGC)" os_prio=0 tid=0x000000000226f800 nid=0x43f8 runnable 

"GC task thread#3 (ParallelGC)" os_prio=0 tid=0x0000000002273000 nid=0x379c runnable 

"VM Periodic Task Thread" os_prio=2 tid=0x0000000056858800 nid=0x3714 waiting on condition 

JNI global references: 307


Found one Java-level deadlock:
=============================
"Thread-1":
  waiting to lock monitor 0x0000000056b3f338 (object 0x00000000ec569e30, a java.lang.Object),
  which is held by "Thread-0"
"Thread-0":
  waiting to lock monitor 0x000000005452b858 (object 0x00000000ec569e40, a java.lang.Object),
  which is held by "Thread-1"

Java stack information for the threads listed above:
===================================================
"Thread-1":
	at com.jb.java.threads.ThreadDeadlocks.method1(ThreadDeadlocks.java:27)
	- waiting to lock <0x00000000ec569e30> (a java.lang.Object)
	at com.jb.java.threads.ThreadDeadlocks.method2(ThreadDeadlocks.java:50)
	- locked <0x00000000ec569e40> (a java.lang.Object)
	at com.jb.java.threads.ThreadDeadlocks.lambda$1(ThreadDeadlocks.java:15)
	at com.jb.java.threads.ThreadDeadlocks$$Lambda$2/471910020.run(Unknown Source)
	at java.lang.Thread.run(Unknown Source)
"Thread-0":
	at com.jb.java.threads.ThreadDeadlocks.method2(ThreadDeadlocks.java:42)
	- waiting to lock <0x00000000ec569e40> (a java.lang.Object)
	at com.jb.java.threads.ThreadDeadlocks.method1(ThreadDeadlocks.java:35)
	- locked <0x00000000ec569e30> (a java.lang.Object)
	at com.jb.java.threads.ThreadDeadlocks.lambda$0(ThreadDeadlocks.java:14)
	at com.jb.java.threads.ThreadDeadlocks$$Lambda$1/791452441.run(Unknown Source)
	at java.lang.Thread.run(Unknown Source)

Found 1 deadlock.


	 * 
	 */

	Object lock1 = new Object();
	Object lock2 = new Object();

	public static void main(String[] args) throws InterruptedException {
		ThreadDeadlocks deadlock = new ThreadDeadlocks();
		Thread th1 = new Thread(() -> deadlock.method1());
		Thread th2 = new Thread(() -> deadlock.method2());
		th1.start();
		th2.start();
		
		th1.join();
		th2.join();
		Thread.currentThread().join();
		System.out.println("Done All Work");
	}

	public void method1() {
		synchronized (lock1) {
			System.out.println("Thread-1 doing work in method1 , holding lock for object1");
			try {
				Thread.sleep(100);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			System.out.println("Thread-1 calling method2 from method1, it will  need lock of object2");
			method2();
			System.out.println("Thread 1 done with method1");
		}
	}

	public void method2() {
		synchronized (lock2) {
			System.out.println("Thread-2 doing work in method2 , holding lock for object2");
			try {
				Thread.sleep(100);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			System.out.println("Thread-2 calling method1 from method2, it will  need lock of object1");
			method1();
			System.out.println("Thread 2 done with method2");
		}
	}

}
