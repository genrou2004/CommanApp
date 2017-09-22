package byaj.repositories;

/**
 * Created by student on 6/20/17.
 */

import byaj.models.CampBuilder;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface CampBuilderRepository extends CrudRepository<CampBuilder, Integer> {
   // public List<CampBuilder> findAllByCampBuilderUser(int num);
    //public CampBuilder findByCampBuilderAuthorOrderByCampBuilderDateDesc(String author);
}