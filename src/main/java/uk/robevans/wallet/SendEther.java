package uk.robevans.wallet;

import org.web3j.crypto.CipherException;
import org.web3j.crypto.Credentials;
import org.web3j.crypto.WalletUtils;
import org.web3j.protocol.Web3j;
import org.web3j.protocol.core.methods.response.TransactionReceipt;
import org.web3j.tx.RawTransactionManager;
import org.web3j.tx.TransactionManager;
import org.web3j.tx.Transfer;

import java.io.IOException;
import java.math.BigDecimal;
import java.math.MathContext;
import java.math.RoundingMode;
import java.util.Optional;
import java.util.concurrent.ExecutionException;

import static java.util.Optional.empty;
import static org.web3j.tx.Contract.GAS_LIMIT;
import static org.web3j.tx.ManagedTransaction.GAS_PRICE;
import static org.web3j.utils.Convert.Unit.ETHER;

/**
 * Created by uk.robevans on 12/10/2017.
 */
public class SendEther {

    private Web3j client;

    String pathToWallet;

    String walletPassword;

    Optional<Credentials> credentials = empty();

    public SendEther(Web3j client, String pathToWallet, String walletPassword) {
        this.client = client;
        this.pathToWallet = pathToWallet;
        this.walletPassword = walletPassword;
    }

    public void sendSomeEther(String toAddress){
        try {
            System.out.println("Making payment...");
            credentials = Optional.of(WalletUtils.loadCredentials(walletPassword, pathToWallet));
        } catch (IOException e) {
            e.printStackTrace();
        } catch (CipherException e) {
            e.printStackTrace();
        }
        BigDecimal amount = new BigDecimal(1, new MathContext(8, RoundingMode.HALF_UP));
        Optional<TransactionReceipt> maybeSentEther = makePayment(amount, toAddress);
        if (!maybeSentEther.isPresent()) {
            System.out.println("Sending ether to '" + toAddress + "' FAILED!");
        }
    }

    public Optional<TransactionReceipt> makePayment(BigDecimal amount, String toAddress) {
        if (credentials.isPresent()) {
            try {
                TransactionManager tm = new RawTransactionManager(client, credentials.get());

                Transfer transfer = new Transfer(client, tm);

                Optional<TransactionReceipt> transactionReceipt = Optional.of(transfer.sendFundsAsync(toAddress,
                        amount,
                        ETHER,
                        GAS_PRICE,
                        GAS_LIMIT).get());

                if (transactionReceipt.isPresent()) {
                    System.out.println("Funds transfer completed..." + transactionReceipt.get().getBlockHash());
                    return transactionReceipt;
                } else {
                    System.out.println("Sorry your transfer failed.");
                }
            } catch (InterruptedException | ExecutionException e) {
                System.out.println("God DAMMIT! " + e.getCause());
            }
        }
        return empty();
    }
}
