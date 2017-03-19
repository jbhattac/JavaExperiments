package com.jb.java.collections;

import java.util.Comparator;
import java.util.PriorityQueue;
import java.util.Queue;
import java.util.Scanner;

/*
 * In computer science, a priority queue is an abstract data type which is like a regular queue, 
 * but where additionally each element has a "priority" associated with it. In a priority queue, an element with high priority 
 * is served before an element with low priority. 
 * 
 * You have to deal with  types of events: ENTER (a student enters the queue) or SERVED.
 * A unique token is assigned to any student entering the queue. The queue serves the students based on the following criteria:
 * 
 * The student having the highest Cumulative Grade Point Average (CGPA) is served first.
 * Any students having the same CGPA will be served by name in ascending case-sensitive alphabetical order.
 * Any students having the same CGPA and name will be served in ascending token order.
 * Given a sequence of  events, print the names of students who are yet to be served(based on above criteria). If the queue is empty, print EMPTY.
 * 
 * Input Format
 * 
 * The first line contains an integer, , denoting the total number of events. Each of the  subsequent lines will be of the following two forms:
 * 
 * ENTER name CGPA token - The student to be inserted into the priority queue.
 * 
 * SERVED - The highest priority student in the queue was served.
 * 
 * Output Format
 * 
 * Print the names (based on the criteria) of the students who are not served at all after executing all  events; if every student in the queue was served, then print EMPTY.
 * 
 * Sample Input
 * 
 * 12
 * ENTER John 3.75 50
 * ENTER Mark 3.8 24
 * ENTER Shafaet 3.7 35
 * SERVED
 * SERVED
 * ENTER Samiha 3.85 36
 * SERVED
 * ENTER Ashley 3.9 42
 * ENTER Maria 3.6 46
 * ENTER Anik 3.95 49
 * ENTER Dan 3.95 50
 * SERVED
 * 
 * Sample Output
 * 
 * Dan
 * Ashley
 * Shafaet
 * Maria
 * 
 * Explanation
 * 
 * Let's call our queue .
 * 
 *  We add John to the empty queue.
 *  
 *  We add Mark to the queue
 *  
 *  We add Shafaet to the queue
 *  
 *  Mark is served as he has the highest CGPA
 *  
 *  John is served next as he has the highest CGPA
 *  
 *  We add Samiha to the queue
 *  
 *  Samiha is served as she has the highest CGPA
 *  
 *  Through , the next four students are added giving us
 *  
 *   Anik is served because though both Anil and Dan have the highest CGPA but Anik comes first when sorted in alphabetic order
 *   
 *   As all events are completed, we print names of each remaining students on a new line.
 * 
 */

class Student {
	private int token;
	private String fname;
	private double cgpa;

	public Student(int id, String fname, double cgpa) {
		super();
		this.token = id;
		this.fname = fname;
		this.cgpa = cgpa;
	}

	public int getToken() {
		return token;
	}

	public String getFname() {
		return fname;
	}

	public double getCgpa() {
		return cgpa;
	}

	@Override
	public String toString() {
		return "Student [token=" + token + ", fname=" + fname + ", cgpa=" + cgpa + "]";
	}
	
}

public class PriorityQueueTest {

	
	


	public static void main(String[] args) {
		Scanner in = new Scanner(System.in);
		int totalEvents = Integer.parseInt(in.nextLine());
		Queue<Student> customerPriorityQueue = new PriorityQueue<>(totalEvents, Comparator.comparing(Student::getCgpa).reversed().thenComparing(Student::getFname).thenComparing(Student::getToken));
		while (totalEvents > 0) {
			String event = in.next();
			if ("ENTER".equals(event)) {
				String fName = in.next();
				double cgpa = in.nextDouble();
				int id = in.nextInt();
				customerPriorityQueue.add(new Student(id, fName, cgpa));
			} else {
				Student stud = customerPriorityQueue.poll();
			}

			// Complete your code

			totalEvents--;
		}
		
		if(customerPriorityQueue.isEmpty()){
			System.out.println("EMPTY");
		}else{
			while (!customerPriorityQueue.isEmpty()) {
				System.out.println(customerPriorityQueue.poll());
			//	customerPriorityQueue.forEach(s -> System.out.println(s.getFname()));
			} 
		}

	}
}
