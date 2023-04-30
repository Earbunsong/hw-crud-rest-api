package com.example.mbanking.api.user;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class User {
    private Integer id;
    private String  name;
    private String gender;
    private String oneSignalId;
    private String studentCardId;
    private boolean isStudent;
    private boolean isDeleted;
}
