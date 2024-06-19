package id.global.apps.pdfesig;

import java.io.IOException;
import java.util.List;
import java.util.UUID;

import id.global.apps.pdfesig.model.RestModels;
import org.eclipse.microprofile.jwt.JsonWebToken;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import id.global.apps.pdfesig.repository.PdfRepository;
import id.global.apps.pdfesig.services.PdfEsigService;
import io.quarkus.security.Authenticated;
import jakarta.inject.Inject;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;

@Path("/pdfesig")
@Authenticated
public class PdfEsigResource {
    private final Logger log = LoggerFactory.getLogger(PdfEsigResource.class);

    private final PdfEsigService pdfEsigService;
    private final PdfRepository pdfRepository;

    @Inject
    JsonWebToken jwt;

    @Inject
    public PdfEsigResource(
            final PdfEsigService pdfEsigService,
            final PdfRepository pdfRepository) {
        this.pdfEsigService = pdfEsigService;
        this.pdfRepository = pdfRepository;
    }

    @Path("/list")
    @GET
    public List<RestModels.PdfItem> list() {
        return pdfEsigService.getPdfs(UUID.fromString(jwt.getSubject()));
    }

    @Path("/{pdfId}")
    @GET
    public RestModels.PdfItem getOne(@PathParam("pdfId") UUID pdfId) {
        return pdfEsigService.getPdf(UUID.fromString(jwt.getSubject()), pdfId);
    }

    @Path("upload")
    @POST
    @Consumes(MediaType.MULTIPART_FORM_DATA)
    public RestModels.UploadPdfResponse uploadPdf(PdfEsigService.FileUploadInput input) throws IOException {
        log.info("uploading file for user {}", jwt.getSubject());
        return pdfEsigService.uploadPdf(UUID.fromString(jwt.getSubject()), input);
    }



}
