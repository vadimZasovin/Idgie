package com.appcraftlab.idgie.sample;

/**
 * Created by vadim on 4/2/17.
 */

final class BaseProfile {

    private final String name;
    private final String email;

    BaseProfile(String name, String email){
        this.name = name;
        this.email = email;
    }

    String getName() {
        return name;
    }

    String getEmail() {
        return email;
    }
}
