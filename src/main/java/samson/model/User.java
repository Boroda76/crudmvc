package samson.model;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import javax.persistence.*;
import java.util.Collection;
import java.util.HashSet;
import java.util.Set;


@Entity
@Table(name = "users")
public class User implements UserDetails {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(unique = true, nullable = false)
    private String login;
    @Column(unique = true, nullable = false)
    private String email;
    @Column
    private String password;
    @Column
    private Boolean sex; //false for female/true for male
    @Column
    private Integer age;
    @Column
    private Double weight;
    @Column
    private Double height;

    @ManyToMany(cascade = CascadeType.REFRESH, fetch = FetchType.EAGER)
    private Set<Role> authorities;

    //For hibernate with love!
    public User() {
    }

    public void setAuthorities(Role role) {
        Set<Role> authorities=new HashSet<>();
        authorities.add(role);
        this.authorities=authorities;
    }

    public User(String login, String email, String password, Boolean sex, Integer age, Double weight, Double height, Set<Role> authorities) {
//        this.id = id;
        this.login = login;
        this.email = email;
        this.password = password;
        this.sex = sex;
        this.age = age;
        this.weight = weight;
        this.height = height;
        this.authorities = authorities;
    }

    public String getRolesString(){
        StringBuilder result=new StringBuilder();
        for(Role r:authorities){
            result.append(r.getAuthority()+", ");
        }
        result.delete(result.length()-2, result.length()-1);
        return result.toString();
    }

    @Override
    public String getUsername() {
        return login;
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return authorities;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getLogin() {
        return login;
    }

    public void setLogin(String login) {
        this.login = login;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    @Override
    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public Boolean getSex() {
        return sex;
    }

    public void setSex(Boolean sex) {
        this.sex = sex;
    }

    public Integer getAge() {
        return age;
    }

    public void setAge(Integer age) {
        this.age = age;
    }

    public Double getWeight() {
        return weight;
    }

    public void setWeight(Double weight) {
        this.weight = weight;
    }

    public Double getHeight() {
        return height;
    }

    public void setHeight(Double height) {
        this.height = height;
    }
}
