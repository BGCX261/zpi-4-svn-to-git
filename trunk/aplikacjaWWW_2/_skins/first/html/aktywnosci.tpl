

					<!-- Tabla -->
					<div class="table">
						<table width="100%" border="0" cellspacing="0" cellpadding="0">
							<tr>
								<th width="17"></th>

								<th><a href="#"></a></th>
								<th><a href="#">Czas</a></th>
								<th><a href="#">Opis zdarzenia</a></th>
								<th><a href="#"></a></th>
								<th><a href="#"></a></th>
							</tr>


							{if not empty($aktywnosci)} {foreach item=aktywnosc
							from=$aktywnosci}
							<tr class="odd">
								<td></td>
								<td>{$aktywnosc.id}</td>
								<td>{$aktywnosc.czas}</td>
								<td>{$aktywnosc.opis}</td>
								<td></td>
								<td></td>
								
								<td></td>
							</tr>
							{/foreach} {/if}









							
						</table>
						<!-- Koniec Tabeli -->

					
