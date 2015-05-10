package org.hibernate.tutorial;

import org.hibernate.Session;
import org.hibernate.tutorial.domain.Event;
import org.hibernate.tutorial.util.HibernateUtil;

import java.util.Date;
import java.util.List;

public class EventManager {

    private final Session session = getSession();

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
        }

        HibernateUtil.getSessionFactory().close();
    }

    private List listEvents() {

        session.beginTransaction();

        List result = session.createQuery("from Event").list();
        session.getTransaction().commit();
        return result;
    }

    private void createAndStoreEvent(String title, Date theDate) {

        session.beginTransaction();

        Event theEvent = new Event();
        theEvent.setTitle(title);
        theEvent.setDate(theDate);
        session.save(theEvent);

        session.getTransaction().commit();
    }

    private Session getSession() {
        return HibernateUtil.getSessionFactory().getCurrentSession();
    }


}
