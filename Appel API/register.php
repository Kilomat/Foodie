<?php
ini_set('display_errors','on');
error_reporting(E_ALL);

include('functions.php');

if (isset($_POST['email']) &&  !empty($_POST['email']) && isset($_POST['password']) && !empty($_POST['password'])) {

  $arr = array('email' => $_POST['email'], 'password' => $_POST['password']);

  $res = createUser($arr);
  if (isset($res->error)) {
    $error = $res->error;
  }
  else {
    header('Location: login.php?msg=Veuillez vous connecter');
  }
}
else {
  $error = 'Fill email and password input';
}
?>

<!DOCTYPE html>
<html>

<head>
  <?php include('general/head.php') ?>
</head>

<body>
  <?php include('general/header.php'); ?>


  <?php include('register/body.php') ?>


  <?php include('general/footer.php'); ?>
  <?php include('general/script.php'); ?>
</body>

</html>
