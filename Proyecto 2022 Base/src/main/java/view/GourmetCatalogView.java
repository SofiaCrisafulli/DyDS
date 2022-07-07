package view;

import model.DataBase;
import presenter.GourmetCatalogInterfacePresenter;
import presenter.GourmetCatalogPresenter;

import javax.swing.*;
import java.awt.*;
import java.io.IOException;
import java.sql.SQLException;

public class GourmetCatalogView implements GourmetCatalogInterfaceView {

    private JTextField seekPanel;
    private JPanel contentPane;
    private JTextPane searchPane;
    private JButton saveLocallyButton;
    private JTabbedPane window;
    private JPanel searchPanel;
    private JPanel storagePanel;
    private JComboBox<Object> storedFoodsAndDrinks;
    private JTextPane articleSaved;
    private JButton searchButton;
    private JCheckBox showCompletePage;
    private JPopupMenu searchOptionsMenu;
    private JOptionPane errorMessage;
    private JOptionPane informationMessage;

    private static GourmetCatalogInterfacePresenter presenter;
    private DataBase dataBase;

    public GourmetCatalogView() {
        presenter = new GourmetCatalogPresenter(this);
        dataBase = new DataBase();
        try {
            storedFoodsAndDrinks.setModel(new DefaultComboBoxModel<Object>(dataBase.getTitles().stream().sorted().toArray()));
        } catch (SQLException e) {
            e.printStackTrace();
        }
        searchPane.setContentType("text/html");
        articleSaved.setContentType("text/html");
        JPopupMenu storedInfoPopup = new JPopupMenu();
        initButtons(storedInfoPopup);
        JMenuItem saveItem = configuringSaveChangesButtom();
        storedInfoPopup.add(saveItem);
        articleSaved.setComponentPopupMenu(storedInfoPopup);
    }

    private JMenuItem configuringSaveChangesButtom() {
        JMenuItem saveItem = new JMenuItem("Save Changes!");
        saveItem.addActionListener(actionEvent -> {
                if (storedFoodsAndDrinks.getSelectedItem() != null) {
                    try {
                        presenter.saveData();
                    } catch (SQLException e) {
                        e.printStackTrace();
                    }
                    showSaveCorrect();
                } else
                    showErrorSaving(new SQLException("Error"));
        });
        return saveItem;
    }

    private void initButtons(JPopupMenu storedInfoPopup) {
        seekPanel.addActionListener(e -> presenter.onEventSearch());
        saveLocallyButton.addActionListener(actionEvent -> presenter.onEventSaveLocallyButton());
        storedFoodsAndDrinks.addActionListener(e -> presenter.searchFoodBD());
        searchButton.addActionListener(e -> presenter.onEventSearch());
        showCompletePage.addActionListener(actionEvent -> presenter.onEventCompletePage());

        JMenuItem deleteItem = new JMenuItem("Delete!");
        deleteItem.addActionListener(actionEvent -> presenter.onEventDelete());
        storedInfoPopup.add(deleteItem);
    }

    public void showView() {
        JFrame frame = new JFrame("Gourmet Catalog");
        frame.setContentPane(new GourmetCatalogView().contentPane);
        frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        frame.pack();
        setFrameIcon(frame);
        frame.setVisible(true);
    }

    private void setFrameIcon(Frame frame) {
        frame.setIconImage(Toolkit.getDefaultToolkit().getImage(getClass().getResource("/restaurante.png")));
    }

    public String getSelectedFood() {
        return storedFoodsAndDrinks.getSelectedItem().toString();
    }

    public int getIndex() {
        return storedFoodsAndDrinks.getSelectedIndex();
    }

    @Override
    public void getStoredFoodAndDrinksSetModel(DefaultComboBoxModel<Object> model) {
        storedFoodsAndDrinks.setModel(model);
    }

    public void setTextPanelStored(String string) {
        articleSaved.setText(string);
    }

    @Override
    public JTextPane getArticleSaved() {
        return articleSaved;
    }

    public void showErrorLoadDataBase(SQLException exception) {
        JOptionPane.showMessageDialog(errorMessage, "Error loading database");
    }

    public void showErrorSaving(SQLException exception) {
        JOptionPane.showMessageDialog(errorMessage, "Error saving data");
    }

    public void showErrorGetContent(SQLException exception) {
        JOptionPane.showMessageDialog(errorMessage, "Error getting content");
    }


    public void showErrorSearching(IOException exception) {
        JOptionPane.showMessageDialog(errorMessage, "Error doing the searching in Wikipedia");
    }

    public void showErrorDeleting(SQLException exception) {
        JOptionPane.showMessageDialog(errorMessage, "Error doing the deleting");
    }

    @Override
    public void showDeleteCorrect() {
        JOptionPane.showMessageDialog(informationMessage, "Delete correctly");
    }

    @Override
    public void showSaveCorrect() {
        JOptionPane.showMessageDialog(informationMessage, "Save correctly");
    }

    public void initListeners() {
        seekPanel.addActionListener(e -> presenter.onEventSearch());
    }

    public boolean completePageIsSelected() {
        return showCompletePage.isSelected();
    }

    public JTextField getSeekPanel() {
        return seekPanel;
    }

    public String getSeekPanelText() {
        return seekPanel.getText();
    }

    public JTextPane getBasePanel() {
        return searchPane;
    }

    public JPanel getSearchPanel() {
        return searchPanel;
    }

    public JComboBox<Object> getStoredFoodsAndDrinks() {
        return storedFoodsAndDrinks;
    }

    public JTextPane getStoredPanel() {
        return articleSaved;
    }
}