package com.ict.domain;

import lombok.Data;

// lombok을 이용하면 자동으로 getter, setter, toString을 만들 수 있습니다
// 아래와 같이 @Data를 클래스명 위에 붙이면 자동생성되고
// 좌측 package explorer에서 생성여부를 확인 가능합니다

@Data
public class TestVO {

	private String name;
	private int age;
	private int level;
	private String address;
}
