package io.github.mickey.rpc.fx.api;

import io.github.mickey.rpc.fx.bean.domain.User;
import io.github.mickey.rpc.fx.bean.request.UserRequest;

public interface UserService {

    User getUser(UserRequest userRequest);

}
