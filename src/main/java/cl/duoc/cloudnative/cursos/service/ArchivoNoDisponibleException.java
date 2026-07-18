package cl.duoc.cloudnative.cursos.service;

public class ArchivoNoDisponibleException extends RuntimeException {

    public ArchivoNoDisponibleException(String message, Throwable cause) {
        super(message, cause);
    }
}
