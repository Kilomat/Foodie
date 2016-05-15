<?php
if (isset($_GET['uid']) && !empty($_GET['uid'])) {

  $res = getUser($_GET['uid']);
  $user = $res->User[0];
}
else {
  $error = 'An error occured';
}
?>

<div class="main" style="margin-top: 50px;">
  <div class="row">
    <div class="col-lg-8 col-lg-offset-2" style="text-align: center;">
      <h1 style="color: black;">My informations</h1>
      <table style="display: inline-block">
        <?php foreach ($user as $key => $value ) { ?>
        <tr>
          <td style="width: 150px;">
            <strong><?php echo $key; ?>:</strong>
          </td>
          <td>
            <?php echo $value; ?>
          </td>
        </tr>

        <?php } ?>
      </table>

      <a href="account.php" class="btn btn-primary">Back</a>
    </div>
  </div>
</div>
