package azure.cosmosdb.mongodb.spring.customer;

import io.reactivex.Flowable;
import io.reactivex.Observable;
import io.reactivex.Single;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.mongodb.core.CollectionOptions;
import org.springframework.data.mongodb.core.ReactiveMongoOperations;
import org.springframework.test.context.junit4.SpringRunner;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import static org.assertj.core.api.Assertions.assertThat;

@RunWith(SpringRunner.class)
@SpringBootTest
public class CustomerRepositoryTest {

    //Example integration test with MockDB

    @Autowired
    CustomerRepository customerRepository;

    @Autowired
    ReactiveMongoOperations operations;

    @Before
    public void setUp() {
        customerRepository.deleteAll().blockingAwait();

        Customer customer = customerRepository
                .saveAll(Flowable.just(new Customer("Chong", "Zhou"),
                        new Customer("Xiao", "Ma")))
                .blockingLast();
    }

    @Test
    public void findByFirstName() {
        Iterable<Customer> customers = customerRepository.findByFirstName(Single.just("Chong"))
                                           .blockingIterable();
        assertThat(customers).containsExactly(new Customer("Chong", "Zhou"));

    }

    @Test
    public void findAll() {
        Iterable<Customer> customers = customerRepository.findAll().blockingIterable();
        assertThat(customers).containsExactly(new Customer("Chong", "Zhou"), new Customer("Xiao", "Ma"));
    }

}