package cryptooperations;

import android.content.Context;
import android.content.SharedPreferences;

import org.bouncycastle.jce.provider.BouncyCastleProvider;
import org.web3j.crypto.ECKeyPair;
import org.web3j.crypto.Keys;
import org.web3j.crypto.Credentials;
import org.web3j.utils.Convert;


import java.math.BigInteger;
import java.security.Provider;
import java.security.Security;
import java.util.HashMap;
import java.util.Map;

public class KeyGenerator {

    private SharedPreferences sharedPreferences;

    public static final String PRIVATE_KEY = "private_key";
    public static final String PUBLIC_KEY = "public_key";
    public static final String ADDRESS = "address";
    public static final String TEMP_PRIV_KEY = "temp_priv_key";
    public static final String TEMP_PUB_KEY = "temp_pub_key";
    public static final String TEMP_ADDRESS = "temp_address";
    public static final String BALANCE = "my_balance";
    public static final String CHIP_BALANCE = "my_chips_balance";
    public static final String SPIN_RESULT_1 = "spin_result_1";
    public static final String SPIN_RESULT_2 = "spin_result_2";
    public static final String SPIN_RESULT_3 = "spin_result_3";



    public KeyGenerator(Context context) {
        initializeBouncyCastle();
        sharedPreferences = context.getSharedPreferences("wallet_prefs", Context.MODE_PRIVATE);

    }

    public void saveKeysTemporarily(Map<String, String> map) {
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString(TEMP_PRIV_KEY, map.get("privateKey"));
        editor.putString(TEMP_PUB_KEY, map.get("publicKey"));
        editor.putString(TEMP_ADDRESS, map.get("address"));
        editor.commit();
    }

    private void deleteTemporaryKeys() {
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.remove(TEMP_PRIV_KEY);
        editor.remove(TEMP_PUB_KEY);
        editor.remove(TEMP_ADDRESS);
        editor.commit();
    }

    public Map<String, String> getTemporaryWallet() {
           String privKey = sharedPreferences.getString(TEMP_PRIV_KEY, null);
           String pubKey = sharedPreferences.getString(TEMP_PUB_KEY, null);
           String address = sharedPreferences.getString(TEMP_ADDRESS, null);

           Map<String, String> result = new HashMap<>();
           result.put("privateKey", privKey);
           result.put("address", Keys.toChecksumAddress(address));
           result.put("publicKey", pubKey);

           this.deleteTemporaryKeys();

           return result;
    }

    public Map<String, String> GenerateWallet() throws Exception {
        Map<String, String> result = new HashMap<>();

            ECKeyPair ecKeyPair = Keys.createEcKeyPair();
            String privateKey = ecKeyPair.getPrivateKey().toString(16);
            String publicKey = ecKeyPair.getPublicKey().toString(16);
            String address = Keys.getAddress(ecKeyPair);
            result = new HashMap<>();
            result.put("privateKey", privateKey);
            result.put("address", Keys.toChecksumAddress(address));
            result.put("publicKey", publicKey);

        return result;
    }

    public void store(Map<String, String> details) {
        this.saveKeys(details.get("privateKey"), details.get("publicKey"), details.get("address"));
    }

    public boolean validateAndStoreKey(String privateKeyHex)  {
        // Check if the private key is a valid hexadecimal string
        if (!privateKeyHex.matches("^(0x)?[0-9a-fA-F]{64}$")) {
           return false;
        }

        // Remove "0x" prefix if present
        if (privateKeyHex.startsWith("0x") || privateKeyHex.startsWith("0X")) {
            privateKeyHex = privateKeyHex.substring(2);
        }

        try {
            // Create credentials from the private key
            Credentials credentials = Credentials.create(privateKeyHex);
            String address = credentials.getAddress();
            this.saveKeys(privateKeyHex, credentials.getEcKeyPair().getPublicKey().toString(16), Keys.toChecksumAddress(address));

            return true;
        } catch (Exception e) {
            return false;
        }
    }



    private void initializeBouncyCastle() {
        final Provider provider = Security.getProvider(BouncyCastleProvider.PROVIDER_NAME);
        if (provider == null) {
            // Register Bouncy Castle provider if not already registered
            Security.addProvider(new BouncyCastleProvider());
        } else {
            // Ensure Bouncy Castle is the preferred provider
            Security.removeProvider(BouncyCastleProvider.PROVIDER_NAME);
            Security.addProvider(new BouncyCastleProvider());
        }
    }
    public void saveKeys(String privateKey, String publicKey, String address) {
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString(PRIVATE_KEY, privateKey);
        editor.putString(PUBLIC_KEY, publicKey);
        editor.putString(ADDRESS, address);
        editor.commit();

        System.out.println( "Inside Shared preferences: " +  sharedPreferences.getString(PRIVATE_KEY, null) );
    }

    public void saveBalance(String balance) {
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString(BALANCE, balance);
        editor.commit();
    }

    public void saveChips(String chips) {
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString(CHIP_BALANCE, chips);
        editor.commit();
    }

    public void saveSpinResult(int a, int b, int c) {
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putInt(SPIN_RESULT_1, a);
        editor.putInt(SPIN_RESULT_2, b);
        editor.putInt(SPIN_RESULT_3, c);
        editor.commit();
    }

    public int[] getLastSpinResult() {
        int[] result = new int[3];
        result[0] = sharedPreferences.getInt(SPIN_RESULT_1, 1);
        result[1] = sharedPreferences.getInt(SPIN_RESULT_2, 2);
        result[2] = sharedPreferences.getInt(SPIN_RESULT_3, 3);

        return result;
    }

    public String getSavedChips() {
        return sharedPreferences.getString(CHIP_BALANCE, null);
    }



    public String getSavedBalance() {
        return sharedPreferences.getString(BALANCE, null);
    }

    public void deleteWallet() {
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.remove(PRIVATE_KEY);
        editor.remove(PUBLIC_KEY);
        editor.remove(ADDRESS);
        editor.remove(TEMP_PRIV_KEY);
        editor.remove(TEMP_PUB_KEY);
        editor.remove(TEMP_ADDRESS);
        editor.remove(BALANCE);
        editor.remove(CHIP_BALANCE);
        editor.remove(SPIN_RESULT_1);
        editor.remove(SPIN_RESULT_2);
        editor.remove(SPIN_RESULT_3);
        editor.commit();
    }

    public String getPrivateKey() {
        return sharedPreferences.getString(PRIVATE_KEY, null);
    }

    public String getPublicKey() {
        return sharedPreferences.getString(PUBLIC_KEY, null);
    }

    public String getAddress() {
        return sharedPreferences.getString(ADDRESS, null);
    }
    public Credentials getCredentials() {
        try {
            Credentials credentials = Credentials.create(getPrivateKey());
            return credentials;
        } catch(Exception ex) {
            ex.printStackTrace();
            return null;
        }
    }

    public static boolean isValidEthereumAddress(String address) {
        // Check if the address starts with "0x" and is 42 characters long
        if (address == null || !address.startsWith("0x") || address.length() != 42) {
            return false;
        }
        // Check if it contains only valid hexadecimal characters
        String hexPattern = "^(0x)[0-9a-fA-F]{40}$";
        return address.matches(hexPattern);
    }


    public static boolean isValidEthereumAddressWithChecksum(String address) {
        if (address == null || !address.startsWith("0x") || address.length() != 42) {
            return false;
        }
        try {
            // Use Web3j's Keys class to validate the checksum
            return Keys.toChecksumAddress(address).equals(address);
        } catch (Exception e) {
            return false; // If there's an error, it's not a valid address
        }
    }

    public static String getEthereumChecksumAddress(String address) {
        if (address == null || !address.startsWith("0x") || address.length() != 42) {
            return null;
        }
        try {
            // Use Web3j's Keys class to validate the checksum
            return Keys.toChecksumAddress(address);
        } catch (Exception e) {
            return null; // If there's an error, it's not a valid address
        }
    }

    public static boolean isFullyValidEthereumAddress(String address) {
        return isValidEthereumAddress(address) && isValidEthereumAddressWithChecksum(address);
    }

    public static BigInteger convertEthToWei(String ether) {
        try {
            BigInteger x = Convert.toWei(ether, Convert.Unit.ETHER).toBigInteger();
            return x;
        } catch (Exception ex) {
            return null;
        }
    }

    public static boolean isValidAmount(BigInteger balanceWei, BigInteger numberAmountWei) {
       return balanceWei != null && numberAmountWei != null && balanceWei.compareTo(numberAmountWei) >= 0;
    }


}
