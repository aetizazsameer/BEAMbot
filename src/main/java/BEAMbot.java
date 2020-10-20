import events.BEAMevent;
import net.dv8tion.jda.api.JDA;
import net.dv8tion.jda.api.JDABuilder;

public class BEAMbot {
   public static void main(String[] args) throws Exception {
      
      JDA jda = new JDABuilder("TOKEN HERE").build();
      jda.addEventListener(new BEAMevent());
      
   }
}
