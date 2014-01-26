package org.swordess.javamisc.xp;

public class CheckForPrime {

	static final int MAX = 1000;	// Set upper bounds.
	static final int MIN = 0;		// Set lower bounds.
	
	static int input = 0;			// Initialize input variable.
	
	public static void main(String[] args) {
		CheckForPrime check = new CheckForPrime();
		
		try {
			check.checkArgs(args);
		} catch (Exception e) {
			System.out.println("Usage: CheckForPrime x");
			System.out.println("       -- where 0<=x<=1000");
			System.exit(1);
		}
		
		if (check.primeCheck(input)) {
			System.out.println("Yippeee... " + input + " is a prime number!");
		} else {
			System.out.println("Bummer... " + input + " is NOT a prime number!");
		}
	}
	
	
	// Calculates prime numbers and compares it to the input
	public boolean primeCheck(int num) {
		double sqroot = Math.sqrt(num);
		
		// Initialize array to hold prime numbers
		boolean[] primeBucket = new boolean[MAX+1];

		// Initialize all elements to true, then set non-primes to false 
		for (int i = 2; i <= MAX; i++) {
			primeBucket[i] = true;
		}
		
		// Do all multiples of 2 first
		int j = 2;
		for (int i = j + j; i <= MAX; i = i + j) { // start with 2j as 2 is
													// prime
			primeBucket[i] = false; // set all multiples to false
		}
		
		for (j = 3; j <= sqroot; j = j + 2) {
			if (primeCheck(j) == true) {
				for (int i = j + j; i <= MAX; i = i + j) {
					primeBucket[i] = false;
				}
			}
		}
		
		return primeBucket[num];
	}
	
	// Method to validate input
	public void checkArgs(String[] args) throws Exception {
		if (args.length != 1) {
			throw new Exception();
		}

		// Get integer from character
		Integer num = Integer.valueOf(args[0]);
		input = num.intValue();
		if (input < MIN) { // If less than lower bounds
			throw new Exception();
		}
		if (input > MAX) {
			throw new Exception(); // If greater than upper bounds
		}
	}

}
