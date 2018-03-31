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
  
$query3 = "SELECT d.name as name
	FROM PARTS_MOD.Customer c
	JOIN PARTS_MOD.Orders o USING(cus_ID)
	JOIN PARTS_MOD.Product d USING(prod_ID) 
	WHERE c.cus_ID in (SELECT p.cus_ID FROM PARTS_MOD.Popular_musicians p)
	GROUP BY d.name;";

?>

<p>
The query:
<p>
<?php
print $query3;
?>

<hr>
<p>
Products used by popular musicians:
<p>

<?php
$result = mysqli_query($conn, $query3)
or die(mysqli_error($conn));

print "<pre>";
while($row = mysqli_fetch_array($result, MYSQLI_BOTH))
  {
    print "\n";
    print "$row[name]";
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
	  
