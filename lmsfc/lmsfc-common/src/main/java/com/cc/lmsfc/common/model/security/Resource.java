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
@Table(name = "tbl_sys_resource")
public class Resource extends BaseModel{

    @Id
    @GeneratedValue(generator="system-uuid")
    @GenericGenerator(name="system-uuid",strategy="uuid")
    private String id;

    @Column(name="resource_name" ,nullable=false, unique = true)
    @NotNull
    @Size(max = 50)
    private String resourceName;

    @Column(name="type" ,nullable=false)
    @NotNull
    @Size(max = 50)
    private String type;

    @Column(name="url" ,nullable=false, unique = true)
    @Size(max = 50)
    private String url;

    @Column(name="permission" ,nullable=true)
    @Size(max = 50)
    private String permission;

    @Column(name = "state",nullable = false)
    private Integer state;

    @ManyToMany(mappedBy = "resources")
    private List<Role> roles;

    @OneToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "parent_resource_id")
    private Resource parentResource;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getResourceName() {
        return resourceName;
    }

    public void setResourceName(String resourceName) {
        this.resourceName = resourceName;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getPermission() {
        return permission;
    }

    public void setPermission(String permission) {
        this.permission = permission;
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

    public Resource getParentResource() {
        return parentResource;
    }

    public void setParentResource(Resource parentResource) {
        this.parentResource = parentResource;
    }

    @Override
    public String[] getProperties() {
        return new String[0];
    }
}
