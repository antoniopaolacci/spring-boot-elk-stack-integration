package it.app.example.test;

import it.app.example.Application;

import org.apache.http.HttpStatus;
import org.hamcrest.Matchers;
import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringRunner;

import com.jayway.restassured.RestAssured;
import com.jayway.restassured.http.ContentType;

@RunWith(SpringRunner.class)
@ContextConfiguration(classes = { Application.class })
@SpringBootTest(webEnvironment = WebEnvironment.RANDOM_PORT)
public class ApplicationResourceTest {

	private static final String API_PATH = "/api";
	private static final String MSG_TEMPLATE = "Hello %s. Version %s - passed in %s";

	@LocalServerPort
	private int port;

	@Before
	public void setup() {
		RestAssured.port = this.port;
	}
	
	@Ignore
	@Test
	public void shouldRetrieveNameVersion1InURL() {
		
		String name = "world";
		
		RestAssured.
			given().
				accept(ContentType.JSON).
			when().
				get(String.format("%s/v1/hello/{name}", API_PATH), name).
			then().
				statusCode(HttpStatus.SC_OK).
				contentType(ContentType.JSON).
				body("msg", Matchers.equalTo(String.format(MSG_TEMPLATE, name, 1, "URL")));
	}

	@Ignore
	@Test
	public void shouldRetrieveNameVersion1InAcceptHeader() {
		
		String name = "world";
		
		RestAssured.
			given().
				accept("application/vnd.asimio-v1+json").
			when().
				get(String.format("%s/hello/{name}", API_PATH), name).
			then().
				statusCode(HttpStatus.SC_OK).
				contentType("application/vnd.asimio-v1+json").
				body("msg", Matchers.equalTo(String.format(MSG_TEMPLATE, name, 1, "Accept Header")));
	}

	@Test
	public void retrieveShouldResultIn404Version1InURL() {
		
		String numeric = "1";
		
		RestAssured.
			when().
				get(String.format("%s/v1/test/{id}", API_PATH), numeric).
			then().
				statusCode(HttpStatus.SC_OK);
	
	}
	
    @Ignore
	@Test
	public void retrieveShouldResultIn404Version1InAcceptHeader() {
		
		String name = "404";
		
		RestAssured.
			given().
				accept("application/vnd.asimio-v1+json").
			when().
				get(String.format("%s/hello/{name}", API_PATH), name).
			then().
				statusCode(HttpStatus.SC_NOT_FOUND);
		
	}
	
}