
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
	1	Ss([1,3],[3,5] | x)
	1 	Ss([1,3],[3,6] | x) 
	2	Ss([1,3],[3,7] | x)  
	3	Ss([1,3],[3,8] | x)
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
	
	
	
	public static double calcualteSpanSigma(String x)
	{
		
	}
	
	public static void main(String[] args) {
		// TODO Auto-generated method stub

	}

}
