package CityUI;

import CityMapPackage.CityGraph;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Iterator;

/**
 * <b><u>CS 241 Homework 4 - City Graph</b></u>
 * <br>
 * This class creates the interface panel for the city information. The user is able to choose
 * a city code and the interface will show the related full city name, population, and
 * elevation, as retrieved from the city graph.
 *
 * @author Lisa Chen
 * @since May 28, 2018
 */
class InfoPanel extends JPanel
{
    private final int PANEL_WIDTH = 480;
    private final int PANEL_HEIGHT = 100;
    private CityGraph graph;
    private JPanel listBoxSection;
    private JComboBox listBox;
    private JPanel infoSection;

    /**
     * Constructs the default city information panel with no city code selected.
     * @param graph City graph object with city vertices data
     */
    public InfoPanel(CityGraph graph)
    {
        this.graph = graph;
        listBoxSection = new JPanel();
        listBox = new JComboBox<>();
        infoSection = new JPanel();

        setPreferredSize(new Dimension(PANEL_WIDTH,PANEL_HEIGHT));
        setLayout(new GridLayout(1,2));

        //list box section setup
        listBoxSection.setAlignmentY(listBoxSection.TOP_ALIGNMENT);
        initializeCityListBox();

        add(listBoxSection);
        add(infoSection);

    }

    /** Initiates the city list box panel section with the drop down box of city codes. */
    private void initializeCityListBox()
    {
        Iterator<String> cityCodeIterator = graph.getCityCodeIterator();
        String boxHeader = "City Code";

        //sets up the drop down box for the list of cities
        listBox.addItem(boxHeader);
        //loops to add all the city codes
        while(cityCodeIterator.hasNext())
            listBox.addItem(cityCodeIterator.next());

        listBox.addActionListener(new ChoiceListener());
        listBoxSection.add(listBox);
    }

    /**
     * This is a listener class that listens to the user's choice from the drop-down box of
     * city codes.
     */
    private class ChoiceListener implements ActionListener
    {
        /**
         * Updates the UI to contain city information based on the item selected from the
         * drop down box
         * @param itemSelected When an item from the drop down box is selected
         */
        public void actionPerformed(ActionEvent itemSelected)
        {
            int selectedIndex = listBox.getSelectedIndex();

            remove(infoSection); //remove info section to update UI

            //clears out the information if nothing is selected
            if (selectedIndex == 0)
                infoSection = new JPanel();
            else
                infoSection = populateInfoPanel(selectedIndex);

            add(infoSection);
            updateUI();
        }

        /**
         * Populates the panel with the associated city information for a city.
         * @param cityNumber The city number reference for a city in the graph
         * @return The information panel with the new information from the city
         */
        private JPanel populateInfoPanel(int cityNumber)
        {
            JPanel panel = new JPanel();
            JLabel[] labelsArray = new JLabel[3];

            panel.setLayout(new GridLayout(0, 1));

            //formats the population to not have a decimal
            String population = Double.toString(graph.getPopulation(cityNumber));
            int decimalIndex = population.lastIndexOf('.');
            population = population.substring(0,decimalIndex);

            //creates the information labels for the cities
            labelsArray[0] = new JLabel("City Name: " + graph.getFullCityName(cityNumber));
            labelsArray[1] = new JLabel("Population: " + population);
            labelsArray[2] = new JLabel("Elevation: " + graph.getElevation(cityNumber));

            //add info labels to panel
            for (int index = 0; index < labelsArray.length; index++)
                panel.add(labelsArray[index]);

            return panel;

        }
    }
}
