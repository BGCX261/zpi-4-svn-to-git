<?php
require_once "_classes/cGroup.php";
require_once "_classes/cUser.php";


if(isset($_POST['id'], $_POST['tab']))
{
	$id = (int)$_POST['id'];
	$tabela = $_POST['tab'];
	
	
	switch($tabela)
	{
	case "uzytkownik":
		$sql = new cUser();
		$sql->delete($id);
		$sql->close();
		break;
	case "grupa":
		$sql = new cGroup();
		$sql->delete($id);
		$sql->close();

		break;
	default:
		break;
	}
	
}
else
	if(isset($_POST['ids'], $_POST['tab']))
	{
		$ids = explode(',', $_POST['ids']);
		$tabela = $_POST['tab'];
		
		switch($tabela)
		{
		case "uzytkownik":
			$sql = new cUser();
			foreach($ids as $id) $sql->delete((int)$id);
			$sql->close();
			break;
		case "grupa":
			$sql = new cGroup();
			foreach($ids as $id) $sql->delete((int)$id);
			$sql->close();
			break;
		default:
			//fwrite($fp, "[ee] Niepoprawna nazwa tabeli!\n");
			break;
		}
		
	}
	
?>

