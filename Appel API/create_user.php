<!DOCTYPE html>
<html lang="en">

<head>
    <?php include('head.php ') ?>
</head>
<!--/head-->

<body class="homepage">

    <?php
        include('header.php');
    ?><!--/header-->


    <section>
        <div class="col-lg-8 col-lg-offset-2">
            <form action="register.php" method="post">

                <label for="email">e-mail:</label>
                <input type="text" name="email" class="form-control">

                <label for="password">Password</label>
                <input type="password" name="password" class="form-control">

                <input type="submit" value="Create user">
            </form>
        </div>
    </section>


    <script src="js/jquery.js"></script>
    <script src="js/bootstrap.min.js"></script>
    <script src="js/jquery.prettyPhoto.js"></script>
    <script src="js/jquery.isotope.min.js"></script>
    <script src="js/main.js"></script>
    <script src="js/wow.min.js"></script>
</body>
</html>
