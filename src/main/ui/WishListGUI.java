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

// Referenced code in JsonSerializationDemo-WorkRoomApp class
// https://github.students.cs.ubc.ca/CPSC210/JsonSerializationDemo

// Referenced code in AlarmSystem-ScreenPrinter class
// https://github.students.cs.ubc.ca/CPSC210/AlarmSystem.git

// Referenced code in:
// https://docs.oracle.com/javase/8/docs/api/javax/swing/JOptionPane.html
// https://stackoverflow.com/questions/6578205/swing-jlabel-text-change-on-the-running-application
// https://docs.oracle.com/javase/tutorial/uiswing/events/intro.html
// https://stackoverflow.com/questions/16295942/java-swing-adding-action-listener-for-exit-on-close

package ui;

import java.awt.*;
import java.awt.event.*;
import java.io.FileNotFoundException;
import java.io.IOException;
import javax.swing.*;
import javax.swing.event.*;

import model.Event;
import model.EventLog;
import model.Product;
import model.ShoppingWishList;
import persistence.JsonReader;
import persistence.JsonWriter;

// Represents the shopping wish list Graphical User Interface
public class WishListGUI extends JPanel
        implements ListSelectionListener {
    private static final String addString = "Add";
    private static final String removeString = "Remove";
    private static final String saveString = "Save";
    private static final String loadString = "Load";
    private static final String JSON_STORE = "./data/shoppingWishList.json";

    private JList list;
    private JPanel productInfo;
    private DefaultListModel listModel;
    private JSplitPane splitPane;

    private JButton removeButton;
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

    private ShoppingWishList wishList;

    private JsonWriter jsonWriter;
    private JsonReader jsonReader;


    // EFFECTS: set up and runs shopping wish list GUI
    public WishListGUI() {
        super(new BorderLayout());
        listSetUp();

        addSaveButton();
        addLoadButton();

        JPanel listButtonPane = new JPanel();
        JPanel listAndButtonPane = new JPanel();
        JScrollPane listScrollPane = getjScrollPane(list, listButtonPane, listAndButtonPane, saveButton, loadButton);

        productInfo = new JPanel(new SpringLayout());
        initLabelText();
        addLabelText();
        SpringUtilities.makeCompactGrid(productInfo, 4, 2, 6, 6, 6, 6);

        JButton addButton = new JButton(addString);
        AddListener addListener = new AddListener(addButton);
        addAddButton(addButton, addListener);

        addRemoveButton();

        addEventListener(addListener);

        JPanel productButtonPane = new JPanel();
        JPanel productAndButtonPane = new JPanel();
        JScrollPane productScrollPane = getjScrollPane(productInfo, productButtonPane, productAndButtonPane,
                addButton, removeButton);

        setSplitPane(listScrollPane, productScrollPane);
    }

    // MODIFIES: this
    // EFFECTS: sets up the wishList, Json, listModel and JList
    private void listSetUp() {
        wishList = new ShoppingWishList();
        jsonWriter = new JsonWriter(JSON_STORE);
        jsonReader = new JsonReader(JSON_STORE);

        listModel = new DefaultListModel();

        list = new JList(listModel);
        list.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        list.setSelectedIndex(0);
        list.addListSelectionListener(this);
    }

    // MODIFIES: this
    // EFFECTS: makes the addButton
    private void addAddButton(JButton addButton, AddListener addListener) {
        addButton.setActionCommand(addString);
        addButton.addActionListener(addListener);
        addButton.setEnabled(false);
    }

    // MODIFIES: this
    // EFFECTS: makes the removeButton
    private void addRemoveButton() {
        removeButton = new JButton(removeString);
        removeButton.setActionCommand(removeString);
        removeButton.addActionListener(new RemoveListener());
    }

    // MODIFIES: this
    // EFFECTS: makes the loadButton
    private void addLoadButton() {
        loadButton = new JButton(loadString);
        loadButton.setActionCommand(loadString);
        loadButton.addActionListener(new LoadListener());
    }

    // MODIFIES: this
    // EFFECTS: makes the saveButton
    private void addSaveButton() {
        saveButton = new JButton(saveString);
        saveButton.setActionCommand(saveString);
        saveButton.addActionListener(new SaveListener());
    }

    // MODIFIES: this
    // EFFECTS: adds the event listener for adding a product
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

    // MODIFIES: this
    // EFFECTS: sets up the split pane
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

    // MODIFIES: this
    // EFFECTS: sets up the scroll pane
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

    // MODIFIES: this
    // EFFECTS: instantiates the JLabels and JTextFields
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

    // MODIFIES: this
    // EFFECTS: pair up the labels and textFields and add to pane
    private void addLabelText() {
        productInfo.add(titleLabel);
        titleLabel.setLabelFor(titleText);
        productInfo.add(titleText);

        productInfo.add(priceLabel);
        priceLabel.setLabelFor(priceText);
        productInfo.add(priceText);

        productInfo.add(quantityLabel);
        quantityLabel.setLabelFor(quantityText);
        productInfo.add(quantityText);

        productInfo.add(starLabel);
        starLabel.setLabelFor(starText);
        productInfo.add(starText);
    }

    // EFFECTS: getter for split pane
    public JSplitPane getSplitPane() {
        return splitPane;
    }

    // MODIFIES: this
    // EFFECTS: Constructs and display the GUI
    private static void createAndShowGUI() {
        JFrame frame = new JFrame("WishListGUI");
        frame.setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
        frame.addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
                for (Event next : EventLog.getInstance()) {
                    System.out.println(next.toString());
                }
                e.getWindow().dispose();
            }
        });
        ui.WishListGUI wishListGUI = new ui.WishListGUI();
        frame.getContentPane().add(wishListGUI.getSplitPane());

        frame.pack();
        frame.setVisible(true);
    }

    public static void main(String[] args) {
        javax.swing.SwingUtilities.invokeLater(new Runnable() {
            public void run() {
                createAndShowGUI();
            }
        });
    }

    // Represents event listener for removing products
    class RemoveListener implements ActionListener {

        // EFFECTS: remove whatever is selected
        public void actionPerformed(ActionEvent e) {
            int index = list.getSelectedIndex();
            listModel.remove(index);
            ImageIcon removePopUp = new ImageIcon("src/main/ui/productRemoved.png");
            JOptionPane.showMessageDialog(null, removePopUp);

            Product product = wishList.getShoppingWishList().get(index);
            wishList.decreaseQuantity(product, product.getQuantity());

            int size = listModel.getSize();

            if (size == 0) { //No product's left, disable removing
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

    // Represents event listener for adding a product
    class AddListener implements ActionListener, DocumentListener {
        private boolean alreadyEnabled = false;
        private JButton button;

        public AddListener(JButton button) {
            this.button = button;
        }

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

            listModel.addElement(titleText.getText() + " $" + priceText.getText() + " quantity: "
                    + quantityText.getText() + " star rating: " + starText.getText());

            wishList.addProduct(titleText.getText(), Double.parseDouble(priceText.getText()),
                    Integer.parseInt(quantityText.getText()));

            Product product = wishList.getShoppingWishList().get(wishList.getShoppingWishList().size() - 1);
            product.rateProduct(Integer.parseInt(starText.getText()));

            resetText();

            list.setSelectedIndex(index);
            list.ensureIndexIsVisible(index);
        }

        // MODIFIES: this
        // EFFECTS: reset the text field
        private void resetText() {
            titleText.requestFocusInWindow();
            titleText.setText("");

            priceText.requestFocusInWindow();
            priceText.setText("");

            quantityText.requestFocusInWindow();
            quantityText.setText("");

            starText.requestFocusInWindow();
            starText.setText("");
        }

        // EFFECTS: return true if list contains the title
        protected boolean alreadyInList(String title) {
            return listModel.contains(title);
        }

        // EFFECTS: Required by DocumentListener.
        public void insertUpdate(DocumentEvent e) {
            enableButton();
        }

        // EFFECTS: Required by DocumentListener.
        public void removeUpdate(DocumentEvent e) {
            handleEmptyTextField(e);
        }

        // EFFECTS: Required by DocumentListener.
        public void changedUpdate(DocumentEvent e) {
            if (!handleEmptyTextField(e)) {
                enableButton();
            }
        }

        // MODIFIES: this
        // EFFECTS: enables button
        private void enableButton() {
            if (!alreadyEnabled) {
                button.setEnabled(true);
            }
        }

        // MODIFIES: this
        // EFFECTS: if textField is empty, disables button and return true;
        // otherwise, return false
        private boolean handleEmptyTextField(DocumentEvent e) {
            if (e.getDocument().getLength() <= 0) {
                button.setEnabled(false);
                alreadyEnabled = false;
                return true;
            }
            return false;
        }
    }

    // Represents event listener for saving wish list
    class SaveListener implements ActionListener {
        public void actionPerformed(ActionEvent e) {
            saveWishList();
        }
    }

    // MODIFIES: this
    // EFFECTS: saves the shopping wish list to file
    private void saveWishList() {
        try {
            jsonWriter.open();
            jsonWriter.write(wishList);
            jsonWriter.close();
        } catch (FileNotFoundException e) {
            // do nothing
        }
    }

    // Represents event listener for loading wish list
    class LoadListener implements ActionListener {
        public void actionPerformed(ActionEvent e) {
            loadWishList();
        }
    }

    // MODIFIES: this
    // EFFECTS: loads shopping wish list from file
    private void loadWishList() {
        try {
            wishList = jsonReader.read();
            for (Product next : wishList.getShoppingWishList()) {
                String title = next.getTitle();
                String price = Double.toString(next.getPrice());
                String quantity = Integer.toString(next.getQuantity());
                String star = Integer.toString(next.getStar());
                listModel.addElement(title + " $" + price + " quantity " + quantity + " star rating: " + star);
            }
        } catch (IOException e) {
            // do nothing
        }
    }


    // MODIFIES: this
    // EFFECTS: enable/disable remove Button base on selection, required by ListSelectionListener.
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