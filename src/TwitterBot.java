import java.io.FileInputStream;
import java.util.List;
import java.util.Properties;

import twitter4j.*;
import twitter4j.auth.*;

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
	
	public static void main(String [] args)
	{
		System.out.println("Running");
		Twitter twitter = null;
		try
		{
			twitter = new TwitterFactory().getInstance();
			
			Properties config = new Properties();
			FileInputStream in = new FileInputStream("twitter.properties");
			config.load(in);
			
			AccessToken accessToken = new AccessToken(config.getProperty("access-token"), config.getProperty("access-token-secret"));
			twitter.setOAuthConsumer(config.getProperty("consumer-key"), config.getProperty("consumer-key-secret"));
			twitter.setOAuthAccessToken(accessToken);
		}
		catch(Exception e)
		{
			System.out.println("Unable to load Twitter properties ");
			System.exit(-1);
		}
		
		List<Status> tweets = searchTweets(twitter,"#twanslate");
		for(Status tweet : tweets)
		{
			System.out.println("@"+tweet.getUser().getScreenName()+"-"+tweet.getText());
		}
	}
}
