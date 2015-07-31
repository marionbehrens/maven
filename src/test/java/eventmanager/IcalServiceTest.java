package eventmanager;

import static org.junit.Assert.assertThat;
import static org.hamcrest.Matchers.*;

import java.time.LocalDateTime;
import java.time.Month;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

import net.fortuna.ical4j.model.Calendar;
import net.fortuna.ical4j.model.component.VEvent;

import org.junit.Test;

public class IcalServiceTest {
	@Test
	public void getCalendar_with_empty_list () throws Exception {
		IcalService service = new IcalService();
		Calendar calendar = service.getCalendar(new ArrayList<Event>());
		
		assertThat(calendar.getComponents().size(), is(0));
	}
	
	@Test
	public void getCalendar_with_one_event_without_location () throws Exception {
		IcalService service = new IcalService();
		LocalDateTime start = LocalDateTime.of(2015, Month.JULY, 20, 19, 0);
		LocalDateTime end = LocalDateTime.of(2015, Month.JULY, 20, 21, 0);
		Event event = new Event("title", "description", start, end, null);
		List<Event> events = Arrays.asList(event);
		Calendar calendar = service.getCalendar(events);
		
		assertThat(calendar.getComponents().size(), is(1));
		VEvent vevent = (VEvent) calendar.getComponents().get(0);
		assertThat(vevent.getSummary().getValue(), equalTo("title"));
		assertThat(vevent.getLocation(), is(nullValue()));
		long startDate = Date.from(start.atZone(ZoneId.of("Europe/Berlin")).toInstant()).getTime();
		assertThat(vevent.getStartDate().getDate().getTime(), equalTo(startDate));
	}
	
	@Test
	public void getCalendar_with_one_event_with_location () throws Exception {
		IcalService service = new IcalService();
		Location location = new Location("Testlocation", "TestStreet", "TestCity");
		LocalDateTime start = LocalDateTime.of(2015, Month.JULY, 20, 19, 0);
		LocalDateTime end = LocalDateTime.of(2015, Month.JULY, 20, 21, 0);
		Event event = new Event("title", "description", start, end, location);
		List<Event> events = Arrays.asList(event);
		Calendar calendar = service.getCalendar(events);
		
		assertThat(calendar.getComponents().size(), is(1));
		VEvent vevent = (VEvent) calendar.getComponents().get(0);
		assertThat(vevent.getSummary().getValue(), equalTo("title"));
		assertThat(vevent.getLocation().getValue(), equalTo("Testlocation TestCity"));
	}

}
