package CityUI;

import CityMapPackage.CityGraph;
import GraphPackage.VertexInterface;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.util.Iterator;

/**
 * <b><u>CS 241 Homework 4 - City Graph</b></u>
 * <br>
 * This class is for the interactive options panel where the user can select what they would
 * like to do or display for the city map. The user can display the information for a city code,
 * find the minimum distance between two cities, and insert/remove a road between existing cities.
 *
 * @author Lisa Chen
 * @since May 28, 2018
 */
class InteractiveCityPanel extends JPanel
{
    private final String QUESTION = "What would you like to do?";
    private final int BUFFER_SPACE = 10;
    private final int PANEL_WIDTH = 480;
    private final int INTERACTIVE_PANEL_HEIGHT = 260;
    private final int QUESTION_SECTION_HEIGHT = 60;
    private final String[] OPTIONS =
            {
                    "",
                    "Display city information",
                    "Find minimum distance between two cities",
                    "Insert a road between existing cities",
                    "Remove a road between existing cities"
            };
    private Main frame;
    private boolean madeChanges;
    private CityGraph cityGraph;
    private JComboBox<String> optionsBox;
    private JPanel interactiveSection;

    /** Constructs default panel with selection request and an empty interactive section. */
    public InteractiveCityPanel(Main frame, File cityFile, File roadFile) throws
            FileNotFoundException
    {
        cityGraph = new CityGraph(cityFile,roadFile);
        interactiveSection = new JPanel();
        this.frame = frame;
        madeChanges = false;

        //formatting for aesthetic purposes
        interactiveSection.setPreferredSize(new Dimension(PANEL_WIDTH,INTERACTIVE_PANEL_HEIGHT));
        setBorder(BorderFactory.createEmptyBorder(BUFFER_SPACE, BUFFER_SPACE, BUFFER_SPACE,
                BUFFER_SPACE));

        add(initializeQuestionSection());
        add(interactiveSection);
    }

    /**
     * Creates the selection request section with the question prompt and selection options
     * for the user.
     * @return Panel with the question text and the drop-down box
     */
    private JPanel initializeQuestionSection()
    {
        JPanel section = new JPanel();
        JLabel optionsLabel = new JLabel(QUESTION);
        BorderLayout optionsLayout = new BorderLayout();
        JPanel optionsBoxEnclosure = new JPanel();

        //format the panel and labels
        section.setPreferredSize(new Dimension(PANEL_WIDTH, QUESTION_SECTION_HEIGHT));
        section.setAlignmentX(section.CENTER_ALIGNMENT);
        optionsLabel.setHorizontalAlignment(JLabel.CENTER);
        optionsLayout.setVgap(BUFFER_SPACE);
        section.setLayout(optionsLayout);

        //create and set up options box
        optionsBox = new JComboBox<>(OPTIONS);
        optionsBox.addActionListener(new OptionListener());
        optionsBoxEnclosure.add(optionsBox);

        section.add(optionsLabel, BorderLayout.NORTH);
        section.add(optionsBoxEnclosure, BorderLayout.CENTER);

        return section;
    }

    /**
     * Saves the city graph information into new road and city files.
     * @throws FileNotFoundException If the directory for the files do not exist.
     */
    protected void saveGraphInfo() throws FileNotFoundException
    {
        String cityFilename = "city";
        String roadFilename = "road";

        File cityFile = getNonexistingDatFile(cityFilename);
        File roadFile = getNonexistingDatFile(roadFilename);

        PrintWriter cityWriter = new PrintWriter(cityFile);
        PrintWriter roadWriter = new PrintWriter(roadFile);

        //runs through each vertex in order of city number to populate the files
        for (int cityNumber = 1; cityNumber <= cityGraph.getNumberOfVertices(); cityNumber++)
        {
            Iterator<VertexInterface<Integer>> neighborIterator =
                    cityGraph.getNeighborIterator(cityNumber);
            Iterator<Double> weightIterator = cityGraph.getWeightIterator(cityNumber);

            //creates the lines for the city files
            String cityCode = cityGraph.getCityCode(cityNumber);
            String fullCityName = cityGraph.getFullCityName(cityNumber);
            double population = cityGraph.getPopulation(cityNumber);
            double elevation = cityGraph.getElevation(cityNumber);
            cityWriter.printf("%2d  %-5s %-16s %10.0f %6.0f%n", cityNumber, cityCode, fullCityName,
                    population, elevation);

            //creates the lines for the road files
            while(neighborIterator.hasNext())
            {
                int neighborCityNumber = neighborIterator.next().getLabel();
                double weight = weightIterator.next();
                roadWriter.printf("%2d%4d%8.0f%n", cityNumber, neighborCityNumber, weight);
            }
        }
        cityWriter.close();
        roadWriter.close();
    }

    /**
     * Gets the first filename that does not exist in the current directory for .dat files.
     * If the given filename exists, it adds/changes a postfix to the filename until the new
     * filename doesn't exist.
     * @param filename The initial filename to check and add postfixes
     * @return File object that does not yet exist in the current directory
     */
    private File getNonexistingDatFile(String filename)
    {
        String ext = ".dat";
        File file = new File(filename + ext);
        int postfix = 1;

        //updates the filename until it finds a non-existing filename
        while (file.exists())
        {
            file = new File(filename + "_" + postfix + ext);
            postfix++;
        }

        return file;
    }


    /**
     * This is a class listener for the option drop-down box. It changes the panel depending on
     * what option the user chooses.
     */
    private class OptionListener implements ActionListener
    {
        /**
         * Invoked when an option from the drop-down box was selected. Chooses and updates the
         * interactive panel based on user's selection from the drop-down box.
         * @param selectedOption The option from the drop-down that was selected
         */
        public void actionPerformed(ActionEvent selectedOption)
        {
            int selectionIndex = optionsBox.getSelectedIndex();
            //changes the panel based on user choice
            switch(selectionIndex)
            {
                case 1: initializeInfoPane(); break;
                case 2: initializeMinDistancePane(); break;
                case 3: initializeInsertRoadPane(); break;
                case 4: initializeRemoveRoadPane(); break;
                default: clearPane(); updateUI(); break;
            }
        }

        /** Updates the interactive panel to have the info panel. */
        public void initializeInfoPane()
        {
            clearPane();
            interactiveSection.add(new InfoPanel(cityGraph));
            updateUI();
        }

        /** Updates the interactive panel to have the min distance panel. */
        public void initializeMinDistancePane()
        {
            clearPane();
            interactiveSection.add(new MinDistancePanel(cityGraph));
            updateUI();
        }

        /** Updates the interactive panel to have the insert roads between cities panel. */
        public void initializeInsertRoadPane()
        {
            clearPane();
            interactiveSection.add(new AddRoadPanel(frame, cityGraph));
            updateUI();
        }

        /** Updates the interactive panel to have the remove roads between cities panel. */
        public void initializeRemoveRoadPane()
        {
            clearPane();
            interactiveSection.add(new RemoveRoadPanel(frame, cityGraph));
            updateUI();
        }

        /** Clears the interactive panel of all components. */
        public void clearPane() { interactiveSection.removeAll(); }
    }
}
