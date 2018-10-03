package CityUI;

import javax.swing.*;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.File;
import java.io.FileNotFoundException;

/**
 * <b><u>CS 241 Homework 4 - City Graph</b></u>
 * <br>
 * This class is the main UI for the city graph program. It creates a frame that contains the
 * panels that change based on inputs. It requests from the user the city and road files, which,
 * if proper files, will allow the user to request city information, get the minimum distance,
 * and add/remove roads between the cities in the graph.
 *
 * @author Lisa Chen
 * @since May 29, 2018
 */
public class Main extends JFrame
{
    private static final int FRAME_WIDTH = 500;
    private static final int FRAME_HEIGHT = 300;
    private static final String FRAME_TITLE = "CS 241: City Map Interface";
    private static JPanel panel;

    public static void main(String[] args)
    {
         new Main();
    }

    /** Constructs main window frame for the program with prompting user for city/road files. */
    public Main()
    {
        setSize(FRAME_WIDTH, FRAME_HEIGHT);
        setTitle(FRAME_TITLE);
        setResizable(false);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null); //centers the frame to the screen

        panel = new FileRequestUI(this);
        add(panel);
        setVisible(true);
    }

    /** Constructs the default frame with default city options panel. */
    protected void initializeCityMapUI(File cityFile, File roadFile) throws FileNotFoundException
    {
        //removes, updates, and re-adds panel for the options panel after correct files added
        remove(panel);
        panel = new InteractiveCityPanel(this, cityFile, roadFile);
        add(panel);

        revalidate();
        repaint();
    }

    /** Adds the save pop-up dialog to the frame that occurs when the window closes. */
    public void addSavePromptDialog()
    {
        setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
        addWindowListener(new CloseWindowListener());
    }

    /**
     * This is a class that listens to the closing of the window. It creates a pop-up requesting
     * if the user wants to save.
     */
    private class CloseWindowListener extends WindowAdapter {
        /**
         * This method creates a dialog when there is a window closing event.
         *
         * @param windowEvent The window closing event
         */
        @Override
        public void windowClosing(WindowEvent windowEvent)
        {
            int willSave = JOptionPane.showConfirmDialog(null,
                    "Do you wish to save your progress?", "Save Progress",
                    JOptionPane.YES_NO_OPTION,
                    JOptionPane.QUESTION_MESSAGE);

            //saves files as new files for referencing
            if (willSave == JOptionPane.YES_OPTION)
            {
                try
                {
                    ((InteractiveCityPanel) panel).saveGraphInfo();
                }
                catch (Exception e)
                {
                    JOptionPane.showMessageDialog(null,
                            "Error: Data progress could not be saved. Program will close.",
                            "File Save Error", JOptionPane.WARNING_MESSAGE);
                }
                System.exit(0);
            }
            //closes the program, not saving files
            else
                System.exit(0);
        }


    }
}
