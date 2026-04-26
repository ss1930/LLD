package NotificationSystem;

import java.util.Set;

public class NotificationDispatcher {
    private NotificationDispatcher(){}

    //ThreadSafe
    private static NotificationDispatcher instance;
    public static NotificationDispatcher getInstance(){
        if(instance==null){
            synchronized(NotificationDispatcher.class){
                if(instance==null){
                    instance=new NotificationDispatcher();
                }
            }
        }

        return instance;
    }

    public void dispatchNotification(Notification n){
        UserPrefrenceService userPrefrenceService=UserPrefrenceService.getInstance();
        UserPrefrence prefrences=userPrefrenceService.getUserPrefrence(n.getUser());

        Set<ChannelType> channels=prefrences.getUserPrefrence();

        for (ChannelType chanel: channels){
            NotificationChannel notificationChannel=NotificationChannelFactory.getNotificationChannel(chanel);

            notificationChannel.sendNotificationViaChannel(n);
        }
    }
}
