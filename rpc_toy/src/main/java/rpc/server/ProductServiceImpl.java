package rpc.server;

import rpc.common.Product;
import rpc.common.ProductService;

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
