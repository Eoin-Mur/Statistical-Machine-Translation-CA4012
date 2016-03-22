import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

//Word Alignment
public class Lab5
{
	public NUM_SENTENCES = 1000;

	public static List<List<String>> biText(String fData, String eData)
	{
		List<List<String>> biText;
		
		ArrayList<String> fSents = new ArrayList<String>();
		ArrayList<String> eSents = new ArrayList<String>();

		Scanner fScanner,eScanner = null;
		try
		{
			fScanner = new Scanner(new File(fData));
			eScanner = new Scanner(new File(eData));
		}
		catch(Exception e)
		{
			System.out.println("Error reading files!");
			return null;
		}

	 	while(fScanner.hasNextLine())
	 	{
	 		fSents.add(fScanner.nextLine());
	 	}

	 	while(eScanner.hasNextLine())
	 	{
	 		eSents.add(eScanner.nextLine());
	 	}

	 	for(int i = 0; i < NUM_SENTENCES; i++)
	 	{
	 		ArrayList<String> temp = new ArrayList<String>();
	 		temp.add(fSents.get(i));
	 		temp.add(eSents.get(i));
	 		biText.add(temp);
	 	}

	 	return biText;

	}

	public static void main(String [] args)
	{
		
	}
}