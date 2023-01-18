package com.example.sitebro;

import java.io.File;
import java.io.IOException;
import java.util.stream.Stream;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.lang.NonNull;

import com.example.exceptions.RootUnidentifiedException;
import com.example.exceptions.UnrelatedTreesException;
import com.example.sitebro.ast.ASTTree;

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
		if(files.size() == 0) LOG.warn("No files specified");
		if(files.size() == 1) LOG.warn("Only 1 source file specified, formatting only");
		// we want to take all args
		var resources = files.stream().map(File::new).flatMap(this::resourceFile);
		// read them into html form
		ASTTree htmls = resources.map(ResourceFile::read).reduce(this::compose).orElse(() -> null);
		// print formatted and merged
		System.out.println(htmls.content());
	}

	private ASTTree compose(@NonNull ASTTree arg0, @NonNull ASTTree arg1) {
		try {
			return ASTTree.rewrite(arg0, arg1);
		} catch (RootUnidentifiedException e) {
			LOG.info("ignoring AST sapling, no valid root to attach with", e);
			return arg0;
		} catch (UnrelatedTreesException e) {
			LOG.info("ignoring AST sapling, no common ids found in common", e);
			return arg0;
		}
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
