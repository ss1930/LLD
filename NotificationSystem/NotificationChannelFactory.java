package NotificationSystem;

public class NotificationChannelFactory {
    public static NotificationChannel getNotificationChannel(ChannelType type){
        // return switch(type){
        //     case SMS -> new SMSNotificationChannel();
        //     case PUSH -> new PushNotificationChannel();
        // };
        
        if (type==ChannelType.PUSH){
            return new PushNotificationChannel();
        }
        return new SMSNotificationChannel();
    }
}
