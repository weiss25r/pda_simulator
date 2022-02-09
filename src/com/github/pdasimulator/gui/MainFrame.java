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
    }

    public static void main(String[] args) {
        new MainFrame();
    }
}
