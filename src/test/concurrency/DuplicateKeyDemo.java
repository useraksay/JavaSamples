package test.concurrency;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class DuplicateKeyDemo {

    public static void main(String[] args) {
        List<Person> personList = new ArrayList<>();
        personList.add(new Person("Micheal", "Goa"));
        personList.add(new Person("Manohar", "Chennai"));
        personList.add(new Person("Manohar", "Chennai"));
        personList.add(new Person("Ravi", "Delhi"));
        personList.add(new Person(null, "Delhi"));
        //personList.add(new Person(null, "Kolkata"));

        Map<String, String> personMap = personList.stream().collect(Collectors.toMap(Person::getName, Person::getAddress));
//        Map<String, String> personMap = personList.stream().collect(Collectors.toMap(Person::getName, Person::getAddress, (address1, address2) -> address2));
        System.out.println(personMap);
    }

    private static class Person{
        private String name;
        private String address;

        private Person(){}

        private Person(String name, String address){
            this.name = name;
            this.address = address;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public String getAddress() {
            return address;
        }

        public void setAddress(String address) {
            this.address = address;
        }
    }

}
