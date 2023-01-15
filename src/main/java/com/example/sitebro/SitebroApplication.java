package com.example.sitebro;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import org.slf4j.*;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class SitebroApplication implements CommandLineRunner {

	 private static Logger LOG = LoggerFactory
      .getLogger(SitebroApplication.class);
	  
	public static void main(String[] args) {
		SpringApplication.run(SitebroApplication.class, args);
	}

	@Override
	public void run(String... args) throws Exception {
		List<ResourceFile> rfs = new ArrayList<>();
		for (String string : args) {
			File f = new File(string);
			if(f.exists()){
				rfs.add(new ResourceFile(f));
			}
		}
	}

}
