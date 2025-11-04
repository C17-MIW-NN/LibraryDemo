package nl.miwnn.ch17.vincent.librarydemo.model;

import jakarta.persistence.*;
import org.springframework.http.MediaType;

import java.util.Arrays;

@Entity
public class Image {
    @Id
    @GeneratedValue
    private Long id;

    @Column(unique = true)
    private String fileName;
    private String contentType;

    @Lob
    private byte[] data;

    @Override
    public String toString() {
        return "Image{" +
                "id=" + id +
                ", fileName='" + fileName + '\'' +
                ", contentType=" + contentType +
                '}';
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getFileName() {
        return fileName;
    }

    public void setFileName(String fileName) {
        this.fileName = fileName;
    }

    public MediaType getContentType() {
        return MediaType.parseMediaType(contentType);
    }

    public void setContentType(MediaType contentType) {
        this.contentType = contentType.toString();
    }

    public byte[] getData() {
        return data;
    }

    public void setData(byte[] data) {
        this.data = data;
    }
}
