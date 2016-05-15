<div class="main" style="margin-top: 50px;">
  <div class="row">
    <div class="col-lg-8 col-lg-offset-2">
      <h1 style="color:black;">Register</h1>
    </div>
  </div>
  <div class="row">
    <div class="col-lg-8 col-lg-offset-2">
      <div class="row">
        <div class="col-lg-8 col-lg-offset-2">
          <?php
          if (isset($error) && !empty($var)) {
          }
            echo $error;

          ?>
        </div>
      </div>
      <form action="register.php" method="post">

        <div class="form-group">
          <label for="email">Email</label>
          <input class="form-control" name="email" type="text" placeholder="Your email address">
        </div>
        <div class="form-group">
          <label for="email">Password</label>
          <input class="form-control" name="password" type="password" placeholder="Your password">
        </div>

        <div class="form-group">
          <input class="form-control" type="submit" value="Register" >
        </div>

      </form>
    </div>
  </div>
</div>
