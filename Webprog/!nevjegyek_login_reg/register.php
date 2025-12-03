<?php
session_start();
include_once('auth.php');
include_once('userstorage.php');
include('utils.php');
// functions
function validate($post, &$data, &$errors): bool
{
  // Adatok "átvétele" és minimális tisztítása
  $data = [
    'username' => trim($post['username'] ?? ''),
    'password' => $post['password'] ?? '',
     'fullname' => trim($post['fullname'] ?? ''),
  ];

  // Felhasználónév ellenőrzése
  if ($data['username'] === '') {
    $errors['username'] = 'A felhasználónév megadása kötelező.';
  }

  // Jelszó ellenőrzése
  if ($data['password'] === '') {
    $errors['password'] = 'A jelszó megadása kötelező.';
  }
  // Fullname ellenőrzése
  if ($data['fullname'] === '') {
    $errors['fullname'] = 'A teljes név megadása kötelező.';
  }

  // Akkor "valid", ha nincs hiba
  return count($errors) === 0;
}
// main
$user_storage = new UserStorage();
$auth = new Auth($user_storage);
$errors = [];
$data = [];
if (count($_POST) > 0) {
  if (validate($_POST, $data, $errors)) {
    if ($auth->user_exists($data['username'])) {
      $errors['global'] = "A felhasználó már létezik";
    } else {
      $auth->register($data);
    redirect('login.php');
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
</head>

<body>
  <h1>Névjegyek</h1>
  <h2>Regisztráció</h2>
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
      <label for="fullname">Full name: </label><br>
      <input type="text" name="fullname" id="fullname" value="<?= $_POST['fullname'] ?? "" ?>">
      <?php if (isset($errors['fullname'])) : ?>
        <span class="error"><?= $errors['fullname'] ?></span>
      <?php endif; ?>
    </div>
    <div>
      <button type="submit">Register</button>
    </div>
  </form>
</body>

</html>