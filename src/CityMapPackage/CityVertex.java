package CityMapPackage;

import GraphPackage.Vertex;

/**
 * <b><u>CS 241 Homework 4 - City Graph</b></u>
 * <br>
 * This class creates a city vertex with the following information:
 * city number (integer), city code (String), full city name (String), population
 * (non-negative integer), and elevation (double).
 *
 * @author Lisa Chen
 * @since May 19, 2018
 */
public class CityVertex extends Vertex<Integer>
{
    String cityCode;
    String fullCityName;
    double population;
    double elevation;

    /**
     * Constructs a city vertex with all the information about the city
     * @param cityNumber The reference city number to be used as a key
     * @param cityCode The city code commonly used to reference the city
     * @param fullCityName The city's full name
     * @param population The city's population
     * @param elevation The city's elevation
     */
    public CityVertex(int cityNumber, String cityCode, String fullCityName, double population,
                      double elevation)
    {
        super(cityNumber);
        setCityCode(cityCode);
        setFullName(fullCityName);
        setPopulation(population);
        setElevation(elevation);
    }

    /**
     * Sets the city code for this city vertex.
     * @param cityCode The new code for the city
     */
    public void setCityCode(String cityCode) { this.cityCode = cityCode; }

    /**
     * Gets the city code for this city vertex.
     * @return The code for the city
     */
    public String getCityCode() { return cityCode; }

    /**
     * Sets the full name of this city vertex.
     * @param fullName The new full name for the city
     */
    public void setFullName(String fullName) { this.fullCityName = fullName; }

    /**
     * Gets the full name of this city vertex.
     * @return The city's full name
     */
    public String getFullCityName() { return fullCityName; }

    /**
     * Sets the population of this city vertex.
     * @param population A new, non-negative, whole number for the city's population
     */
    public void setPopulation(double population)
    {
        if (population < 0)
            throw new IllegalArgumentException("Population input cannot be negative.");
        if (population % 1 != 0)
            throw new IllegalArgumentException("Population cannot be a decimal.");
        this.population = population;
    }
    /**
     * Gets the population of this city vertex.
     * @return The city's population
     */
    public double getPopulation() { return population; }

    /**
     * Sets the elevation of this city vertex.
     * @param elevation The new elevation for the city vertex
     */
    public void setElevation(double elevation) {this.elevation = elevation; }

    /**
     * Gets the elevation of this city vertex.
     * @return The city's elevation
     */
    public double getElevation() { return elevation; }
}
