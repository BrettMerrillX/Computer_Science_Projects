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
  
$query3 = "SELECT p.name as name, v.vst_type as vst_type
	FROM PARTS_MOD.Product p
	JOIN PARTS_MOD.VSTs v USING(vst_ID) 
	WHERE p.prod_ID not in (SELECT o.prod_ID FROM PARTS_MOD.Orders o);";

?>

<p>
The query:
<p>
<?php
print $query3;
?>

<hr>
<p>
Products that have not been purchased yet:
<p>

<?php
$result = mysqli_query($conn, $query3)
or die(mysqli_error($conn));

print "<pre>";
print "Name/Type";

while($row = mysqli_fetch_array($result, MYSQLI_BOTH))
  {
    print "\n";
    print "\n";
    print "$row[name] - $row[vst_type] ";
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
	  
