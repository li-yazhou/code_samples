package rpc.v2;

public class RpcProvider {

	public static void main(String[] args) throws Exception {
		// TODO Auto-generated method stub
		HelloServiceImpl service = new HelloServiceImpl();  
        RpcFramework.export(service, 1234);  

	}

}