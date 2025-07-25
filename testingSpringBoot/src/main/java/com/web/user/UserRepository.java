package com.web.user;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;


import java.util.List;
import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<UserEntity, Long> {


    // get only filed username
    @Query("SELECT u.username FROM UserEntity u WHERE u.id = :id")
    String findUsernameById(@Param("id") Long id);

    Optional<UserEntity> findByUsername(String username);

    // Kiểm tra id đã tồn tại hay chưa
    boolean existsById(@Param("id") Long id);

    // Kiểm tra username đã tồn tại hay chưa
    boolean existsByUsername(String username);


    // Kiểm tra email đã tồn tại hay chưa
    boolean existsByEmail(String email);

    // JPA query
//    @Query("SELECT u FROM UserEntity u WHERE u.username = :username OR u.email = :email")
//    List<UserEntity> findByUsernameOrEmail(@Param("username") String username,
//                                           @Param("email") String email);


//    Optional<UserEntity> save(UserEntity userEntity);
}
