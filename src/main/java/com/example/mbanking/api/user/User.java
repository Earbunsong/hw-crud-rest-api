package com.example.mbanking.api.user;

import com.example.mbanking.api.user.web.Role;
import lombok.*;

import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@ToString
public class User {
    private Integer id;
    private String  name;
    private String gender;
    private String oneSignalId;
    private String studentCardId;
    private boolean isStudent;
    private boolean isDeleted;

    //Auth feature info
    private String email;
    private String password;
    private Boolean isVerified;
    private String verifiedCode;

    //User has roles
    private List<Role> roles;
}
