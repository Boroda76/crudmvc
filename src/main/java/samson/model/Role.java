package samson.model;


import org.springframework.security.core.GrantedAuthority;

import javax.persistence.*;


@Entity
@Table(name = "roles")
public class Role implements GrantedAuthority {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "authority", unique = true)
    private String authority;

    public Role(String authority) {
        this.authority = authority;
    }

    public Role() {

    }
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    @Override
    public String getAuthority() {
        return authority;
    }

    public void setAuthority(String authority) {
        this.authority = authority;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Role userRole = (Role) o;

        if (!id.equals(userRole.id)) return false;
        return authority.equals(userRole.authority);
    }

    @Override
    public int hashCode() {
        int result = id.hashCode();
        result = 31 * result + authority.hashCode();
        return result;
    }
}
