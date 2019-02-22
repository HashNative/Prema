-- phpMyAdmin SQL Dump
-- version 4.6.4
-- https://www.phpmyadmin.net/
--
-- Host: 127.0.0.1
-- Generation Time: Feb 22, 2019 at 05:45 AM
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
(1, 'Tester', '7856908-9', 'kinniya', '1650', 'true');

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
(3, '3', '2019-01-17 12:21:14', 'Ilham', '123', 'C-1', '20000.0', '100.0', '19900.0', '', '', '2019-01-14 14:35:27', '2019-01-14 14:35:33', '200', '3980.0', 'Cash', 'admin', '3980', '0.0');

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
(1, '4');

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
(16, '13', '2019-02-21 23:30:48', 'Tester', '117', 'First Product', '0', '0', '2', '', '0', '100', '200', '50', '500', '650', 'Cash', 'admin', '1000', '-350');

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
(1, '14');

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
(1, 'Tester', 'First Product', '200', '100');

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
(6, '111', 'Customer', '2019-02-22 09:30:12', '2019-02-22 09:31:07', '111', 'Completed', '333'),
(7, '1111', 'Customer', '2019-02-22 09:32:25', '', '444', 'In Progress', '0'),
(8, '77', 'Customer', '2019-02-22 09:33:04', '', '111', 'In Progress', '0'),
(9, '222', 'Customer', '2019-02-22 09:34:51', '', '111', 'In Progress', '0');

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
(2, 'safeek', '54786897', 'kinniya', '200', '600');

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
  MODIFY `No` int(10) UNSIGNED NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=2;
--
-- AUTO_INCREMENT for table `grn`
--
ALTER TABLE `grn`
  MODIFY `No` int(10) UNSIGNED NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=4;
--
-- AUTO_INCREMENT for table `grnno`
--
ALTER TABLE `grnno`
  MODIFY `No` int(10) UNSIGNED NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=2;
--
-- AUTO_INCREMENT for table `invoice`
--
ALTER TABLE `invoice`
  MODIFY `No` int(10) UNSIGNED NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=17;
--
-- AUTO_INCREMENT for table `login`
--
ALTER TABLE `login`
  MODIFY `No` int(10) UNSIGNED NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=5;
--
-- AUTO_INCREMENT for table `personalprice`
--
ALTER TABLE `personalprice`
  MODIFY `No` int(10) UNSIGNED NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=2;
--
-- AUTO_INCREMENT for table `record`
--
ALTER TABLE `record`
  MODIFY `No` int(10) UNSIGNED NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=10;
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
