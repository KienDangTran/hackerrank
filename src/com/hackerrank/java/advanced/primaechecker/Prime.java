package com.hackerrank.java.advanced.primaechecker;

class Prime {
	void checkPrime(int... n) {
		for (int number : n) {
			if (isPrime(number)) {
				System.out.print(number + " ");
			}
		}
		System.out.println();
	}

	private boolean isPrime(int n) {
		if (n <= 1) { return false; }
		if (n <= 3) { return true; }
		if (n % 2 == 0 || n % 3 == 0) { return false; }

		int i = 5;
		while (i * i <= n) {
			if (n % i == 0 || n % (i + 2) == 0) { return false; }
			i += 6;
		}
		return true;
	}
}
