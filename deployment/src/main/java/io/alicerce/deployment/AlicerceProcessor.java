package io.alicerce.deployment;

import io.alicerce.core.I18n;
import io.alicerce.core.PagePath;
import io.alicerce.runtime.AlicerceRecorder;
import io.alicerce.ui.UI;
import io.alicerce.utils.Utils;
import io.quarkus.arc.deployment.AdditionalBeanBuildItem;
import io.quarkus.arc.deployment.SyntheticBeanBuildItem;
import io.quarkus.deployment.annotations.BuildProducer;
import io.quarkus.deployment.annotations.BuildStep;
import io.quarkus.deployment.annotations.ExecutionTime;
import io.quarkus.deployment.annotations.Record;
import io.quarkus.deployment.builditem.FeatureBuildItem;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.enterprise.context.RequestScoped;
import org.jboss.jandex.DotName;

import java.lang.annotation.Annotation;
import java.util.ArrayList;
import java.util.List;
import java.util.function.Supplier;

class AlicerceProcessor {

    static final String FEATURE = "alicerce";

    static final Class<ApplicationScoped> APPLICATION = ApplicationScoped.class;
    static final Class<RequestScoped> REQUEST = RequestScoped.class;

    static final List<String> FRAMEWORK_PATHS = List.of(
            "/framework/path1",
            "/framework/path2");

    @BuildStep
    FeatureBuildItem feature() {
        return new FeatureBuildItem(FEATURE);
    }

    @BuildStep
    @Record(ExecutionTime.STATIC_INIT)
    void build(
            AlicerceConfig config,
            AlicerceRecorder recorder,
            BuildProducer<AdditionalBeanBuildItem> additionalBeanProducer,
            BuildProducer<SyntheticBeanBuildItem> syntheticBeanProducer) {

        buildPagePath(config, recorder, syntheticBeanProducer);
        buildMessagesByLanguage(config, recorder, syntheticBeanProducer);

        produceAdditionalBeans(additionalBeanProducer);
    }

    void buildPagePath(
            AlicerceConfig config,
            AlicerceRecorder recorder,
            BuildProducer<SyntheticBeanBuildItem> producer) {

        var list = new ArrayList<>(FRAMEWORK_PATHS);
        config
                .pagePaths()
                .ifPresent(list::addAll);
        var paths = list
                .stream()
                .map(Utils::splitPath)
                .toList();
        produceSyntheticBean(producer, PagePath.class, recorder.createPagePath(paths), APPLICATION);
    }

    void buildMessagesByLanguage(
            AlicerceConfig config,
            AlicerceRecorder recorder,
            BuildProducer<SyntheticBeanBuildItem> producer) {
    }

    void produceAdditionalBeans(BuildProducer<AdditionalBeanBuildItem> producer) {
        produceAdditionalBean(producer, UI.class, APPLICATION);
        produceAdditionalBean(producer, UI.comp.class, APPLICATION);
        produceAdditionalBean(producer, I18n.class, REQUEST);
    }

    void produceAdditionalBean(
            BuildProducer<AdditionalBeanBuildItem> producer,
            Class<?> beanClass,
            Class<?> defaultScopeClass) {

        var additionalBean = AdditionalBeanBuildItem
                .builder()
                .addBeanClass(beanClass)
                .setDefaultScope(DotName.createSimple(defaultScopeClass))
                .build();
        producer.produce(additionalBean);
    }

    void produceSyntheticBean(
            BuildProducer<SyntheticBeanBuildItem> producer,
            Class<?> implClass,
            Supplier<?> beanSupplier,
            Class<? extends Annotation> scopeClass) {

        var syntheticBean = SyntheticBeanBuildItem.configure(implClass)
                .supplier(beanSupplier)
                .scope(scopeClass)
                .unremovable()
                .done();
        producer.produce(syntheticBean);
    }
}