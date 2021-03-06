package io.github.mickey.shardingsphere.v1.repository;

import io.github.mickey.shardingsphere.v1.domain.User;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * @author mickey
 * @date 3/7/21 00:58
 */
public interface UserRepository extends JpaRepository<User, Long> {
}
