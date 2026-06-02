USE [master]
GO
IF EXISTS (SELECT name FROM sys.databases WHERE name = N'QLBH')
BEGIN
    -- Ngắt kết nối đang hiện hành để có quyền xóa
    ALTER DATABASE [QLBH] SET SINGLE_USER WITH ROLLBACK IMMEDIATE;
    DROP DATABASE [QLBH];
END
GO

CREATE DATABASE [QLBH]
GO

IF (1 = FULLTEXTSERVICEPROPERTY('IsFullTextInstalled'))
begin
EXEC [QLBH].[dbo].[sp_fulltext_database] @action = 'enable'
end
GO
ALTER DATABASE [QLBH] SET ANSI_NULL_DEFAULT OFF 
GO
ALTER DATABASE [QLBH] SET ANSI_NULLS OFF 
GO
ALTER DATABASE [QLBH] SET ANSI_PADDING OFF 
GO
ALTER DATABASE [QLBH] SET ANSI_WARNINGS OFF 
GO
ALTER DATABASE [QLBH] SET ARITHABORT OFF 
GO
ALTER DATABASE [QLBH] SET AUTO_CLOSE OFF 
GO
ALTER DATABASE [QLBH] SET AUTO_SHRINK OFF 
GO
ALTER DATABASE [QLBH] SET AUTO_UPDATE_STATISTICS ON 
GO
ALTER DATABASE [QLBH] SET CURSOR_CLOSE_ON_COMMIT OFF 
GO
ALTER DATABASE [QLBH] SET CURSOR_DEFAULT  GLOBAL 
GO
ALTER DATABASE [QLBH] SET CONCAT_NULL_YIELDS_NULL OFF 
GO
ALTER DATABASE [QLBH] SET NUMERIC_ROUNDABORT OFF 
GO
ALTER DATABASE [QLBH] SET QUOTED_IDENTIFIER OFF 
GO
ALTER DATABASE [QLBH] SET RECURSIVE_TRIGGERS OFF 
GO
ALTER DATABASE [QLBH] SET  ENABLE_BROKER 
GO
ALTER DATABASE [QLBH] SET AUTO_UPDATE_STATISTICS_ASYNC OFF 
GO
ALTER DATABASE [QLBH] SET DATE_CORRELATION_OPTIMIZATION OFF 
GO
ALTER DATABASE [QLBH] SET TRUSTWORTHY OFF 
GO
ALTER DATABASE [QLBH] SET ALLOW_SNAPSHOT_ISOLATION OFF 
GO
ALTER DATABASE [QLBH] SET PARAMETERIZATION SIMPLE 
GO
ALTER DATABASE [QLBH] SET READ_COMMITTED_SNAPSHOT OFF 
GO
ALTER DATABASE [QLBH] SET HONOR_BROKER_PRIORITY OFF 
GO
ALTER DATABASE [QLBH] SET RECOVERY FULL 
GO
ALTER DATABASE [QLBH] SET  MULTI_USER 
GO
ALTER DATABASE [QLBH] SET PAGE_VERIFY CHECKSUM  
GO
ALTER DATABASE [QLBH] SET DB_CHAINING OFF 
GO
ALTER DATABASE [QLBH] SET FILESTREAM( NON_TRANSACTED_ACCESS = OFF ) 
GO
ALTER DATABASE [QLBH] SET TARGET_RECOVERY_TIME = 60 SECONDS 
GO

EXEC sys.sp_db_vardecimal_storage_format N'QLBH', N'ON'
GO

USE [QLBH]
GO

-- =============================================
-- TAO BANG (TABLES)
-- =============================================

SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
CREATE TABLE [dbo].[ChiTietHoaDon](
	[MaHD] [nvarchar](20) NOT NULL,
	[MaSP] [nvarchar](20) NOT NULL,
	[TenSP] [nvarchar](50) NULL,
	[GiaBan] [money] NULL,
	[SoLuong] [int] NULL,
	[Thue] [money] NULL,
	[ThanhTien] [money] NULL,
 CONSTRAINT [PK_ChiTietHoaDon] PRIMARY KEY CLUSTERED 
(
	[MaHD] ASC,
	[MaSP] ASC
)WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON) ON [PRIMARY]
) ON [PRIMARY]
GO

CREATE TABLE [dbo].[ChiTietPhieuNhap](
	[MaPN] [nvarchar](20) NOT NULL,
	[MaSP] [nvarchar](20) NOT NULL,
	[TenSP] [nvarchar](50) NULL,
	[SoLuong] [int] NULL,
	[GiaNhap] [money] NULL,
	[ThanhTien] [money] NULL,
 CONSTRAINT [PK_ChiTietPhieuNhap] PRIMARY KEY CLUSTERED 
(
	[MaPN] ASC,
	[MaSP] ASC
)WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON) ON [PRIMARY]
) ON [PRIMARY]
GO

CREATE TABLE [dbo].[HoaDon](
	[MaHD] [nvarchar](20) NOT NULL,
	[NgayLap] [date] NULL,
	[MaNV] [nvarchar](20) NULL,
	[MaKH] [nvarchar](20) NULL,
	[TrangThaiHD] [nvarchar](30) NULL,
	[TongTien] [money] NULL,
 CONSTRAINT [PK_HoaDon] PRIMARY KEY CLUSTERED 
(
	[MaHD] ASC
)WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON) ON [PRIMARY]
) ON [PRIMARY]
GO

CREATE TABLE [dbo].[KhachHang](
	[MaKH] [nvarchar](20) NOT NULL,
	[TenKH] [nvarchar](50) NULL,
	[SoDienThoai] [nvarchar](10) NULL,
	[DiemTichLuy] [int] NULL,
	[DiemDaNhan] [int] NULL,
	[LoaiKH] [nvarchar](15) NULL,
 CONSTRAINT [PK_KhachHang] PRIMARY KEY CLUSTERED 
(
	[MaKH] ASC
)WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON) ON [PRIMARY]
) ON [PRIMARY]
GO

CREATE TABLE [dbo].[NhaCungCap](
	[MaNCC] [nvarchar](20) NOT NULL,
	[TenNCC] [nvarchar](50) NULL,
	[SoDienThoai] [nvarchar](10) NULL,
	[DiaChi] [nvarchar](100) NULL,
	[Email] [nvarchar](50) NULL,
 CONSTRAINT [PK_NhaCungCap] PRIMARY KEY CLUSTERED 
(
	[MaNCC] ASC
)WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON) ON [PRIMARY]
) ON [PRIMARY]
GO

CREATE TABLE [dbo].[NhanVien](
	[MaNV] [nvarchar](20) NOT NULL,
	[TenNV] [nvarchar](50) NULL,
	[GioiTinh] [bit] NULL,
	[Tuoi] [int] NULL,
	[DiaChi] [nvarchar](100) NULL,
	[SoDienThoai] [nvarchar](10) NULL,
	[Email] [nvarchar](50) NULL,
	[HinhAnh] [nvarchar](50) NULL,
	[CaLamViec] [nvarchar](15) NULL,
	[ViTri] [nvarchar](15) NOT NULL,
	[HeSoLuong] [float] NULL,    -- Cột mới thêm
	[LuongCoBan] [money] NULL,   -- Cột mới thêm
 CONSTRAINT [PK_NhanVien] PRIMARY KEY CLUSTERED 
(
	[MaNV] ASC
)WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON) ON [PRIMARY]
) ON [PRIMARY]
GO

CREATE TABLE [dbo].[PhieuNhap](
	[MaPN] [nvarchar](20) NOT NULL,
	[ThoiGianNhap] [date] NULL,
	[MaNCC] [nvarchar](20) NULL,
	[MaNV] [nvarchar](20) NULL,
	[TongTien] [money] NULL,
 CONSTRAINT [PK_PhieuNhap] PRIMARY KEY CLUSTERED 
(
	[MaPN] ASC
)WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON) ON [PRIMARY]
) ON [PRIMARY]
GO

CREATE TABLE [dbo].[SanPham](
	[MaSP] [nvarchar](20) NOT NULL,
	[TenSP] [nvarchar](50) NULL,
	[LoaiSP] [nvarchar](50) NULL,
	[GiaBan] [money] NULL,
	[GiaNhap] [money] NULL,
	[SoLuongTon] [int] NULL,
	[DonViTinh] [nvarchar](30) NULL,
	[NhaCungCap] [nvarchar](20) NOT NULL,
	[HinhAnh] [nvarchar](100) NULL,
	[hanSuDung] [nvarchar](50) NULL,
	[TrangThai] [nvarchar](50) NULL,
 CONSTRAINT [PK_SanPham] PRIMARY KEY CLUSTERED 
(
	[MaSP] ASC
)WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON) ON [PRIMARY]
) ON [PRIMARY]
GO

CREATE TABLE [dbo].[TaiKhoan](
	[TenTaiKhoan] [nvarchar](50) NOT NULL,
	[MatKhau] [nvarchar](50) NOT NULL,
	[ViTri] [nvarchar](15) NOT NULL,
	[MaNV] [nvarchar](20) NOT NULL,
 CONSTRAINT [PK_TaiKhoan] PRIMARY KEY CLUSTERED 
(
	[TenTaiKhoan] ASC
)WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON) ON [PRIMARY]
) ON [PRIMARY]
GO


-- =============================================
-- INSERT DATA
-- =============================================

INSERT INTO [dbo].[NhaCungCap] ([MaNCC], [TenNCC], [SoDienThoai], [DiaChi], [Email]) VALUES 
(N'NCC1', N'Công ty CP Sữa Việt Nam Vinamilk', N'0901111111', N'Quận 7, TP.HCM', N'lienhe@vinamilk.com'),
(N'NCC2', N'Tập đoàn Thiên Long', N'0902222222', N'Quận 11, TP.HCM', N'contact@thienlong.com'),
(N'NCC3', N'Công ty CP Mondelez Kinh Đô', N'0903333333', N'Bình Dương', N'kinhdo@mondelez.com'),
(N'NCC4', N'Công ty CP Acecook Việt Nam', N'0904444444', N'Quận Tân Phú, TP.HCM', N'info@acecook.vn'),
(N'NCC5', N'Công ty TNHH Nước Giải Khát Coca-Cola', N'0905555555', N'Thủ Đức, TP.HCM', N'cocacola.vn@coca.com'),
(N'NCC6', N'Suntory PepsiCo Việt Nam', N'0906666666', N'Quận 1, TP.HCM', N'pepsico@suntory.com'),
(N'NCC7', N'Công ty TNHH Nestlé Việt Nam', N'0907777777', N'Biên Hòa, Đồng Nai', N'nestle.vn@nestle.com'),
(N'NCC8', N'Công ty Bánh kẹo Hải Châu', N'0908888888', N'Hai Bà Trưng, Hà Nội', N'haichau@hc.com'),
(N'NCC9', N'Tập đoàn Masan Consumer', N'0909999999', N'Quận 1, TP.HCM', N'masan@masan.com'),
(N'NCC10', N'Công ty TNHH Chupa Chups', N'0910000000', N'Bình Dương', N'chupachups@perfetti.com');
GO

INSERT [dbo].[NhanVien] ([MaNV], [TenNV], [GioiTinh], [Tuoi], [DiaChi], [SoDienThoai], [Email], [HinhAnh], [CaLamViec], [ViTri], [HeSoLuong], [LuongCoBan]) VALUES (N'NV1', N'Lê Thành Danh', 0, 20, N'Quận 1, TP.HCM', N'0912345678', N'danhlth@qlbh.com', N'img/avatar/nv1.jpg', N'CA_SANG', N'NHAN_VIEN', 2.0, 5000000.0000)
INSERT [dbo].[NhanVien] ([MaNV], [TenNV], [GioiTinh], [Tuoi], [DiaChi], [SoDienThoai], [Email], [HinhAnh], [CaLamViec], [ViTri], [HeSoLuong], [LuongCoBan]) VALUES (N'NV10', N'Lý Thanh Tùng', 0, 31, N'Quận 12, TP.HCM', N'0901234567', N'tunglt@qlbh.com', N'img/avatar/user.png', N'CA_SANG', N'NHAN_VIEN', 1.5, 4500000.0000)
INSERT [dbo].[NhanVien] ([MaNV], [TenNV], [GioiTinh], [Tuoi], [DiaChi], [SoDienThoai], [Email], [HinhAnh], [CaLamViec], [ViTri], [HeSoLuong], [LuongCoBan]) VALUES (N'NV2', N'Lê Thành Đạt', 0, 20, N'Quận 3, TP.HCM', N'0923456789', N'datlth@qlbh.com', N'img/avatar/nv2.jpg', N'CA_SANG', N'NHAN_VIEN', 2.0, 5000000.0000)
INSERT [dbo].[NhanVien] ([MaNV], [TenNV], [GioiTinh], [Tuoi], [DiaChi], [SoDienThoai], [Email], [HinhAnh], [CaLamViec], [ViTri], [HeSoLuong], [LuongCoBan]) VALUES (N'NV3', N'Phùng Tùng Lâm', 0, 20, N'Bình Thạnh, TP.HCM', N'0934567890', N'lampt@qlbh.com', N'img/avatar/nv3.jpg', N'CA_CHIEU', N'NHAN_VIEN', 2.0, 5000000.0000)
INSERT [dbo].[NhanVien] ([MaNV], [TenNV], [GioiTinh], [Tuoi], [DiaChi], [SoDienThoai], [Email], [HinhAnh], [CaLamViec], [ViTri], [HeSoLuong], [LuongCoBan]) VALUES (N'NV4', N'Trần Phúc An', 0, 20, N'Gò Vấp, TP.HCM', N'0945678901', N'antph@qlbh.com', N'img/avatar/nv4.jpg', N'CA_SANG', N'NHAN_VIEN', 1.8, 4800000.0000)
INSERT [dbo].[NhanVien] ([MaNV], [TenNV], [GioiTinh], [Tuoi], [DiaChi], [SoDienThoai], [Email], [HinhAnh], [CaLamViec], [ViTri], [HeSoLuong], [LuongCoBan]) VALUES (N'NV5', N'Hoàng Minh Trí', 0, 26, N'Thủ Đức, TP.HCM', N'0956789012', N'vip888f2@qlbh.com', N'img/avatar/user.png', N'CA_TOI', N'NHAN_VIEN', 1.5, 4500000.0000)
INSERT [dbo].[NhanVien] ([MaNV], [TenNV], [GioiTinh], [Tuoi], [DiaChi], [SoDienThoai], [Email], [HinhAnh], [CaLamViec], [ViTri], [HeSoLuong], [LuongCoBan]) VALUES (N'NV6', N'Đặng Minh Khôi', 0, 23, N'Quận 10, TP.HCM', N'0967890123', N'khoidm@qlbh.com', N'img/avatar/user.png', N'CA_CHIEU', N'NHAN_VIEN', 1.5, 4500000.0000)
INSERT [dbo].[NhanVien] ([MaNV], [TenNV], [GioiTinh], [Tuoi], [DiaChi], [SoDienThoai], [Email], [HinhAnh], [CaLamViec], [ViTri], [HeSoLuong], [LuongCoBan]) VALUES (N'NV7', N'Bùi Tuyết Nhi', 1, 25, N'Quận 7, TP.HCM', N'0978901234', N'nhibt@qlbh.com', N'img/avatar/user.png', N'CA_SANG', N'NHAN_VIEN', 1.5, 4500000.0000)
INSERT [dbo].[NhanVien] ([MaNV], [TenNV], [GioiTinh], [Tuoi], [DiaChi], [SoDienThoai], [Email], [HinhAnh], [CaLamViec], [ViTri], [HeSoLuong], [LuongCoBan]) VALUES (N'NV8', N'Võ Hoàng Nam', 0, 29, N'Gò Vấp, TP.HCM', N'0989012345', N'namvh@qlbh.com', N'img/avatar/user.png', N'CA_CHIEU', N'NHAN_VIEN', 1.5, 4500000.0000)
INSERT [dbo].[NhanVien] ([MaNV], [TenNV], [GioiTinh], [Tuoi], [DiaChi], [SoDienThoai], [Email], [HinhAnh], [CaLamViec], [ViTri], [HeSoLuong], [LuongCoBan]) VALUES (N'NV9', N'Đỗ Kim Chi', 1, 27, N'Hóc Môn, TP.HCM', N'0990123456', N'chidk@qlbh.com', N'img/avatar/user.png', N'CA_TOI', N'NHAN_VIEN', 1.5, 4500000.0000)
GO

INSERT INTO [dbo].[TaiKhoan] ([TenTaiKhoan], [MatKhau], [ViTri], [MaNV]) VALUES 
(N'admin', N'admin123', N'QUAN_LY', N'NV1'),
(N'antp', N'an123', N'NHAN_VIEN', N'NV4'),
(N'chidk', N'chi123', N'NHAN_VIEN', N'NV9'),
(N'datlt', N'dat123', N'NHAN_VIEN', N'NV2'),
(N'khoidm', N'khoi123', N'NHAN_VIEN', N'NV6'),
(N'lampt', N'lam123', N'NHAN_VIEN', N'NV3'),
(N'namvh', N'nam123', N'NHAN_VIEN', N'NV8'),
(N'nhibt', N'nhi123', N'NHAN_VIEN', N'NV7'),
(N'trihm', N'tri123', N'NHAN_VIEN', N'NV5'),
(N'tunglt', N'tung123', N'NHAN_VIEN', N'NV10');
GO

-- Đã sửa dữ liệu sao cho DiemDaNhan (Tổng điểm đã từng nhận) >= DiemTichLuy (Điểm đang còn)
INSERT INTO [dbo].[KhachHang] ([MaKH], [TenKH], [SoDienThoai], [DiemTichLuy], [DiemDaNhan], [LoaiKH]) VALUES 
(N'KH1', N'Nguyễn Văn An', N'0811111111', 120, 150, N'THANH_VIEN'),
(N'KH2', N'Trần Thị Bình', N'0822222222', 550, 700, N'VIP'),
(N'KH3', N'Lê Minh Cường', N'0833333333', 50, 50, N'VANG_LAI'),
(N'KH4', N'Phạm Hồng Đào', N'0844444444', 1200, 1500, N'VIP'),
(N'KH5', N'Võ Văn Em', N'0855555555', 300, 450, N'THANH_VIEN'),
(N'KH6', N'Đinh Tấn Phát', N'0901234567', 420, 420, N'THANH_VIEN'),
(N'KH7', N'Ngô Tố Như', N'0912345678', 15, 15, N'VANG_LAI'),
(N'KH8', N'Huỳnh Gia Bảo', N'0923456789', 1800, 2500, N'VIP'),
(N'KH9', N'Bùi Hà My', N'0934567890', 250, 300, N'THANH_VIEN'),
(N'KH10', N'Cao Văn Đạt', N'0945678901', 80, 80, N'VANG_LAI');
GO

INSERT INTO [dbo].[SanPham] ([MaSP], [TenSP], [LoaiSP], [GiaBan], [GiaNhap], [SoLuongTon], [DonViTinh], [NhaCungCap], [HinhAnh], [hanSuDung], [TrangThai]) VALUES 
(N'SP1', N'Sữa tươi tiệt trùng Vinamilk', N'SUA', 350000.0000, 300000.0000, 132, N'THUNG', N'NCC1', N'img/suavinamilk.jpg', N'SAU_THANG', N'CON_HANG'),
(N'SP2', N'Sữa chua nha đam Vinamilk', N'SUA', 28000.0000, 22000.0000, 200, N'LOC', N'NCC1', N'img/suachuanhadam.jpg', N'BA_THANG', N'CON_HANG'),
(N'SP3', N'Bút bi Thiên Long TL-027', N'VAN_PHONG_PHAM', 60000.0000, 45000.0000, 120, N'HOP', N'NCC2', N'img/butbitl.jpg', N'BA_NAM', N'CON_HANG'),
(N'SP4', N'Bút dạ quang Thiên Long', N'VAN_PHONG_PHAM', 15000.0000, 10000.0000, 150, N'CAY', N'NCC2', N'img/butdaquangtl.jpg', N'BA_NAM', N'CON_HANG'),
(N'SP5', N'Bánh quy bơ Cosy', N'BANH_KEO', 45000.0000, 35000.0000, 80, N'HOP', N'NCC3', N'img/banhcosy.jpg', N'MUOI_HAI_THANG', N'CON_HANG'),
(N'SP6', N'Bánh quy AFC lúa mì', N'BANH_KEO', 30000.0000, 25000.0000, 100, N'HOP', N'NCC3', N'img/banhafc.jpg', N'MOT_NAM', N'HET_HANG'),
(N'SP7', N'Mì Hảo Hảo tôm chua cay', N'MI_GOI', 110000.0000, 95000.0000, 500, N'THUNG', N'NCC4', N'img/haohaothung.jpg', N'SAU_THANG', N'CON_HANG'),
(N'SP8', N'Phở bò Đệ Nhất', N'MI_GOI', 8000.0000, 6000.0000, 450, N'GOI', N'NCC4', N'img/phodenhat.jpg', N'SAU_THANG', N'CON_HANG'),
(N'SP9', N'Nước ngọt Coca-Cola 330ml', N'DO_UONG', 10000.0000, 8000.0000, 600, N'LON', N'NCC5', N'img/loncoca.jpg', N'MUOI_HAI_THANG', N'CON_HANG'),
(N'SP10', N'Nước giải khát Sprite 1.5L', N'DO_UONG', 20000.0000, 16000.0000, 401, N'CHAI', N'NCC5', N'img/chaisprite.jpg', N'MOT_NAM', N'CON_HANG'),
(N'SP11', N'Nước ngọt Pepsi Cola 330ml', N'DO_UONG', 10000.0000, 7500.0000, 532, N'LON', N'NCC6', N'img/lonpepsi.jpg', N'MUOI_HAI_THANG', N'CON_HANG'),
(N'SP12', N'Trà Ô Long TEA+ Plus 455ml', N'DO_UONG', 12000.0000, 9000.0000, 0, N'CHAI', N'NCC6', N'img/olong.jpg', N'CHIN_THANG', N'HET_HANG'),
(N'SP13', N'Cà phê NesCafé 3in1', N'DO_UONG', 55000.0000, 45000.0000, 150, N'HOP', N'NCC7', N'img/hopcafe.jpg', N'BA_NAM', N'CON_HANG'),
(N'SP14', N'Thức uống lúa mạch Milo', N'SUA', 32000.0000, 26000.0000, 250, N'LOC', N'NCC7', N'img/locmilo.jpg', N'CHIN_THANG', N'CON_HANG'),
(N'SP15', N'Lương khô Hải Châu', N'BANH_KEO', 60000.0000, 50000.0000, 300, N'HOP', N'NCC8', N'img/luongkho.jpg', N'MOT_NAM', N'CON_HANG'),
(N'SP16', N'Kẹo dẻo hương trái cây', N'BANH_KEO', 20000.0000, 15000.0000, 0, N'BICH', N'NCC8', N'img/keodeo.jpg', N'MUOI_HAI_THANG', N'HET_HANG'),
(N'SP17', N'Mì khoai tây Omachi', N'MI_GOI', 200000.0000, 180000.0000, 90, N'THUNG', N'NCC9', N'img/thungomachi.jpg', N'SAU_THANG', N'CON_HANG'),
(N'SP18', N'Mì Kokomi 90g', N'MI_GOI', 85000.0000, 75000.0000, 60, N'THUNG', N'NCC9', N'img/thungkokomi.jpg', N'SAU_THANG', N'CON_HANG'),
(N'SP19', N'Kẹo mút Chupa Chups', N'BANH_KEO', 35000.0000, 28000.0000, 120, N'BICH', N'NCC10', N'img/keochupachups.jpg', N'BA_NAM', N'CON_HANG'),
(N'SP20', N'Sữa đặc Ông Thọ', N'SUA', 25000.0000, 20000.0000, 150, N'LON', N'NCC10', N'img/suaongtho.jpg', N'MOT_NAM', N'CON_HANG');
GO

INSERT INTO [dbo].[PhieuNhap] ([MaPN], [ThoiGianNhap], [MaNCC], [MaNV], [TongTien]) VALUES 
(N'PN001', CAST(N'2025-10-25' AS Date), N'NCC1', N'NV1', 15000000.0000),
(N'PN002', CAST(N'2025-11-15' AS Date), N'NCC2', N'NV5', 5000000.0000),
(N'PN003', CAST(N'2025-12-05' AS Date), N'NCC3', N'NV8', 8500000.0000),
(N'PN004', CAST(N'2025-12-20' AS Date), N'NCC4', N'NV5', 11996000.0000),
(N'PN005', CAST(N'2026-01-10' AS Date), N'NCC5', N'NV8', 6992000.0000),
(N'PN006', CAST(N'2026-02-15' AS Date), N'NCC6', N'NV1', 10497000.0000),
(N'PN007', CAST(N'2026-03-05' AS Date), N'NCC7', N'NV5', 9180000.0000),
(N'PN008', CAST(N'2026-03-25' AS Date), N'NCC8', N'NV8', 4295000.0000),
(N'PN009', CAST(N'2026-04-10' AS Date), N'NCC9', N'NV1', 19950000.0000),
(N'PN010', CAST(N'2026-04-20' AS Date), N'NCC10', N'NV5', 3500000.0000),
(N'PN11',  CAST(N'2026-04-28' AS Date), N'NCC10', N'NV4', 38000.0000),
(N'PN12',  CAST(N'2026-05-01' AS Date), N'NCC1', N'NV4', 22000.0000),
(N'PN13',  CAST(N'2026-05-02' AS Date), N'NCC2', N'NV4', 1000000.0000),
(N'PN14',  CAST(N'2026-05-03' AS Date), N'NCC6', N'NV1', 1800000.0000);
GO

INSERT INTO [dbo].[ChiTietPhieuNhap] ([MaPN], [MaSP], [TenSP], [SoLuong], [GiaNhap], [ThanhTien]) VALUES 
(N'PN001', N'SP1', N'Sữa tươi tiệt trùng Vinamilk', 50, 300000.0000, 15000000.0000),
(N'PN002', N'SP3', N'Bút bi Thiên Long TL-027', 100, 45000.0000, 4500000.0000),
(N'PN002', N'SP4', N'Bút dạ quang Thiên Long', 50, 10000.0000, 500000.0000),
(N'PN003', N'SP5', N'Bánh quy bơ Cosy', 200, 35000.0000, 7000000.0000),
(N'PN003', N'SP6', N'Bánh quy AFC lúa mì', 60, 25000.0000, 1500000.0000),
(N'PN004', N'SP7', N'Mì Hảo Hảo tôm chua cay', 100, 95000.0000, 9500000.0000),
(N'PN004', N'SP8', N'Phở bò Đệ Nhất', 416, 6000.0000, 2496000.0000),
(N'PN005', N'SP10', N'Nước giải khát Sprite 1.5L', 187, 16000.0000, 2992000.0000),
(N'PN005', N'SP9', N'Nước ngọt Coca-Cola 330ml', 500, 8000.0000, 4000000.0000),
(N'PN006', N'SP11', N'Nước ngọt Pepsi Cola 330ml', 1000, 7500.0000, 7500000.0000),
(N'PN006', N'SP12', N'Trà Ô Long TEA+ Plus 455ml', 333, 9000.0000, 2997000.0000),
(N'PN007', N'SP13', N'Cà phê NesCafé 3in1', 100, 45000.0000, 4500000.0000),
(N'PN007', N'SP14', N'Thức uống lúa mạch Milo', 180, 26000.0000, 4680000.0000),
(N'PN008', N'SP15', N'Lương khô Hải Châu', 70, 50000.0000, 3500000.0000),
(N'PN008', N'SP16', N'Kẹo dẻo hương trái cây', 53, 15000.0000, 795000.0000),
(N'PN009', N'SP17', N'Mì khoai tây Omachi', 100, 180000.0000, 18000000.0000),
(N'PN009', N'SP18', N'Mì Kokomi 90g', 26, 75000.0000, 1950000.0000),
(N'PN010', N'SP19', N'Kẹo mút Chupa Chups', 100, 28000.0000, 2800000.0000),
(N'PN010', N'SP20', N'Sữa đặc Ông Thọ', 35, 20000.0000, 700000.0000),
(N'PN11',  N'SP10', N'Nước giải khát Sprite 1.5L', 1, 16000.0000, 16000.0000),
(N'PN11',  N'SP2', N'Sữa chua nha đam Vinamilk', 1, 22000.0000, 22000.0000),
(N'PN12',  N'SP2', N'Sữa chua nha đam Vinamilk', 1, 22000.0000, 22000.0000),
(N'PN13',  N'SP4', N'Bút dạ quang Thiên Long', 100, 10000.0000, 1000000.0000), 
(N'PN14',  N'SP12', N'Trà Ô Long TEA+ Plus 455ml', 200, 9000.0000, 1800000.0000); 
GO

INSERT INTO [dbo].[HoaDon] ([MaHD], [NgayLap], [MaNV], [MaKH], [TrangThaiHD], [TongTien]) VALUES 
(N'HD001', CAST(N'2025-11-05' AS Date), N'NV2', N'KH1', N'DA_THANH_TOAN', 755000.0000),
(N'HD002', CAST(N'2025-11-20' AS Date), N'NV3', N'KH2', N'DA_THANH_TOAN', 1200000.0000),
(N'HD003', CAST(N'2025-12-15' AS Date), N'NV4', N'KH3', N'CHUA_THANH_TOAN', 45000.0000),
(N'HD004', CAST(N'2026-01-10' AS Date), N'NV6', N'KH4', N'DA_THANH_TOAN', 2442000.0000),
(N'HD005', CAST(N'2026-02-14' AS Date), N'NV7', N'KH5', N'DA_THANH_TOAN', 150000.0000),
(N'HD006', CAST(N'2026-03-08' AS Date), N'NV9', N'KH1', N'DA_HUY', 0.0000),
(N'HD007', CAST(N'2026-03-25' AS Date), N'NV10', N'KH2', N'DA_THANH_TOAN', 850000.0000),
(N'HD008', CAST(N'2026-04-10' AS Date), N'NV2', N'KH8', N'DA_THANH_TOAN', 335000.0000),
(N'HD009', CAST(N'2026-04-30' AS Date), N'NV3', N'KH9', N'DA_THANH_TOAN', 446250.0000),
(N'HD010', CAST(N'2026-05-02' AS Date), N'NV4', N'KH7', N'DA_THANH_TOAN', 110000.0000),
(N'HD011', CAST(N'2026-05-05' AS Date), N'NV1', NULL, N'DA_THANH_TOAN', 50000.0000),
(N'HD012', CAST(N'2026-05-06' AS Date), N'NV1', NULL, N'DA_THANH_TOAN', 3150000.0000),
(N'HD013', CAST(N'2026-05-07' AS Date), N'NV1', N'KH6', N'DA_THANH_TOAN', 3150000.0000);
GO

INSERT INTO [dbo].[ChiTietHoaDon] ([MaHD], [MaSP], [TenSP], [GiaBan], [SoLuong], [Thue], [ThanhTien]) VALUES 
(N'HD001', N'SP1', N'Sữa tươi tiệt trùng Vinamilk', 350000.0000, 2, 35000.0000, 735000.0000),
(N'HD001', N'SP19', N'Kẹo mút Chupa Chups', 20000.0000, 1, 0.0000, 20000.0000),
(N'HD002', N'SP17', N'Mì khoai tây Omachi', 200000.0000, 6, 0.0000, 1200000.0000),
(N'HD003', N'SP5', N'Bánh quy bơ Cosy', 45000.0000, 1, 0.0000, 45000.0000),
(N'HD004', N'SP17', N'Mì khoai tây Omachi', 200000.0000, 10, 200000.0000, 2200000.0000),
(N'HD004', N'SP7', N'Mì Hảo Hảo tôm chua cay', 110000.0000, 2, 22000.0000, 242000.0000),
(N'HD005', N'SP20', N'Sữa đặc Ông Thọ', 25000.0000, 6, 0.0000, 150000.0000),
(N'HD007', N'SP1', N'Sữa tươi tiệt trùng Vinamilk', 350000.0000, 2, 70000.0000, 770000.0000),
(N'HD007', N'SP9', N'Nước ngọt Coca-Cola 330ml', 10000.0000, 8, 0.0000, 80000.0000),
(N'HD008', N'SP10', N'Nước giải khát Sprite 1.5L', 20000.0000, 1, 0.0000, 20000.0000),
(N'HD008', N'SP3', N'Bút bi Thiên Long TL-027', 60000.0000, 5, 15000.0000, 315000.0000),
(N'HD009', N'SP17', N'Mì khoai tây Omachi', 200000.0000, 2, 20000.0000, 420000.0000),
(N'HD009', N'SP20', N'Sữa đặc Ông Thọ', 25000.0000, 1, 1250.0000, 26250.0000),
(N'HD010', N'SP7', N'Mì Hảo Hảo tôm chua cay', 110000.0000, 1, 0.0000, 110000.0000),
(N'HD011', N'SP11', N'Nước ngọt Pepsi Cola 330ml', 10000.0000, 5, 0.0000, 50000.0000),
(N'HD012', N'SP1', N'Sữa tươi tiệt trùng Vinamilk', 350000.0000, 9, 0.0000, 3150000.0000),
(N'HD013', N'SP1', N'Sữa tươi tiệt trùng Vinamilk', 350000.0000, 9, 0.0000, 3150000.0000);
GO


-- =============================================
-- THIET LAP KHOA NGOAI (FOREIGN KEYS)
-- =============================================

ALTER TABLE [dbo].[ChiTietHoaDon]  WITH CHECK ADD  CONSTRAINT [FK_ChiTietHoaDon_HoaDon] FOREIGN KEY([MaHD])
REFERENCES [dbo].[HoaDon] ([MaHD])
GO
ALTER TABLE [dbo].[ChiTietHoaDon] CHECK CONSTRAINT [FK_ChiTietHoaDon_HoaDon]
GO
ALTER TABLE [dbo].[ChiTietHoaDon]  WITH CHECK ADD  CONSTRAINT [FK_ChiTietHoaDon_SanPham] FOREIGN KEY([MaSP])
REFERENCES [dbo].[SanPham] ([MaSP])
GO
ALTER TABLE [dbo].[ChiTietHoaDon] CHECK CONSTRAINT [FK_ChiTietHoaDon_SanPham]
GO
ALTER TABLE [dbo].[ChiTietPhieuNhap]  WITH CHECK ADD  CONSTRAINT [FK_ChiTietPhieuNhap_PhieuNhap] FOREIGN KEY([MaPN])
REFERENCES [dbo].[PhieuNhap] ([MaPN])
GO
ALTER TABLE [dbo].[ChiTietPhieuNhap] CHECK CONSTRAINT [FK_ChiTietPhieuNhap_PhieuNhap]
GO
ALTER TABLE [dbo].[ChiTietPhieuNhap]  WITH CHECK ADD  CONSTRAINT [FK_ChiTietPhieuNhap_SanPham] FOREIGN KEY([MaSP])
REFERENCES [dbo].[SanPham] ([MaSP])
GO
ALTER TABLE [dbo].[ChiTietPhieuNhap] CHECK CONSTRAINT [FK_ChiTietPhieuNhap_SanPham]
GO
ALTER TABLE [dbo].[HoaDon]  WITH CHECK ADD  CONSTRAINT [FK_HoaDon_KhachHang] FOREIGN KEY([MaKH])
REFERENCES [dbo].[KhachHang] ([MaKH])
GO
ALTER TABLE [dbo].[HoaDon] CHECK CONSTRAINT [FK_HoaDon_KhachHang]
GO
ALTER TABLE [dbo].[HoaDon]  WITH CHECK ADD  CONSTRAINT [FK_HoaDon_NhanVien] FOREIGN KEY([MaNV])
REFERENCES [dbo].[NhanVien] ([MaNV])
GO
ALTER TABLE [dbo].[HoaDon] CHECK CONSTRAINT [FK_HoaDon_NhanVien]
GO
ALTER TABLE [dbo].[PhieuNhap]  WITH CHECK ADD  CONSTRAINT [FK_PhieuNhap_NhaCungCap] FOREIGN KEY([MaNCC])
REFERENCES [dbo].[NhaCungCap] ([MaNCC])
GO
ALTER TABLE [dbo].[PhieuNhap] CHECK CONSTRAINT [FK_PhieuNhap_NhaCungCap]
GO
ALTER TABLE [dbo].[PhieuNhap]  WITH CHECK ADD  CONSTRAINT [FK_PhieuNhap_NhanVien] FOREIGN KEY([MaNV])
REFERENCES [dbo].[NhanVien] ([MaNV])
GO
ALTER TABLE [dbo].[PhieuNhap] CHECK CONSTRAINT [FK_PhieuNhap_NhanVien]
GO
ALTER TABLE [dbo].[SanPham]  WITH CHECK ADD  CONSTRAINT [FK_SanPham_NhaCungCap] FOREIGN KEY([NhaCungCap])
REFERENCES [dbo].[NhaCungCap] ([MaNCC])
GO
ALTER TABLE [dbo].[SanPham] CHECK CONSTRAINT [FK_SanPham_NhaCungCap]
GO
ALTER TABLE [dbo].[TaiKhoan]  WITH CHECK ADD  CONSTRAINT [FK_TaiKhoan_NhanVien] FOREIGN KEY([MaNV])
REFERENCES [dbo].[NhanVien] ([MaNV])
GO
ALTER TABLE [dbo].[TaiKhoan] CHECK CONSTRAINT [FK_TaiKhoan_NhanVien]
GO
USE [master]
GO
ALTER DATABASE [QLBH] SET  READ_WRITE 
GO