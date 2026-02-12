package io.alicerce.deployment;

import io.alicerce.core.I18n;
import io.alicerce.core.MessagesProvider;
import io.alicerce.core.Paths;
import io.alicerce.runtime.AlicerceRecorder;
import io.alicerce.ui.Menu;
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
    void buildStaticInit(
            AlicerceConfig config,
            AlicerceRecorder recorder,
            BuildProducer<AdditionalBeanBuildItem> additionalProducer,
            BuildProducer<SyntheticBeanBuildItem> syntheticProducer) {

        var pathsArray = producePaths(config, recorder, syntheticProducer);
        produceSyntheticBean(syntheticProducer, Menu.class, recorder.supplierMenu(pathsArray), APPLICATION);
        produceMessagesProvider(config, recorder, syntheticProducer);
        produceAdditionalBeans(additionalProducer);
    }

    List<String[]> producePaths(
            AlicerceConfig config,
            AlicerceRecorder recorder,
            BuildProducer<SyntheticBeanBuildItem> producer) {
        var configPaths = config.paths().isEmpty()
                ? List.of("")
                : config.paths().get();
        var pathsArray = Utils
                .concat(FRAMEWORK_PATHS)
                .concat(configPaths)
                .stream()
                .map(Utils::splitPath)
                .toList();
        produceSyntheticBean(producer, Paths.class, recorder.supplierPaths(pathsArray), APPLICATION);
        return pathsArray;
    }

    void produceMessagesProvider(
            AlicerceConfig config,
            AlicerceRecorder recorder,
            BuildProducer<SyntheticBeanBuildItem> producer) {
        var languages = new ArrayList<String>();
        var baseNames = new ArrayList<String>();
        config.i18n().ifPresent(i18n -> {
            languages.addAll(i18n.languages());
            baseNames.addAll(i18n.baseNames());
        });
        var supplier = recorder.supplierMessagesProvider(languages, baseNames);
        produceSyntheticBean(producer, MessagesProvider.class, supplier, APPLICATION);
    }

    void produceAdditionalBeans(BuildProducer<AdditionalBeanBuildItem> producer) {
        produceAdditionalBean(producer, UI.class, APPLICATION);
        produceAdditionalBean(producer, UI.comp.class, APPLICATION);
        produceAdditionalBean(producer, I18n.class, REQUEST);
    }

    void produceAdditionalBean(
            BuildProducer<AdditionalBeanBuildItem> producer,
            Class<?> beanClass,
            Class<?> scopeClass) {
        var additionalBean = AdditionalBeanBuildItem
                .builder()
                .addBeanClass(beanClass)
                .setDefaultScope(DotName.createSimple(scopeClass))
                .setUnremovable()
                .build();
        producer.produce(additionalBean);
    }

    void produceSyntheticBean(
            BuildProducer<SyntheticBeanBuildItem> producer,
            Class<?> implClass,
            Supplier<?> beanSupplier,
            Class<? extends Annotation> scopeClass) {
        var syntheticBean = SyntheticBeanBuildItem
                .configure(implClass)
                .supplier(beanSupplier)
                .scope(scopeClass)
                .unremovable()
                .done();
        producer.produce(syntheticBean);
    }
}