package io.alicerce.deployment;

import io.quarkus.runtime.annotations.ConfigPhase;
import io.quarkus.runtime.annotations.ConfigRoot;
import io.smallrye.config.ConfigMapping;
import io.smallrye.config.WithDefault;

import java.util.List;
import java.util.Optional;

@ConfigRoot(phase = ConfigPhase.BUILD_TIME)
@ConfigMapping(prefix = "quarkus.alicerce")
public interface AlicerceConfig {

    /**
     * Identifies the application path that serves as the base URI for all resource URIs.
     */
    @WithDefault("/")
    String applicationPath();

    /**
     * List of application page paths
     */
    Optional<List<String>> paths();

    /**
     * Configure internationalization.
     */
    Optional<I18n> i18n();

    interface I18n {

        /**
         *  List of languages supported by the application.
         */
        @WithDefault("")
        List<String> languages();

        /**
         * List of base names for the resource packages present in the application.
         */
        List<String> baseNames();
    }
}