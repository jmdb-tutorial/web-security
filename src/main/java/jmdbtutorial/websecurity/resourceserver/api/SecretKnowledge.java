package jmdbtutorial.websecurity.resourceserver.api;

import com.fasterxml.jackson.annotation.JsonProperty;

public class SecretKnowledge {


    private final String theMessage;

    public SecretKnowledge(String theMessage) {
        this.theMessage = theMessage;
    }


    @JsonProperty
    public String getTheMessage() {
        return theMessage;
    }
}