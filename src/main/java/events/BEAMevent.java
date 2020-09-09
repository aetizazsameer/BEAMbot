package events;
import net.dv8tion.jda.api.entities.Member;
import net.dv8tion.jda.api.events.message.guild.GuildMessageReceivedEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;

public class BEAMevent extends ListenerAdapter {
   
   private boolean enabled = true;
   private String prefix = "!";
   private String[] message;
   
   public void onGuildMessageReceived(GuildMessageReceivedEvent event) {
      
      message=event.getMessage().getContentRaw().split(" ");
      Member member = event.getMember();
      
      if(!member.getUser().isBot()) {
         
         if(enabled && message[0].toUpperCase().matches("☆?BEAM"))
            send(event,"☆BEAM");
         
         if(message[0].startsWith(prefix)) {
            if(!enabled && command("BEAMENABLE")) {
               enabled=true;
               send(event, "☆BEAMbot enabled!");
            }
            
            if(enabled && command("BEAMDISABLE")) {
               enabled=false;
               send(event, "☆BEAMbot disabled!");
            }
            
            if(enabled && command("BEAM"))
              send(event,"https://cdn.discordapp.com/attachments/443775272320499722/750354400161300572/BEAM.jpg");
         }
      }
   }
   
   private boolean command(String command) {
      return message[0].toUpperCase().substring(prefix.length()).equalsIgnoreCase(command);
   }
   
   private void send(GuildMessageReceivedEvent event, String message) {
      event.getChannel().sendMessage(message).queue();
   }
}
