package DependencyInjectionExample;

public class CustomerRepositoryImpl implements CustomerRepository {
    @Override
    public String findCustomerById(String id) {

        if ("C101".equals(id)) {
            return "John Doe";
        }
        return "Customer Not Found";
    }
}