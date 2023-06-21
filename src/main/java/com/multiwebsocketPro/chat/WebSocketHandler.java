package com.multiwebsocketPro.chat;

import java.io.IOException;
import java.util.Collections;
import java.util.HashSet;
import java.util.Set;

import javax.websocket.OnClose;
import javax.websocket.OnError;
import javax.websocket.OnMessage;
import javax.websocket.OnOpen;
import javax.websocket.Session;
import javax.websocket.server.ServerEndpoint;

import org.springframework.stereotype.Component;

//클라이언트가 접속할떄마다 생성되고 클라이언트와 직접 통신할 수 있는 클래스
//웹소켓을 사용하면서 이벤트가 발생될 때마다 실행될 수 있도록 처리
@Component
@ServerEndpoint(value="/chat")
public class WebSocketHandler {
	// 웹소켓 클라이언트의 접속요청이 들어오면 호출되는 메소드
	// -> 웹소켓을 접속하기 위한 명령을 실행하는 클라이언트
	// 세션을 저장하기 위한 컬렉션을 추가
	// Set인데 동기화된 컬렉션 - 멀티스레드 환경에서 안전하게 작업할 수 있도록 제공되는 Set
	private static Set<Session> clientset = Collections.synchronizedSet(new HashSet<Session>());
	
	@OnOpen
	public void onOpen(Session client) {
		if ( !clientset.contains(client)) {
			clientset.add(client);
			System.out.println("클라이언트 접속 : " + client);
		}
	}
	
	//메시지를 받으면 - 모든 접속자들에게 메세지를 전송
	@OnMessage
	public void onMessage(String msg, Session client) throws IOException {
		System.out.println("수신메시지 : " + msg);
		//웹소켓에 접속한 모든 웹소켓 클라이언트에게 메세지 전송
		for (Session data : clientset) {
			System.out.println("전송메세지 : " + msg);
			data.getBasicRemote().sendText(msg);
		}
	}
	//연결이 끊어지면
	@OnClose
	public void onClose(Session client) {
		System.out.println("접속 종료 : " + client);
		clientset.remove(client);
	}
	
	//오류가 발생하면
	//@OnError
	
}
