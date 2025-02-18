package cryptooperations;

import org.web3j.abi.EventValues;
import org.web3j.abi.FunctionEncoder;
import org.web3j.abi.datatypes.Address;
import org.web3j.abi.datatypes.Uint;
import org.web3j.abi.datatypes.generated.Uint256;
import org.web3j.crypto.Credentials;
import org.web3j.crypto.RawTransaction;
import org.web3j.crypto.TransactionEncoder;
import org.web3j.protocol.Web3j;
import org.web3j.protocol.core.DefaultBlockParameterName;
import org.web3j.protocol.core.methods.request.Transaction;
import org.web3j.protocol.core.methods.response.EthBlock;
import org.web3j.protocol.core.methods.response.EthCall;
import org.web3j.protocol.core.methods.response.EthEstimateGas;
import org.web3j.protocol.core.methods.response.EthGasPrice;
import org.web3j.protocol.core.methods.response.EthGetBalance;
import org.web3j.protocol.core.methods.response.EthGetTransactionCount;
import org.web3j.protocol.core.methods.response.EthSendTransaction;
import org.web3j.protocol.core.methods.response.Log;
import org.web3j.protocol.core.methods.response.TransactionReceipt;
import org.web3j.protocol.http.HttpService;
import org.web3j.tx.RawTransactionManager;
import org.web3j.tx.TransactionManager;
import org.web3j.tx.Transfer;
import org.web3j.tx.gas.StaticGasProvider;
import org.web3j.utils.Convert;
import org.web3j.tx.gas.ContractGasProvider;
import org.web3j.tx.gas.DefaultGasProvider;
import org.web3j.utils.Numeric;
import org.web3j.abi.datatypes.generated.StaticArray4;



import java.math.BigDecimal;
import java.math.BigInteger;
import java.math.RoundingMode;
import java.util.Arrays;
import java.util.List;
import java.util.Random;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class MyWeb3Client {
    private Web3j web3j;
    private static final String WEB3_RPC_PROVIDER = "<<MAINNET_URL>>";
    private static final String CONTRACT_ADDRESS = "0x90d07231FB279f560D297F9D973011C54a42302C";
    private KeyGenerator keyGenerator;
    private Etnvegas etnvegas;
    public static final String ERROR_OCCURED = "error";
    public static final String STRING_TRUE = "true";
    public static final String STRING_FALSE = "false";
    BigInteger chainId;




    public MyWeb3Client(KeyGenerator keyGenerator) {
        this.keyGenerator = keyGenerator;
        web3j = Web3j.build(new HttpService(WEB3_RPC_PROVIDER.trim()));
        chainId = BigInteger.valueOf(52014);

        TransactionManager txManager = new RawTransactionManager(web3j, keyGenerator.getCredentials(), chainId.longValue());

        etnvegas = etnvegas.load(CONTRACT_ADDRESS.trim(), web3j, txManager, new DefaultGasProvider());

        etnvegas.setGasProvider(new DefaultGasProvider() {
            @Override
            public BigInteger getGasPrice(String contractFunc) {
                switch (contractFunc) {
                    case Etnvegas.FUNC_BUYTOKEN: return BigInteger.valueOf(100_000_000L);
                    case Etnvegas.FUNC_EXCHANGEWITHETHER: return BigInteger.valueOf(100_000_000L);
                    case Etnvegas.FUNC_PLAYSLOTS: return BigInteger.valueOf(100_000_000L);
                    default: return BigInteger.valueOf(21_000_000_000L);
                }
            }

            @Override
            public BigInteger getGasLimit(String contractFunc) {
                switch (contractFunc) {
                    case Etnvegas.FUNC_BUYTOKEN: return BigInteger.valueOf(4_300_000L);
                    case Etnvegas.FUNC_EXCHANGEWITHETHER: return BigInteger.valueOf(4_300_000L);
                    case Etnvegas.FUNC_PLAYSLOTS: return BigInteger.valueOf(4_300_000L);
                    default: return  BigInteger.valueOf(5_300_000L);
                }
            }
        });

    }

    public String fetchEtherBalance() {
        try {
            EthGetBalance balanceWei = web3j.ethGetBalance(this.keyGenerator.getAddress(), org.web3j.protocol.core.DefaultBlockParameterName.LATEST).send();

            // Convert balance from Wei to Ether and print
            BigInteger balanceInWei = balanceWei.getBalance();
            BigDecimal balanceInEther = Convert.fromWei(new BigDecimal(balanceInWei), Convert.Unit.ETHER);
            balanceInEther = balanceInEther.setScale(5, RoundingMode.HALF_DOWN);

            return balanceInEther.toString();
        } catch(Exception ex) {
            ex.printStackTrace();
        }
        return null;
    }

    public BigInteger fetchEtherBalanceInBigInteger() {
        try {
            EthGetBalance balanceWei = web3j.ethGetBalance(this.keyGenerator.getAddress(), org.web3j.protocol.core.DefaultBlockParameterName.LATEST).send();
            // Convert balance from Wei to Ether and print
            BigInteger balanceInWei = balanceWei.getBalance();
            return balanceInWei;
        } catch(Exception ex) {
            ex.printStackTrace();
        }
        return null;
    }

    public String[] sendEther(String toAddress, BigInteger amountInWei) {
        String[] res = new String[2];
        Credentials credentials = keyGenerator.getCredentials();

        // Convert Ether to Wei (1 Ether = 10^18 Wei)
        System.out.println("Amount: " + amountInWei );
        System.out.println("Address: " + toAddress);


        try {


            // Get the nonce
            EthGetTransactionCount ethGetTransactionCount = web3j.ethGetTransactionCount(
                    credentials.getAddress(), DefaultBlockParameterName.LATEST).send();
            BigInteger nonce = ethGetTransactionCount.getTransactionCount();

            // Create the transaction
            BigInteger gasPrice = getGasPrice();
            Transaction tx = Transaction.createEtherTransaction(keyGenerator.getAddress(), nonce, gasPrice, null, toAddress, amountInWei);
            BigInteger gasLimit = predictGasLimit(tx);


            RawTransaction rawTransaction = RawTransaction.createEtherTransaction(
                    nonce, gasPrice, gasLimit, toAddress, amountInWei);

            // Sign the transaction
            byte[] signedMessage = TransactionEncoder.signMessage(rawTransaction, chainId.longValue(), credentials);
            String hexValue = Numeric.toHexString(signedMessage);

            EthSendTransaction ethSendTransaction = web3j.ethSendRawTransaction(hexValue).send();
            if(ethSendTransaction.hasError() || ethSendTransaction.getTransactionHash() == null) {
                // error
                res[0] = ERROR_OCCURED;
                res[1] = ethSendTransaction.getError().getMessage();
                System.out.println(ethSendTransaction.getJsonrpc());

            } else {
                res[0] = "";
                res[1] = ethSendTransaction.getTransactionHash();
            }

        } catch(Exception ex) {
             ex.printStackTrace();
             res[0] = ERROR_OCCURED;
             res[1] = ex.getMessage();
        }

        return res;

    }

    public String getChipBalance() {
        try {
            BigInteger result = etnvegas.getUserTokenBalance().send();
            return result.toString();
        }  catch(Exception ex) {
            ex.printStackTrace();
        }

        return null;
    }

    public BigInteger getChipBalanceInBigInteger() {
        try {
            BigInteger result = etnvegas.getUserTokenBalance().send();
            return result;
        }  catch(Exception ex) {
            ex.printStackTrace();
        }

        return null;
    }

    public BigInteger getSellPricePerToken() {
        try {
            BigInteger result = etnvegas.TOKEN_SELL_PRICE().send();
            return result;
        }  catch(Exception ex) {
            ex.printStackTrace();
        }

        return null;
    }

    public BigInteger getFeePercentage() {
        try {
            BigInteger result = etnvegas.FEE_PERCENTAGE().send();
            System.out.println("Fee percentage ..................." + result);
            return result;
        }  catch(Exception ex) {
            ex.printStackTrace();
        }

        return null;
    }

    public String getEtherExchangeValue(BigInteger chipsAmount) {
        try {
            BigInteger result = etnvegas.getEtherExchangeAmount(chipsAmount).send();
            BigDecimal etherValue = Convert.fromWei(result.toString(), Convert.Unit.ETHER);
            return etherValue.toString();
        }  catch(Exception ex) {
            ex.printStackTrace();
        }

        return null;
    }

    public BigInteger predictGasLimit(Transaction tx) {
        BigInteger gasLimit = BigInteger.valueOf(100_000_000L);
        try {
            EthEstimateGas estimateGas =  web3j.ethEstimateGas(tx).send();
            if(estimateGas.getError() != null) {
                System.out.println(estimateGas.getError().getMessage());
            }
            gasLimit = estimateGas.getAmountUsed();
            System.out.println("GAS LIMIT : " + gasLimit);
        } catch(Exception ex) {
            ex.printStackTrace();
        }

        return gasLimit;
    }


    public BigInteger getGasPrice() {
        BigInteger gasPrice = BigInteger.valueOf(100_000_000L);
        try {

            BigInteger baseFee = web3j.ethGasPrice().send().getGasPrice();
            System.out.println("BLOCK BASE , GASE FEE: "  +  gasPrice.toString() + ", " + baseFee.toString());
            gasPrice = baseFee;
        } catch(Exception ex) {
            ex.printStackTrace();
        }

        return gasPrice;
    }

    public String[] exchangeChipWithEther(BigInteger chipAmount) {
        String[] result = new String[3];

        try {

            // get gas Limit and price
            BigInteger gasPrice = getGasPrice();
            Transaction tx = Transaction.createFunctionCallTransaction(keyGenerator.getAddress(), null, gasPrice, null, CONTRACT_ADDRESS,  BigInteger.ZERO, etnvegas.exchangeWithEther(chipAmount).encodeFunctionCall());

            BigInteger gasLimit = predictGasLimit(tx);
            etnvegas.setGasProvider(new StaticGasProvider(gasPrice, gasLimit));

            TransactionReceipt txnReceipt = etnvegas.exchangeWithEther(chipAmount).send();
            if(!txnReceipt.isStatusOK()) {
                // transaction was unsuccessful
                result[0] = ERROR_OCCURED;
                result[2] = txnReceipt.getRevertReason();
            } else {
                result[0] = "";
            }
            result[1] = txnReceipt.getTransactionHash();
        } catch (Exception ex) {
            ex.printStackTrace();
            result[0] = ERROR_OCCURED;
            result[1] = Utility.extractTxHashFromErrorMessage(ex.getMessage());
            result[2] = Utility.processTransactionFailure(ex.getMessage());
        }

        return result;
    }



    public String[] buyChips(BigInteger weiAmount) {
        String[] result = new String[3];

        try {

            // get gas Limit and price
            BigInteger gasPrice = getGasPrice();
            Transaction tx = Transaction.createFunctionCallTransaction(keyGenerator.getAddress(), null, gasPrice, null, CONTRACT_ADDRESS,  weiAmount, etnvegas.buyToken(weiAmount).encodeFunctionCall());
            BigInteger gasLimit = predictGasLimit(tx);
            etnvegas.setGasProvider(new StaticGasProvider(gasPrice, gasLimit));

            TransactionReceipt txnReceipt = etnvegas.buyToken(weiAmount).send();
            if(!txnReceipt.isStatusOK()) {
                // transaction was unsuccessful
                result[0] = ERROR_OCCURED;
                result[2] = txnReceipt.getRevertReason();
            } else {
                result[0] = "";
            }
            result[1] = txnReceipt.getTransactionHash();
        } catch (Exception ex) {
            ex.printStackTrace();
            System.out.println("EX MESSAGE: " + ex.getMessage());
            result[0] = ERROR_OCCURED;
            result[1] = Utility.extractTxHashFromErrorMessage(ex.getMessage());
            result[2] = Utility.processTransactionFailure(ex.getMessage());
        }

        return result;
    }

    public BigInteger getBuyPricePerToken() {
        try {
            BigInteger result = etnvegas.TOKEN_BUY_PRICE().send();
            return result;
        }  catch(Exception ex) {
            ex.printStackTrace();
        }

        return null;
    }

    public String[] playSlots(BigInteger betAmount) {
        String[] gameResult = new String[7];

        try {
            Random random = new Random();

            // Generate BigIntegers of a specific bit length
            BigInteger src1 = new BigInteger(43, random); // 50-bit random BigInteger
            BigInteger src2 = new BigInteger(47, random);
            BigInteger src3 = new BigInteger(19, random);
            // get gas Limit and price
            BigInteger gasPrice = getGasPrice();
            Transaction tx = Transaction.createFunctionCallTransaction(keyGenerator.getAddress(), null, null, null, CONTRACT_ADDRESS,  BigInteger.ZERO, etnvegas.playSlots(betAmount, src1, src2, src3).encodeFunctionCall());
            BigInteger gasLimit = predictGasLimit(tx);
            etnvegas.setGasProvider(new StaticGasProvider(gasPrice, gasLimit));

            TransactionReceipt txnReceipt = etnvegas.playSlots(betAmount, src1, src2, src3).send();
            if(!txnReceipt.isStatusOK()) {
                // error
                System.out.println("INSIDE TXN RECEIPT: " + txnReceipt);

                gameResult[4] = ERROR_OCCURED;
                gameResult[5] = txnReceipt.getTransactionHash();
                gameResult[6] = txnReceipt.getRevertReason();
                return gameResult;
            } else {
                // success
                List<Log> logs = txnReceipt.getLogs();
                for (Log log : logs) {
                    // Extract event parameters for the specific event
                    EventValues eventValues = etnvegas.staticExtractEventParameters(
                            etnvegas.SLOTGAMERESULTOUT_EVENT, log
                    );

                    if(eventValues != null) {
                        Address player = (Address) eventValues.getIndexedValues().get(0);
                        StaticArray4<Uint256> slotResults = (StaticArray4<Uint256>) eventValues.getNonIndexedValues().get(0);


                        gameResult[0] = slotResults.getValue().get(0).getValue().intValue()+"";
                        gameResult[1] = slotResults.getValue().get(1).getValue().intValue()+"";
                        gameResult[2] = slotResults.getValue().get(2).getValue().intValue()+"";
                        gameResult[3] = slotResults.getValue().get(3).getValue().intValue()+"";
                        gameResult[4] = "";
                        gameResult[5] = txnReceipt.getTransactionHash();
                        gameResult[6] = "";
                        break;
                    }

                    return gameResult;

                }

            }

        } catch(Exception ex) {
            ex.printStackTrace();
            System.out.println(ex.getMessage());
            gameResult[4] = ERROR_OCCURED;
            gameResult[5] = Utility.extractTxHashFromErrorMessage(ex.getMessage());
            gameResult[6] = Utility.processTransactionFailure(ex.getMessage());
        }
        return gameResult;
    }

    public String[] spinTheWheel(BigInteger betAmount, BigInteger choice) {
        String[] gameResult = new String[5];

        try {
            Random random = new Random();

            // Generate BigIntegers of a specific bit length
            BigInteger src1 = new BigInteger(43, random); // 50-bit random BigInteger
            // get gas Limit and price
            BigInteger gasPrice = getGasPrice();
            Transaction tx = Transaction.createFunctionCallTransaction(keyGenerator.getAddress(), null, null, null, CONTRACT_ADDRESS,  BigInteger.ZERO, etnvegas.spinTheWheel(betAmount, choice, src1).encodeFunctionCall());
            BigInteger gasLimit = predictGasLimit(tx);
            etnvegas.setGasProvider(new StaticGasProvider(gasPrice, gasLimit));

            TransactionReceipt txnReceipt = etnvegas.spinTheWheel(betAmount, choice, src1).send();
            if(!txnReceipt.isStatusOK()) {
                // error
                System.out.println("INSIDE TXN RECEIPT: " + txnReceipt);

                gameResult[2] = ERROR_OCCURED;
                gameResult[3] = txnReceipt.getTransactionHash();
                gameResult[4] = txnReceipt.getRevertReason();
                return gameResult;
            } else {
                // success
                List<Log> logs = txnReceipt.getLogs();
                for (Log log : logs) {
                    // Extract event parameters for the specific event
                    EventValues eventValues = etnvegas.staticExtractEventParameters(
                            etnvegas.SPINRESULTOUT_EVENT, log
                    );

                    if(eventValues != null) {
                        Address player = (Address) eventValues.getIndexedValues().get(0);
                        Uint256 returnedChoice = (Uint256) eventValues.getNonIndexedValues().get(0);
                        Uint256 returnedResult = (Uint256) eventValues.getNonIndexedValues().get(1);


                        gameResult[0] = returnedChoice.getValue().toString();
                        gameResult[1] = returnedResult.getValue().toString();
                        gameResult[2] = "";
                        gameResult[3] = txnReceipt.getTransactionHash();
                        gameResult[4] = "";
                        break;
                    }

                    return gameResult;

                }

            }

        } catch(Exception ex) {
            ex.printStackTrace();
            System.out.println(ex.getMessage());
            gameResult[2] = ERROR_OCCURED;
            gameResult[3] = Utility.extractTxHashFromErrorMessage(ex.getMessage());
            gameResult[4] = Utility.processTransactionFailure(ex.getMessage());
        }
        return gameResult;
    }

    public String[] rollADice(BigInteger betAmount, BigInteger choice) {
        String[] gameResult = new String[5];

        try {
            Random random = new Random();

            // Generate BigIntegers of a specific bit length
            BigInteger src1 = new BigInteger(37, random); // 50-bit random BigInteger
            // get gas Limit and price
            BigInteger gasPrice = getGasPrice();
            Transaction tx = Transaction.createFunctionCallTransaction(keyGenerator.getAddress(), null, null, null, CONTRACT_ADDRESS,  BigInteger.ZERO, etnvegas.rollADice(betAmount, choice, src1).encodeFunctionCall());
            BigInteger gasLimit = predictGasLimit(tx);
            etnvegas.setGasProvider(new StaticGasProvider(gasPrice, gasLimit));

            TransactionReceipt txnReceipt = etnvegas.rollADice(betAmount, choice, src1).send();
            if(!txnReceipt.isStatusOK()) {
                // error
                System.out.println("INSIDE TXN RECEIPT: " + txnReceipt);

                gameResult[2] = ERROR_OCCURED;
                gameResult[3] = txnReceipt.getTransactionHash();
                gameResult[4] = txnReceipt.getRevertReason();
                return gameResult;
            } else {
                // success
                List<Log> logs = txnReceipt.getLogs();
                for (Log log : logs) {
                    // Extract event parameters for the specific event
                    EventValues eventValues = etnvegas.staticExtractEventParameters(
                            etnvegas.DICEROLLRESULTOUT_EVENT, log
                    );

                    if(eventValues != null) {
                        Address player = (Address) eventValues.getIndexedValues().get(0);
                        Uint256 returnedChoice = (Uint256) eventValues.getNonIndexedValues().get(0);
                        Uint256 returnedResult = (Uint256) eventValues.getNonIndexedValues().get(1);


                        gameResult[0] = returnedChoice.getValue().toString();
                        gameResult[1] = returnedResult.getValue().toString();
                        gameResult[2] = "";
                        gameResult[3] = txnReceipt.getTransactionHash();
                        gameResult[4] = "";
                        break;
                    }

                    return gameResult;

                }

            }

        } catch(Exception ex) {
            ex.printStackTrace();
            System.out.println(ex.getMessage());
            gameResult[2] = ERROR_OCCURED;
            gameResult[3] = Utility.extractTxHashFromErrorMessage(ex.getMessage());
            gameResult[4] = Utility.processTransactionFailure(ex.getMessage());
        }
        return gameResult;
    }

    public String[] playRoulette(BigInteger[] betAmount) {
        String[] gameResult = new String[9];

        try {
            Random random = new Random();

            // Generate BigIntegers of a specific bit length
            BigInteger src1 = new BigInteger(41, random); // 50-bit random BigInteger
            // get gas Limit and price
            BigInteger gasPrice = getGasPrice();
            Transaction tx = Transaction.createFunctionCallTransaction(keyGenerator.getAddress(), null, null, null, CONTRACT_ADDRESS,  BigInteger.ZERO, etnvegas.playRoulette(Arrays.asList(betAmount), src1).encodeFunctionCall());
            BigInteger gasLimit = predictGasLimit(tx);
            etnvegas.setGasProvider(new StaticGasProvider(gasPrice, gasLimit));

            TransactionReceipt txnReceipt = etnvegas.playRoulette(Arrays.asList(betAmount), src1).send();

            if(!txnReceipt.isStatusOK()) {
                // error
                System.out.println("INSIDE TXN RECEIPT: " + txnReceipt);

                gameResult[6] = ERROR_OCCURED;
                gameResult[7] = txnReceipt.getTransactionHash();
                gameResult[8] = txnReceipt.getRevertReason();
                return gameResult;
            } else {
                // success
                List<Log> logs = txnReceipt.getLogs();
                for (Log log : logs) {
                    // Extract event parameters for the specific event
                    EventValues eventValues = etnvegas.staticExtractEventParameters(
                            etnvegas.ROULETTEGAMERESULTOUT_EVENT, log
                    );

                    if(eventValues != null) {

                        Address player = (Address) eventValues.getIndexedValues().get(0);
                        Uint256 retSpinNum = (Uint256) eventValues.getNonIndexedValues().get(0);
                        Uint256 retPayoutPlus = (Uint256) eventValues.getNonIndexedValues().get(1);
                        Uint256 retPayoutMinus = (Uint256) eventValues.getNonIndexedValues().get(2);
                        Uint256 retTotalBet = (Uint256) eventValues.getNonIndexedValues().get(3);
                        Uint256 retAfterDeduction = (Uint256) eventValues.getNonIndexedValues().get(4);
                        Uint256 retFinalBalance = (Uint256) eventValues.getNonIndexedValues().get(5);



                        gameResult[0] = retSpinNum.getValue().toString();
                        gameResult[1] = retPayoutPlus.getValue().toString();
                        gameResult[2] = retPayoutMinus.getValue().toString();
                        gameResult[3] = retTotalBet.getValue().toString();
                        gameResult[4] = retAfterDeduction.getValue().toString();
                        gameResult[5] = retFinalBalance.getValue().toString();
                        gameResult[6] = "";
                        gameResult[7] = txnReceipt.getTransactionHash();
                        gameResult[8] = "";
                        break;
                    }

                    return gameResult;

                }

            }

        } catch(Exception ex) {
            ex.printStackTrace();
            System.out.println(ex.getMessage());
            gameResult[6] = ERROR_OCCURED;
            gameResult[7] = Utility.extractTxHashFromErrorMessage(ex.getMessage());
            gameResult[8] = Utility.processTransactionFailure(ex.getMessage());
        }
        return gameResult;
    }



    public BigInteger getBlockBaseFee() {
        BigInteger b = BigInteger.ZERO;
        try {
            // Fetch the latest block
            EthBlock.Block block = web3j.ethGetBlockByNumber(DefaultBlockParameterName.LATEST, false).send().getBlock();
            // Get the block details
            b = block.getBaseFeePerGas();

        } catch(Exception ex) {

        }

        return b;
    }

    // Function to fetch the gas price floor
    public BigInteger getArbitrumMinGasPrice() {
        // Address of ArbGasInfo precompiled contract
        String arbGasInfoAddress = "0x000000000000000000000000000000000000006C";

        // Method selector for getMinimumGasPrice()
        String getMinimumGasPriceSelector = "41b247a8"; // Keccak hash of "getMinimumGasPrice()" truncated to 4 bytes

        try {
            // Build a transaction to call the ArbGasInfo contract
            Transaction transaction = Transaction.createEthCallTransaction(
                    null,  // from address (null for read-only calls)
                    arbGasInfoAddress,  // contract address
                    getMinimumGasPriceSelector // method selector
            );

            // Execute the call
            EthCall response = web3j.ethCall(transaction, org.web3j.protocol.core.DefaultBlockParameterName.LATEST).send();

            // Parse the returned data
            String data = response.getValue();
            if (data == null || data.equals("0x")) {
                throw new Exception("Failed to fetch L2 gas prices");
            }

            // Decode the three returned values (32 bytes each)
            BigInteger baseFee = new BigInteger(data.substring(2, 66), 16);
            BigInteger gasCongestionFee = new BigInteger(data.substring(258, 322), 16);
            BigInteger perArbGasFee = new BigInteger(data.substring(322, 386), 16);
            System.out.println("GAS FEES ARBITRUM: " + gasCongestionFee + ", " + perArbGasFee);
            return perArbGasFee.add(gasCongestionFee); // add for safety.

            // Decode the result (hexadecimal string to BigInteger)

        } catch (Exception e) {
            e.printStackTrace();
            return  BigInteger.valueOf(100_000_000L); // Return 0 if there's an error
        }
    }


}
