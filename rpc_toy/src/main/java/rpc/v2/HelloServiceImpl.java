package rpc.v2;

public class HelloServiceImpl implements HellService{

	public String hello(String name) {
		// TODO Auto-generated method stub
		String helloName = "Hello " + name;
		System.out.println(helloName);
		return helloName;
	}

}