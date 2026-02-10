package io.alicerce.deployment;

import io.alicerce.core.PagePath;
import io.alicerce.runtime.PagePathRecorder;
import io.quarkus.arc.deployment.SyntheticBeanBuildItem;
import io.quarkus.deployment.annotations.BuildProducer;
import io.quarkus.deployment.annotations.BuildStep;
import io.quarkus.deployment.annotations.ExecutionTime;
import io.quarkus.deployment.annotations.Record;
import io.quarkus.deployment.builditem.FeatureBuildItem;
import jakarta.inject.Singleton;

import java.util.ArrayList;
import java.util.List;

class AlicerceProcessor {

    private static final String FEATURE = "alicerce";

    private static final List<String> FRAMEWORK_PATHS = List.of(
            "/framework/path1",
            "/framework/path2");

    @BuildStep
    FeatureBuildItem feature() {
        return new FeatureBuildItem(FEATURE);
    }

    @BuildStep
    @Record(ExecutionTime.STATIC_INIT)
    void processConfiguration(
            AlicerceConfig config,
            PagePathRecorder recorder,
            BuildProducer<SyntheticBeanBuildItem> syntheticBeanProducer) {

        var pathList = new ArrayList<>(FRAMEWORK_PATHS);
        config.pagePaths().ifPresent(pathList::addAll);
        var splitted = pathList.stream().map(AlicerceProcessor::split).toList();

        syntheticBeanProducer.produce(SyntheticBeanBuildItem.configure(PagePath.class)
                .supplier(recorder.buildPagePath(splitted))
                .scope(Singleton.class)
                .unremovable()
                .done());
    }

    static String[] split(String path) {
        path = path.trim();
        while (path.startsWith("/"))
            path = path.substring(1);
        while (path.endsWith("/"))
            path = path.substring(0, path.length() - 1);
        return path.split("/");
    }
}
