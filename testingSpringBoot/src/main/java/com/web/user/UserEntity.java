package com.web.user;

import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

import java.util.Objects;

@Entity
@Table(name = "users") // ✅ Ánh xạ entity này vào bảng "users" trong DB
public class UserEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY) // ✅ ID sẽ tự động tăng (auto-increment)
    private Long id;

    @NotBlank(message = "Tên đăng nhập không được để trống") // ✅ Không được null hoặc rỗng (chỉ chứa khoảng trắng)
    @Size(min = 4, max = 50, message = "Tên đăng nhập phải từ 4 đến 50 ký tự") // ✅ Giới hạn độ dài
    @Column(nullable = false, unique = true, length = 50) // ✅ DB: không null, không trùng, max 50 ký tự
    private String username;

    @NotBlank(message = "Email không được để trống")
    @Email(message = "Định dạng email không hợp lệ") // ✅ Kiểm tra định dạng email
    @Column(nullable = false, unique = true) // ✅ Email là duy nhất và bắt buộc
    private String email;

    @NotBlank(message = "Mật khẩu không được để trống")
    @Column(nullable = false) // ✅ DB bắt buộc có giá trị
    private String password;

    // ========================== Constructors ==========================

    /**
     * ✅ Constructor mặc định – bắt buộc phải có cho JPA hoạt động (Hibernate sử dụng khi truy vấn DB).
     */
    public UserEntity() {
    }

    /**
     * ✅ Constructor tiện lợi để tạo mới user (không cần truyền ID vì DB sẽ tự sinh).
     */
    public UserEntity(String username, String email, String password) {
        this.username = username;
        this.email = email;
        this.password = password;
    }

    /**
     * ✅ Constructor đầy đủ – dùng khi muốn tạo user với ID cụ thể (ít dùng).
     */
    public UserEntity(Long id, String username, String email, String password) {
        this.id = id;
        this.username = username;
        this.email = email;
        this.password = password;
    }

    // ========================== Getters & Setters ==========================

    // ✅ Getter & Setter cho JPA và các framework khác sử dụng
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    // ========================== Business Logic ==========================

    /**
     * ✅ Cập nhật thông tin user từ request DTO.
     * Chỉ cập nhật nếu giá trị mới khác giá trị hiện tại – tránh ghi đè không cần thiết.
     * Đây là một dạng "domain method" – giữ logic cập nhật ngay trong entity.
     */
    public void updateFromRequest(UserDtoRequest req) {
        if (!Objects.equals(this.username, req.getUsername())) {
            this.username = req.getUsername();
        }

        if (!Objects.equals(this.email, req.getEmail())) {
            this.email = req.getEmail();
        }
        if (!Objects.equals(this.password, req.getPassword())) {
            this.password = req.getPassword();
        }
        // ⚠ Có thể thêm điều kiện cập nhật cho các field khác như password, avatar, address...
    }

    public boolean updateViolationUser(UserDtoRequest req) {
        boolean changed = false;

        if (!Objects.equals(this.username, req.getUsername())) {
            this.username = req.getUsername();
            changed = true;
        }

        if (!Objects.equals(this.email, req.getEmail())) {
            this.email = req.getEmail();
            changed = true;
        }

        if (!Objects.equals(this.password, req.getPassword())) {
            this.password = req.getPassword();
            changed = true;
        }

        return changed;
    }

}
