/*
 * Copyright (c) 1995, 2008, Oracle and/or its affiliates. All rights reserved.
 *
 * Redistribution and use in source and binary forms, with or without
 * modification, are permitted provided that the following conditions
 * are met:
 *
 *   - Redistributions of source code must retain the above copyright
 *     notice, this list of conditions and the following disclaimer.
 *
 *   - Redistributions in binary form must reproduce the above copyright
 *     notice, this list of conditions and the following disclaimer in the
 *     documentation and/or other materials provided with the distribution.
 *
 *   - Neither the name of Oracle or the names of its
 *     contributors may be used to endorse or promote products derived
 *     from this software without specific prior written permission.
 *
 * THIS SOFTWARE IS PROVIDED BY THE COPYRIGHT HOLDERS AND CONTRIBUTORS "AS
 * IS" AND ANY EXPRESS OR IMPLIED WARRANTIES, INCLUDING, BUT NOT LIMITED TO,
 * THE IMPLIED WARRANTIES OF MERCHANTABILITY AND FITNESS FOR A PARTICULAR
 * PURPOSE ARE DISCLAIMED.  IN NO EVENT SHALL THE COPYRIGHT OWNER OR
 * CONTRIBUTORS BE LIABLE FOR ANY DIRECT, INDIRECT, INCIDENTAL, SPECIAL,
 * EXEMPLARY, OR CONSEQUENTIAL DAMAGES (INCLUDING, BUT NOT LIMITED TO,
 * PROCUREMENT OF SUBSTITUTE GOODS OR SERVICES; LOSS OF USE, DATA, OR
 * PROFITS; OR BUSINESS INTERRUPTION) HOWEVER CAUSED AND ON ANY THEORY OF
 * LIABILITY, WHETHER IN CONTRACT, STRICT LIABILITY, OR TORT (INCLUDING
 * NEGLIGENCE OR OTHERWISE) ARISING IN ANY WAY OUT OF THE USE OF THIS
 * SOFTWARE, EVEN IF ADVISED OF THE POSSIBILITY OF SUCH DAMAGE.
 */

// Referenced code in components-ListDemoProject-ListDemo class
// https://docs.oracle.com/javase/tutorial/uiswing/examples/zipfiles/components-ListDemoProject.zip

// Referenced code in components-SplitPaneDemoProject-SplitPaneDemo class
// https://docs.oracle.com/javase/tutorial/uiswing/examples/zipfiles/components-SplitPaneDemoProject.zip

// Referenced code in components-TextInputDemoProject-TextInputDemo class
// https://docs.oracle.com/javase/tutorial/uiswing/examples/zipfiles/components-TextInputDemoProject.zip

// Referenced code in layout-SpringFormProject-SpringForm class
// https://docs.oracle.com/javase/tutorial/uiswing/examples/zipfiles/layout-SpringFormProject.zip

package ui;

import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import javax.swing.event.*;

//WishListGUI itself is not a visible component.
public class WishListGUI extends JPanel
        implements ListSelectionListener {
    private JList list;
    private JPanel productInfo;
    private DefaultListModel listModel;
    private JSplitPane splitPane;

    private static final String addString = "Add";
    private static final String removeString = "Remove";
    private JButton removeButton;

    private static final String saveString = "Save";
    private static final String loadString = "Load";
    private JButton saveButton;
    private JButton loadButton;

    private JLabel titleLabel;
    private JLabel priceLabel;
    private JLabel quantityLabel;
    private JLabel starLabel;

    private JTextField titleText;
    private JTextField priceText;
    private JTextField quantityText;
    private JTextField starText;



    public WishListGUI() {
        super(new BorderLayout());

        listModel = new DefaultListModel();
        listModel.addElement("Purse");

        //Create the list and put it in a scroll pane.
        list = new JList(listModel);
        list.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        list.setSelectedIndex(0);
        list.addListSelectionListener(this);

        //Make buttons for list pane
        saveButton = new JButton(saveString);
        saveButton.setActionCommand(saveString);
        saveButton.addActionListener(new SaveListener());

        loadButton = new JButton(loadString);
        loadButton.setActionCommand(loadString);
        loadButton.addActionListener(new LoadListener());

        //Create a panel that uses BoxLayout.
        JPanel listButtonPane = new JPanel();
        JPanel listAndButtonPane = new JPanel();
        JScrollPane listScrollPane = getjScrollPane(list, listButtonPane, listAndButtonPane, saveButton, loadButton);

        //Create the panel and put it in the right scroll pane.
        productInfo = new JPanel(new SpringLayout());
        initLabelText();
        addLabelText(titleLabel, titleText);
        addLabelText(priceLabel, priceText);
        addLabelText(quantityLabel, quantityText);
        addLabelText(starLabel, starText);
        SpringUtilities.makeCompactGrid(productInfo, 4, 2, 6, 6, 6, 6);

        JButton addButton = new JButton(addString);
        AddListener addListener = new AddListener(addButton);
        addButton.setActionCommand(addString);
        addButton.addActionListener(addListener);
        addButton.setEnabled(false);

        removeButton = new JButton(removeString);
        removeButton.setActionCommand(removeString);
        removeButton.addActionListener(new RemoveListener());

        addEventListener(addListener);

        //Create a panel that uses BoxLayout.
        JPanel productButtonPane = new JPanel();
        JPanel productAndButtonPane = new JPanel();
        JScrollPane productScrollPane = getjScrollPane(productInfo, productButtonPane, productAndButtonPane,
                addButton, removeButton);

        setSplitPane(listScrollPane, productScrollPane);
    }

    private void addEventListener(AddListener addListener) {
        titleText.addActionListener(addListener);
        titleText.getDocument().addDocumentListener(addListener);
        priceText.addActionListener(addListener);
        priceText.getDocument().addDocumentListener(addListener);
        quantityText.addActionListener(addListener);
        quantityText.getDocument().addDocumentListener(addListener);
        starText.addActionListener(addListener);
        starText.getDocument().addDocumentListener(addListener);
    }

    private void setSplitPane(JScrollPane listScrollPane, JScrollPane productScrollPane) {
        splitPane = new JSplitPane(JSplitPane.HORIZONTAL_SPLIT,
                listScrollPane, productScrollPane);
        splitPane.setOneTouchExpandable(true);
        splitPane.setDividerLocation(150);

        Dimension minimumSize = new Dimension(100, 50);
        listScrollPane.setMinimumSize(minimumSize);
        productScrollPane.setMinimumSize(minimumSize);

        splitPane.setPreferredSize(new Dimension(400, 200));
    }

    private JScrollPane getjScrollPane(JComponent topPane, JPanel buttonPane, JPanel combinePane,
                                       JButton left, JButton right) {
        buttonPane.setLayout(new BoxLayout(buttonPane, BoxLayout.LINE_AXIS));
        buttonPane.add(left);
        buttonPane.add(Box.createHorizontalStrut(1));
        buttonPane.add(Box.createHorizontalStrut(1));
        buttonPane.add(right);
        buttonPane.setBorder(BorderFactory.createEmptyBorder(1,1,1,1));

        combinePane.setLayout(new BoxLayout(combinePane, BoxLayout.Y_AXIS));
        combinePane.add(topPane, BorderLayout.CENTER);
        combinePane.add(buttonPane, BorderLayout.PAGE_END);
        JScrollPane scrollPane = new JScrollPane(combinePane);
        return scrollPane;
    }

    private void initLabelText() {
        titleLabel = new JLabel("Title: ", JLabel.TRAILING);
        priceLabel = new JLabel("Price($): ", JLabel.TRAILING);
        quantityLabel = new JLabel("Quantity: ", JLabel.TRAILING);
        starLabel = new JLabel("Star: ", JLabel.TRAILING);

        titleText = new JTextField();
        priceText = new JTextField();
        quantityText = new JTextField();
        starText = new JTextField();
    }

    private void addLabelText(JLabel label, JTextField text) {
        productInfo.add(label);
        label.setLabelFor(text);
        productInfo.add(text);
    }

    public JSplitPane getSplitPane() {
        return splitPane;
    }

    /**
     * Create the GUI and show it.  For thread safety,
     * this method should be invoked from the
     * event-dispatching thread.
     */
    private static void createAndShowGUI() {

        //Create and set up the window.
        JFrame frame = new JFrame("WishListGUI");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        ui.WishListGUI wishListGUI = new ui.WishListGUI();
        frame.getContentPane().add(wishListGUI.getSplitPane());

        //Display the window.
        frame.pack();
        frame.setVisible(true);
    }

    public static void main(String[] args) {
        //Schedule a job for the event-dispatching thread:
        //creating and showing this application's GUI.
        javax.swing.SwingUtilities.invokeLater(new Runnable() {
            public void run() {
                createAndShowGUI();
            }
        });
    }

    class RemoveListener implements ActionListener {
        public void actionPerformed(ActionEvent e) {
            //This method can be called only if
            //there's a valid selection
            //so go ahead and remove whatever's selected.
            int index = list.getSelectedIndex();
            listModel.remove(index);

            int size = listModel.getSize();

            if (size == 0) { //Nobody's left, disable firing.
                removeButton.setEnabled(false);

            } else { //Select an index.
                if (index == listModel.getSize()) {
                    //removed item in last position
                    index--;
                }

                list.setSelectedIndex(index);
                list.ensureIndexIsVisible(index);
            }
        }
    }

    //This listener is shared by the text field and the add button.
    class AddListener implements ActionListener, DocumentListener {
        private boolean alreadyEnabled = false;
        private JButton button;

        public AddListener(JButton button) {
            this.button = button;
        }

        //Required by ActionListener.
        public void actionPerformed(ActionEvent e) {
            String title = titleText.getText();

            //User didn't type in a unique title...
            if (title.equals("") || alreadyInList(title)) {
                Toolkit.getDefaultToolkit().beep();
                titleText.requestFocusInWindow();
                titleText.selectAll();
                return;
            }

            int index = list.getSelectedIndex(); //get selected index
            if (index == -1) { //no selection, so insert at beginning
                index = 0;
            } else {           //add after the selected item
                index++;
            }

            listModel.addElement(titleText.getText() + " $" + priceText.getText() + " x " + quantityText.getText()
                    + " star rating: " + starText.getText());

            //Reset the text field.
            titleText.requestFocusInWindow();
            titleText.setText("");

            priceText.requestFocusInWindow();
            priceText.setText("");

            quantityText.requestFocusInWindow();
            quantityText.setText("");

            starText.requestFocusInWindow();
            starText.setText("");

            //Select the new item and make it visible.
            list.setSelectedIndex(index);
            list.ensureIndexIsVisible(index);
        }

        //This method tests for string equality. You could certainly
        //get more sophisticated about the algorithm.  For example,
        //you might want to ignore white space and capitalization.
        protected boolean alreadyInList(String title) {
            return listModel.contains(title);
        }

        //Required by DocumentListener.
        public void insertUpdate(DocumentEvent e) {
            enableButton();
        }

        //Required by DocumentListener.
        public void removeUpdate(DocumentEvent e) {
            handleEmptyTextField(e);
        }

        //Required by DocumentListener.
        public void changedUpdate(DocumentEvent e) {
            if (!handleEmptyTextField(e)) {
                enableButton();
            }
        }

        private void enableButton() {
            if (!alreadyEnabled) {
                button.setEnabled(true);
            }
        }

        private boolean handleEmptyTextField(DocumentEvent e) {
            if (e.getDocument().getLength() <= 0) {
                button.setEnabled(false);
                alreadyEnabled = false;
                return true;
            }
            return false;
        }
    }

    class SaveListener implements ActionListener {
        public void actionPerformed(ActionEvent e) {
            // TODO
        }
    }

    class LoadListener implements ActionListener {
        public void actionPerformed(ActionEvent e) {
            // TODO
        }
    }

    //This method is required by ListSelectionListener.
    public void valueChanged(ListSelectionEvent e) {
        if (e.getValueIsAdjusting() == false) {

            if (list.getSelectedIndex() == -1) {
                //No selection, disable remove button.
                removeButton.setEnabled(false);

            } else {
                //Selection, enable the remove button.
                removeButton.setEnabled(true);
            }
        }
    }


}