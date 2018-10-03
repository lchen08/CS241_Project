package CityUI;

import CityMapPackage.CityGraph;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;

/**
 * <b><u>CS 241 Homework 4 - City Graph</b></u>
 * <br>
 * This class is the panel for adding a road, if it doesn't already exist, between two given
 * cities in the graph. The user cannot add a road between the same city.
 *
 * @author Lisa Chen
 * @since May 28, 2018
 */
class AddRoadPanel extends TwoCityInteractionPanel
{
    private final String BUTTON_TEXT = "Add Road";
    private final String SUCCESS_TEXT = "Road was successfully added.";
    private final String NOT_EXIST_TEXT = "Road between the two cities already exists.";
    private final String NO_SELECTION_TEXT = "One or more cities were not selected.";
    private final String INVALID_DISTANCE_TEXT = "Distance must be a valid non-negative integer.";
    private final String SAME_CITY_INPUTS_TEXT = "City codes should not be the same to add a road.";
    private final String DEFAULT_FIELD_TEXT = "Enter distance";
    private Main frame;
    private boolean madeChanges;
    private int GRID_SPACE = 8;
    private JButton submitButton;
    private JTextField distanceField;
    private JComboBox listBox1;
    private JComboBox listBox2;
    private JPanel reqSection;
    private JPanel resultSection;
    private JLabel resultLabel;

    /**
     * Constructs the default add road panel with the graph data, a submit button, a text field to
     * input the road distance, and drop down boxes for the user to select the two cities to add
     * the new road between them. Selections are default to select nothing and the result
     * section is empty.
     * @param graph The city graph data object
     */
    public AddRoadPanel(Main frame, CityGraph graph)
    {
        super(graph);
        submitButton = new JButton(BUTTON_TEXT);
        distanceField = new JTextField(DEFAULT_FIELD_TEXT);
        resultLabel = new JLabel();
        resultSection = new JPanel();
        madeChanges = false;
        this.frame = frame;
        listBox1 = getListBox1();
        listBox2 = getListBox2();
        reqSection = getReqSection();

        distanceField.addFocusListener(new FieldListener());
        submitButton.addActionListener(new ButtonListener());

        //sets up, adds text field for distance, and submit button to original two dropdown panel
        reqSection.add(distanceField);
        reqSection.add(submitButton);

        reqLayout.setHgap(GRID_SPACE);
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
            Color dkGreen = Color.decode("#2A5733");
            Color dkRed = Color.decode("#660000");
            double distance = -1;
            int selection1Index = listBox1.getSelectedIndex();
            int selection2Index = listBox2.getSelectedIndex();
            String fieldText = distanceField.getText();

            //processes the field text and parses to double, if possible
            try
            {
                distance = Double.parseDouble(fieldText);
            } catch (NumberFormatException e) { } //do nothing if caught, error handled elsewhere

            //if unable to parse field input or field input contained a negative number
            if(distance < 0 || distance % 1 != 0)
            {
                resultLabel.setText(INVALID_DISTANCE_TEXT);
                resultLabel.setForeground(dkRed);
            }
            //appropriate distance entry inputted
            else
            {
                boolean roadAdded = graph.addEdge(selection1Index,selection2Index, distance);

                //when two valid city codes are inputted
                if (selection1Index > 0 && selection2Index > 0)
                {
                    //both drop down inputs are the same city codes
                    if (selection1Index == selection2Index)
                    {
                        resultLabel.setText(SAME_CITY_INPUTS_TEXT);
                        resultLabel.setForeground(dkRed);
                    }
                    //road exists and was removed
                    else if (roadAdded)
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
            }

            resultSection.updateUI();
            updateUI();
        }
    }

    /** This is a focus listener for the text field. */
    private class FieldListener implements FocusListener
    {
        /**
         * Invoked when the text field is clicked/focused. Selects all text in the text field.
         * @param fieldFocused When the text field is clicked/focused
         */
        public void focusGained(FocusEvent fieldFocused) { distanceField.selectAll(); }
        public void focusLost(FocusEvent e) { } //not used
    }
}
