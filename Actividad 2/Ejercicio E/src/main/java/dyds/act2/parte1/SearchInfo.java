package dyds.act2.parte1;

public class SearchInfo {

    private String textToSearch;
    private String topic;
    private String wikiToSearch;

    public SearchInfo() {
    }

    public String getTextToSearch() {
        return textToSearch;
    }

    public String getTopic() {
        return topic;
    }

    public String getWikiToSearch() {
        return wikiToSearch;
    }

    public void setTextToSearch(String textToSearch){
        this.textToSearch = textToSearch;
    }

    public void setTopic(String topic){
        this.topic = topic;
    }

    public void setWikiToSearch(String wikiToSearch){
        this.wikiToSearch = wikiToSearch;
    }
}
