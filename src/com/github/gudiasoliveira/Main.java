package com.github.gudiasoliveira;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Main {
	
	private static BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));

	public static void main(String[] args) {
		String inputLine = readInput();
		
		// Validating empty input
		if (inputLine == null) {
			System.err.println("Empty input!");
			System.exit(1);
			return;
		}
		
		BooleanNetwork boolNet;
		
		// Getting n and k
		String inputs[] = inputLine.split("\\s+");
		if (inputs.length < 2) {
			System.err.println("Invalid input! Missing k param!");
			System.exit(1);
			return;
		}
		try {
			boolNet = new BooleanNetwork(Integer.parseInt(inputs[0]), Integer.parseInt(inputs[1]));
		} catch (NumberFormatException e) {
			System.err.println("Invalid input! Invalid n and/or k param input!");
			System.exit(1);
			return;
		}
		
		inputLine = readInput();
		// Validating functions section expected
		if (!inputLine.equalsIgnoreCase("functions:")) {
			System.err.println("Invalid input! \"functions\" section expected!");
			System.exit(1);
			return;
		}
		
		// Reading boolean functions
		int funcCount = 0;
		inputLine = readInput();
		do {			
			// Validating missing params section
			if (inputLine == null) {
				System.err.println("Invalid input! Missing \"params\" section!");
				System.exit(1);
				return;
			}
			
			inputs = inputLine.split("\\s+");
			// Validating function input
			if (inputs.length != ((int) Math.pow(2, boolNet.getK()))) {
				System.err.println("Invalid input! Function must have 2^k params!");
				System.exit(1);
				return;
			}
			boolean[] f = new boolean[inputs.length];
			for (int i = 0; i < f.length; i++) {
				switch (inputs[i]) {
				case "0":
					f[i] = false;
					break;
				case "1":
					f[i] = true;
					break;
				default:
					System.err.println("Invalid function input!");
					System.exit(1);
					return;
				}
			}
			// Setting function
			boolNet.setFunction(++funcCount, f);
			inputLine = readInput();
		} while(!"params:".equalsIgnoreCase(inputLine));
		
		// Validate n funtions
		if (funcCount != boolNet.getN()) {
			System.err.println("Invalid input! There must be n functions!");
			System.exit(1);
			return;
		}
		
		// Inputting the j-params
		funcCount = 0;
		inputLine = readInput();
		do {
			inputs = inputLine.split("\\s+");
			// Validating j-params input
			if (inputs.length != boolNet.getK()) {
				System.err.println("Invalid input! J-params must have k params!");
				System.exit(1);
				return;
			}
			int[] params = new int[inputs.length];
			for (int i = 0; i < params.length; i++) {
				try {
					params[i] = Integer.parseInt(inputs[i]);
				} catch (NumberFormatException e) {
					System.err.println("Invalid j-params input!");
					System.exit(1);
					return;
				}
			}
			boolNet.setParams(++funcCount, params);
			inputLine = readInput();
		} while(inputLine != null);
		
		// Validate n params
		if (funcCount != boolNet.getN()) {
			System.err.println("Invalid input! There must be n j-params!");
			System.exit(1);
			return;
		}
		
//		// Inputting initial state
//		inputLine = readInput();
//		inputs = inputLine.split("\\s+");
//		// Validating initial state input
//		if (inputs.length != boolNet.getN()) {
//			System.err.println("Invalid input! Initial state must have n genes!");
//			System.exit(1);
//			return;
//		}
//		BooleanState currentState = new BooleanState(boolNet.getN());
//		for (int i = 0; i < currentState.x.length; i++) {
//			switch (inputs[i]) {
//			case "0":
//				currentState.x[i] = false;
//				break;
//			case "1":
//				currentState.x[i] = true;
//				break;
//			default:
//				System.err.println("Invalid initial state input!");
//				System.exit(1);
//				return;
//			}
//		}
		int t = Integer.parseInt(args[0]);
		for (int i = 0; i < ((int) Math.pow(2, boolNet.getN())); i++) {
			String initialState = String.format("%" + boolNet.getN() + "s", Integer.toBinaryString(i)).replace(" ", "0");
			BooleanState state = new BooleanState(initialState);
			for (int j = 0; j < t; j++) {
				System.out.println(state);
				state = boolNet.step(state);
			}
			System.out.println();
		}
	}
	
	private static String readInput() {
		String line;
		do {
			try {
				line = reader.readLine();
				if (line == null)
					return null;
				line = line.trim();
			} catch (IOException e) {
				return null;
			}
		} while (line.isEmpty() || line.startsWith("#"));
		return line;
	}
}