package azure.cosmosdb.mongodb.spring.customer;

import azure.cosmosdb.mongodb.spring.exception.IrisIllegalArgument;
import io.reactivex.Flowable;
import io.reactivex.Single;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class CustomerService {

    @Autowired
    CustomerRepository customerRepository;

    //TODO authz
    Flowable<Customer> findByFirstName(String firstName) {

        return Single.just(firstName)
                     .flatMapPublisher(name -> {
                         if (name.equalsIgnoreCase("Xiao")) {
                             throw new IrisIllegalArgument("Found Xiao");
                         } else {
                             return customerRepository.findByFirstName(Single.just(name));
                         }
                     });
    }
}
