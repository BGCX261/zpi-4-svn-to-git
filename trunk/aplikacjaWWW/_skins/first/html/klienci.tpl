<div id="header">
<div class="header_s">
{include file="html/logout_panel.tpl"}

{include file="html/menu_nav.tpl"} 
</div>
</div>


<div id="container">
<div class="header_s">
<div id="main">
<div class="position">&nbsp;</div>

<div id="content">

<div class="box">
		
<div class="head_b">

<h2 class="l">Czytniki</h2>
<div class="r">
<input type="text" class="field field_s" />
<input type="submit" class="button" value="szukaj" />
</div>
</div>

<!-- Tabla -->
<div class="table">
<table width="100%" border="0" cellspacing="0" cellpadding="0">
<tr>
<th width="17"></th>
<th><a href="#">Nr</th>
<th><a href="#">Nazwa</a></th>
<th><a href="#">Opis</a></th>
<th><a href="#">Liczba u¿ytkowników</a></th>
<th><a href="#">Status</a></th>
<th width="80" class="ac">Opcje</th>
</tr>
<tr class="o">
	<td><input type="checkbox" class="checkbox" /></td>
	<td><input name="nr_czytnik" class="field field_s" size="12" maxlength="30" /></td>
	<td><input name="name_czytnik" class="field field_s" size="12" maxlength="30" /></td>
	<td><input name="description_czytnik" class="field field_s" size="12" maxlength="30" /></td>
	<td></td>
	<td><p></p></td>
<td><a href="#" class="img add">zapisz</a></td>
</tr>
<tr class="odd">
<td><input type="checkbox" class="checkbox" /></td>
<td>00</td>
<td>XXXXX</td>
<td>Budynek B2, piêtro 2</td>
<td>30</td>
<td>aktywny</td>
<td><a href="#" class="img edit">edytuj</a></td>
</tr>
</table>
                                                
<!-- Koniec Tabeli -->

</div>

{include file="html/menu_step.tpl"}
</div>

</div>

<div id="menu_right">

</div>
<div class="position">&nbsp;</div>                      
</div>
</div>
</div>
