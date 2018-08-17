package com.github.gudiasoliveira;


public class BooleanState {
	public boolean[] x;
	
	public BooleanState(int states) {
		x = new boolean[states];
	}
	
	public BooleanState(String state) {
		x = new boolean[state.length()];
		int i = 0;
		for (char bit : state.toCharArray()) {
			x[i] = bit != '0';
			i++;
		}
	}
	
	@Override
	public String toString() {
		String str = "";
		for (boolean xi : x)
			str += xi ? "1" : "0";
		return str;
	}
}