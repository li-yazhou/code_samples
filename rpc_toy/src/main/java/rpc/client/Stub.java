package rpc.client;

import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;
import java.net.Socket;
import java.util.Arrays;

/**
 * @desc   TODO
 * @author liyazhou
 */
public class Stub {


    public static Object getStub(Class clazz) {

        InvocationHandler invocationHandler = new InvocationHandler() {
            @Override
            public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {

                Socket socket = new Socket("127.0.0.1", 6666);

                ObjectOutputStream objectOutputStream = new ObjectOutputStream(socket.getOutputStream());

                String className = clazz.getName();
                String methodName = method.getName();
                Class[] parameterTypes = method.getParameterTypes();

                StringBuilder str = new StringBuilder();
                str.append("className=").append(className).append(",")
                    .append("methodName=").append(methodName).append(",")
                    .append("parameterTypes=").append(Arrays.toString(parameterTypes)).append(",")
                    .append("args=").append(Arrays.toString(args));
                System.out.println(str);

                objectOutputStream.writeUTF(className);
                objectOutputStream.writeUTF(methodName);
                objectOutputStream.writeObject(parameterTypes);
                objectOutputStream.writeObject(args);
                objectOutputStream.flush();


                ObjectInputStream objectInputStream = new ObjectInputStream(socket.getInputStream());
                Object result = objectInputStream.readObject();

                objectOutputStream.close();
                objectInputStream.close();

                return result;
            }
        };

        System.out.println("Interfaces = " + Arrays.toString(clazz.getInterfaces()));
        return Proxy.newProxyInstance(clazz.getClassLoader(), clazz.getInterfaces(), invocationHandler);
    }


}
