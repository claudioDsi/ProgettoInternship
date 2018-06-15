-- phpMyAdmin SQL Dump
-- version 4.7.4
-- https://www.phpmyadmin.net/
--
-- Host: 127.0.0.1:3306
-- Creato il: Giu 15, 2018 alle 19:37
-- Versione del server: 5.7.19
-- Versione PHP: 5.6.31

SET SQL_MODE = "NO_AUTO_VALUE_ON_ZERO";
SET AUTOCOMMIT = 0;
START TRANSACTION;
SET time_zone = "+00:00";


/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8mb4 */;

--
-- Database: `internship`
--

-- --------------------------------------------------------

--
-- Struttura della tabella `azienda`
--

DROP TABLE IF EXISTS `azienda`;
CREATE TABLE IF NOT EXISTS `azienda` (
  `IdAzienda` int(11) NOT NULL AUTO_INCREMENT,
  `Username` varchar(50) DEFAULT NULL,
  `Password` char(64) DEFAULT NULL,
  `Privilegi` int(11) DEFAULT NULL,
  `Status` tinyint(1) DEFAULT NULL,
  `Nome` varchar(250) DEFAULT NULL,
  `RagioneSociale` varchar(250) DEFAULT NULL,
  `Indirizzo` varchar(250) DEFAULT NULL,
  `PartitaIva` varchar(11) DEFAULT NULL,
  `CodiceFiscale` varchar(16) DEFAULT NULL,
  `NomeRappr` varchar(250) DEFAULT NULL,
  `CognomeRappr` varchar(250) DEFAULT NULL,
  `NomeResp` varchar(50) DEFAULT NULL,
  `CognomeResp` varchar(50) DEFAULT NULL,
  `TelefonoResp` varchar(20) DEFAULT NULL,
  `EmailResp` varchar(250) DEFAULT NULL,
  `Foro` varchar(250) DEFAULT NULL,
  `Valutazione` float DEFAULT NULL,
  `NumeroTirocini` int(11) DEFAULT NULL,
  `NumTiroCompletati` int(11) DEFAULT NULL,
  PRIMARY KEY (`IdAzienda`)
) ENGINE=InnoDB AUTO_INCREMENT=3 DEFAULT CHARSET=latin1;

--
-- Dump dei dati per la tabella `azienda`
--

INSERT INTO `azienda` (`IdAzienda`, `Username`, `Password`, `Privilegi`, `Status`, `Nome`, `RagioneSociale`, `Indirizzo`, `PartitaIva`, `CodiceFiscale`, `NomeRappr`, `CognomeRappr`, `NomeResp`, `CognomeResp`, `TelefonoResp`, `EmailResp`, `Foro`, `Valutazione`, `NumeroTirocini`, `NumTiroCompletati`) VALUES
(1, 'brucolandia', 'caroteee', 2, 1, 'micron', 'srl', 'via via', '556434334', 'CCDD454545', 'Riccardo', 'Rubei', 'Claudio', 'Di Sipio', '554443', 'email@gmail', 'Avezzano', 5, 0, NULL),
(2, 'dsfdf', 'ssfdf', 2, 0, 'dsfd', 'fddf', 'via Solaria', 'fff', 'f', 'ff', 'ff', 'gb', 'gfdf', '', 'dfgdf', 'dfgf', 0, 0, NULL);

-- --------------------------------------------------------

--
-- Struttura della tabella `richiesta`
--

DROP TABLE IF EXISTS `richiesta`;
CREATE TABLE IF NOT EXISTS `richiesta` (
  `IdRichiesta` int(11) NOT NULL AUTO_INCREMENT,
  `CodStudente` int(11) DEFAULT NULL,
  `CodTirocinio` int(11) DEFAULT NULL,
  `Status` varchar(20) DEFAULT NULL,
  `Cfu` varchar(4) DEFAULT NULL,
  `NomeTutor` varchar(50) DEFAULT NULL,
  `CognomeTutor` varchar(50) DEFAULT NULL,
  `EmailTutor` varchar(50) DEFAULT NULL,
  PRIMARY KEY (`IdRichiesta`)
) ENGINE=InnoDB AUTO_INCREMENT=2 DEFAULT CHARSET=latin1;

--
-- Dump dei dati per la tabella `richiesta`
--

INSERT INTO `richiesta` (`IdRichiesta`, `CodStudente`, `CodTirocinio`, `Status`, `Cfu`, `NomeTutor`, `CognomeTutor`, `EmailTutor`) VALUES
(1, 5, 3, 'accepted', '6', 'Giuseppe', 'Della Penna', 'nome.cognome@univaq.it');

-- --------------------------------------------------------

--
-- Struttura della tabella `tirocinio`
--

DROP TABLE IF EXISTS `tirocinio`;
CREATE TABLE IF NOT EXISTS `tirocinio` (
  `IdTirocinio` int(11) NOT NULL AUTO_INCREMENT,
  `Luogo` varchar(250) DEFAULT NULL,
  `Orario` varchar(20) DEFAULT NULL,
  `NumOre` int(11) DEFAULT NULL,
  `NumMesi` int(11) DEFAULT NULL,
  `Obiettivi` varchar(250) DEFAULT NULL,
  `Modalità` varchar(250) DEFAULT NULL,
  `Facilitazioni` varchar(250) DEFAULT NULL,
  `Settore` varchar(50) DEFAULT NULL,
  `CodTutore` int(11) DEFAULT NULL,
  `CodAzienda` int(11) DEFAULT NULL,
  `Titolo` varchar(50) DEFAULT NULL,
  `Status` tinyint(1) DEFAULT NULL,
  `DataInizio` date DEFAULT NULL,
  `DataFine` date DEFAULT NULL,
  PRIMARY KEY (`IdTirocinio`)
) ENGINE=InnoDB AUTO_INCREMENT=5 DEFAULT CHARSET=latin1;

--
-- Dump dei dati per la tabella `tirocinio`
--

INSERT INTO `tirocinio` (`IdTirocinio`, `Luogo`, `Orario`, `NumOre`, `NumMesi`, `Obiettivi`, `Modalità`, `Facilitazioni`, `Settore`, `CodTutore`, `CodAzienda`, `Titolo`, `Status`, `DataInizio`, `DataFine`) VALUES
(1, 'Kadara', 'dalle 9 alle 13', 4, 8, 'Fare lo schiavo', 'carota', 'fotocopiatrice', 'ortolano', 1, 1, 'Tirocinio 1', 0, NULL, NULL),
(2, 'Kadara', 'dalle 9 alle 13', 4, 8, 'Fare lo schiavo', 'carota', 'fotocopiatrice', 'ortolano', 1, 1, 'Tirocinio 2', 0, NULL, NULL),
(3, 'f', 'f', 4, 5, 'dsf', 'fsddfsd', 'asdf', 'adf', 1, 1, 'Tirocinio 3', 1, '2018-06-20', '2018-10-20'),
(4, 'sdf', 'af', 4, 5, 'dsf', 'fsddfsd', 'asdf', 'adf', 1, 1, 'Tirocinio 4', 0, NULL, NULL);

-- --------------------------------------------------------

--
-- Struttura della tabella `tutore`
--

DROP TABLE IF EXISTS `tutore`;
CREATE TABLE IF NOT EXISTS `tutore` (
  `IdTutore` int(11) NOT NULL AUTO_INCREMENT,
  `Nome` varchar(250) DEFAULT NULL,
  `Cognome` varchar(250) DEFAULT NULL,
  `DataNasc` date DEFAULT NULL,
  `NumTirocini` int(11) DEFAULT NULL,
  `Telefono` varchar(20) DEFAULT NULL,
  `CodAzienda` int(11) DEFAULT NULL,
  `EmailTutore` varchar(100) DEFAULT NULL,
  PRIMARY KEY (`IdTutore`)
) ENGINE=InnoDB AUTO_INCREMENT=7 DEFAULT CHARSET=latin1;

--
-- Dump dei dati per la tabella `tutore`
--

INSERT INTO `tutore` (`IdTutore`, `Nome`, `Cognome`, `DataNasc`, `NumTirocini`, `Telefono`, `CodAzienda`, `EmailTutore`) VALUES
(1, 'Vittorio', 'Cortellessa', '1968-03-10', 5, '65896589', 1, ''),
(2, 'fff', 'ssss', NULL, 0, '5156', 1, ''),
(3, 'sdgdf', 'gdsg', NULL, 0, 'gfsdsfg', 1, ''),
(4, 'gb', 'gf', NULL, 0, 'dgd', 1, ''),
(5, 'dkf', 'jfn', NULL, 0, 'fslfgjh', 1, ''),
(6, 'gdfdgf', 'gfsfggdf', NULL, 0, 'sggdsfg', 1, '');

-- --------------------------------------------------------

--
-- Struttura della tabella `utente`
--

DROP TABLE IF EXISTS `utente`;
CREATE TABLE IF NOT EXISTS `utente` (
  `IdUtente` int(11) NOT NULL AUTO_INCREMENT,
  `Username` varchar(50) DEFAULT NULL,
  `Password` char(64) DEFAULT NULL,
  `Privilegi` int(11) DEFAULT NULL,
  `Nome` varchar(250) DEFAULT NULL,
  `Cognome` varchar(250) DEFAULT NULL,
  `DataNasc` date DEFAULT NULL,
  `LuogoNasc` varchar(250) DEFAULT NULL,
  `Residenza` varchar(250) DEFAULT NULL,
  `CodiceFisc` varchar(16) DEFAULT NULL,
  `Telefono` varchar(20) DEFAULT NULL,
  `Sesso` varchar(10) DEFAULT NULL,
  `EmailUtente` varchar(100) DEFAULT NULL,
  `CorsoLaurea` varchar(250) DEFAULT NULL,
  `Handicap` tinyint(1) DEFAULT NULL,
  `Laurea` varchar(50) DEFAULT NULL,
  `Dottorato` varchar(50) DEFAULT NULL,
  `ScuolaSpec` varchar(50) DEFAULT NULL,
  PRIMARY KEY (`IdUtente`)
) ENGINE=InnoDB AUTO_INCREMENT=10 DEFAULT CHARSET=latin1;

--
-- Dump dei dati per la tabella `utente`
--

INSERT INTO `utente` (`IdUtente`, `Username`, `Password`, `Privilegi`, `Nome`, `Cognome`, `DataNasc`, `LuogoNasc`, `Residenza`, `CodiceFisc`, `Telefono`, `Sesso`, `EmailUtente`, `CorsoLaurea`, `Handicap`, `Laurea`, `Dottorato`, `ScuolaSpec`) VALUES
(1, 'admin', 'admin', 0, 'Admin', 'Admin', '2010-12-11', 'Roma', 'Roma', 'daljhsf', '165456', 'Maschio', 'admin@admin.com', 'Informatica', 0, 'Magistrale', '', ''),
(5, 'vincenzo91', 'vincenzo', 1, 'Vincenzo', 'Battisti', '2000-10-10', 'Arpino', 'Roma', 'lsjhdfd45', '6545612', 'Maschio', '', 'Informatica', 0, 'triennale', 'oiehfd', 'sdsddf'),
(6, 'fsda', 'fc', 1, ' adsf', 'afsd', '0022-01-12', 'ad', 'das', 'das', '156', 'maschio', NULL, 'Informatica', 0, 'triennale', 'oiehfd', 'sdsddf'),
(7, 'dsfg', 'abc', 1, ' fsd', 'fd', '0022-01-12', 'ads', 'ad', 'sdaf', '5156', 'maschio', NULL, 'asdf', 0, 'triennale', 'af', 'afsd'),
(8, 'afsdsd', 'abc', 1, ' asfdsd', 'asdfsd', '0022-01-12', 'ads', 'afsd', 'asfd', '566', 'maschio', NULL, 'Informatica', 0, 'triennale', 'oiehfd', 'sdsddf'),
(9, 'asd', 'as', 1, ' asd', 'asd', '1991-08-16', 'asd', 'asd', 'asd', '65', 'maschio', NULL, 'Informatica', 0, 'triennale', 'oiehfd', 'sdsddf');
COMMIT;

/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
