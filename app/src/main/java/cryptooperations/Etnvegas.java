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
    public static final String BINARY = "608060405266071afd498d00006004556604f94ae6af800060055560016006556005600755600a600b5563494b41b3600c5563494b41b3600d556301ef15eb600e556003600f557fffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffff601055336000806101000a81548173ffffffffffffffffffffffffffffffffffffffff021916908373ffffffffffffffffffffffffffffffffffffffff1602179055506125d0806100b96000396000f3fe6080604052600436106101385760003560e01c80639d1a116c116100ab578063b93dff571161006f578063b93dff57146104ca578063bca23aa71461052d578063bff1f9e114610568578063c55b44c814610593578063e7ff3ce2146105e2578063f3bb003d146106775761013f565b80639d1a116c146103ce578063a3405f3914610409578063a482171914610434578063a6f9dae11461043e578063ae06c1b71461048f5761013f565b80634be21e33116100fd5780634be21e33146102585780635ca8e4711461029357806378571620146102be57806386b9f10a146102e95780638da5cb5b1461031457806392f8fddb1461036b5761013f565b80620b46f814610141578063047087cc1461016c57806313b9aaa0146101975780631a47b92a146101f25780634807d4df1461022d5761013f565b3661013f57005b005b34801561014d57600080fd5b506101566106d0565b6040518082815260200191505060405180910390f35b34801561017857600080fd5b506101816106d6565b6040518082815260200191505060405180910390f35b3480156101a357600080fd5b506101f0600480360360408110156101ba57600080fd5b81019080803573ffffffffffffffffffffffffffffffffffffffff16906020019092919080359060200190929190505050610785565b005b3480156101fe57600080fd5b5061022b6004803603602081101561021557600080fd5b81019080803590602001909291905050506109bc565b005b34801561023957600080fd5b50610242610a6b565b6040518082815260200191505060405180910390f35b34801561026457600080fd5b506102916004803603602081101561027b57600080fd5b8101908080359060200190929190505050610a71565b005b34801561029f57600080fd5b506102a8610b97565b6040518082815260200191505060405180910390f35b3480156102ca57600080fd5b506102d3610b9d565b6040518082815260200191505060405180910390f35b3480156102f557600080fd5b506102fe610c50565b6040518082815260200191505060405180910390f35b34801561032057600080fd5b50610329610d0d565b604051808273ffffffffffffffffffffffffffffffffffffffff1673ffffffffffffffffffffffffffffffffffffffff16815260200191505060405180910390f35b34801561037757600080fd5b506103b86004803603606081101561038e57600080fd5b81019080803590602001909291908035906020019092919080359060200190929190505050610d32565b6040518082815260200191505060405180910390f35b3480156103da57600080fd5b50610407600480360360208110156103f157600080fd5b8101908080359060200190929190505050611025565b005b34801561041557600080fd5b5061041e611376565b6040518082815260200191505060405180910390f35b61043c6113bd565b005b34801561044a57600080fd5b5061048d6004803603602081101561046157600080fd5b81019080803573ffffffffffffffffffffffffffffffffffffffff1690602001909291905050506115cd565b005b34801561049b57600080fd5b506104c8600480360360208110156104b257600080fd5b81019080803590602001909291905050506116b5565b005b3480156104d657600080fd5b50610517600480360360608110156104ed57600080fd5b810190808035906020019092919080359060200190929190803590602001909291905050506117db565b6040518082815260200191505060405180910390f35b34801561053957600080fd5b506105666004803603602081101561055057600080fd5b8101908080359060200190929190505050611ad0565b005b34801561057457600080fd5b5061057d611bf6565b6040518082815260200191505060405180910390f35b34801561059f57600080fd5b506105cc600480360360208110156105b657600080fd5b8101908080359060200190929190505050611bfc565b6040518082815260200191505060405180910390f35b3480156105ee57600080fd5b506106396004803603608081101561060557600080fd5b8101908080359060200190929190803590602001909291908035906020019092919080359060200190929190505050611e51565b6040518082600460200280838360005b83811015610664578082015181840152602081019050610649565b5050505090500191505060405180910390f35b34801561068357600080fd5b506106ce6004803603608081101561069a57600080fd5b810190808035906020019092919080359060200190929190803590602001909291908035906020019092919050505061220b565b005b60065481565b60008060009054906101000a900473ffffffffffffffffffffffffffffffffffffffff1673ffffffffffffffffffffffffffffffffffffffff163373ffffffffffffffffffffffffffffffffffffffff161461077d576040517f08c379a00000000000000000000000000000000000000000000000000000000081526004018080602001828103825260258152602001806125766025913960400191505060405180910390fd5b600a54905090565b6000809054906101000a900473ffffffffffffffffffffffffffffffffffffffff1673ffffffffffffffffffffffffffffffffffffffff163373ffffffffffffffffffffffffffffffffffffffff161461082a576040517f08c379a00000000000000000000000000000000000000000000000000000000081526004018080602001828103825260258152602001806125766025913960400191505060405180910390fd5b806108336122d2565b10156108a7576040517f08c379a00000000000000000000000000000000000000000000000000000000081526004018080602001828103825260148152602001807f496e73756666696369656e742062616c616e636500000000000000000000000081525060200191505060405180910390fd5b60085481116108c55780600860008282540392505081905550610902565b60095481116108e35780600960008282540392505081905550610901565b60008160095460085401039050806008819055506000600981905550505b5b8173ffffffffffffffffffffffffffffffffffffffff166108fc829081150290604051600060405180830381858888f19350505050158015610948573d6000803e3d6000fd5b506000809054906101000a900473ffffffffffffffffffffffffffffffffffffffff1673ffffffffffffffffffffffffffffffffffffffff167f21c12829b7985abb3407cfc423af80798f87f898de109682819192318c258c16826040518082815260200191505060405180910390a25050565b6000809054906101000a900473ffffffffffffffffffffffffffffffffffffffff1673ffffffffffffffffffffffffffffffffffffffff163373ffffffffffffffffffffffffffffffffffffffff1614610a61576040517f08c379a00000000000000000000000000000000000000000000000000000000081526004018080602001828103825260258152602001806125766025913960400191505060405180910390fd5b80600b8190555050565b60045481565b6000809054906101000a900473ffffffffffffffffffffffffffffffffffffffff1673ffffffffffffffffffffffffffffffffffffffff163373ffffffffffffffffffffffffffffffffffffffff1614610b16576040517f08c379a00000000000000000000000000000000000000000000000000000000081526004018080602001828103825260258152602001806125766025913960400191505060405180910390fd5b6000811415610b8d576040517f08c379a000000000000000000000000000000000000000000000000000000000815260040180806020018281038252600d8152602001807f496e76616c69642076616c75650000000000000000000000000000000000000081525060200191505060405180910390fd5b8060048190555050565b60055481565b60008060009054906101000a900473ffffffffffffffffffffffffffffffffffffffff1673ffffffffffffffffffffffffffffffffffffffff163373ffffffffffffffffffffffffffffffffffffffff1614610c44576040517f08c379a00000000000000000000000000000000000000000000000000000000081526004018080602001828103825260258152602001806125766025913960400191505060405180910390fd5b60095460085401905090565b60008060009054906101000a900473ffffffffffffffffffffffffffffffffffffffff1673ffffffffffffffffffffffffffffffffffffffff163373ffffffffffffffffffffffffffffffffffffffff1614610cf7576040517f08c379a00000000000000000000000000000000000000000000000000000000081526004018080602001828103825260258152602001806125766025913960400191505060405180910390fd5b610cff6122d2565b610d076122e0565b03905090565b6000809054906101000a900473ffffffffffffffffffffffffffffffffffffffff1681565b60006001841015610dab576040517f08c379a00000000000000000000000000000000000000000000000000000000081526004018080602001828103825260208152602001807f42657420616d6f756e742073686f756c64206265206d6f7265207468616e203181525060200191505060405180910390fd5b83600160003373ffffffffffffffffffffffffffffffffffffffff1673ffffffffffffffffffffffffffffffffffffffff168152602001908152602001600020541015610e60576040517f08c379a000000000000000000000000000000000000000000000000000000000815260040180806020018281038252601a8152602001807f496e73756666696369656e7420746f6b656e2062616c616e636500000000000081525060200191505060405180910390fd5b60018310158015610e735750600b548311155b610ee5576040517f08c379a000000000000000000000000000000000000000000000000000000000815260040180806020018281038252600e8152602001807f496e76616c69642063686f63696500000000000000000000000000000000000081525060200191505060405180910390fd5b83600160003373ffffffffffffffffffffffffffffffffffffffff1673ffffffffffffffffffffffffffffffffffffffff168152602001908152602001600020600082825403925050819055506000610f4083600b546122e8565b905080841415610fb357600184018502600160003373ffffffffffffffffffffffffffffffffffffffff1673ffffffffffffffffffffffffffffffffffffffff16815260200190815260200160002060008282540192505081905550838502600a60008282540192505081905550610fc4565b84600a600082825403925050819055505b3373ffffffffffffffffffffffffffffffffffffffff167f3cebb366d5bc659e09d73492dfa2cd3140907f72d7c797a47f7259bfa2744d678583604051808381526020018281526020019250505060405180910390a2809150509392505050565b600081141561109c576040517f08c379a000000000000000000000000000000000000000000000000000000000815260040180806020018281038252600a8152602001807f546f6b656e20697320300000000000000000000000000000000000000000000081525060200191505060405180910390fd5b80600160003373ffffffffffffffffffffffffffffffffffffffff1673ffffffffffffffffffffffffffffffffffffffff168152602001908152602001600020541015611151576040517f08c379a000000000000000000000000000000000000000000000000000000000815260040180806020018281038252601a8152602001807f496e73756666696369656e7420746f6b656e2062616c616e636500000000000081525060200191505060405180910390fd5b600060055482029050611162612422565b8111156111d7576040517f08c379a000000000000000000000000000000000000000000000000000000000815260040180806020018281038252601a8152602001807f616d6f756e742065786365656473206c696162696c697469657300000000000081525060200191505060405180910390fd5b600060646006548302816111e757fe5b049050600081830390506111f9612422565b81111561126e576040517f08c379a000000000000000000000000000000000000000000000000000000000815260040180806020018281038252601d8152602001807f496e73756666696369656e7420636f6e74726163742062616c616e636500000081525060200191505060405180910390fd5b83600160003373ffffffffffffffffffffffffffffffffffffffff1673ffffffffffffffffffffffffffffffffffffffff1681526020019081526020016000206000828254039250508190555083600a60008282540392505081905550816009600082825401925050819055503373ffffffffffffffffffffffffffffffffffffffff166108fc829081150290604051600060405180830381858888f19350505050158015611321573d6000803e3d6000fd5b503373ffffffffffffffffffffffffffffffffffffffff167fb2f3766da8b78571fe25726690dc8f05160ca3ec032d6491606ce7d72b61bb84826040518082815260200191505060405180910390a250505050565b6000600160003373ffffffffffffffffffffffffffffffffffffffff1673ffffffffffffffffffffffffffffffffffffffff16815260200190815260200160002054905090565b600454341015611435576040517f08c379a000000000000000000000000000000000000000000000000000000000815260040180806020018281038252601f8152602001807f53656e742076616c7565206973206c657373207468616e206d696e696d756d0081525060200191505060405180910390fd5b600260003373ffffffffffffffffffffffffffffffffffffffff1673ffffffffffffffffffffffffffffffffffffffff16815260200190815260200160002060009054906101000a900460ff166114f1576003600081548092919060010191905055506001600260003373ffffffffffffffffffffffffffffffffffffffff1673ffffffffffffffffffffffffffffffffffffffff16815260200190815260200160002060006101000a81548160ff0219169083151502179055505b600060045434816114fe57fe5b04905080600a60008282540192505081905550606460075434028161151f57fe5b0460086000828254019250508190555080600160003373ffffffffffffffffffffffffffffffffffffffff1673ffffffffffffffffffffffffffffffffffffffff168152602001908152602001600020600082825401925050819055503373ffffffffffffffffffffffffffffffffffffffff167f26820c461ce4714545b50fc641526bdcb515e08232000670d2b38afd595f9bf3826040518082815260200191505060405180910390a250565b6000809054906101000a900473ffffffffffffffffffffffffffffffffffffffff1673ffffffffffffffffffffffffffffffffffffffff163373ffffffffffffffffffffffffffffffffffffffff1614611672576040517f08c379a00000000000000000000000000000000000000000000000000000000081526004018080602001828103825260258152602001806125766025913960400191505060405180910390fd5b806000806101000a81548173ffffffffffffffffffffffffffffffffffffffff021916908373ffffffffffffffffffffffffffffffffffffffff16021790555050565b6000809054906101000a900473ffffffffffffffffffffffffffffffffffffffff1673ffffffffffffffffffffffffffffffffffffffff163373ffffffffffffffffffffffffffffffffffffffff161461175a576040517f08c379a00000000000000000000000000000000000000000000000000000000081526004018080602001828103825260258152602001806125766025913960400191505060405180910390fd5b60008114156117d1576040517f08c379a000000000000000000000000000000000000000000000000000000000815260040180806020018281038252600d8152602001807f496e76616c69642076616c75650000000000000000000000000000000000000081525060200191505060405180910390fd5b8060068190555050565b600080600690506001851015611859576040517f08c379a00000000000000000000000000000000000000000000000000000000081526004018080602001828103825260208152602001807f42657420616d6f756e742073686f756c64206265206d6f7265207468616e203181525060200191505060405180910390fd5b84600160003373ffffffffffffffffffffffffffffffffffffffff1673ffffffffffffffffffffffffffffffffffffffff16815260200190815260200160002054101561190e576040517f08c379a000000000000000000000000000000000000000000000000000000000815260040180806020018281038252601a8152602001807f496e73756666696369656e7420746f6b656e2062616c616e636500000000000081525060200191505060405180910390fd5b6001841015801561191f5750808411155b611991576040517f08c379a000000000000000000000000000000000000000000000000000000000815260040180806020018281038252600e8152602001807f496e76616c69642063686f63696500000000000000000000000000000000000081525060200191505060405180910390fd5b84600160003373ffffffffffffffffffffffffffffffffffffffff1673ffffffffffffffffffffffffffffffffffffffff1681526020019081526020016000206000828254039250508190555060006119ea84836122e8565b905080851415611a5d57600185018602600160003373ffffffffffffffffffffffffffffffffffffffff1673ffffffffffffffffffffffffffffffffffffffff16815260200190815260200160002060008282540192505081905550848602600a60008282540192505081905550611a6e565b85600a600082825403925050819055505b3373ffffffffffffffffffffffffffffffffffffffff167f7fbe42f4b44cc8df502860f23e5e6c8e096c46816c45d1d246263e44a1272de58683604051808381526020018281526020019250505060405180910390a280925050509392505050565b6000809054906101000a900473ffffffffffffffffffffffffffffffffffffffff1673ffffffffffffffffffffffffffffffffffffffff163373ffffffffffffffffffffffffffffffffffffffff1614611b75576040517f08c379a00000000000000000000000000000000000000000000000000000000081526004018080602001828103825260258152602001806125766025913960400191505060405180910390fd5b6000811415611bec576040517f08c379a000000000000000000000000000000000000000000000000000000000815260040180806020018281038252600d8152602001807f496e76616c69642076616c75650000000000000000000000000000000000000081525060200191505060405180910390fd5b8060058190555050565b60035481565b600080821415611c74576040517f08c379a000000000000000000000000000000000000000000000000000000000815260040180806020018281038252600a8152602001807f546f6b656e20697320300000000000000000000000000000000000000000000081525060200191505060405180910390fd5b81600160003373ffffffffffffffffffffffffffffffffffffffff1673ffffffffffffffffffffffffffffffffffffffff168152602001908152602001600020541015611d29576040517f08c379a000000000000000000000000000000000000000000000000000000000815260040180806020018281038252601a8152602001807f496e73756666696369656e7420746f6b656e2062616c616e636500000000000081525060200191505060405180910390fd5b600060055483029050611d3a612422565b811115611daf576040517f08c379a000000000000000000000000000000000000000000000000000000000815260040180806020018281038252601a8152602001807f616d6f756e742065786365656473206c696162696c697469657300000000000081525060200191505060405180910390fd5b60006064600654830281611dbf57fe5b04905060008183039050611dd1612422565b811115611e46576040517f08c379a000000000000000000000000000000000000000000000000000000000815260040180806020018281038252601d8152602001807f496e73756666696369656e7420636f6e74726163742062616c616e636500000081525060200191505060405180910390fd5b809350505050919050565b611e59612531565b6001851015611ed0576040517f08c379a00000000000000000000000000000000000000000000000000000000081526004018080602001828103825260208152602001807f42657420616d6f756e742073686f756c64206265206d6f7265207468616e203181525060200191505060405180910390fd5b84600160003373ffffffffffffffffffffffffffffffffffffffff1673ffffffffffffffffffffffffffffffffffffffff168152602001908152602001600020541015611f85576040517f08c379a000000000000000000000000000000000000000000000000000000000815260040180806020018281038252601a8152602001807f496e73756666696369656e7420746f6b656e2062616c616e636500000000000081525060200191505060405180910390fd5b84600160003373ffffffffffffffffffffffffffffffffffffffff1673ffffffffffffffffffffffffffffffffffffffff16815260200190815260200160002060008282540392505081905550611fda612553565b611fe685600b546122e8565b81600060038110611ff357fe5b60200201818152505061200884600b546122e8565b8160016003811061201557fe5b60200201818152505061202a83600b546122e8565b8160026003811061203757fe5b602002018181525050612048612531565b60006120538361243a565b90508260006003811061206257fe5b60200201518260006004811061207457fe5b6020020181815250508260016003811061208a57fe5b60200201518260016004811061209c57fe5b602002018181525050826002600381106120b257fe5b6020020151826002600481106120c457fe5b60200201818152505060008114156120dd5760006120e0565b60015b60ff16826003600481106120f057fe5b6020020181815250503373ffffffffffffffffffffffffffffffffffffffff167fcaa1b5a9d41febce64abb4f9320bafbdceee9c1da0165302cc8283930b734a55836040518082600460200280838360005b8381101561215d578082015181840152602081019050612142565b5050505090500191505060405180910390a2600281148061217e5750600381145b156121ec57600181018802600160003373ffffffffffffffffffffffffffffffffffffffff1673ffffffffffffffffffffffffffffffffffffffff16815260200190815260200160002060008282540192505081905550808802600a600082825401925050819055506121fd565b87600a600082825403925050819055505b819350505050949350505050565b6000809054906101000a900473ffffffffffffffffffffffffffffffffffffffff1673ffffffffffffffffffffffffffffffffffffffff163373ffffffffffffffffffffffffffffffffffffffff16146122b0576040517f08c379a00000000000000000000000000000000000000000000000000000000081526004018080602001828103825260258152602001806125766025913960400191505060405180910390fd5b83600c8190555082600d8190555081600e8190555080600f8190555050505050565b600060095460085401905090565b600047905090565b60008082424486600c54600d54600e5433604051602001808881526020018781526020018681526020018581526020018481526020018381526020018273ffffffffffffffffffffffffffffffffffffffff1673ffffffffffffffffffffffffffffffffffffffff1660601b81526014019750505050505050506040516020818303038152906040528051906020012060001c8161238257fe5b0690506001600f546010540303600c54106123a35763494b41b3600c819055505b6001600f546010540303600d54106123c15763494b41b3600d819055505b6001600f546010540303600e54106123df576301ef15eb600e819055505b600f54600c60008282540192505081905550600f54600d60008282540192505081905550600f54600e600082825401925050819055506001810191505092915050565b600061242c6122d2565b6124346122e0565b03905090565b60008160016003811061244957fe5b60200201518260006003811061245b57fe5b602002015114801561248e57508160026003811061247557fe5b60200201518260016003811061248757fe5b6020020151145b1561249c576003905061252c565b816001600381106124a957fe5b6020020151826000600381106124bb57fe5b602002015114806124ed5750816002600381106124d457fe5b6020020151826000600381106124e657fe5b6020020151145b8061251957508160026003811061250057fe5b60200201518260016003811061251257fe5b6020020151145b15612527576002905061252c565b600090505b919050565b6040518060800160405280600490602082028036833780820191505090505090565b604051806060016040528060039060208202803683378082019150509050509056fe4f6e6c7920746865206f776e65722063616e2063616c6c20746869732066756e6374696f6ea26469706673582212204723cbbf6ff8b470a6d8f0a92ad570c662d829db3b824c6785a622a30625b74864736f6c63430006090033";

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

    public static final String FUNC_ROLLADICE = "rollADice";

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

    public static final Event DICEROLLRESULTOUT_EVENT = new Event("DiceRollResultOut",
            Arrays.<TypeReference<?>>asList(new TypeReference<Address>(true) {}, new TypeReference<Uint256>() {}, new TypeReference<Uint256>() {}));
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

    public static List<DiceRollResultOutEventResponse> getDiceRollResultOutEvents(
            TransactionReceipt transactionReceipt) {
        List<Contract.EventValuesWithLog> valueList = staticExtractEventParametersWithLog(DICEROLLRESULTOUT_EVENT, transactionReceipt);
        ArrayList<DiceRollResultOutEventResponse> responses = new ArrayList<DiceRollResultOutEventResponse>(valueList.size());
        for (Contract.EventValuesWithLog eventValues : valueList) {
            DiceRollResultOutEventResponse typedResponse = new DiceRollResultOutEventResponse();
            typedResponse.log = eventValues.getLog();
            typedResponse.player = (String) eventValues.getIndexedValues().get(0).getValue();
            typedResponse.choice = (BigInteger) eventValues.getNonIndexedValues().get(0).getValue();
            typedResponse.diceNumber = (BigInteger) eventValues.getNonIndexedValues().get(1).getValue();
            responses.add(typedResponse);
        }
        return responses;
    }

    public static DiceRollResultOutEventResponse getDiceRollResultOutEventFromLog(Log log) {
        Contract.EventValuesWithLog eventValues = staticExtractEventParametersWithLog(DICEROLLRESULTOUT_EVENT, log);
        DiceRollResultOutEventResponse typedResponse = new DiceRollResultOutEventResponse();
        typedResponse.log = log;
        typedResponse.player = (String) eventValues.getIndexedValues().get(0).getValue();
        typedResponse.choice = (BigInteger) eventValues.getNonIndexedValues().get(0).getValue();
        typedResponse.diceNumber = (BigInteger) eventValues.getNonIndexedValues().get(1).getValue();
        return typedResponse;
    }

    public Flowable<DiceRollResultOutEventResponse> diceRollResultOutEventFlowable(
            EthFilter filter) {
        return web3j.ethLogFlowable(filter).map(log -> getDiceRollResultOutEventFromLog(log));
    }

    public Flowable<DiceRollResultOutEventResponse> diceRollResultOutEventFlowable(
            DefaultBlockParameter startBlock, DefaultBlockParameter endBlock) {
        EthFilter filter = new EthFilter(startBlock, endBlock, getContractAddress());
        filter.addSingleTopic(EventEncoder.encode(DICEROLLRESULTOUT_EVENT));
        return diceRollResultOutEventFlowable(filter);
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

    public RemoteFunctionCall<TransactionReceipt> rollADice(BigInteger betAmount, BigInteger choice,
                                                            BigInteger _randomnessSource1) {
        final Function function = new Function(
                FUNC_ROLLADICE,
                Arrays.<Type>asList(new org.web3j.abi.datatypes.generated.Uint256(betAmount),
                        new org.web3j.abi.datatypes.generated.Uint256(choice),
                        new org.web3j.abi.datatypes.generated.Uint256(_randomnessSource1)),
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

    public static class DiceRollResultOutEventResponse extends BaseEventResponse {
        public String player;

        public BigInteger choice;

        public BigInteger diceNumber;
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
