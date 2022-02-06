package com.github.pdasimulator.gui;

import com.github.pdasimulator.automaton.State;

import javax.swing.*;
import java.awt.*;

public class MainFrame extends JFrame {
    //panels
    private final JPanel buttonsPanel = new JPanel();
    private final JPanel centerPanel = new JPanel();
    private final JScrollPane transitionsPanel = new JScrollPane();
    private final JScrollPane statesPanel = new JScrollPane();
    private final JPanel bottomPanel = new JPanel();

    //components
    private final JButton addBtn = new JButton("Add");
    private final JButton modifyBtn = new JButton("Modify");
    private final JButton deleteBtn = new JButton("Delete");
    private final JButton runBtn = new JButton("Run");

    private final JList<State> statesList = new JList<>();
    private final JList<String> transitionsList = new JList<>();

    private final JLabel modeLbl = new JLabel("Accept mode: ");
    private final ButtonGroup rbGroup = new ButtonGroup();
    private final JRadioButton stackRb = new JRadioButton("Empty stack");
    private final JRadioButton finRb = new JRadioButton("Final state");

    private final JTextField inputTxt = new JTextField(10);

    private final JMenuBar menuBar = new JMenuBar();
    private final JMenu fileMenu = new JMenu("File");
    private final JMenuItem saveMenuItem = new JMenuItem("Save");
    private final JMenuItem loadMenuItem = new JMenuItem("Load");

    public MainFrame() {
        super("PDA simulator");

        setMenu();
        setButtonsPanel();
        setLists();
        setBottomPanel();
        
        this.setSize(new Dimension(500, 500));
        this.setLocationRelativeTo(null);
        this.setDefaultCloseOperation(EXIT_ON_CLOSE);
        this.setVisible(true);
    }

    private void setBottomPanel() {
    }

    private void setLists() {
    }

    private void setMenu() {
        this.fileMenu.add(saveMenuItem);
        this.fileMenu.add(loadMenuItem);
        this.menuBar.add(fileMenu);
        this.setJMenuBar(menuBar);
    }

    private void setButtonsPanel() {
        Container c = this.getContentPane();

        this.buttonsPanel.add(addBtn);
        this.buttonsPanel.add(modifyBtn);
        this.buttonsPanel.add(deleteBtn);

        c.add(this.buttonsPanel);
    }

    public static void main(String[] args) {
        new MainFrame();
    }
}
