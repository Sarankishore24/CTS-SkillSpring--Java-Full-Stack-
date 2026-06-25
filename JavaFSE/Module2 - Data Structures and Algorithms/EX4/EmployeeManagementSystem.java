class Employee {
    private int employeeId;
    private String name;
    private String position;
    private double salary;

    public Employee(int employeeId, String name, String position, double salary) {
        this.employeeId = employeeId;
        this.name = name;
        this.position = position;
        this.salary = salary;
    }

    public int getEmployeeId() {
        return employeeId;
    }

    public String getName() {
        return name;
    }

    public String getPosition() {
        return position;
    }

    public double getSalary() {
        return salary;
    }

    @Override
    public String toString() {
        return "Employee{" +
                "id=" + employeeId +
                ", name='" + name + '\'' +
                ", position='" + position + '\'' +
                ", salary=$" + salary +
                '}';
    }
}

public class EmployeeManagement {
    private Employee[] employees;
    private int count;

    public EmployeeManagement(int capacity) {
        employees = new Employee[capacity];
        count = 0;
    }

    public boolean add(Employee employee) {
        if (count >= employees.length) {
            return false;
        }
        employees[count] = employee;
        count++;
        return true;
    }

    public Employee search(int employeeId) {
        for (int i = 0; i < count; i++) {
            if (employees[i].getEmployeeId() == employeeId) {
                return employees[i];
            }
        }
        return null;
    }

    public void traverse() {
        for (int i = 0; i < count; i++) {
            System.out.println(employees[i]);
        }
    }

    public boolean delete(int employeeId) {
        int indexToDelete = -1;
        for (int i = 0; i < count; i++) {
            if (employees[i].getEmployeeId() == employeeId) {
                indexToDelete = i;
                break;
            }
        }

        if (indexToDelete == -1) {
            return false;
        }

        for (int i = indexToDelete; i < count - 1; i++) {
            employees[i] = employees[i + 1];
        }
        
        employees[count - 1] = null;
        count--;
        return true;
    }

    public static void main(String[] args) {
        EmployeeManagement ems = new EmployeeManagement(5);

        ems.add(new Employee(101, "Alice", "Developer", 85000));
        ems.add(new Employee(102, "Bob", "Designer", 75000));
        ems.add(new Employee(103, "Charlie", "Manager", 95000));

        ems.traverse();

        Employee found = ems.search(102);

        ems.delete(102);
    }
}