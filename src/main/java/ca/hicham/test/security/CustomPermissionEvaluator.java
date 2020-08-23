package ca.hicham.test.security;

import java.io.Serializable;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.PermissionEvaluator;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;

import ca.hicham.test.service.FileRecordService;


public class CustomPermissionEvaluator implements PermissionEvaluator {
	@Autowired
	FileRecordService fileRecordService;
	private static final Logger log = LoggerFactory.getLogger(CustomPermissionEvaluator.class);

    @Override
    public boolean hasPermission(Authentication auth, Object targetDomainObject, Object permission) {
    	log.info("hasPermission("+auth.getName()+","+targetDomainObject.toString()+","+permission.toString());
        if ((auth == null) || (targetDomainObject == null) || !(permission instanceof String)
        		|| !(targetDomainObject instanceof String)){
            return false;
        }
        String target = targetDomainObject.toString();
        
        return hasPrivilege(auth, target, permission.toString().toUpperCase());
    }
 
    @Override
    public boolean hasPermission(Authentication auth, Serializable targetId, String targetType, Object permission) {
    	log.info("hasPermission2("+auth.getName()+","+targetId.toString()+","+targetType+","+permission.toString());
        if ((auth == null) || (targetType == null) || !(permission instanceof String)) {
            return false;
        }
        return hasPrivilege(auth, targetId.toString(), 
          permission.toString().toUpperCase());
    }

	private boolean hasPrivilege(Authentication auth, String target, String permission) {
    	if(permission.toString().toUpperCase().equals("DELETE"))
    	{
    		return fileRecordService.getFileRecord(Integer.parseInt(target)).getUser().getUsername()==auth.getName();
    	}
		for(GrantedAuthority au : auth.getAuthorities()) {
			if(au.getAuthority().equalsIgnoreCase(permission+"_"+target)) {
				return true;
			}
			if(permission.equals("*") && au.getAuthority().endsWith("_"+target)) {
				return true;
			}
		}
		return false;
	}
    
    
}