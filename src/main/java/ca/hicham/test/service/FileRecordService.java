package ca.hicham.test.service;

import java.io.IOException;
import java.util.Collection;
import java.util.Date;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import ca.hicham.test.dao.FileRecordRepository;
import ca.hicham.test.model.FileRecord;
import ca.hicham.test.model.User;

@Service
public class FileRecordService {
	
	@Autowired
	FileRecordRepository repo;
	
	@Autowired
	StorageService storageService;
	
	
	public boolean saveFileRecord(FileRecord fileRecord, MultipartFile file, User user) {
		
		storageService.storeFile(fileRecord.getFilename(), file);
		
		fileRecord.setSize(file.getSize());
		fileRecord.setLoad_date(new Date());
		fileRecord.setMod_date(new Date());
		fileRecord.setUser(user);
		
		repo.save(fileRecord);
		return true;
	}
	
	public Collection<FileRecord> ListFileRecords() {
		 return repo.findAll();
	}
	
	public void deleteFileRecord(long id) throws IOException {
		Optional<FileRecord> fileRecord=repo.findById(id);
	    storageService.delete(fileRecord.get().getFilename());
	    repo.deleteById(id);	
	}
	
	public FileRecord getFileRecord(long id) {
		return repo.findById(id).get();
		
	}
	public void updateFileRecord(FileRecord fileRecord) {
		FileRecord fileRecorddb=repo.findById(fileRecord.getId()).get();
		fileRecorddb.setFilename(fileRecord.getFilename());
		fileRecorddb.setHidden(fileRecord.isHidden());
		fileRecorddb.setDescription(fileRecord.getDescription());
		fileRecorddb.setMod_date(new Date());
		repo.save(fileRecorddb);
	}
}
