package test;

import io.restassured.RestAssured;
import org.testng.annotations.BeforeClass;

import javax.crypto.Mac;
import javax.crypto.spec.SecretKeySpec;
import java.nio.charset.StandardCharsets;
import java.util.Base64;

public class BaseTest {
    // API
    private static final String MOCK_MOMO_API_BASE_URI= "MOCK_MOMO_API_BASE_URI";
    private static final String MOCK_MOMO_API_BASE_PATH = "MOCK_MOMO_API_BASE_PATH";

    // MERCHANT/PARTNER
    protected static final String PARTNER_CODE ="PARTNER_CODE";
    protected static final String ACCESS_KEY="ACCESS_KEY";
    protected static final String SECRET_KEY="SECRET_KEY";


    @BeforeClass
    public static void setup(){
        RestAssured.baseURI = MOCK_MOMO_API_BASE_URI;
        RestAssured.basePath = MOCK_MOMO_API_BASE_PATH;
        RestAssured.useRelaxedHTTPSValidation();

        RestAssured.filters(
                new io.restassured.filter.log.RequestLoggingFilter(),
                new io.restassured.filter.log.ResponseLoggingFilter()
        );
    }

    protected String generateSignature(String rawMessage){
        try {
            Mac sha256_HMAC = Mac.getInstance("HmacSHA256");
            SecretKeySpec secret_key_spec = new SecretKeySpec(BaseTest.SECRET_KEY.getBytes(StandardCharsets.UTF_8), "HmacSHA256");
            sha256_HMAC.init(secret_key_spec);
            byte[] hash = sha256_HMAC.doFinal(rawMessage.getBytes(StandardCharsets.UTF_8));
            // MoMo thường yêu cầu Hex String, không phải Base64 cho chữ ký.
            // Cần kiểm tra lại tài liệu MoMo của bạn để chắc chắn là Base64 hay Hex
            // Nếu là Hex, bạn cần một thư viện như Apache Commons Codec (Hex.encodeHexString)
            // Ví dụ: return Hex.encodeHexString(hash);
            return Base64.getEncoder().encodeToString(hash); // Giả định là Base64 cho ví dụ này
        } catch (Exception e) {
            throw new RuntimeException("Failed to generate HMAC-SHA256 signature", e);
        }
    }
}
