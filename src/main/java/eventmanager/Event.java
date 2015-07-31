package eventmanager;

import java.time.LocalDateTime;

import doclets.GlossaryEntry;

/**
 * A one-time or regular meetup with talks about current tech topics.
 *
 */
@GlossaryEntry
public class Event {

	/**
	 * A meaningful title what this event is about, e.g. name of a User Group
	 */
	private String title;

	/**
	 * A description of the program. eg. a listing of the talks planned for the
	 * event
	 */
	private String description;

	/**
	 * start date and time (in local time without timezone)
	 */
	private LocalDateTime startDateTime;

	/**
	 * the anticipated end date and time of the event (in local time without
	 * timezone)
	 */
	private LocalDateTime endDateTime;

	/**
	 * Where the meetup will take place
	 */
	private Location location;

	public Event(String title, String description, LocalDateTime startDateTime, LocalDateTime endDateTime,
			Location location) {
		this.title = title;
		this.description = description;
		this.startDateTime = startDateTime;
		this.endDateTime = endDateTime;
		this.location = location;
	}

	public LocalDateTime getEndDateTime() {
		return endDateTime;
	}

	public String getTitle() {
		return title;
	}

	public String getDescription() {
		return description;
	}

	public LocalDateTime getStartDateTime() {
		return startDateTime;
	}

	public Location getLocation() {
		return location;
	}

}
