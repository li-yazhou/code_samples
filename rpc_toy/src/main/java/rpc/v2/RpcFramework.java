package rpc.v2;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;
import java.net.ServerSocket;
import java.net.Socket;


public class RpcFramework {

	/**
	 * 暴露服务
	 * @param service 服务实现
	 * @param port 服务端口
	 * @throws Exception 异常处理
	 */
	public static void export(final Object service, int port) throws Exception {
		if (service == null) {
			throw new IllegalArgumentException("service instance == null");
		}
		if (port <= 0 || port > 65535) {
			throw new IllegalArgumentException("Invalid port " + port);
		}
		System.out.println("Export service " + service.getClass().getName() + " on port " + port);

		// 一个端口仅仅处理一个类型的服务
		ServerSocket server = new ServerSocket(port);
		for (;;) {
			try {
				final Socket socket = server.accept();
				new Thread(new Runnable() {

					public void run() {
						// TODO Auto-generated method stub
						try {
							ObjectInputStream inputStream = new ObjectInputStream(socket.getInputStream());
							try {

								// TODO 待完善，根据接口选择实例化服务的实现类（需要维护接口与实现类的关系，并缓存起来），并提供调用服务
								String methodName = inputStream.readUTF();
								Class<?>[] parameterTypes = (Class<?>[])inputStream.readObject();
								Object[] arguments = (Object[])inputStream.readObject();
								
								ObjectOutputStream outputStream = new ObjectOutputStream(socket.getOutputStream());
								try {
									Method method = service.getClass().getMethod(methodName, parameterTypes);
									try {
										Object result = method.invoke(service, arguments);
										outputStream.writeObject(result);
									} catch (Throwable t) {
										outputStream.writeObject(t);
									} finally {
										outputStream.close();
									}
								} catch (Exception e) {
									// TODO Auto-generated catch block
									e.printStackTrace();
								} finally {
									inputStream.close();
								}
							} catch (ClassNotFoundException e) {
								// TODO Auto-generated catch block
								e.printStackTrace();
							}  finally {
								socket.close();
							}
						} catch (IOException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}

					}

				}).start();
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	}

	/**
	 * 
	 * @param interfaceClass 接口泛型
	 * @param host 服务主机
	 * @param port 服务端口
	 * @return 远程服务
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	public static <T> T refer(final Class<T> interfaceClass, final String host, final int port) throws Exception {
		
		if (interfaceClass == null) {
			throw new IllegalArgumentException("InterfaceClass is null");
		}
		if (! interfaceClass.isInterface()) {
			throw new IllegalArgumentException("The " + interfaceClass.getName() + "must be interfaces class");
		}
		if (host == null || host.length() == 0) {
			throw new IllegalArgumentException("Host is null");
		}
		if (port <=0 || port > 65535) {
			throw new IllegalArgumentException("Invalid Port : " + port);
		}
		
		System.out.println("Get remote service " + interfaceClass.getName() + " from server " + host + ":" + port);
		
		return (T) Proxy.newProxyInstance(interfaceClass.getClassLoader(), new Class<?>[] {interfaceClass} , new InvocationHandler(){

			public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
				
				Socket socket = new Socket(host, port);

				try {
					ObjectOutputStream outputStream = new ObjectOutputStream(socket.getOutputStream());
					try {
						outputStream.writeUTF(method.getName());  
						outputStream.writeObject(method.getParameterTypes());  
						outputStream.writeObject(args);
						
						ObjectInputStream inputStream = new ObjectInputStream(socket.getInputStream());
						try {
							Object result = inputStream.readObject();
							if (result instanceof Throwable) {
								throw (Throwable)result;
							}
							return result;
						} catch (Exception e) {
							e.printStackTrace();
						} finally {
							inputStream.close();
						}
						
					} catch (Exception e) {
						e.printStackTrace();
					}  finally {
						outputStream.close();
					}
					
				} catch (Exception e) {
					e.printStackTrace();
				} finally {
					
				}
				
				return null;
			}
		});	
	}
}