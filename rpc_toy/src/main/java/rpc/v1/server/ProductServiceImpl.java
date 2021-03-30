package rpc.v1.server;

import rpc.v1.common.Product;
import rpc.v1.common.ProductService;

/**
 * @desc   TODO
 * @author liyazhou
 * @date   2021/3/30
 */
public class ProductServiceImpl implements ProductService {

    @Override
    public Product findProductById(Integer id) {
        return new Product(id, "productFromRpcServer");
    }
}
