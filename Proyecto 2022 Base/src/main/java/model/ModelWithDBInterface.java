package model;

import java.sql.SQLException;

public interface ModelWithDBInterface {

    public void saveInfo(String title, String content);

    public Object[] getTitleOfDatBase();

    public void onEventDeleteItem(String selectedFood);

    public String getContent(String string) throws SQLException;
}
