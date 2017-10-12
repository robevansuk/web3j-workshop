package uk.robevans.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.web3j.protocol.Web3j;
import org.web3j.protocol.http.HttpService;
import uk.robevans.version.ClientVersion;

/**
 * Created on 12/10/17.
 */
@Configuration
@ComponentScan
public class ApplicationConfiguration {

    @Bean
    public Web3j getWeb3j(@Value("${ethereum.networkUrl}") String networkUrl) {
        return Web3j.build(new HttpService(networkUrl));
    }

    @Autowired
    @Bean
    public ClientVersion getClientVersion(Web3j client) {
        return new ClientVersion(client);
    }


}