package com.example.sitebro;

import java.io.File;
import java.io.IOException;
import java.util.stream.Stream;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.*;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import com.example.sitebro.ast.ASTPrinter;

@SpringBootApplication
public class SitebroApplication implements ApplicationRunner {

	private static Logger LOG = LoggerFactory
			.getLogger(SitebroApplication.class);

	public static void main(String[] args) {
		SpringApplication.run(SitebroApplication.class, args);
	}

	@Override
	public void run(ApplicationArguments args) throws Exception {
		var files = args.getNonOptionArgs();
		if (files.size() == 0)
			LOG.warn("No files specified");
		if (files.size() == 1)
			LOG.warn("Only 1 source file specified, formatting only");
		// we want to take all args
		var resources = files.stream().map(File::new).flatMap(this::resourceFile);
		// read them into html form
		resources.map(ResourceFile::read).map(ASTPrinter::new).map(ASTPrinter::toString).forEach(System.out::println);
	}

	private Stream<? extends ResourceFile> resourceFile(File arg0) {
		try {
			return Stream.of(new ResourceFile(arg0));
		} catch (IOException e) {
			LOG.warn("Can't open " + arg0, e);
			return Stream.empty();
		}
	}

}
