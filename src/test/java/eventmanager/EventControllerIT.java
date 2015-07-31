package eventmanager;

import static org.junit.Assert.assertThat;
import static org.hamcrest.Matchers.*;

import java.net.URL;
import java.util.Arrays;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.IntegrationTest;
import org.springframework.boot.test.SpringApplicationConfiguration;
import org.springframework.boot.test.TestRestTemplate;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.web.client.RestTemplate;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = Application.class)
@WebAppConfiguration
@IntegrationTest({"server.port=0"})
public class EventControllerIT {

    @Value("${local.server.port}")
    private int port;

	private URL base;
	private RestTemplate template;

	@Before
	public void setUp() throws Exception {
		this.base = new URL("http://localhost:" + port + "/");
		template = new TestRestTemplate();
	}

	@Test
	public void getRoot() throws Exception {
		ResponseEntity<String> response = template.getForEntity(base.toString(), String.class);
		assertThat(response.getStatusCode(), is(HttpStatus.OK));
		assertThat(response.getBody(), containsString("Java User Group"));
		assertThat(response.getBody(), containsString("Python User Group"));
	}
	
	@Test
	public void getAllEventsAsJson() throws Exception {
		ResponseEntity<String> response = template.getForEntity(base.toString() + "/events", String.class);
		assertThat(response.getStatusCode(), is(HttpStatus.OK));
		assertThat(response.getBody(), containsString("Java User Group"));
		assertThat(response.getBody(), containsString("Python User Group"));
	}
	
	@Test
	public void getAllEventsAsIcal() throws Exception {
		RestTemplate restTemplate = new RestTemplate();
		HttpHeaders headers = new HttpHeaders();
		headers.setAccept(Arrays.asList(MediaType.parseMediaType("text/calendar")));
		HttpEntity<String> entity = new HttpEntity<String>("parameters", headers);
		ResponseEntity<String> response = restTemplate.exchange(base.toString() + "events/ical", HttpMethod.GET, entity, String.class);
				
		assertThat(response.getStatusCode(), is(HttpStatus.OK));
		assertThat(response.getBody(), containsString("CAL"));
		assertThat(response.getBody(), containsString("Java User Group"));
		assertThat(response.getBody(), containsString("Python User Group"));
	}
}
