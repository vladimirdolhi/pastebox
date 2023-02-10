package by.company.pastebox.service.impl;

import by.company.pastebox.api.request.PasteboxRequest;
import by.company.pastebox.api.request.PublicStatus;
import by.company.pastebox.api.response.PasteboxResponse;
import by.company.pastebox.api.response.PasteboxUrlResponse;
import by.company.pastebox.repository.PasteboxEntity;
import by.company.pastebox.repository.PasteboxRepository;
import by.company.pastebox.service.PasteboxService;
import lombok.Setter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.Collectors;

@Service
@Setter
public class PasteboxServiceImpl implements PasteboxService {
    @Value("${app.host}")
    private String host;
    @Value("${app.public_list_size}")
    private int publicListSize;
    private final PasteboxRepository repository;
    private AtomicInteger idGenerator = new AtomicInteger(0);

    public PasteboxServiceImpl(PasteboxRepository repository) {
        this.repository = repository;
    }

    @Override
    public PasteboxResponse getByHash(String hash) {
        PasteboxEntity pasteboxEntity = repository.getByHash(hash);
        return new PasteboxResponse(pasteboxEntity.getData(), pasteboxEntity.isPublic());
    }

    @Override
    public List<PasteboxResponse> getFirstPublicPasteboxes() {
        List<PasteboxEntity> list = repository.getListOfPublicAndAlive(publicListSize);
        return list.stream().map(pasteboxEntity ->
            new PasteboxResponse(pasteboxEntity.getData(), pasteboxEntity.isPublic())
        ).collect(Collectors.toList());
    }

    @Override
    public PasteboxUrlResponse create(PasteboxRequest request) {
        int hash = generateId();
        PasteboxEntity pasteboxEntity = new PasteboxEntity();
        pasteboxEntity.setData(request.getData());
        pasteboxEntity.setId(hash);
        pasteboxEntity.setHash(Integer.toHexString(hash));
        pasteboxEntity.setPublic(request.getPublicStatus() == PublicStatus.PUBLIC);
        pasteboxEntity.setLifetime(LocalDateTime.now().plusSeconds(request.getExpirationTimeSeconds()));
        repository.add(pasteboxEntity);
        return new PasteboxUrlResponse(host + "/" + pasteboxEntity.getHash());

    }

    private int generateId() {
        return idGenerator.getAndIncrement();
    }
}
