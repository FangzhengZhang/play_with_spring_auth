package cat.frank.playWithAuthJWT.model;

import jakarta.persistence.*;


@Entity
@Table(name = "role_table")
public class Role {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String roleName;
}
