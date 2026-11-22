-- phpMyAdmin SQL Dump
-- version 5.2.1
-- https://www.phpmyadmin.net/
--
-- Host: 127.0.0.1
-- Generation Time: Jan 03, 2026 at 02:18 PM
-- Server version: 10.4.32-MariaDB
-- PHP Version: 8.2.12

SET SQL_MODE = "NO_AUTO_VALUE_ON_ZERO";
START TRANSACTION;
SET time_zone = "+00:00";


/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8mb4 */;

--
-- Database: `uas3`
--

-- --------------------------------------------------------

--
-- Table structure for table `adjust_stock`
--

CREATE TABLE `adjust_stock` (
  `id_adjust` int(11) NOT NULL,
  `id_produk` int(11) NOT NULL,
  `qty` int(11) NOT NULL,
  `keterangan` varchar(255) NOT NULL,
  `tanggal` datetime NOT NULL DEFAULT current_timestamp(),
  `created_by` int(11) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Dumping data for table `adjust_stock`
--

INSERT INTO `adjust_stock` (`id_adjust`, `id_produk`, `qty`, `keterangan`, `tanggal`, `created_by`) VALUES
(1, 3, 1, 'return item', '2025-12-28 19:00:19', 5),
(2, 3, -1, 'barang rusak', '2025-12-28 19:00:34', 5),
(3, 3, 1, 'barang return', '2025-12-28 19:00:54', 5),
(4, 3, -1, 'barang rusak', '2025-12-28 19:08:09', 5);

-- --------------------------------------------------------

--
-- Table structure for table `delivery`
--

CREATE TABLE `delivery` (
  `id_delivery` int(11) NOT NULL,
  `id_outbound` int(11) NOT NULL,
  `nama_penerima` varchar(100) NOT NULL,
  `alamat` text NOT NULL,
  `no_hp` varchar(20) NOT NULL,
  `surat_jalan` varchar(50) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Dumping data for table `delivery`
--

INSERT INTO `delivery` (`id_delivery`, `id_outbound`, `nama_penerima`, `alamat`, `no_hp`, `surat_jalan`) VALUES
(1, 8, 'jokowi', 'solo', '11111111', '1'),
(2, 9, 'jokowi', 'solo', '087771', 'sj00'),
(3, 11, 'jokowi', 'jawa', '1111111', 'SJ-uu-1945'),
(4, 12, 'joko', 'solo', '09998888', 'SJ-5020'),
(5, 13, 'udin', 'duar', '1234567', 'SJ-9090'),
(6, 14, 'kkkk', '111111', '44444', 'SJ-100');

-- --------------------------------------------------------

--
-- Table structure for table `inbound`
--

CREATE TABLE `inbound` (
  `id_inbound` int(11) NOT NULL,
  `envelope_code` varchar(30) NOT NULL,
  `tanggal` datetime NOT NULL,
  `created_by` int(11) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Dumping data for table `inbound`
--

INSERT INTO `inbound` (`id_inbound`, `envelope_code`, `tanggal`, `created_by`) VALUES
(1, '1', '2025-12-05 00:00:00', 1),
(2, '1', '2025-12-04 00:00:00', 5),
(3, '1', '2025-12-04 00:00:00', 5);

-- --------------------------------------------------------

--
-- Table structure for table `inbound_detail`
--

CREATE TABLE `inbound_detail` (
  `id_detail` int(11) NOT NULL,
  `id_inbound` int(11) NOT NULL,
  `id_produk` int(11) NOT NULL,
  `qty` int(11) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Dumping data for table `inbound_detail`
--

INSERT INTO `inbound_detail` (`id_detail`, `id_inbound`, `id_produk`, `qty`) VALUES
(1, 1, 3, 2),
(2, 2, 3, 2),
(3, 3, 9, 1);

-- --------------------------------------------------------

--
-- Table structure for table `outbound`
--

CREATE TABLE `outbound` (
  `id_outbound` int(11) NOT NULL,
  `envelope_code` varchar(50) NOT NULL,
  `tanggal` datetime NOT NULL,
  `created_by` int(11) NOT NULL,
  `jenis_outbound` enum('DELIVERY','PICKUP') NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Dumping data for table `outbound`
--

INSERT INTO `outbound` (`id_outbound`, `envelope_code`, `tanggal`, `created_by`, `jenis_outbound`) VALUES
(8, '1', '2025-12-05 00:00:00', 5, 'DELIVERY'),
(9, '1', '2025-12-05 00:00:00', 5, 'DELIVERY'),
(10, 'ENV-OB-MK-251231', '2025-12-31 00:00:00', 5, 'PICKUP'),
(11, 'ENV-OB-455', '2026-01-02 00:00:00', 5, 'DELIVERY'),
(12, 'ENV-OB-1250', '2026-01-12 00:00:00', 5, 'DELIVERY'),
(13, 'ENV-90', '2026-01-10 00:00:00', 5, 'DELIVERY'),
(14, 'ENV-100', '2026-01-24 00:00:00', 5, 'DELIVERY'),
(16, '122', '2026-01-18 00:00:00', 5, 'PICKUP'),
(17, 'ENV-1', '2026-01-03 00:00:00', 1, 'PICKUP'),
(18, '8', '2026-01-06 00:00:00', 5, 'PICKUP'),
(19, '9', '2026-01-03 00:00:00', 5, 'PICKUP'),
(20, '7', '2026-01-10 00:00:00', 5, 'PICKUP');

-- --------------------------------------------------------

--
-- Table structure for table `outbound_detail`
--

CREATE TABLE `outbound_detail` (
  `id_outbound_detail` int(11) NOT NULL,
  `id_outbound` int(11) NOT NULL,
  `id_produk` int(11) NOT NULL,
  `qty` int(11) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Dumping data for table `outbound_detail`
--

INSERT INTO `outbound_detail` (`id_outbound_detail`, `id_outbound`, `id_produk`, `qty`) VALUES
(6, 16, 3, 1),
(7, 16, 4, 1),
(8, 17, 4, 1),
(9, 18, 3, 1),
(10, 19, 3, 1),
(11, 20, 3, 1);

-- --------------------------------------------------------

--
-- Table structure for table `produk`
--

CREATE TABLE `produk` (
  `id_produk` int(11) NOT NULL,
  `kode_produk` varchar(20) DEFAULT NULL,
  `nama_produk` varchar(100) DEFAULT NULL,
  `jenis` varchar(50) DEFAULT NULL,
  `satuan` varchar(20) DEFAULT NULL,
  `harga` decimal(12,2) DEFAULT NULL,
  `status` enum('aktif','nonaktif') DEFAULT NULL,
  `stok` int(11) DEFAULT 0
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Dumping data for table `produk`
--

INSERT INTO `produk` (`id_produk`, `kode_produk`, `nama_produk`, `jenis`, `satuan`, `harga`, `status`, `stok`) VALUES
(3, 'HMB - 3004', 'Blender Miyako', 'Elektronik', 'Unit', 450000.00, 'aktif', 4),
(4, 'TUF-5423', 'TV XIAOMI', 'Elektronik', 'Unit', 2000000.00, 'aktif', 1),
(8, '1', '2', 'Lainnya', 'Pcs', 3.00, 'aktif', 0),
(9, '1', '1', 'Elektronik', 'Pcs', 1.00, 'nonaktif', 1);

-- --------------------------------------------------------

--
-- Table structure for table `user`
--

CREATE TABLE `user` (
  `id` int(11) NOT NULL,
  `username` varchar(90) NOT NULL,
  `password` varchar(50) NOT NULL,
  `created_at` timestamp NOT NULL DEFAULT current_timestamp()
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Dumping data for table `user`
--

INSERT INTO `user` (`id`, `username`, `password`, `created_at`) VALUES
(1, 'Login', '123', '2025-12-22 07:29:51'),
(3, '111', '123', '2025-12-22 14:39:43'),
(5, '1', '1', '2025-12-22 14:47:42');

--
-- Indexes for dumped tables
--

--
-- Indexes for table `adjust_stock`
--
ALTER TABLE `adjust_stock`
  ADD PRIMARY KEY (`id_adjust`),
  ADD KEY `fk_adjust_produk` (`id_produk`);

--
-- Indexes for table `delivery`
--
ALTER TABLE `delivery`
  ADD PRIMARY KEY (`id_delivery`),
  ADD KEY `id_outbound` (`id_outbound`);

--
-- Indexes for table `inbound`
--
ALTER TABLE `inbound`
  ADD PRIMARY KEY (`id_inbound`),
  ADD KEY `fk_inbound_user` (`created_by`);

--
-- Indexes for table `inbound_detail`
--
ALTER TABLE `inbound_detail`
  ADD PRIMARY KEY (`id_detail`),
  ADD KEY `fk_inbound` (`id_inbound`),
  ADD KEY `fk_produk` (`id_produk`);

--
-- Indexes for table `outbound`
--
ALTER TABLE `outbound`
  ADD PRIMARY KEY (`id_outbound`),
  ADD KEY `created_by` (`created_by`);

--
-- Indexes for table `outbound_detail`
--
ALTER TABLE `outbound_detail`
  ADD PRIMARY KEY (`id_outbound_detail`),
  ADD KEY `id_outbound` (`id_outbound`),
  ADD KEY `id_produk` (`id_produk`);

--
-- Indexes for table `produk`
--
ALTER TABLE `produk`
  ADD PRIMARY KEY (`id_produk`);

--
-- Indexes for table `user`
--
ALTER TABLE `user`
  ADD PRIMARY KEY (`id`);

--
-- AUTO_INCREMENT for dumped tables
--

--
-- AUTO_INCREMENT for table `adjust_stock`
--
ALTER TABLE `adjust_stock`
  MODIFY `id_adjust` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=6;

--
-- AUTO_INCREMENT for table `delivery`
--
ALTER TABLE `delivery`
  MODIFY `id_delivery` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=8;

--
-- AUTO_INCREMENT for table `inbound`
--
ALTER TABLE `inbound`
  MODIFY `id_inbound` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=5;

--
-- AUTO_INCREMENT for table `inbound_detail`
--
ALTER TABLE `inbound_detail`
  MODIFY `id_detail` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=6;

--
-- AUTO_INCREMENT for table `outbound`
--
ALTER TABLE `outbound`
  MODIFY `id_outbound` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=22;

--
-- AUTO_INCREMENT for table `outbound_detail`
--
ALTER TABLE `outbound_detail`
  MODIFY `id_outbound_detail` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=13;

--
-- AUTO_INCREMENT for table `produk`
--
ALTER TABLE `produk`
  MODIFY `id_produk` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=10;

--
-- AUTO_INCREMENT for table `user`
--
ALTER TABLE `user`
  MODIFY `id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=6;

--
-- Constraints for dumped tables
--

--
-- Constraints for table `adjust_stock`
--
ALTER TABLE `adjust_stock`
  ADD CONSTRAINT `fk_adjust_produk` FOREIGN KEY (`id_produk`) REFERENCES `produk` (`id_produk`) ON UPDATE CASCADE;

--
-- Constraints for table `delivery`
--
ALTER TABLE `delivery`
  ADD CONSTRAINT `delivery_ibfk_1` FOREIGN KEY (`id_outbound`) REFERENCES `outbound` (`id_outbound`);

--
-- Constraints for table `inbound`
--
ALTER TABLE `inbound`
  ADD CONSTRAINT `fk_inbound_user` FOREIGN KEY (`created_by`) REFERENCES `user` (`id`);

--
-- Constraints for table `inbound_detail`
--
ALTER TABLE `inbound_detail`
  ADD CONSTRAINT `fk_inbound` FOREIGN KEY (`id_inbound`) REFERENCES `inbound` (`id_inbound`) ON DELETE CASCADE,
  ADD CONSTRAINT `fk_produk` FOREIGN KEY (`id_produk`) REFERENCES `produk` (`id_produk`);

--
-- Constraints for table `outbound`
--
ALTER TABLE `outbound`
  ADD CONSTRAINT `outbound_ibfk_1` FOREIGN KEY (`created_by`) REFERENCES `user` (`id`);

--
-- Constraints for table `outbound_detail`
--
ALTER TABLE `outbound_detail`
  ADD CONSTRAINT `outbound_detail_ibfk_1` FOREIGN KEY (`id_outbound`) REFERENCES `outbound` (`id_outbound`),
  ADD CONSTRAINT `outbound_detail_ibfk_2` FOREIGN KEY (`id_produk`) REFERENCES `produk` (`id_produk`);
COMMIT;

/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
