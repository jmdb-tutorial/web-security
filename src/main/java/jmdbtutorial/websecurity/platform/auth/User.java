package jmdbtutorial.websecurity.platform.auth;

import javax.security.auth.Subject;
import java.security.Principal;
import java.util.Set;

public class User implements Principal{
    public final String username;
    private final Set<String> roles;

    public User(String username, Set<String> roles) {
        this.username = username;
        this.roles = roles;
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

    public boolean isInRole(String role) {
        return roles.contains(role);
    }
}
