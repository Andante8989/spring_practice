package com.ict.di;

import com.ict.di.classfile.Broadcast;
import com.ict.di.classfile.Singer;
import com.ict.di.classfile.Stage;

public class DIMainJavaver {

	public static void main(String[] args) {
		// Singer를 생성해서 노래하게 만들어보세요
		Singer forestella = new Singer();
		forestella.sing();
		
		// Stage도 만들어서 공연을 시켜보세요
		Stage stage = new Stage(forestella);
		stage.perform();
		
		// Broadcast를 생성해서 방송무대를 송출해보겠습니다.
		Broadcast broad = new Broadcast(stage);
		broad.broad();
		
	}

}
