package test;

import io.restassured.http.ContentType;
import io.restassured.response.Response;
import org.assertj.core.api.SoftAssertions;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import org.testcontainers.shaded.com.fasterxml.jackson.databind.ObjectMapper;
import org.testng.annotations.Test;


import java.io.IOException;
import java.io.InputStream;
import java.util.List;
import java.util.UUID;
import java.util.stream.Stream;

import static io.restassured.RestAssured.given;


public class MoMoPaymentApiTest extends BaseTest {


    // get data file JSON
    private static Stream<Arguments> getMomoPaymentData() throws IOException {
        // new JSONMapper() cho JSON
        ObjectMapper mapper = new ObjectMapper();
        //  new YAMLMapper() cho YAML

        InputStream inputStream = MoMoPaymentApiTest.class.getResourceAsStream("/momo_payment_data.json"); // Tên file

        // Đọc danh sách các đối tượng MomoTestCaseData từ JSON
        List<MomoCreatePaymentRequest> testCases = mapper.readValue(
                inputStream,
                mapper.getTypeFactory().constructCollectionType(List.class, MomoCreatePaymentRequest.class)
        );


        return testCases.stream().map(data -> Arguments.of(
                data.testCaseName,
                data.partnerCode,
                data.partnerName,
                data.storeId,
                data.orderInfo,
                data.redirectUrl,
                data.ipnUrl,
                data.lang,
                data.extraData,
                data.requestType,
                data.amount,
                data.expectedErrorCode,
                data.expectedMessage
        ));
    }

//    private static Stream<Arguments> getMomoPaymentDataYAML() throws IOException {
//        YAMLMapper mapperYAML = new YAMLMapper();
//
//        InputStream inputStreamYAML = MoMoPaymentApiTest.class.getResourceAsStream("/momo_payment_data.yaml");
//
//        // Đọc danh sách các đối tượng MomoTestCaseData từ YAML
//        List<MomoTestCaseData> testCasesYAML = mapperYAML.readValue(
//                inputStreamYAML,
//                mapperYAML.getTypeFactory().constructCollectionType(List.class, MomoTestCaseData.class)
//        );
//        return testCasesYAML.stream().map(data -> Arguments.of(
//                data.testCaseName,
//                data.partnerCode,
//                data.partnerName,
//                data.storeId,
//                data.orderInfo,
//                data.redirectUrl,
//                data.ipnUrl,
//                data.lang,
//                data.extraData,
//                data.requestType,
//                data.amount,
//                data.expectedErrorCode,
//                data.expectedMessage
//        ));
//    }

    // TESTCASE 1: THANH TOAN THANH CONG
    // TC_MOMO_CREATE_PAYMENT_001

    @ParameterizedTest(name = "{0}: Thanh toán với dữ liệu từ CSV")
    //@CsvFileSource(resources = "/momo_payment_data.csv", numLinesToSkip = 1)  // file CSV
    //MethodSource("getMomoPaymentDataYAML") // file YAML

    @MethodSource("getMomoPaymentData")  // file JSON
    @Test
       void testCreatePaymentSuccess(
            String testCaseName,
            String partnerCode,
            String partnerName,
            String storeId,
            String orderInfo,
            String redirectUrl,
            String ipnUrl,
            String lang,
            String extraData,
            String requestType,
            long amount,
            int expectedErrorCode,
            String expectedMessage
    ){
        // or use create parameter constructor
        String requestId = UUID.randomUUID().toString();
        String orderId = UUID.randomUUID().toString();


        //
        String rawHash = "accessKey=" + ACCESS_KEY +
                "&amount=" + amount +
                "&extraData=" + "JSON" + // extraData có thể rỗng hoặc chứa JSON
                "&orderId=" + orderId +
                "&orderInfo=" + orderInfo +
                "&partnerCode=" + PARTNER_CODE +
                "&redirectUrl=" + redirectUrl +
                "&ipnUrl=" + ipnUrl +
                "&requestId=" + requestId +
                "&requestType=" + requestType;

        String signature = generateSignature(rawHash); // Hàm tạo chữ ký THẬT SỰ của bạn

        // Request Body
        // Tạo Request Body bằng POJO
        MomoCreatePaymentRequest requestBody = new MomoCreatePaymentRequest();
        requestBody.testCaseName = testCaseName;
        requestBody.partnerCode = partnerCode;
        requestBody.partnerName = partnerName;
        requestBody.storeId = storeId;
        requestBody.requestId = requestId;
        requestBody.amount = amount;
        requestBody.orderId = orderId;
        requestBody.orderInfo = orderInfo;
        requestBody.redirectUrl = redirectUrl;
        requestBody.ipnUrl = ipnUrl;
        requestBody.lang = lang;
        requestBody.extraData = extraData;
        requestBody.requestType = requestType;
        requestBody.signature = signature;

        Response response = given()
                .contentType(ContentType.JSON)
                .body(requestBody)
                .when()
                .post("/createPayment");

        // Khởi tạo SoftAssertions
        SoftAssertions softly = new SoftAssertions();

        // Always assert status code first, this is typically a hard assertion if crucial.
        // But for MoMo (often 200 even on logical errors), you can soft assert it too.
        softly.assertThat(response.statusCode())
                .as("check Status Code")
                .isEqualTo(200);

        // Soft Assertions cho các trường body
        softly.assertThat(response.jsonPath().getInt("errorCode"))
                .as("check errorCode")
                .isEqualTo(expectedErrorCode);

        softly.assertThat(response.jsonPath().getString("message"))
                .as("check message")
                .isEqualTo(expectedMessage);

        // Chỉ check các trường đặc trưng cho thành công khi errorCode là 0
        if (expectedErrorCode == 0) {
            softly.assertThat(response.jsonPath().getString("payUrl"))
                    .as("check payUrl")
                    .isNotNull(); // Hoặc isNotBlank() nếu bạn muốn đảm bảo nó không rỗng

            softly.assertThat(response.jsonPath().getString("requestId"))
                    .as("check requestId")
                    .isEqualTo(requestId);

            softly.assertThat(response.jsonPath().getString("orderId"))
                    .as("check orderId")
                    .isEqualTo(orderId);
        }

        // end
        softly.assertAll();
    }

}