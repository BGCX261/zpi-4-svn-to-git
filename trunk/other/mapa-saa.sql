-- phpMyAdmin SQL Dump
-- version 3.4.9
-- http://www.phpmyadmin.net
--
-- Host: localhost
-- Czas wygenerowania: 01 Kwi 2012, 12:55
-- Wersja serwera: 5.5.21
-- Wersja PHP: 5.3.10

SET SQL_MODE="NO_AUTO_VALUE_ON_ZERO";
SET time_zone = "+00:00";


/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8 */;

--
-- Baza danych: `mapa-saa`
--

-- --------------------------------------------------------

--
-- Struktura tabeli dla  `czytnik`
--

CREATE TABLE IF NOT EXISTS `czytnik` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `nazwa` varchar(30) COLLATE utf8_polish_ci NOT NULL,
  `opis` text COLLATE utf8_polish_ci,
  `aktywny` tinyint(4) NOT NULL DEFAULT '1',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB  DEFAULT CHARSET=utf8 COLLATE=utf8_polish_ci COMMENT='przechowuje informacje o czytnikach autentykacji' AUTO_INCREMENT=3 ;

-- --------------------------------------------------------

--
-- Struktura tabeli dla  `czytnik_uzytkownik`
--

CREATE TABLE IF NOT EXISTS `czytnik_uzytkownik` (
  `czytnik_id` int(11) NOT NULL,
  `uzytkownik_id` int(11) NOT NULL,
  `blacklist` tinyint(4) NOT NULL DEFAULT '0',
  PRIMARY KEY (`czytnik_id`,`uzytkownik_id`),
  KEY `uzytkownik_id` (`uzytkownik_id`),
  KEY `czytnik_id` (`czytnik_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_polish_ci;

-- --------------------------------------------------------

--
-- Struktura tabeli dla  `grupa`
--

CREATE TABLE IF NOT EXISTS `grupa` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `nazwa` varchar(20) COLLATE utf8_polish_ci NOT NULL,
  `opis` text COLLATE utf8_polish_ci,
  `aktywna` tinyint(4) NOT NULL DEFAULT '1',
  PRIMARY KEY (`id`),
  UNIQUE KEY `nazwa` (`nazwa`)
) ENGINE=InnoDB  DEFAULT CHARSET=utf8 COLLATE=utf8_polish_ci COMMENT='przechowuje informacje o grupach użytkowników' AUTO_INCREMENT=3 ;

-- --------------------------------------------------------

--
-- Struktura tabeli dla  `grupa_czytnik`
--

CREATE TABLE IF NOT EXISTS `grupa_czytnik` (
  `grupa_id` int(11) NOT NULL,
  `czytnik_id` int(11) NOT NULL,
  PRIMARY KEY (`grupa_id`,`czytnik_id`),
  KEY `grupa_id` (`grupa_id`),
  KEY `czytnik_id` (`czytnik_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_polish_ci;

-- --------------------------------------------------------

--
-- Struktura tabeli dla  `grupa_panelu`
--

CREATE TABLE IF NOT EXISTS `grupa_panelu` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `nazwa` varchar(255) COLLATE utf8_polish_ci NOT NULL,
  `aktywna` smallint(6) NOT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `nazwa` (`nazwa`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_polish_ci COMMENT='grupy użytkowników panelu administracyjnego' AUTO_INCREMENT=1 ;

-- --------------------------------------------------------

--
-- Struktura tabeli dla  `grupa_panelu_uprawnienie`
--

CREATE TABLE IF NOT EXISTS `grupa_panelu_uprawnienie` (
  `grupa_panelu_id` int(11) NOT NULL,
  `uprawnienie_id` int(11) NOT NULL,
  PRIMARY KEY (`grupa_panelu_id`,`uprawnienie_id`),
  KEY `uprawnienie_id` (`uprawnienie_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_polish_ci;

-- --------------------------------------------------------

--
-- Struktura tabeli dla  `grupa_panelu_uzytkownik_panelu`
--

CREATE TABLE IF NOT EXISTS `grupa_panelu_uzytkownik_panelu` (
  `grupa_panleu_id` int(11) NOT NULL,
  `uzytkownik_panelu_id` int(11) NOT NULL,
  PRIMARY KEY (`grupa_panleu_id`,`uzytkownik_panelu_id`),
  KEY `uzytkownik_panelu_id` (`uzytkownik_panelu_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_polish_ci;

-- --------------------------------------------------------

--
-- Struktura tabeli dla  `grupa_uzytkownik`
--

CREATE TABLE IF NOT EXISTS `grupa_uzytkownik` (
  `grupa_id` int(11) NOT NULL,
  `uzytkownik_id` int(11) NOT NULL,
  PRIMARY KEY (`grupa_id`,`uzytkownik_id`),
  KEY `uzytkownik_id` (`uzytkownik_id`),
  KEY `grupa_id` (`grupa_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_polish_ci;

-- --------------------------------------------------------

--
-- Struktura tabeli dla  `log_aktywnosci`
--

CREATE TABLE IF NOT EXISTS `log_aktywnosci` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `uzytkownik_id` int(11) DEFAULT NULL,
  `czytnik_id` int(11) NOT NULL,
  `czas` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP,
  `opis` varchar(255) COLLATE utf8_polish_ci DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `czytnik_id` (`czytnik_id`),
  KEY `uzytkownik_id` (`uzytkownik_id`),
  KEY `czas` (`czas`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_polish_ci COMMENT='przechowuje dane aktywności systemu' AUTO_INCREMENT=1 ;

-- --------------------------------------------------------

--
-- Struktura tabeli dla  `odcisk_palca`
--

CREATE TABLE IF NOT EXISTS `odcisk_palca` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `hash` varchar(400) COLLATE utf8_polish_ci NOT NULL,
  `aktywny` smallint(6) NOT NULL DEFAULT '1',
  `uzytkownik_id` int(11) NOT NULL,
  `utworzono` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP,
  PRIMARY KEY (`id`),
  KEY `uzytkownik_id` (`uzytkownik_id`)
) ENGINE=InnoDB  DEFAULT CHARSET=utf8 COLLATE=utf8_polish_ci COMMENT='tabela przechowująca dane odcisków palca' AUTO_INCREMENT=12 ;

-- --------------------------------------------------------

--
-- Struktura tabeli dla  `uprawnienie`
--

CREATE TABLE IF NOT EXISTS `uprawnienie` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `nazwa` varchar(255) COLLATE utf8_polish_ci NOT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `nazwa` (`nazwa`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_polish_ci COMMENT='specyficzne nazwy uprawnień dostępu w panelu' AUTO_INCREMENT=1 ;

-- --------------------------------------------------------

--
-- Struktura tabeli dla  `uzytkownik`
--

CREATE TABLE IF NOT EXISTS `uzytkownik` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `imie` varchar(20) COLLATE utf8_polish_ci NOT NULL,
  `nazwisko` varchar(30) COLLATE utf8_polish_ci NOT NULL,
  `opis` text COLLATE utf8_polish_ci,
  `aktywny` tinyint(4) NOT NULL DEFAULT '1',
  `usuniety` smallint(6) NOT NULL DEFAULT '0',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB  DEFAULT CHARSET=utf8 COLLATE=utf8_polish_ci COMMENT='przechowuje dane o użytkownikach systemu' AUTO_INCREMENT=7 ;

-- --------------------------------------------------------

--
-- Struktura tabeli dla  `uzytkownik_panelu`
--

CREATE TABLE IF NOT EXISTS `uzytkownik_panelu` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `imie` varchar(20) COLLATE utf8_polish_ci NOT NULL,
  `nazwisko` varchar(30) COLLATE utf8_polish_ci NOT NULL,
  `opis` text COLLATE utf8_polish_ci,
  `login` varchar(30) COLLATE utf8_polish_ci NOT NULL,
  `haslo` varchar(30) COLLATE utf8_polish_ci NOT NULL,
  `superadmin` smallint(6) NOT NULL,
  `aktywny` smallint(6) NOT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `login` (`login`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_polish_ci COMMENT='użytkownicy panelu administracyjnego' AUTO_INCREMENT=1 ;

-- --------------------------------------------------------

--
-- Struktura tabeli dla tabeli `graf_czytnikow`
--

CREATE TABLE IF NOT EXISTS `graf_czytnikow` (
  `czytnik1` int(11) NOT NULL,
  `czytnik2` int(11) NOT NULL,
  PRIMARY KEY (`czytnik1`,`czytnik2`),
  KEY `czytnik1` (`czytnik1`),
  KEY `czytnik2` (`czytnik2`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_polish_ci;

--
-- Ograniczenia dla zrzutów tabel
--

--
-- Ograniczenia dla tabeli `czytnik_uzytkownik`
--
ALTER TABLE `czytnik_uzytkownik`
  ADD CONSTRAINT `czytnik_uzytkownik_ibfk_1` FOREIGN KEY (`czytnik_id`) REFERENCES `czytnik` (`id`),
  ADD CONSTRAINT `czytnik_uzytkownik_ibfk_2` FOREIGN KEY (`uzytkownik_id`) REFERENCES `uzytkownik` (`id`);

--
-- Ograniczenia dla tabeli `grupa_czytnik`
--
ALTER TABLE `grupa_czytnik`
  ADD CONSTRAINT `grupa_czytnik_ibfk_1` FOREIGN KEY (`grupa_id`) REFERENCES `grupa` (`id`),
  ADD CONSTRAINT `grupa_czytnik_ibfk_2` FOREIGN KEY (`czytnik_id`) REFERENCES `czytnik` (`id`);

--
-- Ograniczenia dla tabeli `grupa_panelu_uprawnienie`
--
ALTER TABLE `grupa_panelu_uprawnienie`
  ADD CONSTRAINT `grupa_panelu_uprawnienie_ibfk_1` FOREIGN KEY (`grupa_panelu_id`) REFERENCES `grupa_panelu` (`id`),
  ADD CONSTRAINT `grupa_panelu_uprawnienie_ibfk_2` FOREIGN KEY (`uprawnienie_id`) REFERENCES `uprawnienie` (`id`);

--
-- Ograniczenia dla tabeli `grupa_panelu_uzytkownik_panelu`
--
ALTER TABLE `grupa_panelu_uzytkownik_panelu`
  ADD CONSTRAINT `grupa_panelu_uzytkownik_panelu_ibfk_1` FOREIGN KEY (`grupa_panleu_id`) REFERENCES `grupa_panelu` (`id`),
  ADD CONSTRAINT `grupa_panelu_uzytkownik_panelu_ibfk_2` FOREIGN KEY (`uzytkownik_panelu_id`) REFERENCES `uzytkownik_panelu` (`id`);

--
-- Ograniczenia dla tabeli `grupa_uzytkownik`
--
ALTER TABLE `grupa_uzytkownik`
  ADD CONSTRAINT `grupa_uzytkownik_ibfk_1` FOREIGN KEY (`grupa_id`) REFERENCES `grupa` (`id`),
  ADD CONSTRAINT `grupa_uzytkownik_ibfk_2` FOREIGN KEY (`uzytkownik_id`) REFERENCES `uzytkownik` (`id`);

--
-- Ograniczenia dla tabeli `log_aktywnosci`
--
ALTER TABLE `log_aktywnosci`
  ADD CONSTRAINT `log_aktywnosci_ibfk_1` FOREIGN KEY (`uzytkownik_id`) REFERENCES `uzytkownik` (`id`),
  ADD CONSTRAINT `log_aktywnosci_ibfk_2` FOREIGN KEY (`czytnik_id`) REFERENCES `czytnik` (`id`);

--
-- Ograniczenia dla tabeli `odcisk_palca`
--
ALTER TABLE `odcisk_palca`
  ADD CONSTRAINT `odcisk_palca_ibfk_1` FOREIGN KEY (`uzytkownik_id`) REFERENCES `uzytkownik` (`id`);

--
-- Ograniczenia dla tabeli `graf_czytnikow`
--
ALTER TABLE `graf_czytnikow`
  ADD CONSTRAINT `graf_czytnikow_ibfk_1` FOREIGN KEY (`czytnik1`) REFERENCES `czytnik` (`id`) ON DELETE CASCADE ON UPDATE CASCADE,
  ADD CONSTRAINT `graf_czytnikow_ibfk_2` FOREIGN KEY (`czytnik2`) REFERENCES `czytnik` (`id`) ON DELETE CASCADE ON UPDATE CASCADE;

/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
