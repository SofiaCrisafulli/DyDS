package model;

import com.google.gson.JsonArray;
import com.google.gson.JsonElement;

import java.io.IOException;
import java.util.ArrayList;

public interface GourmetCatalogInterfaceModel {

    public void doTheSearchInWikipedia(String selectedFood) throws IOException;

    public void addListener(Listener listener);

    public JsonArray getQuery();

    public JsonElement getSearchResultContent();

    public void getPageIntroduction(SearchResult searchResult);

    public void setApi(API api);

    public  ArrayList<Listener> getListeners();
}
