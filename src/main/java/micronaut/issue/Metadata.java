package micronaut.issue;

import io.micronaut.core.annotation.Nullable;

import java.util.List;

public record Metadata(@Nullable String project, @Nullable List<String> tags) {
}
