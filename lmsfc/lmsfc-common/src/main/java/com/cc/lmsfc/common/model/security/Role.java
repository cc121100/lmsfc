package com.cc.lmsfc.common.model.security;

import com.cc.lmsfc.common.model.BaseModel;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.util.List;

/**
 * Created by tomchen on 15-3-24.
 */
@Entity
@Table(name = "tbl_sys_role")
public class Role extends BaseModel{

    @Id
    @GeneratedValue(generator="system-uuid")
    @GenericGenerator(name="system-uuid",strategy="uuid")
    private String id;

    @Column(name="role_name" ,nullable=false, unique = true)
    @NotNull
    @Size(max = 50)
    private String roleName;

    @Column(name="role_description" ,nullable=false, unique = true)
    @NotNull
    @Size(max = 50)
    private String roleDescription;

    @Column(name="state" ,nullable=false)
    @NotNull
    private Integer state;

    @ManyToMany(mappedBy = "roles")
    private List<User> users;

    @ManyToMany(cascade = CascadeType.PERSIST,fetch = FetchType.LAZY)
    @JoinTable(
            name="tbl_sys_role_res",
            joinColumns = @JoinColumn(name="role_id",referencedColumnName="id"),
            inverseJoinColumns = @JoinColumn(name="resource_id",referencedColumnName="id")
    )
    private List<Resource> resources;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getRoleName() {
        return roleName;
    }

    public void setRoleName(String roleName) {
        this.roleName = roleName;
    }

    public String getRoleDescription() {
        return roleDescription;
    }

    public void setRoleDescription(String roleDescription) {
        this.roleDescription = roleDescription;
    }

    public Integer getState() {
        return state;
    }

    public void setState(Integer state) {
        this.state = state;
    }

    public List<User> getUsers() {
        return users;
    }

    public void setUsers(List<User> users) {
        this.users = users;
    }

    public List<Resource> getResources() {
        return resources;
    }

    public void setResources(List<Resource> resources) {
        this.resources = resources;
    }

    @Override
    public String[] getProperties() {
        return new String[0];
    }
}
