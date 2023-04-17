package pet;

import lombok.*;

import java.sql.Timestamp;
import java.util.Date;

@Getter
@Setter
@EqualsAndHashCode
@ToString
@AllArgsConstructor
@NoArgsConstructor
public class Pet {
    private Integer id;
    private String petName;
    private Date birthDate;
    private Double weight;
    private Integer petTypeId;
    private Integer ownerId;

    private Timestamp createdAt;
    private Timestamp updatedAt;

}
