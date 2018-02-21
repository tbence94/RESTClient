package hu.pemik.dcs.restclient;

import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.concurrent.CountDownLatch;
import javax.websocket.ClientEndpoint;
import javax.websocket.CloseReason;
import javax.websocket.DeploymentException;
import javax.websocket.OnClose;
import javax.websocket.OnError;
import javax.websocket.OnMessage;
import javax.websocket.OnOpen;
import javax.websocket.Session;
import org.glassfish.tyrus.client.ClientManager;

/**
 *
 * @author pekmil
 */
@ClientEndpoint
public class WebSocketNotificationClient {
    
    private final CountDownLatch latch;
    
    public WebSocketNotificationClient(){
        this.latch = new CountDownLatch(1);
    }
    
    public void startWsClient(){
        ClientManager client = ClientManager.createClient();
        try {
            client.connectToServer(WebSocketNotificationClient.class, new URI("ws://localhost:8025/ws/notification"));
            latch.await();
 
        } catch (IOException | DeploymentException | URISyntaxException | InterruptedException e) {
            throw new RuntimeException(e);
        }
    }

    @OnOpen
    public void onOpen(Session session) {
        System.out.println("Connected ... " + session.getId());
    }
 
    @OnMessage
    public String onMessage(String message, Session session) {
        System.out.println("Received: " + message);
        return "";
    }
 
    @OnClose
    public void onClose(Session session, CloseReason closeReason) {
        System.out.println(String.format("Session %s close because of %s", session.getId(), closeReason));
        this.latch.countDown();
    }
    
    @OnError
    public void onError(Throwable t){
        System.out.println("Error: " + t.getMessage());
    }
    
}
