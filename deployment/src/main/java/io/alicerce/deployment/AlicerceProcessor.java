package io.alicerce.deployment;

import io.alicerce.core.PagePath;
import io.alicerce.runtime.AlicerceRecorder;
import io.alicerce.utils.Utils;
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
            AlicerceRecorder recorder,
            BuildProducer<SyntheticBeanBuildItem> syntheticBeanProducer) {

        buildPagePath(config, recorder, syntheticBeanProducer);
        buildMessagesByLanguage(config, recorder, syntheticBeanProducer);
    }

    void buildPagePath(
            AlicerceConfig config,
            AlicerceRecorder recorder,
            BuildProducer<SyntheticBeanBuildItem> producer) {

        var pathList = new ArrayList<>(FRAMEWORK_PATHS);
        config.pagePaths().ifPresent(pathList::addAll);
        var splitted = pathList
                .stream()
                .map(Utils::splitPath)
                .toList();
        producer.produce(SyntheticBeanBuildItem.configure(PagePath.class)
                .supplier(recorder.createPagePath(splitted))
                .scope(Singleton.class)
                .unremovable()
                .done());
    }

    void buildMessagesByLanguage(
            AlicerceConfig config,
            AlicerceRecorder recorder,
            BuildProducer<SyntheticBeanBuildItem> producer) {
    }
}