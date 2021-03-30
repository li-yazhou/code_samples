package rpc.common;

import java.io.Serializable;

/**
 * @desc   TODO
 * @author liyazhou
 */
public class User implements Serializable {
    Integer id;
    String name;

    long serialVersionUID = 10001;

    public User(Integer id, String name) {
        this.id = id;
        this.name = name;
    }

    @Override
    public String toString() {
        return "User{" +
                "id=" + id +
                ", name='" + name + '\'' +
                '}';
    }
}
