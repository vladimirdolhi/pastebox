package by.company.pastebox.repository;

import java.util.List;

public interface PasteboxRepository {
    PasteboxEntity getByHash(String hash);
    List<PasteboxEntity> getListOfPublicAndAlive(int amount);
    void add(PasteboxEntity pasteboxEntity);
}
