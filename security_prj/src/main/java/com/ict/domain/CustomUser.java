package com.ict.domain;

import java.util.Collection;
import java.util.stream.Collectors;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;

import lombok.Getter;


// DB에서 보안적인 검사 없이 바로 데이터를 가져올때는 VO를 그대로 활용할 수 있었으나
// 시큐리티에서든 인가된 자료만 취급할 수 있으므로
// 아래와 같이 User를 상속한 클래스를 만들고
// 멤버변수로 VO를 선언해준다음
// 생성자에서 아이디(username), 비밀번호(password), 권한(auth), 최소 이 3항목을 입력해줘야
// 추후 VO대용으로 User의 자식클래스를 활용할 수 있습니다

// private이 걸린 member를 외부에서 꺼내쓰기 위해서 @Getter만 만들어줍니다
@Getter
public class CustomUser extends User {
	
	private static final long serialVersionUID = 1L;
	
	public MemberVO member;
	
	public CustomUser(String username, String password,
			Collection<? extends GrantedAuthority> auth) {
		super(username, password, auth);
	}
	
	// CustomUser가 상속한 User클래스를 기준으로 생성자를 설정해야
	// VO와 시큐리티를 연동할 수 있습니다
	public CustomUser(MemberVO vo) {
		super(vo.getUserid(), // 첫번째 파라미터로 아이디 꺼내주기 
				vo.getUserpw(), // 두번째 파라미터로 비밀번호 꺼내주기
				
				// 세번째 파라미터는 MemberVO에 있는 List<AutoVO>를 
				// SimpleGrantedAuthority로 변환해서
				// 입력해야 셋팅이 됨
				// stream().map 은 forEach와 같이 반복문이라고 보면됨
				vo.getAuthList().stream().map(author ->
				new SimpleGrantedAuthority(author.getAuth()))
				.collect(Collectors.toList()));
		this.member = vo;
	}

}
