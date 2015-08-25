

					<!-- Tabla -->
					<FORM ACTION="?opcja=edit&rodzaj=czytnik" METHOD="post">
						<div class="table">
							<table width="100%" border="0" cellspacing="0" cellpadding="0">
								<tr>
									<th width="17"></th>
									<th>Nr</th>
									<th>Nazwa</th>
									<th>Opis</th>
									<!-- <th>Liczba użytkowników</th> -->
									<th>Status</th>
									<th width="80" class="ac">Opcje</th>
								</tr>
								
								
								
								{if not empty($czytniki)}
								 {foreach item=czytnik from=$czytniki}
						
								
								<tr class="ood">
								
									<td></td>
									<td>
									<input name="nr_czytnik" class="field field_s"
										size="12" maxlength="30"  value="{$czytnik.id}" hidden/>
									<input name="nr_czytnik" class="field field_s"
										size="12" maxlength="30"  value="{$czytnik.id}" disabled/></td>
									<td><input name="name_czytnik" class="field field_s"
										size="12" maxlength="30"  value="{$czytnik.nazwa}" /></td>
									<td><input name="description_czytnik"
										class="field field_s" size="12" maxlength="30"  value="{$czytnik.opis}" /></td>
									<!-- <td></td> -->
									<td><select name=status>
											<option value="1">aktywny</option>
											<option value="0">nieaktywny</option>
									</select></td>
									<td><input type="submit" value="zapisz" /></td>
									</form>
								</tr>
{/foreach} {/if}
								
							</table>

							<!-- Koniec Tabeli -->

						