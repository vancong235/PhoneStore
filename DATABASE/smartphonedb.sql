-- phpMyAdmin SQL Dump
-- version 5.2.1
-- https://www.phpmyadmin.net/
--
-- Host: 127.0.0.1
-- Generation Time: Nov 15, 2023 at 08:05 PM
-- Server version: 10.4.28-MariaDB
-- PHP Version: 8.0.28

SET SQL_MODE = "NO_AUTO_VALUE_ON_ZERO";
START TRANSACTION;
SET time_zone = "+00:00";


/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8mb4 */;

--
-- Database: `smartphonedb`
--

-- --------------------------------------------------------

--
-- Table structure for table `category`
--

CREATE TABLE `category` (
  `id` int(11) NOT NULL,
  `name` varchar(50) NOT NULL,
  `supplierId` int(11) NOT NULL,
  `status` tinyint(1) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Dumping data for table `category`
--

INSERT INTO `category` (`id`, `name`, `supplierId`, `status`) VALUES
(30, 'IPHONE', 1, 1),
(31, 'SAMSUNG', 2, 1);

-- --------------------------------------------------------

--
-- Table structure for table `coupon`
--

CREATE TABLE `coupon` (
  `id` int(11) NOT NULL,
  `code` varchar(50) NOT NULL,
  `count` int(11) NOT NULL,
  `promotion` varchar(50) NOT NULL,
  `description` varchar(50) NOT NULL,
  `status` tinyint(1) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Dumping data for table `coupon`
--

INSERT INTO `coupon` (`id`, `code`, `count`, `promotion`, `description`, `status`) VALUES
(1, 'CM3', 40, '3', 'Khuyến mãi 3%', 1),
(2, 'CM4', 30, '10', 'Khuyến mãi 10%', 1),
(3, 'CM5', 5, '5', 'Khuyến mãi 5%', 1),
(4, 'CM6', 6, '6', 'Khuyến mãi 6%', 1);

-- --------------------------------------------------------

--
-- Table structure for table `customer`
--

CREATE TABLE `customer` (
  `id` int(11) NOT NULL,
  `name` varchar(255) DEFAULT NULL,
  `phoneNumber` varchar(20) DEFAULT NULL,
  `address` varchar(255) DEFAULT NULL,
  `email` varchar(255) DEFAULT NULL,
  `statusCustomer` tinyint(1) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Dumping data for table `customer`
--

INSERT INTO `customer` (`id`, `name`, `phoneNumber`, `address`, `email`, `statusCustomer`) VALUES
(1, 'Mai Ngoc Canh', '0357349402', 'Ben Tre', 'maicanh2002@gmail.com', 1),
(2, 'Nguyen Chi Cong', '0386431800', 'Ca Mau', 'congkhpro291002@gmail.com', 1),
(10, 'Nguyen Van Tien Dung', '0785678340', 'Binh Chanh', 'dung31072002@gmail.com', 1),
(11, 'Lai Quang Vinh', '0912111222', 'Long An', 'vinhquanglai123@gmail.com', 1),
(12, 'Pham Dang Khoa', '0851231231', 'Dak Lak', 'phamdangkhoa123@gmail.com', 1),
(13, 'Nguyen Dinh Thinh', '0912222111', 'Dong Nai', 'dinhthinh2204@gmail.com', 1),
(20, 'nGuyen Cong', '0948399484', 'Ca Mau', 'ccc12312@gmail.com', 1);

-- --------------------------------------------------------

--
-- Table structure for table `employee`
--

CREATE TABLE `employee` (
  `id` int(11) NOT NULL,
  `name` varchar(50) NOT NULL,
  `phoneNumber` varchar(10) NOT NULL,
  `address` varchar(100) NOT NULL,
  `email` varchar(50) NOT NULL,
  `salary` double NOT NULL,
  `status` tinyint(1) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Dumping data for table `employee`
--

INSERT INTO `employee` (`id`, `name`, `phoneNumber`, `address`, `email`, `salary`, `status`) VALUES
(1, 'Nguyen Chi Cong', '0129293493', 'Ca Mau', 'congkhpro291002@gmail.com', 2500000, 0),
(2, 'Mai Ngoc Canh', '0357349402', 'Ben Tre', 'canhmai2002@gmail.com', 10000000, 1),
(3, 'Nguyen Van Tien Dung', '0836112350', 'Ha Noi', 'dung31072002@gmail.com', 20000000, 1),
(4, 'Lai Quang Vinh', '0841231112', 'Long An', 'vinhvinhlai123@gmail.com', 3500000, 1),
(5, 'Pham Dang Khoa', '0613123122', 'Dak Lak', 'khoaphamdang123@gmail.com', 2000000, 0),
(6, 'Vo Van Huan', '0123123124', 'Vung Tau', 'huanhuan2304@gmail.com', 12300000, 1),
(7, 'Pham Dang Khoa', '0836431800', 'Dak Lak', 'dangkhoa@gmail.com', 1232213212, 1);

-- --------------------------------------------------------

--
-- Table structure for table `orderdetail`
--

CREATE TABLE `orderdetail` (
  `id` int(11) NOT NULL,
  `orderId` int(11) DEFAULT NULL,
  `productId` int(11) DEFAULT NULL,
  `employeeId` int(11) NOT NULL,
  `quantity` int(11) DEFAULT NULL,
  `unitPrice` double DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Dumping data for table `orderdetail`
--

INSERT INTO `orderdetail` (`id`, `orderId`, `productId`, `employeeId`, `quantity`, `unitPrice`) VALUES
(17, 4, 131, 4, 10, 20000),
(18, 5, 132, 2, 4, 30000),
(19, 4, 131, 3, 2, 230000),
(20, 4, 134, 6, 10, 20000),
(21, 5, 135, 7, 5, 12200000);

-- --------------------------------------------------------

--
-- Table structure for table `orders`
--

CREATE TABLE `orders` (
  `id` int(11) NOT NULL,
  `customerId` int(11) NOT NULL,
  `orderDate` date NOT NULL,
  `Status` int(11) NOT NULL,
  `couponId` int(11) NOT NULL,
  `paymentMethod` varchar(50) NOT NULL,
  `total` double NOT NULL,
  `ShipName` varchar(50) NOT NULL,
  `ShipAddress` varchar(200) NOT NULL,
  `ShipPhoneNumber` varchar(10) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Dumping data for table `orders`
--

INSERT INTO `orders` (`id`, `customerId`, `orderDate`, `Status`, `couponId`, `paymentMethod`, `total`, `ShipName`, `ShipAddress`, `ShipPhoneNumber`) VALUES
(4, 10, '2023-10-05', 0, 1, 'COD', 1500000, 'Ngoc So', 'Gia Lai', '0129294393'),
(5, 12, '2023-11-10', 0, 2, 'COD', 1500000, 'Khac Tiep', 'Binh Tan Tri', '0854311242'),
(6, 2, '2023-07-03', 0, 2, 'COD', 230000, 'Ngoc Canh', 'Ca Mau', '0386431800');

-- --------------------------------------------------------

--
-- Table structure for table `permissions`
--

CREATE TABLE `permissions` (
  `id` int(11) NOT NULL,
  `name` varchar(50) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Dumping data for table `permissions`
--

INSERT INTO `permissions` (`id`, `name`) VALUES
(1, 'create_product'),
(2, 'delete_product'),
(3, 'update_product'),
(4, 'create_account'),
(5, 'update_account'),
(6, 'delete_account'),
(7, 'view_account'),
(8, 'view_product'),
(9, 'view_supplier'),
(10, 'create_supplier'),
(11, 'update_supplier'),
(12, 'view_supplier');

-- --------------------------------------------------------

--
-- Table structure for table `product`
--

CREATE TABLE `product` (
  `id` int(11) NOT NULL,
  `name` varchar(50) NOT NULL,
  `price` double(50,0) NOT NULL,
  `Image` varchar(100) NOT NULL,
  `description` varchar(100) NOT NULL,
  `categoryId` int(11) NOT NULL,
  `quantity` int(11) NOT NULL,
  `color` varchar(50) NOT NULL,
  `status` tinyint(4) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Dumping data for table `product`
--

INSERT INTO `product` (`id`, `name`, `price`, `Image`, `description`, `categoryId`, `quantity`, `color`, `status`) VALUES
(131, 'IPHONE', 20000000, 'IPhone_1.jpeg', 'xanh đẹp', 30, 2, 'xa', 1),
(132, 'IPHONE2', 2, 'IPhone_2.jpeg', '2', 30, 2, 'xanh', 1),
(133, 'IPHONE 15 PROMAX', 18900000, 'IPhone_3.jpeg', 'Đẹp Xanh', 30, 2, 'Trắng', 1),
(134, 'SAM SUNG GALAXY ULTRA S23', 25400000, 'SamSung_1.jpeg', 'Đen Đẹp', 31, 3, 'Đen', 1),
(135, 'SAM SUNG GALAXY S21', 9490000, 'SamSung_3.jpeg', 'Đen Đẹp Ngầu', 31, 3, 'Đen', 1),
(136, 'IPHONE 13 PROMAX', 35000000, 'IPhone_4.jpeg', 'Đen Đep Ngầu', 30, 3, 'Đen', 1),
(137, 'IPHONE 14', 25000000, 'IPhone_2.jpeg', 'Xanh Đen', 30, 5, 'xanh', 1);

-- --------------------------------------------------------

--
-- Table structure for table `rolepermissions`
--

CREATE TABLE `rolepermissions` (
  `id` int(11) NOT NULL,
  `roleid` int(11) NOT NULL,
  `permissionsid` int(11) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Dumping data for table `rolepermissions`
--

INSERT INTO `rolepermissions` (`id`, `roleid`, `permissionsid`) VALUES
(1, 1, 1),
(2, 1, 8),
(3, 1, 3),
(4, 1, 2),
(5, 2, 8),
(6, 3, 4),
(7, 3, 1),
(8, 3, 10),
(9, 3, 6),
(10, 3, 2),
(11, 3, 5),
(12, 3, 5),
(13, 3, 3);

-- --------------------------------------------------------

--
-- Table structure for table `roles`
--

CREATE TABLE `roles` (
  `id` int(11) NOT NULL,
  `name` varchar(50) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Dumping data for table `roles`
--

INSERT INTO `roles` (`id`, `name`) VALUES
(1, 'Seller'),
(2, 'Customer'),
(3, 'Admin');

-- --------------------------------------------------------

--
-- Table structure for table `supplier`
--

CREATE TABLE `supplier` (
  `Id` int(11) NOT NULL,
  `Name` varchar(50) NOT NULL,
  `PhoneNumber` varchar(10) NOT NULL,
  `Address` varchar(100) NOT NULL,
  `status` tinyint(4) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Dumping data for table `supplier`
--

INSERT INTO `supplier` (`Id`, `Name`, `PhoneNumber`, `Address`, `status`) VALUES
(1, 'Apple', '0816089161', 'Ca Mau', 1),
(2, 'Samsung', '0765433120', 'Ha Noi', 1);

-- --------------------------------------------------------

--
-- Table structure for table `users`
--

CREATE TABLE `users` (
  `id` int(11) NOT NULL,
  `fullName` varchar(50) NOT NULL,
  `userName` varchar(50) NOT NULL,
  `passWord` varchar(50) NOT NULL,
  `roleId` int(11) NOT NULL,
  `status` int(11) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Dumping data for table `users`
--

INSERT INTO `users` (`id`, `fullName`, `userName`, `passWord`, `roleId`, `status`) VALUES
(1, 'Nguyen Chi Cong', 'ChiCong', '123', 1, 1),
(2, 'Mai Ngoc Canh', 'CanhMai', '1234', 2, 1),
(3, 'Nguyen Van Tien Dung', 'TienDung', '1234', 3, 1),
(8, 'Chi Cong Vo Tu', '2', 'Chi Cong Vo Tu', 1, 1);

-- --------------------------------------------------------

--
-- Table structure for table `user_role`
--

CREATE TABLE `user_role` (
  `user_id` int(11) DEFAULT NULL,
  `role_id` int(11) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Dumping data for table `user_role`
--

INSERT INTO `user_role` (`user_id`, `role_id`) VALUES
(1, 3),
(2, 1),
(3, 2);

--
-- Indexes for dumped tables
--

--
-- Indexes for table `category`
--
ALTER TABLE `category`
  ADD PRIMARY KEY (`id`),
  ADD KEY `s` (`supplierId`);

--
-- Indexes for table `coupon`
--
ALTER TABLE `coupon`
  ADD PRIMARY KEY (`id`);

--
-- Indexes for table `customer`
--
ALTER TABLE `customer`
  ADD PRIMARY KEY (`id`);

--
-- Indexes for table `employee`
--
ALTER TABLE `employee`
  ADD PRIMARY KEY (`id`);

--
-- Indexes for table `orderdetail`
--
ALTER TABLE `orderdetail`
  ADD PRIMARY KEY (`id`),
  ADD KEY `order_fk` (`orderId`),
  ADD KEY `product_fk` (`productId`),
  ADD KEY `employee_fk` (`employeeId`);

--
-- Indexes for table `orders`
--
ALTER TABLE `orders`
  ADD PRIMARY KEY (`id`),
  ADD KEY `customer_fk` (`customerId`),
  ADD KEY `coupon_fk` (`couponId`);

--
-- Indexes for table `permissions`
--
ALTER TABLE `permissions`
  ADD PRIMARY KEY (`id`);

--
-- Indexes for table `product`
--
ALTER TABLE `product`
  ADD PRIMARY KEY (`id`),
  ADD KEY `category_fk` (`categoryId`);

--
-- Indexes for table `rolepermissions`
--
ALTER TABLE `rolepermissions`
  ADD PRIMARY KEY (`id`),
  ADD KEY `permission_fk` (`permissionsid`),
  ADD KEY `role_FK` (`roleid`);

--
-- Indexes for table `roles`
--
ALTER TABLE `roles`
  ADD PRIMARY KEY (`id`);

--
-- Indexes for table `supplier`
--
ALTER TABLE `supplier`
  ADD PRIMARY KEY (`Id`);

--
-- Indexes for table `users`
--
ALTER TABLE `users`
  ADD PRIMARY KEY (`id`),
  ADD KEY `role_pk` (`roleId`);

--
-- Indexes for table `user_role`
--
ALTER TABLE `user_role`
  ADD KEY `user_id` (`user_id`),
  ADD KEY `role_id` (`role_id`);

--
-- AUTO_INCREMENT for dumped tables
--

--
-- AUTO_INCREMENT for table `category`
--
ALTER TABLE `category`
  MODIFY `id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=32;

--
-- AUTO_INCREMENT for table `coupon`
--
ALTER TABLE `coupon`
  MODIFY `id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=6;

--
-- AUTO_INCREMENT for table `customer`
--
ALTER TABLE `customer`
  MODIFY `id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=21;

--
-- AUTO_INCREMENT for table `employee`
--
ALTER TABLE `employee`
  MODIFY `id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=8;

--
-- AUTO_INCREMENT for table `orderdetail`
--
ALTER TABLE `orderdetail`
  MODIFY `id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=22;

--
-- AUTO_INCREMENT for table `orders`
--
ALTER TABLE `orders`
  MODIFY `id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=7;

--
-- AUTO_INCREMENT for table `permissions`
--
ALTER TABLE `permissions`
  MODIFY `id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=13;

--
-- AUTO_INCREMENT for table `product`
--
ALTER TABLE `product`
  MODIFY `id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=138;

--
-- AUTO_INCREMENT for table `rolepermissions`
--
ALTER TABLE `rolepermissions`
  MODIFY `id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=14;

--
-- AUTO_INCREMENT for table `roles`
--
ALTER TABLE `roles`
  MODIFY `id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=4;

--
-- AUTO_INCREMENT for table `supplier`
--
ALTER TABLE `supplier`
  MODIFY `Id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=4;

--
-- AUTO_INCREMENT for table `users`
--
ALTER TABLE `users`
  MODIFY `id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=9;

--
-- Constraints for dumped tables
--

--
-- Constraints for table `category`
--
ALTER TABLE `category`
  ADD CONSTRAINT `s` FOREIGN KEY (`supplierId`) REFERENCES `supplier` (`Id`);

--
-- Constraints for table `orderdetail`
--
ALTER TABLE `orderdetail`
  ADD CONSTRAINT `employee_fk` FOREIGN KEY (`employeeId`) REFERENCES `employee` (`id`),
  ADD CONSTRAINT `order_fk` FOREIGN KEY (`orderId`) REFERENCES `orders` (`id`),
  ADD CONSTRAINT `product_fk` FOREIGN KEY (`productId`) REFERENCES `product` (`id`);

--
-- Constraints for table `orders`
--
ALTER TABLE `orders`
  ADD CONSTRAINT `coupon_fk` FOREIGN KEY (`couponId`) REFERENCES `coupon` (`id`),
  ADD CONSTRAINT `customer_fk` FOREIGN KEY (`customerId`) REFERENCES `customer` (`id`);

--
-- Constraints for table `product`
--
ALTER TABLE `product`
  ADD CONSTRAINT `category_fk` FOREIGN KEY (`categoryId`) REFERENCES `category` (`id`);

--
-- Constraints for table `rolepermissions`
--
ALTER TABLE `rolepermissions`
  ADD CONSTRAINT `permission_fk` FOREIGN KEY (`permissionsid`) REFERENCES `permissions` (`id`),
  ADD CONSTRAINT `role_FK` FOREIGN KEY (`roleid`) REFERENCES `roles` (`id`),
  ADD CONSTRAINT `rolepermissions_ibfk_2` FOREIGN KEY (`permissionsid`) REFERENCES `permissions` (`id`);

--
-- Constraints for table `users`
--
ALTER TABLE `users`
  ADD CONSTRAINT `role_pk` FOREIGN KEY (`roleId`) REFERENCES `roles` (`id`);

--
-- Constraints for table `user_role`
--
ALTER TABLE `user_role`
  ADD CONSTRAINT `user_role_ibfk_1` FOREIGN KEY (`user_id`) REFERENCES `users` (`id`),
  ADD CONSTRAINT `user_role_ibfk_2` FOREIGN KEY (`role_id`) REFERENCES `roles` (`id`);
COMMIT;

/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
