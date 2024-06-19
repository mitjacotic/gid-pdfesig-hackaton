package id.global.apps.pdfesig.model;

import java.time.OffsetDateTime;
import java.util.UUID;

import com.fasterxml.jackson.annotation.JsonProperty;

public class RestModels {
    public record UploadPdfResponse(
            @JsonProperty("id") UUID id,
            @JsonProperty("file_name") String fileName) {
    }

    public record PdfItem(
            @JsonProperty("id") UUID id,
            @JsonProperty("file_name") String fileName,
            @JsonProperty("created_at") OffsetDateTime createdAt) {
    }
}
