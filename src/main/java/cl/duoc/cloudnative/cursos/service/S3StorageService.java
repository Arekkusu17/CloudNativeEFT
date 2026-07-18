package cl.duoc.cloudnative.cursos.service;

import cl.duoc.cloudnative.cursos.dto.ArchivoContenido;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import software.amazon.awssdk.core.ResponseBytes;
import software.amazon.awssdk.core.sync.RequestBody;
import software.amazon.awssdk.services.s3.S3Client;
import software.amazon.awssdk.services.s3.model.GetObjectRequest;
import software.amazon.awssdk.services.s3.model.GetObjectResponse;
import software.amazon.awssdk.services.s3.model.PutObjectRequest;
import software.amazon.awssdk.services.s3.model.DeleteObjectRequest;
import software.amazon.awssdk.services.s3.model.S3Exception;

@Service
public class S3StorageService implements StorageService {

    private final S3Client s3Client;
    private final String bucket;

    public S3StorageService(S3Client s3Client, @Value("${aws.s3.bucket}") String bucket) {
        this.s3Client = s3Client;
        this.bucket = bucket;
    }

    @Override
    public void subir(String key, String contentType, byte[] contenido) {
        try {
            PutObjectRequest request = PutObjectRequest.builder()
                    .bucket(bucket)
                    .key(key)
                    .contentType(contentType)
                    .build();
            s3Client.putObject(request, RequestBody.fromBytes(contenido));
        } catch (S3Exception exception) {
            throw new ArchivoNoDisponibleException("No fue posible subir el contenido a S3", exception);
        }
    }

    @Override
    public ArchivoContenido descargar(String key, String nombreArchivo, String contentType) {
        try {
            GetObjectRequest request = GetObjectRequest.builder()
                    .bucket(bucket)
                    .key(key)
                    .build();
            ResponseBytes<GetObjectResponse> response = s3Client.getObjectAsBytes(request);
            return new ArchivoContenido(nombreArchivo, contentType, response.asByteArray());
        } catch (S3Exception exception) {
            throw new ArchivoNoDisponibleException("No fue posible descargar el contenido desde S3", exception);
        }
    }

    @Override
    public void eliminar(String key) {
        try {
            DeleteObjectRequest request = DeleteObjectRequest.builder()
                    .bucket(bucket)
                    .key(key)
                    .build();
            s3Client.deleteObject(request);
        } catch (S3Exception exception) {
            throw new ArchivoNoDisponibleException("No fue posible eliminar el contenido en S3", exception);
        }
    }
}
