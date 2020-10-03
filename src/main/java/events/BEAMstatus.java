package events;
import net.dv8tion.jda.api.OnlineStatus;
import net.dv8tion.jda.api.entities.Activity;
import net.dv8tion.jda.api.events.ReadyEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;

public class BEAMstatus extends ListenerAdapter {
   public void onReady(ReadyEvent event) {
      
      event.getJDA().getPresence().setStatus(OnlineStatus.DO_NOT_DISTURB);
      event.getJDA().getPresence().setActivity(Activity.listening("☆BEAM"));
      
   }
}
