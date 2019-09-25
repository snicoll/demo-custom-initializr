package com.example.custominitializr.generator.contributor;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;

import io.spring.initializr.generator.buildsystem.maven.MavenBuild;
import io.spring.initializr.generator.buildsystem.maven.MavenBuildWriter;
import io.spring.initializr.generator.io.IndentingWriter;
import io.spring.initializr.generator.io.SimpleIndentStrategy;
import io.spring.initializr.generator.project.ProjectDescription;
import io.spring.initializr.generator.project.contributor.ProjectContributor;

public class MavenBuildProjectContributor implements ProjectContributor {

	private final ProjectDescription description;

	public MavenBuildProjectContributor(ProjectDescription description) {
		this.description = description;
	}

	@Override
	public void contribute(Path projectRoot) throws IOException {
		Path pomFile = Files.createFile(projectRoot.resolve("pom.xml"));
		MavenBuildWriter buildWriter = new MavenBuildWriter();
		try (IndentingWriter writer = new IndentingWriter(Files.newBufferedWriter(pomFile),
				new SimpleIndentStrategy("    "))) {
			buildWriter.writeTo(writer, createMavenBuild());
		}
	}

	private MavenBuild createMavenBuild() {
		MavenBuild build = new MavenBuild();
		build.settings().group(this.description.getGroupId()).artifact(this.description.getArtifactId())
				.version(this.description.getVersion()).name(this.description.getName())
				.description(this.description.getDescription());
		build.properties().property("java.version", this.description.getLanguage().jvmVersion());
		this.description.getRequestedDependencies()
				.forEach((id, dependency) -> build.dependencies().add(id, dependency));
		return build;
	}

}
