package id.global.apps.pdfesig;

import id.global.apps.pdfesig.model.RestModels;
import id.global.apps.pdfesig.services.PdfEsigService;
import io.quarkus.oidc.IdToken;
import io.quarkus.oidc.OidcSession;
import io.quarkus.qute.Location;
import io.quarkus.qute.Template;
import io.quarkus.qute.TemplateInstance;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.PathParam;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import org.eclipse.microprofile.jwt.JsonWebToken;

import java.net.URI;
import java.util.List;
import java.util.UUID;

@ApplicationScoped
@Produces(MediaType.TEXT_HTML)
@Path("/web")
public class WebResource {

    @Inject
    PdfEsigService pdfEsigService;

    @Inject
    Template base;

    @Inject
    Template verifypdf;

    @Inject
    OidcSession oidcSession;



    @Inject
    @IdToken
    JsonWebToken idToken;

    @GET
    @Produces(MediaType.TEXT_HTML)
    public TemplateInstance get() {
        String userName = idToken.getClaim("globalid");
        List<RestModels.PdfItem> list = pdfEsigService.getPdfs(UUID.fromString(idToken.getName()));
        System.out.println("FFFF=" + idToken.getSubject() + " items " + list);
        return base.data("name", userName).data("pdflist", list).data("has_items", list.size() > 0);
    }
}
