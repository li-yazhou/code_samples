package rpc.client;

import rpc.common.Product;
import rpc.common.ProductService;
import rpc.common.User;
import rpc.common.UserService;
import rpc.server.ProductServiceImpl;
import rpc.server.UserServiceImpl;

import java.io.Serializable;

/**
 * @desc   TODO
 * @author liyazhou
 */
public class Client {

    public static void main(String[] args) {
        findUserById();
        findProductById();
    }


    private static void findUserById() {
        UserService userService = (UserService) Stub.getStub(UserServiceImpl.class);
        User user = userService.findUserById(1001);
        System.out.println("user = " + user);
    }

    private static void findProductById() {
        ProductService productService = (ProductService) Stub.getStub(ProductServiceImpl.class);
        Product product = productService.findProductById(2001);
        System.out.println("product = " + product);

    }
}
