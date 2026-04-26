package NotificationSystem;

public class PushNotificationChannel implements NotificationChannel {
    @Override
    public void sendNotificationViaChannel(Notification n) {
        System.out.println("Sending Push Notificaion with mssg :: "+n.getMssg()+" to userID:: "+n.getUser() );
    }
}
