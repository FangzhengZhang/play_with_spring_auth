package cat.frank.playWithAuthJWT.model;

import jakarta.persistence.*;
import lombok.Setter;

import java.util.*;

@Entity
@Table(name = "user_table")
@Setter
public class UserEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String username;
    private String password;


    //This will create a many-to-many relationship between user and role
    @ManyToMany(fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    //This is the join table for the user and role
    @JoinTable(name="user_roles", joinColumns = @JoinColumn(name="user_id", referencedColumnName = "id"),
        inverseJoinColumns = @JoinColumn(name = "role_id", referencedColumnName = "id"))
    private List<Role> roles = new ArrayList<>();

    public Long getId() {
        return id;
    }

    public String getUsername() {
        return username;
    }

    public String getPassword() {
        return password;
    }

    public List<Role> getRoles() {
        return roles;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public void setRoles(List<Role> roles) {
        this.roles = roles;
    }
}
