# Ngôn ngữ sử dụng là Tiếng Việt
# language: vi

Feature: Tính khuyến mãi cho giỏ hàng
  Để tăng sự hài lòng của khách hàng thân thiết, hệ thống cần tính toán
  chiết khấu tự động dựa trên loại khách hàng và giá trị đơn hàng.

  Scenario: Khách hàng VIP được giảm giá 10% khi mua hàng trên 2,000,000 VND
    Given một khách hàng là loại "VIP" đang thực hiện thanh toán
    And giỏ hàng của khách có tổng giá trị là 2500000 đồng
    When người dùng yêu cầu tính toán giá cuối cùng
    Then hệ thống sẽ trả về giá cuối cùng là 2250000 đồng