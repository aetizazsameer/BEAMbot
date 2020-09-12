package events;
import net.dv8tion.jda.api.entities.Member;
import net.dv8tion.jda.api.events.message.guild.GuildMessageReceivedEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;


public class BEAMevent extends ListenerAdapter {
   
   private boolean enabled = true;
   private String prefix = "b!", messagestring;
   private String[] message;
   
   
   public void onGuildMessageReceived(GuildMessageReceivedEvent event) {
      
      messagestring=event.getMessage().getContentRaw();
      message=messagestring.split(" ");
      
      Member member = event.getMember();
      
      if(!member.getUser().isBot()) {
         if(enabled) {
            if(message[0].toUpperCase().matches("☆?BEAM"))
               send(event,"☆BEAM");
            
            String[] greetings = new String[] {"Hi", "Hello", "Hey", "Howdy", "Greetings", "Sup dawg", "o/", "(´・◡・｀)/"};
            for(String s: greetings)
               if(messagestring.toUpperCase().contains(s.toUpperCase())) {
                  send(event, s+"!");
               }
            
            if(message.length>1 && (messagestring.toUpperCase().startsWith("I'M") ||
               messagestring.toUpperCase().startsWith("IM")))
                  send(event, "Hi, "+message[1]+"! I'm dad!");
            if(message.length>2 && messagestring.startsWith("I AM"))
               send(event, "Hi, "+message[2]+"! I'm dad!");
         }
         
         if(message[0].startsWith(prefix)) {
            
            if(!enabled && command("ENABLE")) {
               enabled=true;
               send(event, "☆BEAMbot enabled!");
            }
            
            if(enabled) {
               if(command("DISABLE")) {
                  enabled=false;
                  send(event, "☆BEAMbot disabled!");
               }
               
               if(command("BEAM"))
                  send(event,"https://cdn.discordapp.com/attachments/443775272320499722/750354400161300572/BEAM.jpg");
               
               if(command("LINK"))
                  send(event, "https://youtu.be/I25Cqlr5_Sc");
               
               if(command("INVITE"))
                  send(event, "https://discord.com/api/oauth2/authorize?client_id=750688770780823582&permissions=29879360&scope=bot");
               
               if(message.length>1 && command("CHANGEPREFIX")) {
                  prefix = message[1];
                  send(event, "☆BEAMbot prefix changed to `"+prefix+"`!");
               }
               
               if(command("PREFIX")) {
                  send(event, "☆BEAMbot's current prefix is `"+prefix+"`!");
               }
            }
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
