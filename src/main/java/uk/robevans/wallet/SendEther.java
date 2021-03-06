package uk.robevans.wallet;

import org.web3j.crypto.Credentials;
import org.web3j.protocol.Web3j;
import org.web3j.protocol.core.methods.response.TransactionReceipt;
import org.web3j.tx.RawTransactionManager;
import org.web3j.tx.TransactionManager;
import org.web3j.tx.Transfer;

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

    private final Web3j client;
    private final Credentials credentials;

    public SendEther(Web3j client, Credentials credentials) {
        this.client = client;
        this.credentials = credentials;
    }

    public void sendSomeEther(String toAddress){
        System.out.println("Making payment...");

        BigDecimal amount = new BigDecimal(1, new MathContext(8, RoundingMode.HALF_UP));
        Optional<TransactionReceipt> maybeSentEther = makePayment(amount, toAddress);
        if (!maybeSentEther.isPresent()) {
            System.out.println("Sending ether to '" + toAddress + "' FAILED!");
        }
    }

    /**
     * Only uncomment this code if you want to continuously send ether to the defined address. Otherwise
     * leave it commented out or you'll need to get more Ether from the Faucet:
     *
     *
     *
     * @param amount
     * @param toAddress
     * @return
     */
    public Optional<TransactionReceipt> makePayment(BigDecimal amount, String toAddress) {
//        if (credentials != null) {
//            try {
//                TransactionManager tm = new RawTransactionManager(client, credentials);
//
//                Transfer transfer = new Transfer(client, tm);
//
//                Optional<TransactionReceipt> transactionReceipt = Optional.of(transfer.sendFundsAsync(toAddress,
//                        amount,
//                        ETHER,
//                        GAS_PRICE,
//                        GAS_LIMIT).get());
//
//                if (transactionReceipt.isPresent()) {
//                    System.out.println("Funds transfer completed..." + transactionReceipt.get().getBlockHash());
//                    return transactionReceipt;
//                }
//            } catch (InterruptedException | ExecutionException e) {
//                System.out.println("God DAMMIT! " + e.getCause());
//            }
//        }
//        System.out.println("Transaction failed but no exception thrown???");
        return empty();
    }
}
