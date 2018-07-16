-- phpMyAdmin SQL Dump
-- version 4.7.4
-- https://www.phpmyadmin.net/
--
-- Host: 127.0.0.1:3306
-- Creato il: Lug 16, 2018 alle 19:06
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
  `StatusConvenzione` tinyint(4) DEFAULT NULL,
  `IdConvenzione` int(11) DEFAULT NULL,
  PRIMARY KEY (`IdAzienda`)
) ENGINE=InnoDB AUTO_INCREMENT=15 DEFAULT CHARSET=latin1;

--
-- Dump dei dati per la tabella `azienda`
--

INSERT INTO `azienda` (`IdAzienda`, `Username`, `Password`, `Privilegi`, `Status`, `Nome`, `RagioneSociale`, `Indirizzo`, `PartitaIva`, `CodiceFiscale`, `NomeRappr`, `CognomeRappr`, `NomeResp`, `CognomeResp`, `TelefonoResp`, `EmailResp`, `Foro`, `Valutazione`, `NumeroTirocini`, `NumTiroCompletati`, `StatusConvenzione`, `IdConvenzione`) VALUES
(5, 'tales', 'bd044d038bfb5ed8716ac949623ac89e', 2, 1, 'Tales Aerospace', 'Ragione', 'Via Roma,22', '3249432913', '34028482', 'Luigi', 'Di Franco', 'Leonardo', 'DI Federico', '32435667', 'leo.tales@info.com', 'L\\\'Aquila', 5, 1, 1, 1, 0),
(6, 'gunpow', '2882470abb4ebd9b89c1687506c22a40', 2, 1, 'Gunpowder srl', 'Spinoff Univaq', '', '3434565', '', 'Gianni', 'Masciulli', 'Andrea', 'Bianchi', '232313382', 'andrea@bianchi.com', 'Roma', 0, 0, 0, 0, 0),
(7, 'desas', '29820ebfb1a1b5ecc3f706c5478773fb', 2, 1, 'De santis srl', 'Servizi software', 'Via venezia, 1', '3848387482', 'DSSMBR34924', 'Umberto ', 'De Santis', 'Federico', 'De Santis', '55662924', 'federico.desantis@libero.com', 'Foggia', 0, 0, 0, 0, 0),
(8, 'biochimica', '364dab9abd4446677a68895412305480', 2, 1, 'Biochimica s.p.a', 'Biochimica', 'Via Roma, 49', '49384243', '', 'Licia', 'Rossi', 'Ernesto', 'Di Girolamo', '444592813', 'ernesto@biochimica.info', 'Bologna', 0, 1, 0, 1, 0),
(9, 'clac', '76b087e2c1b92df76f10a8d34b561206', 2, 1, 'Clac srl', 'Informatica', 'Via Pescara', '55968384', '43894939', 'Roberto', 'Di Rosa', 'Cinzia', 'Del Rosario', '333477163624', 'cinzia@clac.info', 'Pescara', 0, 0, 0, 0, 0),
(10, 'ital', '7d1d6129eec38bc345ac033e5ebd7350', 2, 1, 'Italdata spa', 'Informatica', 'Via Raiale', '3434565', 'DUSDSDS474563', 'Ivan', 'Fulco', 'Barbara', 'Lezi', '6939844995', 'barbara@italdata.com', 'San Giovanni Teatino', 0, 0, 0, 0, 0),
(11, 'micron', '4e8192d5c24a51e5fda5e9e010296aaa', 2, 1, 'Micron Semiconductor Italia S.r.l.', 'Dispositivi a semiconduttore', 'Via Pacinotti, Avezzano', '12345678910', '', 'Mario', 'Rossi', 'Andrea', 'Bianchi', '0863441005', 'andrea@gmail.com', 'L\\\'Aquila', 0, 0, 0, 1, 7),
(12, 'cotweb', '00363d3b71cbc45d2b5f28ab620de72e', 2, 1, 'CotWeb Software House', 'Web Design', 'Via Eusanio Stella, 17 L\\\'Aquila', '01987654321', '', 'Giorgio', 'Corona', 'Marco', 'Verdi', '3279125824', 'verdimarco@cotweb.com', 'L\\\'Aquila', 0, 0, 0, 1, 8),
(13, 'teamsystem', 'dbe5abbf17c9ccbc10bc63880917083e', 2, 1, 'TeamSystem Roma S.r.l.', 'Software gestionali', 'Via della Sierra Nevada, 60 Roma', '12345625811', '', 'Giovanni', 'Marconi', 'Giacomo', 'Poretti', '3471234569', 'giacomo@teamsystem.com', 'Roma', 0, 0, 0, 1, 9),
(14, 'datacommerce', '061337085f8602ebfb1b823fc7904d7a', 2, 1, 'Data Commerce S.r.l.', 'Servizi Informatici per aziende', 'Via del Casale Agostinelli, 155c Roma', '41736978955', '', 'Pietro', 'Corsi', 'Paolo', 'Rossi', '0690214427', 'paolo@datacommerce.com', 'Roma', 0, 0, 0, 1, 10);

-- --------------------------------------------------------

--
-- Struttura della tabella `documenti`
--

DROP TABLE IF EXISTS `documenti`;
CREATE TABLE IF NOT EXISTS `documenti` (
  `DocId` int(11) NOT NULL AUTO_INCREMENT,
  `Size` int(11) DEFAULT NULL,
  `Localfile` varchar(255) DEFAULT NULL,
  `Tipo` varchar(45) DEFAULT NULL,
  `Filename` varchar(255) DEFAULT NULL,
  `Digest` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`DocId`)
) ENGINE=InnoDB AUTO_INCREMENT=11 DEFAULT CHARSET=latin1;

--
-- Dump dei dati per la tabella `documenti`
--

INSERT INTO `documenti` (`DocId`, `Size`, `Localfile`, `Tipo`, `Filename`, `Digest`) VALUES
(7, 102400, 'upload_5563319204724278934', 'application/pdf', 'micronconv.pdf', '85117-35-126-310342-105-9995-4127-29-117-84-35-6838-9615'),
(8, 102400, 'upload_6412654088959212879', 'application/pdf', 'cotweb.pdf', '11185-6559795103-1533172923-3183-921-110-11381'),
(9, 102400, 'upload_5764027372059727608', 'application/pdf', 'teamsystem.pdf', '67-4857101164074-97-8785-24321-2062733480-3-43'),
(10, 102400, 'upload_8802838842194718649', 'application/pdf', 'datacommerce.pdf', '26-31-36-1044966-5547-26-78-6432868483-2245-61-834');

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
  `CodTutore` int(11) DEFAULT NULL,
  PRIMARY KEY (`IdRichiesta`)
) ENGINE=InnoDB AUTO_INCREMENT=25 DEFAULT CHARSET=latin1;

--
-- Dump dei dati per la tabella `richiesta`
--

INSERT INTO `richiesta` (`IdRichiesta`, `CodStudente`, `CodTirocinio`, `Status`, `Cfu`, `NomeTutor`, `CognomeTutor`, `EmailTutor`, `CodTutore`) VALUES
(16, 17, 6, 'pending', '9', 'Lorenzo', 'Nardocchi', 'lorenzo@tales.com', 8),
(17, 21, 9, 'accepted', '12', 'Franco', 'Neri', 'franco@biochimica.com', 11),
(20, 19, 8, 'pending', '6', 'Vincenzo', 'Battisti', 'raffaella@italdata.info', 10),
(21, 22, 16, 'pending', '6', 'Francesco', 'Conte', 'francesco@univaq.com', 12),
(23, 24, 15, 'pending', '9', 'Mario', 'Rossi', 'mario@univaq.com', 13),
(24, 19, 15, 'pending', '6', 'Gianni', 'Corsi', 'gianni@univaq.com', 13);

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
  `Titolo` varchar(255) DEFAULT NULL,
  `Status` tinyint(1) DEFAULT NULL,
  `DataInizio` date DEFAULT NULL,
  `DataFine` date DEFAULT NULL,
  `StatusVoto` tinyint(4) DEFAULT NULL,
  `StatusProgetto` tinyint(1) DEFAULT NULL,
  `IdProgetto` int(11) DEFAULT NULL,
  `Descrizione` varchar(255) DEFAULT NULL,
  `Risultato` varchar(255) DEFAULT NULL,
  `StatusResoconto` tinyint(1) NOT NULL,
  PRIMARY KEY (`IdTirocinio`)
) ENGINE=InnoDB AUTO_INCREMENT=17 DEFAULT CHARSET=latin1;

--
-- Dump dei dati per la tabella `tirocinio`
--

INSERT INTO `tirocinio` (`IdTirocinio`, `Luogo`, `Orario`, `NumOre`, `NumMesi`, `Obiettivi`, `Modalità`, `Facilitazioni`, `Settore`, `CodTutore`, `CodAzienda`, `Titolo`, `Status`, `DataInizio`, `DataFine`, `StatusVoto`, `StatusProgetto`, `IdProgetto`, `Descrizione`, `Risultato`, `StatusResoconto`) VALUES
(6, 'Roma', '9-13', 5, 12, 'Sviluppo FPGA e design control flow', 'Lavoro su workstation aziendale', 'Nessuna', 'Ingegneria', 8, 5, 'Sviluppo hardware', 0, NULL, NULL, 0, 0, 0, '', '', 0),
(8, 'San Giovanni Teatino', '8-13', 25, 6, 'Sviluppo front end per sito aziendale', 'Lavoro nella sede aziendale', 'Ufficio aziendale ', 'Informatica', 10, 10, 'Sviluppo web', 0, NULL, NULL, 0, 0, 0, '', '', 0),
(9, 'Vicenza', '10-17', 30, 10, 'Analisi DNA in laboratorio', 'Attività di monitoraggio microscopio', 'Trasferta e alloggio', 'Biochimica', 11, 8, 'Analisi laboratorio', 1, '2017-03-01', '2018-01-31', 0, 0, 0, '', '', 0),
(11, 'Avezzano', 'Dalle 9 alle 18', 8, 6, 'Sviluppo dispositivi a semiconduttore', 'Lavoro in sede aziendale', 'Nessuna', 'Informatica', 14, 11, 'Dispositivi a semiconduttore', 0, NULL, NULL, 0, 0, 0, '', '', 0),
(12, 'L\\\'Aquila', 'dalle 9 alle 18', 7, 5, 'Tirocinio sullo sviluppo del design di un sito web', 'Lavoro su laptop aziendale', 'Nessuna', 'Informatica', 15, 12, 'Web Design', 0, NULL, NULL, 0, 0, 0, '', '', 0),
(13, 'Roma', 'Dalle 10 alle 18', 7, 4, 'Sviluppo di software che ottimizzino lo sviluppo aziendale', 'In sede aziendale con workstation aziendali', 'Rimborso Viaggio', 'Informatica', 16, 13, 'Sviluppo Software Gestionali', 0, NULL, NULL, 0, 0, 0, '', '', 0),
(14, 'Roma', 'Dalle 10 alle 18', 7, 6, 'Progettazione e gestione di soluzioni tecnologiche su misura ad alte prestazioni', 'Lavoro in sede aziendale', 'Nessuna', 'Ingegneria', 17, 14, 'Servizi informatici, progetti e forniture per aziende', 0, NULL, NULL, 0, 0, 0, '', '', 0),
(15, 'Avezzano', 'Dalle 9 alle 18', 8, 6, 'Sviluppo software ottimizzazione processi aziendali', 'Lavoro in azienda', 'Nessuna', 'Informatica', 13, 11, 'Sviluppo Software Gestione Processi Aziendali', 0, NULL, NULL, 0, 0, 0, '', '', 0),
(16, 'Avezzano', 'Dalle 9 alle 18', 8, 6, 'Migliorare le scelte aziendale per aumentare il profitto', 'Lavoro in sede aziendale', 'Rimborso viaggio', 'Economia', 12, 11, 'Gestione dell\\\'economia aziendale', 0, NULL, NULL, 0, 0, 0, '', '', 0);

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
) ENGINE=InnoDB AUTO_INCREMENT=18 DEFAULT CHARSET=latin1;

--
-- Dump dei dati per la tabella `tutore`
--

INSERT INTO `tutore` (`IdTutore`, `Nome`, `Cognome`, `DataNasc`, `NumTirocini`, `Telefono`, `CodAzienda`, `EmailTutore`) VALUES
(1, 'Vittorio', 'Cortellessa', '1968-03-10', 1, '65896589', 1, 'abc@cba.com'),
(7, 'Anastasia', 'De Falco', '1979-02-10', 0, '33420382', 5, 'anastasia@tales.com'),
(8, 'Lorenzo', 'Nardocchi', '1956-03-10', 1, '503882', 5, 'lorenzo@tales.com'),
(9, 'Marco', 'Garofano', '1988-10-26', 0, '54594823', 5, 'marco.tales@info.com'),
(10, 'Raffaella', 'Del Monte', '1981-04-17', 0, '5857372242', 10, 'raffaella@italdata.info'),
(11, 'Franco', 'Neri', '1969-04-03', 1, '694756354', 8, 'franco@biochimica.com'),
(12, 'Giorgio', 'Bianchi', '1970-03-14', 0, '3337894561', 11, 'giorgio@micron.com'),
(13, 'Giulia', 'Nargi', '1975-04-17', 0, '3472589637', 11, 'giulia@micron.com'),
(14, 'Franco', 'Rossi', '1980-04-30', 0, '3402587413', 11, 'franco@micron.com'),
(15, 'Marco', 'Bianchi', '1985-04-15', 0, '3492589635', 12, 'marco@cotweb.com'),
(16, 'Francesca', 'Battista', '1970-08-15', 0, '3492471563', 13, 'francesca@teamsystem.com'),
(17, 'Giacomo', 'Conte', '1980-01-20', 0, '3402563214', 14, 'giacomo@datacommerce.com');

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
) ENGINE=InnoDB AUTO_INCREMENT=26 DEFAULT CHARSET=latin1;

--
-- Dump dei dati per la tabella `utente`
--

INSERT INTO `utente` (`IdUtente`, `Username`, `Password`, `Privilegi`, `Nome`, `Cognome`, `DataNasc`, `LuogoNasc`, `Residenza`, `CodiceFisc`, `Telefono`, `Sesso`, `EmailUtente`, `CorsoLaurea`, `Handicap`, `Laurea`, `Dottorato`, `ScuolaSpec`) VALUES
(17, 'giovanni', '037c70dbc1c812f6b2091688804d7b17', 1, 'Giovanni', 'Verdi', '1987-02-02', 'Roma', 'Roma', 'GNVVRD10A45CSSA', '3232455245', 'maschio', 'giovani@verdi.com', 'ingegneria', 0, 'triennale', '', ''),
(18, 'marco', 'f5888d0bb58d611107e11f7cbc41c97a', 1, 'Marco', 'Di Renzo', '1994-05-03', 'Vicenza', 'Imola', 'MRCDRZ13GC456', '456429332', 'maschio', 'marco@gmail.com', 'Scienze biomediche', 0, 'magistrale', 'Biotecnologie', 'Automatica per biologia'),
(19, 'claudio', 'f6a47a638824180d57f0a561fd5843c6', 1, 'Claudio', 'Di Sipio', '1995-04-03', 'Chieti', 'Ripa', 'DSPCLD94A10C632M', '3323022983', 'maschio', 'claudio.disipio94@gmail.com', 'Informatica', 0, 'triennale', 'Informatica', 'Software engeneering'),
(20, 'admin', '21232f297a57a5a743894a0e4a801fc3', 0, 'Mario', 'Di Carlo', '1968-03-10', 'L\\\'Aquila', 'L\\\'Aquila', 'MRDCRL43D', '32439488', 'maschio', 'mario.dicarlo@univaq.it', 'Nessuno', 0, 'triennale', '', ''),
(21, 'eleonora', '7756dad2e8fe14e0b5b64d2b3b3bce32', 1, 'Eleonora', 'De Nardis', '1995-12-04', 'Urbino', 'L\\\'Aquila', 'LNRDNR42N43949C', '438437372', 'donna', 'eleonora@univaq.com', 'Fisica', 0, 'magistrale', 'Fisica', 'Fisica molecolare'),
(22, 'mario', 'de2f15d014d40b93578d255e6221fd60', 1, 'Mario', 'Rossi', '1986-01-20', 'Roma', 'Roma', 'RSSMRA86A20H501D', '3476561651', 'maschio', 'mariorossi@univaq.com', 'Economia', 0, 'triennale', '', ''),
(23, 'bianchi', '1f5c8c3ce5e3c635ab899d59c8635009', 1, 'Marco', 'Bianchi', '1990-06-15', 'L\\\'Aquila', 'L\\\'Aquila', 'BNCMRC90H15A345M', '3336545798', 'maschio', 'bianchi@gmail.com', 'Biotecnologie', 0, 'triennale', '', ''),
(24, 'vinbat', '2d883dad04e07b3f9c55399e288b65fb', 1, 'Vincenzo', 'Battisti', '1991-08-16', 'Arpino', 'Fontana Liri', 'BTTVCN91M16A433R', '3331234567', 'maschio', 'vincenzo-battisti@gmail.com', 'Informatica', 0, 'triennale', '', ''),
(25, 'francesca', '3477402667742da39c8e93bf4f30b271', 1, 'Francesca', 'Di Cola', '1992-07-20', 'Pescara', 'Pescara', 'DCLFNC92L60G482Y', '3402556789', 'donna', 'francesca@gmail.com', 'Medicina', 0, 'magistrale', '', '');
COMMIT;

/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
