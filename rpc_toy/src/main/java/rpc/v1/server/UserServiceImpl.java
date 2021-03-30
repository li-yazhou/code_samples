package rpc.v1.server;

import rpc.v1.common.User;
import rpc.v1.common.UserService;

/**
 * @desc   TODO
 * @author liyazhou
 * @date   2021/3/30
 */
public class UserServiceImpl implements UserService {

    @Override
    public User findUserById(Integer id) {
        return new User(id, "userFromRpcServer");
    }
}
