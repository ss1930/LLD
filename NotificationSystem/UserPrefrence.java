package NotificationSystem;

import java.util.Set;

public class UserPrefrence {
    private String userId;
    private Set<ChannelType> prefrences;

    public UserPrefrence(String id, Set<ChannelType> prefrences){
        this.userId=id;
        this.prefrences=prefrences;
    }

    public Set<ChannelType> getUserPrefrence(){
        return prefrences;
    }

    public String getUser() {
       return userId;
    }
}
