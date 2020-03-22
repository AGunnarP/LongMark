import java.awt.Toolkit;
import java.awt.datatransfer.Clipboard;
import java.awt.datatransfer.StringSelection;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.Instant;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Date;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import javax.security.auth.login.LoginException;
import com.mysql.cj.util.StringUtils;
import net.dv8tion.jda.api.AccountType;
import net.dv8tion.jda.api.JDA;
import net.dv8tion.jda.api.JDABuilder;
import net.dv8tion.jda.api.OnlineStatus;
import net.dv8tion.jda.api.entities.Activity;
import net.dv8tion.jda.api.entities.MessageChannel;
import net.dv8tion.jda.api.entities.TextChannel;

public class LongMark{

	public static void main(String args[]) throws LoginException, IOException, InterruptedException{		
		//JDA is meant to be created here
		 JDA jda = new JDABuilder(AccountType.BOT)
                .setToken(System.getenv("token")).build();
		 			
        		jda.addEventListener(new LongMan());
        		
        		jda.awaitReady();
        		
        		 LocalDateTime now = LocalDateTime.now(); 
        		 String unformattedDate=now.toString();
        		 System.out.println(unformattedDate);
        		 Pattern pattern=Pattern.compile("-\\d\\d-");
        		 Matcher matcher=null;
        		 Pattern pattern2=Pattern.compile("-\\d\\d(\\w|\\d)");
        		 Matcher matcher2=null;
        		 
        		 int day=0;
        		 int month=0;
        		 matcher=pattern.matcher(unformattedDate);
        		 if(matcher.find()) {
        			 month=Integer.parseInt(matcher.group(0).replace("-", ""));
        		 }
        		 matcher2=pattern2.matcher(unformattedDate);
        		 if(matcher2.find()) {
        			 day=Integer.parseInt(matcher2.group(0).replace("-", "").replace("T", ""));
        		 }
        		long unixTime = Instant.now().getEpochSecond();
        		unixTime= (long) ((unixTime-656726400.00)+(86400*day)+(2592000*month));
        			for(int i=0;i<5;i++) {
        		LongMark.boondockspam(jda,unixTime);
        		unixTime=unixTime+84000;
        		}
        		Thread.sleep(86400);//bot sleeps for a day*/
        		
	}
        	public static void boondockspam(JDA jda,long unixTime) throws IOException, InterruptedException{
        		String date=LongMark.getDate(unixTime);
        		System.out.println("date'"+date);
        		//gets current time for reference 
        		
        		long IDen=(680222868595933209L);//this is the channel id for boondocs-spam
        		
        		URL url=null;
        		try {
					 url=new URL("https://www.gocomics.com/boondocks/"+date);//plugs in past date to connect to website with comic
					 System.out.println("https://www.gocomics.com/boondocks/"+date);
				} catch (MalformedURLException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
        		
        		//reads website for 
        		URLConnection urlConn= url.openConnection();//opens url connection to the comic website
    			InputStreamReader InputReader= new InputStreamReader(urlConn.getInputStream());
    			BufferedReader buff= new BufferedReader(InputReader);
    			
    			Pattern urlpattern= Pattern.compile("https:..assets.amuniversal.com.(\\d*\\w*)");//grabs the url to the comic picture
    			
    			Matcher urlmatcher=null;//this subroutine creates the url to the picture we will use to post on discord
    			String line=buff.readLine();
    			URL boondockurl= null;
    			while(line !=null) {
    				urlmatcher=urlpattern.matcher(line);
    				if(urlmatcher.find()==true) {
    					String tempurl=urlmatcher.group(0);
    					boondockurl= new URL(tempurl);
    					break;
    				}
    				line=buff.readLine();
    			}
    			/*StringSelection selection = new StringSelection(lines);
    			Clipboard clipboard = Toolkit.getDefaultToolkit().getSystemClipboard();
    			clipboard.setContents(selection, selection);*/
    			
        		MessageChannel channel = jda.getTextChannelById(IDen);//bot posts url to the picture
        		channel.sendMessage(boondockurl.toString()).queue();
        		
        		
			
	}
        	public static String getDate(long unixTime) {
        		//the program grabs the date from near the beginning of the boondocks comics over time it naturally progresses
        		Instant instant = Instant.ofEpochSecond(unixTime);
        		String moment=instant.toString();//converting the past time we are grabbing the comic to string so we can use regex
        		
        		Pattern pattern= Pattern.compile("\\d\\d\\d\\d-\\d\\d-\\d\\d");//uses a regex to create a useable form of the date
        		Matcher matcher=null;
        		matcher=pattern.matcher(moment);
        		String date=null;
        		while(matcher.find()==true) {
        			date=matcher.group(0);
        		}
        		
        		date=date.replace("-", "/");//replaces the date so that the dashes become slashes so it can be used with the url
        		return date;
        	}
}