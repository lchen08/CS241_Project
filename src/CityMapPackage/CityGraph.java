package CityMapPackage;

import GraphPackage.DirectedGraph;
import GraphPackage.VertexInterface;
import java.io.File;
import java.io.FileNotFoundException;
import java.util.Iterator;
import java.util.NoSuchElementException;
import java.util.Scanner;

/**
 * <b><u>CS 241 Homework 4 - City Graph</b></u>
 * <br>
 * This class creates a city graph with implementation to populate the information from city
 * and road .dat files. The city file will need to have, in each line: city number (integer),
 * city code (String), full city name (String), population (non-negative integer), and
 * elevation (double).
 * The road file will need to have, in each line: from-city (city number), to-city
 * (city number), and distance between them (double).
 *
 * @author Lisa Chen
 * @since May 28, 2018
 */
public class CityGraph extends DirectedGraph<Integer>
{
    /** Constructs an empty city graph. */
    public CityGraph() { super(); }

    /**
     * Constructs a city graph from city and road data files.
     * @param cityFile Data file with information about the cities, including city number,
     *                 city code, full city name, population, and elevation
     * @param roadFile Data file with information about the roads, including from-city
     *                 (city number), to-city (city number), and distance between them
     * @throws FileNotFoundException When any of the files given cannot be found
     */
    public CityGraph(File cityFile, File roadFile) throws FileNotFoundException
    {
        this();
        createCities(cityFile);
        createRoads(roadFile);
    }

    /**
     * Adds a city vertex to the graph.
     * @param cityNumber Number reference for the city
     * @param cityCode Two letter code for the city
     * @param fullCityName Full name of the city
     * @param population The city's population
     * @param elevation The city's elevation
     * @return True if the vertex was added successfully; false otherwise.
     */
    public boolean addVertex(int cityNumber, String cityCode, String fullCityName,
                             double population, double elevation)
    {
        CityVertex newCity = new CityVertex(cityNumber, cityCode, fullCityName, population,
                elevation);
        VertexInterface<Integer> addOutcome = vertices.put(cityNumber, newCity);
        return addOutcome == null;
    }

    /**
     * Adds an edge between two cities with an assigned distance.
     * @param begin Starting city for a directed road/edge
     * @param end Ending city for a directed road/edge
     * @param edgeWeight Assigned distance between the two cities
     * @return True if the edge was added successfully; false otherwise
     */
    public boolean addEdge(int begin, int end, double edgeWeight)
    {
        if (edgeWeight < 0)
            throw new IllegalArgumentException("Distance between two cities cannot be negative.");
        return super.addEdge(begin,end, edgeWeight);
    }

    /**
     * Creates the cities from a data file that are added to the graph.
     * @param cityData Data file with information about the cities, including city number,
     *                 city code, full city name, population, and elevation
     * @throws FileNotFoundException When the city file cannot be found
     */
    private void createCities(File cityData) throws FileNotFoundException
    {
        Scanner in = new Scanner(cityData);
        /*
        go through file and read line by line; each line is in this order in the city file:
        City Number, City Code, Full City Name, Population, and Elevation.
         */
        while (in.hasNext())
        {
            String line = in.nextLine();
            Scanner lineScanner = new Scanner(line);
            int cityNumber = lineScanner.nextInt();
            String cityCode = lineScanner.next();
            String fullCityName = lineScanner.next();
            double population = -1;

            //full city name can be multiple words - scan until words are done and set population
            while (population == -1)
            {
                String nextText = lineScanner.next();
                try
                {
                    population = Double.parseDouble(nextText);
                }
                catch(NumberFormatException e)
                {
                    fullCityName += " " + nextText;
                }
            }
            double elevation = lineScanner.nextDouble();
            addVertex(cityNumber, cityCode, fullCityName, population, elevation);
        }
        in.close();
    }

    /**
     * Creates the directed roads between the cities in the graph from a data file.
     * @param roadData Data file with information about the roads, including from-city (city
     *                 number), to-city (city number), and distance between them
     * @throws FileNotFoundException When the road file cannot be found
     */
    private void createRoads (File roadData) throws FileNotFoundException
    {
        Scanner in = new Scanner(roadData);
        /*
        go through file and read line by line; each line is in this order in the road file:
        From_City (city number), To_City (city number), and Distance
         */
        while (in.hasNext())
        {
            String line = in.nextLine();
            Scanner lineScanner = new Scanner(line);
            int fromCity = lineScanner.nextInt();
            int toCity = lineScanner.nextInt();
            double distance = lineScanner.nextDouble();
            addEdge(fromCity, toCity, distance);
        }
        in.close();
    }

    /**
     * Gets the city code of a city vertex in the city graph.
     * @param cityNumber The city reference number for an existing city vertex in the city graph
     * @return The city's reference code
     */
    public String getCityCode(int cityNumber)
    {
        CityVertex vertex = (CityVertex) vertices.get(cityNumber);
        return vertex.getCityCode();
    }

    /**
     * Gets the full city name of a city vertex in the city graph.
     * @param cityNumber The city reference number for an existing city vertex in the city graph
     * @return THe city's full name
     */
    public String getFullCityName(int cityNumber)
    {
        CityVertex vertex = (CityVertex) vertices.get(cityNumber);
        return vertex.getFullCityName();
    }

    /**
     * Gets the population of a city vertex in the city graph.
     * @param cityNumber The city reference number for an existing city vertex in the city graph
     * @return THe city's population
     */
    public double getPopulation(int cityNumber)
    {
        CityVertex vertex = (CityVertex) vertices.get(cityNumber);
        return vertex.getPopulation();
    }

    /**
     * Gets the elevation of a city vertex in the city graph.
     * @param cityNumber The city reference number for an existing city vertex in the city graph
     * @return THe city's elevation
     */
    public double getElevation(int cityNumber)
    {
        CityVertex vertex = (CityVertex) vertices.get(cityNumber);
        return vertex.getElevation();
    }

    /**
     * Sets the city code for this city vertex.
     * @param cityNumber The city reference number for an existing city vertex in the city graph
     * @param cityCode The new code for the city
     */
    public void setCityCode(int cityNumber, String cityCode)
    {
        CityVertex vertex = (CityVertex) vertices.get(cityNumber);
        vertex.setCityCode(cityCode);
    }

    /**
     * Sets the full name of this city vertex.
     * @param cityNumber The city reference number for an existing city vertex in the city graph
     * @param fullName The new full name for the city
     */
    public void setFullName(int cityNumber, String fullName)
    {
        CityVertex vertex = (CityVertex) vertices.get(cityNumber);
        vertex.setFullName(fullName);
    }
    /**
     * Sets the population of this city vertex.
     * @param cityNumber The city reference number for an existing city vertex in the city graph
     * @param population A new, non-negative, whole number for the city's population
     */
    public void setPopulation(int cityNumber, double population)
    {
        if (population < 0)
            throw new IndexOutOfBoundsException("Population input cannot be negative.");
        if (population % 1 != 0)
            throw new IndexOutOfBoundsException("Population cannot be a decimal.");
        CityVertex vertex = (CityVertex) vertices.get(cityNumber);
        vertex.setPopulation(population);
    }

    /**
     * Sets the elevation of this city vertex.
     * @param cityNumber The city reference number for an existing city vertex in the city graph
     * @param elevation The new elevation for the city vertex
     */
    public void setElevation(int cityNumber, double elevation)
    {
        CityVertex vertex = (CityVertex) vertices.get(cityNumber);
        vertex.setElevation(elevation);
    }

    /**
     * Retrieves the city code iterator that goes in order sequentially by city number.
     * @return The sequential iterator for the city codes of the cities in the graph
     */
    public Iterator<String> getCityCodeIterator()
    {
        return new CityCodeIterator();
    }

    /** This is for an iterator to retrieve vertices in order of city number. */
    private class CityCodeIterator implements Iterator<String>
    {
        int vertexPtr;

        /** Constructs the iterator starting from the first city code. */
        private CityCodeIterator()
        {
            vertexPtr = 1;
        }

        /**
         * Returns true if the iteration has more elements.
         * @return True if the iteration has more elements; false otherwise.
         */
        public boolean hasNext() {
            return vertexPtr <= getNumberOfVertices();
        }

        /**
         * Gets the next city code in the iteration.
         * @return the next city code in the iteration
         * @throws NoSuchElementException if the iteration has no more elements
         */
        public String next()
        {
            if (hasNext())
                return ((CityVertex) vertices.get(vertexPtr++)).getCityCode();
            else
                throw new NoSuchElementException();
        }
    }
}