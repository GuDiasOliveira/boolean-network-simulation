package com.github.gudiasoliveira;


public class BooleanNetwork {
	private int n, k;
	private boolean[][] x_f;
	private int[][] j_f;
	
	public BooleanNetwork(int n, int k) {
		this.n = n;
		this.k = k;
		x_f = new boolean[(int) Math.pow(2, k)][n];
		j_f = new int[k][n];
	}
	
	public int getN() {
		return this.n;
	}
	
	public int getK() {
		return this.k;
	}
	
	public void setFunction(int i, boolean... function) {
		i--; // i is 1 based index
		if (function.length != x_f.length)
			throw new IllegalArgumentException("Function size must be equal to N^2");
		for (int index = 0; index < x_f.length; index++) {
			boolean f = function[index];
			x_f[index][i] = f;
		}
	}
	
	public void setFunction(int i, int... function) {
		boolean[] f = new boolean[function.length];
		for (int index = 0; index < f.length; index++)
			f[index] = function[index] != 0;
		setFunction(i, f);
	}
	
	public void setFunction(int i, String function) {
		boolean[] f = new boolean[function.length()];
		for (int index = 0; index < f.length; index++)
			f[index] = function.charAt(index) != '0';
		setFunction(i, f);
	}
	
	public void setParams(int i, int... params) {
		i--; // i is 1 based index
		if (params.length != k)
			throw new IllegalArgumentException("Params count must be equal to K");
		for (int index = 0; index < k; index++) {
			int param = params[index];
			if (param <= 0 || param > n)
				throw new IllegalArgumentException("Param must be between 1 and N");
			j_f[index][i] = param;
		}
	}
	
	public BooleanState step(BooleanState state1) {
		BooleanState state2 = new BooleanState(n);
		for (int i = 0; i < n; i++) {
			int l = 0;
			for (int j = 0; j < k; j++) {
				l += state1.x[j_f[j][i] - 1] ? ((int) Math.pow(2, 2 - j)) : 0;
			}
			state2.x[i] = x_f[l][i];
		}
		return state2;
	}
	
	public String step(String state1) {
		return step(new BooleanState(state1)).toString();
	}
}