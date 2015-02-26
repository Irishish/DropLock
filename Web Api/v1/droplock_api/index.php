<?php

	/**
	 * File to login request
	 * 
	 * Each request will be identified by TAG
	 * Response will be JSON data?
	 * check for POST request 
	 * */
	 
	 //Determines if a POST was received and that it isn't null
	 if (isset($_POST['useremail']) && $_POST['useremail'] != '') {
		
		
		$user = "brian@gmail.com";
		$pass = "test";
		$msg = "";
		
		//Add DB handler here
		
		$email = $_POST['useremail'];
		$password = $_POST['password'];
		
		$response = array("message" => $msg);
		
		//Check user match
		if ( $user == $email && $pass == $password ) {
			//Login Success
			$msg = "Login Success!";
			$response["message"] = $msg;
			echo json_encode($response);
			
		} else {
			//Login failed
			$msg = "Login Failed";
			$response["message"] = $msg;
			echo json_encode($response);
		}
	} else {
    	echo "Missing required field";
	}

	
	
	
	 
 