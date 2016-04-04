<?php
	//php -S localhost:8000

	parse_str($_SERVER['QUERY_STRING']);


	$actionUrl = "submitEval.php?id=".$id;

	//echo $actionUrl."<br>";
	$valid = 0;
	$user = "";
	$oTweet = "";
	$tTweet = "";


	$handle = @fopen("../Data/user.eval", "r");
	if($handle)
	{
		while(($buffer=fgets($handle,4609)) !== false)
		{
			$split = explode("    ", $buffer);
			if($split[0] == $id)
			{
				$valid = -1;
				break;
			}
		}
		fclose($handle);
	}


	$handle = @fopen("../Data/test.tweets", "r");
	if($handle && $valid != -1)
	{
		while(($buffer=fgets($handle,4609)) !== false)
		{
			$split = explode("    ", $buffer);
			if($split[0] == $id)
			{
				$user = $split[1];
				$oTweet = $split[2];
				$tTweet = $split[3];
				$valid = 1;
				break;

			}
		}
		fclose($handle);
	}

?>
<html>
	<head>
		<style>
			body
			{
				background-color: lightgrey;
			}
			table.blah
			{
				border-spacing: 50px 0;
			}
		</style>
	</head>
	<body>
		<div  align="center">
			<h1>Evaluation Form</h1>
<?php 
			if($valid == 0)
			{
				echo "<p>Unable to find a tweet matching your id: ".$id."</p>";
			}
			elseif($valid == 1)
			{
				echo "<p>Hi <b>".$user."</b> Thanks For taking the time to evaluate the translation we sent you</p>
					<p>Your Original Tweet Was:<b>".$oTweet."</b></p>
					<p>Our Translation Was: <b>".$tTweet."</p>
					<p>Please use the form below to rate this translation</p>";
			}
			elseif($valid == -1)
			{
				echo "<p>You already submitted a evaluation for your tweet with id: ".$id."</p>";
			}	
?>

			<form action='<?php echo $actionUrl ?>' method="post">
				<table class=blah>
					<tr>
						<td>
							<table>
								<tr>
									<th colspan="5">Adequacy</th>
								</tr>
								<tr align="center">
									<td><input type="radio" name="adequacy" value="1"></td>
									<td><input type="radio" name="adequacy" value="2"></td>
									<td><input type="radio" name="adequacy" value="3"></td>
									<td><input type="radio" name="adequacy" value="4"></td>
									<td><input type="radio" name="adequacy" value="5"></td>
								</tr>
								<tr align="center">
									<td>1</td>
									<td>2</td>
									<td>3</td>
									<td>4</td>
									<td>5</td>
								</tr>
								<tr>
									<td colspan=5>
										5=All Meaning</br>
										4=Most Meaning</br>
										3=Much Meaning</br>
										2=Little Meaning</br>
										1=None Meaning</br>
									</td>
								</tr>
							</table>
						</td>
						<td>		
							<table>
								<tr>
									<th colspan="5">Fluency</th>
								</tr>
								<tr align="center">
									<td><input type="radio" name="fluency" value="1"></td>
									<td><input type="radio" name="fluency" value="2"></td>
									<td><input type="radio" name="fluency" value="3"></td>
									<td><input type="radio" name="fluency" value="4"></td>
									<td><input type="radio" name="fluency" value="5"></td>
								</tr>
								<tr align="center">
									<td>1</td>
									<td>2</td>
									<td>3</td>
									<td>4</td>
									<td>5</td>
								</tr>
								<tr>
									<td colspan=5>
										5=Flawless English</br>
										4=Good English</br>
										3=Non-native English</br>
										2=Disfluent English</br>
										1=Incomprehensible</br>
									</td>
								</tr>
							</table>
						</td>
					</tr>
					<tr align="center"> 
						<td colspan="2">
<?php 
								if($valid == 1){echo '<input type="submit" value="Submit">';}
								elseif($valid == -1){echo "Already Submitted";}
?>
						</td>
					</tr>
				</table>
			</form>
		</div>	
	</body>

</html>

