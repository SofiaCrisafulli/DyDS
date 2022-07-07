package dyds.act2.tests.utils;

import dyds.act2.parte1.ServiceProvider;

import java.net.URI;

public class StubbedServiceProvider implements ServiceProvider {
    @Override
    public String resolveCall(URI uri) {
        //Lets keep the results as simple as possible, for testing purposes ofc
        //(Wikiapi JSONs results would be more complex for sure)
        if(uri.toString().equals("https://en.wikipedia.org/w/api.php?action=query&format=json&list=search&srsearch=Baldurs%20Gate%203%20articletopic%3A%22video-games%22")) {
            return "{ \"search\" : [\"page1\", \"page2\", \"page3\"] }";
        } else {
            return "{ \"search\" : [] }";
        }
    }
}
