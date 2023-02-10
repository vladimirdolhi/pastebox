package by.company.pastebox.api.response;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class PasteboxResponse {
    private String data;
    private boolean isPublic;
}
