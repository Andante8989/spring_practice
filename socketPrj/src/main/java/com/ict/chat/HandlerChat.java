package com.ict.chat;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.web.socket.CloseStatus;
import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.WebSocketSession;
import org.springframework.web.socket.handler.TextWebSocketHandler;

import com.fasterxml.jackson.databind.ObjectMapper;

public class HandlerChat extends TextWebSocketHandler{
	
	// 현재 서버에 접속한 접속자 전원 리스트 받아옴
	// { room_id" : 방 id, "session" : 세션, "userId": 유저닉네임
	private List<Map<String, Object>> sessionList = new ArrayList<Map<String, Object>>();



	
	// 한 유저가 메시지를 전달하면 같은 채팅방 전체 접속자에게 전달해주는 메서드
	@Override
	protected void handleTextMessage(WebSocketSession session, TextMessage message) throws Exception {
		
		super.handleTextMessage(session, message);
		
		// json 데이터를 Map으로 변환
		ObjectMapper objectMapper = new ObjectMapper();
		Map<String, String> mapReceive = objectMapper.readValue(message.getPayload(), Map.class);
		
		switch (mapReceive.get("cmd")) {
		// 채팅창에서 전송버튼을 누르면 CMD_MSG_SEND가 보내짐
		case "CMD_MSG_SEND":
			
			for(int i = 0; i < sessionList.size(); i++) {
				Map<String, Object> mapSessionList = sessionList.get(i);
				String room_id = (String) mapSessionList.get("room_id");
				WebSocketSession sess = (WebSocketSession) mapSessionList.get("session");
				
				if (room_id.equals(mapReceive.get("room_id"))) {
					Map<String, String> mapToSend = new HashMap<String, String>();
					mapToSend.put("roon_id", room_id);
					mapToSend.put("cmd", "CMD_MSG_SEND");
					mapToSend.put("msg", session.getId() + " : " + mapReceive.get("msg"));
					
					String jsonStr = objectMapper.writeValueAsString(mapToSend);
					sess.sendMessage(new TextMessage(jsonStr));
				}
			}
			break;
		case "CMD_ENTER":
			Map<String, Object> map = new HashMap<String, Object>();
			map.put("room_id", mapReceive.get("room_id"));
			map.put("session", session);
			sessionList.add(map);
			
			// 같은 채팅방에 입장메시지 전송
			for(int i = 0; i < sessionList.size(); i++) {
				Map<String, Object> mapSessionList = sessionList.get(i);
				String room_id = (String) mapSessionList.get("room_id");
				WebSocketSession sess = (WebSocketSession) mapSessionList.get("session");
				
				
				// 입장메시지도 같은 채팅방인지 검새헛 알려줘야 하는데, 메세지 부분만 조금 달라짐
				if (room_id.equals(mapReceive.get("room_id"))) {
					Map<String, String> mapToSend = new HashMap<String, String>();
					mapToSend.put("roon_id", room_id);
					mapToSend.put("cmd", "CMD_ENTER");
					mapToSend.put("msg", session.getId() + " 님이 입장하셨습니다.");
					
					String jsonStr = objectMapper.writeValueAsString(mapToSend);
					sess.sendMessage(new TextMessage(jsonStr));
				}
			}
			break;
		}
			
		 

	} // handleTextMessage
	
	
	// 사용자가 접속 종료시 안내하는 메서드
	@Override
	public void afterConnectionClosed(WebSocketSession session, CloseStatus status) throws Exception {
		
		super.afterConnectionClosed(session, status);
		
		ObjectMapper objectMapper = new ObjectMapper();
		String current_room_id = "";
		
		// 사용자 세션을 리스트에서 제거
		for(int i = 0; i < sessionList.size(); i++) {
			Map<String, Object> map = sessionList.get(i);
			String room_id = (String) map.get("room_id");
			WebSocketSession sess = (WebSocketSession) map.get("session");
			
			if(session.equals(sess)) {
				current_room_id = room_id;
				sessionList.remove(map);
				break;
			}
		}
		
		// 같은 채팅방에 퇴장 메시지 전송
		for(int i = 0; i < sessionList.size(); i++) {
			Map<String, Object> mapSessionList = sessionList.get(i);
			String room_id = (String) mapSessionList.get("room_id");
			WebSocketSession sess = (WebSocketSession) mapSessionList.get("session");
			
			if(room_id.equals(current_room_id)) {
				Map<String, String> mapToSend = new HashMap<String, String>();
				mapToSend.put("roon_id", room_id);
				mapToSend.put("cmd", "CMD_EXIT");
				mapToSend.put("msg", session.getId() + " 님이 퇴장 했습니다.");
				
				String jsonStr = objectMapper.writeValueAsString(mapToSend);
				sess.sendMessage(new TextMessage(jsonStr));
			}
		}
	}

}
