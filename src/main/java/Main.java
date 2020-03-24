import com.hedera.hashgraph.sdk.Client;
import com.hedera.hashgraph.sdk.HederaStatusException;
import com.hedera.hashgraph.sdk.account.AccountId;
import com.hedera.hashgraph.sdk.crypto.ed25519.Ed25519PrivateKey;
import com.hedera.hashgraph.sdk.crypto.ed25519.Ed25519PublicKey;
import io.github.cdimascio.dotenv.Dotenv;

import java.util.Objects;
import java.util.Scanner;

public class Main {

    private static final AccountId OPERATOR_ID = AccountId.fromString(Objects.requireNonNull(Dotenv.load().get("OPERATOR_ID")));
    private static final Ed25519PrivateKey OPERATOR_KEY = Ed25519PrivateKey.fromString(Objects.requireNonNull(Dotenv.load().get("OPERATOR_KEY")));
    private static final Ed25519PublicKey OPERATOR_PUBLIC_KEY = Ed25519PublicKey.fromString(Objects.requireNonNull(Dotenv.load().get("OPERATOR_PUBLIC_KEY")));
    private static final String MIRROR_NODE_ADDRESS = Objects.requireNonNull(Dotenv.load().get("MIRROR_NODE_ADDRESS"));

    public static void main(String[] args) throws HederaStatusException {
        System.out.println("Your account: "+OPERATOR_ID);
        System.out.println("Your public key: "+OPERATOR_PUBLIC_KEY);

        //Configure a client for Hedera testnet access
        Client client = Client.forTestnet();

        // Defaults the operator account ID and key such that all generated transactions will be paid for
        // by this account and be signed by this key
        client.setOperator(OPERATOR_ID,OPERATOR_KEY); //now this is the operator

        // Build the mirror node client
        // final MirrorClient mirrorClient = new MirrorClient(MIRROR_NODE_ADDRESS);

        // Let's try to do a cryptocurrency transaction first
        transaction mytransaction = new transaction();

        //Insert the recipient account id
        System.out.println("Insert the recipient account:");
        Scanner inputrecipient = new Scanner(System.in);
        String recipientIdst = inputrecipient.nextLine();
        AccountId recipientId = AccountId.fromString(recipientIdst);
        //Insert the amount you want to transfer
        System.out.println("Insert the amount of Hbar (in tiny Hbar):");
        Scanner inputamount = new Scanner(System.in);
        long amount = inputamount.nextLong();
        //Insert the memo of your transaction
        System.out.println("Write the transaction memo:");
        Scanner inputmemo = new Scanner(System.in);
        String memo = inputmemo.nextLine();

        mytransaction.sendHbar(OPERATOR_ID,recipientId,client,amount,memo);

    }
}
