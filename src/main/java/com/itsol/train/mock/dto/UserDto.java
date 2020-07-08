package com.itsol.train.mock.dto;

import com.itsol.train.mock.domain.Authority;
import com.itsol.train.mock.domain.User;
import lombok.*;

import javax.validation.constraints.NotNull;
import java.util.Date;
import java.util.Set;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
@EqualsAndHashCode
public class UserDto {
    private Long id;

    @NotNull
    private String login;

    private String username;

    private String email;

    @NotNull
    private String password;

    private Boolean activated;

    private Boolean rememberMe;

    private Set<Authority> authorities;

    private String langKey;
    private String imageUrl;
    private Date createDate;
    private Date updateDate;

    public UserDto(User user){
        this.id = user.getId();
        this.username = user.getUsername();
        this.email = user.getEmail();
        this.authorities = user.getAuthorities();
    }
}
