package view;

import org.apache.ambari.view.ViewContext;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.inject.Inject;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.io.IOException;

@Path("/")
public class HdfsSnapshotResource {
  private static Logger LOG = LoggerFactory.getLogger(HdfsSnapshotResource.class);

  @Inject
  private ViewContext context;

  private SnapshotService service;

  @GET
  @Produces(MediaType.APPLICATION_JSON)
  public Response retrieveSnapshots() throws IOException, InterruptedException {
    if (service == null) {
      Thread.currentThread().setContextClassLoader(null);
      service = new SnapshotService(context.getProperties().get("fs.url"));
    }
    SnapshotInfo[] result = service.getSnapshotDirectories();
    if (result.length == 0) {
      return Response.noContent().build();
    }

    return Response.ok(result).build();
  }

  @GET
  @Path("/currentuser")
  @Produces(MediaType.TEXT_PLAIN)
  public Response getUserName() throws IOException {
    return Response.ok(context.getUsername()).build();
  }
}
