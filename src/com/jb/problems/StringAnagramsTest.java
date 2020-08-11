package com.jb.problems;

import java.util.Arrays;
import java.util.stream.Collector;
import java.util.stream.Collectors;

public class StringAnagramsTest {

	public static void main(String[] args) {
		String testString1 = "Angel";
		String testString2 = "Ageln";
		
		String sortedTestString1 = testString1.chars()
									.mapToObj(obj->(char)obj)
									.sorted()
									.map(obj->obj.toString())
									.collect(Collectors.joining());
		
		
		System.out.println("Sorted test String 1  :: "+ sortedTestString1);
		
		String sortedTestString2 = testString2.chars()
									.mapToObj(obj->(char)obj)
									.sorted()
									.map(obj->obj.toString())
									.collect(Collectors.joining());

		System.out.println("Sorted test String 2  :: "+ sortedTestString2);
		
		System.out.println("Test String are anagrams : "+ sortedTestString1.equalsIgnoreCase(sortedTestString2));
		
	}

}
