package ca.hicham.test.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import ca.hicham.test.model.FileRecord;

public interface FileRecordRepository extends JpaRepository<FileRecord,Long> {

}
