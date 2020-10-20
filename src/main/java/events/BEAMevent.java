package events;
import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.OnlineStatus;
import net.dv8tion.jda.api.Permission;
import net.dv8tion.jda.api.entities.Activity;
import net.dv8tion.jda.api.entities.Member;
import net.dv8tion.jda.api.events.ReadyEvent;
import net.dv8tion.jda.api.events.message.guild.GuildMessageReceivedEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;
import java.awt.Color;
import java.util.List;

public class BEAMevent extends ListenerAdapter {
   
   private boolean enabled = true, dadbot_enabled = true, greetings_enabled = true,
      beam_enabled = true, moderation_enabled=true, reply_delete=false;
   private String prefix = "b!";
   private String[] message;
   private EmbedBuilder eb;
   private List<Member> mentionedMembers;
   
   public void onReady(ReadyEvent event) {
      
      event.getJDA().getPresence().setStatus(OnlineStatus.DO_NOT_DISTURB);
      event.getJDA().getPresence().setActivity(Activity.listening("☆BEAM"));
      
   }
   
   public void onGuildMessageReceived(GuildMessageReceivedEvent event) {
      
      String messagestring=event.getMessage().getContentRaw();
      message=messagestring.split(" ");
      
      Member member = event.getMember();
      if(!member.getUser().isBot()) {
         if(enabled) {
            if(beam_enabled && message[0].toLowerCase().matches("☆?beam"))
               send(event,"☆BEAM");
            if(reply_delete && message[0].toLowerCase().matches("delete")) {
               reply_delete = false;
               event.getChannel().deleteMessages(event.getChannel().getHistory().retrievePast(3).complete()).queue();
            }
            
            if(greetings_enabled) {
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
            }
            
            if(dadbot_enabled) {
               if(message.length>1) {
                  if(messagestring.toLowerCase().startsWith("im "))
                     send(event, "Hi, "+messagestring.substring(3)+"! I'm ☆BEAM!");
                  if(messagestring.toLowerCase().startsWith("i'm "))
                     send(event, "Hi, "+messagestring.substring(4)+"! I'm ☆BEAM!");
               }
               if(message.length>2 && messagestring.startsWith("I AM "))
                  send(event, "Hi, "+messagestring.substring(5)+"! I'm ☆BEAM!");
            }
         }
         
         if(messagestring.equalsIgnoreCase("b!prefix") || messagestring.equalsIgnoreCase(prefix+"prefix"))
            send(event, "The current ☆BEAMbot prefix is `"+prefix+"`!");
         if(messagestring.equalsIgnoreCase("b!prefix default") || messagestring.equalsIgnoreCase(prefix+"prefix default"))
            send(event, "The default ☆BEAMbot prefix is `b!`!");
         
         if(message[0].startsWith(prefix)) {
            
            if(command("enable")) {
               send(event, enabled ? "☆BEAMbot is already enabled!" : "☆BEAMbot enabled!");
               enabled=true;
            }
            
            if(enabled) {
               if(command("disable")) {
                  enabled=false;
                  send(event, "☆BEAMbot disabled!");
               }
               
               if(command("beam_enable")) {
                  send(event, beam_enabled ? "BEAM replies are already enabled!" : "BEAM replies enabled!");
                  beam_enabled=true;
               }
               
               if(command("beam_disable")) {
                  send(event, beam_enabled ? "BEAM replies disabled!" : "BEAM replies are already disabled!");
                  beam_enabled=false;
               }
               
               if(command("dadbot_enable")) {   
                  send(event, dadbot_enabled ? "Dadbot functionality is already enabled!" : "Dadbot functionality enabled!");
                  dadbot_enabled=true;
               }
               
               if(command("dadbot_disable")) {
                  send(event, dadbot_enabled ? "Dadbot functionality disabled!" : "Dadbot functionality is already disabled!");
                  dadbot_enabled=false;
               }
               
               if(command("greetings_enable")) {
                  send(event, greetings_enabled ? "Greetings are already enabled!" : "Greetings enabled!");
                  greetings_enabled=true;
               }
   
               if(command("greetings_disable")) {
                  send(event, greetings_enabled ? "Greetings disabled!" : "Greetings are already disabled!");
                  greetings_enabled=false;
               }
   
               if(command("moderation_enable")) {
                  send(event, moderation_enabled ? "Moderation commands are already enabled!" : "Moderation commands enabled!");
                  moderation_enabled=true;
               }
   
               if(command("moderation_disable")) {
                  send(event, moderation_enabled ? "Moderation commands disabled!" : "Moderation commands are already disabled!");
                  moderation_enabled=false;
               }
               
               if(command("beam"))
                  send(event,"https://cdn.discordapp.com/attachments/443775272320499722/750354400161300572/BEAM.jpg");
               
               if(command("link_youtube"))
                  send(event, "https://youtu.be/I25Cqlr5_Sc");
               
               if(command("link_spotify"))
                  send(event, "https://open.spotify.com/track/43fdUr1bBMtG2vL7PRwjug?si=usam7ji2Shm00du7a79ztw");
               
               if(command("invite"))
                  send(event, "https://discord.com/api/oauth2/authorize?client_id=750688770780823582&permissions=13102150&scope=bot");
               
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
               
               if(message.length==1 && command("help")) {
                  resetEmbedBuilder("Help menu");
                  eb.addField("`"+prefix+"help`:", "Displays this help message! (´・◡・｀)", false)
                     .addField("`"+prefix+"help general`:", "General and fun commands", false)
                     .addField("`"+prefix+"help moderation`:", "Moderation commands", false)
                     .addField("`"+prefix+"help management`:", "Bot management commands", false)
                     .addField("`"+prefix+"help prefixes`:", "Prefix commands", false);
                  setEmbedBuilderFooter(event);
                  sendEmbedBuilder(event);
               }
               
               if(messagestring.equalsIgnoreCase(prefix+"help general")) {
                  resetEmbedBuilder("General and fun commands!");
                  eb.addField("`"+prefix+"beam`:", "Responds with ☆BEAM image", false)
                     .addField("`"+prefix+"link_youtube`:", "Responds with Koisuru☆Beam YouTube link", false)
                     .addField("`"+prefix+"link_spotify`:", "Responds with Koisuru☆Beam Spotify link", false)
                     .addField("`"+prefix+"invite`:", "Responds with shareable bot invite link", false);
                  setEmbedBuilderFooter(event);
                  sendEmbedBuilder(event);
               }
   
               if(messagestring.equalsIgnoreCase(prefix+"help moderation")) {
                  resetEmbedBuilder("Moderation commands!");
                  eb.addField("`"+prefix+"kick [@user]`:", "Kicks [user] from the guild.\n" +
                        "Requires kick permissions. Use with caution!", false)
                     .addField("`"+prefix+"ban [@user] [optional: number]`:", "Bans [user] from the guild and deletes [user]'s messages from past [number] days, or 0 days by default.\n" +
                        "Requires ban permissions. Use with caution!", false)
                     .addField("`"+prefix+"unban [user ID]`:", "Unbans [user] from the guild.\n" +
                        "Requires ban permissions.", false)
                     .addField("`"+prefix+"mute [@user] [optional: number]`:", "Mutes [user] in the guild for [number] hours, or 1 hour by default, or until unmuted.\n" +
                        "Requires manage server permissions and a Muted guild role without send message permissions.", false)
                     .addField("`"+prefix+"unmute [@user]`:", "Unmutes [user] in the guild.\n" +
                        "Requires manage server permissions and a Muted guild role without send message permissions.", false)
                     .addField("`"+prefix+"userclear [@user] [optional: number]`:", "Deletes [user]'s messages from past [number] days, or 1 day by default.\n" +
                        "Requires manage messages permissions. Use with caution!", false)
                     .addField("`"+prefix+"clear [number]`:", "Deletes last [number] messages in current channel.\n" +
                        "Requires manage messages permissions. Use with caution!", false);
                  setEmbedBuilderFooter(event);
                  sendEmbedBuilder(event);
               }
   
               if(messagestring.equalsIgnoreCase(prefix+"help management")) {
                  resetEmbedBuilder("Bot management commands!");
                  eb.addField("`"+prefix+"enable`:", "Enables ☆BEAMbot commands and messages", false)
                     .addField("`"+prefix+"disable`:", "Disables ☆BEAMbot commands and messages except "+prefix+"enable", false)
                     .addField("`"+prefix+"beam_enable`:", "Enables replies to BEAM and ☆BEAM", false)
                     .addField("`"+prefix+"beam_disable`:", "Disables replies to BEAM and ☆BEAM", false)
                     .addField("`"+prefix+"dadbot_enable`:", "Enables Dadbot functionality", false)
                     .addField("`"+prefix+"dadbot_disable`:", "Disables Dadbot functionality", false)
                     .addField("`"+prefix+"greetings_enable`:", "Enables replies to greetings", false)
                     .addField("`"+prefix+"greetings_disable`:", "Disables replies to greetings", false)
                     .addField("`"+prefix+"moderation_enable`:", "Enables moderation commands", false)
                     .addField("`"+prefix+"moderation_disable`:", "Disables moderation commands", false);
                  setEmbedBuilderFooter(event);
                  sendEmbedBuilder(event);
               }
   
               if(messagestring.equalsIgnoreCase(prefix+"help prefix")) {
                  resetEmbedBuilder("Prefix commands!");
                  eb.addField(prefix.equals("b!") ? "`b!prefix [default]`" : "`b!prefix` or `"+prefix+"prefix`:", "Displays current ☆BEAMbot prefix, or default prefix if specified.`", false)
                     .addField(prefix.equals("b!") ? "`b!resetprefix`" : "`b!resetprefix` or `"+prefix+"resetprefix`:", "Resets prefix to default prefix `b!`", false)
                     .addField("`"+prefix+"changeprefix [new prefix]`:", "Changes prefix to specified prefix. Prefix must not contain spaces.", false);
                  sendEmbedBuilder(event);
               }
               
               if(moderation_enabled && command("kick")) {
                  if(!member.hasPermission(Permission.KICK_MEMBERS))
                     send(event, "You don't have permission to run that command!");
                  if(!event.getGuild().getSelfMember().hasPermission(Permission.KICK_MEMBERS))
                     send(event, "I don't have permission to run that command!");
                  else {
                     mentionedMembers = event.getMessage().getMentionedMembers();
                     if(mentionedMembers.isEmpty())
                        send(event, "You must mention a user to kick!");
                     else if(mentionedMembers.get(0).hasPermission(Permission.BAN_MEMBERS) || !event.getGuild().getSelfMember().canInteract(mentionedMembers.get(0)))
                        send(event, "I cannot kick a moderator or a user with a role higher than mine.");
                     else try {
                        event.getGuild().kick(mentionedMembers.get(0)).queue();
                        resetEmbedBuilder("Kicked " + mentionedMembers.get(0).getUser().getName() + "!");
                        reply_delete=true;
                        eb.setDescription("Reply \"delete\" to delete this interaction. (´・◡・｀)");
                        setEmbedBuilderFooter(event);
                        sendEmbedBuilder(event);
                     }
                     catch(Exception e) { send(event, "Please correctly mention a user to kick."); }
                  }
               }
               
               if(moderation_enabled && command("ban")) {
                  if(!member.hasPermission(Permission.BAN_MEMBERS))
                     send(event, "You don't have permission to run that command!");
                  if(!event.getGuild().getSelfMember().hasPermission(Permission.BAN_MEMBERS))
                     send(event, "I don't have permission to run that command!");
                  else {
                     mentionedMembers = event.getMessage().getMentionedMembers();
                     if(mentionedMembers.isEmpty())
                        send(event, "You must mention a user to ban!");
                     else if(mentionedMembers.get(0).hasPermission(Permission.BAN_MEMBERS) || !event.getGuild().getSelfMember().canInteract(mentionedMembers.get(0)))
                        send(event, "I cannot ban a moderator or a user with a role higher than mine.");
                     else try{
                        if(message.length==2)
                           event.getGuild().ban(mentionedMembers.get(0), 0).queue();
                        else
                           event.getGuild().ban(mentionedMembers.get(0), Integer.parseInt(message[3])).queue();
                        resetEmbedBuilder("Banned " + mentionedMembers.get(0).getUser().getName() + "!");
                        reply_delete=true;
                        eb.setDescription("Reply \"delete\" to delete this interaction. (´・◡・｀)");
                        setEmbedBuilderFooter(event);
                        sendEmbedBuilder(event);
                     }
                     catch(Exception e) { send(event, "Please correctly mention a user to ban."); }
                  }
               }
   
               if(moderation_enabled && command("unban")) {
                  if(!member.hasPermission(Permission.BAN_MEMBERS))
                     send(event, "You don't have permission to run that command!");
                  if(!event.getGuild().getSelfMember().hasPermission(Permission.BAN_MEMBERS))
                     send(event, "I don't have permission to run that command!");
                  else {
                     if(message.length==1)
                        send(event, "You must specify the banned user's ID to unban!");
                     else try{
                        event.getGuild().unban(message[1]).queue();
                        resetEmbedBuilder("Unbanned user if banned!");
                        reply_delete=true;
                        eb.setDescription("Reply \"delete\" to delete this interaction. (´・◡・｀)");
                        setEmbedBuilderFooter(event);
                        sendEmbedBuilder(event);
                     }
                     catch(NumberFormatException e) { send(event, "You must specify the banned user's ID to unban!"); }
                  }
               }
               
               if(moderation_enabled && command("mute")) { }
               if(moderation_enabled && command("unmute")) { }
               if(moderation_enabled && command("userclear")) { }
               if(moderation_enabled && command("clear")) { }
            }
         }
      }
   }
   
   private boolean command(String command) {
      return message[0].substring(prefix.length()).equalsIgnoreCase(command);
   }
   
   private void send(GuildMessageReceivedEvent event, String message) {
      event.getChannel().sendMessage(message).queue();
   }
   
   private void resetEmbedBuilder(String title) {
      eb = new EmbedBuilder();
      eb.setColor(new Color(224, 2, 39)).setTitle(title).setAuthor( "☆BEAMbot",
         "https://cdn.discordapp.com/attachments/443775272320499722/750354400161300572/BEAM.jpg",
         "https://cdn.discordapp.com/attachments/443775272320499722/750354400161300572/BEAM.jpg");
   }
   
   private void setEmbedBuilderFooter(GuildMessageReceivedEvent event) {
      eb.setFooter("Requested by " + event.getMember().getUser().getName() + ".", event.getMember().getUser().getAvatarUrl());
   }
   
   private void sendEmbedBuilder(GuildMessageReceivedEvent event) {
      event.getChannel().sendMessage(eb.build()).queue();
   }
}
