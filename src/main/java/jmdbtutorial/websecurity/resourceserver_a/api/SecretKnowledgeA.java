package jmdbtutorial.websecurity.resourceserver_a.api;

import com.fasterxml.jackson.annotation.JsonProperty;

public class SecretKnowledgeA {


    private final String theMessage;

    public SecretKnowledgeA(String theMessage) {
        this.theMessage = theMessage;
    }


    @JsonProperty
    public String getTheMessage() {
        return theMessage;
    }
}