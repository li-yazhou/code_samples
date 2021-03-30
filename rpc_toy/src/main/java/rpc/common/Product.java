package rpc.common;

import java.io.Serializable;

/**
 * @desc   TODO
 * @author liyazhou
 */
public class Product implements Serializable {
    Integer id;
    String name;

    long serialVersionUID = 20001;

    public Product(Integer id, String name) {
        this.id = id;
        this.name = name;
    }

    @Override
    public String toString() {
        return "Product{" +
                "id=" + id +
                ", name='" + name + '\'' +
                '}';
    }
}
