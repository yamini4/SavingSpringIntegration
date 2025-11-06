package com.indusind.example.controller;

class CircularLinkedList {

	private static class Node {
		int data;
		Node next;

		Node(int data) {
			this.data = data;
		}
	}

	// Function to find Josephus survivor
	public int findJosephusPosition(int n, int k) {
		// Step 1: Create circular linked list
		Node head = new Node(1);
		Node prev = head;
		for (int i = 2; i <= n; i++) {
			prev.next = new Node(i);
			prev = prev.next;
		}
		prev.next = head; // make circular

		// Step 2: Start elimination process
		Node ptr = head;
		Node prevPtr = prev;

		while (ptr.next != ptr) { // until one remains
			// move (k - 1) steps
			for (int count = 1; count < k; count++) {
				prevPtr = ptr;
				ptr = ptr.next;
			}

			// Eliminate current node
			// System.out.println("Eliminating: " + ptr.data);

			// remove ptr
			prevPtr.next = ptr.next;
			ptr = ptr.next; // move to next node
		}

		// Step 3: Last node is survivor
		return ptr.data;
	}
}
