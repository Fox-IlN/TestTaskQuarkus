package org.task.controller;

import jakarta.inject.Inject;
import jakarta.transaction.Transactional;
import jakarta.validation.Valid;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import org.eclipse.microprofile.openapi.annotations.Operation;
import org.task.model.User;
import org.task.service.UserService;

@Path("/user")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class UserController {

    @Inject
    UserService userService;

    @GET
    @Operation(summary = "Получить всех user")
    public Response getAll() {
        return userService.getAllUsers();
    }

    @POST
    @Path("/create")
    @Transactional
    @Operation(summary = "Добавить нового user")
    public Response create(@Valid User user) {
        return userService.createUser(user);
    }

    @GET
    @Path("/{id}")
    @Operation(summary = "Найти user по id")
    public Response findById(@PathParam("id") Long id) {
        return userService.userById(id);
    }

    @DELETE
    @Path("/delete/{id}")
    @Transactional
    @Operation(summary = "Удалить user с соответствующим id")
    public Response delete(@PathParam("id") Long id) {
        return userService.deleteUser(id);
    }

    @POST
    @Path("/update/{id}")
    @Transactional
    @Operation(summary = "Обновить пользователя с соответствующим id")
    public Response update(@PathParam("id") Long id, @Valid User user) {
        return userService.updateUser(id, user);
    }

}
