package NotificationSystem;

public class NotificationService {
    public void sendNotification(Notification n){
        NotificationDispatcher.getInstance().dispatchNotification(n);
    }
}
