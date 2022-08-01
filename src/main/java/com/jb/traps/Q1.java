package com.jb.traps;

/*
 * 
 * Here I am going to explain why java ambiguous
 *  method call error comes with some examples. 
 * This ambiguous method call error 
 * always comes with method overloading where compiler 
 * fails to find out which of the overloaded method should be used.
 * 
 * The reason behind this is java compiler tries to find out the method 
 * with most specific input parameters to invoke a method. We know that 
 * Object is the parent class of String, so the choice was easy. 
 * Here is the excerpt from Java Language Specification.
 * 
 * 
 */

public class Q1 {

	public static void t1(String s) {
		System.out.println("String");
	}

	public static void t1(Integer s) {
		System.out.println("Integer");
	}

	public static void t1(Object s) {
		System.out.println("Object");
	}

	public static void main(String args[]) {
		//t1(null);
	}
}
