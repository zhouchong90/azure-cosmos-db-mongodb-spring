package azure.cosmosdb.mongodb.spring.customer;

import azure.cosmosdb.mongodb.spring.views.Views;
import com.fasterxml.jackson.annotation.JsonView;
import io.reactivex.Flowable;
import io.reactivex.Single;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

@RestController("/customer")
public class CustomerController {

    @Autowired
    CustomerRepository customerRepository;

    @Autowired
    CustomerService customerService;

    @GetMapping("/all")
    Flowable<Customer> findAll(){
        return customerRepository.findAll();
    }

    @GetMapping("/firstName/{firstName}")
    Flowable<Customer> findByFirstName(@PathVariable("firstName") String firstName){
        return customerService.findByFirstName(firstName);
    }

    @JsonView(Views.Internal.class)
    @GetMapping("/firstName/{firstName}/limited")
    Flowable<Customer> findByFirstNameLimited(@PathVariable("firstName") String firstName){
        return customerService.findByFirstName(firstName);
    }

}
