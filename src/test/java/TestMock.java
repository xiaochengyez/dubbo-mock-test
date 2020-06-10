import com.github.tomakehurst.wiremock.WireMockServer;
import com.gongxc.mock.MockUtil;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import static com.github.tomakehurst.wiremock.client.WireMock.*;
import static org.hamcrest.Matchers.equalTo;
import static com.github.tomakehurst.wiremock.core.WireMockConfiguration.wireMockConfig;
import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.containsString;


/**
 * <p>
 * mock测试
 * </p>
 *
 * @author gongxc
 * @since 2020/5/16
 */
public class TestMock {
    static WireMockServer wireMockServer;

    @BeforeAll
    static void beforeAll() {
        wireMockServer = new WireMockServer(wireMockConfig().port(8083)); //No-args constructor will start on port 8080, no HTTPS
        wireMockServer.start();
        configureFor("127.0.0.1", 8083);
        System.out.println("mock server start");
    }

    @AfterAll
    static void afterAll() {
        wireMockServer.stop();
    }


    @Test
    void stub() {
        stubFor(get(urlEqualTo("/stub")).willReturn(aResponse().withBody("stub demo")));

        given()
                .when().log().all().get("http://127.0.0.1:8083/stub")
                .then().log().all().body(containsString("stub"));

    }


    @Test
    void exactUrlOnly() {
        stubFor(get(urlEqualTo("/some/thing"))
                .willReturn(aResponse().withHeader("Content-Type", "text/plain").withBody("Hello world!")));
        given()
                .when().log().all().get("http://127.0.0.1:8083/some/thing")
                .then().log().all().body(containsString("Hello world"));

    }



    @Test
    void resJsons(){

        stubFor(get(urlEqualTo("/json"))
                .willReturn(aResponse().withHeader("content-type", "application/json")
                        .withBody("{\"result\":\"success\",\"message\":\"成功！\"}")));
        given()
                .when().log().all().get("http://127.0.0.1:8083/json")
                .then().log().all().body("message",equalTo("成功！"));

    }

    @Test
    void resJson1(){

        stubFor(get(urlEqualTo("/json"))
                .willReturn(aResponse().withHeader("content-type", "application/json")
                        .withBody("{\"result\":\"success\",\"message\":\"成功！\"}")));
        given()
                .when().log().all().get("http://127.0.0.1:8083/json")
                .then().log().all().body("message",equalTo("成功！"));

    }



    @Test
    void mock() throws InterruptedException {
        stubFor(get(urlMatching(".*")).atPriority(10).willReturn(aResponse().proxiedFrom("https://baidu.com")));
        given()
                .when().log().all().get("http://127.0.0.1:8083/1")
                .then().log().all().statusCode(200);

    }

    @Test
    void mocks(){
        MockUtil.mocking("you are a goog boy");
        given()
                .when().log().all().get("http://127.0.0.1:8084/1")
                .then().log().all().statusCode(200);
       MockUtil.stopMocking();

    }

    @Test
    void mocking(){
        MockUtil.mocking("{\"result\":\"success\",\"message\":\"成功！\"}");
        given()
                .when().log().all().get("http://127.0.0.1:8084/1")
                .then().log().all().statusCode(200)
                .body("result",equalTo("success"));
        MockUtil.stopMocking();

    }
}