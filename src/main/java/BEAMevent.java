package events;
import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.entities.Member;
import net.dv8tion.jda.api.events.message.guild.GuildMessageReceivedEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;
import java.awt.*;

public class BEAMevent extends ListenerAdapter {
   
   private boolean enabled = true;
   private String prefix = "b!";
   private String[] message;
   
   public void onGuildMessageReceived(GuildMessageReceivedEvent event) {
      
      String messagestring=event.getMessage().getContentRaw();
      message=messagestring.split(" ");
      
      Member member = event.getMember();
      if(!member.getUser().isBot()) {
         if(enabled) {
            if(message[0].toUpperCase().matches("☆?BEAM"))
               send(event,"☆BEAM");
            
            String[] greetings = new String[] {"Hi", "Hello", "Hey", "Howdy", "Greetings", "o/", "(´・◡・｀)/"};
            for(int i=0; i<message.length; i++) {
               if(message[i].length()<2 || message[i].length()>8)
                  continue;
               if(i+1<message.length && message[i].equalsIgnoreCase("Sup") && message[i+1].equalsIgnoreCase("dawg"))
                  send(event, "Sup dawg!");
               for(String greeting: greetings)
                  if(messagestring.equalsIgnoreCase(greeting))
                     send(event, greeting+"!");
            }
            
            if(message.length>1 && (messagestring.toUpperCase().startsWith("I'M") ||
               messagestring.toUpperCase().startsWith("IM ")))
                  send(event, "Hi, "+message[1]+"! I'm dad!");
            if(message.length>2 && messagestring.startsWith("I AM "))
               send(event, "Hi, "+message[2]+"! I'm dad!");
         }
         
         if(message[0].equalsIgnoreCase("b!prefix") || message[0].equalsIgnoreCase(prefix+"prefix"))
            send(event, "The current ☆BEAMbot prefix is `"+prefix+"`!");
         
         if(message[0].startsWith(prefix)) {
            
            if(!enabled && command("enable")) {
               enabled=true;
               send(event, "☆BEAMbot enabled!");
            }
            
            if(enabled) {
               if(command("disable")) {
                  enabled=false;
                  send(event, "☆BEAMbot disabled!");
               }
               
               if(command("beam"))
                  send(event,"https://cdn.discordapp.com/attachments/443775272320499722/750354400161300572/BEAM.jpg");
               
               if(command("link_youtube"))
                  send(event, "https://youtu.be/I25Cqlr5_Sc");
               
               if(command("link_spotify"))
                  send(event, "https://open.spotify.com/track/43fdUr1bBMtG2vL7PRwjug?si=usam7ji2Shm00du7a79ztw");
               
               if(command("invite"))
                  send(event, "https://discord.com/api/oauth2/authorize?client_id=750688770780823582&permissions=29879360&scope=bot");
               
               if(command("resetprefix")) {
                  prefix="b!";
                  send(event, "☆BEAMbot prefix reset to default `"+prefix+"`!");
               }
               
               if(command("changeprefix")) {
                  switch(message.length) {
                     case 1: prefix="b!"; break;
                     case 2: prefix=message[1]; break;
                     default: send(event, "☆BEAMbot prefix must not contain spaces");
                  }
                  send(event, "☆BEAMbot prefix changed to `"+prefix+"`!");
               }
               
               if(command("help")) {
                  EmbedBuilder eb = new EmbedBuilder();
                  eb.setColor(new Color(255, 144, 169)).setTitle("Commands");
                  eb.setAuthor( "☆BEAMbot", "https://cdn.discordapp.com/attachments/443775272320499722/750354400161300572/BEAM.jpg",
                     "https://cdn.discordapp.com/attachments/443775272320499722/750354400161300572/BEAM.jpg");
                  
                  eb.addField("`"+prefix+"beam`:", "Responds with ☆BEAM image", false)
                     .addField("`"+prefix+"link_youtube`:", "Responds with Koisuru☆Beam YouTube link", false)
                     .addField("`"+prefix+"link_spotify`:", "Responds with Koisuru☆Beam Spotify link", false)
                     .addField("`"+prefix+"invite`:", "Responds with shareable bot invite link", false)
                     .addField("`"+prefix+"enable`:", "Enables ☆BEAMbot if disabled", false)
                     .addField("`"+prefix+"disable`:", "Disables all ☆BEAMbot commands and messages except "+prefix+"enable", false)
                     .addField("`"+prefix+"help`:", "Displays this help message! (´・◡・｀)", false)
                     .addField(prefix.equals("b!") ? "`b!prefix`" : "`b!prefix` or `"+prefix+"prefix`:", "Displays current ☆BEAMbot prefix.`", false)
                     .addField(prefix.equals("b!") ? "`b!resetprefix`" : "`b!resetprefix` or `"+prefix+"resetprefix`:", "Resets prefix to default prefix `b!`", false)
                     .addField("`"+prefix+"changeprefix [new prefix]`:", "Changes prefix to specified prefix. Prefix must not contain spaces.", false);
                  event.getChannel().sendMessage(eb.build()).queue();
               }
            }
         }
      }
   }
   
   private boolean command(String command) {
      return message[0].substring(prefix.length()).equalsIgnoreCase(command);
   }
   
   private void send(GuildMessageReceivedEvent event, String message) { event.getChannel().sendMessage(message).queue(); }
}
