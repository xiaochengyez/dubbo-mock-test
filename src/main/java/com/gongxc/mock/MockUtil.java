package com.gongxc.mock;

import com.github.tomakehurst.wiremock.WireMockServer;

import static com.github.tomakehurst.wiremock.client.WireMock.*;
import static com.github.tomakehurst.wiremock.core.WireMockConfiguration.wireMockConfig;

/**
 * <p>
 * util
 * </p>
 *
 * @author gongxc
 * @date 2020/5/16
 */

public class MockUtil {
    static WireMockServer wireMockServer;
    public static void mocking (String response) {
        wireMockServer = new WireMockServer(wireMockConfig().port(8084));
        wireMockServer.start();
        configureFor("127.0.0.1", 8084);
        stubFor(get(urlMatching(".*"))
                .willReturn(aResponse().withHeader("content-type", "application/json")
                        .withBody(response)));

    }

    public static void stopMocking() {
        wireMockServer.stop();
        System.out.println("mock server stop");
    }
}
