

					<!-- Tabla -->
					<FORM ACTION="?opcja=add&rodzaj=uzytkownika" METHOD=POST>
						<div class="table">
							<table width="100%" border="0" cellspacing="0" cellpadding="0">
								<tr>
									<th width="17"></th>
									<th>{if $sortuj_wg eq 'nazwisko'}<img src="_skins/first/gfx/k2.png" alt=""/>
									{else}<img src="_skins/first/gfx/k1.png" alt=""/>{/if}
										<a href="?opcja=wyswietl&rodzaj=uzytkownikow&sortuj=nazwisko">Nazwisko</a>
									</th>
									<th>{if $sortuj_wg eq 'imie'}<img src="_skins/first/gfx/k2.png" alt=""/>
									{else}<img src="_skins/first/gfx/k1.png" alt=""/>{/if}
										<a href="?opcja=wyswietl&rodzaj=uzytkownikow&sortuj=imie">Imię</a>
									</th>
									<th>{if $sortuj_wg eq 'opis'}<img src="_skins/first/gfx/k2.png" alt=""/>
									{else}<img src="_skins/first/gfx/k1.png" alt=""/>{/if}
										<a href="?opcja=wyswietl&rodzaj=uzytkownikow&sortuj=opis">Opis</a>
									</th>
									<th><a href="#">Dodany przez</a></th>
									<th>{if $sortuj_wg eq 'aktywny'}<img src="_skins/first/gfx/k2.png" alt=""/>
									{else}<img src="_skins/first/gfx/k1.png" alt=""/>{/if}
										<a href="?opcja=wyswietl&rodzaj=uzytkownikow&sortuj=aktywny">Status</a>
									</th>
									<th><a href="#">Nazwa grupy</a></th>
									<th width="80" class="ac">Opcje</th>
								</tr>
								<tr class="o">
									<td><input type="checkbox" class="checkbox" /></td>
									<td><input id="surname" name="surname"
										class="field field_s" size="12" maxlength="30" /></td>
									<td><input id="name" name="name" class="field field_s"
										size="12" maxlength="30" /></td>
									<td><input id="description" name="description"
										class="field field_s" size="12" maxlength="30" /></td>
									<td>Administrator</td>
									<td><p></p></td>
									<td><select name=grupa> {foreach item=x
											from=$grupa}
											<option>{$x.nazwa}</option> {/foreach}

									</select></td>
									<td><input type="submit" value="zapisz" />
									<a href="#" class="img hide_form">anuluj</a></td>
								</tr>
								</font> {if not empty($userzy)} {foreach item=user from=$userzy}
								<tr class="odd">
									<td><input id="{$user.id}" type="checkbox" class="checkbox" /></td>
									<td>{$user.nazwisko}</td>
									<td>{$user.imie}</td>
									<td>{$user.opis}</td>
									<td><a href="#">Administrator</a></td>
									<td>{if $user.aktywny eq 1} aktywny {else} nieaktywny
										{/if}</td>
									<td><a href="#">Administracja</a></td>
									<td><a href="?opcja=edit&rodzaj=uzytkownika&id={$user.id}"
										class="img edit">edytuj</a> <a
										href="?opcja=delete&rodzaj=user&id={$user.id}"
										name="uzytkownik" id="{$user.id}" class="img del">usuń</a></td>
								</tr>
								{/foreach} {/if}
							</table>

							<!-- Koniec Tabeli -->

						
