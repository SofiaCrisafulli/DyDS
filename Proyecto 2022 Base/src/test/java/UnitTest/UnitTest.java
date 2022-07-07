package UnitTest;

import model.DataBase;
import model.GourmetCatalogModel;
import model.HtmlConverter;
import model.ModelDB;
import org.junit.Before;
import presenter.GourmetCatalogPresenter;
import view.GourmetCatalogView;
import org.junit.Test;

import javax.swing.*;
import java.io.IOException;
import java.sql.SQLException;

import static org.mockito.Mockito.*;

public class UnitTest {

    private GourmetCatalogView view;
    private GourmetCatalogPresenter presenter;
    private GourmetCatalogModel model;
    private HtmlConverter html;
    private DataBase dataBase;
    private ModelDB modelDB;

    @Before
    public void initVariables() {
        view = mock(GourmetCatalogView.class);
        model = mock(GourmetCatalogModel.class);
        modelDB = mock(ModelDB.class);
        presenter = new GourmetCatalogPresenter(view);
        presenter.setModel(model);
        html = new HtmlConverter();
        dataBase = mock(DataBase.class);
        presenter.setDataBase(dataBase);
        presenter.setModelDB(modelDB);
    }

    @Test
    public void testSearch() throws IOException {
        when(view.getSeekPanelText()).thenReturn("Pizza");
        presenter.onEventSearch();
        verify(model, times(1)).doTheSearchInWikipedia("Pizza");
    }

    @Test
    public void testSaved(){
        presenter.setLastSearchedText("Birthday cake");
        presenter.setSelectedResultTitle("Cake");
        presenter.onEventSaveLocallyButton();
        verify(modelDB, times(1)).saveInfo("Cake", "Birthday cake");
    }

    @Test
    public void testDeleted(){
        String arreglo [] = new String[0];
        when(modelDB.getTitleOfDatBase()).thenReturn(arreglo);
        presenter.onEventDelete();
        verify(view, times(1)).setTextPanelStored("");
    }

    @Test
    public void testSearchFood() throws SQLException {
        when(view.getSelectedFood()).thenReturn("Pizza");
        when(modelDB.getContent("Pizza")).thenReturn("Pizza1");
        when(view.getStoredPanel()).thenReturn(new JTextPane());
        presenter.searchFoodBD();
        verify(modelDB, times(1)).getContent("Pizza");
    }

    @Test
    public void testSaveChanges() throws SQLException {
        JComboBox jComboBox = new JComboBox<>();
        jComboBox.addItem("Pizza");
        when(view.getStoredFoodsAndDrinks()).thenReturn(jComboBox);
        when(view.getArticleSaved()).thenReturn(new JTextPane());
        presenter.saveData();
        verify(dataBase, times(1)).saveInfo("Pizza", "");
    }
}
