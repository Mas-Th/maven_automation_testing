📌 Tiến độ Dự án Spring Boot — PROGRESS.md
✅ Đã hoàn thành | ⬜ Chưa hoàn thành | ⚠️ Cần cải tiến thêm

✅ Yêu cầu chung cho mỗi Service (Mức tối thiểu & chuyên nghiệp)

Thành phần	    Mức tối thiểu   	                Mức chuyên nghiệp (gợi ý tiếp theo)
Entity  	    Có đầy đủ trường và annotation JPA  	Validation logic, Audit fields (createdAt, updatedAt)
Repository  	Interface kế thừa JpaRepository 	    Tùy biến query với @Query, Specification
DTO	            DTO request/response    	            Mapping logic rõ ràng, ModelMapper hoặc thủ công sạch sẽ
Service Interface	        Khai báo chức năng chính    Phân tách IService và Impl, xử lý business rõ ràng
Service Implementation	    Đáp ứng chức năng	        Bắt lỗi nghiệp vụ, logging 5W1H, xử lý ngoại lệ riêng biệt
Controller      	        Tạo endpoint	            Response chuẩn hóa, xử lý ExceptionHandler, status code
Unit Test	                Chưa bắt buộc	            Test cho service, controller (Mockito, JUnit5)
Security (nếu có)	        Không yêu cầu ban đầu	    JWT/Session, role-based access
Documentation	            README.md + Swagger	API versioning, OpenAPI chuẩn hóa mô tả

🧩 Service 1: User Service
✅ Tiến độ Hiện tại
UserEntity

UserRepository

UserService:

Đăng ký người dùng

Đăng nhập người dùng

UserController

POST /api/users/register

POST /api/users/login

🧠 Gợi ý yêu cầu tiếp theo
Áp dụng BCrypt để mã hóa mật khẩu

Đăng nhập dùng JWT hoặc Session

Viết DTO UserLoginRequest, UserLoginResponse

Xử lý lỗi username/email tồn tại

Swagger cho endpoint /login

Unit test UserService.login

Tách AuthController riêng nếu hệ thống lớn

🛍 Service 2: Product Service
✅ Tiến độ Hiện tại
ProductEntity

ProductRepository

ProductService

ProductController

🧠 Gợi ý yêu cầu tiếp theo
Viết DTO ProductDtoRequest, ProductDtoResponse

Tạo các chức năng:

Thêm sản phẩm

Cập nhật sản phẩm

Xem chi tiết sản phẩm

Xóa sản phẩm

Lọc sản phẩm theo tên/giá

Swagger mô tả rõ các field sản phẩm

Validation bằng annotation @NotBlank, @Positive

Tách AdminProductController nếu có phân quyền

📦 Service 3: Order Service
✅ Tiến độ Hiện tại
OrderEntity

OrderRepository

OrderService

OrderController

🧠 Gợi ý yêu cầu tiếp theo
Liên kết User và Product bằng khóa ngoại

DTO: OrderRequest, OrderResponse

Tạo các chức năng:

Tạo đơn hàng từ danh sách sản phẩm

Tính tổng tiền tự động

Xem đơn hàng theo user

Hủy đơn hàng

Áp dụng @Transactional cho logic đặt hàng

Log nghiệp vụ với logger

Test service đặt hàng nhiều sản phẩm

🧪 Khuyến nghị bổ sung chuyên nghiệp
Chủ đề	Gợi ý triển khai
Global Exception	Tạo @ControllerAdvice để xử lý lỗi toàn cục
Standard Response	Wrapper ResponseResult<T> hoặc ApiResponse<T> để chuẩn hóa phản hồi
Logging	Sử dụng Slf4j, chuẩn 5W1H để trace dễ dàng
Swagger	Tích hợp springdoc-openapi hoặc swagger-ui
Configuration Split	Tách application-dev.yml, application-prod.yml
Docker	Dockerfile, docker-compose cho app + PostgreSQL
Security	Bổ sung Spring Security, JWT hoặc Session
CI/CD	Thêm GitHub Actions / GitLab CI / Jenkins