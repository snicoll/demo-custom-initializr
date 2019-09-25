package com.example.custominitializr.generator;

import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

import io.spring.initializr.generator.io.IndentingWriterFactory;
import io.spring.initializr.generator.io.template.MustacheTemplateRenderer;
import io.spring.initializr.generator.project.DefaultProjectAssetGenerator;
import io.spring.initializr.generator.project.ProjectAssetGenerator;
import io.spring.initializr.generator.project.ProjectDescription;
import io.spring.initializr.generator.project.ProjectGenerator;
import io.spring.initializr.metadata.InitializrMetadata;
import io.spring.initializr.metadata.InitializrMetadataBuilder;

import org.springframework.core.io.ClassPathResource;

class SimpleGenerator {

	private static final DateTimeFormatter formatter = DateTimeFormatter.ofPattern("HHmmss");

	Path generateProject(ProjectDescription description) {
		ProjectGenerator projectGenerator = new ProjectGenerator((context) -> {
			context.registerBean(InitializrMetadata.class, () -> InitializrMetadataBuilder.create()
					.withInitializrMetadata(new ClassPathResource("sample-metadata.json")).build());
			context.registerBean(IndentingWriterFactory.class, IndentingWriterFactory::withDefaultSettings);
			context.registerBean(MustacheTemplateRenderer.class,
					() -> new MustacheTemplateRenderer("classpath:/templates"));
		});
		ProjectAssetGenerator<Path> projectAssetGenerator = new DefaultProjectAssetGenerator(
				(resolvedDescription) -> Paths.get("target/projects",
						"project-" + formatter.format(LocalDateTime.now())));
		return projectGenerator.generate(description, projectAssetGenerator);
	}

}
