

					<!-- Tabla -->
					<FORM ACTION="?opcja=edit&rodzaj=grupe" METHOD=POST>
						<div class="table">
							<table width="100%" border="0" cellspacing="0" cellpadding="0">
								<tr>
									<th width="17"></th>
									<th><a href="#">Nazwa grupy</a></th>
									<th><a href="#">Opis</a></th>
									<th><a href="#">Utworzona przez</a></th>
									<th><a href="#">Liczba użytkowników</a></th>
									<th><a href="#">Status</a></th>
									<th width="80" class="ac">Opcje</th>
								</tr>
								<tr class="o">
									<td><input type="checkbox" class="checkbox" /></td>
									<td><input name="name_grupa" class="field field_s"
										size="12" maxlength="30" /></td>
									<td><input name="description_grupa" class="field field_s"
										size="12" maxlength="30" /></td>
									<td><p>Administrator</p></td>
									<td><p></p></td>
									<td><p></p></td>
									<td><input type="submit" value="zapisz" /></td>
								</tr>
								</form>
								{if not empty($grupa)} {foreach item=x from=$grupa}
								<tr class="odd">
									<td><input type="checkbox" class="checkbox" /></td>
									<td><a href="#">{$x.nazwa}</a></td>
									<td>{$x.opis}</td>
									<td><a href="#">Administrator</a></td>
									<td>30</td>
									<td>{if $x.aktywna eq 1} aktywna {else} nieaktywna {/if}</td>
									<td><a href="#" class="img edit_group">edytuj</a><a
										href="#" name="grupa" id="{$x.id}" class="img del">usuń</a></td>
								</tr>
								{/foreach} {/if}




							</table>

							<!-- Koniec Tabeli -->

						