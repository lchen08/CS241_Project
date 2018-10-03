package CityUI;

import javax.swing.*;
import javax.swing.filechooser.FileFilter;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.FileNotFoundException;

/**
 * <b><u>CS 241 Homework 4 - City Graph</b></u>
 * <br>
 * This class creates the interface for the file request section of the interface. This interface
 * requests from the user the file paths for the city and road files using a file chooser pop-up.
 * It will also alert the user for incorrect file paths submitted and asks the user to submit
 * the paths again.
 *
 * @author Lisa Chen
 * @since May 28, 2018
 */
class FileRequestUI extends JPanel
{
    private final String REQUEST_TEXT = "Please select the city and road files to upload the " +
            "map information.";
    private final String  CITY_REQ_TEXT = "City File";
    private final String ROAD_REQ_TEXT = "Road File";
    private final int BUFFER_SPACE = 10;
    private final int REQ_PANEL_WIDTH = 330;
    private final int REQ_PANEL_HEIGHT = 15;
    private final String DEFAULT_PATH = "No file selected.";
    private final String START_FILE_DIR = System.getProperty("user.dir"); //gets directory of app
    private Main parentFrame;
    private String cityFilePath;
    private String roadFilePath;
    private JFileChooser fc;
    private JPanel submitPanel;
    private JLabel requestText;
    private BorderLayout panelLayout;

    /**
     * Constructs the interface for the file request section.
     * @param parentFrame The parent frame of the interface.
     */
    public FileRequestUI(Main parentFrame)
    {
        this.parentFrame = parentFrame;
        panelLayout = new BorderLayout();
        requestText = new JLabel(REQUEST_TEXT);
        fc = new JFileChooser();
        cityFilePath = DEFAULT_PATH;
        roadFilePath = DEFAULT_PATH;
        submitPanel = getSubmitButton();

        //formatting and layout
        panelLayout.setVgap(BUFFER_SPACE);
        setLayout(panelLayout);
        setBorder(BorderFactory.createEmptyBorder(BUFFER_SPACE, BUFFER_SPACE, BUFFER_SPACE,
                BUFFER_SPACE));

        add(requestText, panelLayout.NORTH);
        add(initializeFileReqPanel(), panelLayout.CENTER);
    }

    /**
     * Initializes the overall file request panel that contains the choose file buttons, the city
     * and road file paths, and the submit button.
     * @return The overall file request panel
     */
    private JPanel initializeFileReqPanel()
    {
        JPanel overallPanel = new JPanel();
        JPanel cityReqPanel = new JPanel();
        JPanel roadReqPanel = new JPanel();
        GridLayout gridLayout = new GridLayout(3,1);

        overallPanel.setLayout(gridLayout);

        //creates the choose files section for city and road
        cityReqPanel.add(new JLabel(CITY_REQ_TEXT));
        cityReqPanel.add(getChooseFilePanel(CITY_REQ_TEXT));
        roadReqPanel.add(new JLabel(ROAD_REQ_TEXT));
        roadReqPanel.add(getChooseFilePanel(ROAD_REQ_TEXT));

        overallPanel.add(cityReqPanel);
        overallPanel.add(roadReqPanel);
        overallPanel.add(submitPanel);

        return overallPanel;
    }

    /**
     * Initializes a general choose file panel.
     * @param fileRequested File type requested (city or road)
     * @return The choose file panel
     */
    private JPanel getChooseFilePanel(String fileRequested)
    {
        String chooseText = "Select A File";
        JPanel choosePanel = new JPanel();
        JButton chooseFileButton = new JButton(chooseText);
        JLabel filenameLabel = new JLabel(DEFAULT_PATH);

        //formatting and adding listeners
        filenameLabel.setPreferredSize(new Dimension(REQ_PANEL_WIDTH,REQ_PANEL_HEIGHT));
        chooseFileButton.addActionListener(new ChooseFileButtonListener(filenameLabel,
                fileRequested));

        //formats and sets up the panel
        choosePanel.setBorder(BorderFactory.createLineBorder(Color.BLACK));
        choosePanel.add(chooseFileButton);
        choosePanel.add(filenameLabel);

        return choosePanel;
    }

    /**
     * Retrieves the container for the submit button.
     * @return The panel container of the submit button
     */
    private JPanel getSubmitButton()
    {
        JPanel buttonPanel = new JPanel();
        JButton button = new JButton("Submit");

        button.addActionListener(new SubmitButtonListener());
        buttonPanel.setBorder(BorderFactory.createEmptyBorder(BUFFER_SPACE, BUFFER_SPACE,
                BUFFER_SPACE, BUFFER_SPACE));
        buttonPanel.add(button);

        return buttonPanel;
    }

    /**
     * This is the listener class for the "Choose File" button. It updates the file paths for
     * the city and road files for the user and for the parent frame per what the user chooses.
     */
    private class ChooseFileButtonListener implements ActionListener
    {
        private String fileRequested;
        private JLabel fileNameText;

        /**
         * Constructs the button listener and related attributes.
         * @param fileNameText The label for the filename path for user's reference
         * @param fileRequested The type of file requested (city or road)
         */
        private ChooseFileButtonListener(JLabel fileNameText, String fileRequested)
        {
            this.fileNameText = fileNameText;
            this.fileRequested = fileRequested;

            fc.setCurrentDirectory(new File(START_FILE_DIR));
            fc.setFileFilter(new DatFileFilter());
            fc.setAcceptAllFileFilterUsed(false);
        }

        /**
         * Invoked when the "Choose File" button is pressed. Updates to show the filename path
         * to the user.
         * @param buttonPressed The choose file button is pressed
         */
        public void actionPerformed(ActionEvent buttonPressed)
        {
            //clears selected file since both choose buttons use same file chooser for directory
            fc.setSelectedFile(new File(""));
            int returnVal = fc.showDialog(fc, "Select " + fileRequested);
            if (returnVal == fc.APPROVE_OPTION)
            {
                //updates the file path for user's reference
                fileNameText.setText(fc.getSelectedFile().toString());

                //updates the file paths depending on which "Choose File" button was pressed
                if (fileRequested.equals(CITY_REQ_TEXT))
                    cityFilePath = fileNameText.getText();
                else if (fileRequested.equals(ROAD_REQ_TEXT))
                    roadFilePath = fileNameText.getText();
                else
                    throw new RuntimeException("Error: Unable to assign file path.");
            }
        }
    }

    /**
     * This is a listener class for the submit button where the city and road filename paths
     * are submitted to the parent frame.
     */
    private class SubmitButtonListener implements ActionListener
    {

        /**
         * When the submit button is pressed, it submits the city and road file paths to
         * initialize the city map with the information from the files. It handles issues with
         * the files (incorrect files, wrong pathways, or no files selected) and prompts the
         * user to enter again.
         * @param buttonPressed The submit button is pressed
         */
        public void actionPerformed(ActionEvent buttonPressed)
        {
            try
            {
                parentFrame.initializeCityMapUI(new File(cityFilePath), new File(roadFilePath));
            }
            //when a file isn't selected or file path is wrong for some reason
            catch (FileNotFoundException e)
            {
                JOptionPane.showMessageDialog(parentFrame,
                        "One or more files do not exist or were not selected.",
                        "File Not Found", JOptionPane.WARNING_MESSAGE);
                //hides and recreates the frame since it is no longer interactive on pop-up dialog
                parentFrame.setVisible(false);
                parentFrame = new Main();
                parentFrame.setVisible(true);
            }
            //when the files chosen have the wrong text, it can cause numerous exceptions
            catch(Exception e2)
            {
                JOptionPane.showMessageDialog(parentFrame,
                        "One or more files selected are not the correct files for " +
                                "this program.",
                        "Incorrect Files", JOptionPane.WARNING_MESSAGE);
                //hides and recreates the frame since it is no longer interactive on pop-up dialog
                parentFrame.setVisible(false);
                parentFrame = new Main();
                parentFrame.setVisible(true);
            }
        }
    }

    /** This is a file filter class that is filters for .dat files in the file chooser. */
    private class DatFileFilter extends FileFilter
    {
        private String acceptableExt = "dat";

        /**
         * Tests whether or not the specified abstract pathname should be included in a
         * pathname list.
         * @param f The pathname to be tested
         * @return True if the file path ends with .dat or is a directory; false otherwise
         */
        public boolean accept(File f)
        {
            //allows directory folders to show
            if (f.isDirectory())
                return true;

            String path = f.getPath();
            int dotLocation = path.lastIndexOf('.');
            if (dotLocation > 0)
            {
                String ext = path.substring(dotLocation + 1);
                if (ext.equals(acceptableExt))
                    return true;
            }
            return false;
        }

        /**
         * Gets the description of the file type that is accepted.
         * @return The description of the accepted file type
         */
        public String getDescription()
        {
            return "." + acceptableExt;
        }
    }
}
