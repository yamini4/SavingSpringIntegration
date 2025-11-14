package com.indusind.example.service;

public class StringRotateTest {

	public static boolean stringRotateTest(String input1, String input2) {
//		String input1 = "miniay";
//		String input2 = "yami";

		boolean flag = false;
		if (input1.length() != input2.length()) {
			flag = false;
		} else {
			for (int i = 0; i < input2.length(); i++) {
				if (input1.startsWith(input2.substring(i, input2.length()))) {
					String sample = input2.substring(0, i);
					if (input1.endsWith(sample)) {
						flag = true;
						break;
					}
				}
			}
		}

		System.out.println(flag);
		return flag;
	}
}
