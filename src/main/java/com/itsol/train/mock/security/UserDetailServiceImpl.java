package com.itsol.train.mock.security;

import com.itsol.train.mock.domain.User;
import com.itsol.train.mock.repo.UserRepository;
import org.hibernate.validator.internal.constraintvalidators.hv.EmailValidator;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

/**
 *
 */
@Component("userDetailsService")
public class UserDetailServiceImpl implements UserDetailsService {

    private Logger log = LoggerFactory.getLogger(UserDetailServiceImpl.class);

    private final UserRepository userRepository;

    public UserDetailServiceImpl(UserRepository userRepository){
        this.userRepository = userRepository;
    }

    @Override
    @Transactional
    public UserDetails loadUserByUsername(String login) throws UsernameNotFoundException {
        log.trace("Service authenticate: {}", login);
        if (new EmailValidator().isValid(login, null)) {
            return userRepository.findOneWithAuthoritiesByEmail(login)
                    .map(user -> createSpringSecurityUser(login, user))
                    .orElseThrow(() -> new UsernameNotFoundException("User with email " + login + " not found in the database"));
        }

        return userRepository.findOneWithAuthoritiesByUsername(login)
                .map(user -> createSpringSecurityUser(login, user))
                .orElseThrow(() -> new UsernameNotFoundException("User " + login + " not found in the database"));

    }

    private org.springframework.security.core.userdetails.User createSpringSecurityUser(String login, User user) {
        if (!user.getActivated()) {
            throw new UserNotActivatedException("User " + login + " was not activated");
        }
        List<GrantedAuthority> grantedAuthorities = user.getAuthorities()
                .stream().map(authority -> new SimpleGrantedAuthority(authority.getName()))
                .collect(Collectors.toList());
        return new org.springframework.security.core.userdetails.User(user.getUsername(),
                user.getPassword(), grantedAuthorities);
    }
}
