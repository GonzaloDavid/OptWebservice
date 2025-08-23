package service;

import jakarta.inject.Inject;

public class ClientService {
    public String convertirNombresMayuscula(String nombre)
    {
        String nombreMayuscula = nombre.toUpperCase();
        return nombreMayuscula;
    }

    public String convertirNombresMinuscula(String nombre)
        {
            String nombreMinuscula = nombre.toLowerCase();
            return nombreMinuscula;
        }
}
