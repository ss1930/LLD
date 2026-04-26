package NotificationSystem;

import java.util.HashMap;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;


public class UserPrefrenceService {
    private ConcurrentHashMap<String,UserPrefrence> userPrefrences=new ConcurrentHashMap<>();

    private UserPrefrenceService(){}

    //ThreadSafe
    private static UserPrefrenceService instance;
    public static UserPrefrenceService getInstance(){
        if(instance==null){
            synchronized(UserPrefrenceService.class){
                if(instance==null){
                    instance=new UserPrefrenceService();
                }
            }
        }

        return instance;
    }

    public void savePrefrence(UserPrefrence up){
        userPrefrences.put(up.getUser(),up);
    }

    public UserPrefrence getUserPrefrence(String userId){
        return userPrefrences.getOrDefault(userId, new UserPrefrence(userId, Set.of(ChannelType.SMS)));
    }
}
