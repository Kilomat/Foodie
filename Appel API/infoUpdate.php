<?php


	session_start();

	ini_set('display_errors','on');
	error_reporting(E_ALL);

	$url_auth = $_SESSION['api'].$_SESSION['Id'].'/'.$_SESSION['Token'];
	

	
	$birthday = $_POST['year'].'-'.$_POST['month'].'-'.$_POST['day'];
	$zipCode = intval($_POST['zipCode']);
	
	$notification = 0;
	if(isset($_POST['notification']))
		$notification = 1;

	$data = array("firstName" => $_POST['firstName'], "lastName" => $_POST['lastName'], "oldPassword" => $_POST['oldPassword'],
		"newPassword" => $_POST['newPassword'], "birthday" => $birthday, "adress" => $_POST['adress'], "city" => $_POST['city'],
		"zipcode" => $zipCode, "bio" => $_POST['bio'], "gender" => $_POST['gender'], "phone" => $_POST['phone'],
		"notification" => $notification);

	$data = json_encode($data);


	function getToken($str) {
		$str = substr($str, 8);
		$pos = strpos($str, ':"');
		$token = substr($str, 0, $pos - 7);
		$_SESSION['Token'] = $token;
	}

	function updateInfo($url, $post) {
	  $ch = curl_init($url);
	  curl_setopt($ch, CURLOPT_CUSTOMREQUEST, "PUT");  
	  curl_setopt($ch, CURLOPT_RETURNTRANSFER, true);
	  curl_setopt($ch, CURLOPT_POSTFIELDS,$post);
	  curl_setopt($ch, CURLOPT_HTTPHEADER, array('Content-Type: application/json', 'Content-Length: ' . strlen($post)));
	  curl_setopt($ch, CURLOPT_FOLLOWLOCATION, 1);
	  $result = curl_exec($ch);
	  curl_close($ch);
	  return $result;
	}

	$info = updateInfo($url_auth, $data);
	getToken($info);
	echo $info."<br/></br>";

	





?>