package net.golovach.eshop.listener;

import javax.servlet.http.HttpSessionActivationListener;
import javax.servlet.http.HttpSessionEvent;

/**
 * Created by BELSHINA on 07.02.2017.
 */

public class MyHttpSessionActivationListener implements HttpSessionActivationListener {

    // Public constructor is required by servlet spec
    public MyHttpSessionActivationListener() {
        System.out.println(">> MyHttpSessionActivationListener - NEW");
    }

    public void sessionWillPassivate(HttpSessionEvent event) {
        System.out.println(">> HttpSession - will passivate, id = " + event.getSession().getId());
    }

    public void sessionDidActivate(HttpSessionEvent event) {
        System.out.println(">> HttpSession - did passivate, id = " + event.getSession().getId());
    }
}
