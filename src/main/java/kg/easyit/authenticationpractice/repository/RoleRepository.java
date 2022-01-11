package kg.easyit.authenticationpractice.repository;

import kg.easyit.authenticationpractice.model.entity.Role;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;


@Repository
public interface RoleRepository extends JpaRepository<Role, Long> {

    boolean existsByRoleName(String roleName);

}
