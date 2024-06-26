-- phpMyAdmin SQL Dump
-- version 5.2.1
-- https://www.phpmyadmin.net/
--
-- Máy chủ: 127.0.0.1:3307
-- Thời gian đã tạo: Th6 15, 2024 lúc 03:16 AM
-- Phiên bản máy phục vụ: 10.4.32-MariaDB
-- Phiên bản PHP: 8.2.12

SET SQL_MODE = "NO_AUTO_VALUE_ON_ZERO";
START TRANSACTION;
SET time_zone = "+00:00";


/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8mb4 */;

--
-- Cơ sở dữ liệu: `petshop`
--

-- --------------------------------------------------------

--
-- Cấu trúc bảng cho bảng `nhanvien`
--

CREATE TABLE `nhanvien` (
  `ID` int(11) NOT NULL COMMENT 'AUTO_INCREMENT',
  `TenNV` varchar(150) NOT NULL,
  `GioiTinh` varchar(10) NOT NULL,
  `CCCD` int(35) NOT NULL,
  `NgaySinh` date NOT NULL,
  `phone` int(11) NOT NULL,
  `diachi` varchar(200) NOT NULL,
  `quyen` varchar(30) NOT NULL,
  `Luong` int(150) NOT NULL,
  `TongLuong` int(150) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Đang đổ dữ liệu cho bảng `nhanvien`
--

INSERT INTO `nhanvien` (`ID`, `TenNV`, `GioiTinh`, `CCCD`, `NgaySinh`, `phone`, `diachi`, `quyen`, `Luong`, `TongLuong`) VALUES
(0, 'Trần Hải Đăng', 'Nam', 23729294, '2014-06-26', 976555341, 'QuangNam', 'nhanvien', 1500000, 0),
(1, 'Nguyễn Mono', 'Nam', 98765432, '2014-06-04', 123456789, 'Đà Nẵng', 'nhanvien', 1200000, 0),
(2, 'Trần LyLy', 'Nữ', 23456789, '2014-07-11', 987654321, 'Đà Nẵng', 'nhanvien', 1300000, 0),
(3, 'Đỗ Quốc Huy', 'Nam', 76579697, '2005-06-03', 767333567, 'Quảng Bình', 'nhanvien', 1200000, 0),
(4, 'Nguyễn Thị Lành', 'Nữ', 28736423, '2002-06-27', 989679111, 'Quảng Nam', 'nhanvien', 1000000, 0);

-- --------------------------------------------------------

--
-- Cấu trúc bảng cho bảng `products`
--

CREATE TABLE `products` (
  `IDsp` int(11) NOT NULL,
  `Ten` varchar(100) NOT NULL,
  `Loai` varchar(50) NOT NULL,
  `Gia` int(50) NOT NULL,
  `SoLuong` int(50) NOT NULL,
  `DaNhap` int(50) NOT NULL,
  `NgayNhap` date NOT NULL,
  `DaXuat` int(50) NOT NULL,
  `NgayXuat` date NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Đang đổ dữ liệu cho bảng `products`
--

INSERT INTO `products` (`IDsp`, `Ten`, `Loai`, `Gia`, `SoLuong`, `DaNhap`, `NgayNhap`, `DaXuat`, `NgayXuat`) VALUES
(1, 'Áo tết size M', '', 35000, 100, 130, '2024-06-03', 30, '2024-06-15'),
(3, 'Thức Ăn Hạt 200g', '', 25000, 7, 107, '2024-06-15', 100, '2024-06-15'),
(4, 'CỎ MÈO', 'PHỤ KIỆN', 3000, 1, 10, '2024-06-12', 9, '2024-06-14');

-- --------------------------------------------------------

--
-- Cấu trúc bảng cho bảng `users`
--

CREATE TABLE `users` (
  `ID` int(11) NOT NULL,
  `username` varchar(250) NOT NULL,
  `password` int(12) NOT NULL,
  `quyen` varchar(30) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Đang đổ dữ liệu cho bảng `users`
--

INSERT INTO `users` (`ID`, `username`, `password`, `quyen`) VALUES
(0, 'quanli', 123, 'quanli'),
(1, 'nhanvien1', 123, 'nhanvien'),
(2, 'nhanvien2', 123, 'nhanvien'),
(3, 'nhanvien3', 123, 'nhanvien'),
(4, 'nhanvien4', 123, 'nhanvien');

--
-- Chỉ mục cho các bảng đã đổ
--

--
-- Chỉ mục cho bảng `nhanvien`
--
ALTER TABLE `nhanvien`
  ADD PRIMARY KEY (`ID`);

--
-- Chỉ mục cho bảng `products`
--
ALTER TABLE `products`
  ADD PRIMARY KEY (`IDsp`);

--
-- Chỉ mục cho bảng `users`
--
ALTER TABLE `users`
  ADD PRIMARY KEY (`ID`);

--
-- Các ràng buộc cho các bảng đã đổ
--

--
-- Các ràng buộc cho bảng `nhanvien`
--
ALTER TABLE `nhanvien`
  ADD CONSTRAINT `nhanvien_ibfk_1` FOREIGN KEY (`ID`) REFERENCES `users` (`ID`);
COMMIT;

/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
