package rpc.server;

import java.io.*;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Arrays;

/**
 * @desc   TODO
 * @author liyazhou
 */
public class Server {


    public static void main(String[] args) throws IOException, ClassNotFoundException, InvocationTargetException, NoSuchMethodException, InstantiationException, IllegalAccessException {

        ServerSocket serverSocket = new ServerSocket(6666);

        while (true) {
            Socket socket = serverSocket.accept();
            process(socket);
            socket.close();
        }
    }

    private static void process(Socket socket) throws IOException, ClassNotFoundException, NoSuchMethodException, IllegalAccessException, InstantiationException, InvocationTargetException {
        InputStream inputStream = socket.getInputStream();
        OutputStream outputStream = socket.getOutputStream();

        ObjectInputStream objectInputStream = new ObjectInputStream(inputStream);

        String className = objectInputStream.readUTF();
        String methodName = objectInputStream.readUTF();
        Class[] parameterTypes = (Class[]) objectInputStream.readObject();
        Object[] args = (Object[]) objectInputStream.readObject();

        StringBuilder str = new StringBuilder();
        str.append("className=").append(className).append(",")
                .append("methodName=").append(methodName).append(",")
                .append("parameterTypes=").append(Arrays.toString(parameterTypes)).append(",")
                .append("args=").append(Arrays.toString(args));
        System.out.println(str);

        // 从服务注册找到具体的类
        Class clazz = null;
        if (className.contains("UserService")) {
            clazz = UserServiceImpl.class;
        } else if (className.contains("ProductService")) {
            clazz = ProductServiceImpl.class;
        }

        Method method = clazz.getMethod(methodName, parameterTypes);
        Object result = method.invoke(clazz.newInstance(), args);

        ObjectOutputStream objectOutputStream = new ObjectOutputStream(outputStream);
        objectOutputStream.writeObject(result);
        objectOutputStream.flush();
    }

}
