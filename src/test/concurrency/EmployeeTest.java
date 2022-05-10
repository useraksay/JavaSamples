package test.concurrency;

import java.util.HashSet;
import java.util.Scanner;

class InvalidInputException extends Exception {
        InvalidInputException(){
            super("Exception Occurred");
        }
}

class Employee {

    private int id = 0;
    private String name = null;
    private boolean male = true;

    Employee(int id, String name,  boolean male) {
        super();
        this.id = id;
        this.name = name;
        this.male = male;
    }

    @Override
    public String toString() {
        return "Employee [id=" + id + ", name=" + name + ",  male=" + male + "]";
    }

    @Override
    public int hashCode() {
        // Complete all relevent code
        return this.id;
    }

    @Override
    public boolean equals(Object o){
        if(o == null) return false;
        if(this == o) return true;

        Employee e = (Employee) o;
        if(this.id == ((Employee) o).id) return true;

        return false;
    }


}

public class EmployeeTest {

    /*
     * Validate the input and create Employee object
     * If any of the given input is not valid throw InvalidInputException();
     */
    public Employee createEmployee(String record) {

        //Complete the code
        String[] emp = record.split("\\,");
        Employee employee = new Employee(Integer.parseInt(emp[0]), emp[1], Boolean.parseBoolean(emp[2]));

        return employee;

    }

    public static void main(String[] str) {

        //Input is expected as comma separated values for Employee object like id(int), name (String),isMale(boolean)"
        // 1,John,true
        Scanner scan = new Scanner(System.in);
        HashSet<Employee> employeeSet = new HashSet<>();

        /*Process each line and create Employee object using the "createEmployee" method add in employeeSet*/
        while (scan.hasNextLine()) {
            try {
                //Complete the code
                String record = scan.nextLine();
                System.out.println("record " + record);
                EmployeeTest t = new EmployeeTest();
                Employee e = t.createEmployee(record);
                employeeSet.add(e);
            }catch(Exception e) {
                System.out.println(e.getClass().getName());
                System.exit(0);
            }

        }

        //Don't delete or modify this print statement
         System.out.println("Employees info : "+ employeeSet);

    }

}