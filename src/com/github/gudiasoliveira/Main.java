package com.github.gudiasoliveira;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Main {
	
	private static BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
	private static boolean endFlag;

	public static void main(String[] args) {
		endFlag = args.length >= 2 && args[1].equals("--end-flag");
		
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
		if (inputLine == null) {
			System.err.println("Invalid input! Missing \"functions\" and \"params\" sections!");
			System.exit(1);
			return;
		}
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
			// Validate n funtions
			if (funcCount >= boolNet.getN()) {
				System.err.println("Invalid input! There must be n functions!");
				System.exit(1);
				return;
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
		while((inputLine = readInput()) != null) {
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
			// Validate n params
			if (funcCount >= boolNet.getN()) {
				System.err.println("Invalid input! There must be n j-params!");
				System.exit(1);
				return;
			}
			// Validate params between 1 and N
			for (int param : params) {
				if (param <= 0 || param > boolNet.getN()) {
					System.err.println("Invalid params input! Param must be between 1 and N");
					System.exit(1);
					return;
				}
			}
			// Setting params
			boolNet.setParams(++funcCount, params);
		}
		
		// Validate n params
		if (funcCount != boolNet.getN()) {
			System.err.println("Invalid input! There must be n j-params!");
			System.exit(1);
			return;
		}
		
		// Processing simulation
		int t = Integer.parseInt(args[0]);
		StringBuilder headerSb = new StringBuilder("t");
		for (int i = 0; i < boolNet.getN(); i++)
			headerSb.append("\tg").append(i + 1);
		String header = headerSb.toString();
		for (int i = 0; i < (1 << boolNet.getN()); i++) {
			System.out.println(header);
			String initialState = String.format("%" + boolNet.getN() + "s", Integer.toBinaryString(i)).replace(" ", "0");
			BooleanState state = new BooleanState(initialState);
			for (int j = 0; j < t; j++) {
				System.out.print(j);
				for (boolean x : state.x)
					System.out.print(x ? "\t1" : "\t0");
				System.out.println();
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
				if (endFlag) {
					if (line.matches("#\\s*(?i:end)"))
						return null;
				}
			} catch (IOException e) {
				return null;
			}
		} while (line.isEmpty() || line.startsWith("#"));
		return line;
	}
}