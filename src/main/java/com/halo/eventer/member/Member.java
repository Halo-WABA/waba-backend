package com.halo.eventer.member;


import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.List;

@Entity
@NoArgsConstructor
@Getter
public class Member {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String loginId;
    private String password;

    @OneToMany(mappedBy = "member",fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    private List<Authority> authorities;

    public void setMember(List<Authority> roles) {
        this.authorities = roles;
        roles.forEach(o -> o.setMember(this));
    }


}
