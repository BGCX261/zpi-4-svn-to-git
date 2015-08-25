<?php
error_reporting( 1 ) ;

header('Content-type: text/html; charset=utf-8');

include "_plugins/smarty/Smarty.class.php";
include "_configs/config.php";

function __autoload($class_name) 
{
	require_once '_classes/'.$class_name . '.php';
}

$smarty = new Smarty();
$sesja=new cSession();
$grupa=new cGroup();
$user=new cUser();
$finger=new cFinger();
$log=new cLog();
$czytnik=new cCzytnik();

$smarty->template_dir = '_skins/first';
$smarty->compile_dir = '_var/tmp_c';
$smarty->cache_dir = '_var/cache';
$smarty->config_dir = '_configs';
$smarty->caching=0;
$smarty->compile_check = true;//sprawdza czy plik szablonu lub konfiguracja uleg�y zmienie
$smarty->cache_lifetime=3600;//czas �ycia pliku w cache 1 godzina

$sesja->start();


$opcja=$_GET['opcja'];
$rodzaj=$_GET['rodzaj'];
$sortuj='';

if($_GET[sortuj])
$sortuj=$_GET[sortuj];

$smarty->assign("slownik",$lang);
$smarty->assign("lang","pl");
$language="pl";

if($_POST['username']=="admin" && $_POST['password']=="admin")
{
	$sesja->set("zalogowany","admin");
}


if($sesja->get("zalogowany")=="admin")
{


	switch($opcja)
	{
		case "wyswietl":
		{
			
			switch($rodzaj)
			{
				case "grupe":
				{
					
					
					$smarty->assign("nav_step_all",$grupa->get_rows());
					$smarty->assign('grupa',$grupa->read(0,$_GET['page']));
					$smarty->assign('plik_css',"style.css");
					$smarty->assign('srodek',"html/grupy.tpl");
					$smarty->assign('nazwa_tabeli',"nazwa_tab_grupa");
					
					
					$smarty->assign('sortuj_wg', $sortuj);
					$smarty->assign('dodaj', "add_group");
					$smarty->assign('zaznacz', "select_groups");
					$smarty->assign('usun', "delete_groups");
					$smarty->assign('naglowek_tabeli', 'Grupy');
					
					
					
					$smarty->display('index.tpl');
					
					break;
				}
				case "uzytkownikow":
				{
						$smarty->assign('grupa',$grupa->read());
						$smarty->assign("nav_step_all",$user->get_rows());		
						$smarty->assign('userzy',$user->read(0,$_GET['page']));
						$smarty->assign('plik_css',"style.css");
						$smarty->assign('srodek',"html/users.tpl");
						$smarty->assign('nazwa_tabeli',"nazwa_tab_user");
						
						
						$smarty->assign('dodaj', "add_user");
						$smarty->assign('zaznacz', "select_users");
						$smarty->assign('usun', "delete_users");
						$smarty->assign('naglowek_tabeli', 'Użytkownicy');
						
						$smarty->display('index.tpl');
						break;
				}
				case "klientow":
					{
						$smarty->assign("nav_step_all",$czytnik->get_rows());
						$smarty->assign('czytniki',$czytnik->read(0,$_GET['page']));
						$smarty->assign('plik_css',"style.css");
						$smarty->assign('srodek',"html/czytniki.tpl");
						$smarty->assign('nazwa_tabeli',"nazwa_tab_czytnik");
						
						$smarty->assign('czytniki',$czytnik->read());
						$smarty->assign('sortuj_wg', $sortuj);
						$smarty->assign('naglowek_tabeli', 'Klienci');
						$smarty->display('index.tpl');
						break;
					}
				case "aktywnosci":
						{
							
							$smarty->assign("nav_step_all",$log->get_rows());
							$smarty->assign('aktywnosci',$log->read());
							$smarty->assign('plik_css',"style.css");
							$smarty->assign('srodek',"html/aktywnosci.tpl");
							$smarty->assign('nazwa_tabeli',"nazwa_tab_aktiv");
							
							$smarty->assign('sortuj_wg', $sortuj);
							$smarty->assign('dodaj', "add_aktiv");
							$smarty->assign('zaznacz', "select_activs");
							$smarty->assign('usun', "delete_aktivs");
							$smarty->assign('naglowek_tabeli', 'AktywnoĹ›ci');
							$smarty->display('index.tpl');
							break;
						}
				
				default:
					break;
			}
					
		
			exit;
			break;
		} // wyswietl
		case "wyloguj":
			{
				$sesja->del("zalogowany");
				$smarty->assign('plik_css',"login.css");
	$smarty->display('html/login.tpl');
				//$smarty->display('index.tpl');
				
				break;
			}//wyloguj
		
		case "add":
			{
				switch($rodzaj)
				{
					
					case "grupe":
						{
							$grupa->add($_POST['name_grupa'],$_POST['description_grupa'],$_POST['status']);
							$log->add("ADD: grupe : {$_POST['name_grupa']}");
							$smarty->assign("nav_step_all",$grupa->get_rows());
							$smarty->assign('grupa',$grupa->read());
							$smarty->assign('plik_css',"style.css");
							$smarty->assign('srodek',"html/grupy.tpl");
							$smarty->assign('nazwa_tabeli',"nazwa_tab_grupa");
							
							$smarty->assign('dodaj', "add_group");
							$smarty->assign('zaznacz', "select_groups");
							$smarty->assign('usun', "delete_groups");
							$smarty->display('index.tpl');
							
							
							break;
						}
					case "uzytkownika":
						{
							$smarty->assign('grupa',$grupa->read());
							$user->add($_POST['name'],$_POST['surname'],$_POST['description'],1);
							$log->add("ADD: user : {$_POST['name']} {$_POST['surname']}");
							$smarty->assign("nav_step_all",$user->get_rows());
							$smarty->assign('userzy',$user->read());
							$smarty->assign('dodaj', "add_user");
							$smarty->assign('zaznacz', "select_users");
							$smarty->assign('usun', "delete_users");
							$smarty->assign('popup', '1');
							
							$smarty->assign('plik_css',"style.css");
							$smarty->assign('srodek',"html/users.tpl");
							$smarty->assign('nazwa_tabeli',"nazwa_tab_user");
							$smarty->display('index.tpl');
							
							break;
						}
						
						case "czytnik":
							{
									
								$czytnik->add($_POST['name_czytnik'],$_POST['description_czytnik'],$_POST['status']);
								$log->add("ADD: czytnik : {$_POST['name_czytnik']}");
								$smarty->assign("nav_step_all",$czytnik->get_rows());
						$smarty->assign('czytniki',$czytnik->read());
						$smarty->assign('plik_css',"style.css");
						$smarty->assign('srodek',"html/czytniki.tpl");
						$smarty->assign('nazwa_tabeli',"nazwa_tab_czytnik");
						$smarty->display('index.tpl');
						break;
							}
					
				}
				break;	
					
					
				
				
				
				
				
				
				
				
				
				
			}
			
			
			case "edit":
				{
					switch($rodzaj)
					{
							
						case "grupe":
							{
								if($_POST['nr_grupy'])
								{
									$grupa->edit($_POST['nr_grupy'],$_POST['name_grupa'],$_POST['description_grupa'],$_POST['status']);
									$log->add("EDIT: grupe : {$_POST['name_grupa']}");
									$smarty->assign('srodek',"html/grupy.tpl");
									$smarty->assign('grupa',$grupa->read());
									$grupa->del_grupa_czytnik($_POST['nr_grupy']);
									$grupa->add_grupa_czytnik($_POST['nr_grupy'], $_POST['box']);
									
									
									
									
									
								}								
								else
								{
									$smarty->assign('czytniki',$czytnik->read());
									$smarty->assign('grupa',$grupa->read($_GET['id']));
									$smarty->assign('srodek',"html/edit/edit_grupy.tpl");
									$smarty->assign('get_czytnik',$grupa->get_grupa_czytnik($_GET['id']));
									
									
								}
								$smarty->assign('dodaj', "add_group");
								$smarty->assign('zaznacz', "select_groups");
								$smarty->assign('usun', "delete_groups");
								$smarty->assign('nazwa_tabeli',"nazwa_tab_grupa");
								
								
								
								
								
								
								//$smarty->assign("nav_step_all",$grupa->get_rows());
								//
								$smarty->assign('plik_css',"style.css");
								
								$smarty->display('index.tpl');
									
									
								break;
							}
						
			
						case "czytnik":
							{
								
								
								if($_POST['nr_czytnik'])
								{
									$czytnik->edit($_POST['nr_czytnik'],$_POST['name_czytnik'],$_POST['description_czytnik'],$_POST['status']);
									$smarty->assign('czytniki',$czytnik->read());
									$log->add("EDIT: czytnik : {$_POST['name_czytnik']}");
									$smarty->assign('srodek',"html/czytniki.tpl");
									
									
								}
								else
								{
									
									$smarty->assign('czytniki',$czytnik->read($_GET[id]));
								
									$smarty->assign('srodek',"html/edit/edit_czytniki.tpl");
								
									
								}	
								
								$smarty->assign('nazwa_tabeli',"nazwa_tab_czytnik");
								//$smarty->assign("nav_step_all",$czytnik->get_rows());
								
								$smarty->assign('plik_css',"style.css");
								
								$smarty->display('index.tpl');
								break;
							}
							case "uzytkownika":
								{
									if($_POST[id])
									{
										$user->edit($_POST['id'],$_POST['name'],$_POST['surname'],$_POST['description'],$_POST['status']);
										$smarty->assign('userzy',$user->read());
										$smarty->assign('srodek',"html/users.tpl");
										$log->add("EDIT: user : {$_POST['name']} {$_POST['surname']}");
										//$smarty->assign('grupa',$grupa->read());
										
										$grupa->del_grupa_user($_POST['id']);
										$grupa->add_grupa_user($_POST['id'], $_POST['box']);
										$czytnik->del_czytnik_user($_POST['id']);
										$czytnik->add_czytnik_user($_POST['id'], $_POST['box2']);
										
										
										
									}
									else
									{
										$smarty->assign('userzy',$user->read($_GET[id]));
										$smarty->assign('srodek',"html/edit/edit_users.tpl");
										
										$smarty->assign('get_grupa',$grupa->get_grupa_user($_GET['id']));
										$smarty->assign('get_czytnik',$czytnik->get_czytnik_user($_GET['id']));
									}
									
									$smarty->assign('dodaj', "add_user");
									$smarty->assign('zaznacz', "select_users");
									$smarty->assign('usun', "delete_users");
									$smarty->assign('popup', '0');
									$smarty->assign('nazwa_tabeli',"nazwa_tab_user");
									$smarty->assign('grupa',$grupa->read());
									$smarty->assign('czytniki',$czytnik->read());
									//$smarty->assign("nav_step_all",$user->get_rows());
									//$smarty->assign('userzy',$user->read());
									$smarty->assign('plik_css',"style.css");
									//$smarty->assign('srodek',"html/edit/edit_users.tpl");
									$smarty->display('index.tpl');
										
									break;
								}		
					}
					break;
						
						
			
			
			
			
			
			
			
			
			
			
				}
				
				
				
				case "delete":
					{
						switch($rodzaj)
						{
								
							case "grupe":
								{
									$grupa->delete($_GET["id"]);
										
									$smarty->assign("nav_step_all",$grupa->get_rows());
									$smarty->assign('grupa',$grupa->read());
									$smarty->assign('plik_css',"style.css");
									$smarty->assign('srodek',"html/grupy.tpl");
									
									$smarty->assign('dodaj', "add_group");
									$smarty->assign('zaznacz', "select_groups");
									$smarty->assign('usun', "delete_groups");
									$smarty->display('index.tpl');
										
										
									break;
								}
							case "uzytkownika":
								{
										
									$user->delete($_GET["id"]);
										
									$smarty->assign("nav_step_all",$user->get_rows());
									$smarty->assign('userzy',$user->read());
									$smarty->assign('plik_css',"style.css");
									$smarty->assign('srodek',"html/users.tpl");
									
									
									
									$smarty->assign('dodaj', "add_user");
									$smarty->assign('zaznacz', "select_users");
									$smarty->assign('usun', "delete_users");
									$smarty->assign('popup', '0');
									$smarty->display('index.tpl');
										
									break;
								}
				
							case "czytnik":
								{
										
									$czytnik->delete($_GET["id"]);
										
									$smarty->assign("nav_step_all",$czytnik->get_rows());
									$smarty->assign('czytniki',$czytnik->read());
									$smarty->assign('plik_css',"style.css");
									$smarty->assign('srodek',"html/czytniki.tpl");
									$smarty->display('index.tpl');
									break;
								}
							case "odcisk":
								{
									$finger->delete($_GET['id']);
									$smarty->assign('grupa',$grupa->read());
									$user->add($_POST['name'],$_POST['surname'],$_POST['description'],1);
									$log->add("ADD: user : {$_POST['name']} {$_POST['surname']}");
									$smarty->assign("nav_step_all",$user->get_rows());
									$smarty->assign('userzy',$user->read());
										
									$smarty->assign('plik_css',"style.css");
									$smarty->assign('srodek',"html/users.tpl");
									$smarty->assign('nazwa_tabeli',"nazwa_tab_user");
									$smarty->display('index.tpl');
									
									
									$smarty->assign('grupa',$grupa->read());
									$smarty->assign("nav_step_all",$user->get_rows());
									$smarty->assign('userzy',$user->read(0,$_GET['page']));
									$smarty->assign('plik_css',"style.css");
									$smarty->assign('srodek',"html/users.tpl");
									$smarty->assign('nazwa_tabeli',"nazwa_tab_user");
									
									
									$smarty->assign('dodaj', "add_user");
									$smarty->assign('zaznacz', "select_users");
									$smarty->assign('usun', "delete_users");
									$smarty->assign('naglowek_tabeli', 'Użytkownicy');
									
									$smarty->display('index.tpl');
									
									
									break;
								}
									
						}
						break;
							
							
				
				
				
				
				
				
				
				
				
				
					}
		
		default:
			$smarty->assign('grupa',$grupa->read());
			$smarty->assign('plik_css',"style.css");
			$smarty->assign('srodek',"html/grupy.tpl");
			$smarty->assign('dodaj', "add_group");
			$smarty->assign('zaznacz', "select_groups");
			$smarty->assign('usun', "delete_groups");
			$smarty->assign('naglowek_tabeli', 'Grupy');
			$smarty->display('index.tpl');
			//$smarty->assign('plik_css',"style.css");
			//$smarty->assign('srodek',"html/grupy.tpl");
			//$smarty->display('index.tpl');
				
	}
	
	

}
else //nie zalogowany wyswietl panel logowania
{
	$smarty->assign('plik_css',"login.css");
	$smarty->display('html/login.tpl');
	//$smarty->display('index.tpl');
	
	
}



?>

