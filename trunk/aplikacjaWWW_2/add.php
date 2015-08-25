<?php
require_once "_classes/cUser.php";
require_once "_classes/cGroup.php";
//require_once "_configs/config.php";

$fp = fopen('add.log', 'a');

if(isset($_POST['tab']))
{
	$tabela = $_POST['tab'];
	switch($tabela)
	{
	case 'uzytkownik':
		$sql = new cUser();
		$imie = $_POST['name'];
		$nazwisko = $_POST['surname'];
		$opis = $_POST['desc'];
		$grupa = $_POST['group'];
		$id = $sql->add($imie, $nazwisko, $opis, 1); // nie ma m,ożliwości w formularzu wybrać statusu
		$sql->close();
		fwrite($fp, "[ii] dodalem uzytkownika o ID = $id\n");
		break;
	case 'grupa':
		$sql = new cGroup();
		$nazwa = $_POST['name'];
		$opis = $_POST['desc'];
		$id = $sql->add($nazwa, $opis, 1);
		$sql->close();
		fwrite($fp, "[ii] dodalem grupe o ID = $id\n");
		break;
	default:
		fwrite($fp, "[ee] Niepoprawna tabela\n");
		break;
	}
}
else
	fwrite($fp, "[ee] Coś z postem nie tak\n");

fclose($fp);
?>

