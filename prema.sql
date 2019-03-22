-- phpMyAdmin SQL Dump
-- version 4.6.4
-- https://www.phpmyadmin.net/
--
-- Host: 127.0.0.1
-- Generation Time: Mar 22, 2019 at 12:04 PM
-- Server version: 5.7.14
-- PHP Version: 5.6.25

SET SQL_MODE = "NO_AUTO_VALUE_ON_ZERO";
SET time_zone = "+00:00";


/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8mb4 */;

--
-- Database: `prema`
--

-- --------------------------------------------------------

--
-- Table structure for table `customers`
--

CREATE TABLE `customers` (
  `No` int(10) UNSIGNED NOT NULL,
  `Name` varchar(45) NOT NULL DEFAULT '',
  `Contact` varchar(45) NOT NULL DEFAULT '',
  `address` varchar(45) NOT NULL DEFAULT '',
  `due` varchar(45) NOT NULL DEFAULT '0',
  `vatnbt` varchar(45) NOT NULL DEFAULT ''
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

--
-- Dumping data for table `customers`
--

INSERT INTO `customers` (`No`, `Name`, `Contact`, `address`, `due`, `vatnbt`) VALUES
(1, 'Tester', '7856908-9', 'kinniya', '-550', 'true'),
(2, 'tuiuiuuguhfuehfhefheoihfiehfiehif', '13141', 'wdfewf', '200', 'true');

-- --------------------------------------------------------

--
-- Table structure for table `grn`
--

CREATE TABLE `grn` (
  `No` int(10) UNSIGNED NOT NULL,
  `grnno` varchar(45) NOT NULL DEFAULT '',
  `Date` varchar(45) NOT NULL DEFAULT '',
  `Supplier` varchar(45) NOT NULL DEFAULT '',
  `Plate_no` varchar(45) NOT NULL DEFAULT '',
  `Product` varchar(45) NOT NULL DEFAULT '',
  `first_weight` varchar(45) NOT NULL DEFAULT '',
  `second_weight` varchar(45) NOT NULL DEFAULT '',
  `net_weight` varchar(45) NOT NULL DEFAULT '',
  `reduced_weight` varchar(255) DEFAULT NULL,
  `cross_weight` varchar(255) DEFAULT NULL,
  `intime` varchar(45) NOT NULL DEFAULT '',
  `outtime` varchar(45) NOT NULL DEFAULT '',
  `amount` varchar(45) NOT NULL DEFAULT '',
  `total` varchar(45) NOT NULL DEFAULT '',
  `payment_method` varchar(45) NOT NULL DEFAULT '',
  `user` varchar(45) NOT NULL DEFAULT '',
  `paid` varchar(45) NOT NULL DEFAULT '',
  `due` varchar(45) NOT NULL DEFAULT ''
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

--
-- Dumping data for table `grn`
--

INSERT INTO `grn` (`No`, `grnno`, `Date`, `Supplier`, `Plate_no`, `Product`, `first_weight`, `second_weight`, `net_weight`, `reduced_weight`, `cross_weight`, `intime`, `outtime`, `amount`, `total`, `payment_method`, `user`, `paid`, `due`) VALUES
(1, '1', '2019-01-14 14:35:35', 'Ilham', '123', 'C-1', '20000.0', '100.0', '19900.0', '', '', '2019-01-14 14:35:27', '2019-01-14 14:35:33', '200', '3980.0', 'Cash', 'admin', '3980', '0.0'),
(2, '2', '2019-01-14 15:03:42', 'Ilham', '123', 'C-1', '20000.0', '100.0', '19900.0', '', '', '2019-01-14 14:35:27', '2019-01-14 14:35:33', '200', '3980.0', 'Cash', 'admin', '3980', '0.0'),
(3, '3', '2019-01-17 12:21:14', 'Ilham', '123', 'C-1', '20000.0', '100.0', '19900.0', '', '', '2019-01-14 14:35:27', '2019-01-14 14:35:33', '200', '3980.0', 'Cash', 'admin', '3980', '0.0'),
(4, '4', '2019-02-22 14:25:54', 'Customer', '111', 'C-1', '111.0', '333.0', '-222.0', '50', '-272.0', '2019-02-22 09:30:12', '2019-02-22 09:31:07', '200', '-54.4', 'Cash', 'admin', '50', '-104.4'),
(5, '5', '2019-02-22 14:54:46', 'Customer', '111', 'C-1', '111.0', '333.0', '-222.0', '50', '-272.0', '2019-02-22 09:30:12', '2019-02-22 09:31:07', '200', '-54.4', 'Cash', 'admin', '-100', '45.6'),
(6, '6', '2019-02-22 14:56:35', 'Customer', '111', 'C-1', '111.0', '333.0', '-222.0', '50', '-272.0', '2019-02-22 09:30:12', '2019-02-22 09:31:07', '200', '-54.4', 'Cash', 'admin', '100', '-154.4'),
(7, '7', '2019-02-22 15:01:40', 'Customer', '111', 'C-1', '111.0', '333.0', '-222.0', '50', '-272.0', '2019-02-22 09:30:12', '2019-02-22 09:31:07', '200', '-54.4', 'Cash', 'admin', '100', '-154.4'),
(8, '8', '2019-02-28 13:58:28', 'Customer', '111', 'C-1', '200.0', '333.0', '-133.0', '500', '-633.0', '2019-01-19 17:22:41', '2019-02-22 09:31:07', '200', '-126.6', 'Cash', 'admin', '0', '-126.6'),
(9, '10', '2019-03-05 17:00:29', 'tuiuiuuguhfuehfhefheoihfiehfiehif', '1234', 'C-1', '200.0', '300.0', '-100.0', '0', '-100.0', '2019-03-05 15:04:14', '2019-03-05 15:04:33', '200', '-20.0', 'Cash', 'admin', '0', '-20.0'),
(10, '11', '2019-03-05 17:00:29', 'tuiuiuuguhfuehfhefheoihfiehfiehif', '1234', 'C-1', '200.0', '300.0', '-100.0', '0', '-100.0', '2019-03-05 15:04:14', '2019-03-05 15:04:33', '200', '-20.0', 'Cash', 'admin', '0', '-20.0'),
(11, '12', '2019-03-05 17:00:29', 'tuiuiuuguhfuehfhefheoihfiehfiehif', '1234', 'C-1', '200.0', '300.0', '-100.0', '0', '-100.0', '2019-03-05 15:04:14', '2019-03-05 15:04:33', '200', '-20.0', 'Cash', 'admin', '0', '-20.0'),
(12, '13', '2019-03-05 17:00:29', 'tuiuiuuguhfuehfhefheoihfiehfiehif', '1234', 'C-1', '200.0', '300.0', '-100.0', '0', '-100.0', '2019-03-05 15:04:14', '2019-03-05 15:04:33', '200', '-20.0', 'Cash', 'admin', '0', '-20.0'),
(13, '14', '2019-03-05 17:00:29', 'tuiuiuuguhfuehfhefheoihfiehfiehif', '1234', 'C-1', '200.0', '300.0', '-100.0', '0', '-100.0', '2019-03-05 15:04:14', '2019-03-05 15:04:33', '0', '-0.0', 'Cash', 'admin', '0', '-0.0'),
(14, '15', '2019-03-05 17:00:29', 'tuiuiuuguhfuehfhefheoihfiehfiehif', '1234', 'C-1', '200.0', '300.0', '-100.0', '0', '-100.0', '2019-03-05 15:04:14', '2019-03-05 15:04:33', '0', '-0.0', 'Cash', 'admin', '0', '-0.0'),
(15, '16', '2019-03-05 17:00:29', 'tuiuiuuguhfuehfhefheoihfiehfiehif', '1234', 'C-1', '200.0', '300.0', '-100.0', '0', '-100.0', '2019-03-05 15:04:14', '2019-03-05 15:04:33', '0', '-0.0', 'Cash', 'admin', '0', '-0.0'),
(16, '17', '2019-03-07 11:35:42', 'Customer', '555', 'C-1', '90000.0', '2000.0', '88000.0', '20000', '68000.0', '2019-02-23 09:18:04', '2019-02-23 09:18:14', '200', '13600.0', 'Cash', 'admin', '5000', '8600.0'),
(17, '18', '2019-03-07 11:35:42', 'Customer', '555', 'C-1', '90000.0', '2000.0', '88000.0', '6000', '82000.0', '2019-02-23 09:18:04', '2019-02-23 09:18:14', '600', '49200.0', 'Cash', 'admin', '50000', '-800.0'),
(18, '19', '2019-03-07 11:35:42', 'Customer', '555', 'C-1', '90000.0', '2000.0', '88000.0', '60000', '28000.0', '2019-02-23 09:18:04', '2019-02-23 09:18:14', '0', '0.0', 'Cash', 'admin', '0', '0.0'),
(19, '20', '2019-03-07 11:35:42', 'Customer', '555', 'C-1', '90000.0', '2000.0', '88000.0', '6000', '82000.0', '2019-02-23 09:18:04', '2019-02-23 09:18:14', '600', '49200.0', 'Credit', 'admin', '0', '49200.0'),
(20, '21', '2019-03-08 11:44:27', 'safeek', '896', 'C-1', '50000.0', '2000.0', '48000.0', '5000', '43000.0', '2019-03-08 11:45:28', '2019-03-08 11:45:41', '600', '25800.0', 'Credit', 'admin', '9000', '16800.0'),
(21, '22', '2019-03-15 10:19:07', 'safeek', '896', 'C-1', '50000.0', '2000.0', '48000.0', '5000', '43000.0', '2019-03-08 11:45:28', '2019-03-08 11:45:41', '600', '25800.0', 'Cash', 'admin', '18000', '7800.0');

-- --------------------------------------------------------

--
-- Table structure for table `grnno`
--

CREATE TABLE `grnno` (
  `No` int(10) UNSIGNED NOT NULL,
  `grnno` varchar(45) NOT NULL DEFAULT ''
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

--
-- Dumping data for table `grnno`
--

INSERT INTO `grnno` (`No`, `grnno`) VALUES
(1, '23');

-- --------------------------------------------------------

--
-- Table structure for table `invoice`
--

CREATE TABLE `invoice` (
  `No` int(10) UNSIGNED NOT NULL,
  `ino` varchar(45) NOT NULL DEFAULT '',
  `Date` varchar(45) NOT NULL DEFAULT '',
  `Customer` varchar(45) NOT NULL DEFAULT '',
  `Plate_no` varchar(45) NOT NULL DEFAULT '',
  `Product` varchar(45) NOT NULL DEFAULT '',
  `first_weight` varchar(45) NOT NULL DEFAULT '',
  `second_weight` varchar(45) NOT NULL DEFAULT '',
  `net_weight` varchar(45) NOT NULL DEFAULT '',
  `intime` varchar(45) NOT NULL DEFAULT '',
  `outtime` varchar(45) NOT NULL DEFAULT '',
  `amount` varchar(45) NOT NULL DEFAULT '',
  `total` varchar(45) NOT NULL DEFAULT '',
  `discount` varchar(255) NOT NULL,
  `transport` varchar(255) NOT NULL,
  `net_total` varchar(255) NOT NULL,
  `payment_method` varchar(45) NOT NULL DEFAULT '',
  `user` varchar(45) NOT NULL DEFAULT '',
  `paid` varchar(45) NOT NULL DEFAULT '',
  `due` varchar(45) NOT NULL DEFAULT ''
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

--
-- Dumping data for table `invoice`
--

INSERT INTO `invoice` (`No`, `ino`, `Date`, `Customer`, `Plate_no`, `Product`, `first_weight`, `second_weight`, `net_weight`, `intime`, `outtime`, `amount`, `total`, `discount`, `transport`, `net_total`, `payment_method`, `user`, `paid`, `due`) VALUES
(1, '1', '2019-01-14 14:35:35', 'Ilham', '123', 'C-1', '20000.0', '100.0', '19900.0', '2019-01-14 14:35:27', '2019-01-14 14:35:33', '200', '3980.0', '0', '', '0', 'Cash', 'admin', '3980', '0.0'),
(2, '2', '2019-01-14 15:03:42', 'Ilham', '123', 'C-1', '20000.0', '100.0', '19900.0', '2019-01-14 14:35:27', '2019-01-14 14:35:33', '200', '3980.0', '0', '', '0', 'Cash', 'admin', '3980', '0.0'),
(3, '3', '2019-01-17 12:21:14', 'Ilham', '123', 'C-1', '20000.0', '100.0', '19900.0', '2019-01-14 14:35:27', '2019-01-14 14:35:33', '200', '3980.0', '0', '', '0', 'Cash', 'admin', '3980', '0.0'),
(4, '1', '2019-01-19 18:39:06', 'Tester', '111', 'First Product', '200.0', '2000.0', '1800.0', '2019-01-19 17:22:41', '2019-01-19 17:22:49', '200', '360.0', '0', '', '0', 'Cash', 'admin', '360', '0.0'),
(5, '2', '2019-01-28 15:00:33', 'Tester', '', 'First Product', '200.0', '2000.0', '1800.0', '2019-01-19 17:22:41', '2019-01-19 17:22:49', '200', '360.0', '0', '', '0', 'Cash', 'admin', '360', '0.0'),
(6, '3', '2019-01-31 11:43:59', 'Tester', '', 'First Product', '200.0', '2000.0', '2', '2019-01-19 17:22:41', '2019-01-19 17:22:49', '100', '200.0', '0', '', '0', 'Cash', 'admin', '200', '0.0'),
(7, '4', '2019-01-31 11:43:59', 'Tester', '', 'First Product', '200.0', '2000.0', '1800.0', '2019-01-19 17:22:41', '2019-01-19 17:22:49', '100', '180.0', '0', '', '0', 'Cash', 'admin', '180', '0.0'),
(8, '5', '2019-02-01 00:00:00', 'Tester', '', 'First Product', '1000.0', '2000.0', '2', '2019-02-01 10:23:05', '2019-02-01 10:25:27', '100', '200.0', '0', '', '0', 'Cash', 'admin', '200', '0.0'),
(9, '6', '2019-02-01 00:00:00', 'Tester', '', 'First Product', '1000.0', '2000.0', '1000.0', '2019-02-01 10:23:05', '2019-02-01 10:25:27', '200', '200.0', '0', '', '0', 'Cash', 'admin', '200', '0.0'),
(10, '7', '2019-02-08 11:42:57', 'Customer', '', 'First Product', '200.0', '2000.0', '1800.0', '2019-01-19 17:22:41', '2019-01-19 17:22:49', '200', '360.0', '0', '', '360.0', 'Cash', 'admin', '200', '160.0'),
(11, '8', '2019-02-08 11:42:57', 'Customer', '', 'First Product', '200.0', '2000.0', '1800.0', '2019-01-19 17:22:41', '2019-01-19 17:22:49', '200', '360.0', '0', '', '360.0', 'Cash', 'admin', '300', '60.0'),
(12, '9', '2019-02-08 11:45:30', 'Customer', '', 'First Product', '200.0', '2000.0', '1800.0', '2019-01-19 17:22:41', '2019-01-19 17:22:49', '200', '360.0', '0', '', '360.0', 'Cash', 'admin', '100', '260.0'),
(13, '10', '2019-02-08 11:47:24', 'Customer', '', 'First Product', '200.0', '2000.0', '1800.0', '2019-01-19 17:22:41', '2019-01-19 17:22:49', '200', '360.0', '0', '', '360.0', 'Cash', 'admin', '200', '160.0'),
(14, '11', '2019-02-08 11:47:24', 'Customer', '', 'First Product', '200.0', '2000.0', '1800.0', '2019-01-19 17:22:41', '2019-01-19 17:22:49', '200', '360.0', '0', '', '360.0', 'Cash', 'admin', '500', '-140.0'),
(15, '12', '2019-02-21 23:30:48', 'Customer', '111', 'First Product', '200.0', '2000.0', '1800.0', '2019-01-19 17:22:41', '2019-01-19 17:22:49', '200', '360.0', '50', '200', '510.0', 'Cash', 'admin', '500', '10.0'),
(16, '13', '2019-02-21 23:30:48', 'Tester', '117', 'First Product', '0', '0', '2', '', '0', '100', '200', '50', '500', '650', 'Cash', 'admin', '1000', '-350'),
(17, '14', '2019-02-22 11:49:27', 'Customer', '111', 'First Product', '111.0', '333.0', '222.0', '2019-02-22 09:30:12', '2019-02-22 09:31:07', '200', '44.4', '41', '50', '53.4', 'Cash', 'admin', '0', '53.4'),
(18, '15', '2019-02-22 14:56:35', 'Customer', '111', 'First Product', '111.0', '333.0', '222.0', '2019-02-22 09:30:12', '2019-02-22 09:31:07', '200', '44.4', '11', '11', '44.4', 'Cash', 'admin', '50', '-5.600000000000001'),
(19, '16', '2019-02-28 11:06:09', 'Customer', '111', 'First Product', '200.0', '333.0', '133.0', '2019-01-19 17:22:41', '2019-02-22 09:31:07', '200', '26.6', '0', '0', '26.6', 'Cash', 'admin', '0', '26.6'),
(20, '17', '2019-02-28 11:09:14', 'Customer', '111', 'First Product', '200.0', '333.0', '133.0', '2019-01-19 17:22:41', '2019-02-22 09:31:07', '200', '26.6', '0', '0', '26.6', 'Cash', 'admin', '0', '26.6'),
(21, '18', '2019-02-28 11:10:33', 'Customer', '111', 'First Product', '200.0', '333.0', '133.0', '2019-01-19 17:22:41', '2019-02-22 09:31:07', '200', '26.6', '0', '0', '26.6', 'Cash', 'admin', '0', '26.6'),
(22, '19', '2019-02-28 11:11:15', 'Customer', '111', 'First Product', '200.0', '333.0', '133.0', '2019-01-19 17:22:41', '2019-02-22 09:31:07', '200', '26.6', '0', '0', '26.6', 'Cash', 'admin', '0', '26.6'),
(23, '20', '2019-02-28 13:58:28', 'Customer', '111', 'First Product', '200.0', '333.0', '133.0', '2019-01-19 17:22:41', '2019-02-22 09:31:07', '200', '26.6', '0', '0', '26.6', 'Cash', 'admin', '0', '26.6'),
(24, '21', '2019-02-28 13:58:28', 'Tester', 'LM-147', 'First Product', '5000.0', '1000.0', '-4000.0', '2019-02-22 15:33:19', '2019-02-22 15:33:38', '200', '-800.0', '100', '0', '-900.0', 'Cash', 'admin', '0', '-900.0'),
(25, '22', '2019-02-28 14:13:56', 'Customer', '111', 'First Product', '200.0', '333.0', '133.0', '2019-01-19 17:22:41', '2019-02-22 09:31:07', '200', '26.6', '0', '0', '26.6', 'Cash', 'admin', '0', '26.6'),
(26, '23', '2019-03-02 11:32:26', 'Tester', '111', 'First Product', '0', '0', '1', '', '0', '100', '100', '0', '0', '100', 'Cash', 'admin', '0', '100'),
(27, '24', '2019-03-05 11:11:21', 'Customer', '111', 'First Product', '200.0', '333.0', '133.0', '2019-01-19 17:22:41', '2019-02-22 09:31:07', '200', '26.6', '0', '0', '26.6', 'Cash', 'admin', '0', '26.6'),
(28, '25', '2019-03-05 11:18:09', 'Customer', '111', 'First Product', '200.0', '333.0', '133.0', '2019-01-19 17:22:41', '2019-02-22 09:31:07', '200', '26.6', '0', '0', '26.6', 'Cash', 'admin', '0', '26.6'),
(29, '25', '2019-03-05 11:22:02', 'Customer', '111', 'First Product', '200.0', '333.0', '133.0', '2019-01-19 17:22:41', '2019-02-22 09:31:07', '200', '26.6', '0', '0', '26.6', 'Cash', 'admin', '0', '26.6'),
(30, '26', '2019-03-05 11:25:29', 'Customer', '111', 'First Product', '200.0', '333.0', '133.0', '2019-01-19 17:22:41', '2019-02-22 09:31:07', '200', '26.6', '0', '0', '26.6', 'Cash', 'admin', '0', '26.6'),
(31, '27', '2019-03-05 11:31:07', 'Customer', '111', 'First Product', '200.0', '333.0', '133.0', '2019-01-19 17:22:41', '2019-02-22 09:31:07', '200', '26.6', '0', '0', '26.6', 'Cash', 'admin', '0', '26.6'),
(32, '28', '2019-03-05 11:36:22', 'Customer', '555', 'First Product', '90000.0', '2000.0', '-88000.0', '2019-02-23 09:18:04', '2019-02-23 09:18:14', '200', '-17600.0', '0', '0', '-17600.0', 'Cash', 'admin', '0', '-17600.0'),
(33, '29', '2019-03-05 11:40:17', 'Tester', 'LM-147', 'First Product', '5000.0', '1000.0', '-4000.0', '2019-02-22 15:33:19', '2019-02-22 15:33:38', '200', '-800.0', '0', '0', '-800.0', 'Cash', 'admin', '0', '-800.0'),
(34, '30', '2019-03-05 14:42:08', 'Customer', '555', 'First Product', '90000.0', '2000.0', '-88000.0', '2019-02-23 09:18:04', '2019-02-23 09:18:14', '200', '-17600.0', '0', '0', '-17600.0', 'Cash', 'admin', '0', '-17600.0'),
(35, '31', '2019-03-05 14:46:19', 'Customer', '555', 'First Product', '90000.0', '2000.0', '-88000.0', '2019-02-23 09:18:04', '2019-02-23 09:18:14', '200', '-17600.0', '0', '0', '-17600.0', 'Cash', 'admin', '0', '-17600.0'),
(36, '32', '2019-03-05 14:48:03', 'Customer', '555', 'First Product', '90000.0', '2000.0', '-88000.0', '2019-02-23 09:18:04', '2019-02-23 09:18:14', '200', '-17600.0', '0', '0', '-17600.0', 'Cash', 'admin', '0', '-17600.0'),
(37, '33', '2019-03-05 14:53:00', 'Customer', '111', 'First Product', '200.0', '333.0', '133.0', '2019-01-19 17:22:41', '2019-02-22 09:31:07', '0', '0.0', '0', '0', '0.0', 'Cash', 'admin', '0', '0.0'),
(38, '33', '2019-03-05 14:55:21', 'Customer', '111', 'First Product', '200.0', '333.0', '133.0', '2019-01-19 17:22:41', '2019-02-22 09:31:07', '200', '26.6', '0', '0', '26.6', 'Cash', 'admin', '0', '26.6'),
(39, '34', '2019-03-05 15:03:29', 'tuiuiuuguhfuehfhefheoihfiehfiehif', '1234', 'First Product', '200.0', '300.0', '100.0', '2019-03-05 15:04:14', '2019-03-05 15:04:33', '200', '20.0', '0', '0', '20.0', 'Cash', 'admin', '0', '20.0'),
(40, '35', '2019-03-05 15:03:29', 'tuiuiuuguhfuehfhefheoihfiehfiehif', '1234', 'First Product', '200.0', '300.0', '100.0', '2019-03-05 15:04:14', '2019-03-05 15:04:33', '200', '20.0', '0', '0', '20.0', 'Cash', 'admin', '0', '20.0'),
(41, '36', '2019-03-05 15:03:29', 'tuiuiuuguhfuehfhefheoihfiehfiehif', '1234', 'First Product', '200.0', '300.0', '100.0', '2019-03-05 15:04:14', '2019-03-05 15:04:33', '200', '20.0', '0', '0', '20.0', 'Cash', 'admin', '0', '20.0'),
(42, '37', '2019-03-05 15:03:29', 'tuiuiuuguhfuehfhefheoihfiehfiehif', '1234', 'First Product', '200.0', '300.0', '100.0', '2019-03-05 15:04:14', '2019-03-05 15:04:33', '200', '20.0', '0', '0', '20.0', 'Cash', 'admin', '0', '20.0'),
(43, '38', '2019-03-05 15:03:29', 'tuiuiuuguhfuehfhefheoihfiehfiehif', '1234', 'First Product', '200.0', '300.0', '100.0', '2019-03-05 15:04:14', '2019-03-05 15:04:33', '200', '20.0', '0', '0', '20.0', 'Cash', 'admin', '0', '20.0'),
(44, '39', '2019-03-05 15:03:29', 'tuiuiuuguhfuehfhefheoihfiehfiehif', '1234', 'First Product', '200.0', '300.0', '100.0', '2019-03-05 15:04:14', '2019-03-05 15:04:33', '200', '20.0', '0', '0', '20.0', 'Cash', 'admin', '0', '20.0'),
(45, '40', '2019-03-05 15:03:29', 'tuiuiuuguhfuehfhefheoihfiehfiehif', '1234', 'First Product', '200.0', '300.0', '100.0', '2019-03-05 15:04:14', '2019-03-05 15:04:33', '200', '20.0', '0', '0', '20.0', 'Cash', 'admin', '0', '20.0'),
(46, '41', '2019-03-05 15:03:29', 'tuiuiuuguhfuehfhefheoihfiehfiehif', '1234', 'First Product', '200.0', '300.0', '100.0', '2019-03-05 15:04:14', '2019-03-05 15:04:33', '200', '20.0', '0', '0', '20.0', 'Cash', 'admin', '0', '20.0'),
(47, '42', '2019-03-05 15:03:29', 'tuiuiuuguhfuehfhefheoihfiehfiehif', '1234', 'First Product', '200.0', '300.0', '100.0', '2019-03-05 15:04:14', '2019-03-05 15:04:33', '200', '20.0', '0', '0', '20.0', 'Cash', 'admin', '0', '20.0'),
(48, '43', '2019-03-05 17:00:29', 'tuiuiuuguhfuehfhefheoihfiehfiehif', '1234', 'First Product', '200.0', '300.0', '100.0', '2019-03-05 15:04:14', '2019-03-05 15:04:33', '200', '20.0', '0', '0', '20.0', 'Cash', 'admin', '0', '20.0'),
(49, '44', '2019-03-06 12:17:27', 'Customer', '111', 'First Product', '200.0', '333.0', '133.0', '2019-01-19 17:22:41', '2019-02-22 09:31:07', '200', '26.6', '0', '0', '26.6', 'Credit', 'admin', '0', '26.6'),
(50, '45', '2019-03-06 12:17:27', 'Customer', '111', 'First Product', '200.0', '333.0', '133.0', '2019-01-19 17:22:41', '2019-02-22 09:31:07', '0', '0.0', '0', '0', '0.0', 'Credit', 'admin', '0', '0.0'),
(51, '46', '2019-03-06 12:17:27', 'Customer', '111', 'First Product', '200.0', '333.0', '133.0', '2019-01-19 17:22:41', '2019-02-22 09:31:07', '0', '0.0', '0', '0', '0.0', 'Credit', 'admin', '0', '0.0'),
(52, '47', '2019-03-06 12:17:27', 'Customer', '111', 'First Product', '200.0', '333.0', '133.0', '2019-01-19 17:22:41', '2019-02-22 09:31:07', '0', '0.0', '0', '0', '0.0', 'Credit', 'admin', '0', '0.0'),
(53, '48', '2019-03-06 12:17:27', 'Customer', '111', 'First Product', '200.0', '333.0', '133.0', '2019-01-19 17:22:41', '2019-02-22 09:31:07', '0', '0.0', '0', '0', '0.0', 'Credit', 'admin', '0', '0.0'),
(54, '49', '2019-03-06 12:17:27', 'Customer', '111', 'First Product', '200.0', '333.0', '133.0', '2019-01-19 17:22:41', '2019-02-22 09:31:07', '0', '0.0', '0', '0', '0.0', 'Credit', 'admin', '0', '0.0'),
(55, '50', '2019-03-15 00:00:00', 'Tester', '896', 'First Product', '0', '0', '10', '', '0', '100', '1000', '100', '500', '1400', 'Cash', 'admin', '2000', '-600');

-- --------------------------------------------------------

--
-- Table structure for table `invoiceno`
--

CREATE TABLE `invoiceno` (
  `No` int(10) UNSIGNED NOT NULL DEFAULT '1',
  `ino` varchar(45) NOT NULL DEFAULT '1'
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

--
-- Dumping data for table `invoiceno`
--

INSERT INTO `invoiceno` (`No`, `ino`) VALUES
(1, '51');

-- --------------------------------------------------------

--
-- Table structure for table `login`
--

CREATE TABLE `login` (
  `No` int(10) UNSIGNED NOT NULL,
  `username` varchar(45) NOT NULL DEFAULT '',
  `password` varchar(45) NOT NULL DEFAULT ''
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

--
-- Dumping data for table `login`
--

INSERT INTO `login` (`No`, `username`, `password`) VALUES
(1, 'admin', 'admin'),
(4, 'user', '123');

-- --------------------------------------------------------

--
-- Table structure for table `personalprice`
--

CREATE TABLE `personalprice` (
  `No` int(10) UNSIGNED NOT NULL,
  `Customer` varchar(45) NOT NULL DEFAULT '',
  `Product` varchar(45) NOT NULL DEFAULT '',
  `Price` varchar(45) NOT NULL DEFAULT '',
  `Cube_price` varchar(45) NOT NULL DEFAULT ''
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

--
-- Dumping data for table `personalprice`
--

INSERT INTO `personalprice` (`No`, `Customer`, `Product`, `Price`, `Cube_price`) VALUES
(1, 'Tester', 'First Product', '200', '100'),
(2, 'tuiuiuuguhfuehfhefheoihfiehfiehif', 'First Product', '200', '0');

-- --------------------------------------------------------

--
-- Table structure for table `record`
--

CREATE TABLE `record` (
  `No` int(10) UNSIGNED NOT NULL,
  `Plate_no` varchar(45) NOT NULL DEFAULT '',
  `Driver_name` varchar(45) NOT NULL DEFAULT '',
  `Intime` varchar(45) NOT NULL DEFAULT '',
  `Outtime` varchar(45) NOT NULL DEFAULT '',
  `Inweight` varchar(45) NOT NULL DEFAULT '',
  `status` varchar(45) NOT NULL DEFAULT '',
  `OutWeight` varchar(45) NOT NULL DEFAULT ''
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

--
-- Dumping data for table `record`
--

INSERT INTO `record` (`No`, `Plate_no`, `Driver_name`, `Intime`, `Outtime`, `Inweight`, `status`, `OutWeight`) VALUES
(1, '123', 'Ilham', '2019-01-14 14:35:27', '2019-02-01 10:18:15', '20000', 'Completed', '0'),
(2, '111', 'Customer', '2019-01-19 17:22:41', '2019-02-22 09:31:07', '200', 'Completed', '333'),
(3, '123', 'Ilham', '2019-02-01 10:18:10', '2019-02-01 10:18:15', '0', 'Completed', '0'),
(4, '111DN', 'Customer', '2019-02-01 10:23:05', '2019-02-01 10:25:27', '1000', 'Completed', '2000'),
(10, 'LM-147', 'Tester', '2019-02-22 15:33:19', '2019-02-22 15:33:38', '5000', 'Completed', '1000'),
(11, '555', 'Customer', '2019-02-23 09:18:04', '2019-02-23 09:18:14', '90000', 'Completed', '2000'),
(12, '1234', 'tuiuiuuguhfuehfhefheoihfiehfiehif', '2019-03-05 15:04:14', '2019-03-05 15:04:33', '200', 'Completed', '300'),
(13, '896', 'safeek', '2019-03-08 11:45:28', '2019-03-08 11:45:41', '50000', 'Completed', '2000');

-- --------------------------------------------------------

--
-- Table structure for table `stock`
--

CREATE TABLE `stock` (
  `No` int(10) UNSIGNED ZEROFILL NOT NULL,
  `Product` varchar(45) NOT NULL DEFAULT '',
  `weight` varchar(45) NOT NULL DEFAULT '',
  `unit` varchar(45) NOT NULL DEFAULT ''
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

--
-- Dumping data for table `stock`
--

INSERT INTO `stock` (`No`, `Product`, `weight`, `unit`) VALUES
(0000000001, 'First Product', '2000', '');

-- --------------------------------------------------------

--
-- Table structure for table `suppliers`
--

CREATE TABLE `suppliers` (
  `No` int(10) UNSIGNED NOT NULL,
  `name` varchar(45) NOT NULL DEFAULT '',
  `contact` varchar(45) NOT NULL DEFAULT '',
  `Address` varchar(45) NOT NULL DEFAULT '',
  `Due` varchar(45) NOT NULL DEFAULT '',
  `amount` varchar(45) NOT NULL DEFAULT ''
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

--
-- Dumping data for table `suppliers`
--

INSERT INTO `suppliers` (`No`, `name`, `contact`, `Address`, `Due`, `amount`) VALUES
(1, 'Ilham', '11234', 'kinniya', '20000', '200'),
(2, 'safeek', '54786897', 'kinniya', '24800', '600');

--
-- Indexes for dumped tables
--

--
-- Indexes for table `customers`
--
ALTER TABLE `customers`
  ADD PRIMARY KEY (`No`);

--
-- Indexes for table `grn`
--
ALTER TABLE `grn`
  ADD PRIMARY KEY (`No`);

--
-- Indexes for table `grnno`
--
ALTER TABLE `grnno`
  ADD PRIMARY KEY (`No`);

--
-- Indexes for table `invoice`
--
ALTER TABLE `invoice`
  ADD PRIMARY KEY (`No`);

--
-- Indexes for table `invoiceno`
--
ALTER TABLE `invoiceno`
  ADD PRIMARY KEY (`No`);

--
-- Indexes for table `login`
--
ALTER TABLE `login`
  ADD PRIMARY KEY (`No`);

--
-- Indexes for table `personalprice`
--
ALTER TABLE `personalprice`
  ADD PRIMARY KEY (`No`);

--
-- Indexes for table `record`
--
ALTER TABLE `record`
  ADD PRIMARY KEY (`No`);

--
-- Indexes for table `stock`
--
ALTER TABLE `stock`
  ADD PRIMARY KEY (`No`);

--
-- Indexes for table `suppliers`
--
ALTER TABLE `suppliers`
  ADD PRIMARY KEY (`No`);

--
-- AUTO_INCREMENT for dumped tables
--

--
-- AUTO_INCREMENT for table `customers`
--
ALTER TABLE `customers`
  MODIFY `No` int(10) UNSIGNED NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=3;
--
-- AUTO_INCREMENT for table `grn`
--
ALTER TABLE `grn`
  MODIFY `No` int(10) UNSIGNED NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=22;
--
-- AUTO_INCREMENT for table `grnno`
--
ALTER TABLE `grnno`
  MODIFY `No` int(10) UNSIGNED NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=2;
--
-- AUTO_INCREMENT for table `invoice`
--
ALTER TABLE `invoice`
  MODIFY `No` int(10) UNSIGNED NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=56;
--
-- AUTO_INCREMENT for table `login`
--
ALTER TABLE `login`
  MODIFY `No` int(10) UNSIGNED NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=5;
--
-- AUTO_INCREMENT for table `personalprice`
--
ALTER TABLE `personalprice`
  MODIFY `No` int(10) UNSIGNED NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=3;
--
-- AUTO_INCREMENT for table `record`
--
ALTER TABLE `record`
  MODIFY `No` int(10) UNSIGNED NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=14;
--
-- AUTO_INCREMENT for table `stock`
--
ALTER TABLE `stock`
  MODIFY `No` int(10) UNSIGNED ZEROFILL NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=2;
--
-- AUTO_INCREMENT for table `suppliers`
--
ALTER TABLE `suppliers`
  MODIFY `No` int(10) UNSIGNED NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=3;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
