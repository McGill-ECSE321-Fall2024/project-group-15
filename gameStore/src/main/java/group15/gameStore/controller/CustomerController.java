package group15.gameStore.controller;

import java.util.ArrayList;
import java.util.List;

import org.apache.catalina.connector.Response;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import group15.gameStore.dto.CategoryDto;
import group15.gameStore.dto.CustomerDto;
import group15.gameStore.model.Customer;
import group15.gameStore.service.CustomerService;

@RestController
public class CustomerController {

    @Autowired
    private CustomerService customerService;
    
    //getCustomerByID
    @GetMapping("/customer/{cid}")
    public ResponseEntity<CustomerDto> getCustomerByID(@PathVariable int cid) {
        Customer customer = customerService.getCustomerByID(cid);
        return new ResponseEntity<>(new CustomerDto(customer), HttpStatus.OK);
        //CustomerDto customerDto = new CustomerDto(customerService.getCustomerByID(cid));
        //return customerDto;
    }

    //getCustomerByEmail
    @GetMapping("/customer/{email}")
    public ResponseEntity<CustomerDto> getCustomerByEmail(@PathVariable String email) {
        Customer customer = customerService.getCustomerByEmail(email);
        //CustomerDto customerDto = new CustomerDto(customerService.getCustomerByEmail(email));
        return new ResponseEntity<>(new CustomerDto(customer), HttpStatus.OK);
        // return customerDto;
    }

    //findAllCustomers
    @GetMapping("/customer")
    public List<CustomerDto> findAllCustomers() {
        List<CustomerDto> customerDtos = new ArrayList<>();
        for (Customer customer : customerService.findAllCustomers()) {
            customerDtos.add(new CustomerDto(customer));
        }
        return customerDtos;
    }

    //createCustomer
    @PostMapping("/customer/create")
    public ResponseEntity<CustomerDto> createCustomer(@RequestBody CustomerDto customerDto) {
        Customer customer = customerService.createCustomer(customerDto.getUsername(), customerDto.getPassword(), customerDto.getEmail(), customerDto.getAddress(), customerDto.getPhoneNumber());
        CustomerDto responseDto = new CustomerDto(customer);
        return new ResponseEntity<>(responseDto, HttpStatus.CREATED);
    }

    //deleteCustomer
    @PostMapping("/customer/delete")
    public ResponseEntity<Response> deleteCustomer(@RequestBody CustomerDto customerDto) {
        Customer customer = customerService.getCustomerByID(customerDto.getUserId());
        customerService.deleteCustomer(customer);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    //updateCustomerUsername
    @PostMapping("/customer/update/username/{userId}")
    public ResponseEntity<CustomerDto> updateCustomerUsername(@PathVariable int userId, @RequestBody CustomerDto customerDto) {
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
    public ResponseEntity<CustomerDto> updateCustomerPassword(@PathVariable int userId, @RequestBody CustomerDto customerDto) {
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
    public ResponseEntity<CustomerDto> updateCustomerEmail(@PathVariable int userId, @RequestBody CustomerDto customerDto) {
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
    public ResponseEntity<CustomerDto> updateCustomerAddress(@PathVariable int userId, @RequestBody CustomerDto customerDto) {
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
    public ResponseEntity<CustomerDto> updateCustomerPhoneNumber(@PathVariable int userId, @RequestBody CustomerDto customerDto) {
        try {
            Customer updatedCustomer = customerService.updateCustomerPhoneNumber(customerService.getCustomerByID(userId), customerDto.getPhoneNumber());
            CustomerDto responseDto = new CustomerDto(updatedCustomer);
            return new ResponseEntity<>(responseDto, HttpStatus.OK);
        } catch (IllegalArgumentException e) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }

}