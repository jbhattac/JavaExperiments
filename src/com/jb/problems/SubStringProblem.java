package com.jb.problems;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;


public class SubStringProblem {
	
	
	public static int findAllSubString(String passwordText){
		List<String> password = new ArrayList<>();
		for(int i =0;i<=passwordText.length();i++){
			for(int k=i+1;k<=passwordText.length();k++){
				password.add(passwordText.substring(i,k));
			}
		}
		Optional<String> op =password.stream()
									.filter(p->p.matches(".*[A-Z].*"))
									.filter(p->!p.matches(".*\\d+.*"))
									//.forEach(System.out::println);
									.max((p,q)->p.length()-q.length());
							//
		
		return op.isPresent()?op.get().length():-1;
		
	}

	public static void main(String[] args) {
		System.out.println("For String a0Ba : "+findAllSubString("a0Ba"));
		System.out.println("For String a0bb : "+findAllSubString("a0bb"));
	}

}
