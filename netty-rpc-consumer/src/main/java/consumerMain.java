import com.adru.api.IUserService;
import com.adru.proxy.RpcClientProxy;

public class consumerMain {
    public static void main(String[] args) {
        RpcClientProxy rpcClientProxy = new RpcClientProxy();
        IUserService iUserService = rpcClientProxy.clientProxy(IUserService.class, "127.0.0.1", 8080);
        System.out.println(iUserService.isSuccess("wuing"));
        System.out.println("sadsadas--------------------------------------------");
    }
}
