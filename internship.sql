-- phpMyAdmin SQL Dump
-- version 4.7.4
-- https://www.phpmyadmin.net/
--
-- Host: 127.0.0.1
-- Creato il: Giu 13, 2018 alle 23:09
-- Versione del server: 10.1.29-MariaDB
-- Versione PHP: 7.2.0

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

CREATE TABLE `azienda` (
  `IdAzienda` int(11) NOT NULL,
  `Username` varchar(50) NOT NULL,
  `Password` char(64) NOT NULL,
  `Privilegi` int(11) NOT NULL,
  `Status` tinyint(1) NOT NULL,
  `Nome` varchar(250) NOT NULL,
  `RagioneSociale` varchar(250) NOT NULL,
  `Indirizzo` varchar(250) NOT NULL,
  `PartitaIva` varchar(11) NOT NULL,
  `CodiceFiscale` varchar(16) NOT NULL,
  `NomeRappr` varchar(250) NOT NULL,
  `CognomeRappr` varchar(250) NOT NULL,
  `NomeResp` varchar(50) NOT NULL,
  `CognomeResp` varchar(50) NOT NULL,
  `TelefonoResp` varchar(20) NOT NULL,
  `EmailResp` varchar(250) NOT NULL,
  `Foro` varchar(250) NOT NULL,
  `Valutazione` float NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

--
-- Dump dei dati per la tabella `azienda`
--

INSERT INTO `azienda` (`IdAzienda`, `Username`, `Password`, `Privilegi`, `Status`, `Nome`, `RagioneSociale`, `Indirizzo`, `PartitaIva`, `CodiceFiscale`, `NomeRappr`, `CognomeRappr`, `NomeResp`, `CognomeResp`, `TelefonoResp`, `EmailResp`, `Foro`, `Valutazione`) VALUES
(1, 'brucolandia', 'caroteee', 2, 0, 'micron', 'srl', 'via via', '556434334', 'CCDD454545', 'Riccardo', 'Rubei', 'Claudio', 'Di Sipio', '554443', 'email@gmail', 'Avezzano', 5),
(2, 'dsfdf', 'ssfdf', 2, 0, 'dsfd', 'fddf', 'via Solaria', 'fff', 'f', 'ff', 'ff', 'gb', 'gfdf', '', 'dfgdf', 'dfgf', 0);

-- --------------------------------------------------------

--
-- Struttura della tabella `richiesta`
--

CREATE TABLE `richiesta` (
  `IdRichiesta` int(11) NOT NULL,
  `CodStudente` int(11) NOT NULL,
  `CodTirocinio` int(11) NOT NULL,
  `Status` varchar(20) NOT NULL,
  `Cfu` varchar(4) NOT NULL,
  `NomeTutor` varchar(50) NOT NULL,
  `CognomeTutor` varchar(50) NOT NULL,
  `EmailTutor` varchar(50) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

--
-- Dump dei dati per la tabella `richiesta`
--

INSERT INTO `richiesta` (`IdRichiesta`, `CodStudente`, `CodTirocinio`, `Status`, `Cfu`, `NomeTutor`, `CognomeTutor`, `EmailTutor`) VALUES
(1, 5, 3, 'in attesa', '6', 'Giuseppe', 'Della Penna', 'nome.cognome@univaq.it');

-- --------------------------------------------------------

--
-- Struttura della tabella `tirocinio`
--

CREATE TABLE `tirocinio` (
  `IdTirocinio` int(11) NOT NULL,
  `Luogo` varchar(250) NOT NULL,
  `Orario` varchar(20) NOT NULL,
  `NumOre` int(11) NOT NULL,
  `NumMesi` int(11) NOT NULL,
  `Obiettivi` varchar(250) NOT NULL,
  `Modalità` varchar(250) NOT NULL,
  `Facilitazioni` varchar(250) NOT NULL,
  `Settore` varchar(50) NOT NULL,
  `CodTutore` int(11) NOT NULL,
  `CodAzienda` int(11) NOT NULL,
  `Titolo` varchar(50) NOT NULL,
  `Status` tinyint(1) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

--
-- Dump dei dati per la tabella `tirocinio`
--

INSERT INTO `tirocinio` (`IdTirocinio`, `Luogo`, `Orario`, `NumOre`, `NumMesi`, `Obiettivi`, `Modalità`, `Facilitazioni`, `Settore`, `CodTutore`, `CodAzienda`, `Titolo`, `Status`) VALUES
(1, 'Kadara', 'dalle 9 alle 13', 4, 8, 'Fare lo schiavo', 'carota', 'fotocopiatrice', 'ortolano', 1, 1, 'Tirocinio 1', 0),
(2, 'Kadara', 'dalle 9 alle 13', 4, 8, 'Fare lo schiavo', 'carota', 'fotocopiatrice', 'ortolano', 1, 1, 'Tirocinio 2', 0),
(3, 'f', 'f', 4, 5, 'dsf', 'fsddfsd', 'asdf', 'adf', 1, 1, 'Tirocinio 3', 0),
(4, 'sdf', 'af', 4, 5, 'dsf', 'fsddfsd', 'asdf', 'adf', 1, 1, 'Tirocinio 4', 0);

-- --------------------------------------------------------

--
-- Struttura della tabella `tutore`
--

CREATE TABLE `tutore` (
  `IdTutore` int(11) NOT NULL,
  `Nome` varchar(250) NOT NULL,
  `Cognome` varchar(250) NOT NULL,
  `DataNasc` varchar(20) NOT NULL,
  `NumTirocini` int(11) NOT NULL,
  `Telefono` varchar(20) NOT NULL,
  `CodAzienda` int(11) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

--
-- Dump dei dati per la tabella `tutore`
--

INSERT INTO `tutore` (`IdTutore`, `Nome`, `Cognome`, `DataNasc`, `NumTirocini`, `Telefono`, `CodAzienda`) VALUES
(1, 'Vittorio', 'Cortellessa', '03-10-1968', 5, '65896589', 1),
(2, 'fff', 'ssss', 'aaaa', 0, '5156', 1),
(3, 'sdgdf', 'gdsg', 'gdsgdf', 0, 'gfsdsfg', 1),
(4, 'gb', 'gf', 'gfd', 0, 'dgd', 1),
(5, 'dkf', 'jfn', 'ljdsfh', 0, 'fslfgjh', 1),
(6, 'gdfdgf', 'gfsfggdf', 'dgfd', 0, 'sggdsfg', 1);

-- --------------------------------------------------------

--
-- Struttura della tabella `utente`
--

CREATE TABLE `utente` (
  `IdUtente` int(11) NOT NULL,
  `Username` varchar(50) NOT NULL,
  `Password` char(64) NOT NULL,
  `Privilegi` int(11) NOT NULL,
  `Nome` varchar(250) NOT NULL,
  `Cognome` varchar(250) NOT NULL,
  `DataNasc` varchar(30) NOT NULL,
  `LuogoNasc` varchar(250) NOT NULL,
  `Residenza` varchar(250) NOT NULL,
  `CodiceFisc` varchar(16) NOT NULL,
  `Telefono` varchar(20) NOT NULL,
  `Sesso` varchar(10) NOT NULL,
  `Email` varchar(30) NOT NULL,
  `CorsoLaurea` varchar(250) NOT NULL,
  `Handicap` tinyint(1) NOT NULL,
  `Laurea` varchar(50) NOT NULL,
  `Dottorato` varchar(50) NOT NULL,
  `ScuolaSpec` varchar(50) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

--
-- Dump dei dati per la tabella `utente`
--

INSERT INTO `utente` (`IdUtente`, `Username`, `Password`, `Privilegi`, `Nome`, `Cognome`, `DataNasc`, `LuogoNasc`, `Residenza`, `CodiceFisc`, `Telefono`, `Sesso`, `Email`, `CorsoLaurea`, `Handicap`, `Laurea`, `Dottorato`, `ScuolaSpec`) VALUES
(5, 'vincenzo91', 'vincenzo', 1, 'Vincenzo', 'Battisti', '20-01-1959', 'Arpino', 'Roma', 'lsjhdfd45', '6545612', '', '', 'Informatica', 0, 'triennale', 'oiehfd', 'sdsddf');

--
-- Indici per le tabelle scaricate
--

--
-- Indici per le tabelle `azienda`
--
ALTER TABLE `azienda`
  ADD PRIMARY KEY (`IdAzienda`);

--
-- Indici per le tabelle `richiesta`
--
ALTER TABLE `richiesta`
  ADD PRIMARY KEY (`IdRichiesta`);

--
-- Indici per le tabelle `tirocinio`
--
ALTER TABLE `tirocinio`
  ADD PRIMARY KEY (`IdTirocinio`);

--
-- Indici per le tabelle `tutore`
--
ALTER TABLE `tutore`
  ADD PRIMARY KEY (`IdTutore`);

--
-- Indici per le tabelle `utente`
--
ALTER TABLE `utente`
  ADD PRIMARY KEY (`IdUtente`);

--
-- AUTO_INCREMENT per le tabelle scaricate
--

--
-- AUTO_INCREMENT per la tabella `azienda`
--
ALTER TABLE `azienda`
  MODIFY `IdAzienda` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=3;

--
-- AUTO_INCREMENT per la tabella `richiesta`
--
ALTER TABLE `richiesta`
  MODIFY `IdRichiesta` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=2;

--
-- AUTO_INCREMENT per la tabella `tirocinio`
--
ALTER TABLE `tirocinio`
  MODIFY `IdTirocinio` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=5;

--
-- AUTO_INCREMENT per la tabella `tutore`
--
ALTER TABLE `tutore`
  MODIFY `IdTutore` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=7;

--
-- AUTO_INCREMENT per la tabella `utente`
--
ALTER TABLE `utente`
  MODIFY `IdUtente` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=11;
COMMIT;

/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
