package edu.eci.arsw.socket;

import org.junit.Test;

public class DrawChatWebSocketConfigTest {
	
	@Test
	public void socketTest() {
		
	}
	
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
