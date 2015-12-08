package com.mfuentes.hermesmatiasfuentes.model;

public class Configuracion {

    private String ip;
    private String puerto;

    public Configuracion(String ip, String puerto) {
        this.ip = ip;
        this.puerto = puerto;
    }

    public String getIp() {
        return ip;
    }

    public String getPuerto() {
        return puerto;
    }

}
