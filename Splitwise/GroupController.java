package Splitwise;

import java.util.HashMap;

public class GroupController {
    //SingleTon

    private GroupController(){}

    //Thread Safe
    private static volatile GroupController instance;
    
    public static GroupController getInstance(){
        if(instance==null){
            synchronized(GroupController.class){
                if(instance==null){
                    instance=new GroupController();
                }
            }
        }
        return instance;
    }
    HashMap<String,Group> groups=new HashMap<>();///all groups
    public void  createGroup(String id,String name,User u){
        Group g=new Group(id);
        g.setName(name);
        g.addMember(u);

        groups.put(id,g);
    }

    public Group getGroup(String id){
        return groups.get(id);
    }


}
