<div class="main" style="margin-top: 50px">
  <div class="row">
    <div class="col-lg-8 col-lg-offset-2" style="text-align: center;">
      <?php
      if (isset($_GET['msg']) && !empty($_GET['msg'])) {
        echo $_GET['msg'];
      }
      ?>
      <form method="post" action="auth.php">

        <div class="form-group">
          <label for="email">Email</label>
          <input name="email" class="form-control" type="text" placeholder="Your email address">
        </div>

        <div class="form-group">
          <label for="password">Password</label>
          <input name="password" class="form-control" type="text" placeholder="Your password">
        </div>

        <div class="form-group">
          <input class="btn btn-primary" type="submit">
        </div>
      </form>
    </div>
  </div>
  <div class="row" style="margin-bottom: 50px;">
    <div class="col-lg-8 col-lg-offset-2" style="text-align: center;">
      <a href="register.php">Register</a>
    </div>
  </div>
</div>
