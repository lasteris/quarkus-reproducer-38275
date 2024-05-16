package me.lasteris.quarkus_repro_38275;

import io.quarkus.rest.client.reactive.ClientExceptionMapper;
import io.smallrye.common.annotation.Blocking;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.core.Response;
import org.eclipse.microprofile.rest.client.inject.RegisterRestClient;
import org.jboss.resteasy.reactive.RestQuery;

/***
 * Uncomment Line 32 to make build work OR GET ERROR described below
 * <pre>
 *     Wrong usage(s) of @Blocking found:
 * 	- me.lasteris.quarkus_repro_38275.BrokenBlockingApi.toException(jakarta.ws.rs.core.Response)
 * The @Blocking, @NonBlocking and @RunOnVirtualThread annotations may only be used on "entrypoint" methods (methods invoked by various frameworks in Quarkus)
 * Using the @Blocking, @NonBlocking and @RunOnVirtualThread annotations on methods that can only be invoked by application code is invalid
 * 	at io.quarkus.deployment.execannotations.ExecutionModelAnnotationsProcessor.check(ExecutionModelAnnotationsProcessor.java:55)
 * 	at java.base/jdk.internal.reflect.DirectMethodHandleAccessor.invoke(DirectMethodHandleAccessor.java:103)
 * 	at java.base/java.lang.reflect.Method.invoke(Method.java:580)
 * 	at io.quarkus.deployment.ExtensionLoader$3.execute(ExtensionLoader.java:849)
 * 	at io.quarkus.builder.BuildContext.run(BuildContext.java:256)
 * 	at org.jboss.threads.ContextHandler$1.runWith(ContextHandler.java:18)
 * 	at org.jboss.threads.EnhancedQueueExecutor$Task.doRunWith(EnhancedQueueExecutor.java:2516)
 * 	at org.jboss.threads.EnhancedQueueExecutor$Task.run(EnhancedQueueExecutor.java:2495)
 * 	at org.jboss.threads.EnhancedQueueExecutor$ThreadBody.run(EnhancedQueueExecutor.java:1521)
 * 	at java.base/java.lang.Thread.run(Thread.java:1583)
 * 	at org.jboss.threads.JBossThread.run(JBossThread.java:483)
 * </pre>
 */
@RegisterRestClient(configKey = "google-client")
//@Path("/")
public interface BrokenBlockingApi {
    @GET
    @Path("/search")
    String query(@RestQuery String q);

    @Blocking
    @ClientExceptionMapper
    static RuntimeException toException(Response response) {
        String entity = response.readEntity(String.class).isEmpty() ?
                response.getStatusInfo().getReasonPhrase() :
                response.readEntity(String.class);

        return new RuntimeException(entity);
    }

}
