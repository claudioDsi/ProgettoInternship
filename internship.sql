-- phpMyAdmin SQL Dump
-- version 4.7.4
-- https://www.phpmyadmin.net/
--
-- Host: 127.0.0.1
-- Creato il: Lug 08, 2018 alle 18:05
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
  `StatusConvenzione` tinyint(4) DEFAULT NULL,
  `IdConvenzione` int(11) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

--
-- Dump dei dati per la tabella `azienda`
--

INSERT INTO `azienda` (`IdAzienda`, `Username`, `Password`, `Privilegi`, `Status`, `Nome`, `RagioneSociale`, `Indirizzo`, `PartitaIva`, `CodiceFiscale`, `NomeRappr`, `CognomeRappr`, `NomeResp`, `CognomeResp`, `TelefonoResp`, `EmailResp`, `Foro`, `Valutazione`, `NumeroTirocini`, `NumTiroCompletati`, `StatusConvenzione`, `IdConvenzione`) VALUES
(5, 'tales', 'bd044d038bfb5ed8716ac949623ac89e', 2, 1, 'Tales Aerospace', '', 'Via Roma,22', '3249432913', '34028482', 'Luigi', 'Di Franco', 'Leonardo', 'DI Federico', '32435667', 'leo.tales@info.com', 'L\\\'Aquila', 5, 1, 1, 0, 0),
(6, 'gunpow', '2882470abb4ebd9b89c1687506c22a40', 2, 1, 'Gunpowder srl', 'Spinoff Univaq', '', '3434565', '', 'Gianni', 'Masciulli', 'Andrea', 'Bianchi', '232313382', 'andrea@bianchi.com', 'Roma', 0, 0, 0, 0, 0),
(7, 'desas', '29820ebfb1a1b5ecc3f706c5478773fb', 2, 0, 'De santis srl', 'Servizi software', 'Via venezia, 1', '3848387482', 'DSSMBR34924', 'Umberto ', 'De Santis', 'Federico', 'De Santis', '55662924', 'federico.desantis@libero.com', 'Foggia', 0, 0, 0, 0, 0),
(8, 'biochimica', '364dab9abd4446677a68895412305480', 2, 0, 'Biochimica s.p.a', 'Biochimica', 'Via Roma, 49', '49384243', '', 'Licia', 'Rossi', 'Ernesto', 'Di Girolamo', '444592813', 'ernesto@biochimica.info', 'Bologna', 0, 0, 0, 0, 0);

-- --------------------------------------------------------

--
-- Struttura della tabella `documenti`
--

CREATE TABLE `documenti` (
  `DocId` int(11) NOT NULL,
  `Size` int(11) DEFAULT NULL,
  `Localfile` varchar(255) DEFAULT NULL,
  `Tipo` varchar(45) DEFAULT NULL,
  `Filename` varchar(255) DEFAULT NULL,
  `Digest` varchar(255) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

--
-- Dump dei dati per la tabella `documenti`
--

INSERT INTO `documenti` (`DocId`, `Size`, `Localfile`, `Tipo`, `Filename`, `Digest`) VALUES
(3, 45056, 'upload_9211088413647419974', 'application/pdf', 'Internship Helper.pdf', '74-27-1031-3912-118-107-123-735660-1108886713-8034-42'),
(4, 40960, 'upload_5069977613869361033', 'application/pdf', 'Internship Helper.pdf', '-2083421-18-49-5-75-70-6572-14109986-61635134-41'),
(5, 40960, 'upload_8608345256948304001', 'application/pdf', 'Internship Helper.pdf', '-2083421-18-49-5-75-70-6572-14109986-61635134-41');

-- --------------------------------------------------------

--
-- Struttura della tabella `richiesta`
--

CREATE TABLE `richiesta` (
  `IdRichiesta` int(11) NOT NULL,
  `CodStudente` int(11) DEFAULT NULL,
  `CodTirocinio` int(11) DEFAULT NULL,
  `Status` varchar(20) DEFAULT NULL,
  `Cfu` varchar(4) DEFAULT NULL,
  `NomeTutor` varchar(50) DEFAULT NULL,
  `CognomeTutor` varchar(50) DEFAULT NULL,
  `EmailTutor` varchar(50) DEFAULT NULL,
  `CodTutore` int(11) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

--
-- Dump dei dati per la tabella `richiesta`
--

INSERT INTO `richiesta` (`IdRichiesta`, `CodStudente`, `CodTirocinio`, `Status`, `Cfu`, `NomeTutor`, `CognomeTutor`, `EmailTutor`, `CodTutore`) VALUES
(16, 17, 6, 'accepted', '9', 'Lorenzo', 'Nardocchi', 'lorenzo@tales.com', 8);

-- --------------------------------------------------------

--
-- Struttura della tabella `tirocinio`
--

CREATE TABLE `tirocinio` (
  `IdTirocinio` int(11) NOT NULL,
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
  `StatusVoto` tinyint(4) DEFAULT NULL,
  `StatusProgetto` tinyint(1) DEFAULT NULL,
  `IdProgetto` int(11) DEFAULT NULL,
  `Descrizione` varchar(255) DEFAULT NULL,
  `Risultato` varchar(255) DEFAULT NULL,
  `StatusResoconto` tinyint(1) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

--
-- Dump dei dati per la tabella `tirocinio`
--

INSERT INTO `tirocinio` (`IdTirocinio`, `Luogo`, `Orario`, `NumOre`, `NumMesi`, `Obiettivi`, `Modalità`, `Facilitazioni`, `Settore`, `CodTutore`, `CodAzienda`, `Titolo`, `Status`, `DataInizio`, `DataFine`, `StatusVoto`, `StatusProgetto`, `IdProgetto`, `Descrizione`, `Risultato`, `StatusResoconto`) VALUES
(1, 'Kadara', 'dalle 9 alle 13', 4, 8, 'Fare lo schiavo', 'carota', 'fotocopiatrice', 'ortolano', 0, 1, 'Tirocinio 1', 0, NULL, NULL, 0, 0, 0, NULL, NULL, 0),
(2, 'Kadara', 'dalle 9 alle 13', 4, 8, 'Fare lo schiavo', 'carota', 'fotocopiatrice', 'ortolano', 0, 1, 'Tirocinio 2', 0, NULL, NULL, 0, 0, 0, NULL, NULL, 0),
(3, 'f', 'f', 4, 5, 'dsf', 'fsddfsd', 'asdf', 'adf', 0, 1, 'Tirocinio 3', 0, NULL, NULL, 0, 0, 0, NULL, NULL, 0),
(4, 'sdf', 'af', 4, 5, 'dsf', 'fsddfsd', 'asdf', 'adf', 0, 1, 'Tirocinio 4', 0, NULL, NULL, 0, 0, 0, NULL, NULL, 0),
(5, 'afds', 'sgf', 4, 5, 'gs', 'sfg', 'sg', 'gs', 1, 1, 'Tirocinio 5', 1, '2018-01-14', '2018-06-14', 1, 1, 5, 'Descrizione', 'Risultati', 1),
(6, 'Roma', '8-13', 5, 6, 'Sviluppo FPGA e design control flow', 'Lavoro su workstation aziendale', 'Nessuna', 'Ingegneria', 8, 5, 'Sviluppo hardware', 1, '2017-05-01', '2017-11-30', 1, 0, 0, 'Hardware co-desing\r\n', 'Ha svolto l\\\'attività in modo costante', 1);

-- --------------------------------------------------------

--
-- Struttura della tabella `tutore`
--

CREATE TABLE `tutore` (
  `IdTutore` int(11) NOT NULL,
  `Nome` varchar(250) DEFAULT NULL,
  `Cognome` varchar(250) DEFAULT NULL,
  `DataNasc` date DEFAULT NULL,
  `NumTirocini` int(11) DEFAULT NULL,
  `Telefono` varchar(20) DEFAULT NULL,
  `CodAzienda` int(11) DEFAULT NULL,
  `EmailTutore` varchar(100) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

--
-- Dump dei dati per la tabella `tutore`
--

INSERT INTO `tutore` (`IdTutore`, `Nome`, `Cognome`, `DataNasc`, `NumTirocini`, `Telefono`, `CodAzienda`, `EmailTutore`) VALUES
(1, 'Vittorio', 'Cortellessa', '1968-03-10', 1, '65896589', 1, 'abc@cba.com'),
(7, 'Anastasia', 'De Falco', '1979-02-10', 0, '33420382', 5, 'anastasia@tales.com'),
(8, 'Lorenzo', 'Nardocchi', '1956-03-10', 1, '503882', 5, 'lorenzo@tales.com');

-- --------------------------------------------------------

--
-- Struttura della tabella `utente`
--

CREATE TABLE `utente` (
  `IdUtente` int(11) NOT NULL,
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
  `ScuolaSpec` varchar(50) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

--
-- Dump dei dati per la tabella `utente`
--

INSERT INTO `utente` (`IdUtente`, `Username`, `Password`, `Privilegi`, `Nome`, `Cognome`, `DataNasc`, `LuogoNasc`, `Residenza`, `CodiceFisc`, `Telefono`, `Sesso`, `EmailUtente`, `CorsoLaurea`, `Handicap`, `Laurea`, `Dottorato`, `ScuolaSpec`) VALUES
(17, 'giovanni', '037c70dbc1c812f6b2091688804d7b17', 1, 'Giovanni', 'Verdi', '1987-02-02', 'Roma', 'Roma', 'GNVVRD10A45CSSA', '3232455245', 'maschio', 'giovani@verdi.com', 'ingegneria', 0, 'triennale', '', ''),
(18, 'marco', 'f5888d0bb58d611107e11f7cbc41c97a', 1, 'Marco', 'Di Renzo', '1994-05-03', 'Vicenza', 'Imola', 'MRCDRZ13GC456', '456429332', 'maschio', 'marco@gmail.com', 'Scienze biomediche', 0, 'magistrale', 'Biotecnologie', 'Automatica per biologia'),
(19, 'claudio', 'f6a47a638824180d57f0a561fd5843c6', 1, 'Claudio', 'Di Sipio', '1995-04-03', 'Chieti', 'Ripa', 'DSPCLD94A10C632M', '3323022983', 'maschio', 'claudio.disipio94@gmail.com', 'Informatica', 0, 'triennale', 'Informatica', 'Software engeneering'),
(20, 'admin', '21232f297a57a5a743894a0e4a801fc3', 0, 'Mario', 'Di Carlo', '1968-03-10', 'L\\\'Aquila', 'L\\\'Aquila', 'MRDCRL43D', '32439488', 'maschio', 'mario.dicarlo@univaq.it', 'Nessuno', 0, 'triennale', '', ''),
(21, 'eleonora', '7756dad2e8fe14e0b5b64d2b3b3bce32', 1, 'Eleonora', 'De Nardis', '1995-12-04', 'Urbino', 'L\\\'Aquila', 'LNRDNR42N43949C', '438437372', 'donna', 'eleonora@univaq.com', 'Fisica', 0, 'magistrale', 'Fisica', 'Fisica molecolare');

--
-- Indici per le tabelle scaricate
--

--
-- Indici per le tabelle `azienda`
--
ALTER TABLE `azienda`
  ADD PRIMARY KEY (`IdAzienda`);

--
-- Indici per le tabelle `documenti`
--
ALTER TABLE `documenti`
  ADD PRIMARY KEY (`DocId`);

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
  MODIFY `IdAzienda` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=9;

--
-- AUTO_INCREMENT per la tabella `documenti`
--
ALTER TABLE `documenti`
  MODIFY `DocId` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=6;

--
-- AUTO_INCREMENT per la tabella `richiesta`
--
ALTER TABLE `richiesta`
  MODIFY `IdRichiesta` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=17;

--
-- AUTO_INCREMENT per la tabella `tirocinio`
--
ALTER TABLE `tirocinio`
  MODIFY `IdTirocinio` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=7;

--
-- AUTO_INCREMENT per la tabella `tutore`
--
ALTER TABLE `tutore`
  MODIFY `IdTutore` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=9;

--
-- AUTO_INCREMENT per la tabella `utente`
--
ALTER TABLE `utente`
  MODIFY `IdUtente` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=22;
COMMIT;

/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
