package NotificationSystem;

public class Notification {
    private String mssg;
    private String userId;
    private ChannelType type;// for future we want to see this nottification went via which channek

    public Notification(String userID, String mssg){
        this.mssg=mssg;
        this.userId=userID;
    }

    public String getMssg() {
        return mssg;
    }

    public String getUser() {
        return userId;
    }


}
