package view;

import javax.swing.*;
import java.io.IOException;
import java.sql.SQLException;

public interface GourmetCatalogInterfaceView {

    public JTextField getSeekPanel();

    public JPanel getSearchPanel();

    public JTextPane getBasePanel();

    public JComboBox<Object> getStoredFoodsAndDrinks();

    public JTextPane getStoredPanel();

    public void showErrorLoadDataBase(SQLException exception);

    public void showErrorSaving(SQLException exception);

    public String getSeekPanelText();

    public boolean completePageIsSelected();

    public void showErrorSearching(IOException exception);

    public void showErrorDeleting(SQLException exception);

    public void showDeleteCorrect();

    public void showSaveCorrect();

    public void showErrorGetContent(SQLException e);

    public String getSelectedFood();

    public int getIndex();

    public void getStoredFoodAndDrinksSetModel(DefaultComboBoxModel<Object> objectDefaultComboBoxModel);

    public void setTextPanelStored(String s);

    public JTextPane getArticleSaved();
}