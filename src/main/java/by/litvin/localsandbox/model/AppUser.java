package by.litvin.localsandbox.model;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import java.io.Serializable;

/**
 * Application user entity
 * Spring cache requited an entity to be {@link Serializable}
 */
@Entity
@Getter
@Setter
@NoArgsConstructor
public class AppUser implements Serializable {

    public AppUser(String firstName, String lastName, String email, String phone) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.email = email;
        this.phone = phone;
    }

    @Id
    @GeneratedValue
    private Long id;

    private String firstName;

    private String lastName;

    private String email;

    private String phone;

}
