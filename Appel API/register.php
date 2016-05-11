<?php
	session_start();

	ini_set('display_errors','on');
	error_reporting(E_ALL);

	$_SESSION['api'] = "http://54.87.186.67:9000/";

	$data = array("email" => $_POST['Email'], "password" => $_POST['Password']);
	$url_createUser = $_SESSION['api']."users";

	$data = json_encode($data);

	function addUser($url, $post)
	{
	  $ch = curl_init($url);
	  curl_setopt($ch, CURLOPT_CUSTOMREQUEST, "POST");  
	  curl_setopt($ch, CURLOPT_RETURNTRANSFER, true);
	  curl_setopt($ch, CURLOPT_POSTFIELDS,$post);
	  curl_setopt($ch, CURLOPT_HTTPHEADER, array('Content-Type: application/json', 'Content-Length: ' . strlen($post)));
	  curl_setopt($ch, CURLOPT_FOLLOWLOCATION, 1);
	  $result = curl_exec($ch);
	  curl_close($ch);
	  return $result;
	}

	echo "  ".addUser($url_createUser, $data);


// 	$curl = curl_init('http://54.87.186.67:9000/');
// 	//curl_setopt($curl, CURLOPT_URL, 'http://google.fr');
// curl_setopt($curl, CURLOPT_RETURNTRANSFER, true);
// curl_setopt($curl, CURLOPT_COOKIESESSION, true); 
// curl_setopt($curl, CURLOPT_FOLLOWLOCATION, true);
// $retour = curl_exec($curl);
// curl_close($curl);
// echo $retour;

	//echo phpinfo();



?>