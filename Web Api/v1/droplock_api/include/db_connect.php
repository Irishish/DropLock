<?php
 
/**
 *  The class file to connect to database
 */
 
 class DB_CONNECT {
	 
	 function __construct() {
		//connecting to database
	 	$this->connect();
	 }
	 
	 function __destruct() {
		 //closing database connection
		 $this->close();
	 }
	 
	 /**
     * Function to connect with database
     */
	 function connect() {
		 //import database connection variables from config.php doc
		 require_once __DIR__ . '/config.php';
		 
		 // Connecting to MYSQL DB
		 $con = mysql_connect(DB_HOST, DB_USER, DB_PASSWORD) or die(mysql_error());
		 
		 // Selecting DB
		 $db = mysql_select_db(DB_DATABASE) or die(mysql_error()) or die(mysql_error());
		 
		 // Returning connection cursor
		 return $con;
	 }
	 
	 
	 /**
     * Function to close db connection
     */
	 function close() {
		 // Closing DB connection
		 mysql_close();
	 }
	 
 }
 
 ?>