package com.ict.di.classfile;



import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;

@Component
public class Stage {
	
		// 빈 컨테이너 내부에서 자동으로 Stage 내부에 Singer를 대입해줌.
		//@Autowired
		//@Qualifier
		//@Resource(name="forestella")
		private Singer singer; // 무대에 서는 가수
		
		// Qualifier를 쓸거라면 아무것도 입력받지 않고 아무실행도 하지 않는 생성자를 추가해야합니다.
		public Stage() {
			
		}
		
		// 무대는 가수가 있어야 성립합니다.
		//public Stage(Singer singer) {
		//	this.singer = singer; // 무대에 설 가수를 입력해야 생성자 실행이 가능하게 처리
		//}
		
		// setter를 이용해 의존성 주입을 해 줄 수도 있다.
		public void setSinger(Singer singer) {
			this.singer = singer;
		}
		
		public void perform() {
			System.out.print("무대에서 ");
			this.singer.sing();
		}
	}
