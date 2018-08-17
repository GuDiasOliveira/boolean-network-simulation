# Boolean Network Simulator

## Running the program

```bash
java -jar boolean-network-simulator.jar [T] < [input-file]
```

### Example

Simulating 10 steps, inputting from `sample-input.txt` file, available on this source.

```bash
java -jar boolean-network-simulator.jar 10 < sample-input.txt
```

## Input format

	# Comment lines are ignored
	[n] [k]
	functions:
		[function-1]
		[function-2]
		...
		[function-n]
	params:
		[j-params-1]
		[j-params-2]
		...
		[j-params-n]

**Where:**
- `n` is the boolean state length;
- `k` is the number of params;
- `[function-i]` is the i'th function with a sequence of 0's an 1's separated by whitespaces;
- `[j-params-i]` is the params for the i-th function with a sequence of one-based gene indexes separated by whitespaces.

**Requirements:**
- There must be `n` functions and j-params;
- Each function must have **2^k^** arguments;
- Each j-params must have `k` arguments.

There is a `sample-input.txt` file to run.

## Output format

Print for each of all possible states as the initial state (`t = 0`).

	t         g1          g2           ...    g[n]
	0        [gene]    [gene]    ...    [gene]
	1        [gene]    [gene]    ...    [gene]
	...
	[T-1]  [gene]    [gene]    ...    [gene]

**Where:**
- Each line has the boolean state, with `[gene]`'s from `g1` to `g[n]`, for its time step;
- `T` is the number of steps to simulate.