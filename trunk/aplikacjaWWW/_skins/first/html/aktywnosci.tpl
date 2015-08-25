

					<!-- Tabla -->
					<div class="table">
						<table width="100%" border="0" cellspacing="0" cellpadding="0">
							<tr>
								<th width="17"></th>

								<th><a href="#">Id użytkownika</a></th>
								<th><a href="#">Id czytnika</a></th>
								<th><a href="#">Data</a></th>
								<th><a href="#">Czas logowania (h)</a></th>
								<th><a href="#">Opis</a></th>
							</tr>


							{if not empty($aktywnosci)} {foreach item=aktywnosc
							from=$aktywnosci}
							<tr class="odd">
								<td><input {$aktywnosc.id} type="checkbox" class="checkbox" /></td>
								<td>{$aktywnosc.id}</td>
								<td>{$aktywnosc.czas}</td>
								<td>{$aktywnosc.opis}</td>
								<td><a href="#">Administrator</a></td>
								<td>{if $aktywnosc.aktywny eq 1} aktywny {else} nieaktywny
									{/if}</td>
								
								<td></td>
							</tr>
							{/foreach} {/if}









							
						</table>
						<!-- Koniec Tabeli -->

					
