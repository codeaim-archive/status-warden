package com.codeaim.statuswarden.api.controller;

import com.codeaim.statuswarden.common.exception.AlreadyExistsException;
import com.codeaim.statuswarden.common.exception.MethodNotAllowedException;
import com.codeaim.statuswarden.common.exception.NotFoundException;
import com.codeaim.statuswarden.common.exception.UnauthorizedException;
import com.codeaim.statuswarden.common.model.User;
import com.codeaim.statuswarden.common.repository.UserRepository;
import com.codeaim.statuswarden.common.utility.Security;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.Optional;
import java.util.UUID;

@RestController
@RequestMapping("/user")
public class UserController extends AbstractController
{
    @Autowired
    private UserRepository userRepository;

    @RequestMapping(value = "/access-token", method = RequestMethod.POST)
    public ResponseEntity<String> accessToken(
        @RequestParam(value = "email", required = true) String email,
        @RequestParam(value = "password", required = true) String password)
    {
        Optional<User> optionalUser = userRepository.findByEmail(email);
        if (!optionalUser.isPresent())
        {
            throw new NotFoundException();
        }

        User user = optionalUser.get();

        if (!user.isEmailVerified())
        {
            throw new MethodNotAllowedException();
        }

        if (Security.hashToString(Security.hashUsingSHA256(user.getSalt() + password)).equals(user.getPasswordHash()))
        {
            return new ResponseEntity<>(
                userRepository.save(User.buildFrom(user).accessToken(UUID.randomUUID().toString()).build()).getAccessToken(),
                HttpStatus.OK
            );
        }

        throw new UnauthorizedException();
    }

    @RequestMapping(method = RequestMethod.POST)
    public ResponseEntity create(
        @RequestParam(value = "name", required = true) String name,
        @RequestParam(value = "email", required = true) String email,
        @RequestParam(value = "password", required = true) String password)
    {
        Optional<User> optionalUser = userRepository.findByEmail(email);
        if (optionalUser.isPresent())
        {
            throw new AlreadyExistsException();
        }

        String passwordSalt = UUID.randomUUID().toString();

        userRepository.save(User.builder()
            .accessToken(UUID.randomUUID().toString())
            .email(email)
            .name(name)
            .salt(passwordSalt)
            .passwordHash(Security.hashToString(Security.hashUsingSHA256(passwordSalt + password)))
            .build());

        return new ResponseEntity(HttpStatus.CREATED);
    }
}
