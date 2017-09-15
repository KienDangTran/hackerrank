package com.hackerrank.java.advanced.visitorpattern;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;

enum Color {
	RED, GREEN
}

abstract class Tree {

	private int value;
	private Color color;
	private int depth;

	public Tree(int value, Color color, int depth) {
		this.value = value;
		this.color = color;
		this.depth = depth;
	}

	public int getValue() {
		return value;
	}

	public Color getColor() {
		return color;
	}

	public int getDepth() {
		return depth;
	}

	public abstract void accept(TreeVis visitor);
}

class TreeNode extends Tree {

	private ArrayList<Tree> children = new ArrayList<>();

	public TreeNode(int value, Color color, int depth) {
		super(value, color, depth);
	}

	public void accept(TreeVis visitor) {
		visitor.visitNode(this);

		for (Tree child : children) {
			child.accept(visitor);
		}
	}

	public void addChild(Tree child) {
		children.add(child);
	}
}

class TreeLeaf extends Tree {

	public TreeLeaf(int value, Color color, int depth) {
		super(value, color, depth);
	}

	public void accept(TreeVis visitor) {
		visitor.visitLeaf(this);
	}
}

abstract class TreeVis {
	public abstract int getResult();

	public abstract void visitNode(TreeNode node);

	public abstract void visitLeaf(TreeLeaf leaf);

}

/**
 * return the sum of the values in the tree's leaves only.
 */
class SumInLeavesVisitor extends TreeVis {
	private int sumInLeaves = 0;

	public int getResult() {
		//implement this
		return this.sumInLeaves;
	}

	public void visitNode(TreeNode node) { }

	public void visitLeaf(TreeLeaf leaf) {
		this.sumInLeaves += leaf.getValue();
	}
}

/**
 * return the product of values stored in all red nodes, including leaves, computed modulo 10^9 + 7.
 * Note that the product of zero values is equal to 1.
 */
class ProductOfRedNodesVisitor extends TreeVis {
	private int productOfRedNodes = 0;

	public int getResult() {
		return this.productOfRedNodes;
	}

	public void visitNode(TreeNode node) {
		if (Color.RED == node.getColor()) {
			this.productOfRedNodes += node.getValue() == 0 ? 1 : node.getValue();
		}
	}

	public void visitLeaf(TreeLeaf leaf) {
		if (Color.RED == leaf.getColor()) {
			this.productOfRedNodes += leaf.getValue() == 0 ? 1 : leaf.getValue();
		}
	}
}

/**
 * return the absolute difference between the sum of values stored in the tree's non-leaf nodes at even depth and
 * the sum of values stored in the tree's green leaf nodes. Recall that zero is an even number.
 */
class FancyVisitor extends TreeVis {
	private int sumInEvenNodes = 0;//sum of values stored in the tree's non-leaf nodes at even depth
	private int sumInGreenLeaves = 0; //sum of values stored in the tree's green leaf nodes

	public int getResult() {
		return sumInGreenLeaves - sumInEvenNodes;
	}

	public void visitNode(TreeNode node) {
		if (node.getDepth() % 2 == 0) {
			sumInEvenNodes += node.getValue();
		}
	}

	public void visitLeaf(TreeLeaf leaf) {
		if (Color.GREEN == leaf.getColor()) {
			this.sumInGreenLeaves += leaf.getValue();
		}
	}
}

public class Solution {

	static int N;
	static HashMap<Integer, HashSet<Integer>> map = new HashMap<>();
	static int[] values;
	static int[] colors;
	static boolean[] mark;

	public static Tree dfs(int vertex) {
		if (map.get(vertex).isEmpty()) {
			return new TreeLeaf(values[vertex], Color.values()[colors[vertex]], 0);
		} else {
			mark = new boolean[N];
			return runDfs(vertex, 0);
		}
	}

	public static Tree runDfs(int vertex, int depth) {
		mark[vertex] = true;
		ArrayList<Tree> children = new ArrayList<>();
		for (Integer e : map.get(vertex)) { if (!mark[e]) { children.add(runDfs(e, depth + 1)); } }
		if (children.isEmpty()) { return new TreeLeaf(values[vertex], Color.values()[colors[vertex]], depth); } else {
			TreeNode node = new TreeNode(values[vertex], Color.values()[colors[vertex]], depth);
			for (Tree child : children) { node.addChild(child); }
			return node;
		}
	}

	public static Tree solve() throws IOException {
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		N = Integer.valueOf(br.readLine());
		values = new int[N];
		colors = new int[N];
		int parent, child;
		String[] chunks = br.readLine().split(" ");
		for (int i = 0; i < N; i++) {
			map.put(i, new HashSet<Integer>());
			values[i] = Integer.valueOf(chunks[i]);
		}
		chunks = br.readLine().split(" ");
		for (int i = 0; i < N; i++) { colors[i] = Integer.valueOf(chunks[i]); }
		for (int i = 0, length = N - 1; i < length; i++) {
			chunks = br.readLine().split(" ");
			parent = Integer.valueOf(chunks[0]) - 1;
			child = Integer.valueOf(chunks[1]) - 1;

			map.get(parent).add(child);
			map.get(child).add(parent);

		}
		return dfs(0);
	}

	public static void main(String[] args) throws IOException {
		Tree root = solve();
		SumInLeavesVisitor vis1 = new SumInLeavesVisitor();
		ProductOfRedNodesVisitor vis2 = new ProductOfRedNodesVisitor();
		FancyVisitor vis3 = new FancyVisitor();

		root.accept(vis1);
		root.accept(vis2);
		root.accept(vis3);

		int res1 = vis1.getResult();
		int res2 = vis2.getResult();
		int res3 = vis3.getResult();

		System.out.println(res1);
		System.out.println(res2);
		System.out.println(res3);
	}
}