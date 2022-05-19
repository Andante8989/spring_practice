package com.ict.di.classfile;

import org.springframework.stereotype.Component;

@Component
public class Sink extends Singer {
	
	@Override
	public void sing() {
		System.out.println("신케이가 노래를 합니다.");
	}
}
