package by.litvin.localsandbox.model;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * User post entity
 */
@Getter
@Setter
@NoArgsConstructor
@Entity
public class Post implements Serializable {

    public Post(String message, String mediaUrl, AppUser appUser) {
        this.message = message;
        this.mediaUrl = mediaUrl;
        this.appUser = appUser;
    }

    @Id
    @GeneratedValue
    private Long id;

    private LocalDateTime creationTime = LocalDateTime.now();

    private String message;

    private String mediaUrl;

    private boolean isEdited;

    @ManyToOne
    @JoinColumn(name = "app_user_id")
    private AppUser appUser;
}
