package id.global.apps.pdfesig.repository;

import java.util.UUID;

import id.global.apps.pdfesig.jpa.PdfJpa;
import io.quarkus.hibernate.orm.panache.PanacheRepositoryBase;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.transaction.Transactional;

@ApplicationScoped
public class PdfRepository implements PanacheRepositoryBase<PdfJpa, UUID> {

    @Transactional
    public PdfJpa addPdfFile(UUID gidUuid, String fileName, String filePath) {
        final var pdfJpa = new PdfJpa();
        pdfJpa.id = UUID.randomUUID();
        pdfJpa.filePath = filePath;
        pdfJpa.fileName = fileName;
        pdfJpa.gidUuid = gidUuid;
        pdfJpa.persistAndFlush();
        return pdfJpa;
    }
}
