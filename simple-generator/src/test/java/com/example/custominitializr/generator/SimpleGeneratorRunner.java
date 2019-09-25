package com.example.custominitializr.generator;

import java.nio.file.Path;

import io.spring.initializr.generator.buildsystem.BuildSystem;
import io.spring.initializr.generator.buildsystem.Dependency;
import io.spring.initializr.generator.buildsystem.maven.MavenBuildSystem;
import io.spring.initializr.generator.language.Language;
import io.spring.initializr.generator.language.java.JavaLanguage;
import io.spring.initializr.generator.project.MutableProjectDescription;
import io.spring.initializr.generator.version.VersionReference;

public class SimpleGeneratorRunner {

	public static void main(String[] args) {
		MutableProjectDescription description = new MutableProjectDescription();
		description.setGroupId("com.example");
		description.setArtifactId("app");
		description.setVersion("1.0.0.BUILD-SNAPSHOT");
		description.setBuildSystem(BuildSystem.forId(MavenBuildSystem.ID));
		description.setLanguage(Language.forId(JavaLanguage.ID, "11"));
		description.addDependency("spring-context", Dependency.withCoordinates("org.springframework", "spring-context")
				.version(VersionReference.ofValue("5.1.8.RELEASE")));

		SimpleGenerator generator = new SimpleGenerator();
		Path directory = generator.generateProject(description);
		System.out.println("Generated project available at " + directory.toAbsolutePath());
	}

}
