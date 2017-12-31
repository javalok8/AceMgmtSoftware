-- phpMyAdmin SQL Dump
-- version 3.2.0.1
-- http://www.phpmyadmin.net
--
-- Host: localhost
-- Generation Time: Dec 31, 2017 at 09:03 AM
-- Server version: 5.1.36
-- PHP Version: 5.3.0

SET SQL_MODE="NO_AUTO_VALUE_ON_ZERO";


/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8 */;

--
-- Database: `lok_pun`
--

-- --------------------------------------------------------

--
-- Table structure for table `tbl_credit_debit`
--

CREATE TABLE IF NOT EXISTS `tbl_credit_debit` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `date` date NOT NULL,
  `codeno` varchar(100) NOT NULL,
  `product_name` varchar(150) NOT NULL,
  `qty_type` varchar(100) NOT NULL,
  `total_qty` int(11) NOT NULL,
  `product_single_price` double NOT NULL,
  `total_amount` double NOT NULL,
  `description` varchar(250) NOT NULL,
  `type` varchar(50) NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB  DEFAULT CHARSET=latin1 AUTO_INCREMENT=8 ;

--
-- Dumping data for table `tbl_credit_debit`
--

INSERT INTO `tbl_credit_debit` (`id`, `date`, `codeno`, `product_name`, `qty_type`, `total_qty`, `product_single_price`, `total_amount`, `description`, `type`) VALUES
(2, '2074-09-11', 'kk', 'Mobile', 'kk', 12, 77, 924, 'asdfasdf', 'Credit'),
(3, '2074-09-11', 'as', 'Head Phone', 'sdsss', 2, 1111, 2222, 'dfsfdf', 'Debit'),
(4, '2074-09-11', 'aa', 'Mobile', 'aa', 21, 88, 1848, 'sdfsdf', 'Credit'),
(5, '2074-09-16', 'aa', 'Mobile', 'aa', 1, 88, 88, 'dsfd', 'Credit'),
(6, '2074-09-16', 'as', 'Head Phone', 'sdsss', 1, 1111, 1111, 'dsfsd', 'Credit'),
(7, '2074-09-16', 'aa', 'Mobile', 'aa', 1, 88, 88, 'sf', 'Debit');

-- --------------------------------------------------------

--
-- Table structure for table `tbl_damage_lost_product`
--

CREATE TABLE IF NOT EXISTS `tbl_damage_lost_product` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `date` date NOT NULL,
  `codeno` varchar(100) NOT NULL,
  `product_name` varchar(150) NOT NULL,
  `product_qty_name` varchar(100) NOT NULL,
  `product_qty` int(11) NOT NULL,
  `product_single_price` double NOT NULL,
  `total_amount` double NOT NULL,
  `type` varchar(50) NOT NULL,
  `description` varchar(250) NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB  DEFAULT CHARSET=latin1 AUTO_INCREMENT=5 ;

--
-- Dumping data for table `tbl_damage_lost_product`
--

INSERT INTO `tbl_damage_lost_product` (`id`, `date`, `codeno`, `product_name`, `product_qty_name`, `product_qty`, `product_single_price`, `total_amount`, `type`, `description`) VALUES
(1, '2074-09-11', 'kk', 'Mobile', 'kk', 15, 77, 1155, 'Lost', 'sadasd'),
(3, '2074-09-15', 'kk', 'Mobile', 'kk', 1, 77, 77, 'Lost', 'ldkf'),
(4, '2074-09-15', 'as', 'Head Phone', 'sdsss', 1, 1111, 1111, 'Damage', 'Lost because of robbery');

-- --------------------------------------------------------

--
-- Table structure for table `tbl_import_product_record`
--

CREATE TABLE IF NOT EXISTS `tbl_import_product_record` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `date` date NOT NULL,
  `codeno` varchar(100) NOT NULL,
  `product_name` varchar(150) NOT NULL,
  `product_qty_name` varchar(100) NOT NULL,
  `product_single_price` double NOT NULL,
  `product_qty` int(11) NOT NULL,
  `total_amount` double NOT NULL,
  `qty_amount` int(11) NOT NULL,
  `description` varchar(250) NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB  DEFAULT CHARSET=latin1 AUTO_INCREMENT=7 ;

--
-- Dumping data for table `tbl_import_product_record`
--

INSERT INTO `tbl_import_product_record` (`id`, `date`, `codeno`, `product_name`, `product_qty_name`, `product_single_price`, `product_qty`, `total_amount`, `qty_amount`, `description`) VALUES
(2, '2074-09-11', 'kk', 'Mobile', 'kk', 77, 12, 924, 84, 'sdfsd'),
(3, '2074-09-11', 'aa', 'Mobile', 'aa', 88, 12, 1056, 96, 'df'),
(4, '2074-09-15', 'kk', 'Mobile', 'kk', 77, 5, 385, 35, 'kk'),
(5, '2074-09-15', 'kk', 'Mobile', 'kk', 77, 90, 6930, 630, 'kkk'),
(6, '2074-09-15', 'as', 'Head Phone', 'sdsss', 1111, 70, 77770, 770, 'kkl');

-- --------------------------------------------------------

--
-- Table structure for table `tbl_laboring`
--

CREATE TABLE IF NOT EXISTS `tbl_laboring` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `date` date NOT NULL,
  `job_type` varchar(100) NOT NULL,
  `amount` double NOT NULL,
  `description` varchar(200) NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB  DEFAULT CHARSET=latin1 AUTO_INCREMENT=6 ;

--
-- Dumping data for table `tbl_laboring`
--

INSERT INTO `tbl_laboring` (`id`, `date`, `job_type`, `amount`, `description`) VALUES
(2, '2074-09-11', 'sdfsd', 12000, 'sdf'),
(3, '2074-09-11', 'mechanic', 10000, 'lkdjfskdf'),
(4, '2074-09-15', 'mobile repairing', 12000, 'mobile sojeko '),
(5, '2074-09-15', 'mobil change', 1200, 'repairing of mobile ');

-- --------------------------------------------------------

--
-- Table structure for table `tbl_price_manager`
--

CREATE TABLE IF NOT EXISTS `tbl_price_manager` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `date` date NOT NULL,
  `codeno` varchar(100) NOT NULL,
  `product_name` varchar(150) NOT NULL,
  `qty_type` varchar(100) NOT NULL,
  `selling_price` double NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB  DEFAULT CHARSET=latin1 AUTO_INCREMENT=4 ;

--
-- Dumping data for table `tbl_price_manager`
--

INSERT INTO `tbl_price_manager` (`id`, `date`, `codeno`, `product_name`, `qty_type`, `selling_price`) VALUES
(1, '2074-09-11', 'as', 'Head Phone', 'sdsss', 1200),
(3, '2074-09-11', 'aa', 'Mobile', 'aa', 12000);

-- --------------------------------------------------------

--
-- Table structure for table `tbl_productname_entry`
--

CREATE TABLE IF NOT EXISTS `tbl_productname_entry` (
  `date` varchar(20) NOT NULL,
  `product_name` varchar(200) NOT NULL,
  `product_id` int(11) NOT NULL AUTO_INCREMENT,
  PRIMARY KEY (`product_id`)
) ENGINE=InnoDB  DEFAULT CHARSET=latin1 AUTO_INCREMENT=9 ;

--
-- Dumping data for table `tbl_productname_entry`
--

INSERT INTO `tbl_productname_entry` (`date`, `product_name`, `product_id`) VALUES
('2074-9-11', 'Mobile', 6),
('2074-9-11', 'Head Phone', 8);

-- --------------------------------------------------------

--
-- Table structure for table `tbl_qty_type_manager`
--

CREATE TABLE IF NOT EXISTS `tbl_qty_type_manager` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `date` date NOT NULL,
  `codeno` varchar(100) NOT NULL,
  `product_name` varchar(150) NOT NULL,
  `qty_type` varchar(100) NOT NULL,
  `qty_amount` int(11) NOT NULL,
  `buying_price` double NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB  DEFAULT CHARSET=latin1 AUTO_INCREMENT=5 ;

--
-- Dumping data for table `tbl_qty_type_manager`
--

INSERT INTO `tbl_qty_type_manager` (`id`, `date`, `codeno`, `product_name`, `qty_type`, `qty_amount`, `buying_price`) VALUES
(1, '2074-09-11', 'as', 'Head Phone', 'sdsss', 11, 1111),
(3, '2074-09-11', 'kk', 'Mobile', 'kk', 7, 77),
(4, '2074-09-11', 'aa', 'Mobile', 'aa', 8, 88);

-- --------------------------------------------------------

--
-- Table structure for table `tbl_selling`
--

CREATE TABLE IF NOT EXISTS `tbl_selling` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `date` date NOT NULL,
  `codeno` varchar(100) NOT NULL,
  `product_name` varchar(150) NOT NULL,
  `qty_type` varchar(100) NOT NULL,
  `product_qty` int(11) NOT NULL,
  `product_single_price` double NOT NULL,
  `total_amount` double NOT NULL,
  `description` varchar(250) NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB  DEFAULT CHARSET=latin1 AUTO_INCREMENT=9 ;

--
-- Dumping data for table `tbl_selling`
--

INSERT INTO `tbl_selling` (`id`, `date`, `codeno`, `product_name`, `qty_type`, `product_qty`, `product_single_price`, `total_amount`, `description`) VALUES
(2, '2074-08-11', 'as', 'Head Phone', 'sdsss', 10, 1200, 12000, 'sdasd'),
(3, '2074-09-14', 'aa', 'Mobile', 'aa', 12, 12000, 144000, 'df'),
(4, '2074-09-14', 'as', 'Head Phone', 'sdsss', 10, 1200, 12000, 'sdfsd lksdjfld lkdkf our faceility is very very much \nfavorable user the userk k dfj '),
(5, '2074-09-14', 'aa', 'Mobile', 'aa', 7, 12000, 84000, 'd'),
(6, '2074-09-14', 'as', 'Head Phone', 'sdsss', 8, 1200, 9600, 'df'),
(7, '2074-09-15', 'aa', 'Mobile', 'aa', 1, 12000, 12000, 'dfsf'),
(8, '2074-09-15', 'as', 'Head Phone', 'sdsss', 22, 1200, 26400, 'dsf');

-- --------------------------------------------------------

--
-- Table structure for table `tbl_user`
--

CREATE TABLE IF NOT EXISTS `tbl_user` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `date` date NOT NULL,
  `user_type` varchar(100) NOT NULL,
  `password` varchar(100) NOT NULL,
  `msetting` tinyint(4) NOT NULL,
  `mimport` tinyint(4) NOT NULL,
  `mselling` tinyint(4) NOT NULL,
  `mreporting` tinyint(4) NOT NULL,
  `mother` tinyint(4) NOT NULL,
  `lock_status` tinyint(4) NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB  DEFAULT CHARSET=latin1 AUTO_INCREMENT=3 ;

--
-- Dumping data for table `tbl_user`
--

INSERT INTO `tbl_user` (`id`, `date`, `user_type`, `password`, `msetting`, `mimport`, `mselling`, `mreporting`, `mother`, `lock_status`) VALUES
(1, '2074-12-12', 'lok', 'lok', 1, 1, 1, 1, 1, 0);
