<?php

class cCheck
{
	// class do sprawdzania danych wprowadzanych z formularza
	// wersja rozwojowa
	public function is_numeric()
	{
				
	}
	
	public function is_time($value)
	{
		//todo: funkcja czas
		return 1;
	}
	public function is_text($value)
	{
		if(is_string($value))
		{
			return 1;
		}	
		else
		{
			echo "cCHECK: wartosc nie jest textem";
			exit;
		}
	}
	public function is_int($value)
	{
		if(is_int($value))
			return 1;
		else
		{
			echo "cCHECK: BLAD wartosc nie jest INT";
			exit;
			
		}
	}
	
}