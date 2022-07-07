package model;

import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import retrofit2.Response;

import javax.swing.*;
import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Map;
import java.util.Set;

import static model.HtmlConverter.textToHtml;

public class GourmetCatalogModel implements GourmetCatalogInterfaceModel {

    private JsonArray query;
    private API api;
    private Gson gson;
    private ArrayList<Listener> listeners;
    private JsonElement searchResultContent;


    public GourmetCatalogModel() {
        api = new APIIntroduction();
        gson = new Gson();
        listeners = new ArrayList<Listener>();
    }

    public void doTheSearchInWikipedia(String selectedFood) throws IOException {
        Response<String> callForSearchResponse = api.getSearchAPI().searchForTerm(selectedFood + " articletopic:\"food-and-drink\"").execute();
        JsonObject jsonObject = gson.fromJson(callForSearchResponse.body(), JsonObject.class);
        JsonObject query = jsonObject.get("query").getAsJsonObject();
        this.query = query.get("search").getAsJsonArray();
        for (Listener l : listeners)
            l.finishSearch();
    }

    public void getPageIntroduction(SearchResult searchResult) {
        Response<String> callForPageResponse = api.getPageAPI(searchResult.pageID);
        JsonObject jsonObject = gson.fromJson(callForPageResponse.body(), JsonObject.class);
        JsonObject query2 = jsonObject.get("query").getAsJsonObject();
        JsonObject pages = query2.get("pages").getAsJsonObject();
        Set<Map.Entry<String, JsonElement>> pagesSet = pages.entrySet();
        Map.Entry<String, JsonElement> first = pagesSet.iterator().next();
        JsonObject page = first.getValue().getAsJsonObject();
        searchResultContent = page.get("extract");
        for (Listener l : listeners)
            l.fetchPage();
    }

    public void addListener(Listener listener) {
        listeners.add(listener);
    }

    public JsonArray getQuery() {
        return query;
    }

    public JsonElement getSearchResultContent() {
        return searchResultContent;
    }

    public void setApi(API api) {
        this.api = api;
    }

    public ArrayList<Listener> getListeners() {
        return listeners;
    }
}