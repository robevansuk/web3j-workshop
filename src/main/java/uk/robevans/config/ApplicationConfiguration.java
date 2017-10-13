package uk.robevans.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.util.StringUtils;
import org.web3j.crypto.CipherException;
import org.web3j.crypto.Credentials;
import org.web3j.crypto.WalletUtils;
import org.web3j.protocol.Web3j;
import org.web3j.protocol.http.HttpService;
import uk.robevans.smartcontract.helloworld.HelloBlockchainWorld;
import uk.robevans.version.ClientVersion;
import uk.robevans.wallet.SendEther;

import java.io.IOException;

/**
 * Created on 12/10/17.
 */
@Configuration
@ComponentScan
@PropertySource("classpath:application.properties")
public class ApplicationConfiguration {

    @Bean
    public Web3j getWeb3j(@Value("${ethereum.network_url:}") String networkUrl) {
        return Web3j.build(new HttpService(networkUrl));
    }

    @Bean
    public ClientVersion getClientVersion() {
        return new ClientVersion();
    }

    @Bean
    public Credentials getCredentials(@Value("${ethereum.wallet.dir}") String pathToWallet,
                                      @Value ("${ethereum.wallet.password}") String walletPassword) throws IOException, CipherException {
        if (StringUtils.isEmpty(pathToWallet)){
            throw new IllegalArgumentException("Wallet path must be specified but was '" + pathToWallet + "'");
        }
        if (StringUtils.isEmpty(walletPassword)) {
            throw new IllegalArgumentException("Wallet Password must be specified: '" + walletPassword + "'");
        }

        return  WalletUtils.loadCredentials(walletPassword, pathToWallet);

    }

    @Autowired
    @Bean
    public HelloBlockchainWorld getHelloBlockchainWorld(Web3j client, Credentials credentials) {
        return new HelloBlockchainWorld(client, credentials);
    }

    @Autowired
    @Bean
    public SendEther getSendEther(Web3j client, Credentials credentials){
        SendEther sendEther = new SendEther(client, credentials);
        sendEther.sendSomeEther("0x1e87ba20e81df142b2f93e0e6c5992d677572fec");
        return sendEther;
    }


}