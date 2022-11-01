package by.litvin.localsandbox.data;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import java.time.LocalDateTime;

@Getter
@Setter
@Entity
@NoArgsConstructor
public class SomeData {

    public SomeData(String text) {
        this.text = text;
    }

    @Id
    @GeneratedValue
    private Long id;
    private String text;
    private LocalDateTime creationDate = LocalDateTime.now();
}
