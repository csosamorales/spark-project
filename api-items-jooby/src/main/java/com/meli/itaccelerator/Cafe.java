package com.meli.itaccelerator;

import org.jooby.mvc.GET;
import org.jooby.mvc.Path;

import java.util.Optional;

@Path("/mvc")
public class Cafe {

    @Path("/cafe")
    @GET
    public String cafe(Optional<String> tipo){
        return tipo.orElse("capuchino");
    }
}
