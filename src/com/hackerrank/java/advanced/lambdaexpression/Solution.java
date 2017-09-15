package com.hackerrank.java.advanced.lambdaexpression;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Arrays;
import java.util.StringTokenizer;
import java.util.stream.IntStream;
import java.util.stream.Stream;

interface PerformOperation {
	boolean check(int a);
}

class MyMath {
	public static boolean checker(PerformOperation p, int num) {
		return p.check(num);
	}

	public PerformOperation isOdd() {
		return n -> n % 2 == 1;
	}

	public PerformOperation isPrime() {
		return n -> {
			if (n <= 1) { return false; }
			if (n <= 3) { return true; }
			if (n % 2 == 0 || n % 3 == 0) { return false; }

			int i = 5;
			while (i * i <= n) {
				if (n % i == 0 || n % (i + 2) == 0) { return false; }
				i += 6;
			}
			return true;
		};
	}

	public PerformOperation isPalindrome() {
		return n -> {
			char[] chunks = String.valueOf(n).toCharArray();
			if (chunks.length % 2 == 0) { return false; }

			for (int i = 0; i < chunks.length / 2; i++) {
				if (chunks[i] != chunks[chunks.length - 1]) {
					return false;
				}
			}

			return true;
		};
	}
}


public class Solution {

	public static void main(String[] args) throws IOException {
		MyMath ob = new MyMath();
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		int T = Integer.parseInt(br.readLine());
		PerformOperation op;
		boolean ret = false;
		String ans = null;
		while (T-- > 0) {
			String s = br.readLine().trim();
			StringTokenizer st = new StringTokenizer(s);
			int ch = Integer.parseInt(st.nextToken());
			int num = Integer.parseInt(st.nextToken());
			if (ch == 1) {
				op = ob.isOdd();
				ret = ob.checker(op, num);
				ans = (ret) ? "ODD" : "EVEN";
			} else if (ch == 2) {
				op = ob.isPrime();
				ret = ob.checker(op, num);
				ans = (ret) ? "PRIME" : "COMPOSITE";
			} else if (ch == 3) {
				op = ob.isPalindrome();
				ret = ob.checker(op, num);
				ans = (ret) ? "PALINDROME" : "NOT PALINDROME";

			}
			System.out.println(ans);
		}
	}
}
