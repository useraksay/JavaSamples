package test.concurrency;

import java.util.*;

public class ComparatorCheck {
    private static final List<String> reachableOrder = Arrays.asList("Y", "N");
    public static void main(String[] args){
        List<City> cities = new ArrayList<>();
      /*cities.add(new City("BGL", "555.1", "Y"));
        cities.add(new City("DEV", "555.2", "N"));
        cities.add(new City("NAG", "755.1", "N"));
        cities.add(new City("MUM", "655.1", "N"));
        cities.add(new City("NAS", "655.12", "N"));
        cities.add(new City("MYS", "755.1", "Y"));
        cities.add(new City("HBL", "255.1", "Y"));*/

        cities.add(new City("BGL", "555.1", "Z"));
        cities.add(new City("DEV", "555.2", "X"));
        cities.add(new City("NAG", "755.13", "X"));
        cities.add(new City("MUM", "655.1", "Z"));
        cities.add(new City("NAS", "655.12", "X"));
        cities.add(new City("MYS", "755.11", "Z"));
        cities.add(new City("HBL", "255.1", "X"));

//        printCities(cities);
        cities.sort(Comparator.comparing((City c) -> reachableOrder.indexOf(c.getStatus())).thenComparing(city -> Double.parseDouble(city.getDistance())));
        printCities(cities);
    }

    private static void printCities(List<City> cities) {
        Iterator<City> cityIterator = cities.iterator();
        while (cityIterator.hasNext()){
            City city = cityIterator.next();
            System.out.println(city);
        }
    }

    private static class City{
        private String name;
        private String distance;
        private String status;

        public City(String name, String distance, String status) {
            this.name = name;
            this.distance = distance;
            this.status = status;
        }

        public City(){}


        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public String getDistance() {
            return distance;
        }

        public void setDistance(String distance) {
            this.distance = distance;
        }

        public String getStatus() {
            return status;
        }

        public void setStatus(String status) {
            this.status = status;
        }


        @Override
        public String toString() {
            return "City{" +
                    "name='" + name + '\'' +
                    ", distance='" + distance + '\'' +
                    ", status='" + status + '\'' +
                    '}';
        }
    }
}
