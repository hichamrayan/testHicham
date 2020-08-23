package ca.hicham.test.model;
import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.ManyToOne;

@Entity
public class FileRecord {
	 @Id
	 @GeneratedValue
	private long id;
	 private String filename;
	 private long size;
	 private boolean hidden=false;
	 private String description;
	 private Date load_date;
	 private Date mod_date;
	 
	 @ManyToOne(fetch = FetchType.EAGER)
	 private User user;
	
 public long getId() {
		return id;
	}
	public void setId(long id) {
		this.id = id;
	}
	public String getFilename() {
		return filename;
	}
	public void setFilename(String filename) {
		this.filename = filename;
	}
	public long getSize() {
		return size;
	}
	public void setSize(long size) {
		this.size = size;
	}
	public boolean isHidden() {
		return hidden;
	}
	public void setHidden(boolean hidden) {
		this.hidden = hidden;
	}
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}
	public Date getLoad_date() {
		return load_date;
	}
	public void setLoad_date(Date date) {
		this.load_date = date;
	}
	public Date getMod_date() {
		return mod_date;
	}
	public void setMod_date(Date mod_date) {
		this.mod_date = mod_date;
	}
	 @Override
	public String toString() {
		return "FileRecord [id=" + id + ", filename=" + filename + ", size=" + size + ", hidden=" + hidden
				+ ", description=" + description + ", load_date=" + load_date + ", mod_date=" + mod_date + "]";
	}
	public User getUser() {
		return user;
	}
	public void setUser(User user) {
		this.user = user;
	}
}
