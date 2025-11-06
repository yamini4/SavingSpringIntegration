package com.indusind.example.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.integration.annotation.IntegrationComponentScan;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.indusind.example.service.ProcessGateway;

@RestController
@RequestMapping("/api")
@IntegrationComponentScan("com.indusind.example.service")
public class ProcessController {

	@Autowired
	private ProcessGateway gateway;

	@PostMapping("/process")
	public String process(@RequestParam String name) {
		System.out.println("REST call received for name = " + name);
		String result = gateway.processName(name);
		System.out.println("Gateway result = " + result);
		return result;
	}

	@GetMapping("/josephSolution")
	public void josephSolution() {
		int n = 100;
		int k = 2;

		CircularLinkedList list = new CircularLinkedList();
		int survivor = list.findJosephusPosition(n, k);

		System.out.println("The survivor is at position using Circular Linked List : " + survivor);

		System.out.println("The survivor is at position using Recurtion : " + solution(100, 2));
		// using doubly linked List
		CircularDoublyLinkedList listDoubleSide = new CircularDoublyLinkedList();

		System.out.println("Clockwise elimination:");
		int survivorClockwise = listDoubleSide.findJosephusPosition(n, k, CircularDoublyLinkedList.Direction.CLOCKWISE);
		System.out.println("Survivor (clockwise): " + survivorClockwise);
		System.out.println();

		System.out.println("Anticlockwise elimination:");
		int survivorAnti = listDoubleSide.findJosephusPosition(n, k, CircularDoublyLinkedList.Direction.ANTICLOCKWISE);
		System.out.println("Survivor (anticlockwise): " + survivorAnti);

		System.out.println("Clockwise (start at " + 1 + "):");
		System.out.println(listDoubleSide.getEliminationOrder(n, k, CircularDoublyLinkedList.Direction.CLOCKWISE, 1));
		System.out.println();

		System.out.println("Anticlockwise (start at " + 1 + "):");
		System.out
				.println(listDoubleSide.getEliminationOrder(n, k, CircularDoublyLinkedList.Direction.ANTICLOCKWISE, 1));

	}

	static int solution(int n, int k) {
		if (n == 1)
			return 1;
		else
			return (solution(n - 1, k) + k - 1) % n + 1;
	}

}
