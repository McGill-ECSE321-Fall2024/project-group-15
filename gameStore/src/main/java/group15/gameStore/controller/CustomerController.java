package group15.gameStore.controller;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import group15.gameStore.dto.CustomerDto;
import group15.gameStore.model.Customer;
import group15.gameStore.service.CustomerService;

@CrossOrigin(origins = "http://localhost:5173") // Frontend's base URL
@RestController
public class CustomerController {

    @Autowired
    private CustomerService customerService;
    
    //getCustomerByID
    @GetMapping("/customer/id/{cid}")
    public ResponseEntity<CustomerDto> getCustomerByID(@PathVariable("cid") int cid) {
        Customer customer = customerService.getCustomerByID(cid);
        if (customer == null) return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        return new ResponseEntity<>(new CustomerDto(customer), HttpStatus.OK);
    }

    //getCustomerByEmail
    @GetMapping("/customer/email/{email}")
    public ResponseEntity<CustomerDto> getCustomerByEmail(@PathVariable("email") String email) {
        Customer customer = customerService.getCustomerByEmail(email);
        if (customer == null) return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        return new ResponseEntity<>(new CustomerDto(customer), HttpStatus.OK);
    }

    //findAllCustomers
    @GetMapping("/customer")
    public ResponseEntity<List<CustomerDto>> findAllCustomers() {
        List<Customer> customers = customerService.findAllCustomers();
        List<CustomerDto> customerDtos = customers.stream().map(CustomerDto::new).collect(Collectors.toList());
        return new ResponseEntity<>(customerDtos, HttpStatus.OK);
    }

    //createCustomer
    @PostMapping("/customer/create")
    public ResponseEntity<CustomerDto> createCustomer(@RequestBody CustomerDto customerDto) {
        Customer customer = customerService.createCustomer(customerDto.getUsername(), customerDto.getPassword(), customerDto.getEmail(), customerDto.getAddress(), customerDto.getPhoneNumber());
        CustomerDto responseDto = new CustomerDto(customer);
        return new ResponseEntity<>(responseDto, HttpStatus.CREATED);
    }

    //deleteCustomer
    @DeleteMapping("/customer/delete/{cid}")
    public ResponseEntity<Void> deleteCustomer(@PathVariable("cid") int cid) {
        Customer customer = customerService.getCustomerByID(cid);
        customerService.deleteCustomer(customer);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    //updateCustomerUsername
    @PostMapping("/customer/update/username/{userId}")
    public ResponseEntity<CustomerDto> updateCustomerUsername(@PathVariable("userId") int userId, @RequestBody CustomerDto customerDto) {
        try {
            Customer updatedCustomer = customerService.updateCustomerUsername(customerService.getCustomerByID(userId), customerDto.getUsername());
            CustomerDto responseDto = new CustomerDto(updatedCustomer);
            return new ResponseEntity<>(responseDto, HttpStatus.OK);
        } catch (IllegalArgumentException e) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }

    //updateCustomerPassword
    @PostMapping("/customer/update/password/{userId}")
    public ResponseEntity<CustomerDto> updateCustomerPassword(@PathVariable("userId") int userId, @RequestBody CustomerDto customerDto) {
        try {
            Customer updatedCustomer = customerService.updateCustomerPassword(customerService.getCustomerByID(userId), customerDto.getPassword());
            CustomerDto responseDto = new CustomerDto(updatedCustomer);
            return new ResponseEntity<>(responseDto, HttpStatus.OK);
        } catch (IllegalArgumentException e) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }

    //updateCustomerEmail
    @PostMapping("/customer/update/email/{userId}")
    public ResponseEntity<CustomerDto> updateCustomerEmail(@PathVariable("userId") int userId, @RequestBody CustomerDto customerDto) {
        try {
            Customer updatedCustomer = customerService.updateCustomerEmail(customerService.getCustomerByID(userId), customerDto.getEmail());
            CustomerDto responseDto = new CustomerDto(updatedCustomer);
            return new ResponseEntity<>(responseDto, HttpStatus.OK);
        } catch (IllegalArgumentException e) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }

    //updateCustomerAddress
    @PostMapping("/customer/update/address/{userId}")
    public ResponseEntity<CustomerDto> updateCustomerAddress(@PathVariable("userId") int userId, @RequestBody CustomerDto customerDto) {
        try {
            Customer updatedCustomer = customerService.updateCustomerAddress(customerService.getCustomerByID(userId), customerDto.getAddress());
            CustomerDto responseDto = new CustomerDto(updatedCustomer);
            return new ResponseEntity<>(responseDto, HttpStatus.OK);
        } catch (IllegalArgumentException e) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }

    //updateCustomerPhoneNumber
    @PostMapping("/customer/update/phoneNumber/{userId}")
    public ResponseEntity<CustomerDto> updateCustomerPhoneNumber(@PathVariable("userId") int userId, @RequestBody CustomerDto customerDto) {
        try {
            Customer updatedCustomer = customerService.updateCustomerPhoneNumber(customerService.getCustomerByID(userId), customerDto.getPhoneNumber());
            CustomerDto responseDto = new CustomerDto(updatedCustomer);
            return new ResponseEntity<>(responseDto, HttpStatus.OK);
        } catch (IllegalArgumentException e) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }

}