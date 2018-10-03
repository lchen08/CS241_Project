package CityUI;

import CityMapPackage.CityGraph;
import javax.swing.*;
import java.awt.*;
import java.util.Iterator;

/**
 * <b><u>CS 241 Homework 4 - City Graph</b></u>
 * <br>
 * This is a general panel class with two drop-down boxes populated with the city codes from
 * the city graph.
 *
 * @author Lisa Chen
 * @since May 28, 2018
 */
class TwoCityInteractionPanel extends JPanel
{
    private final int PANEL_WIDTH = 420;
    private final int PANEL_HEIGHT = 250;
    private final int GRID_HEIGHT = 20;
    private int GRID_SPACE = 30;
    protected CityGraph graph;
    private JPanel reqSection;
    private JComboBox listBox1;
    private JComboBox listBox2;
    protected GridLayout reqLayout;

    /**
     * Constructs the default panel from information from the graph. The panel has two
     * drop-down boxes populated with city codes from the graph.
     * @param graph The city graph data object
     */
    public TwoCityInteractionPanel(CityGraph graph)
    {
        this.graph = graph;
        reqSection = new JPanel();
        listBox1 = new JComboBox<>();
        listBox2 = new JComboBox<>();
        reqLayout = new GridLayout(1,0);

        //formatting for aesthetic purposes
        setPreferredSize(new Dimension(PANEL_WIDTH,PANEL_HEIGHT));
        reqLayout.setHgap(GRID_SPACE);
        reqSection.setLayout(reqLayout);

        initializeCityListBoxes();
        add(reqSection);
    }

    /** Initiates the city list box panel section with the two drop down boxes of city codes. */
    private void initializeCityListBoxes()
    {
        Iterator<String> cityCodeIterator = graph.getCityCodeIterator();
        String box1Header = "From City";
        String box2Header = "To City";

        //sets up the drop down box for the list of cities
        listBox1.addItem(box1Header);
        listBox2.addItem(box2Header);
        //loops to add all the city codes
        while (cityCodeIterator.hasNext())
        {
            String nextItem = cityCodeIterator.next();
            listBox1.addItem(nextItem);
            listBox2.addItem(nextItem);
        }

        //list box section formatting
        reqSection.setPreferredSize(new Dimension(PANEL_WIDTH,GRID_HEIGHT));
        reqSection.add(listBox1);
        reqSection.add(listBox2);
    }

    /**
     * Retrieves the user request section panel object.
     * @return The user request section panel
     */
    public JPanel getReqSection() { return reqSection; }

    /**
     * Retrieves the first city code drop-down box object.
     * @return The first city code drop-down box
     */
    public JComboBox getListBox1() { return listBox1; }

    /**
     * Retrieves the second city code drop-down box object.
     * @return The second city code drop-down box
     */
    public JComboBox getListBox2() { return listBox2; }
}
