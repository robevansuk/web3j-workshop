package uk.robevans.version;

import org.springframework.beans.factory.annotation.Autowired;
import org.web3j.protocol.Web3j;
import org.web3j.protocol.core.methods.response.NetVersion;

import javax.annotation.PostConstruct;
import java.io.IOException;

/**
 * Created by uk.robevans on 12/10/2017.
 */
public class ClientVersion {

    @Autowired
    private Web3j client;

    @PostConstruct
    public void getTheVersion() {
        try {
            getClientVersion();
        } catch (IOException ex) {
            System.out.println("ERROR ... ");
            ex.printStackTrace();
        }
    }

    public void getClientVersion() throws IOException {
        NetVersion version = client.netVersion().send();
        System.out.println("NetVersion: " + version.getNetVersion());
    }
}
