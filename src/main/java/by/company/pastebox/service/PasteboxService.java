package by.company.pastebox.service;

import by.company.pastebox.api.request.PasteboxRequest;
import by.company.pastebox.api.response.PasteboxUrlResponse;
import by.company.pastebox.api.response.PasteboxResponse;

import java.util.List;

public interface PasteboxService {
    PasteboxResponse getByHash(String hash);
    List<PasteboxResponse> getFirstPublicPasteboxes();
    PasteboxUrlResponse create(PasteboxRequest request);
}
