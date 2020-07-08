package com.itsol.train.mock.service.impl;

import com.itsol.train.mock.dao.UserDAO;
import com.itsol.train.mock.dto.UserDto;
import com.itsol.train.mock.dto.UserSearchDto;
import com.itsol.train.mock.exception.EmailExistException;
import com.itsol.train.mock.exception.UsernameExistException;
import com.itsol.train.mock.security.AuthoritiesConstants;
import com.itsol.train.mock.domain.Authority;
import com.itsol.train.mock.domain.User;
import com.itsol.train.mock.repo.UserRepository;
import com.itsol.train.mock.security.SecurityUtils;
import com.itsol.train.mock.service.UserService;
import org.modelmapper.ModelMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;

@Service
public class UserServiceImpl implements UserService {

    private Logger log = LoggerFactory.getLogger(getClass());

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private ModelMapper modelMapper;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private UserDAO userDAO;

    @Override
    public void register(UserDto userDto) throws UsernameExistException, EmailExistException {
        log.trace("Service to register user in web site");
        String username = userDto.getUsername();
        Optional<User> checkUsername = userRepository.findOneWithAuthoritiesByUsername(username);
        if(checkUsername.isPresent()){
            throw new UsernameExistException("Username has exist in database");
        }
        Optional<User> checkEmail = userRepository.findOneWithAuthoritiesByEmail(username);
        if(checkEmail.isPresent()){
            throw new EmailExistException("Email has exist in database");
        }
        User entity = modelMapper.map(userDto, User.class);
        entity.setPassword(passwordEncoder.encode(entity.getPassword()));
        Set<Authority> authorities = new HashSet<>(Collections
                .singletonList(new Authority(AuthoritiesConstants.WEB_USER)));
        entity.setAuthorities(authorities);
        entity.setActivated(Boolean.FALSE);
        userRepository.save(entity);
    }

    @Transactional(readOnly = true)
    public Optional<User> getUserWithAuthorities() {
        return SecurityUtils.getCurrentUserLogin().flatMap(userRepository::findOneWithAuthoritiesByUsername);
    }

    @Override
    public List<UserDto> getAllUserNotActive(UserSearchDto userSearchDto) {
        log.trace("Service to get all user not active: {}", userSearchDto);
//        return userDAO.findAllUserNotActive2(userSearchDto);
        return userDAO.usingHibernate(userSearchDto);
    }
}
