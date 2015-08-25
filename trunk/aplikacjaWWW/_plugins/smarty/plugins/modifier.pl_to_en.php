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
 * @author   Smoli�ski Piotr (VatheR) vather@poczta.onet.pl
 * @param string
 * @return string
 */
function smarty_modifier_pl_to_en($string)
{
	$string = str_replace(" ", "_", $string);
   $string = str_replace(',', "_", $string);

   $string = str_replace("�", "a", $string); 
   $string = str_replace("�", "c", $string); 
   $string = str_replace("�", "e", $string); 
   $string = str_replace("�", "l", $string);    
   $string = str_replace("�", "n", $string);   
   $string = str_replace("�", "o", $string); 
   $string = str_replace("�", "s", $string);       	     
   $string = str_replace("�", "z", $string); 
   $string = str_replace("�", "z", $string); 
 
   $string = str_replace("�", "�", $string); 
   $string = str_replace("�", "C", $string); 
   $string = str_replace("�", "E", $string); 
   $string = str_replace("�", "L", $string);    
   $string = str_replace("�", "N", $string);   
   $string = str_replace("�", "O", $string); 
   $string = str_replace("�", "S", $string);       	     
   $string = str_replace("�", "Z", $string); 
   $string = str_replace("�", "Z", $string);   
 
	  
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