package com.example.custominitializr.generator;

import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

import com.example.custominitializr.generator.contributor.SimpleContributor;
import io.spring.initializr.generator.project.DefaultProjectAssetGenerator;
import io.spring.initializr.generator.project.ProjectAssetGenerator;
import io.spring.initializr.generator.project.ProjectDescription;
import io.spring.initializr.generator.project.ProjectGenerator;

class SimpleGenerator {

	private static final DateTimeFormatter formatter = DateTimeFormatter.ofPattern("HHmmss");

	Path generateProject(ProjectDescription description) {
		ProjectGenerator projectGenerator = new ProjectGenerator((context) -> {
			context.registerBean(SimpleContributor.class, SimpleContributor::new);
		});
		ProjectAssetGenerator<Path> projectAssetGenerator = new DefaultProjectAssetGenerator(
				(resolvedDescription) -> Paths.get("target/projects",
						"project-" + formatter.format(LocalDateTime.now())));
		return projectGenerator.generate(description, projectAssetGenerator);
	}

}
