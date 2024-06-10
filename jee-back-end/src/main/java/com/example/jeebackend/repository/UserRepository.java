package com.example.jeebackend.repository;

import com.example.jeebackend.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Long> {
    boolean existsByEmail(String email);
    Optional<User> findByLoginAndPassword(String login, String password);
    @Query("SELECT u FROM User u JOIN u.hands o WHERE o.id = :id")
    List<User> findUsersWithOfferInHands(@Param("id") Long id);

    @Query("SELECT count(*) FROM User u JOIN u.hands o WHERE o.id = :id")
    int countUsersWithOfferInHands(@Param("id") Long id);

    @Query("SELECT u FROM User u JOIN u.likes o WHERE o.id = :id")
    List<User> findUsersWithOfferInLike(@Param("id") Long id);

    @Query("SELECT count(*) FROM User u JOIN u.likes o WHERE o.id = :id")
    int countUsersWithOfferInLike(@Param("id") Long id);


    @Query("SELECT u FROM User u JOIN u.myOffres o WHERE o.id = :id")
    Optional<User> findUserbyMyOffre(@Param("id") Long id);
}
