/*
 * Copyright 2012-2018 the original author or authors.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package azure.cosmosdb.mongodb.spring;

import azure.cosmosdb.mongodb.spring.customer.Customer;
import azure.cosmosdb.mongodb.spring.customer.CustomerRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.mongodb.repository.config.EnableReactiveMongoRepositories;

@SpringBootApplication
@EnableReactiveMongoRepositories
public class SampleMongoApplication implements CommandLineRunner {

	public static void main(String[] args) {
		SpringApplication.run(SampleMongoApplication.class, args);
	}

	@Autowired
	private CustomerRepository customerRepository;

	public void run(String... args) throws Exception {

		// Use WriteRepository to do some write operations
		this.customerRepository.deleteAll().blockingAwait();

		// save a couple of customers
		this.customerRepository.save(new Customer("Alice", "Smith"));
		this.customerRepository.save(new Customer("Bob", "Smith"));

		// Use ReadRepository to do some read operations
		// fetch all customers
		System.out.println("Customers found with findAll():");
		System.out.println("-------------------------------");

		customerRepository.findAll()
						  .blockingForEach(customer-> System.out.println(customer));

		System.out.println();

		// fetch an individual customer
		System.out.println("Customer found with findByFirstName('Alice'):");
		System.out.println("--------------------------------");
		this.customerRepository.findByFirstName("Alice")
							   .subscribe(customer -> System.out.println(customer));

		System.out.println("Customers found with findByLastName('Smith'):");
		System.out.println("--------------------------------");

		customerRepository.findByLastName("Smith")
						  .blockingForEach(customer -> System.out.println(customer));
	}



}
