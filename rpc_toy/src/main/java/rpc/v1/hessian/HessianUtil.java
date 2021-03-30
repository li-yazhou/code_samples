package rpc.v1.hessian;

import com.caucho.hessian.io.Hessian2Input;
import com.caucho.hessian.io.Hessian2Output;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;

/**
 * @desc   TODO
 * @author liyazhou
 */
public class HessianUtil {

    public static byte[] jdkSerialize(Object object) throws Exception {
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();

        ObjectOutputStream outputStream = new ObjectOutputStream(byteArrayOutputStream);
        outputStream.writeObject(object);
        outputStream.flush();

        byte[] bytes = byteArrayOutputStream.toByteArray();

        byteArrayOutputStream.close();
        outputStream.close();
        return bytes;
    }

    public static Object jdkDeserialize(byte[] bytes) throws Exception {
        ByteArrayInputStream byteArrayInputStream = new ByteArrayInputStream(bytes);
        ObjectInputStream inputStream = new ObjectInputStream(byteArrayInputStream);

        Object object = inputStream.readObject();

        inputStream.close();
        byteArrayInputStream.close();
        return object;
    }


    // Hessian 序列化效率更高，占用空间更小
    public static byte[] hessianSerialize(Object object) throws Exception {
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();

        Hessian2Output outputStream = new Hessian2Output(byteArrayOutputStream);
        outputStream.writeObject(object);
        outputStream.flush();

        byte[] bytes = byteArrayOutputStream.toByteArray();

        byteArrayOutputStream.close();
        outputStream.close();
        return bytes;
    }

    public static Object hessianDeserialize(byte[] bytes) throws Exception {
        ByteArrayInputStream byteArrayInputStream = new ByteArrayInputStream(bytes);
        Hessian2Input inputStream = new Hessian2Input(byteArrayInputStream);

        Object object = inputStream.readObject();

        inputStream.close();
        byteArrayInputStream.close();
        return object;
    }
}
