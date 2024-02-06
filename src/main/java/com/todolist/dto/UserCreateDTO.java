package com.todolist.dto;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.util.Objects;

@Getter
@Setter
@ToString
@EqualsAndHashCode
public class UserCreateDTO {

    private String userName;

    private String password;

    private String email;

}
