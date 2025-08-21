package service;

import jakarta.inject.Inject;

public class ClientService {
    public String convertirNombresMayuscula(String nombre)
    {
        String nombreMayuscula = nombre.toUpperCase();
        return nombreMayuscula;
    }
}
