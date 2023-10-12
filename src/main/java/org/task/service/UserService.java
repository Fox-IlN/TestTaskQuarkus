package org.task.service;

import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.ws.rs.core.Response;
import org.task.repository.UserRepository;
import org.task.exception.CustomException;
import org.task.model.User;

import java.util.List;

@ApplicationScoped
public class UserService {
    @Inject
    UserRepository userRepository;

    public String emailIsNotUnique = "Email не уникальный";
    public String usersNotFound = "Пустой список";
    public String userNotFound = "User с указанным ID не найден";

    public Response getAllUsers() {                                   //Метод для получения всех записей из таблицы БД users и возврата ответа на клиент
        List<User> usersList = userRepository.findAll().list();
        if (usersList.isEmpty()) {
            throw new CustomException(usersNotFound);
        }
        return Response.ok()                                          //Создаем новый ResponseBuilder со статусом 200
                .entity(usersList)                                    //Передаем коллекцию как объект ответа
                .build();                                             //Создаем экземпляр Response
    }

    public User findUsersAndValidationByEmail(User user){             //Метод для проверки уникальности email
        List<User> usersByEmailList = userRepository.findByEmail(user.getEmail());
        if (!usersByEmailList.isEmpty()) {
            throw new CustomException(emailIsNotUnique);
        }
        return user;
    }

    public Response createUser(User user) {                           //Метод для сохранения User в БД
        findUsersAndValidationByEmail(user);
        userRepository.persist(user);
        return Response.ok().entity(user).build();
    }

    public User findUserById(Long id){                                //Метод для поиска User по id
        User userById = userRepository.findById(id);
        if(userById == null){
            throw new CustomException(userNotFound);
        }
        return userById;
    }
    
    public Response userById(Long id){                                //Метод для возврата ответа на запрос поиска User по id
        User user = findUserById(id);
        return Response.ok().entity(user).build();
    }

    public Response deleteUser(Long id){                              //Метод для удаления User из БД по id
        User user = findUserById(id);
        userRepository.delete(user);
        return Response.ok().build();
    }

    public Response updateUser(Long id, User user){                   //Метод для обновления записи User в БД по id
        User userEntity = findUserById(id);
        userEntity.setName(user.getName());
        userEntity.setEmail(findUsersAndValidationByEmail(user).getEmail());
        userEntity.setPassword(user.getPassword());
        userRepository.persist(userEntity);
        return Response.ok().entity(userEntity).build();
    }

}
