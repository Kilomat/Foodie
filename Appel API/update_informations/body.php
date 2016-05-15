<?php
if (isset($_GET['uid']) && !empty($_GET['uid'])) {

  $res = getUser($_GET['uid']);
  $user = $res->User[0];
}
else {
  $error = 'An error occured';
}

if (isset($_POST['firstName']) && isset($_POST['lastName']) && isset($_POST['oldPassword']) && isset($_POST['newPassword']) && isset($_POST['birthday']) && isset($_POST['adress']) && isset($_POST['city']) && isset($_POST['zipcode']) && isset($_POST['bio']) && isset($_POST['gender']) && isset($_POST['phone']) && isset($_POST['notification'])) {
  $array = array(
                 'firstName' => $_POST['firstName'],
                 'lastName' => $_POST['lastName'],
                 'oldPassword' => $_POST['oldPassword'],
                 'newPassword' => $_POST['newPassword'],
                 'birthday' => $_POST['birthday'],
                 'adress' => $_POST['adress'],
                 'city' => $_POST['city'],
                 'zipcode' => $_POST['zipcode'],
                 'bio' => $_POST['bio'],
                 'gender' => $_POST['gender'],
                 'phone' => $_POST['phone'],
                 'notification' => $_POST['notification']
                 );
  $res = updateUser($_GET['uid'], $array);
  echo $res;
}


?>
<div class="main" style="margin-top: 50px;">
  <div class="row">
    <div class="col-lg-8 col-lg-offset-2">
      <h1 style="color: black">Update my personnal informations</h1>
      <form action="update_informations.php?uid=<?php echo $_SESSION['uid'] ?>" method="post">


        <div class="form-group">
          <label for="firstName">Firstname</label>
          <input name="firstName" type="text" value="<?php echo $user->firstName; ?>" class="form-control">
        </div>

        <div class="form-group">
          <label for="lastName">last Name</label>
          <input  value="<?php echo $user->lastName; ?>" name="lastName" type="text" class="form-control">
        </div>

        <div class="form-group">
          <label for="oldPassword">Old password</label>
          <input  name="oldPassword" type="text" class="form-control">
        </div>

        <div class="form-group">
          <label for="newPassword">New password</label>
          <input name="newPassword" type="text" class="form-control">
        </div>

        <div class="form-group">
          <label for="birthday">Birthday</label>
          <input value="<?php echo $user->birthday; ?>" name="birthday" type="text" class="form-control" placeholder="YYYY-MM-DD">
        </div>

        <div class="form-group">
          <label for="adress">Address</label>
          <input value="<?php echo $user->adress; ?>" name="adress" type="text" class="form-control">
        </div>

        <div class="form-group">
          <label for="city">City</label>
          <input value="<?php echo $user->city; ?>" name="city" type="text" class="form-control">
        </div>

        <div class="form-group">
          <label for="zipcode">Zipcode</label>
          <input value="<?php echo $user->zipcode; ?>" name="zipcode" type="text" class="form-control">
        </div>

        <div class="form-group">
          <label for="bio">Bio</label>
          <input value="<?php echo $user->bio; ?>" name="bio" type="text" class="form-control">
        </div>

        <div class="form-group">
          <label for="gender">Gender</label>

          <select name="gender" id="gender">
            <option value="0">Male</option>
            <option value="1">Female</option>
          </select>
        </div>

        <div class="form-group">
          <label for="phone">Phone</label>
          <input name="phone" type="text" class="form-control">
        </div>

        <div class="form-group">
          <label for="notification">Notification</label>
          <select name="gender" id="gender">
            <option value="0">Yes</option>
            <option value="1">No</option>
          </select>
        </div>

        <div class="form-group">
          <input name="notification" type="submit" class="form-control">
        </div>

      </form>

    </div>

  </div>

</div>
