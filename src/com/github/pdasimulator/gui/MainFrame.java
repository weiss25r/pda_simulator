package com.github.pdasimulator.gui;

import com.github.pdasimulator.automaton.Automaton;
import com.github.pdasimulator.automaton.State;

import javax.swing.*;
import java.awt.*;
import java.util.LinkedHashSet;
import java.util.Set;
import java.util.jar.JarEntry;

public class MainFrame extends JFrame {
    //panels
    private final JPanel mainPanel = new JPanel();
    private final JPanel buttonsPanel = new JPanel();
    private final JPanel centerPanel = new JPanel();
    private final JScrollPane transitionsPanel = new JScrollPane();
    private final JScrollPane statesPanel = new JScrollPane();
    private final JPanel bottomPanel = new JPanel();

    //components

    //buttons
    private final JButton addBtn = new JButton("Add");
    private final JButton modifyBtn = new JButton("Modify");
    private final JButton deleteBtn = new JButton("Delete");
    private final JButton runBtn = new JButton("Run");

    //lists
    private final JList<State> statesList = new JList<>();
    private final JList<String> transitionsList = new JList<>();
    private DefaultListModel<State> statesModel = new DefaultListModel<>();
    private DefaultListModel<String> transitionsModel = new DefaultListModel<>();

    //run panel
    private final JLabel modeLbl = new JLabel("Accept mode: ");
    private final ButtonGroup rbGroup = new ButtonGroup();
    private final JRadioButton stackRb = new JRadioButton("Empty stack");
    private final JRadioButton finRb = new JRadioButton("Final state");
    private final JTextField inputTxt = new JTextField(10);

    //menu bar
    private final JMenuBar menuBar = new JMenuBar();
    private final JMenu fileMenu = new JMenu("File");
    private final JMenuItem saveMenuItem = new JMenuItem("Save");
    private final JMenuItem loadMenuItem = new JMenuItem("Load");

    private Set<State> stateSet = new LinkedHashSet<>();
    private Automaton pda;

    public MainFrame() {
        super("PDA simulator");
        mainPanel.setLayout(new BorderLayout());

        setMenu();
        setButtonsPanel();
        setLists();
        setBottomPanel();

        //TODO: action listeners for top panel buttons
        setButtonsActionListener();

        this.getContentPane().add(mainPanel);
        this.setSize(new Dimension(550, 550));
        this.setLocationRelativeTo(null);
        this.setDefaultCloseOperation(EXIT_ON_CLOSE);
        this.setResizable(false);
        this.setVisible(true);
    }

    private void setBottomPanel() {
        this.rbGroup.add(stackRb);
        this.rbGroup.add(finRb);

        this.bottomPanel.add(modeLbl);
        this.bottomPanel.add(stackRb);
        this.bottomPanel.add(finRb);
        this.bottomPanel.add(inputTxt);
        this.bottomPanel.add(runBtn);

        this.mainPanel.add(bottomPanel, BorderLayout.SOUTH);
    }

    private void setLists() {
        centerPanel.setLayout(new BorderLayout());
        transitionsPanel.setViewportView(transitionsList);
        transitionsPanel.setBorder(BorderFactory.createTitledBorder("Transitions"));

        //default list model
        statesList.setModel(statesModel);
        transitionsList.setModel(transitionsModel);

        statesPanel.setViewportView(statesList);
        centerPanel.add(statesPanel, BorderLayout.WEST);
        statesPanel.setBorder(BorderFactory.createTitledBorder("States"));
        centerPanel.add(transitionsPanel, BorderLayout.EAST);
        this.mainPanel.add(centerPanel, BorderLayout.CENTER);
    }

    private void setMenu() {
        this.fileMenu.add(saveMenuItem);
        this.fileMenu.add(loadMenuItem);
        this.menuBar.add(fileMenu);
        this.setJMenuBar(menuBar);
    }

    private void setButtonsPanel() {
        this.buttonsPanel.add(addBtn);
        this.buttonsPanel.add(modifyBtn);
        this.buttonsPanel.add(deleteBtn);
        this.mainPanel.add(buttonsPanel, BorderLayout.NORTH);
    }

    private void setButtonsActionListener() {
        this.addBtn.addActionListener(e -> {
            JComboBox<String> comboBox = new JComboBox<>();
            comboBox.addItem("State");
            comboBox.addItem("Transition");
            comboBox.setSelectedIndex(0);

            JOptionPane.showMessageDialog(null, comboBox, "Select an option", JOptionPane.QUESTION_MESSAGE);

            if (comboBox.getSelectedIndex() == 0) {
                this.addState();
            } else this.addTransition();
        });


    }

    private void addState() {
        JPanel panel = new JPanel(new BorderLayout());
        JPanel firstPanel = new JPanel();
        JPanel secondPanel = new JPanel();

        JLabel nameLbl = new JLabel("State name: ");
        JLabel typeLbl = new JLabel("State type: ");

        JTextField nameTxt = new JTextField(10);
        ButtonGroup typeGroup = new ButtonGroup();
        JRadioButton initialRb = new JRadioButton("Initial");
        JRadioButton finalRb = new JRadioButton("Final");
        JRadioButton noneRb = new JRadioButton("None");

        typeGroup.add(initialRb);
        typeGroup.add(finalRb);
        typeGroup.add(noneRb);

        firstPanel.add(nameLbl);
        firstPanel.add(nameTxt);
        secondPanel.add(typeLbl);
        secondPanel.add(initialRb);
        secondPanel.add(finalRb);
        secondPanel.add(noneRb);

        panel.add(firstPanel, BorderLayout.NORTH);
        panel.add(secondPanel, BorderLayout.SOUTH);

        //TODO: catch exceptions

        JOptionPane.showMessageDialog(null, panel, "Add state", JOptionPane.QUESTION_MESSAGE);

        State s = new State(nameTxt.getText(), initialRb.isSelected(), finalRb.isSelected());
        stateSet.add(s);
        statesModel.addElement(s);
    }

    private void addTransition() {
        JPanel[] panels = new JPanel[6];
        JComboBox<State> stateJComboBox = new JComboBox<>();
        JComboBox<State> secondStateComboBox = new JComboBox<>();
        JTextField inputCharTxt = new JTextField(10);
        JTextField inputStackTopTxt = new JTextField(10);
        JTextField outputStackTopTxt = new JTextField(10);
        JLabel inputCharLbl = new JLabel("Input char: ");
        JLabel inputStateLbl = new JLabel("Input state: ");
        JLabel outputStateLbl = new JLabel("Output state: ");
        JLabel inputStackTopLbl = new JLabel("Stack top: ");
        JLabel outputStackTopLbl = new JLabel("Characters to push: ");
        JButton addEmptyStringSymbol = new JButton("Add lambda");

        for(State s : stateSet) {
            stateJComboBox.addItem(s);
        }

        panels[0] = new JPanel();
        panels[0].add(inputStateLbl);
        panels[0].add(stateJComboBox);

        panels[1] = new JPanel();
        panels[1].add(inputCharLbl);
        panels[1].add(inputCharTxt);

        panels[2] = new JPanel();
        panels[2].add(inputStackTopLbl);
        panels[2].add(inputStackTopTxt);

        panels[3] = new JPanel();
        panels[3].add(outputStateLbl);
        panels[3].add(secondStateComboBox);

        panels[4] = new JPanel();
        panels[4].add(outputStackTopLbl);
        panels[4].add(outputStackTopTxt);


        panels[5] = new JPanel(new GridLayout(5,1));

        for(int i = 0; i < panels.length-1; i++)
            panels[5].add(panels[i], i);

        JOptionPane.showMessageDialog(null, panels[5], "Add transition", JOptionPane.QUESTION_MESSAGE);

        State inputState = (State) stateJComboBox.getSelectedItem();
        State outputState = (State) secondStateComboBox.getSelectedItem();

        inputState.addTransition(inputCharTxt.getText().charAt(0), inputStackTopTxt.getText().charAt(0), outputState, outputStackTopTxt.getText());

    }

    public static void main(String[] args) {
        new MainFrame();
    }
}
