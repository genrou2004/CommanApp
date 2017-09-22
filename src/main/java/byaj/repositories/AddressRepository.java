package byaj.repositories;

import byaj.models.Address;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

/**
 * Created by student on 7/17/17.
 */
public interface AddressRepository extends CrudRepository<Address, Integer> {
    public Address findByOwnerId(int num);
    public List<Address> findAllByOrderByAddressDateDesc();
    public List<Address> findAllByOrderByOwnerIdDesc();
    public Address findByOwnerIdAndOwnerType(int num, String ownertype);
}
