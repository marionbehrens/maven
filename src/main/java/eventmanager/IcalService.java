package eventmanager;

import java.net.SocketException;
import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Date;
import java.util.List;

import net.fortuna.ical4j.model.Calendar;
import net.fortuna.ical4j.model.DateTime;
import net.fortuna.ical4j.model.TimeZone;
import net.fortuna.ical4j.model.TimeZoneRegistry;
import net.fortuna.ical4j.model.TimeZoneRegistryFactory;
import net.fortuna.ical4j.model.component.VEvent;
import net.fortuna.ical4j.model.component.VTimeZone;
import net.fortuna.ical4j.model.property.CalScale;
import net.fortuna.ical4j.model.property.Location;
import net.fortuna.ical4j.model.property.ProdId;
import net.fortuna.ical4j.model.property.Uid;
import net.fortuna.ical4j.model.property.Version;
import net.fortuna.ical4j.util.UidGenerator;

import org.springframework.stereotype.Service;

@Service
public class IcalService {

	public Calendar getCalendar(List<Event> events) throws Exception {
		Calendar calendar = new Calendar();
		calendar.getProperties().add(new ProdId("-//Informatica Feminale//eventmanager//EN"));
		calendar.getProperties().add(Version.VERSION_2_0);
		calendar.getProperties().add(CalScale.GREGORIAN);
		try {
			for (Event event : events) {
				VEvent vevent = this.getIcalMeeting(getTimeZone(), event);

				calendar.getComponents().add(vevent);
			}
		} catch (SocketException e) {
			throw new Exception(e.toString());
		}
		return calendar;
	}

	private VEvent getIcalMeeting(TimeZone timezone, Event event) throws SocketException {
		VTimeZone tz = timezone.getVTimeZone();
		DateTime start = getDateTime(event.getStartDateTime());
		DateTime end = getDateTime(event.getEndDateTime());
		VEvent meeting = new VEvent(start, end, event.getTitle());
		meeting.getProperties().add(tz.getTimeZoneId());

		UidGenerator ug = new UidGenerator("uidGen");
		Uid uid = ug.generateUid();
		meeting.getProperties().add(uid);
		eventmanager.Location location = event.getLocation();
		if (location != null) {
			net.fortuna.ical4j.model.property.Location icalLocation = new Location(
					location.getName() + " " + location.getCity());
			meeting.getProperties().add(icalLocation);
		}
		return meeting;
	}

	private DateTime getDateTime(LocalDateTime ldt) {
		Instant instant = ldt.atZone(ZoneId.of("Europe/Berlin")).toInstant();
		Date date = Date.from(instant);
		DateTime icaldateto = new DateTime(date.getTime());
		return icaldateto;
	}

	private TimeZone getTimeZone() {
		TimeZoneRegistry registry = TimeZoneRegistryFactory.getInstance().createRegistry();
		TimeZone timezone = registry.getTimeZone("Europe/Berlin");
		return timezone;
	}

}
