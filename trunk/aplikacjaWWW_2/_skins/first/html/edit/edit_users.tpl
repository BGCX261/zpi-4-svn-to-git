

					<!-- Tabla -->
					<FORM ACTION="?opcja=edit&rodzaj=uzytkownika" METHOD=POST>
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
								{if not empty($userzy)} {foreach item=user from=$userzy}
								<tr class="ood">
									<td><input type="hidden" name=id value={$user.id} /></td>
									<td><input id="surname" name="surname"
										class="field field_s" size="12" maxlength="30"
										value="{$user.nazwisko}" /></td>
									<td><input id="name" name="name" class="field field_s"
										size="12" maxlength="30" value="{$user.imie}" /></td>
									<td><input id="description" name="description"
										class="field field_s" size="12" maxlength="30"
										value="{$user.opis}" /></td>
									<td>Administrator</td>
									<td><select name=status>
									{if $user.aktywny eq 1} 
										<option value="1">aktywna</option>
										<option value="0">nieaktywna</option> 
									{else} 
										<option value="0">nieaktywna</option>
										<option value="1">aktywna</option>
									{/if}
									</select></td>


									<td><a href="?opcje=delete&rodzaj=odcisk&id={$user.id}" >Usun odcisk</a></td>
									<td><input type="submit" value="zapisz" name=zapisz /></td>
								</tr>

								{/foreach} {/if}
								
								<tr class="odd">
									<td></td>
									<td><b>Grupy:</b></td>
									<td></td>
									<td>applet:</td>
									<td> 
									{literal}
									<script src="http://www.java.com/js/deployJava.js"></script>
    <script> 
        var attributes = {
            code:'applet.Applet',  width:200, height:60} ; 
        var parameters = {jnlp_href: '../applet/applet/applet.jnlp',{/literal}uzytkownik_id:'{$user.id}'{literal}} ; 
        deployJava.runApplet(attributes, parameters,  '1.6'); 
    </script>
									{/literal}
									
									</td>
									<td></td>
									<td><a href="?opcje=delete&rodzaj=odcisk&id={$user.id}" >Usun odcisk</a></td>
									<td><input type="submit" value="zapisz" name=zapisz /></td>
								</tr>
								
								{if not empty($grupa)} {foreach item=x from=$grupa}
								<tr class="odd">
									<td>
									{if $get_grupa[$x.id] eq 1}
										<input type="checkbox" name="box[]" value="{$x.id}" class="checkbox" checked /></td>
									{else}
										<input type="checkbox" name="box[]" value="{$x.id}" class="checkbox" /></td>
									{/if}
									
									
									
									<td>{$x.id}</td>
									<td>{$x.nazwa}</td>
									<td>{$x.opis}</td>
									<td></td>
									<td>{if $x.aktywna eq 1} aktywny {else} nieaktywny
										{/if}</td>
									
									<td></td>
									<td></td>
								</tr>
								{/foreach} {/if}
								
								
								<tr class="odd">
									<td></td>
									<td><b>Czytniki</b></td>
									<td></td>
									<td></td>
									<td> 
									
										
									</td>
									<td></td>
									<td></td>
									<td></td>
								</tr>
								
								
								
								
								
								{if not empty($czytniki)} {foreach item=czytnik from=$czytniki}
								<tr class="odd">
									<td>{if $get_czytnik[$czytnik.id] eq 1}
										<input type="checkbox" name="box2[]" value="{$czytnik.id}" class="checkbox" checked /></td>
									{else}
										<input type="checkbox" name="box2[]" value="{$czytnik.id}" class="checkbox" /></td>
									{/if}</td>
									<td>{$czytnik.id}</td>
									<td>{$czytnik.nazwa}</td>
									<td>{$czytnik.opis}</td>
									<td>uzupelnic ??</td>
									<td>{if $czytnik.aktywny eq 1} aktywny {else} nieaktywny
										{/if}</td>
									
									<td></td>
									<td></td>
								</tr>
								{/foreach} {/if}
								
								
								
								
								
								
								
								
								
								
								
								</font>

							</table>

							<!-- Koniec Tabeli -->

						