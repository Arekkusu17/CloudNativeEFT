package cl.duoc.cloudnative.cursos.service;

import cl.duoc.cloudnative.cursos.dto.ArchivoContenido;

public interface StorageService {

    void subir(String key, String contentType, byte[] contenido);

    ArchivoContenido descargar(String key, String nombreArchivo, String contentType);

    void eliminar(String key);
}
