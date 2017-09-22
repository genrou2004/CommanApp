package byaj.repositories;

/**
 * Created by student on 6/20/17.
 */

import byaj.models.EmailInfo;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface EmailInfoRepository extends CrudRepository<EmailInfo, Integer> {
    public List<EmailInfo> findAllByEmailUser(int num);
    public EmailInfo findByEmailAuthorOrderByEmailDateDesc(String author);
}