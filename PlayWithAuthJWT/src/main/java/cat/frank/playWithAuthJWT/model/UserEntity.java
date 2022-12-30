package cat.frank.playWithAuthJWT.model;

import jakarta.persistence.*;

import java.util.*;

@Entity
@Table(name = "user")
public class UserEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String username;
    private String password;


    //This will create a many-to-many relationship between user and role
    @ManyToMany(fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    //This is the join table for the user and role
    @JoinTable(name="user_rolls", joinColumns = @JoinColumn(name="user_id", referencedColumnName = "id"),
        inverseJoinColumns = @JoinColumn(name = "roll_id", referencedColumnName = "id"))
    private Set<Role> roles = new HashSet<>();
}
