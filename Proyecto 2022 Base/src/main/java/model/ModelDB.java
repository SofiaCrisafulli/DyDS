package model;

import java.sql.SQLException;

public class ModelDB implements ModelWithDBInterface {

    private GourmetCatalogInterfaceModel model;
    private DataBase dataBase;

    public ModelDB(GourmetCatalogInterfaceModel model) {
        this.model = model;
        dataBase = new DataBase();
    }

    public void saveInfo(String title, String extract) {
        boolean itWasDone = false;
        try {
            itWasDone = dataBase.saveInfo(title, extract);
        } catch (SQLException e) {
            for (Listener listener : model.getListeners())
                listener.notifyViewErrorSavingLocally(e);
        }
        for (Listener l : model.getListeners())
            l.didUpdateListener();
        if (itWasDone)
            for (Listener listener : model.getListeners())
                listener.notifyViewSaveCorrect();
    }

    public Object[] getTitleOfDatBase() {
        Object[] title = null;
        try {
            title = dataBase.getTitles().stream().sorted().toArray();
        } catch (SQLException e) {
            for (Listener listener : model.getListeners())
                listener.notifyViewErrorLoadDB(e);
        }
        return title;
    }

    public void onEventDeleteItem(String food) {
        boolean itWasDone = false;
        try {
            itWasDone = dataBase.deleteEntry(food);
            if (itWasDone) {
                for (Listener l : model.getListeners())
                    l.notifyViewDeleteCorrect();
            }
        } catch (SQLException e) {
        }
        if (!itWasDone)
            for (Listener l : model.getListeners())
                l.notifyViewErrorDeleting(new SQLException("Error"));
    }

    public String getContent(String string) throws SQLException {
        return dataBase.getContent(string);
    }
}
