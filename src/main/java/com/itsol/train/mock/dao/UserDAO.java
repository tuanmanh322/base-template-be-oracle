package com.itsol.train.mock.dao;

import com.itsol.train.mock.dto.UserDto;
import com.itsol.train.mock.dto.UserSearchDto;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface UserDAO {
    List<UserDto> findAllUserNotActive1();

    List<UserDto> findAllUserNotActive2(UserSearchDto userSearchDto);

    List<UserDto> usingHibernate(UserSearchDto userSearchDto);

}
