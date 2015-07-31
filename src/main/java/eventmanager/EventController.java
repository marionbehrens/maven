package eventmanager;

import java.io.IOException;
import java.util.List;

import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class EventController {

	@Autowired
	IcalService icalService;

	@Autowired
	EventRepository eventRepository;

	@RequestMapping(value = "/")
	public void getRoot(HttpServletResponse response) throws Exception {
		try {
			response.sendRedirect("/events");
		} catch (IOException e) {
			throw new Exception(e.toString());
		}
	}

	@RequestMapping(value = "/events", produces = MediaType.APPLICATION_JSON_VALUE)
	public List<Event> getEventsAsJson() {
		return eventRepository.getEvents();
	}

	@RequestMapping(value = "/events/ical", produces = "text/calendar")
	public String getEventsAsIcal() throws Exception {
		List<Event> events = eventRepository.getEvents();
		return icalService.getCalendar(events).toString();
	}
}
