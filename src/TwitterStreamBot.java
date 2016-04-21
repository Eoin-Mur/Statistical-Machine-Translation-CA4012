package twanslateES;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.io.FileInputStream;
import java.util.Properties;
import twitter4j.*;
import twitter4j.conf.*;


public class TwitterStreamBot {
	
	public static final String EVAL_URL = "www.student.computing.dcu.ie/~murphe74/evaluate_tweet.php";
	
	public static void main(String [] args)
	{
		//add a listener which listens for new tweets
		StatusListener listener = new StatusListener()
		{
			
			//on reciving a status from our listener
			public void onStatus(Status status)
			{
				InputStream std; 
				InputStream err; 
				
				//System.out.println("@"+status.getUser().getScreenName()+"-"+status.getText());
				
				String text;
				
				//get the users twitter name who sent the tweet with the hashtag
				String user = status.getUser().getScreenName();
				long id = status.getId();
				String userEvalUrl = EVAL_URL + "?id="+id;
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
				
				text = text.replace("#twanslate","");
				
				//print to the screen the user we just read and their message
				System.out.println("@"+user+"-"+text);
				
				//TODO:MOSES INTERFACE!
				//interface with moses and send the text to be translated to english.
				String translation = "";
				try {
					//you need putty, plink and this code in the same folder
					//add your username and password and name of your putty session ie "DCU Linux" to this command
					//String command = "plink -load \"DCU Linux\" -l yourusername -pw yourpassword"; //this one works, just add your details (putty session, username password)
					
					//extra line for batch commands text file, can't get it working, 

					//in the moses.ini file you need to specify the full path of the LM and phrase table
					String SH_SCRIPT = "/CA4012/twanslateES/SMT/sendTweet.sh ' "+text+" ' ";
					//String command = "plink -load \"DCU Linux\" -l yourusername -pw yourpassword -batch ";
					
					//String command = "plink student.computing.dcu.ie -l sdasd -pw asdasda -batch "+SH_SCRIPT;
					
					String command = SH_SCRIPT;
					System.out.println("Sending String for translation:\n\t"+text);

					//Runtime r = Runtime.getRuntime ();
					//Process p = r.exec (SH_SCRIPT);
					
					ProcessBuilder pb = new ProcessBuilder("/CA4012/twanslateES/SMT/sendTweet.sh", text);
					Process p = pb.start();
					std = p.getInputStream ();
					err = p.getErrorStream ();
					p.waitFor();

					//only need to read the first line as it will only return one line which is the translation
					BufferedReader reader = new BufferedReader(new InputStreamReader(std));
					translation = reader.readLine();	
					//check if we recived a error from server
					if(err.available() > 0)
					{
						int value = 0;
						System.out.println("\nRecived Error From Server!");
						while (err.available () > 0) {
							value = err.read ();
							System.out.print ((char) value);
						}
					}
					//other wise print out our recived translation
					else
					{
						System.out.println("\nRecived translation from server:\n\t"+translation);
					}
					p.destroy ();
				}
				catch (Exception e) {
					e.printStackTrace ();
				}
				
				
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
					Status reply = twitter.updateStatus("@"+user+" "+translation);
					System.out.println("Sent reply tweet::");
					DirectMessage dm = twitter.sendDirectMessage(user, 
							"Sorry to bother you,would you mind taking 10 seconds to evaluate "
							+ "the translation you got via: "+userEvalUrl);
					System.out.println("Sent eval DM::");
					//TODO:Add call to send direct message with link to user eval form
					
					
					PrintWriter pw = new PrintWriter(new FileOutputStream(new File("/users/case4/murphe74/public_html/Data/recived.tweets"),true));
					pw.println("@"+user+"    "+id+"    "+text+"    "+translation);
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
			twitterStream.filter("#twanslate");
			
			//System.out.println("Crawling Tweets");
		}
		catch(Exception e)
		{
			e.printStackTrace();
			System.exit(-1);
		}
	}	
}
