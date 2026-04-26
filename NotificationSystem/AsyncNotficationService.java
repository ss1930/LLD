package NotificationSystem;

import java.util.concurrent.Executors;
import java.util.concurrent.ExecutorService;

public class AsyncNotficationService {
    private final ExecutorService ex=Executors.newFixedThreadPool(10);
    
    public void sendNotification(Notification n){
        ex.submit(()-> NotificationDispatcher.getInstance().dispatchNotification(n));
    }
}
