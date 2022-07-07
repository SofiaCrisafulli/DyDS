package presenter;

import model.DataBase;
import view.GourmetCatalogView;

import java.sql.SQLException;


public class Main {

    private static GourmetCatalogView view;
    private static DataBase dataBase;

    public static void main(String[] args) {
        dataBase = new DataBase();
        view = new GourmetCatalogView();
        try {
            dataBase.loadDataBase();
        } catch (SQLException e) {
            view.showErrorLoadDataBase(e);
        }
        view.showView();
    }
}
