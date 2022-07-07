package presenter;

import model.GourmetCatalogInterfaceModel;

import java.sql.SQLException;

public interface GourmetCatalogInterfacePresenter {

    public void onEventSaveLocallyButton();

    public void onEventSearch();

    public GourmetCatalogInterfaceModel getModel();

    public void onEventCompletePage();

    public void onEventDelete();

    public void searchFoodBD();

    public void saveData() throws SQLException;
}
