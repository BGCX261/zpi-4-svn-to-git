{include file="html/header.tpl"}
	<div id="container">
		<div class="header_s">
			<div id="main">
				<div class="position">&nbsp;</div>

				<div id="content">

					<div class="box">

						<div class="head_b">

							<h2 class="l">Użytkownicy</h2>
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
									<th>Nazwisko</th>
									<th>Imię</th>
									<th>Opis</th>
									<th>Dodany przez</th>
									<th>Status</th>
									<th>Nazwa grupy</th>
									<th width="80" class="ac">Opcje</th>
								</tr>
								<!-- formularz dodawania użytkownika -->
								<tr class="od">
									<td>&nbsp;</td>
									<td><input id="surname" name="surname" class="field field_s" size="12" maxlength="30" /></td>
									<td><input id="name" name="name" class="field field_s" size="12" maxlength="30" /></td>
									<td><input id="description" name="description" class="field field_s" size="12" maxlength="30" /></td>
									<td>{$login}</td>
									<td><p></p></td>
									<td>
										<select id="group" name="group" class="field field_s">
											{section name=g loop=$grupy}
											<option value="{$grupy[g].id}">{$grupy[g].nazwa}</option>
											{/section}
										</select>
									</td>
									<td>
										<a href="#" onclick="add_user()" name="uzytkownik" class="img add">zapisz</a>
										<a href="#" name="uzytkownik" class="img hide_form">usuń</a>
									</td>
								</tr>
								<!-- uzytkownicy -->
								{section name=i loop=$uzytkownicy}
								<tr class="odd">
									<td><input onclick="zaznaczenie()" type="checkbox" class="checkbox" /></td>
									<td>{$uzytkownicy[i].nazwisko}</td>
									<td>{$uzytkownicy[i].imie}</td>
									<td>{$uzytkownicy[i].opis}</td>
									<td><a href="#">{$login}</a></td>
									<td>{$uzytkownicy[i].status}</td>
									<td><a href="#">{$uzytkownicy[i].grupa}</a></td>
									<td>
										<a href="index.php?page=edituser&amp;imie={$uzytkownicy[i].imie}&amp;nazwisko={$uzytkownicy[i].nazwisko}&amp;opis={$uzytkownicy[i].opis}&amp;status={$uzytkownicy[i].status}&amp;grupa={$uzytkownicy[i].grupa}&amp;id={$uzytkownicy[i].id}" class="img edit">edytuj</a>
										<a href="#" name="uzytkownik" id="{$uzytkownicy[i].id}" class="img del">usuń</a>
									</td>
								</tr>
								{/section}
							</table>
							<!-- Koniec Tabeli -->
							
							<div class="table_footer">
								<div class="l">Strona {$strona} - {$rekord_n} z {$rekordow}</div>
								<div class="r">
									{$porcjowanie}
								</div>
							</div>
						</div>

					</div>

				</div>

				<div id="menu_right">
					<div class="box">
						<div class="box_head">
							<h2>Panel kontrolny</h2>
						</div>
						<div class="box_content">
							<p><a id="additem" href="#">Dodaj nowego użytkownika</a></p>
							<div class="cl">&nbsp;</div>
							<p><a id="selectallitems" href="#">Zaznacz wszystkich użytkowników</a></p>
							<div class="cl">&nbsp;</div>
							<p><a id="deleteitems" href="#">Usuń wybranych użytkowników</a></p>
							<div class="cl">&nbsp;</div>
							<div class="cl">&nbsp;</div>
							<div class="sort">
							<label>Sortuj wg.</label>
							<div class="cl">&nbsp;</div>
							<select class="surname">
								<option value="">Nazwiska</option>
							</select>
							<select class="group">
								<option value="">Grupy</option>
							</select>
							<select class="state">
								<option value="">Statusu</option>
							</select>
						</div>
					</div>
				</div>
			</div>
{include file="html/footer.tpl"}

