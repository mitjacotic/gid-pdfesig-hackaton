package id.global.apps.pdfesig.services;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.List;
import java.util.UUID;

import org.eclipse.microprofile.config.inject.ConfigProperty;
import org.jboss.resteasy.reactive.multipart.FileUpload;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import id.global.apps.pdfesig.model.RestModels;
import id.global.apps.pdfesig.repository.PdfRepository;
import io.quarkus.panache.common.Parameters;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.transaction.Transactional;
import jakarta.ws.rs.FormParam;
import jakarta.ws.rs.NotFoundException;

@ApplicationScoped
public class PdfEsigService {
    private final static Logger log = LoggerFactory.getLogger(PdfEsigService.class);

    private final PdfRepository pdfRepository;

    @ConfigProperty(name = "pdfesig.upload-path")
    String uploadLocation;

    @Inject
    public PdfEsigService(final PdfRepository pdfRepository) {
        this.pdfRepository = pdfRepository;
    }

    @Transactional
    public RestModels.UploadPdfResponse uploadPdf(UUID gidUuid, FileUploadInput input) throws IOException {
        for (FileUpload file : input.file) {
            var inputStream = file.uploadedFile().toUri().toURL().openStream();
            byte[] bytes = inputStream.readAllBytes();
            File customDir = new File(uploadLocation + File.separator + gidUuid);
            if (!customDir.exists())
                customDir.mkdirs();
            String filePath = customDir.getCanonicalPath() + File.separator + file.fileName();
            writeFile(bytes, filePath);
            log.info("uploaded file for user {}: {}", gidUuid, file.fileName());
            var pdfJpa = pdfRepository.addPdfFile(gidUuid, file.fileName(), filePath);

            return new RestModels.UploadPdfResponse(pdfJpa.id, pdfJpa.fileName);
        }

        return null;
    }

    private void writeFile(byte[] content, String filename) throws IOException {
        File file = new File(filename);
        if (!file.exists()) {
            file.createNewFile();
        }
        try (FileOutputStream fop = new FileOutputStream(file)) {
            fop.write(content);
            fop.flush();
        }
    }

    @Transactional
    public List<RestModels.PdfItem> getPdfs(UUID gidUuid) {
        return pdfRepository
                .find("gidUuid = :gidUuid", Parameters.with("gidUuid", gidUuid))
                .stream()
                .map(s -> new RestModels.PdfItem(s.id, s.fileName, s.createdAt))
                .toList();
    }

    @Transactional
    public RestModels.PdfItem getPdf(UUID gidUuid, UUID id) {
        var pdfJpa = pdfRepository
                .find("gidUuid = :gidUuid AND id = :id",
                        Parameters.with("gidUuid", gidUuid).and("id", id))
                .singleResultOptional().orElseThrow(
                        () -> new NotFoundException());

        return new RestModels.PdfItem(pdfJpa.id, pdfJpa.fileName, pdfJpa.createdAt);
    }

    @Transactional
    public RestModels.PdfItem getPdfById(UUID id) {
        var pdfJpa = pdfRepository
                .find(" id = :id",
                        Parameters.with("id", id))
                .singleResultOptional().orElse(null);
        if(pdfJpa == null) {
            return null;
        }

        return new RestModels.PdfItem(pdfJpa.id, pdfJpa.fileName, pdfJpa.createdAt);
    }

    public static class FileUploadInput {
        @FormParam("text")
        public String text;

        @FormParam("file")
        public List<FileUpload> file;
    }

}
