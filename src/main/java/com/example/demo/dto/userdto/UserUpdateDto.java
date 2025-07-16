package com.example.demo.dto.userdto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UserUpdateDto {
    private String nickname;
    private String email;
    private String currentPassword;
    private String newPassword;
}
