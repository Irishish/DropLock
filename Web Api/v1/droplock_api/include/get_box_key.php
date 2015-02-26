<?php

/*
 * Following code will get single product details
 * A product is identified by product id (pid)
 */
 
 //Array for JSON response
 $response = array();
 
 
 // Include db connect class
 require_once __DIR__ . '/db_connect.php';
 
 // Connecting to db
 $db = new DB_CONNECT();
 
 // Check for post data
 if (isset($_GET["idBoxes"])) {
	 $idBoxes= $_GET['idBoxes'];
	 
	 // Get a user from users table
	 $result = mysql_query("SELECT box_key FROM Boxes WHERE idBoxes = $idBoxes");
	 
	 if(!empty($result)) {
		 //check for empty result
		 if (mysql_num_rows($result) > 0) {
			 
			 $result = mysql_fetch_array($result);
			 
			 $box_key = array();
			 $box_key["idBox"] = $result["idBox"];
			 $box_key["box_key"] = $result["box_key"];
			 //success
			 $response["success"] = 1;
			 
			 //box_key node
			 $response["box_key"] = array();
			 
			 array_push($response["box_key"], $box_key);
			 
			 // echoing JSON response
			 echo json_encode($response);
		 } else {
			 // no user found
			 $response ["success"] = 0;
			 $response ["message"] = "No box found";
			 
			 // echo no users JSON
			 echo json_encode($response);
		  }
 } else {
	 // required field is missing
	 $response["success"] = 0;
	 $response["message"] = "Required field(s) is missing";
	 
	 // echoing JSON response
	 echo json_encode($response);
 }
 ?>