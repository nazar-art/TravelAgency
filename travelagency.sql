SET SQL_MODE = "NO_AUTO_VALUE_ON_ZERO";
SET time_zone = "+00:00";

--
-- Database: `travelagency`
--
CREATE DATABASE IF NOT EXISTS `travelagency` DEFAULT CHARACTER SET utf8 COLLATE utf8_general_ci;
USE `travelagency`;

-- --------------------------------------------------------

--
-- Table structure for table `purchases`
--

DROP TABLE IF EXISTS `purchases`;
CREATE TABLE IF NOT EXISTS `purchases` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `user_id` int(11) NOT NULL,
  `tour_id` int(11) NOT NULL,
  `time_of_purchase` TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
  `price` double NOT NULL,
  `checked` tinyint(1) NOT NULL DEFAULT '0',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB  DEFAULT CHARSET=utf8 AUTO_INCREMENT=22;

--
-- Dumping data for table `purchases`
--

INSERT INTO `purchases` (`id`, `user_id`, `tour_id`, `time_of_purchase`, `price`, `checked`) VALUES
(1, 1, 14, '2014-02-23 21:16:01', 7, 0),
(2, 2, 15, '2014-02-23 21:19:50', 199, 0),
(3, 3, 12, '2014-02-23 21:20:41', 270, 0),
(4, 4, 10, '2014-02-23 21:20:57', 30, 1),
(5, 5, 13, '2014-02-23 21:21:06', 50, 1),
(6, 6, 16, '2014-02-23 21:21:52', 250, 0),
(7, 7, 11, '2014-02-23 21:22:22', 120, 0);

-- --------------------------------------------------------

--
-- Table structure for table `tours`
--

DROP TABLE IF EXISTS `tours`;
CREATE TABLE IF NOT EXISTS `tours` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `name` varchar(160) NOT NULL,
  `type` varchar(30) NOT NULL,
  `depart` date NOT NULL,
  `arrival` date NOT NULL,
  `price` double NOT NULL,
  `discount` int(11) NOT NULL DEFAULT '0',
  `image` varchar(100) NOT NULL DEFAULT 'default_Shopping.jpg',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB  DEFAULT CHARSET=utf8 AUTO_INCREMENT=20;

--
-- Dumping data for table `tours`
--

INSERT INTO `tours` (`id`, `name`, `type`, `depart`, `arrival`, `price`, `discount`, `image`) VALUES
(1, 'Yamaika - best resort', 'Vacation', '2014-02-12', '2014-02-27', 220, 0, 'default_Vacation.jpg'),
(2, 'Bulgaria', 'Vacation', '2014-02-18', '2014-02-28', 348, 5, 'default_Vacation.jpg'),
(3, 'Kiev Maydan', 'Excursion', '2014-02-26', '2014-03-01', 30, 0, 'default_Excursion.jpg'),
(4, 'Cuba', 'Vacation', '2014-02-05', '2014-02-28', 120, 0, 'default_Vacation.jpg'),
(5, 'Miami', 'Vacation', '2014-03-01', '2014-03-21', 300, 10, 'default_Vacation.jpg'),
(6, 'Milan, Italy', 'Shopping', '2014-02-12', '2014-02-12', 50, 0, 'default_Shopping.jpg'),
(7, 'Yanukovic Villa Medsugiria', 'Excursion', '2014-02-28', '2014-03-01', 10, 30, 'default_Excursion.jpg'),
(8, 'Google''s Mountain View', 'Excursion', '2014-04-09', '2014-04-17', 199, 0, 'default_Excursion.jpg'),
(9, 'New York', 'Excursion', '2014-03-20', '2014-03-22', 250, 0, 'default_Excursion.jpg'),
(10, 'Paris', 'Shopping', '2014-03-01', '2014-03-04', 80, 0, 'default_Shopping.jpg');

-- --------------------------------------------------------

--
-- Table structure for table `users`
--

DROP TABLE IF EXISTS `users`;
CREATE TABLE IF NOT EXISTS `users` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `first_name` varchar(20) NOT NULL,
  `last_name` varchar(30) NOT NULL,
  `email` varchar(40) NOT NULL,
  `password` varchar(255) NOT NULL,
  `discount` int(11) NOT NULL DEFAULT '0',
  `admin` tinyint(1) NOT NULL DEFAULT '0',
  `image` varchar(100) NOT NULL DEFAULT 'default.png',
  PRIMARY KEY (`id`),
  UNIQUE KEY `email` (`email`)
) ENGINE=InnoDB  DEFAULT CHARSET=utf8 AUTO_INCREMENT=23;

--
-- Dumping data for table `users`
--

INSERT INTO `users` (`id`, `first_name`, `last_name`, `email`, `password`, `discount`, `admin`, `image`) VALUES
(1, 'Oleh', 'Zasadnyy', 'oleh.zasadnyy@gmail.com', '63a9f0ea7bb98050796b649e85481845', 50, 1, 'default.png'),
(2, 'Amadeus', 'Mozart', 'mozart@gmail.com', '1a1dc91c907325c69271ddf0c944bc72', 20, 0, 'default.png'),
(3, 'Christofor', 'Columb', 'columb@gmail.com', '32250170a0dca92d53ec9624f336ca24', 0, 0, 'default.png'),
(4, 'James', 'Cook', 'cook@gmail.com', '74dc8d5f58297164a11346450fe631c9', 0, 0, 'default.png'),
(5, 'Fernando', 'Magelan', 'magelan@gmail.com', '32250170a0dca92d53ec9624f336ca24', 10, 0, 'default.png'),
(6, 'Makluha', 'Maklay', 'maklay@gmail.com', '1a1dc91c907325c69271ddf0c944bc72', 0, 0, 'default.png'),
(7, 'Nazar', 'Lelyak', 'lelyak@gmail.com', 'f21b2728da8bc2672255c758aaf2cdd3', 0, 1, 'default.png');
