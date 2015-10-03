package jmdbtutorial.websecurity.openam.resourceserver_b.api;

import com.fasterxml.jackson.annotation.JsonProperty;

public class SecretKnowledgeB {


    private final String theMessage;

    public SecretKnowledgeB(String theMessage) {
        this.theMessage = theMessage;
    }


    @JsonProperty
    public String getTheMessage() {
        return theMessage;
    }
}