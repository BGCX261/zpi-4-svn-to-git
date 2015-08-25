{include file="html/header.tpl"}

<div id="header">
	<div class="header_s">{include file="html/logout_panel.tpl"}

		{include file="html/menu_nav.tpl"}</div>
</div>


<div id="container">
	<div class="header_s">
		<div id="main">
			<div class="position">&nbsp;</div>

			<div id="content">

				<div class="box">

					<div class="head_b">
					
											<h2 class="l">{$naglowek_tabeli}</h2>
						<div class="r">
							<input type="text" class="field field_s" /> <input type="submit"
								class="button" value="szukaj" />
						</div>
					</div>
					
					
					
					
					{include file="$srodek"}
					

					</div>

					{include file="html/menu_step.tpl"}
				</div>

			</div>

			<div id="menu_right"></div>
			{include file="html/managment.tpl"}
			<div class="position">&nbsp;</div>
			
		</div>
	</div>
</div>





{include file="html/footer.tpl"}
