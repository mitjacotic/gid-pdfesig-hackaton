package id.global.apps.pdfesig;

import jakarta.enterprise.context.ApplicationScoped;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.MediaType;

@ApplicationScoped
@Produces(MediaType.TEXT_HTML)
@Path("/web")
public class WebResource {

    //@Location("files.html")
    // Templ template;

    //   @GET
    //  public TemplateInstance get() {
    //     return template.instance();
    //}
}
