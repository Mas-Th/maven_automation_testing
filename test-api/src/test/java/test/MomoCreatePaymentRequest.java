package test;

public class MomoCreatePaymentRequest {
    public String testCaseName;
    public String partnerCode;
    public String partnerName;
    public String storeId;
    public String requestId;
    public long amount;
    public String orderId;
    public String orderInfo;
    public String redirectUrl;
    public String ipnUrl;
    public String lang;
    public String extraData;
    public String requestType;
    public String signature; // Chữ ký này thường được tạo ra trong test, không đọc từ CSV
    public Object expectedErrorCode;
    public Object expectedMessage;

    public MomoCreatePaymentRequest(){};
    // Constructor cho các trường bạn muốn đọc từ CSV
    // Lưu ý: signature thường được tính toán trong test, không truyền vào từ CSV
    public MomoCreatePaymentRequest(String testCaseName,
                                    String partnerCode,
                                    String partnerName,
                                    String storeId,
                                    String orderInfo,
                                    String redirectUrl,
                                    String ipnUrl,
                                    String lang,
                                    String extraData,
                                    String requestType,
                                    long amount) {
        this.testCaseName = testCaseName;
        this.partnerCode = partnerCode;
        this.partnerName = partnerName;
        this.storeId = storeId;
        this.orderInfo = orderInfo;
        this.redirectUrl = redirectUrl;
        this.ipnUrl = ipnUrl;
        this.lang = lang;
        this.extraData = extraData;
        this.requestType = requestType;
        this.amount = amount;
        // requestId và orderId sẽ được tạo động trong test
    }
}
