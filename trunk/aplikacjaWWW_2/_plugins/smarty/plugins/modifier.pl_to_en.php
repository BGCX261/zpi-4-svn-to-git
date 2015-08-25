<?php
/**
 * Smarty plugin
 * @package Smarty
 * @subpackage plugins
 */


/**
 * Smarty upper modifier plugin
 *
 * Type:     modifier<br>
 * Name:     pl_to_en<br>
 * Purpose:  convert string language pl to language en.
 * @author   Smoliñski Piotr (VatheR) vather@poczta.onet.pl
 * @param string
 * @return string
 */
function smarty_modifier_pl_to_en($string)
{
	$string = str_replace(" ", "_", $string);
   $string = str_replace(',', "_", $string);

   $string = str_replace("±", "a", $string); 
   $string = str_replace("æ", "c", $string); 
   $string = str_replace("ê", "e", $string); 
   $string = str_replace("³", "l", $string);    
   $string = str_replace("ñ", "n", $string);   
   $string = str_replace("ó", "o", $string); 
   $string = str_replace("¶", "s", $string);       	     
   $string = str_replace("¿", "z", $string); 
   $string = str_replace("¼", "z", $string); 
 
   $string = str_replace("¡", "¡", $string); 
   $string = str_replace("Æ", "C", $string); 
   $string = str_replace("Ê", "E", $string); 
   $string = str_replace("£", "L", $string);    
   $string = str_replace("Ñ", "N", $string);   
   $string = str_replace("Ó", "O", $string); 
   $string = str_replace("¦", "S", $string);       	     
   $string = str_replace("¯", "Z", $string); 
   $string = str_replace("¬", "Z", $string);   
 
	  
   $string = strtolower($string);   

   $string = str_replace('^', "", $string);
   $string = str_replace('[', "", $string);
   $string = str_replace(']', "", $string);
   $string = str_replace('(', "", $string);	     
   $string = str_replace(')', "", $string);
   $string = str_replace('-', "", $string);
   $string = str_replace('{', "", $string);
   $string = str_replace('}', "", $string);	     
   $string = str_replace('<', "", $string);
   $string = str_replace('>', "", $string);
   $string = str_replace('!', "", $string);
   $string = str_replace('?', "", $string);	
   
   $string = str_replace('.', "", $string);
   $string = str_replace('@', "", $string);	     
   $string = str_replace('#', "", $string);
   $string = str_replace('$', "", $string);
   $string = str_replace('%', "", $string);
   $string = str_replace('&', "", $string);	

   
   $string = str_replace('*', "", $string);
   $string = str_replace('+', "", $string);	     
   $string = str_replace('"', "", $string);
   $string = str_replace(':', "", $string);
   $string = str_replace(';', "", $string);
   $string = str_replace('/', "", $string);	
   $string = str_replace('\\', "", $string);	   
                    
					
   $string = str_replace('__', "_", $string);	
   $string = str_replace('__', "_", $string);	
	
    return $string;
}

?>