package kg.easyit.authenticationpractice.model.entity;

import lombok.*;
import lombok.experimental.FieldDefaults;
import org.springframework.data.jpa.domain.AbstractPersistable;

import javax.persistence.*;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Table(name = "tb_role")
@FieldDefaults(level = AccessLevel.PRIVATE)
public class Role extends AbstractPersistable<Long> {
    @Column(name = "role_name", nullable = false)
    String roleName;

    @ElementCollection
    @CollectionTable(name = "role_has_authorities", joinColumns = @JoinColumn(name = "role_id"))
    @Column(name = "authorities_id", nullable = false)
    List<Authority> authorities;

}
