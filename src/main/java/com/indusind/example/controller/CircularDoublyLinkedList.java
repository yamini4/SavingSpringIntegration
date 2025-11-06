package com.indusind.example.controller;

import java.util.ArrayList;
import java.util.List;

public class CircularDoublyLinkedList {

	public enum Direction {
		CLOCKWISE, ANTICLOCKWISE
	}

	private static class Node {
		int data;
		Node next;
		Node prev;

		Node(int data) {
			this.data = data;
		}
	}

	private Node buildCircularList(int n) {
		if (n <= 0)
			return null;

		Node head = new Node(1);
		Node curr = head;
		for (int i = 2; i <= n; i++) {
			Node node = new Node(i);
			curr.next = node;
			node.prev = curr;
			curr = node;
		}
		curr.next = head;
		head.prev = curr;
		return head;
	}

	public int findJosephusPosition(int n, int k, Direction dir) {
		if (n <= 0)
			throw new IllegalArgumentException("n must be >= 1");
		if (k <= 0)
			throw new IllegalArgumentException("k must be >= 1");

		Node head = buildCircularList(n);
		Node curr = head;

		while (curr.next != curr) {
			for (int step = 1; step < k; step++) {
				curr = (dir == Direction.CLOCKWISE) ? curr.next : curr.prev;
			}

			System.out.println("Eliminating: " + curr.data);

			Node left = curr.prev;
			Node right = curr.next;
			left.next = right;
			right.prev = left;

			curr = right;
		}

		return curr.data;
	}

	// Build circular doubly linked list 1..n and return head (node with data == 1)
	private Node buildList(int n) {
		Node head = new Node(1);
		Node curr = head;
		for (int i = 2; i <= n; i++) {
			Node node = new Node(i);
			curr.next = node;
			node.prev = curr;
			curr = node;
		}
		curr.next = head;
		head.prev = curr;
		return head;
	}

	/**
	 * Return elimination order as a List<Integer>.
	 *
	 * Counting semantics: - Counting includes the node you start at (startIndex) as
	 * count = 1. - Then move in chosen direction; the k-th counted node is removed.
	 *
	 * @param n          total people
	 * @param k          step
	 * @param dir        direction of counting
	 * @param startIndex 1-based index where counting starts
	 */
	public List<Integer> getEliminationOrder(int n, int k, Direction dir, int startIndex) {
		if (n <= 0)
			throw new IllegalArgumentException("n must be >= 1");
		if (k <= 0)
			throw new IllegalArgumentException("k must be >= 1");
		if (startIndex < 1 || startIndex > n)
			throw new IllegalArgumentException("startIndex out of range");

		Node head = buildList(n);

		// move head to startIndex
		Node curr = head;
		for (int i = 1; i < startIndex; i++)
			curr = curr.next;

		List<Integer> eliminationOrder = new ArrayList<>(n);

		while (curr.next != curr) { // more than one node remains
			// Count k nodes including curr as 1
			for (int step = 1; step < k; step++) {
				curr = (dir == Direction.CLOCKWISE) ? curr.next : curr.prev;
			}

			// curr is the node to eliminate
			eliminationOrder.add(curr.data);

			// unlink curr
			Node left = curr.prev;
			Node right = curr.next;
			left.next = right;
			right.prev = left;

			// After removing 'curr', the next starting node is 'right' (the node
			// immediately after removed node)
			curr = right;
		}

		// last remaining
		eliminationOrder.add(curr.data);
		return eliminationOrder;
	}
}
