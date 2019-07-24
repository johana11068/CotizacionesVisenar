package com.example.cotizacionesvisenar.Model;

public class Horas {
    private String hora;

    public Horas() {
    }

    public Horas(String hora) {
        this.hora = hora;
    }

    public String getHora() {
        return hora;
    }

    public void setHora(String hora) {
        this.hora = hora;
    }

    @Override
    public String toString() {
        return hora;
    }
}
