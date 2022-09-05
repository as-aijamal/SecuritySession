package peaksoft.model;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;

@Entity
@Table(name = "roles")
@Getter
@Setter
@NoArgsConstructor
public class Role {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE,
            generator = "role_sequence")
    @SequenceGenerator(name = "role_sequence",
            sequenceName = "role_sequence",
            allocationSize = 1)
    private Long id;

    private String name;

    public Role(String name){
        this.name = name;
    }










}
