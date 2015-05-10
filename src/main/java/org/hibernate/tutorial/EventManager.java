package org.hibernate.tutorial;

import org.hibernate.Session;
import org.hibernate.tutorial.domain.Event;
import org.hibernate.tutorial.domain.Person;
import org.hibernate.tutorial.util.HibernateUtil;

import java.util.Date;
import java.util.List;

public class EventManager {

    public static void main(String[] args) {
        EventManager mgr = new EventManager();

        switch (args[0]) {
            case "store":
                mgr.createAndStoreEvent("My Event", new Date());
                break;
            case "list":
                List<Event> events = mgr.listEvents();
                for (Event event : events) {
                    System.out.println(
                            "Event: " + event.getTitle() + " Time: " + event.getDate()
                    );
                }
                break;
            case "addpersontoevent":
                Long eventId = mgr.createAndStoreEvent("My Event", new Date());
                Long personId = mgr.createAndStorePerson("Foo", "Bar", 34);
                mgr.addPersonToEven(personId, eventId);
                System.out.println("Added person " + personId + " to event " + eventId);
                break;
            case "addemailtoperson":
                personId = mgr.createAndStorePerson("Yep", "Joe", 45);
                mgr.addEmailToPerson(personId, "joe@example.com");
                break;
        }

        HibernateUtil.getSessionFactory().close();
    }

    private List<Event> listEvents() {

        Session session = getSession();

        session.beginTransaction();

        List<Event> result = session.createQuery("from Event").list();
        session.getTransaction().commit();
        return result;
    }

    private Long createAndStoreEvent(String title, Date theDate) {

        Session session = getSession();

        session.beginTransaction();

        Event theEvent = new Event();
        theEvent.setTitle(title);
        theEvent.setDate(theDate);
        session.save(theEvent);

        session.getTransaction().commit();

        return theEvent.getId();
    }

    private Long createAndStorePerson(String firstName, String lastName, int age) {

        Session session = getSession();

        session.beginTransaction();

        Person aPerson = new Person();
        aPerson.setFirstname(firstName);
        aPerson.setLastname(lastName);
        aPerson.setAge(age);
        session.save(aPerson);

        session.getTransaction().commit();

        return aPerson.getId();
    }

    private Session getSession() {
        Session session = HibernateUtil.getSessionFactory().getCurrentSession();

        return session.isOpen() ? session : HibernateUtil.getSessionFactory().openSession();
    }

    private void addPersonToEven(Long personId, Long eventId) {

        Session session = getSession();

        session.beginTransaction();

        Person aPerson = (Person) session.load(Person.class, personId);
        Event anEvent = (Event) session.load(Event.class, eventId);

        aPerson.getEvents().add(anEvent);

        session.getTransaction().commit();
    }

    private void addEmailToPerson(Long personId, String emailAddress) {
        Session session = HibernateUtil.getSessionFactory().getCurrentSession();
        session.beginTransaction();

        Person aPerson = (Person) session.load(Person.class, personId);
        // adding to the emailAddress collection might trigger a lazy load of the collection
        aPerson.getEmailAddresses().add(emailAddress);

        session.getTransaction().commit();
    }
}
