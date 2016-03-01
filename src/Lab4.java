//Languge Modeling
import java.util.ArrayList;
import java.util.List;
import java.util.Arrays;
import java.util.Collections;

public class Lab4
{

	public static List<String> sentanceToNGrams(String sentance, int n)
	{
		List<String> nGrams = new ArrayList<String>();
		if(n == 0)
		{
			System.out.println("Error: cannot parse 0-Grams\n\t Returned null");
			return null;
		}

		String[] split = sentance.split(" ");

		if(n == 1) return nGrams = Arrays.asList(split);

		//split.length - (n-1) to stop the innear loop to go out of bounds
		for(int i = 0; i < split.length - (n-1); i++)
		{
			String s = split[i] + " ";
			for(int j = i + 1; j < i + n; j++)
			{
				s = s + split[j] + " ";
			}
			s = s.substring(0,s.length()-1);
			nGrams.add(s);
		}
		return nGrams;
	}

	public static int totalWordCount(String sentance)
	{
		return sentance.split(" ").length;
	}

	public static int wordFrequency(String sentance, String word)
	{
		return Collections.frequency(Arrays.asList(sentance.split(" ")), word);
	}

	public static int wordCount(String sentance, String word, int n)
	{

		String [] words = sentance.split(" ");
		String prevWord = "";
		if(Arrays.asList(words).indexOf(word) - n < 0)
			prevWord = "";
		else
		{
			for(int i = n-1; i !=0 ; i--)
			{
				prevWord = prevWord + words[(Arrays.asList(words).indexOf(word))-i] + " ";
			}
			prevWord = prevWord.substring(0, prevWord.length()-1);
		}


		System.out.println("N: "+n);
		List<String> nGrams = sentanceToNGrams(sentance, n);
		int count = 0;
		for(int j = 0; j < nGrams.size(); j ++)
		{
			System.out.println("Checking: "+nGrams.get(j)+" -> "+prevWord+" "+word);
			if(nGrams.get(j).equals(prevWord +" "+word))
				count++;
		}
		System.out.println("\tcount("+word+"|"+prevWord+") = "+count);

		int count2 = 0;
		nGrams = sentanceToNGrams(sentance, n-1);
		for(int w = 0; w < nGrams.size(); w ++)
		{
			List<String> nGrams2 = sentanceToNGrams(sentance,n);
			for(int j = 0; j < nGrams2.size(); j ++)
			{
				if(nGrams2.get(j).equals(nGrams.get(w)+" "+word))
					count2 ++;
			}	
		}
		System.out.println("\tcount("+word+"| W ) = "+count2);
		if(count2 == 0 ) return 0;
		return count/count2;
	}

	//public static List<String> allCounts(String sentance)
	//{

	//}

	public static void main(String [] args)
	{
		//wordCount("cat sat on the mat","mat");

		for(int n = 2; n <= 4; n++)
		{
			System.out.println("\tcount(mat)= "+wordCount("cat sat on the mat", "mat",n));
		}

	}	
}