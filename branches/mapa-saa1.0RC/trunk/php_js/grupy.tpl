{include file="html/header.tpl"}
	<div id="container">
		<div class="header_s">
			<div id="main">
				<div class="position">&nbsp;</div>

				<div id="content">

					<div class="box">

						<div class="head_b">
							<h2 class="l">Grupy</h2>
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
							<th>Nazwa grupy</th>
							<th>Opis</th>
							<th>Utworzona przez</th>
							<th>Liczba użytkowników</th>
							<th>Status</th>
							<th width="80" class="ac">Opcje</th>
						</tr>
						<tr class="od">
							<td>&nbsp;</td>
							<td><input id="name" name="name_grupa" class="field field_s" size="12" maxlength="30" /></td>
							<td><input id="description" name="description_grupa" class="field field_s" size="12" maxlength="30" /></td>
							<td><p>{$login}</p></td>
							<td><p></p></td>
							<td><p></p></td>
							<td>
								<a href="#" onclick="add_group()" name="grupa" class="img add">zapisz</a>
								<a href="#" name="grupa" class="img hide_form">usuń</a>
							</td>
						</tr>
						{section name=i loop=$grupy}
						<tr class="odd">
							<td><input onclick="zaznaczenie()" type="checkbox" class="checkbox" /></td>
							<td><a href="#">{$grupy[i].nazwa}</a></td>
							<td>{$grupy[i].opis}</td>
							<td><a href="#">{$login}</a></td>
							<td>{$grupy[i].uzytkownikow}</td>
							<td>{$grupy[i].aktywna}</td>
							<td>
								<a href="#" class="img edit_group">edytuj</a>
								<a href="#" name="grupa" id="{$grupy[i].id}" class="img del">usuń</a>
							</td>
						</tr>
						{/section}
					</table>

					<div class="table_footer">
						<div class="l">Strona {$strona} - {$rekord_n} z {$rekordow}</div>
						<div class="r">
							{$porcjowanie}
							</div>
						</div>
				</div>
				<!-- Koniec Tabeli -->

				</div>

			</div>

				<div id="menu_right">
					<div class="box">
						<div class="box_head">
						<h2>Panel kontrolny</h2>
						</div>
						<div class="box_content">
							<p><a id="additem" href="#">Utwórz nową grupę</a></p>
							<div class="cl">&nbsp;</div>
							<p><a id="selectallitems" href="#">Zaznacz wszystkie grupy</a></p>
							<div class="cl">&nbsp;</div>
							<p><a id="deleteitems" href="#">Usuń wybrane grupy</a></p>
							<div class="cl">&nbsp;</div>
							<div class="cl">&nbsp;</div>
							<div class="sort">
								<label>Sortuj wg.</label>
								<div class="cl">&nbsp;</div>
								<select class="name_group">
									<option value="">Nazwy</option>
								</select>
								<select class="state_group">
									<option value="">Statusu</option>
								</select>
							</div>
						</div>
					</div>
				</div>
{include file="html/footer.tpl"}

