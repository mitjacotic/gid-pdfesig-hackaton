package id.global.apps.pdfesig;

import id.global.apps.pdfesig.model.RestModels;
import id.global.apps.pdfesig.services.PdfEsigService;
import io.quarkus.oidc.IdToken;
import io.quarkus.oidc.OidcSession;
import io.quarkus.qute.Template;
import io.quarkus.qute.TemplateInstance;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.PathParam;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.MediaType;
import org.eclipse.microprofile.jwt.JsonWebToken;

import java.util.List;
import java.util.UUID;

@ApplicationScoped
@Produces(MediaType.TEXT_HTML)
@Path("/pdf")
public class WebPdfResource {

    @Inject
    PdfEsigService pdfEsigService;


    @Inject
    Template verifypdf;




    @GET
    @Produces(MediaType.TEXT_HTML)
    @Path("/{pdfId}")
    public TemplateInstance getPdf(@PathParam("pdfId") UUID pdfId) {
        var pdf = pdfEsigService.getPdfById(pdfId);
        if(pdf == null) {
            return verifypdf.data("notFound", true);
        }
        return verifypdf.data("notFound", false).data("pdf", pdf);
    }

}
