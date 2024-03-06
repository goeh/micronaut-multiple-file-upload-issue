package micronaut.issue;

import io.micronaut.core.annotation.Nullable;
import io.micronaut.http.MediaType;
import io.micronaut.http.annotation.Controller;
import io.micronaut.http.annotation.Part;
import io.micronaut.http.annotation.PathVariable;
import io.micronaut.http.annotation.Post;
import io.micronaut.http.multipart.CompletedFileUpload;
import org.reactivestreams.Publisher;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import reactor.core.scheduler.Schedulers;

import java.io.*;
import java.util.List;

@Controller
public class UploadController {

    private static final Logger log = LoggerFactory.getLogger(UploadController.class);

    @Post(value = "/{+path}", consumes = MediaType.MULTIPART_FORM_DATA, produces = MediaType.APPLICATION_JSON)
    public Mono<List<String>> upload(@PathVariable String path,
                                     @Part("file") Publisher<CompletedFileUpload> file,
                                     @Nullable @Part("metadata") Metadata metadata) {
        return Flux.from(file)
                .subscribeOn(Schedulers.boundedElastic())
                .map(part -> {
                    try (InputStream in = part.getInputStream(); OutputStream out = new BufferedOutputStream(new FileOutputStream(File.createTempFile("upload", ".dat")))) {
                        in.transferTo(out);
                        if (metadata != null) {
                            log.info("metadata={}", metadata);
                        }
                        log.info("{} uploaded to {}", part.getFilename(), path);
                    } catch (IOException e) {
                        throw new RuntimeException(e);
                    }
                    return part.getFilename();
                })
                .collectList();
    }
}
