package com.example.jpa.resource;

import static org.springframework.web.bind.annotation.RequestMethod.DELETE;
import static org.springframework.web.bind.annotation.RequestMethod.GET;
import static org.springframework.web.bind.annotation.RequestMethod.POST;
import static org.springframework.web.bind.annotation.RequestMethod.PUT;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.example.jpa.repository.Customer;
import com.example.jpa.repository.CustomerRepository;

@Controller
@RequestMapping("/customer")
public class CustomerController {

	@Autowired
	private CustomerRepository repository;

	@RequestMapping(method = POST)
	public @ResponseBody Customer create(@RequestBody Customer customer) {
		customer = repository.save(customer);
		return customer;
	}


	@RequestMapping(value = "/{id}", method = PUT)
	public @ResponseBody boolean update(@PathVariable long id, @RequestBody Customer customer) {
	
		if(id != customer.getId()){
			return false;
		}
		
		if (repository.exists(customer.getId())) {
			customer = repository.save(customer);
			return true;
		}
		return false;
	}

	/**
	 * @return all the Customers
	 */
	@RequestMapping(method = GET)
	public @ResponseBody Iterable<Customer> get() {
		return repository.findAll();
	}

	/**
	 * @param id
	 *            the identifier of the charge point
	 * @return charge point
	 */
	@RequestMapping(value = "/{id}", method = GET)
	public @ResponseBody Customer get(@PathVariable long id) {
		return repository.findOne(id);
	}
	
 

	@RequestMapping(value = "/{id}", method = DELETE)
	public @ResponseBody String delete(@PathVariable long id) {
		repository.delete(id);
		return null;
	}

    @RequestMapping(method = DELETE)
    public @ResponseBody int delete(@RequestBody List<Customer> customers) {

        if (customers != null) {
            customers.forEach(c -> repository.delete(c.getId()));
            return customers.size();
        } else {
            return 0;
        }

    }
}
