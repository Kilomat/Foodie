<?php

include('functions.php');

if (isset($_POST['email']) && !empty($_POST['email']) && isset($_POST['password']) && !empty($_POST['password']))
{

	$arr = array('email' => $_POST['email'], 'password' => $_POST['password']);

	$res = auth($arr);
	if (isset($res->error)) {
		header('Location: login.php?msg=Email/password false');
	}
	else {
		$_SESSION['token'] = $res->JWT;
		$_SESSION['uid'] = $res->uid;
//		header('Location: index.php');
	}

}
else {
	header('Location: login.php?msg=Please fill up email and password input');
}

?>
