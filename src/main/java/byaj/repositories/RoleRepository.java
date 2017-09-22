package byaj.repositories;

/**
 * Created by student on 7/17/17.
 */


        import byaj.models.Role;
        import org.springframework.data.repository.CrudRepository;


public interface RoleRepository extends CrudRepository<Role, Integer> {
    Role findByRoleName(String role);
}