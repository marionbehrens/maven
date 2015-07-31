package eventmanager;

import java.time.LocalDateTime;
import java.time.Month;
import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Component;

@Component
public class EventRepository {

	public List<Event> getEvents() {
		Location location1 = new Location("Firma JavaCom", "Karlsplatz 5", "München");
		LocalDateTime start1 = LocalDateTime.of(2015, Month.AUGUST, 21, 19, 0);
		LocalDateTime end1 = LocalDateTime.of(2015, Month.AUGUST, 21, 21, 0);
		Event event1 = new Event("Java User Group", "Vortrag über Spring Boob", start1, end1, location1);

		Location location2 = new Location("LMU München", "Uniplatz 4", "München");
		LocalDateTime start2 = LocalDateTime.of(2015, Month.SEPTEMBER, 10, 19, 0);
		LocalDateTime end2 = LocalDateTime.of(2015, Month.SEPTEMBER, 10, 21, 0);
		Event event2 = new Event("Python User Group", "Vortrag über REST APIs", start2, end2, location2);

		List<Event> events = new ArrayList<Event>();
		events.add(event1);
		events.add(event2);
		return events;
	}

}
