package WebService;

import jakarta.ws.rs.ApplicationPath;
import jakarta.ws.rs.core.Application;
import java.util.Set;

@ApplicationPath("resources")
public class JAXRSConfiguration extends Application {


    @Override
    public Set<Class<?>> getClasses() {
        Set<Class<?>> resources = new java.util.HashSet<>();
        resources.add(PrimerosPasosWs.class);
        return resources;
    }
}
