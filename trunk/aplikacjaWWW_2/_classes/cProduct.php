<?php

    require_once 'cImage.php';


class cProduct extends cImage
{
	public $countsearch=0;
	public function dodaj()
	{
		$data=date("Y-m-d");
		$this->query("INSERT INTO `new_products` (`id_products`, `date`, `name`, `description`, `status`) VALUES (NULL, '$data', 'tytul', 'opis', '0');");
		return $this->get_id();
	}
	public function viewcategory($id,$page="0")
	{
		$this->is_int($id);
		
		
				$this->query("SELECT count(id_products) as ile FROM new_cat_prod WHERE id_products=$id");
				$ile=mysql_fetch_array($this->wynik);
				$this->ile_ksiazek=$ile[ile];
				if($page>0)	
					$page=$page*4;		
				$this->query("SELECT DISTINCT(Pr.id_products),Ca.id_category,Pr.date,Pr.name ,Pr.description,Pi.url
								FROM new_products as Pr,new_cat_prod as Ca,new_pictures as Pi WHERE Pr.status=1 AND  
								Ca.id_category=$id AND Ca.id_products=Pr.id_products AND Pr.id_products=Pi.id_products GROUP BY Pr.id_products ORDER BY Pr.priorytet DESC,Pr.date DESC");
				$wynik=$this->wynik;
				while($rzad = mysql_fetch_array($wynik))
				{
					$idimage=$this->image($rzad[id_products]);
					$produkt[]=array("id_products"=>$rzad[id_products],"id_category"=>$rzad[id_category],"date"=>$rzad[date],"name"=>$rzad[name],"description"=>$rzad[description],"picture"=>$idimage);
				}
				return $produkt;
	}
		
}
?>