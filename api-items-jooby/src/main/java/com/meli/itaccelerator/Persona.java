package com.meli.itaccelerator;

public class Persona {

    public String nombre;
    public String apellido;
    public int edad;

    public Persona(){

    }

    public Persona(String nombre, String apellido, int edad){
        this.nombre = nombre;
        this.apellido = apellido;
        this.edad = edad;
    }

    @Override
    public String toString() {
        return nombre + ", " + apellido + ", " + edad;
    }
}
