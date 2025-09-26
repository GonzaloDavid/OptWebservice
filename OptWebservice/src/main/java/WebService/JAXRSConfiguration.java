package WebService;

import javax.ws.rs.ApplicationPath;
import javax.ws.rs.core.Application;
import java.util.Set;

@ApplicationPath("resources")
public class JAXRSConfiguration extends Application {


    @Override
    public Set<Class<?>> getClasses() {
        Set<Class<?>> resources = new java.util.HashSet<>();
        resources.add(PrimerosPasosWs.class);
        resources.add(CliluiWS.class);
        return resources;
    }
}
