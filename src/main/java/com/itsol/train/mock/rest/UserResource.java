package com.itsol.train.mock.rest;


import com.itsol.train.mock.constants.AppConstants;
import com.itsol.train.mock.dto.ResponseDto;
import com.itsol.train.mock.dto.UserDto;
import com.itsol.train.mock.dto.UserSearchDto;
import com.itsol.train.mock.service.UserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/user")
public class UserResource {

    private Logger log = LoggerFactory.getLogger(UserResource.class);

    @Autowired
    private UserService userService;

    @PostMapping("/register")
    public ResponseEntity<ResponseDto> register(@RequestBody UserDto userDto){
        log.trace("REST request to register user website: {}", userDto);
        ResponseDto responseDto = new ResponseDto();
        try{
            userService.register(userDto);
            responseDto.setResponseCode(AppConstants.RESPONSE_OK);
        } catch (Exception exception){
            responseDto.setResponseCode(AppConstants.RESPONSE_ERRORS);
            responseDto.setMessage(exception.getMessage());
        }
        return new ResponseEntity<>(responseDto, HttpStatus.CREATED);
    }
    @GetMapping("/get-profile")
    public ResponseEntity<UserDto> getProfile(){
        UserDto userDto = userService.getUserWithAuthorities()
                .map(UserDto::new)
                .orElseThrow(() -> new AccountResourceException("User could not be found"));
        return new ResponseEntity<>(userDto, HttpStatus.OK);
    }

    @PostMapping("/get-user-not-active")
    public ResponseEntity<UserSearchDto> getUserNotActive(@RequestBody UserSearchDto userSearchDto){
        log.trace("REST to get all user not active");
        List<UserDto> lstUserNotActive = userService.getAllUserNotActive(userSearchDto);
        userSearchDto.setData(lstUserNotActive);
        return new ResponseEntity<>(userSearchDto, HttpStatus.OK);
    }
    private static class AccountResourceException extends RuntimeException {
        private AccountResourceException(String message) {
            super(message);
        }
    }

}
