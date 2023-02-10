package by.company.pastebox.repository;

import by.company.pastebox.exception.EntityNotFoundException;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.stream.Collectors;

@Repository
public class PasteboxRepositoryMap implements PasteboxRepository{

    private final Map<String, PasteboxEntity> vault = new ConcurrentHashMap<>();
    @Override
    public PasteboxEntity getByHash(String hash) {
        PasteboxEntity pasteboxEntity = vault.get(hash);
        if(pasteboxEntity == null){
            throw new EntityNotFoundException("Pastebox with hash = " + hash + "not found");
        }
        return pasteboxEntity;
    }

    @Override
    public List<PasteboxEntity> getListOfPublicAndAlive(int amount) {
        LocalDateTime currentTimestamp = LocalDateTime.now();
        return vault.values().stream()
                .filter(PasteboxEntity::isPublic)
                .filter(pasteboxEntity -> pasteboxEntity.getLifetime().isAfter(currentTimestamp))
                .sorted(Comparator.comparing(PasteboxEntity::getId).reversed())
                .limit(amount)
                .collect(Collectors.toList());
    }

    @Override
    public void add(PasteboxEntity pasteboxEntity) {
        vault.put(pasteboxEntity.getHash(), pasteboxEntity);
    }
}
