package CityUI;

import CityMapPackage.CityGraph;
import StackPackage.ArrayStack;
import StackPackage.StackInterface;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/**
 * <b><u>CS 241 Homework 4 - City Graph</b></u>
 * <br>
 * This class is the panel for finding the minimum distance and path between two given cities
 * in the graph.
 *
 * @author Lisa Chen
 * @since May 28, 2018
 */
class MinDistancePanel extends TwoCityInteractionPanel
{
    private final int RESULT_PANEL_WIDTH = 420;
    private final int RESULT_PANEL_HEIGHT = 80;
    private final int BUFFER_SPACE = 10;
    private JPanel resultSection;
    private JLabel pathLabel;
    private JLabel distanceLabel;
    private JComboBox listBox1;
    private JComboBox listBox2;
    private GridLayout resultLayout;
    private ActionListener selectionListener;

    /**
     * Constructs the default panel with the graph data and drop down boxes for the user to select
     * the two cities to find the minimum distance. Selections are default to select nothing
     * and the result section is empty.
     * @param graph The city graph data object
     */
    public MinDistancePanel(CityGraph graph)
    {
        super(graph);
        pathLabel = new JLabel();
        distanceLabel = new JLabel();
        resultSection = new JPanel();
        resultLayout = new GridLayout(0, 1);
        selectionListener = new SelectionListener();

        //sets up the list boxes
        listBox1 = getListBox1();
        listBox2 = getListBox2();
        listBox1.addActionListener(selectionListener);
        listBox2.addActionListener(selectionListener);

        //UI formatting for aesthetic purposes
        pathLabel.setHorizontalAlignment(pathLabel.LEFT);
        distanceLabel.setHorizontalAlignment(distanceLabel.LEFT);
        resultSection.setPreferredSize(new Dimension(RESULT_PANEL_WIDTH, RESULT_PANEL_HEIGHT));
        resultSection.setBorder(BorderFactory.createEmptyBorder(BUFFER_SPACE, 0,
                BUFFER_SPACE, 0));
        resultLayout.setHgap(BUFFER_SPACE);
        resultSection.setLayout(resultLayout);

        resultSection.add(pathLabel);
        resultSection.add(distanceLabel);
        add(resultSection);
    }

    private class SelectionListener implements ActionListener
    {
        /**
         * When the user selects two valid city codes, the minimum path and distance is
         * displayed to the user, if it exists.
         * @param selectionChosen When a selection is chosen from the drop-down city code box
         */
        public void actionPerformed(ActionEvent selectionChosen)
        {
            final String PATH_INTRO = "Path: ";
            final String DISTANCE_INTRO = "Distance: ";
            String NO_PATH_TEXT = "No path available.";
            int selection1Index = listBox1.getSelectedIndex();
            int selection2Index = listBox2.getSelectedIndex();

            //when a selection is a valid city code
            if (selection1Index > 0 && selection2Index > 0)
            {
                StackInterface<Integer> path = new ArrayStack<>();
                int minDistance = graph.getCheapestPath(selection1Index, selection2Index, path);

                //no path was found
                if (path.isEmpty())
                {
                    pathLabel.setText(NO_PATH_TEXT);
                    distanceLabel.setText("");
                    resultSection.updateUI();
                    updateUI();
                }

                //a path exists between the two cities
                else
                {
                    String pathList = "";

                    //gets the minimum path
                    while (!path.isEmpty())
                    {
                        int cityNumber = path.pop();
                        String cityCode = graph.getCityCode(cityNumber);
                        pathList += cityCode + " ";
                    }

                    pathLabel.setText(PATH_INTRO + pathList);
                    distanceLabel.setText(DISTANCE_INTRO + minDistance);
                    resultSection.updateUI();
                    updateUI();
                }
            }

            //clears the result panel if one or more city codes is not selected
            else
            {
                pathLabel.setText("");
                distanceLabel.setText("");
                resultSection.updateUI();
                updateUI();
            }
        }
    }
}
