package DependencyInjectionExample;

public class CustomerService {
    private final CustomerRepository repository;

    // Step 5: Constructor Injection
    public CustomerService(CustomerRepository repository) {
        this.repository = repository;
    }

    public void getCustomerDetails(String id) {
        String customerName = repository.findCustomerById(id);
        System.out.println("Customer Profile Lookup [ID: " + id + "] -> Name: " + customerName);
    }
}