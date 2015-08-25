<div class="table_footer">
	<div class="l">Strona {if $smarty.get.page == ""} 1 {else}
		{$smarty.get.page} {/if} z {$nav_step_all}</div>
	<div class="r">
		<!-- 
<a href="#"> << </a>
<a href="#"> < </a>
 -->

		{section name=foo loop=$nav_step_all} {if $smarty.get.page ==
		$smarty.section.foo.iteration} {$smarty.section.foo.iteration} {else}
		<a
			href="?opcja=wyswietl&rodzaj={$smarty.get.rodzaj}&page={$smarty.section.foo.iteration}">{$smarty.section.foo.iteration}</a>
		{/if} {/section}



		<!-- 
<span>...</span>
<a href="#"> > </a>
<a href="#"> >> </a>
 -->
	</div>
</div>