-- phpMyAdmin SQL Dump
-- version 3.4.9
-- http://www.phpmyadmin.net
--
-- Host: localhost
-- Czas wygenerowania: 08 Mar 2012, 17:23
-- Wersja serwera: 5.5.20
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
-- Struktura tabeli dla  `grupa`
--

CREATE TABLE IF NOT EXISTS `grupa` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `nazwa` varchar(20) COLLATE utf8_polish_ci NOT NULL,
  `opis` text COLLATE utf8_polish_ci,
  `aktywna` tinyint(4) NOT NULL DEFAULT '1',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB  DEFAULT CHARSET=utf8 COLLATE=utf8_polish_ci COMMENT='przechowuje informacje o grupach użytkowników' AUTO_INCREMENT=2 ;

--
-- Zrzut danych tabeli `grupa`
--

INSERT INTO `grupa` (`id`, `nazwa`, `opis`, `aktywna`) VALUES
(1, 'grupa 1', 'dopuszcza dostęp do klientów 1 i 2', 1);

-- --------------------------------------------------------

--
-- Struktura tabeli dla  `grupa_klient`
--

CREATE TABLE IF NOT EXISTS `grupa_klient` (
  `grupa_id` int(11) NOT NULL,
  `klient_id` int(11) NOT NULL,
  PRIMARY KEY (`grupa_id`,`klient_id`),
  KEY `grupa_id` (`grupa_id`),
  KEY `klient_id` (`klient_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_polish_ci;

--
-- Zrzut danych tabeli `grupa_klient`
--

INSERT INTO `grupa_klient` (`grupa_id`, `klient_id`) VALUES
(1, 1),
(1, 2);

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

--
-- Zrzut danych tabeli `grupa_uzytkownik`
--

INSERT INTO `grupa_uzytkownik` (`grupa_id`, `uzytkownik_id`) VALUES
(1, 2),
(1, 3),
(1, 5);

-- --------------------------------------------------------

--
-- Struktura tabeli dla  `klient`
--

CREATE TABLE IF NOT EXISTS `klient` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `nazwa` varchar(30) COLLATE utf8_polish_ci NOT NULL,
  `opis` text COLLATE utf8_polish_ci,
  `aktywny` tinyint(4) NOT NULL DEFAULT '1',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB  DEFAULT CHARSET=utf8 COLLATE=utf8_polish_ci COMMENT='przechowuje informacje o klientach autentykacji' AUTO_INCREMENT=3 ;

--
-- Zrzut danych tabeli `klient`
--

INSERT INTO `klient` (`id`, `nazwa`, `opis`, `aktywny`) VALUES
(1, 'klient 1', 'pierwszy klient systemu', 1),
(2, 'klient 2', 'drugi klient systemu', 1);

-- --------------------------------------------------------

--
-- Struktura tabeli dla  `klient_uzytkownik`
--

CREATE TABLE IF NOT EXISTS `klient_uzytkownik` (
  `klient_id` int(11) NOT NULL,
  `uzytkownik_id` int(11) NOT NULL,
  `blacklist` tinyint(4) NOT NULL DEFAULT '0',
  PRIMARY KEY (`klient_id`,`uzytkownik_id`),
  KEY `uzytkownik_id` (`uzytkownik_id`),
  KEY `klient_id` (`klient_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_polish_ci;

--
-- Zrzut danych tabeli `klient_uzytkownik`
--

INSERT INTO `klient_uzytkownik` (`klient_id`, `uzytkownik_id`, `blacklist`) VALUES
(1, 1, 0),
(1, 3, 1),
(1, 5, 1),
(2, 5, 1);

-- --------------------------------------------------------

--
-- Struktura tabeli dla  `log_aktywnosci`
--

CREATE TABLE IF NOT EXISTS `log_aktywnosci` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `uzytkownik_id` int(11) NOT NULL,
  `klient_id` int(11) NOT NULL,
  `czas` datetime NOT NULL,
  `opis` varchar(255) COLLATE utf8_polish_ci DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `klient_id` (`klient_id`),
  KEY `uzytkownik_id` (`uzytkownik_id`),
  KEY `czas` (`czas`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_polish_ci COMMENT='przechowuje dane aktywności systemu' AUTO_INCREMENT=1 ;

-- --------------------------------------------------------

--
-- Struktura tabeli dla  `odcisk_palca`
--

CREATE TABLE IF NOT EXISTS `odcisk_palca` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `hash` varchar(255) COLLATE utf8_polish_ci NOT NULL,
  `aktywny` smallint(6) NOT NULL DEFAULT '1',
  `uzytkownik_id` int(11) NOT NULL,
  PRIMARY KEY (`id`),
  KEY `uzytkownik_id` (`uzytkownik_id`)
) ENGINE=InnoDB  DEFAULT CHARSET=utf8 COLLATE=utf8_polish_ci COMMENT='tabela przechowująca dane odcisków palca' AUTO_INCREMENT=7 ;

--
-- Zrzut danych tabeli `odcisk_palca`
--

INSERT INTO `odcisk_palca` (`id`, `hash`, `aktywny`, `uzytkownik_id`) VALUES
(2, 'tylko_klient_1', 1, 1),
(3, 'klient_1i2', 1, 2),
(4, 'tylko_klient_2', 1, 3),
(5, '0_klientów', 1, 4),
(6, 'full_blacklist', 1, 5);

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
  PRIMARY KEY (`id`)
) ENGINE=InnoDB  DEFAULT CHARSET=utf8 COLLATE=utf8_polish_ci COMMENT='przechowuje dane o użytkownikach systemu' AUTO_INCREMENT=6 ;

--
-- Zrzut danych tabeli `uzytkownik`
--

INSERT INTO `uzytkownik` (`id`, `imie`, `nazwisko`, `opis`, `aktywny`) VALUES
(1, 'SUCCESS', '1', 'użytkownik z danymi dopuszaczjącymi autoryzację na podstawie blacklisty', 1),
(2, 'SUCCESS', '2', 'użytkownik z danymi dopuszaczjącymi autoryzację na podstawie grup', 1),
(3, 'SUCCESS', '3', 'użytkownik z dostępem do klienta 1 i 2 z grupy i zblacklistowanym klientem 1', 1),
(4, 'FAIL', '4', 'użytkownik bez dostępu do żadnego czytnika zarówno przez grupy jak i blacklistę', 1),
(5, 'FAIL', '5', 'użytkownik z dostępem do czytanika 1 i 2 przez grupy i zblacklistowanymi czytnikami 1 i 2', 1);

--
-- Ograniczenia dla zrzutów tabel
--

--
-- Ograniczenia dla tabeli `grupa_klient`
--
ALTER TABLE `grupa_klient`
  ADD CONSTRAINT `grupa_klient_ibfk_1` FOREIGN KEY (`grupa_id`) REFERENCES `grupa` (`id`),
  ADD CONSTRAINT `grupa_klient_ibfk_2` FOREIGN KEY (`klient_id`) REFERENCES `klient` (`id`);

--
-- Ograniczenia dla tabeli `grupa_uzytkownik`
--
ALTER TABLE `grupa_uzytkownik`
  ADD CONSTRAINT `grupa_uzytkownik_ibfk_1` FOREIGN KEY (`grupa_id`) REFERENCES `grupa` (`id`),
  ADD CONSTRAINT `grupa_uzytkownik_ibfk_2` FOREIGN KEY (`uzytkownik_id`) REFERENCES `uzytkownik` (`id`);

--
-- Ograniczenia dla tabeli `klient_uzytkownik`
--
ALTER TABLE `klient_uzytkownik`
  ADD CONSTRAINT `klient_uzytkownik_ibfk_1` FOREIGN KEY (`klient_id`) REFERENCES `klient` (`id`),
  ADD CONSTRAINT `klient_uzytkownik_ibfk_2` FOREIGN KEY (`uzytkownik_id`) REFERENCES `uzytkownik` (`id`);

--
-- Ograniczenia dla tabeli `log_aktywnosci`
--
ALTER TABLE `log_aktywnosci`
  ADD CONSTRAINT `log_aktywnosci_ibfk_1` FOREIGN KEY (`uzytkownik_id`) REFERENCES `uzytkownik` (`id`),
  ADD CONSTRAINT `log_aktywnosci_ibfk_2` FOREIGN KEY (`klient_id`) REFERENCES `klient` (`id`);

--
-- Ograniczenia dla tabeli `odcisk_palca`
--
ALTER TABLE `odcisk_palca`
  ADD CONSTRAINT `odcisk_palca_ibfk_1` FOREIGN KEY (`uzytkownik_id`) REFERENCES `uzytkownik` (`id`);

/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
