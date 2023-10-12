package org.task.repository;

import io.quarkus.hibernate.orm.panache.PanacheRepository;
import jakarta.enterprise.context.ApplicationScoped;
import org.task.model.User;

import java.util.List;

@ApplicationScoped
public class UserRepository implements PanacheRepository<User> {

    public List<User> findByEmail(String email){
        return find("email", email).list();
    };
}
