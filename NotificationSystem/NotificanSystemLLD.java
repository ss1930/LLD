// https://excalidraw.com/#json=YMB1H2dsf1HPJ3HReFbD1,4NcLVpfByqnJn5U6qR8Ulw
package NotificationSystem;

import java.util.Set;

public class NotificanSystemLLD {
    public static void main(String[] args) {
        AsyncNotficationService ans=new AsyncNotficationService();
        ans.sendNotification(new Notification("2", "Hoola"));
        System.out.println("---");
        NotificationService ns=new NotificationService();

        // UserPrefrenceService.getInstance().savePrefrence(new UserPrefrence("1", Set.of(ChannelType.PUSH)));

        ns.sendNotification(new Notification("1", "Hello"));

        
    }
}
