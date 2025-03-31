package bcu.cmp5332.bookingsystem.test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import bcu.cmp5332.bookingsystem.model.Customer;

public class CustomerTest {

    private Customer customer;

    @BeforeEach
    public void setUp() {
        customer = new Customer(1, "John Doe", "1234567890", "john@example.com", 1);
    }

    @Test
    public void testCustomerId() {
        assertEquals(1, customer.getId());
    }

    @Test
    public void testCustomerName() {
        assertEquals("John Doe", customer.getName());
    }

    @Test
    public void testCustomerPhone() {
        assertEquals("1234567890", customer.getPhone());
    }

    @Test
    public void testCustomerEmail() {
        assertEquals("john@example.com", customer.getEmail());
    }
}
