import java.io.File;
import java.io.FileOutputStream;
import java.io.PrintWriter;

import java.io.FileInputStream;
import java.util.Properties;
import twitter4j.*;
import twitter4j.conf.*;


public class TwitterStreamBot {
	public static void main(String [] args)
	{
		//add a listener which listens for new tweets
		StatusListener listener = new StatusListener()
		{
			
			//on reciving a status from our listener
			public void onStatus(Status status)
			{
				//System.out.println("@"+status.getUser().getScreenName()+"-"+status.getText());
				
				String text;
				//get the users twitter name who sent the tweet with the hashtag
				String user = "@"+status.getUser().getScreenName();
				long id = status.getId();
				//get any quoted tweets ie. the user retweeted a tweet and then added the hashtag to it
				Status quoted = status.getQuotedStatus();
				//if their was a quoted tweet in it.
				if(quoted != null) 
				{
					//get the text from the quoted tweet
					text = quoted.getText();
				}
				//other wise it was just a normal tweet so get the text
				else
				{
					text = status.getText();
				}
				
				//print to the screen the user we just read and their message
				System.out.println(user+"-"+text);
				
				//TODO:MOSES INTERFACE!
				//interface with moses and send the text to be translated to english.
				
				String translation = text;//just use text untill we get the interface up.
			
				
				try
				{
					//load our keys
					Properties config = new Properties();
					try
					{
						FileInputStream in = new FileInputStream("twitter.properties");
						config.load(in);
					}
					catch(Exception e)
					{
						System.out.println("unable to load properties file");
						System.exit(-1);
					}
					
					//build a new twitter api connection with our credentals
					ConfigurationBuilder cb = new ConfigurationBuilder();
					cb.setDebugEnabled(true)
						.setOAuthConsumerKey(config.getProperty("consumer-key"))
						.setOAuthConsumerSecret(config.getProperty("consumer-key-secret"))
						.setOAuthAccessToken(config.getProperty("access-token"))
						.setOAuthAccessTokenSecret(config.getProperty("access-token-secret"));
					
					Twitter twitter = new TwitterFactory(cb.build()).getInstance();
					
					//reply to the use with the translation
					Status reply = twitter.updateStatus(user+" i hear you");
					System.out.println("Sent reply tweet:: "+reply);
					//TODO:Add call to send direct message with link to user eval form
					
					
					PrintWriter pw = new PrintWriter(new FileOutputStream(new File("Data/recived.tweets"),true));
					pw.println(user+"    "+id+"    "+text+"    "+translation);
					pw.close();
				}
				catch(Exception e)
				{
					e.printStackTrace();
				}
				

			}
			//have to overide this methods as with the interface documentation
			public void onDeletionNotice(StatusDeletionNotice statusDeletionNotice) {}
		    public void onTrackLimitationNotice(int numberOfLimitedStatuses) {}
		    public void onException(Exception ex) 
		    {
		          ex.printStackTrace();
		    }
			public void onScrubGeo(long arg0, long arg1) {}	
			public void onStallWarning(StallWarning arg0) {}
		};
		
		//set up a new twitter stream class
		TwitterStream twitterStream = null;
		
		//get our twitter properties
		Properties config = new Properties();
		try
		{
			FileInputStream in = new FileInputStream("twitter.properties");
			config.load(in);
		}
		catch(Exception e)
		{
			System.out.println("unable to load properties file");
			System.exit(-1);
		}
		
		try
		{	
			//We need to pass our properties to a configuration builder
			//the reason being when just passing them directly with the method
			//twitterStream.setOAuthConsumer(); it can cause an error saying the key/secret is already set.
			//as per the documentation this is the correct way to pass configuration settings
			ConfigurationBuilder cb = new ConfigurationBuilder();
			cb.setDebugEnabled(true)
				.setOAuthConsumerKey(config.getProperty("consumer-key"))
				.setOAuthConsumerSecret(config.getProperty("consumer-key-secret"))
				.setOAuthAccessToken(config.getProperty("access-token"))
				.setOAuthAccessTokenSecret(config.getProperty("access-token-secret"));
			
			//create our twitterfactory with the configuration settings above
			TwitterStreamFactory tsf = new TwitterStreamFactory(cb.build());
			//get our instance
			//get instance now will return an authenticated instance using the configuarion settings
			//passed in with the builder
			twitterStream = tsf.getInstance();
			
			//add out listener
			twitterStream.addListener(listener);
			//call the sample stream
			twitterStream.filter("twanslate");
			
			//System.out.println("Crawling Tweets");
		}
		catch(Exception e)
		{
			e.printStackTrace();
			System.exit(-1);
		}
	}	
}
