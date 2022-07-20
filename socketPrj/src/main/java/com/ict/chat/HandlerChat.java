package com.ict.chat;

import java.util.ArrayList;
import java.util.List;

import org.springframework.web.socket.CloseStatus;
import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.WebSocketSession;
import org.springframework.web.socket.handler.TextWebSocketHandler;

public class HandlerChat extends TextWebSocketHandler{
	
	// 현재 서버에 접속한 접속자 전원 리스트 받아옴
	private List<WebSocketSession> sessionList = new ArrayList<WebSocketSession>();
	
	// 사용자가 신규 연결될 때마다 작동하는 메서드
	@Override
	public void afterConnectionEstablished(WebSocketSession session) throws Exception {
		
		// 채팅방에 새로 접속한 사용자 세션을 리스트에 추가
		sessionList.add(session);
		System.out.println(sessionList);
		
		// 모든 세션에 채팅 전달
		for(int i = 0; i < sessionList.size(); i++) {
			WebSocketSession s = sessionList.get(i);
			s.sendMessage(new TextMessage(session.getId() + "님이 입장 했습니다."));
		}
	} // afterConnection
	
	// 한 유저가 메시지를 전달하면 같은 채팅방 전체 접속자에게 전달해주는 메서드
	@Override
	protected void handleTextMessage(WebSocketSession session, TextMessage message) throws Exception {
		
		// 모든 세션에 채팅전달
		for (int i = 0; i < sessionList.size(); i++) {
			// 전체 접속자의 목록인 sessionList 에서 한명한명의 정보를 변수 s에 저장
			WebSocketSession s = sessionList.get(i);
			// 해당 한명한명의 유저에게 메시지 전송
			s.sendMessage(new TextMessage(session.getId() + " : " + message.getPayload()));
			System.out.println(message);
		}
	} // handleTextMessage
	
	@Override
	public void afterConnectionClosed(WebSocketSession session, CloseStatus status) throws Exception {
		
		// 채팅방에서 퇴장한 사용자 세션을 리스트에서 제거
		sessionList.remove(session);
		
		// 모든 세션에 채팅 전달
		for(int i = 0; i < sessionList.size(); i++) {
			WebSocketSession s = sessionList.get(i);
			s.sendMessage(new TextMessage(session.getId() + "님이 퇴장 했습니다."));
		}
	}

}
