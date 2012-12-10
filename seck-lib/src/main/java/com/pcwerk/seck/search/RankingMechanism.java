package com.pcwerk.seck.search;

import java.util.HashMap;

public interface RankingMechanism {
	
	//This will take input parameters.  Haven't decided yet.
	public HashMap<String, Double> rank(Object o);
}
