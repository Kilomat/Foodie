<?php


	session_start();

	ini_set('display_errors','on');
	error_reporting(E_ALL);

	$url_auth = $_SESSION['api']."meals/".$_SESSION['Token'];

	$price = intval($_POST['price']);

	$data = array("restaurantId" => $_SESSION['restaurantId'], "title" => $_POST['title'], "description" => $_POST['description'],
		"price" => $price, "city" => $_POST['city']);



	$data = json_encode($data);


	function sendMoment($url, $post) {
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

	$info = sendMoment($url_auth, $data);
	echo $info."<br/></br>";





?>	
