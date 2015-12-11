package com.codeaim.statuswarden.web.api.controller;

import com.codeaim.statuswarden.common.exception.AlreadyExistsException;
import com.codeaim.statuswarden.common.exception.MethodNotAllowedException;
import com.codeaim.statuswarden.common.exception.UnauthorizedException;
import com.codeaim.statuswarden.common.model.User;
import com.codeaim.statuswarden.common.utility.Security;
import com.codeaim.statuswarden.common.exception.NotFoundException;
import com.codeaim.statuswarden.common.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.Optional;
import java.util.UUID;

@RestController
@RequestMapping("/api/user")
public class UserApiController extends AbstractApiController
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

        if (Security.hashToString(Security.hashUsingSHA256(password)).equals(user.getPassword()))
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

        userRepository.save(User.builder()
            .accessToken(UUID.randomUUID().toString())
            .email(email)
            .name(name)
            .password(new BCryptPasswordEncoder().encode(password))
            .build());

        return new ResponseEntity(HttpStatus.CREATED);
    }
}
