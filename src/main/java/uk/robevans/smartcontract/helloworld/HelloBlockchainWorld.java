package uk.robevans.smartcontract.helloworld;

import org.web3j.abi.datatypes.Utf8String;
import org.web3j.crypto.Credentials;
import org.web3j.example.generated.Greeter;
import org.web3j.protocol.Web3j;

import javax.annotation.PostConstruct;
import java.math.BigInteger;
import java.util.concurrent.ExecutionException;

import static org.web3j.tx.Contract.GAS_LIMIT;

/**
 * Created by uk.robevans on 13/10/2017.
 */
public class HelloBlockchainWorld {

    Web3j client;
    Credentials credentials;

    public HelloBlockchainWorld(Web3j client, Credentials credentials) {
        this.client = client;
        this.credentials = credentials;
    }

    @PostConstruct
    public void deploy() {
        Greeter contract  = null;
        try {
            try {
                contract = Greeter.deploy(client,
                        credentials,
                        BigInteger.ONE,
                        GAS_LIMIT,
                        BigInteger.ONE,
                        new Utf8String("Hello blockchain world!")).get();
            } catch (ExecutionException ex) {
                if (ex.getCause().getMessage().contains("")) {
                    // TODO Get the contract ID and try to obtain a reference to it ready for the next steps.
                }
            }

            Utf8String greeting = contract.greet().get();
            System.out.println("The greeting found in the blockchain smart contract was: '" + greeting.getTypeAsString() + "'");

//            System.out.println("Updating the greeting to : '" + newGreeting + "'");

        } catch( InterruptedException | ExecutionException ex ) {
            ex.printStackTrace();
        }
    }
}
