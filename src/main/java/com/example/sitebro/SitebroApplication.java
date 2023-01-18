package com.example.sitebro;

import java.io.File;
import java.io.IOException;
import java.util.stream.Stream;

import org.slf4j.*;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import com.example.sitebro.ast.ASTTree;

import lombok.NonNull;

@SpringBootApplication
public class SitebroApplication implements CommandLineRunner {

	private static Logger LOG = LoggerFactory
			.getLogger(SitebroApplication.class);

	public static void main(String[] args) {
		SpringApplication.run(SitebroApplication.class, args);
	}

	@Override
	public void run(String... args) throws Exception {
		// we want to take all args
		var files = Stream.of(args).map(File::new).flatMap(this::resourceFile);
		// read them into html form
		var htmls = files.map(ResourceFile::read).reduce(() -> null, this::compose);
	}

	private ASTTree compose(ASTTree arg0, @NonNull ASTTree arg1) {
		// var mainId = arg1.id();
		// return arg0.rewrite(mainId, arg1);
		return null;
	}

	private Stream<? extends ResourceFile> resourceFile(File arg0) {
		try {
			return Stream.of(new ResourceFile(arg0));
		} catch (IOException e) {
			LOG.info("IO Exception when opening ", arg0);
			return Stream.empty();
		}
	}

}
