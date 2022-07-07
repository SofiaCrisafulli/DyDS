package presenter;

import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import model.*;
import view.GourmetCatalogInterfaceView;

import javax.swing.*;
import java.awt.*;
import java.io.IOException;
import java.sql.SQLException;

import static model.HtmlConverter.textToHtml;


public class GourmetCatalogPresenter implements GourmetCatalogInterfacePresenter {

    private String selectedResultTitle = null;
    private String lastSearchedText = "";
    private JPopupMenu searchOptionsMenu;
    private String searchResultTitle;
    private String searchResultPageId;
    private String searchResultSnippet;
    private SearchResult selectedSearchResult;

    private GourmetCatalogInterfaceView view;
    private GourmetCatalogInterfaceModel model;
    private DataBase dataBase;
    private ModelWithDBInterface modelDB;


    public GourmetCatalogPresenter(GourmetCatalogInterfaceView view) {
        this.view = view;
        model = new GourmetCatalogModel();
        onEventSaveLocallyButton();
        initListeners();
        dataBase = new DataBase();
        modelDB = new ModelDB(model);
    }

    public void onEventSaveLocallyButton() {
        if (lastSearchedText != "")
            modelDB.saveInfo(selectedResultTitle.replace("'", "`"), lastSearchedText);
    }

    public void initListeners() {
        model.addListener(new Listener() {
            @Override
            public void didUpdateListener() {
                setTitleDataBase();
            }

            @Override
            public void fetchPage() {
                JsonElement searchResultContent = model.getSearchResultContent();
                if (searchResultContent == null) {
                    lastSearchedText = "No Results";
                } else {
                    lastSearchedText = "<h1>" + selectedSearchResult.title + "</h1>";
                    selectedResultTitle = selectedSearchResult.title;
                    lastSearchedText += searchResultContent.getAsString().replace("\\n", "\n");
                    lastSearchedText = textToHtml(lastSearchedText);
                }
                view.getBasePanel().setText(lastSearchedText);
                view.getBasePanel().setCaretPosition(0);
                setWatingStatus();
            }

            @Override
            public void finishSearch() {
                JPopupMenu searchOptionsMenu = new JPopupMenu("Search Results");
                listOfFoodAndDrinks(searchOptionsMenu);
                searchOptionsMenu.show(view.getSeekPanel(), view.getSeekPanel().getX(), view.getSeekPanel().getY());
            }

            @Override
            public void notifyViewSaveCorrect() {
                view.showSaveCorrect();
            }

            @Override
            public void notifyViewDeleteCorrect() {
                view.showDeleteCorrect();
            }

            @Override
            public void notifyViewErrorLoadDB(SQLException exception) {
                view.showErrorLoadDataBase(exception);
            }

            @Override
            public void notifyViewErrorDeleting(SQLException exception) {
                view.showErrorDeleting(exception);
            }

            @Override
            public void notifyViewErrorSavingLocally(SQLException exception) {
                view.showErrorSaving(exception);
            }
        });
    }

    private void setTitleDataBase() {
        view.getStoredFoodsAndDrinks().setModel(new DefaultComboBoxModel<Object>(modelDB.getTitleOfDatBase()));
    }

    private void listOfFoodAndDrinks(JPopupMenu searchOptionsMenu) {
        for (JsonElement jsonElement : model.getQuery()) {
            JsonObject jsonObject = jsonElement.getAsJsonObject();
            searchResultTitle = jsonObject.get("title").getAsString();
            searchResultPageId = jsonObject.get("pageid").getAsString();
            searchResultSnippet = jsonObject.get("snippet").getAsString();
            SearchResult searchResult = new SearchResult(searchResultTitle, searchResultPageId, searchResultSnippet);
            searchOptionsMenu.add(searchResult);
            searchResult.addActionListener(actionEvent -> {
                setWorkingStatus();
                selectedSearchResult = searchResult;
                model.getPageIntroduction(searchResult);
            });
        }
    }

    public void onEventCompletePage() {
        if (view.completePageIsSelected())
            model.setApi(new APIPageComplete());
        else
            model.setApi(new APIIntroduction());
    }

    public void onEventDelete() {
        int indexSelected = view.getIndex();
        if (indexSelected > -1) {
            try {
                modelDB.onEventDeleteItem(view.getSelectedFood());
                uploadFoodList();
                view.setTextPanelStored("");
            } catch (SQLException e) {
            }
        }
    }

    private void uploadFoodList() throws SQLException {
        view.getStoredFoodAndDrinksSetModel(new DefaultComboBoxModel<Object>(modelDB.getTitleOfDatBase()));
    }

    public void searchFoodBD() {
        String string = view.getSelectedFood();
        try {
            view.getStoredPanel().setText(textToHtml(modelDB.getContent(string)));
        } catch (SQLException e) {
            view.showErrorGetContent(e);
        }
    }

    public void onEventSearch() {
        try {
            model.doTheSearchInWikipedia(view.getSeekPanelText());
        } catch (IOException e) {
            view.showErrorSearching(e);
        }
    }

    private void setWorkingStatus() {
        for (Component c : view.getSearchPanel().getComponents())
            c.setEnabled(false);
        view.getBasePanel().setEnabled(false);
    }

    private void setWatingStatus() {
        for (Component c : view.getSearchPanel().getComponents()) c.setEnabled(true);
        view.getBasePanel().setEnabled(true);
    }

    public void saveData() throws SQLException {
        dataBase.saveInfo(view.getStoredFoodsAndDrinks().getSelectedItem().toString().replace("'", "`"), view.getArticleSaved().getText());
    }

    public void setDataBase(DataBase dataBase){
        this.dataBase = dataBase;
    }

    public GourmetCatalogInterfaceModel getModel() {
        return model;
    }

    public void setModel(GourmetCatalogInterfaceModel model) {
        this.model = model;
    }

    public void setLastSearchedText(String lastSearchedText) {
        this.lastSearchedText = lastSearchedText;
    }

    public void setSelectedResultTitle(String selectedResultTitle) {
        this.selectedResultTitle = selectedResultTitle;
    }

    public void setModelDB(ModelWithDBInterface modelDB) {
        this.modelDB = modelDB;
    }
}