package com.ict.domain;

import java.util.Scanner;

public class practice {

	public static void main(String[] args) {
		Scanner scan = new Scanner(System.in);
		System.out.println("돈을 넣어주세요 : ");
		int money = scan.nextInt();
		int coffee = 200;
		if(money < coffee) {
			System.out.println("요금이 부족합니다");
		} else {
			System.out.println("몇잔을 원하나요? : ");
			int jan = scan.nextInt();
			int totalmoney = coffee * jan;
			if (money % coffee == 0) {
				System.out.println(jan + "잔");
			} else {
				System.out.println(jan + "잔");
				System.out.println(money - totalmoney + "원 반납");
			}
		}
		
		
		
		
		
	}

}
