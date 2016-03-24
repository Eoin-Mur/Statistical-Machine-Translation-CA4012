import java.awt.Point;

public class ExtractParallelData {

	
	/*Ss([p,q],[u,v] | x) = 
				(q - p + 1) + (v - u + 1)
	-----------------------------------------------------
	SUM0<p'<=q'<=u'<=v'<=n (q' - p' + 1) + ( v' - u' + 1 )
	
	
	
	x = "	the 	house 	is 		small  	das 	haus 	ist 	klein"
			1		2		3		4		5		6		7		8
	--Assume that the string has a least a trigram
	--tokenise and remove any bullshit chars
	
	n = 8
	All possible spans:
	4	Ss([1,3],[4,6] | x)
	5  	Ss([1,3],[4,7] | x)
	6	Ss([1,3],[4,8] | x)
	7 	Ss([1,3],[5,7] | x)
	8	Ss([1,3],[5,8] | x)
	9	Ss([1,3],[6,8] | x)
	10	Ss([1,4],[5,7] | x)
	11	Ss([1,4],[5,8] | x)
	12	Ss([1,5],[6,8] | x)
	13 	Ss([2,4],[5,7] | x)
	14	Ss([2,4],[6,8] | x)
	15	Ss([3,5],[6,8] | x)
	
	*/
	public static double spanScore(String [] x, Point lSpan, Point rSpan)
	{
		return (lSpan.y - lSpan.x + 1)+(rSpan.y - rSpan.x + 1) / calculateSpanSigma(x);
	}
	
	public static double calculateSpanSigma(String [] x)
	{
		double sigma = 0.0;
		int n = x.length;
		//initalilise our variables to n to allow for us to set them in the loops..
		int p = n,q = n,u = n,v = n;
		for(p = 1; p <= (q - 2); p++)
		{
			for(q = p + 2; q <= (u - 2); q++)
			{
				for(u = q + 1; u <= (v-2); u++)
				{
					for(v = u + 2; v <= n; v++)
					{
						//System.out.println("Ss(["+p+","+q+"],["+u+","+v+"] | x)");
						sigma = sigma + (q - p + 1) + (v - u + 1);
					}
				}
			}
		}
		return sigma;
	}
	
	public static void main(String[] args) {
		String s = "the house is small das haus ist klein";
		String [] x = s.split(" ");
		System.out.println(
				"Sigma (q-p+1)+(v-u+1) for all possible spans of: \n'"+s+"'\n= "+
				calculateSpanSigma(x)
		);

	}

}
