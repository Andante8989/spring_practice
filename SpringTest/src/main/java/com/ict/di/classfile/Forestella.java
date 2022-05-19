package com.ict.di.classfile;

import org.springframework.stereotype.Component;

@Component
public class Forestella extends Singer {
	
	@Override
	public void sing() {
		System.out.println("포레스텔라가 노래를 합니다.");
	}
}
