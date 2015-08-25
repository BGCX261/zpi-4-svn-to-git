

					<!-- Tabla -->
					<FORM ACTION="?opcja=edit&rodzaj=grupe" METHOD=POST>
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
								{if not empty($grupa)} {foreach item=x from=$grupa}
								<tr class="ood">
									<td>
									<input name="nr_grupy" class="field field_s"
										size="12" maxlength="30"  value="{$x.id}" hidden/>
									</td>
									<td><input name="name_grupa" class="field field_s"
										size="12" maxlength="30" value="{$x.nazwa}"  /></td>
									<td><input name="description_grupa" class="field field_s"
										size="12" maxlength="30"  value="{$x.opis}"/></td>
									<td><p>Administrator</p></td>
									<td><p></p></td>
									<td><p><select name=status>
									{if $x.aktywna eq 1} 
										<option value="1">aktywna</option>
										<option value="0">nieaktywna</option> 
									{else} 
										<option value="0">nieaktywna</option>
										<option value="1">aktywna</option>
									{/if}
									</select>
									
									
									</p></td>
									<td><input type="submit" value="zapisz" /></td>
								</tr>
								
								{/foreach} {/if}
								<tr class="o">
									<td></td>
									<td><b>Czytniki:</b></td>
									<td></td>
									<td></td>
									<td></td>
									<td></td>
									
									<td></td>
								</tr>
								{if not empty($czytniki)} {foreach item=czytnik from=$czytniki}
								<tr class="odd">
									<td>
									{if $get_czytnik[$czytnik.id] eq 1}
										<input type="checkbox" name="box[]" value="{$czytnik.id}" class="checkbox" checked /></td>
									{else}
										<input type="checkbox" name="box[]" value="{$czytnik.id}" class="checkbox" /></td>
									{/if}
									
									
									
									<td>{$czytnik.id}</td>
									<td>{$czytnik.nazwa}</td>
									<td>{$czytnik.opis}</td>
									<td></td>
									<td>{if $czytnik.aktywny eq 1} aktywny {else} nieaktywny
										{/if}</td>
									
									<td></td>
								</tr>
								{/foreach} {/if}
								
								
								
</form>



							</table>

							<!-- Koniec Tabeli -->

						