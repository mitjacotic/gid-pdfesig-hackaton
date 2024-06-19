package id.global.apps.pdfesig.model;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.time.OffsetDateTime;
import java.util.Date;
import java.util.UUID;

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
