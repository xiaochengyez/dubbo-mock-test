package com.gongxc.mock;

import com.github.tomakehurst.wiremock.WireMockServer;

import static com.github.tomakehurst.wiremock.client.WireMock.*;
import static com.github.tomakehurst.wiremock.client.WireMock.aResponse;
import static com.github.tomakehurst.wiremock.core.WireMockConfiguration.wireMockConfig;

/**
 * <p>
 * ServerMock
 * </p>
 *
 * @author gongxc
 * @date 2020/5/16
 */

public class ServerStub {
    public static void main(String[] args) throws Exception {
        WireMockServer wireMockServer = new WireMockServer(wireMockConfig().port(8083));
        wireMockServer.start();
        configureFor("127.0.0.1", 8083);
        stubFor(get(urlMatching(".*")).willReturn(aResponse().withBody("you are a good man")));
    }
}
