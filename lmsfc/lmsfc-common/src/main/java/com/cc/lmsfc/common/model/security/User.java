package com.cc.lmsfc.common.model.security;

import com.cc.lmsfc.common.model.BaseModel;
import org.hibernate.annotations.GenericGenerator;
import org.hibernate.validator.constraints.NotEmpty;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.util.List;

/**
 * Created by tomchen on 15-3-24.
 */
@Entity
@Table(name = "tbl_sys_user")
public class User extends BaseModel{

    @Id
    @GeneratedValue(generator="system-uuid")
    @GenericGenerator(name="system-uuid",strategy="uuid")
    private String id;

    @Column(name="user_name" ,nullable=false, unique = true)
    @NotNull
    @Size(max = 20,min = 5)
    private String userName;

    @Column(name="password" ,nullable=false)
    @NotNull
    private String password;

    @Column(name="salt" ,nullable=false)
    @NotNull
    private String salt;

    @Column(name="state" ,nullable=false)
    @NotNull
    private Integer state;

    @ManyToMany(cascade = CascadeType.PERSIST,fetch = FetchType.LAZY)
    @JoinTable(
            name="tbl_sys_user_role",
            joinColumns = @JoinColumn(name="user_id",referencedColumnName="id"),
            inverseJoinColumns = @JoinColumn(name="role_id",referencedColumnName="id")
    )
    private List<Role> roles;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getSalt() {
        return salt;
    }

    public void setSalt(String salt) {
        this.salt = salt;
    }

    public Integer getState() {
        return state;
    }

    public void setState(Integer state) {
        this.state = state;
    }

    public List<Role> getRoles() {
        return roles;
    }

    public void setRoles(List<Role> roles) {
        this.roles = roles;
    }

    @Override
    public String[] getProperties() {
        return new String[0];
    }
}
