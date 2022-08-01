package com.jb.traps;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

public class Test {

	public static void main(String[] args) {
		// TODO Auto-generated method stub

		Map<String, String> usedYears = Collections.synchronizedMap(new HashMap<String, String>());
		usedYears.put("PALAO2011", "PALAO");
		usedYears.put("PALAO2010", "PALAO");
		usedYears.put("PALAO2009", "PALAO");

		usedYears.put("PALTIT2011", "PALAO");
		usedYears.put("PALTIT2010", "PALAO");
		usedYears.put("PALAL2009", "PALAO");
		int year = 2010;

		System.out.println("No of years : " + findMaxIncludeYear(usedYears, year, "PALAO",2));

	}

	private static int findMaxIncludeYear(Map<String, String> usedYears, int endYear, String law, int remaningYears) {
		List<String> keyP = new ArrayList<String>(usedYears.keySet());
		Iterator<String> it = keyP.iterator();
		while (it.hasNext()) {
			String value = it.next();
			if (!value.startsWith(law)) {
				it.remove();
			} 
		}
		Collections.sort(keyP);
		int maxYear = Integer.parseInt(keyP.get(keyP.size() - 1).replaceAll(law, ""));
		System.out.println("Max Year stored : " + maxYear+"  Supplied End Year "+endYear);
		int calculatedYears = Math.abs(maxYear - endYear);
		return calculatedYears>remaningYears?remaningYears:calculatedYears;
	}

}
