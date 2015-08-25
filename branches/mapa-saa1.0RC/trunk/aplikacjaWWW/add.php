<?php
require_once "_classes/cUser.php";
require_once "_classes/cGroup.php";


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
		$id = $sql->add($imie, $nazwisko, $opis, 1); 
		$sql->close();
		//fwrite($fp, "[ii] dodalem uzytkownika o ID = $id\n");
		break;
	case 'grupa':
		$sql = new cGroup();
		$nazwa = $_POST['name'];
		$opis = $_POST['desc'];
		$id = $sql->add($nazwa, $opis, 1);
		$sql->close();
		//fwrite($fp, "[ii] dodalem grupe o ID = $id\n");
		break;
	default:
		//fwrite($fp, "[ee] Niepoprawna tabela\n");
		break;
	}
}

?>

