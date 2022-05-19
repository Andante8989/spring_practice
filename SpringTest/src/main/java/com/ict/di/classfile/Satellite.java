package com.ict.di.classfile;

import org.springframework.stereotype.Component;

@Component
public class Satellite {

	private Broadcast broadcast;
		
	public Satellite(Broadcast broadcast) {
		this.broadcast = broadcast;
	}
	
	public void satelliteBroad() {
		System.out.print("위성 ");
		this.broadcast.broad();
	}
}
