package ca.hicham.test.service;

import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;

@Service
public class StorageService {
	@Value( "${storage.folder}" )
	private String folder;
	
	void storeFile(String filename, MultipartFile file) {
		try {
			if (file.isEmpty()) {
				throw new StorageException("Failed to store empty file " + filename);
			}
			if (filename.contains("..")) {
				// This is a security check
				throw new StorageException(
						"Cannot store file with relative path outside current directory "
								+ filename);
			}
			try (InputStream inputStream = file.getInputStream()) {
				Path dir= Paths.get(folder);
				Files.copy(inputStream, dir.resolve(filename),
					StandardCopyOption.REPLACE_EXISTING);
			}
		}
		catch (IOException e) {
			throw new StorageException("Failed to store file " + filename, e);
		}
	}
	
	public Resource getFile(String filename) throws Exception {
		Path file = Paths.get(folder).resolve(filename);
		Resource resource = new UrlResource(file.toUri());
			return resource;
	}
	
	void delete(String filename) throws IOException {
		try {
		Path file = Paths.get(folder).resolve(filename);
		Files.delete(file);
		}
		catch(Exception e) {}
	}
	
}
