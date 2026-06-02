# SwiftPick - Ứng dụng Quản lý Bán hàng Cửa hàng Tiện lợi

## Thông tin dự án
* **Cơ quan chủ quản:** Bộ Công thương - Trường Đại học Công nghiệp Thành phố Hồ Chí Minh
* **Khoa:** Công nghệ Thông tin
* **Môn học:** Lập trình hướng sự kiện với công nghệ Java
* **Giảng viên hướng dẫn:** ThS. Trần Thị Anh Thi
* **Lớp học:** DHKTPM19B | **Nhóm:** 3

---

## Bảng phân công & Đánh giá thành viên

| STT | Họ và tên | MSSV | Lớp | Nhiệm vụ | Hoàn thành |
| :--- | :--- | :--- | :--- | :--- | :--- |
| 1 | Lê Thành Danh | 24667441 | DHKTPM20A | Quy trình bán hàng, In & tìm kiếm hóa đơn, Xem chi tiết hóa đơn. | 100% |
| 2 | Lê Thành Đạt | 24663071 | DHKTPM20A | Quy trình nhập hàng, Phân quyền người dùng, Xem lịch sử nhập hàng & thông tin nhân viên. | 100% |
| 3 | Trần Phúc An | 24664811 | DHKTPM20A | Thống kê doanh thu (tuần/tháng/năm), Quản lý nhân viên (ca làm, lương, lọc nâng cao), Quản lý khách hàng (tích điểm, lọc nâng cao). | 100% |
| 4 | Phùng Tùng Lâm | 24659221 | DHKTPM20A | Xử lý tích điểm khách hàng, Quản lý sản phẩm (lọc nâng cao), Quản lý nhà cung cấp (bộ lọc). | 100% |

---

## Thu thập và Phân tích yêu cầu

### Khảo sát thực trạng & Xác định bài toán
* **Mục tiêu:** Xây dựng ứng dụng quản lý nội bộ hỗ trợ bán hàng nhanh, quản lý nhân sự và kiểm soát kho hàng chặt chẽ, tối ưu hóa sơ đồ tổ chức.
* **Đối tượng sử dụng:**
  * **Nhân viên bán hàng:** Thực hiện nghiệp vụ tại quầy thanh toán.
  * **Người quản lý:** Điều hành hoạt động, quản lý nhân sự, kho hàng, xem thống kê doanh thu và có toàn quyền hệ thống.

### Quy trình nghiệp vụ chính
* **Bán hàng & Tích điểm:** Tra cứu khách hàng qua SĐT $\rightarrow$ Tích điểm tự động (100 VNĐ = 1 điểm) $\rightarrow$ Áp dụng giảm giá (tối đa 100,000 VNĐ/hóa đơn) $\rightarrow$ Thanh toán & Tự động trừ tồn kho $\rightarrow$ Xuất hóa đơn in file PDF.
* **Quản trị & Nhân sự:** Thêm/sửa/khóa tài khoản nhân viên, quản lý nhập kho (cập nhật số lượng, giá vốn, HSD), điều chỉnh hóa đơn sai sót và thống kê doanh thu theo Tuần/Tháng/Năm.

### Yêu cầu phi chức năng
* **Bảo mật:** Cơ chế đăng nhập phân quyền rõ ràng (Ẩn các chức năng nhạy cảm như Lương, Doanh thu đối với nhân viên).
* **Ràng buộc:** Tuổi > 0, Giá nhập/bán > 0. Nhân viên chỉ được thêm khách hàng, không được sửa/xóa nhằm tránh gian lận điểm.
* **Hiệu năng:** Tối ưu hóa câu truy vấn đảm bảo tốc độ phản hồi dưới 1 giây cho các tác vụ tại quầy.

---

