

					<!-- Tabla -->
					<FORM ACTION="?opcja=edit&rodzaj=uzytkownika" METHOD=POST>
						<div class="table">
							<table width="100%" border="0" cellspacing="0" cellpadding="0">
								<tr>
									<th width="17"></th>
									<th><a href="#">Nazwisko</a></th>
									<th><a href="#">Imię</a></th>
									<th><a href="#">Opis</a></th>
									<th><a href="#">Dodany przez</a></th>
									<th><a href="#">Status</a></th>
									<th><a href="#">Nazwa grupy</a></th>
									<th width="80" class="ac">Opcje</th>
								</tr>
								{if not empty($userzy)} {foreach item=user from=$userzy}
								<tr class="o">
									<td><input type="hidden" name=id value={$user.id} /><input
										type="checkbox" class="checkbox" /></td>
									<td><input id="surname" name="surname"
										class="field field_s" size="12" maxlength="30"
										value="{$user.nazwisko}" /></td>
									<td><input id="name" name="name" class="field field_s"
										size="12" maxlength="30" value="{$user.imie}" /></td>
									<td><input id="description" name="description"
										class="field field_s" size="12" maxlength="30"
										value="{$user.opis}" /></td>
									<td>Administrator</td>
									<td><p></p></td>


									<td><select name=grupa> {foreach item=x
											from=$grupa}
											<option>{$x.nazwa}</option> {/foreach}

									</select></td>
									<td><input type="submit" value="zapisz" name=zapisz /></td>
								</tr>

								{/foreach} {/if}
								</font>

							</table>

							<!-- Koniec Tabeli -->

						