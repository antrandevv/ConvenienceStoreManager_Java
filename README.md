# 🛒 SwiftPick - Ứng dụng Quản lý Bán hàng Cửa hàng Tiện lợi

## 🏫 Thông tin dự án
* **Cơ quan chủ quản:** Bộ Công thương - Trường Đại học Công nghiệp Thành phố Hồ Chí Minh
* **Khoa:** Công nghệ Thông tin
* **Môn học:** Lập trình hướng sự kiện với công nghệ Java
* **Giảng viên hướng dẫn:** ThS. Trần Thị Anh Thi
* **Lớp học:** DHKTPM19B | **Nhóm:** 3

---

## 👥 Bảng phân công & Đánh giá thành viên

| STT | Họ và tên | MSSV | Lớp | Nhiệm vụ | Hoàn thành |
| :--- | :--- | :--- | :--- | :--- | :--- |
| 1 | Lê Thành Danh | 24667441 | DHKTPM20A | Quy trình bán hàng, In & tìm kiếm hóa đơn, Xem chi tiết hóa đơn. | 100% |
| 2 | Lê Thành Đạt | 24663071 | DHKTPM20A | Quy trình nhập hàng, Phân quyền người dùng, Xem lịch sử nhập hàng & thông tin nhân viên. | 100% |
| 3 | Trần Phúc An | 24664811 | DHKTPM20A | Thống kê doanh thu (tuần/tháng/năm), Quản lý nhân viên (ca làm, lương, lọc nâng cao), Quản lý khách hàng (tích điểm, lọc nâng cao). | 100% |
| 4 | Phùng Tùng Lâm | 24659221 | DHKTPM20A | Xử lý tích điểm khách hàng, Quản lý sản phẩm (lọc nâng cao), Quản lý nhà cung cấp (bộ lọc). | 100% |

---

## 📌 Phần 1: Thu thập và Phân tích yêu cầu

### 1.1. Khảo sát thực trạng & Xác định bài toán
* **Mục tiêu:** Xây dựng ứng dụng quản lý nội bộ hỗ trợ bán hàng nhanh, quản lý nhân sự và kiểm soát kho hàng chặt chẽ, tối ưu hóa sơ đồ tổ chức.
* **Đối tượng sử dụng:**
  * **Nhân viên bán hàng:** Thực hiện nghiệp vụ tại quầy thanh toán.
  * **Người quản lý:** Điều hành hoạt động, quản lý nhân sự, kho hàng, xem thống kê doanh thu và có toàn quyền hệ thống.

### 1.2. Quy trình nghiệp vụ chính
* **Bán hàng & Tích điểm:** Tra cứu khách hàng qua SĐT $\rightarrow$ Tích điểm tự động (100 VNĐ = 1 điểm) $\rightarrow$ Áp dụng giảm giá (tối đa 100,000 VNĐ/hóa đơn) $\rightarrow$ Thanh toán & Tự động trừ tồn kho $\rightarrow$ Xuất hóa đơn in file PDF.
* **Quản trị & Nhân sự:** Thêm/sửa/khóa tài khoản nhân viên, quản lý nhập kho (cập nhật số lượng, giá vốn, HSD), điều chỉnh hóa đơn sai sót và thống kê doanh thu theo Tuần/Tháng/Năm.

### 1.3. Yêu cầu phi chức năng
* **Bảo mật:** Cơ chế đăng nhập phân quyền rõ ràng (Ẩn các chức năng nhạy cảm như Lương, Doanh thu đối với nhân viên).
* **Ràng buộc:** Tuổi > 0, Giá nhập/bán > 0. Nhân viên chỉ được thêm khách hàng, không được sửa/xóa nhằm tránh gian lận điểm.
* **Hiệu năng:** Tối ưu hóa câu truy vấn đảm bảo tốc độ phản hồi dưới 1 giây cho các tác vụ tại quầy.

---

## 📊 Phần 2: Sơ đồ kiến trúc & Cơ sở dữ liệu

### 2.1. Sơ đồ lớp (Class Diagram)
*Quản lý mối quan hệ giữa các thực thể trong hệ thống: Tài khoản, Nhân viên, Khách hàng, Sản phẩm, Hóa đơn, Phiếu nhập...*

### 2.2. Sơ đồ cơ sở dữ liệu (Database Diagram)
* Hệ thống sử dụng mô hình quan hệ với các bảng chính: `SanPham`, `NhaCungCap`, `PhieuNhap`, `ChiTietPhieuNhap`, `HoaDon`, `ChiTietHoaDon`, `KhachHang`, `NhanVien`, `TaiKhoan`.
* Mối quan hệ giữa các bảng tuân thủ kiến trúc chuẩn `1 - n` (ví dụ: Một hóa đơn chứa nhiều chi tiết hóa đơn) và `1 - 1` (Mỗi nhân viên có duy nhất một tài khoản).

---

## 🖥 Phần 3: Giao diện ứng dụng (Giao diện SwiftPick)

### 3.1. Màn hình Đăng nhập (Login)
Nhân viên đăng nhập với tên tài khoản và mật khẩu được cấp để hệ thống nhận diện vai trò trực tuyến.

### 3.2. Màn hình Bán hàng (Màn hình POS chính)
* **Sidebar (Bên trái):** Menu điều hướng nhanh giữa các chức năng.
* **Khu vực trung tâm:** Danh sách sản phẩm hiển thị dạng lưới (Grid), có kèm hình ảnh, giá bán và số lượng tồn kho trực quan.
* **Giỏ hàng (Bên phải):** Xử lý nhập SĐT tích điểm, tính toán tự động Tạm tính, Thuế VAT (10%), Tiền nhận và Tiền thừa trả khách.

### 3.3. Màn hình Quản lý Sản phẩm
* Hỗ trợ bộ công cụ CRUD (Thêm, Sửa, Xóa, Làm mới) sản phẩm.
* Tích hợp tính năng tự động tạo mã sản phẩm thông minh và bộ lọc tìm kiếm nâng cao theo loại/trạng thái hàng hóa.

### 3.4. Quản lý Hóa đơn & Xuất File PDF
* Danh sách hóa đơn hiển thị trực quan dưới dạng Thẻ (Card View).
* Hỗ trợ Pop-up xem trước biên lai và chức năng in trực tiếp ra file PDF lưu về máy để mở trên trình duyệt.

### 3.5. Hệ thống Thống kê dữ liệu trực quan (Dành cho Quản lý)
* **Theo Tuần:** Biểu đồ cột (Bar Chart) chi tiết Doanh thu và Chi phí theo từng ngày.
* **Theo Tháng:** Biểu đồ đường (Line Chart) theo dõi xu hướng tài chính từ ngày 1 đến ngày 31.
* **Theo Năm:** Đánh giá tổng quan dài hạn, đưa ra các cảnh báo lỗ/lãi và tự động đưa ra các gợi ý đề xuất kinh doanh thực tế.

### 3.6. Quản lý Nhân sự & Khách hàng
* Quản lý thông tin chi tiết của nhân viên (phân ca làm việc sáng/chiều/tối, hệ số lương, lương cơ bản).
* Quản lý phân hạng khách hàng (Thành viên, VIP, Vãng lai) phục vụ các chiến dịch chăm sóc khách hàng thân thiết.
