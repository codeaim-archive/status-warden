package com.codeaim.statuswarden.web.configuration;

import com.codeaim.statuswarden.common.model.User;
import com.codeaim.statuswarden.common.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.stream.Collectors;

@Repository
public class UserService implements UserDetailsService
{
    @Autowired
    private UserRepository userRepository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException
    {
        final Optional<User> user = userRepository.findByEmail(username);
        if(user.isPresent())
        {
            return new org.springframework.security.core.userdetails.User(
                user.get().getEmail(),
                user.get().getPassword(),
                true,
                true,
                true,
                true,
                user.get().getRoles().stream().map(SimpleGrantedAuthority::new).collect(Collectors.toList()));
        }

        throw new UsernameNotFoundException(String.format("User with email %s not found", username));
    }
}
