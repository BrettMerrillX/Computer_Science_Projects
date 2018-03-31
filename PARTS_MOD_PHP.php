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
  
$Popular_musicians = $_POST['Popular_musicians'];

$Popular_musicians = mysqli_real_escape_string($conn, $Popular_musicians);

$query = "SELECT d.name as name
FROM PARTS_MOD.Customer c
JOIN PARTS_MOD.Orders o USING(cus_ID)
JOIN PARTS_MOD.Product d USING(prod_ID)
WHERE c.cus_ID in (SELECT p.cus_ID FROM PARTS_MOD.Popular_musicians p) and c.name=";
$query = $query."'".$Popular_musicians."' GROUP BY d.name;";

$query2 = "SELECT d.name
	FROM PARTS_MOD.Customer c
	JOIN PARTS_MOD.Orders o USING(cus_ID) 
	JOIN PARTS_MOD.Product d USING(prod_ID)
	WHERE c.cus_ID in (SELECT p.cus_ID FROM PARTS_MOD.Popular_musicians p)
	group by d.name;";

?>

<p>
The query:
<p>
<?php
print $query;
?>

<hr>
<?php
print "$Popular_musicians uses:" 
?>
<?php
$result = mysqli_query($conn, $query)
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
	  
