package eventmanager;

import static org.hamcrest.Matchers.containsString;
import static org.junit.Assert.assertThat;

import org.junit.Rule;
import org.junit.Test;
import org.springframework.boot.test.OutputCapture;


public class ApplicationIT {
	
	@Rule
	public OutputCapture capture = new OutputCapture();

	@Test
	public void defaultApplicationContext() throws Exception {
		Application.main(new String[]{});
		assertThat(capture.toString(), containsString("Started Application"));
		
	}

}
