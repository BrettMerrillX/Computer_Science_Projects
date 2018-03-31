<?php

include('connectionData2.txt');

$conn = mysqli_connect($server, $user, $pass, $dbname, $port)
or die('Error connecting to MySQL server.');

?>

<html>
<head>
  <title>Final Project for CIS451 SQL/PHP</title>
  </head>
  
  <body bgcolor="green">
  
  
  <hr>
  
  
<?php

$query5 = "SELECT CONCAT('$',sum(p.price)) as Total_spent
	FROM PARTS_MOD.Orders o  
	JOIN PARTS_MOD.Product p USING(prod_ID);";

?>

<p>
The query:
<p>
<?php
print $query5;
?>

<hr>
<p>
Total dollars spent:
<p>

<?php
$result = mysqli_query($conn, $query5)
or die(mysqli_error($conn));

print "<pre>";
while($row = mysqli_fetch_array($result, MYSQLI_BOTH))
  {
    print "\n";
    print "$row[Total_spent]";
  }
print "</pre>";

mysqli_free_result($result);

mysqli_close($conn);

?>

<p>
<hr>

<p>
<a href="PARTS_MOD_PHP.txt" >Contents</a>
of the PHP program that created this page. 	 
 
</body>
</html>
	  
