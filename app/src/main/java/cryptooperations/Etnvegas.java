package cryptooperations;

import io.reactivex.Flowable;
import java.math.BigInteger;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import org.web3j.abi.EventEncoder;
import org.web3j.abi.TypeReference;
import org.web3j.abi.datatypes.Address;
import org.web3j.abi.datatypes.Array;
import org.web3j.abi.datatypes.Event;
import org.web3j.abi.datatypes.Function;
import org.web3j.abi.datatypes.Type;
import org.web3j.abi.datatypes.generated.StaticArray4;
import org.web3j.abi.datatypes.generated.Uint256;
import org.web3j.crypto.Credentials;
import org.web3j.protocol.Web3j;
import org.web3j.protocol.core.DefaultBlockParameter;
import org.web3j.protocol.core.RemoteCall;
import org.web3j.protocol.core.RemoteFunctionCall;
import org.web3j.protocol.core.methods.request.EthFilter;
import org.web3j.protocol.core.methods.response.BaseEventResponse;
import org.web3j.protocol.core.methods.response.Log;
import org.web3j.protocol.core.methods.response.TransactionReceipt;
import org.web3j.tx.Contract;
import org.web3j.tx.TransactionManager;
import org.web3j.tx.gas.ContractGasProvider;

/**
 * <p>Auto generated code.
 * <p><strong>Do not modify!</strong>
 * <p>Please use the <a href="https://docs.web3j.io/command_line.html">web3j command line tools</a>,
 * or the org.web3j.codegen.SolidityFunctionWrapperGenerator in the
 * <a href="https://github.com/hyperledger/web3j/tree/main/codegen">codegen module</a> to update.
 *
 * <p>Generated with web3j version 1.6.1.
 */
@SuppressWarnings("rawtypes")
public class Etnvegas extends Contract {
    public static final String BINARY = "608060405266071afd498d00006004556604f94ae6af800060055560016006556005600755600a600b5563494b41b3600c5563494b41b3600d556301ef15eb600e556003600f557fffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffff601055336000806101000a81548173ffffffffffffffffffffffffffffffffffffffff021916908373ffffffffffffffffffffffffffffffffffffffff160217905550612262806100b96000396000f3fe60806040526004361061012d5760003560e01c806392f8fddb116100ab578063ae06c1b71161006f578063ae06c1b714610484578063bca23aa7146104bf578063bff1f9e1146104fa578063c55b44c814610525578063e7ff3ce214610574578063f3bb003d1461060957610134565b806392f8fddb146103605780639d1a116c146103c3578063a3405f39146103fe578063a482171914610429578063a6f9dae11461043357610134565b80634be21e33116100f25780634be21e331461024d5780635ca8e4711461028857806378571620146102b357806386b9f10a146102de5780638da5cb5b1461030957610134565b80620b46f814610136578063047087cc1461016157806313b9aaa01461018c5780631a47b92a146101e75780634807d4df1461022257610134565b3661013457005b005b34801561014257600080fd5b5061014b610662565b6040518082815260200191505060405180910390f35b34801561016d57600080fd5b50610176610668565b6040518082815260200191505060405180910390f35b34801561019857600080fd5b506101e5600480360360408110156101af57600080fd5b81019080803573ffffffffffffffffffffffffffffffffffffffff16906020019092919080359060200190929190505050610717565b005b3480156101f357600080fd5b506102206004803603602081101561020a57600080fd5b810190808035906020019092919050505061094e565b005b34801561022e57600080fd5b506102376109fd565b6040518082815260200191505060405180910390f35b34801561025957600080fd5b506102866004803603602081101561027057600080fd5b8101908080359060200190929190505050610a03565b005b34801561029457600080fd5b5061029d610b29565b6040518082815260200191505060405180910390f35b3480156102bf57600080fd5b506102c8610b2f565b6040518082815260200191505060405180910390f35b3480156102ea57600080fd5b506102f3610be2565b6040518082815260200191505060405180910390f35b34801561031557600080fd5b5061031e610c9f565b604051808273ffffffffffffffffffffffffffffffffffffffff1673ffffffffffffffffffffffffffffffffffffffff16815260200191505060405180910390f35b34801561036c57600080fd5b506103ad6004803603606081101561038357600080fd5b81019080803590602001909291908035906020019092919080359060200190929190505050610cc4565b6040518082815260200191505060405180910390f35b3480156103cf57600080fd5b506103fc600480360360208110156103e657600080fd5b8101908080359060200190929190505050610fb4565b005b34801561040a57600080fd5b50610413611305565b6040518082815260200191505060405180910390f35b61043161134c565b005b34801561043f57600080fd5b506104826004803603602081101561045657600080fd5b81019080803573ffffffffffffffffffffffffffffffffffffffff16906020019092919050505061155c565b005b34801561049057600080fd5b506104bd600480360360208110156104a757600080fd5b8101908080359060200190929190505050611644565b005b3480156104cb57600080fd5b506104f8600480360360208110156104e257600080fd5b810190808035906020019092919050505061176a565b005b34801561050657600080fd5b5061050f611890565b6040518082815260200191505060405180910390f35b34801561053157600080fd5b5061055e6004803603602081101561054857600080fd5b8101908080359060200190929190505050611896565b6040518082815260200191505060405180910390f35b34801561058057600080fd5b506105cb6004803603608081101561059757600080fd5b8101908080359060200190929190803590602001909291908035906020019092919080359060200190929190505050611aeb565b6040518082600460200280838360005b838110156105f65780820151818401526020810190506105db565b5050505090500191505060405180910390f35b34801561061557600080fd5b506106606004803603608081101561062c57600080fd5b8101908080359060200190929190803590602001909291908035906020019092919080359060200190929190505050611e9c565b005b60065481565b60008060009054906101000a900473ffffffffffffffffffffffffffffffffffffffff1673ffffffffffffffffffffffffffffffffffffffff163373ffffffffffffffffffffffffffffffffffffffff161461070f576040517f08c379a00000000000000000000000000000000000000000000000000000000081526004018080602001828103825260258152602001806122086025913960400191505060405180910390fd5b600a54905090565b6000809054906101000a900473ffffffffffffffffffffffffffffffffffffffff1673ffffffffffffffffffffffffffffffffffffffff163373ffffffffffffffffffffffffffffffffffffffff16146107bc576040517f08c379a00000000000000000000000000000000000000000000000000000000081526004018080602001828103825260258152602001806122086025913960400191505060405180910390fd5b806107c5611f63565b1015610839576040517f08c379a00000000000000000000000000000000000000000000000000000000081526004018080602001828103825260148152602001807f496e73756666696369656e742062616c616e636500000000000000000000000081525060200191505060405180910390fd5b60085481116108575780600860008282540392505081905550610894565b60095481116108755780600960008282540392505081905550610893565b60008160095460085401039050806008819055506000600981905550505b5b8173ffffffffffffffffffffffffffffffffffffffff166108fc829081150290604051600060405180830381858888f193505050501580156108da573d6000803e3d6000fd5b506000809054906101000a900473ffffffffffffffffffffffffffffffffffffffff1673ffffffffffffffffffffffffffffffffffffffff167f21c12829b7985abb3407cfc423af80798f87f898de109682819192318c258c16826040518082815260200191505060405180910390a25050565b6000809054906101000a900473ffffffffffffffffffffffffffffffffffffffff1673ffffffffffffffffffffffffffffffffffffffff163373ffffffffffffffffffffffffffffffffffffffff16146109f3576040517f08c379a00000000000000000000000000000000000000000000000000000000081526004018080602001828103825260258152602001806122086025913960400191505060405180910390fd5b80600b8190555050565b60045481565b6000809054906101000a900473ffffffffffffffffffffffffffffffffffffffff1673ffffffffffffffffffffffffffffffffffffffff163373ffffffffffffffffffffffffffffffffffffffff1614610aa8576040517f08c379a00000000000000000000000000000000000000000000000000000000081526004018080602001828103825260258152602001806122086025913960400191505060405180910390fd5b6000811415610b1f576040517f08c379a000000000000000000000000000000000000000000000000000000000815260040180806020018281038252600d8152602001807f496e76616c69642076616c75650000000000000000000000000000000000000081525060200191505060405180910390fd5b8060048190555050565b60055481565b60008060009054906101000a900473ffffffffffffffffffffffffffffffffffffffff1673ffffffffffffffffffffffffffffffffffffffff163373ffffffffffffffffffffffffffffffffffffffff1614610bd6576040517f08c379a00000000000000000000000000000000000000000000000000000000081526004018080602001828103825260258152602001806122086025913960400191505060405180910390fd5b60095460085401905090565b60008060009054906101000a900473ffffffffffffffffffffffffffffffffffffffff1673ffffffffffffffffffffffffffffffffffffffff163373ffffffffffffffffffffffffffffffffffffffff1614610c89576040517f08c379a00000000000000000000000000000000000000000000000000000000081526004018080602001828103825260258152602001806122086025913960400191505060405180910390fd5b610c91611f63565b610c99611f71565b03905090565b6000809054906101000a900473ffffffffffffffffffffffffffffffffffffffff1681565b60006001841015610d3d576040517f08c379a00000000000000000000000000000000000000000000000000000000081526004018080602001828103825260208152602001807f42657420616d6f756e742073686f756c64206265206d6f7265207468616e203181525060200191505060405180910390fd5b83600160003373ffffffffffffffffffffffffffffffffffffffff1673ffffffffffffffffffffffffffffffffffffffff168152602001908152602001600020541015610df2576040517f08c379a000000000000000000000000000000000000000000000000000000000815260040180806020018281038252601a8152602001807f496e73756666696369656e7420746f6b656e2062616c616e636500000000000081525060200191505060405180910390fd5b60018310158015610e055750600b548311155b610e77576040517f08c379a000000000000000000000000000000000000000000000000000000000815260040180806020018281038252600e8152602001807f496e76616c69642063686f63696500000000000000000000000000000000000081525060200191505060405180910390fd5b83600160003373ffffffffffffffffffffffffffffffffffffffff1673ffffffffffffffffffffffffffffffffffffffff168152602001908152602001600020600082825403925050819055506000610ecf83611f79565b905080841415610f4257600184018502600160003373ffffffffffffffffffffffffffffffffffffffff1673ffffffffffffffffffffffffffffffffffffffff16815260200190815260200160002060008282540192505081905550838502600a60008282540192505081905550610f53565b84600a600082825403925050819055505b3373ffffffffffffffffffffffffffffffffffffffff167f3cebb366d5bc659e09d73492dfa2cd3140907f72d7c797a47f7259bfa2744d678583604051808381526020018281526020019250505060405180910390a2809150509392505050565b600081141561102b576040517f08c379a000000000000000000000000000000000000000000000000000000000815260040180806020018281038252600a8152602001807f546f6b656e20697320300000000000000000000000000000000000000000000081525060200191505060405180910390fd5b80600160003373ffffffffffffffffffffffffffffffffffffffff1673ffffffffffffffffffffffffffffffffffffffff1681526020019081526020016000205410156110e0576040517f08c379a000000000000000000000000000000000000000000000000000000000815260040180806020018281038252601a8152602001807f496e73756666696369656e7420746f6b656e2062616c616e636500000000000081525060200191505060405180910390fd5b6000600554820290506110f16120b4565b811115611166576040517f08c379a000000000000000000000000000000000000000000000000000000000815260040180806020018281038252601a8152602001807f616d6f756e742065786365656473206c696162696c697469657300000000000081525060200191505060405180910390fd5b6000606460065483028161117657fe5b049050600081830390506111886120b4565b8111156111fd576040517f08c379a000000000000000000000000000000000000000000000000000000000815260040180806020018281038252601d8152602001807f496e73756666696369656e7420636f6e74726163742062616c616e636500000081525060200191505060405180910390fd5b83600160003373ffffffffffffffffffffffffffffffffffffffff1673ffffffffffffffffffffffffffffffffffffffff1681526020019081526020016000206000828254039250508190555083600a60008282540392505081905550816009600082825401925050819055503373ffffffffffffffffffffffffffffffffffffffff166108fc829081150290604051600060405180830381858888f193505050501580156112b0573d6000803e3d6000fd5b503373ffffffffffffffffffffffffffffffffffffffff167fb2f3766da8b78571fe25726690dc8f05160ca3ec032d6491606ce7d72b61bb84826040518082815260200191505060405180910390a250505050565b6000600160003373ffffffffffffffffffffffffffffffffffffffff1673ffffffffffffffffffffffffffffffffffffffff16815260200190815260200160002054905090565b6004543410156113c4576040517f08c379a000000000000000000000000000000000000000000000000000000000815260040180806020018281038252601f8152602001807f53656e742076616c7565206973206c657373207468616e206d696e696d756d0081525060200191505060405180910390fd5b600260003373ffffffffffffffffffffffffffffffffffffffff1673ffffffffffffffffffffffffffffffffffffffff16815260200190815260200160002060009054906101000a900460ff16611480576003600081548092919060010191905055506001600260003373ffffffffffffffffffffffffffffffffffffffff1673ffffffffffffffffffffffffffffffffffffffff16815260200190815260200160002060006101000a81548160ff0219169083151502179055505b6000600454348161148d57fe5b04905080600a6000828254019250508190555060646007543402816114ae57fe5b0460086000828254019250508190555080600160003373ffffffffffffffffffffffffffffffffffffffff1673ffffffffffffffffffffffffffffffffffffffff168152602001908152602001600020600082825401925050819055503373ffffffffffffffffffffffffffffffffffffffff167f26820c461ce4714545b50fc641526bdcb515e08232000670d2b38afd595f9bf3826040518082815260200191505060405180910390a250565b6000809054906101000a900473ffffffffffffffffffffffffffffffffffffffff1673ffffffffffffffffffffffffffffffffffffffff163373ffffffffffffffffffffffffffffffffffffffff1614611601576040517f08c379a00000000000000000000000000000000000000000000000000000000081526004018080602001828103825260258152602001806122086025913960400191505060405180910390fd5b806000806101000a81548173ffffffffffffffffffffffffffffffffffffffff021916908373ffffffffffffffffffffffffffffffffffffffff16021790555050565b6000809054906101000a900473ffffffffffffffffffffffffffffffffffffffff1673ffffffffffffffffffffffffffffffffffffffff163373ffffffffffffffffffffffffffffffffffffffff16146116e9576040517f08c379a00000000000000000000000000000000000000000000000000000000081526004018080602001828103825260258152602001806122086025913960400191505060405180910390fd5b6000811415611760576040517f08c379a000000000000000000000000000000000000000000000000000000000815260040180806020018281038252600d8152602001807f496e76616c69642076616c75650000000000000000000000000000000000000081525060200191505060405180910390fd5b8060068190555050565b6000809054906101000a900473ffffffffffffffffffffffffffffffffffffffff1673ffffffffffffffffffffffffffffffffffffffff163373ffffffffffffffffffffffffffffffffffffffff161461180f576040517f08c379a00000000000000000000000000000000000000000000000000000000081526004018080602001828103825260258152602001806122086025913960400191505060405180910390fd5b6000811415611886576040517f08c379a000000000000000000000000000000000000000000000000000000000815260040180806020018281038252600d8152602001807f496e76616c69642076616c75650000000000000000000000000000000000000081525060200191505060405180910390fd5b8060058190555050565b60035481565b60008082141561190e576040517f08c379a000000000000000000000000000000000000000000000000000000000815260040180806020018281038252600a8152602001807f546f6b656e20697320300000000000000000000000000000000000000000000081525060200191505060405180910390fd5b81600160003373ffffffffffffffffffffffffffffffffffffffff1673ffffffffffffffffffffffffffffffffffffffff1681526020019081526020016000205410156119c3576040517f08c379a000000000000000000000000000000000000000000000000000000000815260040180806020018281038252601a8152602001807f496e73756666696369656e7420746f6b656e2062616c616e636500000000000081525060200191505060405180910390fd5b6000600554830290506119d46120b4565b811115611a49576040517f08c379a000000000000000000000000000000000000000000000000000000000815260040180806020018281038252601a8152602001807f616d6f756e742065786365656473206c696162696c697469657300000000000081525060200191505060405180910390fd5b60006064600654830281611a5957fe5b04905060008183039050611a6b6120b4565b811115611ae0576040517f08c379a000000000000000000000000000000000000000000000000000000000815260040180806020018281038252601d8152602001807f496e73756666696369656e7420636f6e74726163742062616c616e636500000081525060200191505060405180910390fd5b809350505050919050565b611af36121c3565b6001851015611b6a576040517f08c379a00000000000000000000000000000000000000000000000000000000081526004018080602001828103825260208152602001807f42657420616d6f756e742073686f756c64206265206d6f7265207468616e203181525060200191505060405180910390fd5b84600160003373ffffffffffffffffffffffffffffffffffffffff1673ffffffffffffffffffffffffffffffffffffffff168152602001908152602001600020541015611c1f576040517f08c379a000000000000000000000000000000000000000000000000000000000815260040180806020018281038252601a8152602001807f496e73756666696369656e7420746f6b656e2062616c616e636500000000000081525060200191505060405180910390fd5b84600160003373ffffffffffffffffffffffffffffffffffffffff1673ffffffffffffffffffffffffffffffffffffffff16815260200190815260200160002060008282540392505081905550611c746121e5565b611c7d85611f79565b81600060038110611c8a57fe5b602002018181525050611c9c84611f79565b81600160038110611ca957fe5b602002018181525050611cbb83611f79565b81600260038110611cc857fe5b602002018181525050611cd96121c3565b6000611ce4836120cc565b905082600060038110611cf357fe5b602002015182600060048110611d0557fe5b60200201818152505082600160038110611d1b57fe5b602002015182600160048110611d2d57fe5b60200201818152505082600260038110611d4357fe5b602002015182600260048110611d5557fe5b6020020181815250506000811415611d6e576000611d71565b60015b60ff1682600360048110611d8157fe5b6020020181815250503373ffffffffffffffffffffffffffffffffffffffff167fcaa1b5a9d41febce64abb4f9320bafbdceee9c1da0165302cc8283930b734a55836040518082600460200280838360005b83811015611dee578082015181840152602081019050611dd3565b5050505090500191505060405180910390a26002811480611e0f5750600381145b15611e7d57600181018802600160003373ffffffffffffffffffffffffffffffffffffffff1673ffffffffffffffffffffffffffffffffffffffff16815260200190815260200160002060008282540192505081905550808802600a60008282540192505081905550611e8e565b87600a600082825403925050819055505b819350505050949350505050565b6000809054906101000a900473ffffffffffffffffffffffffffffffffffffffff1673ffffffffffffffffffffffffffffffffffffffff163373ffffffffffffffffffffffffffffffffffffffff1614611f41576040517f08c379a00000000000000000000000000000000000000000000000000000000081526004018080602001828103825260258152602001806122086025913960400191505060405180910390fd5b83600c8190555082600d8190555081600e8190555080600f8190555050505050565b600060095460085401905090565b600047905090565b600080600b54424485600c54600d54600e5433604051602001808881526020018781526020018681526020018581526020018481526020018381526020018273ffffffffffffffffffffffffffffffffffffffff1673ffffffffffffffffffffffffffffffffffffffff1660601b81526014019750505050505050506040516020818303038152906040528051906020012060001c8161201557fe5b0690506001600f546010540303600c54106120365763494b41b3600c819055505b6001600f546010540303600d54106120545763494b41b3600d819055505b6001600f546010540303600e5410612072576301ef15eb600e819055505b600f54600c60008282540192505081905550600f54600d60008282540192505081905550600f54600e6000828254019250508190555060018101915050919050565b60006120be611f63565b6120c6611f71565b03905090565b6000816001600381106120db57fe5b6020020151826000600381106120ed57fe5b602002015114801561212057508160026003811061210757fe5b60200201518260016003811061211957fe5b6020020151145b1561212e57600390506121be565b8160016003811061213b57fe5b60200201518260006003811061214d57fe5b6020020151148061217f57508160026003811061216657fe5b60200201518260006003811061217857fe5b6020020151145b806121ab57508160026003811061219257fe5b6020020151826001600381106121a457fe5b6020020151145b156121b957600290506121be565b600090505b919050565b6040518060800160405280600490602082028036833780820191505090505090565b604051806060016040528060039060208202803683378082019150509050509056fe4f6e6c7920746865206f776e65722063616e2063616c6c20746869732066756e6374696f6ea2646970667358221220594abe6ae049b7e04604efc1e6b9f518778600e679c74695aabc406d8871707f64736f6c63430006090033";

    private static String librariesLinkedBinary;

    public static final String FUNC_FEE_PERCENTAGE = "FEE_PERCENTAGE";

    public static final String FUNC_TOKEN_BUY_PRICE = "TOKEN_BUY_PRICE";

    public static final String FUNC_TOKEN_SELL_PRICE = "TOKEN_SELL_PRICE";

    public static final String FUNC_BUYTOKEN = "buyToken";

    public static final String FUNC_CHANGEOWNER = "changeOwner";

    public static final String FUNC_EXCHANGEWITHETHER = "exchangeWithEther";

    public static final String FUNC_GETETHEREXCHANGEAMOUNT = "getEtherExchangeAmount";

    public static final String FUNC_GETFREEFLOATINGCAPITALFORADMIN = "getFreeFloatingCapitalForAdmin";

    public static final String FUNC_GETHOUSEPROFITTILLNOWFORADMIN = "getHouseProfitTillNowForAdmin";

    public static final String FUNC_GETTOTALTOKENSUPPLY = "getTotalTokenSupply";

    public static final String FUNC_GETUSERTOKENBALANCE = "getUserTokenBalance";

    public static final String FUNC_OWNER = "owner";

    public static final String FUNC_PLAYSLOTS = "playSlots";

    public static final String FUNC_SETFEEPERCENTAGE = "setFeePercentage";

    public static final String FUNC_SETJACKPOTNUMBER = "setJackPotNumber";

    public static final String FUNC_SETNONCESOURCES = "setNonceSources";

    public static final String FUNC_SETTOKENBUYPRICE = "setTokenBuyPrice";

    public static final String FUNC_SETTOKENSELLPRICE = "setTokenSellPrice";

    public static final String FUNC_SPINTHEWHEEL = "spinTheWheel";

    public static final String FUNC_TOTALUSERS = "totalUsers";

    public static final String FUNC_WITHDRAWHOUSINGPROFIT = "withdrawHousingProfit";

    public static final Event CHIPBOUGHT_EVENT = new Event("ChipBought",
            Arrays.<TypeReference<?>>asList(new TypeReference<Address>(true) {}, new TypeReference<Uint256>() {}));
    ;

    public static final Event EXCHANGEDCHIP_EVENT = new Event("ExchangedChip",
            Arrays.<TypeReference<?>>asList(new TypeReference<Address>(true) {}, new TypeReference<Uint256>() {}));
    ;

    public static final Event HOUSINGPROFITWITHDRAWN_EVENT = new Event("HousingProfitWithdrawn",
            Arrays.<TypeReference<?>>asList(new TypeReference<Address>(true) {}, new TypeReference<Uint256>() {}));
    ;

    public static final Event ROULETTEGAMERESULTOUT_EVENT = new Event("RouletteGameResultOut",
            Arrays.<TypeReference<?>>asList(new TypeReference<Address>(true) {}, new TypeReference<Uint256>() {}));
    ;

    public static final Event SLOTGAMERESULTOUT_EVENT = new Event("SlotGameResultOut",
            Arrays.<TypeReference<?>>asList(new TypeReference<Address>(true) {}, new TypeReference<StaticArray4<Uint256>>() {}));
    ;

    public static final Event SPINRESULTOUT_EVENT = new Event("SpinResultOut",
            Arrays.<TypeReference<?>>asList(new TypeReference<Address>(true) {}, new TypeReference<Uint256>() {}, new TypeReference<Uint256>() {}));
    ;

    @Deprecated
    protected Etnvegas(String contractAddress, Web3j web3j, Credentials credentials,
                       BigInteger gasPrice, BigInteger gasLimit) {
        super(BINARY, contractAddress, web3j, credentials, gasPrice, gasLimit);
    }

    protected Etnvegas(String contractAddress, Web3j web3j, Credentials credentials,
                       ContractGasProvider contractGasProvider) {
        super(BINARY, contractAddress, web3j, credentials, contractGasProvider);
    }

    @Deprecated
    protected Etnvegas(String contractAddress, Web3j web3j, TransactionManager transactionManager,
                       BigInteger gasPrice, BigInteger gasLimit) {
        super(BINARY, contractAddress, web3j, transactionManager, gasPrice, gasLimit);
    }

    protected Etnvegas(String contractAddress, Web3j web3j, TransactionManager transactionManager,
                       ContractGasProvider contractGasProvider) {
        super(BINARY, contractAddress, web3j, transactionManager, contractGasProvider);
    }

    public static List<ChipBoughtEventResponse> getChipBoughtEvents(
            TransactionReceipt transactionReceipt) {
        List<Contract.EventValuesWithLog> valueList = staticExtractEventParametersWithLog(CHIPBOUGHT_EVENT, transactionReceipt);
        ArrayList<ChipBoughtEventResponse> responses = new ArrayList<ChipBoughtEventResponse>(valueList.size());
        for (Contract.EventValuesWithLog eventValues : valueList) {
            ChipBoughtEventResponse typedResponse = new ChipBoughtEventResponse();
            typedResponse.log = eventValues.getLog();
            typedResponse.buyer = (String) eventValues.getIndexedValues().get(0).getValue();
            typedResponse.amount = (BigInteger) eventValues.getNonIndexedValues().get(0).getValue();
            responses.add(typedResponse);
        }
        return responses;
    }

    public static ChipBoughtEventResponse getChipBoughtEventFromLog(Log log) {
        Contract.EventValuesWithLog eventValues = staticExtractEventParametersWithLog(CHIPBOUGHT_EVENT, log);
        ChipBoughtEventResponse typedResponse = new ChipBoughtEventResponse();
        typedResponse.log = log;
        typedResponse.buyer = (String) eventValues.getIndexedValues().get(0).getValue();
        typedResponse.amount = (BigInteger) eventValues.getNonIndexedValues().get(0).getValue();
        return typedResponse;
    }

    public Flowable<ChipBoughtEventResponse> chipBoughtEventFlowable(EthFilter filter) {
        return web3j.ethLogFlowable(filter).map(log -> getChipBoughtEventFromLog(log));
    }

    public Flowable<ChipBoughtEventResponse> chipBoughtEventFlowable(
            DefaultBlockParameter startBlock, DefaultBlockParameter endBlock) {
        EthFilter filter = new EthFilter(startBlock, endBlock, getContractAddress());
        filter.addSingleTopic(EventEncoder.encode(CHIPBOUGHT_EVENT));
        return chipBoughtEventFlowable(filter);
    }

    public static List<ExchangedChipEventResponse> getExchangedChipEvents(
            TransactionReceipt transactionReceipt) {
        List<Contract.EventValuesWithLog> valueList = staticExtractEventParametersWithLog(EXCHANGEDCHIP_EVENT, transactionReceipt);
        ArrayList<ExchangedChipEventResponse> responses = new ArrayList<ExchangedChipEventResponse>(valueList.size());
        for (Contract.EventValuesWithLog eventValues : valueList) {
            ExchangedChipEventResponse typedResponse = new ExchangedChipEventResponse();
            typedResponse.log = eventValues.getLog();
            typedResponse.withdrawer = (String) eventValues.getIndexedValues().get(0).getValue();
            typedResponse.amount = (BigInteger) eventValues.getNonIndexedValues().get(0).getValue();
            responses.add(typedResponse);
        }
        return responses;
    }

    public static ExchangedChipEventResponse getExchangedChipEventFromLog(Log log) {
        Contract.EventValuesWithLog eventValues = staticExtractEventParametersWithLog(EXCHANGEDCHIP_EVENT, log);
        ExchangedChipEventResponse typedResponse = new ExchangedChipEventResponse();
        typedResponse.log = log;
        typedResponse.withdrawer = (String) eventValues.getIndexedValues().get(0).getValue();
        typedResponse.amount = (BigInteger) eventValues.getNonIndexedValues().get(0).getValue();
        return typedResponse;
    }

    public Flowable<ExchangedChipEventResponse> exchangedChipEventFlowable(EthFilter filter) {
        return web3j.ethLogFlowable(filter).map(log -> getExchangedChipEventFromLog(log));
    }

    public Flowable<ExchangedChipEventResponse> exchangedChipEventFlowable(
            DefaultBlockParameter startBlock, DefaultBlockParameter endBlock) {
        EthFilter filter = new EthFilter(startBlock, endBlock, getContractAddress());
        filter.addSingleTopic(EventEncoder.encode(EXCHANGEDCHIP_EVENT));
        return exchangedChipEventFlowable(filter);
    }

    public static List<HousingProfitWithdrawnEventResponse> getHousingProfitWithdrawnEvents(
            TransactionReceipt transactionReceipt) {
        List<Contract.EventValuesWithLog> valueList = staticExtractEventParametersWithLog(HOUSINGPROFITWITHDRAWN_EVENT, transactionReceipt);
        ArrayList<HousingProfitWithdrawnEventResponse> responses = new ArrayList<HousingProfitWithdrawnEventResponse>(valueList.size());
        for (Contract.EventValuesWithLog eventValues : valueList) {
            HousingProfitWithdrawnEventResponse typedResponse = new HousingProfitWithdrawnEventResponse();
            typedResponse.log = eventValues.getLog();
            typedResponse.beneficiary = (String) eventValues.getIndexedValues().get(0).getValue();
            typedResponse.amount = (BigInteger) eventValues.getNonIndexedValues().get(0).getValue();
            responses.add(typedResponse);
        }
        return responses;
    }

    public static HousingProfitWithdrawnEventResponse getHousingProfitWithdrawnEventFromLog(
            Log log) {
        Contract.EventValuesWithLog eventValues = staticExtractEventParametersWithLog(HOUSINGPROFITWITHDRAWN_EVENT, log);
        HousingProfitWithdrawnEventResponse typedResponse = new HousingProfitWithdrawnEventResponse();
        typedResponse.log = log;
        typedResponse.beneficiary = (String) eventValues.getIndexedValues().get(0).getValue();
        typedResponse.amount = (BigInteger) eventValues.getNonIndexedValues().get(0).getValue();
        return typedResponse;
    }

    public Flowable<HousingProfitWithdrawnEventResponse> housingProfitWithdrawnEventFlowable(
            EthFilter filter) {
        return web3j.ethLogFlowable(filter).map(log -> getHousingProfitWithdrawnEventFromLog(log));
    }

    public Flowable<HousingProfitWithdrawnEventResponse> housingProfitWithdrawnEventFlowable(
            DefaultBlockParameter startBlock, DefaultBlockParameter endBlock) {
        EthFilter filter = new EthFilter(startBlock, endBlock, getContractAddress());
        filter.addSingleTopic(EventEncoder.encode(HOUSINGPROFITWITHDRAWN_EVENT));
        return housingProfitWithdrawnEventFlowable(filter);
    }

    public static List<RouletteGameResultOutEventResponse> getRouletteGameResultOutEvents(
            TransactionReceipt transactionReceipt) {
        List<Contract.EventValuesWithLog> valueList = staticExtractEventParametersWithLog(ROULETTEGAMERESULTOUT_EVENT, transactionReceipt);
        ArrayList<RouletteGameResultOutEventResponse> responses = new ArrayList<RouletteGameResultOutEventResponse>(valueList.size());
        for (Contract.EventValuesWithLog eventValues : valueList) {
            RouletteGameResultOutEventResponse typedResponse = new RouletteGameResultOutEventResponse();
            typedResponse.log = eventValues.getLog();
            typedResponse.player = (String) eventValues.getIndexedValues().get(0).getValue();
            typedResponse.winningNumber = (BigInteger) eventValues.getNonIndexedValues().get(0).getValue();
            responses.add(typedResponse);
        }
        return responses;
    }

    public static RouletteGameResultOutEventResponse getRouletteGameResultOutEventFromLog(Log log) {
        Contract.EventValuesWithLog eventValues = staticExtractEventParametersWithLog(ROULETTEGAMERESULTOUT_EVENT, log);
        RouletteGameResultOutEventResponse typedResponse = new RouletteGameResultOutEventResponse();
        typedResponse.log = log;
        typedResponse.player = (String) eventValues.getIndexedValues().get(0).getValue();
        typedResponse.winningNumber = (BigInteger) eventValues.getNonIndexedValues().get(0).getValue();
        return typedResponse;
    }

    public Flowable<RouletteGameResultOutEventResponse> rouletteGameResultOutEventFlowable(
            EthFilter filter) {
        return web3j.ethLogFlowable(filter).map(log -> getRouletteGameResultOutEventFromLog(log));
    }

    public Flowable<RouletteGameResultOutEventResponse> rouletteGameResultOutEventFlowable(
            DefaultBlockParameter startBlock, DefaultBlockParameter endBlock) {
        EthFilter filter = new EthFilter(startBlock, endBlock, getContractAddress());
        filter.addSingleTopic(EventEncoder.encode(ROULETTEGAMERESULTOUT_EVENT));
        return rouletteGameResultOutEventFlowable(filter);
    }

    public static List<SlotGameResultOutEventResponse> getSlotGameResultOutEvents(
            TransactionReceipt transactionReceipt) {
        List<Contract.EventValuesWithLog> valueList = staticExtractEventParametersWithLog(SLOTGAMERESULTOUT_EVENT, transactionReceipt);
        ArrayList<SlotGameResultOutEventResponse> responses = new ArrayList<SlotGameResultOutEventResponse>(valueList.size());
        for (Contract.EventValuesWithLog eventValues : valueList) {
            SlotGameResultOutEventResponse typedResponse = new SlotGameResultOutEventResponse();
            typedResponse.log = eventValues.getLog();
            typedResponse.player = (String) eventValues.getIndexedValues().get(0).getValue();
            typedResponse.result = (List<BigInteger>) ((Array) eventValues.getNonIndexedValues().get(0)).getNativeValueCopy();
            responses.add(typedResponse);
        }
        return responses;
    }

    public static SlotGameResultOutEventResponse getSlotGameResultOutEventFromLog(Log log) {
        Contract.EventValuesWithLog eventValues = staticExtractEventParametersWithLog(SLOTGAMERESULTOUT_EVENT, log);
        SlotGameResultOutEventResponse typedResponse = new SlotGameResultOutEventResponse();
        typedResponse.log = log;
        typedResponse.player = (String) eventValues.getIndexedValues().get(0).getValue();
        typedResponse.result = (List<BigInteger>) ((Array) eventValues.getNonIndexedValues().get(0)).getNativeValueCopy();
        return typedResponse;
    }

    public Flowable<SlotGameResultOutEventResponse> slotGameResultOutEventFlowable(
            EthFilter filter) {
        return web3j.ethLogFlowable(filter).map(log -> getSlotGameResultOutEventFromLog(log));
    }

    public Flowable<SlotGameResultOutEventResponse> slotGameResultOutEventFlowable(
            DefaultBlockParameter startBlock, DefaultBlockParameter endBlock) {
        EthFilter filter = new EthFilter(startBlock, endBlock, getContractAddress());
        filter.addSingleTopic(EventEncoder.encode(SLOTGAMERESULTOUT_EVENT));
        return slotGameResultOutEventFlowable(filter);
    }

    public static List<SpinResultOutEventResponse> getSpinResultOutEvents(
            TransactionReceipt transactionReceipt) {
        List<Contract.EventValuesWithLog> valueList = staticExtractEventParametersWithLog(SPINRESULTOUT_EVENT, transactionReceipt);
        ArrayList<SpinResultOutEventResponse> responses = new ArrayList<SpinResultOutEventResponse>(valueList.size());
        for (Contract.EventValuesWithLog eventValues : valueList) {
            SpinResultOutEventResponse typedResponse = new SpinResultOutEventResponse();
            typedResponse.log = eventValues.getLog();
            typedResponse.player = (String) eventValues.getIndexedValues().get(0).getValue();
            typedResponse.choice = (BigInteger) eventValues.getNonIndexedValues().get(0).getValue();
            typedResponse.spinNumber = (BigInteger) eventValues.getNonIndexedValues().get(1).getValue();
            responses.add(typedResponse);
        }
        return responses;
    }

    public static SpinResultOutEventResponse getSpinResultOutEventFromLog(Log log) {
        Contract.EventValuesWithLog eventValues = staticExtractEventParametersWithLog(SPINRESULTOUT_EVENT, log);
        SpinResultOutEventResponse typedResponse = new SpinResultOutEventResponse();
        typedResponse.log = log;
        typedResponse.player = (String) eventValues.getIndexedValues().get(0).getValue();
        typedResponse.choice = (BigInteger) eventValues.getNonIndexedValues().get(0).getValue();
        typedResponse.spinNumber = (BigInteger) eventValues.getNonIndexedValues().get(1).getValue();
        return typedResponse;
    }

    public Flowable<SpinResultOutEventResponse> spinResultOutEventFlowable(EthFilter filter) {
        return web3j.ethLogFlowable(filter).map(log -> getSpinResultOutEventFromLog(log));
    }

    public Flowable<SpinResultOutEventResponse> spinResultOutEventFlowable(
            DefaultBlockParameter startBlock, DefaultBlockParameter endBlock) {
        EthFilter filter = new EthFilter(startBlock, endBlock, getContractAddress());
        filter.addSingleTopic(EventEncoder.encode(SPINRESULTOUT_EVENT));
        return spinResultOutEventFlowable(filter);
    }

    public RemoteFunctionCall<BigInteger> FEE_PERCENTAGE() {
        final Function function = new Function(FUNC_FEE_PERCENTAGE,
                Arrays.<Type>asList(),
                Arrays.<TypeReference<?>>asList(new TypeReference<Uint256>() {}));
        return executeRemoteCallSingleValueReturn(function, BigInteger.class);
    }

    public RemoteFunctionCall<BigInteger> TOKEN_BUY_PRICE() {
        final Function function = new Function(FUNC_TOKEN_BUY_PRICE,
                Arrays.<Type>asList(),
                Arrays.<TypeReference<?>>asList(new TypeReference<Uint256>() {}));
        return executeRemoteCallSingleValueReturn(function, BigInteger.class);
    }

    public RemoteFunctionCall<BigInteger> TOKEN_SELL_PRICE() {
        final Function function = new Function(FUNC_TOKEN_SELL_PRICE,
                Arrays.<Type>asList(),
                Arrays.<TypeReference<?>>asList(new TypeReference<Uint256>() {}));
        return executeRemoteCallSingleValueReturn(function, BigInteger.class);
    }

    public RemoteFunctionCall<TransactionReceipt> buyToken(BigInteger weiValue) {
        final Function function = new Function(
                FUNC_BUYTOKEN,
                Arrays.<Type>asList(),
                Collections.<TypeReference<?>>emptyList());
        return executeRemoteCallTransaction(function, weiValue);
    }

    public RemoteFunctionCall<TransactionReceipt> changeOwner(String _newOwner) {
        final Function function = new Function(
                FUNC_CHANGEOWNER,
                Arrays.<Type>asList(new org.web3j.abi.datatypes.Address(160, _newOwner)),
                Collections.<TypeReference<?>>emptyList());
        return executeRemoteCallTransaction(function);
    }

    public RemoteFunctionCall<TransactionReceipt> exchangeWithEther(BigInteger token) {
        final Function function = new Function(
                FUNC_EXCHANGEWITHETHER,
                Arrays.<Type>asList(new org.web3j.abi.datatypes.generated.Uint256(token)),
                Collections.<TypeReference<?>>emptyList());
        return executeRemoteCallTransaction(function);
    }

    public RemoteFunctionCall<BigInteger> getEtherExchangeAmount(BigInteger token) {
        final Function function = new Function(FUNC_GETETHEREXCHANGEAMOUNT,
                Arrays.<Type>asList(new org.web3j.abi.datatypes.generated.Uint256(token)),
                Arrays.<TypeReference<?>>asList(new TypeReference<Uint256>() {}));
        return executeRemoteCallSingleValueReturn(function, BigInteger.class);
    }

    public RemoteFunctionCall<BigInteger> getFreeFloatingCapitalForAdmin() {
        final Function function = new Function(FUNC_GETFREEFLOATINGCAPITALFORADMIN,
                Arrays.<Type>asList(),
                Arrays.<TypeReference<?>>asList(new TypeReference<Uint256>() {}));
        return executeRemoteCallSingleValueReturn(function, BigInteger.class);
    }

    public RemoteFunctionCall<BigInteger> getHouseProfitTillNowForAdmin() {
        final Function function = new Function(FUNC_GETHOUSEPROFITTILLNOWFORADMIN,
                Arrays.<Type>asList(),
                Arrays.<TypeReference<?>>asList(new TypeReference<Uint256>() {}));
        return executeRemoteCallSingleValueReturn(function, BigInteger.class);
    }

    public RemoteFunctionCall<BigInteger> getTotalTokenSupply() {
        final Function function = new Function(FUNC_GETTOTALTOKENSUPPLY,
                Arrays.<Type>asList(),
                Arrays.<TypeReference<?>>asList(new TypeReference<Uint256>() {}));
        return executeRemoteCallSingleValueReturn(function, BigInteger.class);
    }

    public RemoteFunctionCall<BigInteger> getUserTokenBalance() {
        final Function function = new Function(FUNC_GETUSERTOKENBALANCE,
                Arrays.<Type>asList(),
                Arrays.<TypeReference<?>>asList(new TypeReference<Uint256>() {}));
        return executeRemoteCallSingleValueReturn(function, BigInteger.class);
    }

    public RemoteFunctionCall<String> owner() {
        final Function function = new Function(FUNC_OWNER,
                Arrays.<Type>asList(),
                Arrays.<TypeReference<?>>asList(new TypeReference<Address>() {}));
        return executeRemoteCallSingleValueReturn(function, String.class);
    }

    public RemoteFunctionCall<TransactionReceipt> playSlots(BigInteger betAmount,
                                                            BigInteger _randomnessSource1, BigInteger _randomnessSource2,
                                                            BigInteger _randomnessSource3) {
        final Function function = new Function(
                FUNC_PLAYSLOTS,
                Arrays.<Type>asList(new org.web3j.abi.datatypes.generated.Uint256(betAmount),
                        new org.web3j.abi.datatypes.generated.Uint256(_randomnessSource1),
                        new org.web3j.abi.datatypes.generated.Uint256(_randomnessSource2),
                        new org.web3j.abi.datatypes.generated.Uint256(_randomnessSource3)),
                Collections.<TypeReference<?>>emptyList());
        return executeRemoteCallTransaction(function);
    }

    public RemoteFunctionCall<TransactionReceipt> setFeePercentage(BigInteger value) {
        final Function function = new Function(
                FUNC_SETFEEPERCENTAGE,
                Arrays.<Type>asList(new org.web3j.abi.datatypes.generated.Uint256(value)),
                Collections.<TypeReference<?>>emptyList());
        return executeRemoteCallTransaction(function);
    }

    public RemoteFunctionCall<TransactionReceipt> setJackPotNumber(BigInteger _jn) {
        final Function function = new Function(
                FUNC_SETJACKPOTNUMBER,
                Arrays.<Type>asList(new org.web3j.abi.datatypes.generated.Uint256(_jn)),
                Collections.<TypeReference<?>>emptyList());
        return executeRemoteCallTransaction(function);
    }

    public RemoteFunctionCall<TransactionReceipt> setNonceSources(BigInteger _s1, BigInteger _s2,
                                                                  BigInteger _s3, BigInteger _in) {
        final Function function = new Function(
                FUNC_SETNONCESOURCES,
                Arrays.<Type>asList(new org.web3j.abi.datatypes.generated.Uint256(_s1),
                        new org.web3j.abi.datatypes.generated.Uint256(_s2),
                        new org.web3j.abi.datatypes.generated.Uint256(_s3),
                        new org.web3j.abi.datatypes.generated.Uint256(_in)),
                Collections.<TypeReference<?>>emptyList());
        return executeRemoteCallTransaction(function);
    }

    public RemoteFunctionCall<TransactionReceipt> setTokenBuyPrice(BigInteger value) {
        final Function function = new Function(
                FUNC_SETTOKENBUYPRICE,
                Arrays.<Type>asList(new org.web3j.abi.datatypes.generated.Uint256(value)),
                Collections.<TypeReference<?>>emptyList());
        return executeRemoteCallTransaction(function);
    }

    public RemoteFunctionCall<TransactionReceipt> setTokenSellPrice(BigInteger value) {
        final Function function = new Function(
                FUNC_SETTOKENSELLPRICE,
                Arrays.<Type>asList(new org.web3j.abi.datatypes.generated.Uint256(value)),
                Collections.<TypeReference<?>>emptyList());
        return executeRemoteCallTransaction(function);
    }

    public RemoteFunctionCall<TransactionReceipt> spinTheWheel(BigInteger betAmount,
                                                               BigInteger choice, BigInteger _randomnessSource1) {
        final Function function = new Function(
                FUNC_SPINTHEWHEEL,
                Arrays.<Type>asList(new org.web3j.abi.datatypes.generated.Uint256(betAmount),
                        new org.web3j.abi.datatypes.generated.Uint256(choice),
                        new org.web3j.abi.datatypes.generated.Uint256(_randomnessSource1)),
                Collections.<TypeReference<?>>emptyList());
        return executeRemoteCallTransaction(function);
    }

    public RemoteFunctionCall<BigInteger> totalUsers() {
        final Function function = new Function(FUNC_TOTALUSERS,
                Arrays.<Type>asList(),
                Arrays.<TypeReference<?>>asList(new TypeReference<Uint256>() {}));
        return executeRemoteCallSingleValueReturn(function, BigInteger.class);
    }

    public RemoteFunctionCall<TransactionReceipt> withdrawHousingProfit(String beneficiary,
                                                                        BigInteger amount) {
        final Function function = new Function(
                FUNC_WITHDRAWHOUSINGPROFIT,
                Arrays.<Type>asList(new org.web3j.abi.datatypes.Address(160, beneficiary),
                        new org.web3j.abi.datatypes.generated.Uint256(amount)),
                Collections.<TypeReference<?>>emptyList());
        return executeRemoteCallTransaction(function);
    }

    @Deprecated
    public static Etnvegas load(String contractAddress, Web3j web3j, Credentials credentials,
                                BigInteger gasPrice, BigInteger gasLimit) {
        return new Etnvegas(contractAddress, web3j, credentials, gasPrice, gasLimit);
    }

    @Deprecated
    public static Etnvegas load(String contractAddress, Web3j web3j,
                                TransactionManager transactionManager, BigInteger gasPrice, BigInteger gasLimit) {
        return new Etnvegas(contractAddress, web3j, transactionManager, gasPrice, gasLimit);
    }

    public static Etnvegas load(String contractAddress, Web3j web3j, Credentials credentials,
                                ContractGasProvider contractGasProvider) {
        return new Etnvegas(contractAddress, web3j, credentials, contractGasProvider);
    }

    public static Etnvegas load(String contractAddress, Web3j web3j,
                                TransactionManager transactionManager, ContractGasProvider contractGasProvider) {
        return new Etnvegas(contractAddress, web3j, transactionManager, contractGasProvider);
    }

    public static RemoteCall<Etnvegas> deploy(Web3j web3j, Credentials credentials,
                                              ContractGasProvider contractGasProvider, BigInteger initialWeiValue) {
        return deployRemoteCall(Etnvegas.class, web3j, credentials, contractGasProvider, getDeploymentBinary(), "", initialWeiValue);
    }

    public static RemoteCall<Etnvegas> deploy(Web3j web3j, TransactionManager transactionManager,
                                              ContractGasProvider contractGasProvider, BigInteger initialWeiValue) {
        return deployRemoteCall(Etnvegas.class, web3j, transactionManager, contractGasProvider, getDeploymentBinary(), "", initialWeiValue);
    }

    @Deprecated
    public static RemoteCall<Etnvegas> deploy(Web3j web3j, Credentials credentials,
                                              BigInteger gasPrice, BigInteger gasLimit, BigInteger initialWeiValue) {
        return deployRemoteCall(Etnvegas.class, web3j, credentials, gasPrice, gasLimit, getDeploymentBinary(), "", initialWeiValue);
    }

    @Deprecated
    public static RemoteCall<Etnvegas> deploy(Web3j web3j, TransactionManager transactionManager,
                                              BigInteger gasPrice, BigInteger gasLimit, BigInteger initialWeiValue) {
        return deployRemoteCall(Etnvegas.class, web3j, transactionManager, gasPrice, gasLimit, getDeploymentBinary(), "", initialWeiValue);
    }

    public static void linkLibraries(List<Contract.LinkReference> references) {
        librariesLinkedBinary = linkBinaryWithReferences(BINARY, references);
    }

    private static String getDeploymentBinary() {
        if (librariesLinkedBinary != null) {
            return librariesLinkedBinary;
        } else {
            return BINARY;
        }
    }

    public static class ChipBoughtEventResponse extends BaseEventResponse {
        public String buyer;

        public BigInteger amount;
    }

    public static class ExchangedChipEventResponse extends BaseEventResponse {
        public String withdrawer;

        public BigInteger amount;
    }

    public static class HousingProfitWithdrawnEventResponse extends BaseEventResponse {
        public String beneficiary;

        public BigInteger amount;
    }

    public static class RouletteGameResultOutEventResponse extends BaseEventResponse {
        public String player;

        public BigInteger winningNumber;
    }

    public static class SlotGameResultOutEventResponse extends BaseEventResponse {
        public String player;

        public List<BigInteger> result;
    }

    public static class SpinResultOutEventResponse extends BaseEventResponse {
        public String player;

        public BigInteger choice;

        public BigInteger spinNumber;
    }
}
