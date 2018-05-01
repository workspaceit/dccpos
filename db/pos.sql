-- phpMyAdmin SQL Dump
-- version 4.0.10deb1
-- http://www.phpmyadmin.net
--
-- Host: localhost
-- Generation Time: May 01, 2018 at 01:02 PM
-- Server version: 5.6.39
-- PHP Version: 5.5.9-1ubuntu4.24

SET SQL_MODE = "NO_AUTO_VALUE_ON_ZERO";
SET time_zone = "+00:00";


/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8 */;

--
-- Database: `pos`
--

-- --------------------------------------------------------

--
-- Table structure for table `acc_entries`
--

CREATE TABLE IF NOT EXISTS `acc_entries` (
  `id` bigint(20) unsigned NOT NULL AUTO_INCREMENT,
  `entry_type` int(11) unsigned DEFAULT NULL,
  `number` int(11) DEFAULT NULL,
  `date` date NOT NULL,
  `dr_total` decimal(15,2) NOT NULL DEFAULT '0.00',
  `cr_total` decimal(15,2) NOT NULL DEFAULT '0.00',
  `narration` text COLLATE utf8_unicode_ci NOT NULL,
  `created_at` timestamp NULL DEFAULT NULL,
  `created_by` int(10) unsigned DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `ac_entries_created_by_index` (`created_by`),
  KEY `FKgjbihsd3i7rquwws86fcye0jl` (`entry_type`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_unicode_ci AUTO_INCREMENT=1 ;

-- --------------------------------------------------------

--
-- Table structure for table `acc_entry_items`
--

CREATE TABLE IF NOT EXISTS `acc_entry_items` (
  `id` bigint(20) unsigned NOT NULL AUTO_INCREMENT,
  `entry_id` bigint(20) unsigned DEFAULT NULL,
  `ledger_id` int(10) unsigned NOT NULL,
  `amount` decimal(15,2) NOT NULL DEFAULT '0.00',
  `dc` enum('DR','CR') COLLATE utf8_unicode_ci NOT NULL,
  `reconciliation_date` datetime DEFAULT NULL,
  `created_at` timestamp NULL DEFAULT NULL,
  `created_by` int(10) unsigned DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `FKsktxpw9rj3098v6icvo480t87` (`created_by`),
  KEY `FKt6w8e3lqgl6v1gvmuxalw34jw` (`entry_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_unicode_ci AUTO_INCREMENT=1 ;

-- --------------------------------------------------------

--
-- Table structure for table `acc_entry_types`
--

CREATE TABLE IF NOT EXISTS `acc_entry_types` (
  `id` int(10) unsigned NOT NULL AUTO_INCREMENT,
  `label` enum('RECEIPT','PAYMENT','CONTRA','JOURNAL') COLLATE utf8_unicode_ci NOT NULL,
  `name` varchar(100) COLLATE utf8_unicode_ci NOT NULL,
  `description` varchar(255) COLLATE utf8_unicode_ci NOT NULL,
  `base_type` int(11) NOT NULL,
  `numbering` int(11) NOT NULL,
  `prefix` varchar(10) COLLATE utf8_unicode_ci NOT NULL,
  `suffix` varchar(10) COLLATE utf8_unicode_ci NOT NULL,
  `zero_padding` int(11) NOT NULL,
  `bank_cash_ledger_restriction` int(11) NOT NULL DEFAULT '1',
  `created_at` timestamp NULL DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB  DEFAULT CHARSET=utf8 COLLATE=utf8_unicode_ci AUTO_INCREMENT=5 ;

--
-- Dumping data for table `acc_entry_types`
--

INSERT INTO `acc_entry_types` (`id`, `label`, `name`, `description`, `base_type`, `numbering`, `prefix`, `suffix`, `zero_padding`, `bank_cash_ledger_restriction`, `created_at`) VALUES
  (1, 'RECEIPT', 'Reciept', 'Received in Bank account or Cash account', 1, 1, '', '', 0, 2, NULL),
  (2, 'PAYMENT', 'Payment', 'Payment made from Bank account or Cash account', 1, 1, '', '', 0, 3, NULL),
  (3, 'CONTRA', 'Contra', 'Transfer between Bank account and Cash account', 1, 1, '', '', 0, 4, NULL),
  (4, 'JOURNAL', 'Journal', 'Transfer between Non Bank account and Cash account', 1, 1, '', '', 0, 5, NULL);

-- --------------------------------------------------------

--
-- Table structure for table `acc_groups`
--

CREATE TABLE IF NOT EXISTS `acc_groups` (
  `id` bigint(18) NOT NULL AUTO_INCREMENT,
  `parent_id` bigint(18) DEFAULT '0',
  `name` varchar(255) COLLATE utf8_unicode_ci NOT NULL,
  `code` enum('ASSET','LIABILITY','EXPENSE','INCOME','SALE','WHOLE_SALE','SALARY','EMPLOYEE_SALARY','SUPPLIER','WHOLESALER','RECEIVABLE','PAYABLE') COLLATE utf8_unicode_ci DEFAULT NULL,
  `affects_gross` int(1) NOT NULL DEFAULT '0',
  PRIMARY KEY (`id`),
  UNIQUE KEY `unique_id` (`id`),
  UNIQUE KEY `name` (`name`),
  UNIQUE KEY `code` (`code`),
  KEY `id` (`id`),
  KEY `parent_id` (`parent_id`)
) ENGINE=InnoDB  DEFAULT CHARSET=utf8 COLLATE=utf8_unicode_ci AUTO_INCREMENT=13 ;

--
-- Dumping data for table `acc_groups`
--

INSERT INTO `acc_groups` (`id`, `parent_id`, `name`, `code`, `affects_gross`) VALUES
  (1, NULL, 'Assets', 'ASSET', 0),
  (2, NULL, 'Liabilities and Owners Equity', 'LIABILITY', 0),
  (3, NULL, 'Incomes', 'INCOME', 0),
  (4, NULL, 'Expenses', 'EXPENSE', 0),
  (5, 2, 'Wholesaler', 'WHOLESALER', 0),
  (6, 3, 'Sale', 'SALE', 0),
  (7, 6, 'Whole sale', 'WHOLE_SALE', 0),
  (8, 4, 'Salary', 'SALARY', 0),
  (9, 2, 'Supplier', 'SUPPLIER', 0),
  (11, 1, 'Receivable ', 'RECEIVABLE', 0),
  (12, 2, 'Payable', 'PAYABLE', 0);

-- --------------------------------------------------------

--
-- Table structure for table `acc_ledgers`
--

CREATE TABLE IF NOT EXISTS `acc_ledgers` (
  `id` bigint(18) NOT NULL AUTO_INCREMENT,
  `group_id` bigint(18) NOT NULL,
  `personal_info_id` int(11) DEFAULT NULL,
  `company_id` int(11) DEFAULT NULL,
  `code` enum('INVENTORY','COGS','CASH','SHIPMENT_COST','DUE_SHIPMENT_COST','INVESTMENT') COLLATE utf8_unicode_ci DEFAULT NULL,
  `name` varchar(255) COLLATE utf8_unicode_ci NOT NULL,
  `op_balance` decimal(25,2) NOT NULL DEFAULT '0.00',
  `op_balance_dc` enum('DR','CR') COLLATE utf8_unicode_ci NOT NULL,
  `current_balance` decimal(25,2) NOT NULL,
  `current_balance_dc` enum('DR','CR') COLLATE utf8_unicode_ci NOT NULL,
  `type` enum('CASH_ACCOUNT','OTHER') COLLATE utf8_unicode_ci NOT NULL,
  `reconciliation` int(1) NOT NULL DEFAULT '0',
  `notes` varchar(500) COLLATE utf8_unicode_ci NOT NULL,
  `created_at` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP,
  PRIMARY KEY (`id`),
  UNIQUE KEY `unique_id` (`id`),
  UNIQUE KEY `code` (`code`),
  KEY `id` (`id`),
  KEY `group_id` (`group_id`),
  KEY `personal_info_id` (`personal_info_id`),
  KEY `company_id` (`company_id`),
  FULLTEXT KEY `name` (`name`)
) ENGINE=InnoDB  DEFAULT CHARSET=utf8 COLLATE=utf8_unicode_ci AUTO_INCREMENT=179 ;

--
-- Dumping data for table `acc_ledgers`
--

INSERT INTO `acc_ledgers` (`id`, `group_id`, `personal_info_id`, `company_id`, `code`, `name`, `op_balance`, `op_balance_dc`, `current_balance`, `current_balance_dc`, `type`, `reconciliation`, `notes`, `created_at`) VALUES
  (1, 1, NULL, NULL, 'INVENTORY', 'Inventory', 0.00, 'DR', 0.00, 'DR', 'OTHER', 0, '', '2018-04-20 10:27:16'),
  (2, 4, NULL, NULL, 'COGS', 'Cost Of Good Sold', 0.00, 'DR', 0.00, 'DR', 'OTHER', 0, '', '2018-04-20 10:35:20'),
  (3, 1, NULL, NULL, 'CASH', 'Cash', 0.00, 'DR', 0.00, 'DR', 'CASH_ACCOUNT', 0, '', '2018-04-20 10:36:24'),
  (4, 4, NULL, NULL, 'SHIPMENT_COST', 'Shipment Cost', 0.00, 'DR', 0.00, 'DR', 'OTHER', 0, '', '2018-04-20 10:41:28'),
  (5, 1, NULL, NULL, NULL, 'Bank', 0.00, 'DR', 0.00, 'DR', 'CASH_ACCOUNT', 0, '', '2018-04-24 06:36:45'),
  (6, 2, NULL, NULL, 'DUE_SHIPMENT_COST', 'Due shipment cost', 0.00, 'CR', 0.00, 'CR', 'OTHER', 0, '', '2018-04-23 10:09:29'),
  (7, 2, NULL, NULL, 'INVESTMENT', 'Owner''s Investment', 0.00, 'CR', 0.00, 'CR', 'OTHER', 0, '', '2018-05-01 07:46:29'),
  (101, 8, 1, NULL, NULL, 'Person 1', 0.00, 'DR', 0.00, 'DR', 'OTHER', 0, '', '2018-04-20 12:26:10');

-- --------------------------------------------------------

--
-- Table structure for table `acess_role`
--

CREATE TABLE IF NOT EXISTS `acess_role` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `auth_credential_id` int(11) NOT NULL,
  `role` enum('ALL','POS_OPERATOR') COLLATE utf8_unicode_ci NOT NULL,
  `created_at` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  PRIMARY KEY (`id`),
  KEY `auth_credential_id` (`auth_credential_id`)
) ENGINE=InnoDB  DEFAULT CHARSET=utf8 COLLATE=utf8_unicode_ci AUTO_INCREMENT=2 ;

--
-- Dumping data for table `acess_role`
--

INSERT INTO `acess_role` (`id`, `auth_credential_id`, `role`, `created_at`) VALUES
  (1, 1, 'POS_OPERATOR', '2018-04-30 11:09:53');

-- --------------------------------------------------------

--
-- Table structure for table `address`
--

CREATE TABLE IF NOT EXISTS `address` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `formatted_address` text CHARACTER SET utf8 COLLATE utf8_unicode_ci,
  `created_at` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB  DEFAULT CHARSET=latin1 AUTO_INCREMENT=2 ;

--
-- Dumping data for table `address`
--

INSERT INTO `address` (`id`, `formatted_address`, `created_at`) VALUES
  (1, 'Road 84, House 16. Nikunja 2,Dhaka', '2018-04-20 13:23:12');

-- --------------------------------------------------------

--
-- Table structure for table `auth_credential`
--

CREATE TABLE IF NOT EXISTS `auth_credential` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `personal_info_id` int(11) NOT NULL,
  `email` varchar(200) NOT NULL,
  `password` varchar(200) NOT NULL,
  `status` enum('ACTIVE','DEACTIVE') NOT NULL,
  `created_at` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  PRIMARY KEY (`id`),
  KEY `FK204315vkkjatxsc2egk1ifmlf` (`personal_info_id`)
) ENGINE=InnoDB  DEFAULT CHARSET=latin1 AUTO_INCREMENT=2 ;

--
-- Dumping data for table `auth_credential`
--

INSERT INTO `auth_credential` (`id`, `personal_info_id`, `email`, `password`, `status`, `created_at`) VALUES
  (1, 1, 'admin@admin.com', '$2a$10$5aTN9klJNhO3DERcg/j11.VQC2skPV2zz37SG3kraKRms.v5.XYyC', 'ACTIVE', '2018-04-24 04:47:04');

-- --------------------------------------------------------

--
-- Table structure for table `category`
--

CREATE TABLE IF NOT EXISTS `category` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `name` varchar(200) CHARACTER SET utf8 COLLATE utf8_unicode_ci NOT NULL,
  `created_at` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB  DEFAULT CHARSET=latin1 AUTO_INCREMENT=4 ;

--
-- Dumping data for table `category`
--

INSERT INTO `category` (`id`, `name`, `created_at`) VALUES
  (1, 'Chocolate', '2018-04-16 08:15:59'),
  (2, 'Grocery', '2018-04-16 08:15:59'),
  (3, 'Drinks', '2018-04-16 08:16:11');

-- --------------------------------------------------------

--
-- Table structure for table `company`
--

CREATE TABLE IF NOT EXISTS `company` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `address_id` int(11) DEFAULT NULL,
  `title` varchar(200) CHARACTER SET utf8 COLLATE utf8_unicode_ci NOT NULL,
  `phone` varchar(200) CHARACTER SET utf8 COLLATE utf8_unicode_ci DEFAULT NULL,
  `email` varchar(200) CHARACTER SET utf8 COLLATE utf8_unicode_ci DEFAULT NULL,
  `created_at` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  PRIMARY KEY (`id`),
  KEY `address_id` (`address_id`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1 AUTO_INCREMENT=1 ;

-- --------------------------------------------------------

--
-- Table structure for table `company_role`
--

CREATE TABLE IF NOT EXISTS `company_role` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `personal_info_id` int(11) NOT NULL,
  `role` varchar(200) CHARACTER SET utf8 COLLATE utf8_unicode_ci NOT NULL,
  `created_at` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  PRIMARY KEY (`id`),
  KEY `personal_info_id` (`personal_info_id`)
) ENGINE=InnoDB  DEFAULT CHARSET=latin1 AUTO_INCREMENT=2 ;

--
-- Dumping data for table `company_role`
--

INSERT INTO `company_role` (`id`, `personal_info_id`, `role`, `created_at`) VALUES
  (1, 1, 'EMPLOYEE', '2018-04-20 12:26:10');

-- --------------------------------------------------------

--
-- Table structure for table `employee`
--

CREATE TABLE IF NOT EXISTS `employee` (
  `id` int(11) unsigned NOT NULL AUTO_INCREMENT,
  `personal_info_id` int(11) NOT NULL,
  `employee_id` varchar(200) DEFAULT NULL,
  `type` enum('ADMIN','OFFICER') NOT NULL,
  `salary` float(11,2) NOT NULL,
  `created_at` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP,
  PRIMARY KEY (`id`),
  UNIQUE KEY `personal_info_id` (`personal_info_id`),
  UNIQUE KEY `employee_id` (`employee_id`)
) ENGINE=InnoDB  DEFAULT CHARSET=latin1 AUTO_INCREMENT=2 ;

--
-- Dumping data for table `employee`
--

INSERT INTO `employee` (`id`, `personal_info_id`, `employee_id`, `type`, `salary`, `created_at`) VALUES
  (1, 1, '1231', 'OFFICER', 125.00, '2018-04-20 12:26:10');

-- --------------------------------------------------------

--
-- Table structure for table `inventory`
--

CREATE TABLE IF NOT EXISTS `inventory` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `product_id` int(11) NOT NULL,
  `shipment_id` int(11) DEFAULT NULL,
  `purchase_price` decimal(7,2) NOT NULL,
  `purchase_quantity` int(11) NOT NULL,
  `sold_quantity` int(11) NOT NULL,
  `available_quantity` int(11) NOT NULL,
  `status` enum('IN_STOCK','SOLD_OUT') NOT NULL,
  `created_at` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP,
  PRIMARY KEY (`id`),
  KEY `product_id` (`product_id`,`shipment_id`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1 AUTO_INCREMENT=1 ;

-- --------------------------------------------------------

--
-- Table structure for table `inventory_details`
--

CREATE TABLE IF NOT EXISTS `inventory_details` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `inventory_id` int(11) DEFAULT NULL COMMENT 'Keep it for Hibernate sake',
  `selling_price` decimal(25,2) NOT NULL,
  `purchased_quantity` int(11) NOT NULL,
  `sold_quantity` int(11) NOT NULL,
  `available_quantity` int(11) NOT NULL,
  `condition` enum('GOOD','DAMAGED') NOT NULL,
  `cycle` enum('FROM_SUPPLIER','RETURN_FROM_SALE') NOT NULL,
  `created_at` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP,
  PRIMARY KEY (`id`),
  KEY `inventory_id` (`inventory_id`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1 AUTO_INCREMENT=1 ;

-- --------------------------------------------------------

--
-- Table structure for table `personal_information`
--

CREATE TABLE IF NOT EXISTS `personal_information` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `address_id` int(11) DEFAULT NULL,
  `full_name` varchar(200) NOT NULL,
  `dob` date DEFAULT NULL,
  `email` varchar(200) CHARACTER SET utf8 COLLATE utf8_unicode_ci DEFAULT NULL,
  `phone` varchar(200) CHARACTER SET utf8 COLLATE utf8_unicode_ci DEFAULT NULL,
  `created_at` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP,
  PRIMARY KEY (`id`),
  KEY `address_id` (`address_id`)
) ENGINE=InnoDB  DEFAULT CHARSET=latin1 AUTO_INCREMENT=2 ;

--
-- Dumping data for table `personal_information`
--

INSERT INTO `personal_information` (`id`, `address_id`, `full_name`, `dob`, `email`, `phone`, `created_at`) VALUES
  (1, 1, 'Person 1', '2018-04-04', NULL, NULL, '2018-04-20 13:19:58');

-- --------------------------------------------------------

--
-- Table structure for table `product`
--

CREATE TABLE IF NOT EXISTS `product` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `category_id` int(11) DEFAULT NULL,
  `name` varchar(200) CHARACTER SET utf8 COLLATE utf8_unicode_ci DEFAULT NULL,
  `weight` int(11) NOT NULL,
  `weight_unit` enum('KG','GM') DEFAULT NULL,
  `image` varchar(500) DEFAULT NULL,
  `barcode` varchar(200) CHARACTER SET utf8 COLLATE utf8_unicode_ci DEFAULT NULL,
  `total_available_quantity` int(11) NOT NULL,
  `created_at` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  PRIMARY KEY (`id`),
  KEY `category_id` (`category_id`,`barcode`),
  KEY `weight` (`weight`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1 AUTO_INCREMENT=1 ;

-- --------------------------------------------------------

--
-- Table structure for table `reset_password_tokens`
--

CREATE TABLE IF NOT EXISTS `reset_password_tokens` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `auth_credential_id` int(11) NOT NULL,
  `token` varchar(255) NOT NULL,
  `created_at` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  PRIMARY KEY (`id`),
  UNIQUE KEY `auth_credential_id_2` (`auth_credential_id`),
  KEY `admin_id` (`auth_credential_id`),
  KEY `admin_id_2` (`auth_credential_id`),
  KEY `auth_credential_id` (`auth_credential_id`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1 AUTO_INCREMENT=1 ;

-- --------------------------------------------------------

--
-- Table structure for table `shipment`
--

CREATE TABLE IF NOT EXISTS `shipment` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `tracking_id` varchar(100) DEFAULT NULL,
  `supplier_id` int(11) NOT NULL,
  `entry_id` int(11) DEFAULT NULL,
  `cf_cost` int(11) NOT NULL,
  `carrying_cost` int(11) NOT NULL,
  `labor_cost` int(11) NOT NULL,
  `other_cost` int(11) NOT NULL,
  `total_quantity` int(11) NOT NULL,
  `total_product_price` decimal(25,2) NOT NULL,
  `total_cost` decimal(25,2) NOT NULL,
  `total_paid` decimal(25,2) NOT NULL,
  `purchased_by` int(11) DEFAULT NULL,
  `purchased_date` date NOT NULL,
  `created_at` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP,
  PRIMARY KEY (`id`),
  KEY `supplier_id` (`supplier_id`),
  KEY `entry_id` (`entry_id`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1 AUTO_INCREMENT=1 ;

-- --------------------------------------------------------

--
-- Table structure for table `shipment_transaction`
--

CREATE TABLE IF NOT EXISTS `shipment_transaction` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `shipment_id` int(11) DEFAULT NULL,
  `paid_amount` decimal(25,2) NOT NULL,
  `created_by` int(11) NOT NULL,
  `created_at` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1 AUTO_INCREMENT=1 ;

-- --------------------------------------------------------

--
-- Table structure for table `shop_information`
--

CREATE TABLE IF NOT EXISTS `shop_information` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `name` varchar(200) COLLATE utf8_unicode_ci DEFAULT NULL,
  `address` varchar(200) COLLATE utf8_unicode_ci DEFAULT NULL,
  `logo` varchar(200) CHARACTER SET latin1 NOT NULL,
  `email` varchar(200) COLLATE utf8_unicode_ci DEFAULT NULL,
  `phone` varchar(200) COLLATE utf8_unicode_ci DEFAULT NULL,
  `created_at` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_unicode_ci AUTO_INCREMENT=1 ;

-- --------------------------------------------------------

--
-- Table structure for table `supplier`
--

CREATE TABLE IF NOT EXISTS `supplier` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `company_id` int(11) NOT NULL,
  `supplier_id` varchar(200) DEFAULT NULL,
  `created_at` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP,
  PRIMARY KEY (`id`),
  UNIQUE KEY `personal_info_id` (`company_id`),
  UNIQUE KEY `supplier_id` (`supplier_id`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1 AUTO_INCREMENT=1 ;

-- --------------------------------------------------------

--
-- Table structure for table `temp_file`
--

CREATE TABLE IF NOT EXISTS `temp_file` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `token` int(11) NOT NULL,
  `path` text NOT NULL,
  `file_name` varchar(500) NOT NULL,
  `created_date` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  PRIMARY KEY (`id`),
  KEY `token` (`token`)
) ENGINE=InnoDB  DEFAULT CHARSET=latin1 AUTO_INCREMENT=20 ;

--
-- Dumping data for table `temp_file`
--

INSERT INTO `temp_file` (`id`, `token`, `path`, `file_name`, `created_date`) VALUES
  (1, 1000777818, '/home/mi/project-file/pmc/tmp/22041597251246.jpeg', '22041597251246.jpeg', '2018-04-16 10:33:51'),
  (2, 1000178532, '/home/mi/project-file/pmc/tmp/22193915543084.jpeg', '22193915543084.jpeg', '2018-04-16 10:36:23'),
  (3, 1000190006, '/home/mi/project-file/pos/tmp/22256818623673.jpeg', '22256818623673.jpeg', '2018-04-16 10:37:26'),
  (4, 1000435901, '/home/mi/project-file/pos/tmp/22282936970489.jpg', '22282936970489.jpg', '2018-04-16 10:37:52'),
  (5, 1000121185, '/home/mi/project-file/pos/tmp/22585543613975.jpg', '22585543613975.jpg', '2018-04-16 10:42:54'),
  (6, 1000849460, '/home/mi/project-file/pos/tmp/14256678808523.JPG', '14256678808523.JPG', '2018-04-20 09:54:00'),
  (7, 1000802082, '/home/mi/project-file/pos/tmp/14287007458519.JPG', '14287007458519.JPG', '2018-04-20 09:54:30'),
  (8, 1000592484, '/home/mi/project-file/pos/tmp/14538367016252.JPG', '14538367016252.JPG', '2018-04-20 09:58:42'),
  (9, 1000709215, '/home/mi/project-file/pos/tmp/17868672631188.JPG', '17868672631188.JPG', '2018-04-20 10:54:12'),
  (10, 1000664691, '/home/mi/project-file/pos/tmp/21954480378058.JPG', '21954480378058.JPG', '2018-04-20 12:02:18'),
  (11, 1000809138, '/home/mi/project-file/pos/tmp/22226852309307.JPG', '22226852309307.JPG', '2018-04-20 12:06:50'),
  (12, 1000884278, '/home/mi/project-file/pos/tmp/22549630972247.JPG', '22549630972247.JPG', '2018-04-20 12:12:13'),
  (13, 1000040114, '/home/mi/project-file/pos/tmp/22611032070774.JPG', '22611032070774.JPG', '2018-04-20 12:13:14'),
  (14, 1000091472, '/home/mi/project-file/pos/tmp/23393150465367.JPG', '23393150465367.JPG', '2018-04-20 12:26:16'),
  (15, 1000289192, '/home/mi/project-file/pos/tmp/27234275626165.JPG', '27234275626165.JPG', '2018-04-20 13:30:18'),
  (16, 1000547343, '/home/mi/project-file/pos/tmp/27602303432731.JPG', '27602303432731.JPG', '2018-04-20 13:36:26'),
  (17, 1000454183, '/home/mi/project-file/pos/tmp/25136638767649.png', '25136638767649.png', '2018-04-23 11:01:20'),
  (18, 1000800958, '/home/mi/project-file/pos/tmp/8395239381726.png', '8395239381726.png', '2018-04-24 06:15:23'),
  (19, 1000388290, '/home/mi/project-file/pos/tmp/3976418198347.png', '3976418198347.png', '2018-04-24 11:35:08');

-- --------------------------------------------------------

--
-- Table structure for table `wholesaler`
--

CREATE TABLE IF NOT EXISTS `wholesaler` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `company_id` int(11) NOT NULL,
  `wholesaler_id` varchar(200) NOT NULL,
  `created_at` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP,
  PRIMARY KEY (`id`),
  UNIQUE KEY `personal_info_id` (`company_id`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1 AUTO_INCREMENT=1 ;

--
-- Constraints for dumped tables
--

--
-- Constraints for table `acc_entries`
--
ALTER TABLE `acc_entries`
  ADD CONSTRAINT `FKgjbihsd3i7rquwws86fcye0jl` FOREIGN KEY (`entry_type`) REFERENCES `acc_entry_types` (`id`),
  ADD CONSTRAINT `FKqudvjutt4g4pwshohckj2y7y6` FOREIGN KEY (`created_by`) REFERENCES `employee` (`id`);

--
-- Constraints for table `acc_entry_items`
--
ALTER TABLE `acc_entry_items`
  ADD CONSTRAINT `FKsktxpw9rj3098v6icvo480t87` FOREIGN KEY (`created_by`) REFERENCES `employee` (`id`);

--
-- Constraints for table `acc_groups`
--
ALTER TABLE `acc_groups`
  ADD CONSTRAINT `FK8j2urejsci4dtrtkj1i5agu8r` FOREIGN KEY (`parent_id`) REFERENCES `acc_groups` (`id`);

--
-- Constraints for table `acc_ledgers`
--
ALTER TABLE `acc_ledgers`
  ADD CONSTRAINT `FK4x9i7aii90w763vd8n2cjx23l` FOREIGN KEY (`company_id`) REFERENCES `company` (`id`),
  ADD CONSTRAINT `FKf1vma88deqda982c82fnv316r` FOREIGN KEY (`group_id`) REFERENCES `acc_groups` (`id`),
  ADD CONSTRAINT `FKle7hns1e51sar2k697qi1orpp` FOREIGN KEY (`personal_info_id`) REFERENCES `personal_information` (`id`),
  ADD CONSTRAINT `ledgers_fk_check_group_id` FOREIGN KEY (`group_id`) REFERENCES `acc_groups` (`id`);

--
-- Constraints for table `acess_role`
--
ALTER TABLE `acess_role`
  ADD CONSTRAINT `FKu5gxdleqtq6tckr8df8durll` FOREIGN KEY (`auth_credential_id`) REFERENCES `auth_credential` (`id`);

--
-- Constraints for table `auth_credential`
--
ALTER TABLE `auth_credential`
  ADD CONSTRAINT `FK204315vkkjatxsc2egk1ifmlf` FOREIGN KEY (`personal_info_id`) REFERENCES `personal_information` (`id`);

--
-- Constraints for table `company`
--
ALTER TABLE `company`
  ADD CONSTRAINT `FKc0m5yof4h6qpn69i2l6mxl51v` FOREIGN KEY (`address_id`) REFERENCES `address` (`id`);

--
-- Constraints for table `company_role`
--
ALTER TABLE `company_role`
  ADD CONSTRAINT `FK8q319mjc9bhewhjifwifel4p1` FOREIGN KEY (`personal_info_id`) REFERENCES `personal_information` (`id`);

--
-- Constraints for table `employee`
--
ALTER TABLE `employee`
  ADD CONSTRAINT `FKmdcbaythf5rppp1014vitdkfy` FOREIGN KEY (`personal_info_id`) REFERENCES `personal_information` (`id`);

--
-- Constraints for table `personal_information`
--
ALTER TABLE `personal_information`
  ADD CONSTRAINT `FKe3ip0abhdip6wyyg4clupqqjn` FOREIGN KEY (`address_id`) REFERENCES `address` (`id`);

--
-- Constraints for table `reset_password_tokens`
--
ALTER TABLE `reset_password_tokens`
  ADD CONSTRAINT `FKiccgrrjc4a92ycy0xa4sh7kt6` FOREIGN KEY (`auth_credential_id`) REFERENCES `auth_credential` (`id`);

--
-- Constraints for table `supplier`
--
ALTER TABLE `supplier`
  ADD CONSTRAINT `FKp23ntd8c3i2egts0c3ap2py58` FOREIGN KEY (`company_id`) REFERENCES `company` (`id`),
  ADD CONSTRAINT `FKt2v335ylgm3mw2bnmhtvaw5ks` FOREIGN KEY (`company_id`) REFERENCES `company` (`id`);

--
-- Constraints for table `wholesaler`
--
ALTER TABLE `wholesaler`
  ADD CONSTRAINT `FKd20x0cu4drcgrxl8fhpaygu0n` FOREIGN KEY (`company_id`) REFERENCES `company` (`id`);

/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;