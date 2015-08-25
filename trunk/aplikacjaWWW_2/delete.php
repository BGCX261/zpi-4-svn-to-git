<?php
require_once "_classes/cGroup.php";
require_once "_classes/cUser.php";

$fp = fopen('delete.log', 'a');

if(isset($_POST['id'], $_POST['tab']))
{
	$id = (int)$_POST['id'];
	$tabela = $_POST['tab'];
	
	fwrite($fp, "[ii] tabela: $tabela, id: $id\n");
	
	switch($tabela)
	{
	case "uzytkownik":
		$sql = new cUser();
		$sql->delete($id);
		$sql->close();
		fwrite($fp, "[ii] usunieto uzytkownika o ID = $id\n");
		break;
	case "grupa":
		$sql = new cGroup();
		$sql->delete($id);
		$sql->close();
		fwrite($fp, "[ii] usunieto grupe o ID = $id\n");
		break;
	default:
		fwrite($fp, "[ee] Niepoprawna nazwa tabeli!\n");
		break;
	}
	
}
else fwrite($fp, "[ee] coś nie tak z POST!\n");

fclose($fp);
?>

