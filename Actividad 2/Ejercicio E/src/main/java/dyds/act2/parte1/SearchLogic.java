package dyds.act2.parte1;

import com.google.gson.Gson;

import java.net.URI;
import java.net.URISyntaxException;

public class SearchLogic {

    private ServiceProvider serviceProvider;

    public SearchLogic(ServiceProvider serviceProvider) {
        this.serviceProvider = serviceProvider;
    }


    public String[] search(SearchInfo searchInfo) {
        String[] returnResult = new String[0];

        String linkWebPage = "https://" + searchInfo.getWikiToSearch() + "/w/api.php?" + "action=query&format=json&list=linkWebPage" + "&srsearch=" + searchInfo.getTextToSearch();

        if (searchInfo.getTopic() != null && !searchInfo.getTopic().equals(""))
            linkWebPage += " articletopic%3A\"" + searchInfo.getTopic() + "\"";

        linkWebPage = replaceSpaces(linkWebPage);
        linkWebPage = replaceQuotationMarks(linkWebPage);

        returnResult = jsonFormat(returnResult, linkWebPage);
        return returnResult;
    }

    private String[] jsonFormat(String[] result, String search) {
        String json = null;
        try {
            json = serviceProvider.resolveCall(new URI(search));
        } catch (URISyntaxException e) {
            e.printStackTrace();
        }
        Search addUserResult = null;
        if (json != null) {
            Gson gson = new Gson();
            addUserResult = gson.fromJson(json, Search.class);
            result = addUserResult.getSearch();
        }
        return result;
    }

    private String replaceQuotationMarks(String search) {
        return search.replace("\"", "%22");
    }

    private String replaceSpaces(String search) {
        return search.replace(" ", "%20");
    }
}
