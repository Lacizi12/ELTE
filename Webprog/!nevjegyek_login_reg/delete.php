<?php
session_start();
include_once("contactstorage.php");
include('auth.php');
include('userstorage.php');
include('utils.php');

if (!isset($_POST["id"])) {
  header("Location: index.php");
  exit();
}

$id = $_POST["id"];
$cs = new ContactStorage();
$contact = $cs->findById($id);

if (!$contact) {
  header("Location: index.php");
  exit();
}

// Jogosultságellenőrzés: csak a névjegy tulajdonosa törölheti
$user_storage = new UserStorage();
$auth = new Auth($user_storage);
if (!$auth->is_authenticated() || $contact["creator"] !== $_SESSION["user"]["id"]) {
  die("Nincs jogosultsága a törléshez!");
}

$cs->delete($id);
redirect('index.php');