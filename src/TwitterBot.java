import java.io.FileInputStream;
import java.util.List;
import java.util.Properties;

import twitter4j.*;
import twitter4j.conf.ConfigurationBuilder;

//using he twiter4j library
public class TwitterBot {
	
	public static List<Status> searchTweets(Twitter twitter, String q)
	{
		List<Status> tweets = null;
		try
		{
			Query query = new Query(q);
			QueryResult result;
			result = twitter.search(query);
			tweets = result.getTweets();
			
			System.out.println("Found "+tweets.size()+" tweets");
			
		}
		catch(TwitterException e)
		{
			e.printStackTrace();
			System.out.println("Failed to search tweets "+e.getMessage());
		}
		System.out.println("Finished");
		
		return tweets;
	}
	
	public static Status Reply(Twitter twitter, String user, String text)
	{
		Status status = null;
		try
		{
			status =  twitter.updateStatus(user+text);
	
		}
		catch(TwitterException e)
		{
			e.printStackTrace();
		}
	
		return  status;
	}
	
	public static void main(String [] args)
	{
		System.out.println("Running");
		Twitter twitter = null;
		
		try
		{	
			//We need to pass our properties to a configuration builder
			//the reason being when just passing them directly with the method
			//twitterStream.setOAuthConsumer(); it can cause an error saying the key/secret is already set.
			//as per the documentation this is the correct way to pass configuration settings
			Properties config = new Properties();
			FileInputStream in = new FileInputStream("twitter.properties");
			config.load(in);
			
			ConfigurationBuilder cb = new ConfigurationBuilder();
			cb.setDebugEnabled(true)
				.setOAuthConsumerKey(config.getProperty("consumer-key"))
				.setOAuthConsumerSecret(config.getProperty("consumer-key-secret"))
				.setOAuthAccessToken(config.getProperty("access-token"))
				.setOAuthAccessTokenSecret(config.getProperty("access-token-secret"));
			
			twitter = new TwitterFactory(cb.build()).getInstance();
			
		}
		catch(Exception e)
		{
			e.printStackTrace();
		}
		
		List<Status> tweets = searchTweets(twitter,"#twanslate");
		for(Status tweet : tweets)
		{
			System.out.println("@"+tweet.getUser().getScreenName()+"-"+tweet.getText());
			System.out.println("Replying with "+
					Reply(twitter, "@"+tweet.getUser(),tweet.getText()+"I read you load on clear").getText());
		}
	}
}
