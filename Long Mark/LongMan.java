import net.dv8tion.jda.api.entities.Message;
import net.dv8tion.jda.api.entities.MessageChannel;
import net.dv8tion.jda.api.events.message.MessageReceivedEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;

public class LongMan extends ListenerAdapter {
	
	@Override
	public void onMessageReceived(MessageReceivedEvent event) {
		
		//Creating a string object and setting it to lowercase for the bot to check later
		Message msg1= event.getMessage();
		String msg= msg1.getContentRaw();
		msg= msg.toLowerCase();
		MessageChannel channel = event.getChannel();
		
		//Using a regular expression to return true if any message contains "long long man" within
		if(msg.matches("(.*)long long man(.*)")||msg.matches("long long man")||msg.matches("(.*)l(o*)ng l(o*)ng m(a*)n(.*)")||msg.matches("l(o*)ng l(o*)ng m(a*)n")&& !(event.getAuthor().isBot())) {
			channel.sendMessage("https://www.youtube.com/watch?v=6-1Ue0FFrHY").queue();//Video of long long man
		}
	}

}