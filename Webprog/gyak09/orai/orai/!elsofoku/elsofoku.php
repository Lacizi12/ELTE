<?php

declare(strict_types=1);
print_r($_GET);
//függvények
function elsofoku(float $a, float $b): float
{
    return -$b / $a;
}

function validate($get, &$data, &$hibak): bool
{

    if (!isset($get["a"])) {
        $hibak[] = 'Hiányzik az "a" paraméter';
    } else if ((trim($get["a"]) === '')) {
        $hibak[] = 'Hiányzik az "a"';
    } else if (filter_var($get["a"], FILTER_VALIDATE_FLOAT) === false) {
        $hibak[] = '"a" nem szám';
    } else{
        $a = (float)$get["a"];
        if ($a === 0.0) {
            $hibak[] = '"a" nem lehet 0';
        } else {
            $data['a'] = $a;
        }

    }

    if (!isset($get["b"])) {
        $hibak[] = 'Hiányzik az "b" paraméter';
    } else if ((trim($get["b"]) === '')) {
        $hibak[] = 'Hiányzik az "b"';
    } else if (filter_var($get["b"], FILTER_VALIDATE_FLOAT) === false) {
        $hibak[] = '"b" nem szám';
    } else {
       $data['b'] = (float)$get["b"];
    }
    return count($hibak)===0;
}

$hibak = [];
$data = [];
//beolvaás és ellenőrzés
if (count($_GET) > 0){
if (validate($_GET, $data, $hibak)) {
    //beolvasás
    $a = $data["a"];
    $b = $data["b"];
    //feldolgozás
    $x = elsofoku($a, $b);
}
}
//print_r($hibak);
?>
<!DOCTYPE html>
<html lang="en">

<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Elsőfokú</title>
</head>

<body>
    <?php if (count($hibak) > 0): ?>
        <ul>
            <?php foreach ($hibak as $hiba): ?>
                <li><?= $hiba ?></li>
            <?php endforeach ?>
        </ul>
    <?php endif ?>

    <form action="elsofoku.php" method="get">
        a=<input type="text" name="a" value="<?=$_GET['a']?? '' ?>"><br>
        b=<input type="text" name="b" value="<?=$_GET['b']??'' ?>"><br>
        <button type="submit">Beállít</button>
    </form>
    <?php if (isset($x)) : ?>
        Az egyenlet megoldása: x=<?= $x ?>
    <?php endif ?>
</body>

</html>