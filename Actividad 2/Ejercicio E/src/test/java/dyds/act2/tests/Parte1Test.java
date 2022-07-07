package dyds.act2.tests;

import dyds.act2.tests.utils.StubbedServiceProvider;
import org.junit.Before;
import org.junit.Test;

import dyds.act2.parte1.ServiceProvider;
import dyds.act2.parte1.SearchInfo;
import dyds.act2.parte1.SearchLogic;

import static org.junit.Assert.*;

public class Parte1Test {

	ServiceProvider serviceProvider;

	@Before
	public void setUp() throws Exception {
		//Using the stubbed version of the ServiceProvider, this is helpfull for testing stuff
		serviceProvider =  new StubbedServiceProvider();
	}

	@Test
	public void testBien() {
		// Arrange.
		SearchLogic searchLogic = new SearchLogic(serviceProvider);
		SearchInfo searchInfoOk =  new SearchInfo();
		searchInfoOk.setTextToSearch("Baldurs Gate 3");
		searchInfoOk.setTopic("video-games");
		searchInfoOk.setWikiToSearch("en.wikipedia.org");
		// Act.
		String[] results = searchLogic.search(searchInfoOk);
		
		// Assert.
		assertEquals(3, results.length);
	}

	@Test
	public void testMalSearchInfo() {
		// Arrange.
		SearchLogic searchLogic = new SearchLogic(serviceProvider);
		SearchInfo searchInfoNotOk =  new SearchInfo();
		searchInfoNotOk.setTextToSearch("Batman");
		searchInfoNotOk.setTopic("biology");
		searchInfoNotOk.setWikiToSearch( "en.wikipedia.org");

		// Act.
		String[] results = searchLogic.search(searchInfoNotOk);

		// Assert.
		assertEquals(0, results.length);
	}

	@Test
	public void testMalServiceURL() {
		// Arrange.
		SearchLogic searchLogic = new SearchLogic(serviceProvider);
		SearchInfo searchInfoBadURL =  new SearchInfo();
		searchInfoBadURL.setTextToSearch("Messi");
		searchInfoBadURL.setTopic("Football");
		searchInfoBadURL.setWikiToSearch("www.wikinada.com");
		// Act.
		String[] results = searchLogic.search(searchInfoBadURL);

		// Assert.
		assertEquals(0, results.length);
	}
}
