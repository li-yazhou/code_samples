package rpc.v1.client;

import rpc.v1.common.Product;
import rpc.v1.common.ProductService;
import rpc.v1.common.User;
import rpc.v1.common.UserService;
import rpc.v1.server.ProductServiceImpl;
import rpc.v1.server.UserServiceImpl;

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
