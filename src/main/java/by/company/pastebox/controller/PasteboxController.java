package by.company.pastebox.controller;

import by.company.pastebox.api.request.PasteboxRequest;
import by.company.pastebox.api.response.PasteboxResponse;
import by.company.pastebox.api.response.PasteboxUrlResponse;
import by.company.pastebox.service.PasteboxService;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.Collection;

@RestController
@AllArgsConstructor
public class PasteboxController {
    private final PasteboxService pasteboxService;
    @GetMapping("/")
    public Collection<PasteboxResponse> getPublicPasteList(){
        return pasteboxService.getFirstPublicPasteboxes();
    }
    @GetMapping("/{hash}")
    public PasteboxResponse getByHash(@PathVariable String hash){
        return pasteboxService.getByHash(hash);
    }

    @PostMapping("/")
    public PasteboxUrlResponse add(@RequestBody PasteboxRequest request){
        return pasteboxService.create(request);
    }
}
