package org.swordess.toy.javamisc.lombok;

import lombok.Builder;
import lombok.Getter;

@Builder
@Getter
public class UserView {

    private String username;
    private String password;
    private int age;

}
