package com.jb.traps;

public class ThrdJob1 extends Thread {
	
	private int counter;
	
	public void run(){
		synchronized (this) {
			for(int i=0;i<100000;i++){
				counter++;
			}
				this.notifyAll();
				System.out.println("Counting done");
		
			
		}
		
	}

	public static void main(String[] args) throws InterruptedException {
		ThrdJob1 job = new ThrdJob1();
		job.start();
		Thread.sleep(10000);
		System.out.println("Waiting to end");
		synchronized (job) {
			job.wait();
		}
		System.out.println(job.counter);
	}

}
