package com.hackerrank.datastructure.array.sparsearrays;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Arrays;

public class Solution {
	public static void main(String[] args) {
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		try {
			int n = Integer.parseInt(br.readLine());
			String[] str = new String[n];
			for (int i = 0; i < n; i++) {
				str[i] = br.readLine();
			}

			int q = Integer.parseInt(br.readLine());
			for (int i = 0; i < q; i++) {
				String query = br.readLine();
				System.out.println(Arrays.stream(str).filter(s -> s.equals(query)).count());
			}

		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}