public class EnumerationTest {
    public static void main(String[] args) {
        SourceApp sourceApp = SourceApp.TPP;

        switch (sourceApp) {
            case TPP:
                System.out.println("TPP");
            case S2C:
                System.out.println("S2C");
            case SLC:
                System.out.println("SLC");
        }
    }


    private enum SourceApp {
        TPP("tpp"), S2C("s2c"), SLC("slc");

        public String name;

        SourceApp(String s) {
        }

        public String getName() {
            return this.name;
        }
    }


}
