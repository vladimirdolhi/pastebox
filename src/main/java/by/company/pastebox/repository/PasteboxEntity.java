package by.company.pastebox.repository;

import lombok.Data;

import java.time.LocalDateTime;

@Data
public class PasteboxEntity {
    private long id;
    private String data;
    private String hash;
    private LocalDateTime lifetime;
    private boolean isPublic;
}
