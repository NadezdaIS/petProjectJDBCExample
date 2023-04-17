package owner;

import lombok.*;

import java.sql.Timestamp;

@Getter
@Setter
@EqualsAndHashCode
@ToString
@AllArgsConstructor
@NoArgsConstructor
public class Owner {
    private Integer id;
    private String ownerName;
    private Integer age;
    private String email;

    private Timestamp createdAt;
    private Timestamp updatedAt;

}
