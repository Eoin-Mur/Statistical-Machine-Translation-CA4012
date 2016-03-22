//Word Alignment
public class Lab5
{
	public NUM_SENTENCES = 1000;

	public static List<List<String>> biText(String fData, String eData)
	{
		ArrayList<ArrayList<String>> biText;
		
		ArrayList<String> fSents = new ArrayList<String>();
		ArrayList<String> eSents = new ArrayList<String>();

		Scanner fScanner,eData = null;
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
	 		fSents = fScanner.nextLine();
	 	}

	 	while(eScanner.hasNextLine())
	 	{
	 		eSents = eScanner.nextLine();
	 	}

	 	for(int i = 0; i < NUM_SENTENCES; i++)
	 	{
	 		ArrayList<String> temp = new ArrayList();
	 		temp.add(fSents[i]);
	 		temp.add(eSents[i]);
	 		biText.add(temp);
	 	}

	 	return biText;

	}

	public static void main()
}