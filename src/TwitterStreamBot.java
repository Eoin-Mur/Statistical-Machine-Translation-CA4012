import java.io.FileInputStream;
import java.util.Properties;

import twitter4j.*;
import twitter4j.auth.*;
import twitter4j.conf.*;
public class TwitterStreamBot {
	public static void main(String [] args)
	{
		//add a listener which listens for new tweets
		StatusListener listener = new StatusListener()
		{
			//calls this function when a tweet is received
			//This is where we will have our code to detect possible parallel data in a tweet
			public void onStatus(Status status)
			{
				System.out.println("@"+status.getUser().getScreenName()+"-"+status.getText());
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
			twitterStream.sample();
		}
		catch(Exception e)
		{
			e.printStackTrace();
			System.exit(-1);
		}
		
	}
		
}
