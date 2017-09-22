package byaj.repositories;

/**
 * Created by student on 6/20/17.
 */

import byaj.models.Enable;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface EnableRepository extends CrudRepository<Enable, Integer> {
    public List<Enable> findAllByEnableUser(int num);
    public Enable findByEnableAuthorOrderByEnableDateDesc(String author);
}