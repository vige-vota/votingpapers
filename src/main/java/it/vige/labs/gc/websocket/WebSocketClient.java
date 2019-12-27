package it.vige.labs.gc.websocket;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.messaging.converter.MappingJackson2MessageConverter;
import org.springframework.messaging.simp.stomp.StompSession;
import org.springframework.messaging.simp.stomp.StompSessionHandlerAdapter;
import org.springframework.stereotype.Service;
import org.springframework.web.socket.client.standard.StandardWebSocketClient;
import org.springframework.web.socket.messaging.WebSocketStompClient;

import it.vige.labs.gc.JavaAppApplication;

@Service
public class WebSocketClient {

	private WebSocketStompClient stompClient = new WebSocketStompClient(new StandardWebSocketClient());

	private StompSession stompSession;

	@Value("${server.port}")
	private int serverPort;

	@Value("${websocket.scheme}")
	private String websocketScheme;

	@Value("${votingpapers.host}")
	private String votingpapersHost;

	private void connect() throws Exception {
		stompClient.setMessageConverter(new MappingJackson2MessageConverter());
		stompSession = stompClient
				.connect(websocketScheme + "://" + votingpapersHost + ":" + serverPort + JavaAppApplication.BROKER_NAME,
						new StompSessionHandlerAdapter() {
						})
				.get();
	}

	public StompSession getStompSession() throws Exception {
		if (stompSession == null)
			connect();
		return stompSession;
	}
}
