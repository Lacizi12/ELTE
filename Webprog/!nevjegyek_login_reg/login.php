<?php
//Indítok sessiont. Az Auth osztály konstruktorában $_SESSION["user"]-t használunk
session_start();
include('auth.php');
include_once("userstorage.php");
include('utils.php');

//print_r($_POST);
// functions

function validate($post, &$data, &$errors): bool
{
  // Adatok "átvétele" és minimális tisztítása
  $data = [
    'username' => trim($post['username'] ?? ''),
    'password' => $post['password'] ?? '',
  ];

  // Felhasználónév ellenőrzése
  if ($data['username'] === '') {
    $errors['username'] = 'A felhasználónév megadása kötelező.';
  }

  // Jelszó ellenőrzése
  if ($data['password'] === '') {
    $errors['password'] = 'A jelszó megadása kötelező.';
  }

  // Akkor "valid", ha nincs hiba
  return count($errors) === 0;
}

$user_storage = new UserStorage();
$auth = new Auth($user_storage);
$data = [];
$errors = [];
//Csak akkor fut, ha a formot elküldték
if (count($_POST) > 0) {
  if (validate($_POST, $data, $errors)) {
    // Beolvasás: $data
    //Megpróbálja megkeresni a megadott username+password pároshoz tartozó usert.
    $auth_user = $auth->authenticate($data['username'], $data['password']);
    if (!$auth_user) {
      $errors['global'] = "A usernév-jelszó páros nem megfelelő";
    } else {
      $auth->login($auth_user);
      //„A redirect_url paraméter csak akkor jön létre, ha a login oldalra egy automatikus átirányítás vezet (pl. egy védett oldalról). Ha a felhasználó közvetlenül nyitja meg a login.php-t, nincs információ arról, honnan érkezett, ezért nincs redirect_url.”
      if (isset($_GET["redirect_url"])) {
        redirect($_GET["redirect_url"]);
      } else {
        redirect('index.php');
      }
    }
  }
}
?>
<!DOCTYPE html>
<html lang="en">

<head>
  <meta charset="UTF-8">
  <meta name="viewport" content="width=device-width, initial-scale=1.0">
  <title>Document</title>
  <style>
    input~span {
      color: red;
      font-size: smaller;
    }
  </style>
</head>

<body>
  <h1>Névjegyek</h1>
  <h2>Bejelentkezés</h2>
  <?php if (isset($errors['global'])) : ?>
    <p><span class="error"><?= $errors['global'] ?></span></p>
  <?php endif; ?>
  <form action="" method="post">
    <div>
      <label for="username">Username: </label><br>
      <input type="text" name="username" id="username" value="<?= $_POST['username'] ?? "" ?>">
      <?php if (isset($errors['username'])) : ?>
        <span class="error"><?= $errors['username'] ?></span>
      <?php endif; ?>
    </div>
    <div>
      <label for="password">Password: </label><br>
      <input type="password" name="password" id="password">
      <?php if (isset($errors['password'])) : ?>
        <span class="error"><?= $errors['password'] ?></span>
      <?php endif; ?>
    </div>
    <div>
      <button type="submit">Login</button>
    </div>
  </form>
  <p>
    <a href="register.php">Register</a>
  </p>
</body>

</html>