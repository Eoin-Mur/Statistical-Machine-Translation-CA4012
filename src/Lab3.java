//MT Evaluation
import java.util.List;
import java.util.Arrays;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Collections;

public class Lab3
{

	public static List<String> sentanceToNGrams(String sentance, int n)
	{
		List<String> nGrams = new ArrayList<String>();
		if(n == 0)
		{
			System.out.println("Error: cannot parse 0-Grams\n\t Return null");
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
			nGrams.add(s);
		}
		return nGrams;
	}

	public static double percision(String trans, String ref, int n)
	{
		List<String> transNGrams = sentanceToNGrams(trans , n);
		List<String> refNGrams = sentanceToNGrams(ref , n);

		double totalNgram = transNGrams.size();
		double totalClipped = 0;

		HashMap<String,Integer> count = new HashMap<String, Integer>();
		for(int i = 0; i < totalNgram; i++)
		{
			for(int j = 0; j < refNGrams.size(); j++)
			{
				if(transNGrams.get(i).equals(refNGrams.get(j)))
				{
					if(count.get(transNGrams.get(i)) == null)
						count.put(transNGrams.get(i), 1);
					else
						count.put(transNGrams.get(i), count.get(transNGrams.get(i))+1 );
				}
			}
		}

		for(String key : count.keySet())
		{ 
			int f = Collections.frequency(refNGrams, key);
			if(count.get(key) > f)
				totalClipped = totalClipped + f;
			else
				totalClipped = totalClipped + count.get(key);
		}

		System.out.println("p = "+totalClipped +"/"+totalNgram);
		return (totalClipped / totalNgram);
	}

	public static double precisionProduct(String trans, String ref, int i)
	{
		double x = 1;
		for(int n = 1; n <= i; n++)
		{
			x = x * percision(trans,ref,n);
		}
		System.out.println(x);
		return x;
	}

	public static double brevity(String trans, String ref)
	{
		double x = trans.split(" ").length / ref.split(" ").length;
		System.out.println(x);
		if(x > 1.0) return 1.0;
		return x;
	}

	//TODO: need to write evaluate function
	public static double evaluateTranslation(String trans, String ref)
	{
		return brevity(trans,ref)*Math.pow(precisionProduct(trans,ref,4),0.25);
	} 

	public static void main(String [] args)
	{
		System.out.println(evaluateTranslation(args[0],args[1]));
		//System.out.println(percision("the cat is in the hat","the cat is on the mat",Integer.parseInt(args[0])));
		//sentanceToNGrams("cat sat on the mat", Integer.parseInt(args[0]));
	}
}