<?php
session_start();
//Egy konkrét névjegy adatait jeleníti meg.
include_once("contactstorage.php");
//include('auth.php');
//include('userstorage.php');
include('utils.php');

//melyik névjegy?
if (!isset($_GET["id"])) {
    // Ha valaki simán beírja az URL-t id nélkül, akkor visszadobjuk a listára.
    header("Location: index.php");
    exit();
}
$id = $_GET["id"];
$cs = new ContactStorage();
$contact = $cs->findById($id);
if (!$contact) {
    //ha pl törölték az adott id-jú névjegyet
    header("Location: index.php");
    exit();
}

if ($contact["creator"] !== $_SESSION["user"]["id"]) {
    die("not allowed");
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
    <h2>Részletek</h2>
    <dl>
        <dt>Név</dt>
        <dd><?= $contact["name"] ?></dd>

        <dt>Email</dt>
        <dd><?= implode("<br>", $contact["emails"]) ?></dd>
    </dl>
    <div>
        <a href="edit.php?id=<?= $contact["id"] ?>">Módosít</a>
        <!--törlésnél, módosításnál post kell! Get esetén az id látszik az URL-ben.Könnyű véletlenül eltörölni valamit, ha valaki újratölti az oldalt. Köbyvjelzőben / előzményben visszakattintható → veszélyes.-->
        <!--<form action="delete.php?id=<?= $contact["id"] ?>" method="post">
        <button>Töröl</button>
    </form>-->
        <form action="delete.php" method="post">
            <button name="id" value="<?= $contact["id"] ?>">Töröl</button>
        </form>
    </div>
</body>

</html>