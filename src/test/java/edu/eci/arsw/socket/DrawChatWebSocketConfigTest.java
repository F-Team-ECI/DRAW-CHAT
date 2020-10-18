package edu.eci.arsw.socket;

import java.util.concurrent.BlockingQueue;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.LinkedBlockingDeque;
import java.util.concurrent.TimeoutException;

import org.junit.Before;
import org.junit.FixMethodOrder;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.MethodSorters;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.messaging.simp.stomp.StompFrameHandler;
import org.springframework.messaging.simp.stomp.StompHeaders;
import org.springframework.messaging.simp.stomp.StompSession;
import org.springframework.messaging.simp.stomp.StompSessionHandlerAdapter;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.annotation.DirtiesContext.ClassMode;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.web.socket.client.standard.StandardWebSocketClient;
import org.springframework.web.socket.messaging.WebSocketStompClient;
import org.springframework.web.socket.sockjs.client.SockJsClient;
import org.springframework.web.socket.sockjs.client.WebSocketTransport;
import static java.util.Arrays.asList;
import static java.util.concurrent.TimeUnit.SECONDS;

import edu.eci.arsw.application.DrawChatApp;
import junit.framework.Assert;

import java.lang.reflect.Type;


@RunWith(SpringRunner.class)
@SpringBootTest(classes = DrawChatApp.class, webEnvironment = WebEnvironment.RANDOM_PORT)
@ActiveProfiles("test")
@FixMethodOrder(MethodSorters.NAME_ASCENDING)
@DirtiesContext(classMode = ClassMode.AFTER_CLASS)

public class DrawChatWebSocketConfigTest {
	/**
	@LocalServerPort
	private Integer port;
	
	//static final String WEBSOCKET_URI = "ws://localhost:8080/stompendpoint";
    static final String WEBSOCKET_TOPIC = "/topic";
    
    BlockingQueue<String> blockingQueue;
    WebSocketStompClient stompClient;
    
    @Before
    public void setup() {
    	blockingQueue = new LinkedBlockingDeque<>();
        stompClient = new WebSocketStompClient(new SockJsClient(asList(new WebSocketTransport(new StandardWebSocketClient()))));
    }
	
	@Test
	public void shouldReceiveAMessageFromTheServer() throws InterruptedException, ExecutionException, TimeoutException {
		StompSession session = stompClient
                .connect(String.format("ws://localhost:%d/ws-endpoint", port), new StompSessionHandlerAdapter() {})
                .get(1, SECONDS);
		session.subscribe(WEBSOCKET_TOPIC, new DefaultStompFrameHandler());
		
		String mensaje = "Mensaje de prueba";
		session.send(WEBSOCKET_TOPIC, mensaje.getBytes());
		
		Assert.assertEquals(mensaje, blockingQueue.poll(1,SECONDS));
	}
	
	class DefaultStompFrameHandler implements StompFrameHandler {
        @Override
        public Type getPayloadType(StompHeaders stompHeaders) {
            return byte[].class;
        }

        @Override
        public void handleFrame(StompHeaders stompHeaders, Object o) {
            blockingQueue.offer(new String((byte[]) o));
        }
    }
    */

}
