package byaj.repositories;

import byaj.models.UserApplication;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

/**
 * Created by student on 7/17/17.
 */
public interface UserApplicationRepository extends CrudRepository<UserApplication, Integer> {
    public UserApplication findByUsername(String username);
    public List <UserApplication> findAllByOrderByUserAppDateDesc();
    public UserApplication findByAddressId(int addressId);
    public List <UserApplication> findAllByOrderByUserIdDesc();
    public List <UserApplication> findAllByLevelOfEduContainingOrderByUserIdDesc(String levelOfEdu);
    public UserApplication findById(int id);
}