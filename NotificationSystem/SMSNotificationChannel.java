package NotificationSystem;

public class SMSNotificationChannel implements NotificationChannel {

    @Override
    public void sendNotificationViaChannel(Notification n) {
        System.out.println("Sending SMS Notificaion with mssg :: "+n.getMssg()+" to userID:: "+n.getUser() );
    }
    
}
