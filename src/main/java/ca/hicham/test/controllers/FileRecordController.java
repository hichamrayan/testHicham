package ca.hicham.test.controllers;

import java.io.IOException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import ca.hicham.test.forms.Alert;
import ca.hicham.test.model.FileRecord;
import ca.hicham.test.model.User;
import ca.hicham.test.security.AuthenticationFacade;
import ca.hicham.test.service.FileRecordService;

@Controller
public class FileRecordController {
	
	private static final Logger logger = LoggerFactory.getLogger(FileRecordController.class);

	
	@Autowired
	FileRecordService fileRecordService;
	
	@PreAuthorize("hasPermission('files', 'list')")
	@GetMapping("/files")
	String listFileRecords(Model model){
		model.addAttribute("files",fileRecordService.ListFileRecords());
		return "files/list";
	}
	@GetMapping("/files/upload")
	String uploadFileRecord(Model model){
		model.addAttribute("fileRecord", new FileRecord());
		return "files/upload";
	}
	@PostMapping("/files/upload")
	String uploadFileRecord(AuthenticationFacade authenticationFacade,@RequestParam MultipartFile file, @ModelAttribute FileRecord fileRecord,RedirectAttributes redirectAttributes){
		logger.info("user="+authenticationFacade.getCurrentUser().get());
		 if(fileRecordService.saveFileRecord(fileRecord, file,authenticationFacade.getCurrentUser().get())) {
			 redirectAttributes.addFlashAttribute("msg", new Alert("success","upload OK"));
			 return "redirect:/files";
		 }
		 return "files/upload"; // Envoyer un flash
	}
	
	@PreAuthorize("hasPermission(#id, 'delete')")
	@GetMapping("/files/delete/{id}")
	String deleteFileRecord(@PathVariable long id,RedirectAttributes redirectAttributes) throws IOException{
		fileRecordService.deleteFileRecord(id);
		redirectAttributes.addFlashAttribute("msg", new Alert("success","deleted OK"));
		return "redirect:/files"; // Envoyer un flash
	}
	
	@GetMapping("/files/edit/{id}")
	String editFileRecord(@PathVariable long id, Model model) throws IOException{
		model.addAttribute("fileRecord",fileRecordService.getFileRecord(id));
		return "/files/edit";
	}
	
	@PostMapping("/files/edit")
	String editFileRecord(@ModelAttribute FileRecord fileRecord, RedirectAttributes redirectAttributes) throws IOException{
		fileRecordService.updateFileRecord(fileRecord);
		redirectAttributes.addFlashAttribute("msg", new Alert("success","Sauvegarde OK"));
		return "redirect:/files"; // Envoyer un flash
	}
}
