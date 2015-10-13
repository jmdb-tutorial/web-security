package jmdbtutorial.websecurity.platform.openam;

import javax.security.auth.Subject;
import java.security.Principal;

public class User implements Principal{
    public final String username;

    public User(String username) {
        this.username = username;
    }


    @Override
    public String getName() {
        return username;
    }

    @Override
    public boolean implies(Subject subject) {
        return false;
    }

    public String toString() {
        return username;
    }
}
