package com.example.codingevents;

import com.example.codingevents.data.EventRepository;
import com.example.codingevents.models.Event;
import com.example.codingevents.models.EventType;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase.Replace;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.test.annotation.Rollback;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;

@DataJpaTest
@AutoConfigureTestDatabase(replace = Replace.NONE)
@Rollback(false)
public class EventRepositoryTest {

    @Autowired
    private TestEntityManager entityManager;
    @Autowired
    private EventRepository repo;

    private static Event event_ammar;
    private static Event event_anastasia;
    private static Event event_luke;
    private static Event event_jesus;
    private static Event event_obed;

    @BeforeAll
    public static void Setup() {
        event_ammar = new Event(
            "Ammar",
                "Strategio",
                "Technologist",
                "[Link to LinkedIn Account]",
                "Ammar is Ammar.",
                "ammar@ammar.com",
                EventType.SOCIAL,
                "Invalid image link."
        );

        event_anastasia = new Event(
                "Anastasia",
                "Strategio",
                "Technologist",
                "[Link to LinkedIn Account]",
                "Anastasia is Anastasia.",
                "anastasia@anastasia.com",
                EventType.CONFERENCE,
                "Invalid image link."
        );

        event_jesus = new Event(
                "Jesus",
                "Strategio",
                "Technologist",
                "[Link to LinkedIn Account]",
                "Jesus is Jesus.",
                "jesus@jesus.com",
                EventType.MEETUP,
                "Invalid image link."
        );

        event_luke = new Event(
                "Luke",
                "Strategio",
                "Technologist",
                "[Link to LinkedIn Account]",
                "Luke is Luke.",
                "luke@luke.com",
                EventType.WORKSHOP,
                "Invalid image link."
        );

        event_obed = new Event(
                "Obed",
                "Strategio",
                "Technologist",
                "[Link to LinkedIn Account]",
                "Obed is Obed.",
                "obed@obed.com",
                EventType.SOCIAL,
                "Invalid image link."
        );
    }

    @Test
    public void testCreateEvent() {
        // Create a completely new blank event
        Event newEvent = new Event("No Name");
        Event savedEvent = repo.save(newEvent);
        Event pluckedEvent = entityManager.find(Event.class, savedEvent.getId());

        // Assert that the blank event's email is the same as the email plucked from the repo.
        assertEquals(newEvent.getContactEmail(), pluckedEvent.getContactEmail());
    }

//    @Test
//    public void testReadEvent() {
//        // Save Event Luke to repo and get pluckedEvent back
//        Event savedEvent = repo.save(event_luke);
//        Event pluckedEvent = entityManager.find(Event.class, savedEvent.getId());
//        // Make all assertions.
//        assertEquals(savedEvent.getName(), "Luke");
//        assertEquals(savedEvent.getCompany(), "Strategio");
//        assertEquals(savedEvent.getTitle(), "Technologist");
//        assertEquals(savedEvent.getLinkedin(), "[Link to LinkedIn Account]");
//        assertEquals(savedEvent.getDescription(), "Luke is Luke.");
//        assertEquals(savedEvent.getContactEmail(), "luke@luke.com");
//    }

    @Test
    public void testCountEvents() {
        // Clear the repo
        repo.deleteAll();
        // Assert that repo is empty
        assertEquals(repo.count(), 0);
        // Add Anastasia and Ammar repo
        repo.save(event_anastasia);
        repo.save(event_ammar);
        // Assert that repo count equals 2
        assertEquals(repo.count(), 2);
        // Add Jesus, Luke, and Obed to repo
        repo.save(event_jesus);
        repo.save(event_luke);
        repo.save(event_obed);
        // Assert that repo count equals 5
        assertEquals(repo.count(), 5);
        // Remove Luke from repo
        repo.delete(event_luke);
        // Assert that repo count equals 4
        assertEquals(repo.count(), 4);
    }

    @Test
    public void testDeleteEvent() {
        // Remove Ammar from the repo
        repo.delete(event_ammar);
        // Assert that Ammar is not present in the repo and therefore cannot be plucked
        Event pluckedEvent = entityManager.find(Event.class, event_ammar.getId());
        assertNull(pluckedEvent);
    }
}