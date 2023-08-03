package ci.nkagou.parcauto.entities;

import lombok.*;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "App_User", //
        uniqueConstraints = { //
                @UniqueConstraint(name = "APP_USER_UK", columnNames = "User_Name") })
/*@Data*/
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter

public class AppUser {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "User_Id", nullable = false)
    private Long userId;

    @Column(name = "User_Name", length = 36, nullable = false)
    private String userName;

    @Column(name = "Encryted_Password", length = 128, nullable = false)
    private String encrytedPassword;

    @Column(name = "Enabled", length = 1, nullable = false)
    private boolean enabled;

    @ManyToOne
    @JoinColumn(name = "idEmploye")
    private Employe employe;

    @OneToMany(mappedBy = "appUser", fetch = FetchType.EAGER)
    private List<UserRole> userRoles;





   /* @JoinTable(
            name = "user_role",
            joinColumns = @JoinColumn(
                    name = "user_id", referencedColumnName = "user_id"),
            inverseJoinColumns = @JoinColumn(
                    name = "role_id", referencedColumnName = "role_id"))

    @ManyToMany*/
   @ManyToMany(cascade = {
           CascadeType.PERSIST,
           CascadeType.MERGE
   }, fetch = FetchType.EAGER)
   @JoinTable(
           name = "user_role",
           joinColumns = @JoinColumn(name = "user_id"),
           inverseJoinColumns = @JoinColumn(name = "role_id")
   )
    private List<AppRole> roles = new ArrayList<>();

    @Transient
    private String mesroles;

    public AppUser(String userName, String encrytedPassword, boolean enabled) {
        this.userName = userName;
        this.encrytedPassword = encrytedPassword;
        this.enabled = enabled;
    }

    public AppUser(Long userId, String userName, String encrytedPassword, boolean enabled, Employe employe) {
        this.userId = userId;
        this.userName = userName;
        this.encrytedPassword = encrytedPassword;
        this.enabled = enabled;
        this.employe = employe;
    }

    public AppUser(String userName, String encrytedPassword, boolean enabled, Employe employe) {
        this.userName = userName;
        this.encrytedPassword = encrytedPassword;
        this.enabled = enabled;
        this.employe = employe;
    }



}