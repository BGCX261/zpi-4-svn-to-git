{include file="html/header.tpl"}
<div id="container">
		<div class="header_s">
			<div id="main">
				<div class="position">&nbsp;</div>

				<div id="content">

					<div class="box">

						<div class="head_b">

							<h2 class="l">Edycja użytkownika</h2>
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
								<tr>
									<td>&nbsp;</td>
									<td><input id="surname" name="surname" class="field field_s" size="12" maxlength="30" value="{$nazwisko}" /></td>
									<td><input id="name" name="name" class="field field_s" size="12" maxlength="30" value={$imie} /></td>
									<td><input id="description" name="description" class="field field_s" size="12" maxlength="30" value="{$opis}" /></td>
									<td>{$login}</td>
									<td><p></p></td>
									<td>
										<select id="group" name="group" class="field field_s">
											{section name=g loop=$grupy}
											{if $grupa eq $grupy[g].id}
											<option selected="selected" value="{$grupy[g].id}">{$grupy[g].nazwa}</option>
											{else}
											<option value="{$grupy[g].id}">{$grupy[g].nazwa}</option>
											{/if}
											{/section}
										</select>
									</td>
									<td>
										<a href="#" onclick="edit_user()" name="uzytkownik" class="img edit">zapisz</a>
										<a href="uzytkownicy.html" name="uzytkownik" class="img hide_form">anuluj</a>
									</td>
								</tr>
							</table>
							
							<div class="table_footer">
								<div class="l">Strona 1-12 z 20</div>
								<div class="r">
									<a href="#">1</a>
									<a href="#">2</a>
									<a href="#">3</a>
									<a href="#">4</a>
									<span>...</span>
									<a href="#">następna</a>
									<a href="#">wszystkie</a>
								</div>
							</div>
						</div>

					</div>

				</div>

{include file="html/footer.tpl"}

