package byaj.repositories;

/**
 * Created by student on 6/20/17.
 */

import byaj.models.Accept;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface AcceptRepository extends CrudRepository<Accept, Integer> {
    public List<Accept> findAllByAcceptUser(int num);
    public Accept findByAcceptAuthorOrderByAcceptDateDesc(String author);
}