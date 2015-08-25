

					<!-- Tabla -->
					<FORM ACTION="?opcja=add&rodzaj=czytnik" METHOD=POST>
						<div class="table">
							<table width="100%" border="0" cellspacing="0" cellpadding="0">
								<tr>
									<th width="17"></th>
									<th><a href="#">Nr</th>
									<th><a href="#">Nazwa</a></th>
									<th><a href="#">Opis</a></th>
									<!-- <th><a href="#">Liczba użytkowników</a></th> -->
									<th><a href="#">Status</a></th>
									<th width="80" class="ac">Opcje</th>
								</tr>
								<tr class="odd">
									<td><input type="checkbox" class="checkbox" /></td>
									<td></td>
									<td><input name="name_czytnik" class="field field_s"
										size="12" maxlength="30" /></td>
									<td><input name="description_czytnik"
										class="field field_s" size="12" maxlength="30" /></td>
									<td></td>
									<td><select name=status>
											<option value="1">aktywny</option>
											<option value="0">nieaktywny</option>
									</select></td>
									<td><input type="submit" value="zapisz" /></td>
									</form>
								</tr>


								{if not empty($czytniki)} {foreach item=czytnik from=$czytniki}
								<tr class="odd">
									<td><input type="checkbox" class="checkbox" /></td>
									<td>{$czytnik.id}</td>
									<td>{$czytnik.nazwa}</td>
									<td>{$czytnik.opis}</td>
									<!-- <td>uzupelnic ??</td> -->
									<td>{if $czytnik.aktywny eq 1} aktywny {else} nieaktywny
										{/if}</td>
									
									<td><a href="?opcja=edit&rodzaj=czytnik&id={$czytnik.id}" class="img edit">edytuj</a><a href="#"
										name="uzytkownik" id="{$user.id}" class="img del">usuń</a></td>
								</tr>
								{/foreach} {/if}






								
							</table>

							<!-- Koniec Tabeli -->

						
