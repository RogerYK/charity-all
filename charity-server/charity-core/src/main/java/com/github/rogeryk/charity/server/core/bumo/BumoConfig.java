package com.github.rogeryk.charity.server.core.bumo;

import jdk.internal.util.xml.impl.ReaderUTF8;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import io.bumo.SDK;

import java.io.InputStream;
import java.io.Reader;
import java.nio.charset.StandardCharsets;

@Configuration
@Slf4j
public class BumoConfig {
    private String feeAccountAddress="buQYzuZW8XoeAGqsh7TKqAXYUWfswhPDocjA";

    private String feeAccountPrivateKey="privbwwBPvX4zwDh1nZeiEcKNRLisN5ht4hVTwwuB6BMd3n17bk84qDz";

    private String assetCode = "CHY";

    private String assetIssuer = "buQYzuZW8XoeAGqsh7TKqAXYUWfswhPDocjA";

    private String crowdFundingContract;

    public BumoConfig() {
        try (InputStream stream = BumoConfig.class.getResourceAsStream("contract/crowdfunding.js")) {
            int n = stream.available();
            byte[] buf = new byte[n];
            stream.read(buf);
            crowdFundingContract = new String(buf, StandardCharsets.UTF_8);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Bean
    public SDK getSDK() {
        return SDK.getInstance("http://seed1.bumotest.io:26002");
    }

    public String getFeeAccountAddress() {
        return feeAccountAddress;
    }

    public String getFeeAccountPrivateKey() {
        return feeAccountPrivateKey;
    }

    public String getAssetCode() {
        return assetCode;
    }

    public String getAssetIssuer() {
        return assetIssuer;
    }
    public String getCrowdFundingContract() {
        return crowdFundingContract;
    }
}
