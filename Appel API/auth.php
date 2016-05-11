<?php


	session_start();

	ini_set('display_errors','on');
	error_reporting(E_ALL);

	$_SESSION['api'] = "http://54.87.186.67:9000/";
	$url_auth = $_SESSION['api']."users/auth";

	$data = array("email" => $_POST['Email'], "password" => $_POST['Password']);
	$data = json_encode($data);


	function getTokenAndId($str) {
		$str = substr($str, 8);
		$pos = strpos($str, ':"');
		$token = substr($str, 0, $pos - 7);
		$id = substr($str, $pos + 2, -2);

		$_SESSION['Token'] = $token;
		$_SESSION['Id'] = $id;
		echo '<br/></br> TOKEN = '.$_SESSION['Token'];
		echo '<br/></br> ID = '.$_SESSION['Id'];
	}

	function addUser($url, $post) {
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

	$info = addUser($url_auth, $data);
	echo $info."<br/></br>";
	getTokenAndId($info);

	//var_dump($info);

	//echo json_decode($info);


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