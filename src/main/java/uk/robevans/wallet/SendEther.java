package uk.robevans.wallet;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.web3j.crypto.CipherException;
import org.web3j.crypto.Credentials;
import org.web3j.crypto.WalletUtils;
import org.web3j.protocol.Web3j;
import org.web3j.protocol.core.methods.response.TransactionReceipt;
import org.web3j.protocol.exceptions.TransactionTimeoutException;

import javax.annotation.PostConstruct;
import java.io.IOException;
import java.math.BigDecimal;
import java.util.Optional;
import java.util.concurrent.ExecutionException;

import static java.util.Optional.empty;
import static org.web3j.tx.Transfer.sendFundsAsync;
import static org.web3j.utils.Convert.Unit.ETHER;

/**
 * Created by uk.robevans on 12/10/2017.
 */
public class SendEther {

    Web3j client;

    Optional<Credentials> credentials;

    @Autowired
    public SendEther(Web3j client,
                     @Value("${ethereum.wallet.dir}") String pathToWallet,
                     @Value("${ethereum.wallet.password}") String walletPassword) {
        Optional<Credentials> credentials = empty();
        try {
            credentials = Optional.of(WalletUtils.loadCredentials(walletPassword, pathToWallet));
        } catch (IOException e) {
            e.printStackTrace();
        } catch (CipherException e) {
            e.printStackTrace();
        }
    }

    @PostConstruct
    public void sendSomeEther(String toAddress){
        Double amount = new Double(0.001);
        Optional<TransactionReceipt> maybeSentEther = sendEther(amount, "0x1e87ba20e81df142b2f93e0e6c5992d677572fec");
        if (!maybeSentEther.isPresent()) {
            System.out.println("God Dammit! Sending ether to '0x1e87ba20e81df142b2f93e0e6c5992d677572fec' did not work!");
        }
    }

    public Optional<TransactionReceipt> sendEther(Double amount, String toAddress) {
        if (credentials.isPresent()) {
            try {
                Optional<TransactionReceipt> transactionReceipt = Optional.of(sendFundsAsync(
                        client,
                        credentials.get(),
                        toAddress,
                        BigDecimal.valueOf(amount),
                        ETHER).get());

                if (transactionReceipt.isPresent()) {
                    System.out.println("Funds transfer completed..." + transactionReceipt.get().getBlockHash());
                    return transactionReceipt;
                } else {
                    System.out.println("Sorry your transfer failed.");
                }
            } catch (InterruptedException e) {
                e.printStackTrace();
            } catch (ExecutionException e) {
                e.printStackTrace();
            } catch (TransactionTimeoutException e) {
                e.printStackTrace();
            }
        }
        return empty();
    }
}
