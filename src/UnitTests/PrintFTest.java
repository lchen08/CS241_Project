package UnitTests;

//import CityMapPackage.CityGraph;
//import CityUI.InteractiveCityPanel;
//
//import java.io.File;
//import java.io.FileNotFoundException;

/** Used to test the formatting of the printf lines. */
public class PrintFTest
{
    public static void main(String[] args)
    {
        double number = 1273000;
        System.out.printf("%2d  %-5s %-16s %10.0f %6.0f%n", 1, "AN", "ANAHEIM", number, 310.0);
        System.out.printf("%2d  %-5s %-16s %10.0f %6.0f%n",20, "WW", "WRIGHTWOOD", 9234.0, 7910.0);

        System.out.printf("%2d%4d%8.0f%n", 1, 19, 36.0);
        System.out.printf("%2d%4d%8.0f%n", 4, 3, 273.0);
        System.out.printf("%2d%4d%8.0f%n", 17, 1, 77.0);


//        InteractiveCityPanel testPanel = new InteractiveCityPanel(null, new File("city.dat"),
//                new File("road.dat"));
//        testPanel.saveGraphInfo();
    }
}
