package CityUI;

import CityMapPackage.CityGraph;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/**
 * <b><u>CS 241 Homework 4 - City Graph</b></u>
 * <br>
 * This class is the panel for removing a road, if it exists, between two given cities in the
 * graph.
 *
 * @author Lisa Chen
 * @since May 28, 2018
 */
class RemoveRoadPanel extends TwoCityInteractionPanel
{
    private final String BUTTON_TEXT = "Remove Road";
    private final String SUCCESS_TEXT = "Road was successfully removed.";
    private final String NOT_EXIST_TEXT = "Road between the two cities does not exist.";
    private final String NO_SELECTION_TEXT = "One or more cities were not selected.";
    private boolean madeChanges;
    private Main frame;
    private JButton submitButton;
    private JComboBox listBox1;
    private JComboBox listBox2;
    private JPanel reqSection;
    private JPanel resultSection;
    private JLabel resultLabel;

    /**
     * Constructs the default remove road panel with the graph data, a submit button, and
     * drop down boxes for the user to select the two cities to remove the road between them.
     * Selections are default to select nothing and the result section is empty.
     * @param graph The city graph data object
     */
    public RemoveRoadPanel(Main frame, CityGraph graph)
    {
        super(graph);
        submitButton = new JButton(BUTTON_TEXT);
        resultLabel = new JLabel();
        resultSection = new JPanel();
        madeChanges = false;
        this.frame = frame;
        listBox1 = getListBox1();
        listBox2 = getListBox2();
        reqSection = getReqSection();

        //sets up and adds the submit button to original two drop down panel
        submitButton.addActionListener(new ButtonListener());
        reqSection.add(submitButton);

        resultSection.add(resultLabel);
        add(resultSection);
    }

    /**
     * This is a listener for the submit button. When the button is pressed, it updates the
     * result section for whether or not the road between the two inputted city codes was
     * successfully removed.
     */
    private class ButtonListener implements ActionListener
    {
        /**
         * Displays to the user if the road between the two input city codes was successfully
         * removed when the submit button is pressed.
         * @param buttonPressed The submit button was pressed
         */
        public void actionPerformed(ActionEvent buttonPressed)
        {
            int selection1Index = listBox1.getSelectedIndex();
            int selection2Index = listBox2.getSelectedIndex();
            boolean roadRemoved = graph.removeEdge(selection1Index,selection2Index);
            Color dkGreen = Color.decode("#2A5733");
            Color dkRed = Color.decode("#660000");

            //when two valid city codes are inputted
            if (selection1Index > 0 && selection2Index > 0)
            {
                //road exists and was removed
                if (roadRemoved)
                {
                    resultLabel.setText(SUCCESS_TEXT);
                    resultLabel.setForeground(dkGreen);
                    if (!madeChanges)
                    {
                        madeChanges = true;
                        frame.addSavePromptDialog();
                    }
                }

                //road does not exist between the two city codes
                else
                {
                    resultLabel.setText(NOT_EXIST_TEXT);
                    resultLabel.setForeground(dkRed);
                }
            }

            //one or more city codes were not selected
            else
            {
                resultLabel.setText(NO_SELECTION_TEXT);
                resultLabel.setForeground(dkRed);
            }

            resultSection.updateUI();
            updateUI();
        }
    }
}
