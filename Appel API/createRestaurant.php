<?php


	session_start();

	ini_set('display_errors','on');
	error_reporting(E_ALL);

	$url_auth = $_SESSION['api']."restaurants/".$_SESSION['Token'];

	$places = intval($_POST['places']);
	$data = array("name" => $_POST['name'], "adress" => $_POST['adress'], "city" => $_POST['city'],
		"description" => $_POST['description'], "places" => $places);


	$data = json_encode($data);


	function sendResto($url, $post) {
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

	$info = sendResto($url_auth, $data);
	echo $info."<br/></br>";





?>