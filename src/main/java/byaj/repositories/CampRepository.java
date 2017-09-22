package byaj.repositories;

import byaj.models.Camp;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

/**
 * Created by student on 7/17/17.
 */
public interface CampRepository extends CrudRepository<Camp, Integer> {
    public Camp findByUsername(String username);
    public List<Camp> findAllByOrderByStartDateAsc();
    public List<Camp> findAllByOrderByDirectorIdDesc();
    public Camp findByAddressId(int addressId);
    public List<Camp> findAllByDescriptionContainingOrderByDirectorIdDesc(String search);
    public Camp findById(int id);
    public List<Camp> findAllByOrderByCampDateDesc();
    public Camp findByDirectorId(int id);
}
