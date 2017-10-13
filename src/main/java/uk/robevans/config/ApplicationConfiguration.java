package uk.robevans.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.web3j.protocol.Web3j;
import org.web3j.protocol.http.HttpService;
import uk.robevans.version.ClientVersion;
import uk.robevans.wallet.SendEther;

/**
 * Created on 12/10/17.
 */
@Configuration
@ComponentScan
@PropertySource("classpath:application.properties")
public class ApplicationConfiguration {

    @Value("${ethereum.network_url}")
    String networkUrl;

    @Bean
    public Web3j getWeb3j() {
        return Web3j.build(new HttpService(networkUrl));
    }

    @Bean
    public ClientVersion getClientVersion() {
        return new ClientVersion();
    }

    @Autowired
    @Bean
    public SendEther getSendEther(Web3j client,
                                  @Value("${ethereum.wallet.dir}") String pathToWallet,
                                  @Value ("${ethereum.wallet.password}") String walletPassword){
        SendEther sendEther = new SendEther(client, pathToWallet, walletPassword);
        sendEther.sendSomeEther("0x1e87ba20e81df142b2f93e0e6c5992d677572fec");
        return sendEther;
    }


}