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
  
$query6 = "SELECT s.sname as sname, count(o.cus_ID) as count
	FROM PARTS_MOD.Orders o
	JOIN PARTS_MOD.Customer c USING(cus_ID) 
	JOIN PARTS_MOD.State s using (scode)
	group by c.scode
	ORDER BY count DESC;";

?>

<p>
The query:
<p>
<?php
print $query6;
?>

<hr>
<p>
Number of items purchased by state:
<p>

<?php
$result = mysqli_query($conn, $query6)
or die(mysqli_error($conn));

print "<pre>";
while($row = mysqli_fetch_array($result, MYSQLI_BOTH))
  {
    print "\n";
    print "$row[sname]: $row[count]";
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
	  
