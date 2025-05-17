package com.kushal.firstapp.service;

import com.cloudinary.Cloudinary;
    import com.cloudinary.utils.ObjectUtils;
    
    import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.Map;

@Service
public class CloudinaryService {

    private final Cloudinary cloudinary;

    public CloudinaryService(
            @Value("${cloudinary.cloud-name}") String cloudName,
            @Value("${cloudinary.api-key}") String apiKey,
            @Value("${cloudinary.api-secret}") String apiSecret) {
        this.cloudinary = new Cloudinary(ObjectUtils.asMap(
                "cloud_name", cloudName,
                "api_key", apiKey,
                "api_secret", apiSecret
        ));
    }

    

    public String uploadFile2(MultipartFile file) throws IOException {
        String originalFilename = file.getOriginalFilename();
        if (originalFilename == null || !originalFilename.endsWith(".zip")) {
            throw new RuntimeException("Only ZIP files are allowed!");
        }
    
        // Extract filename without extension
        String fileNameWithoutExt = originalFilename.replace(".zip", "");
    
        Map<String, Object> options = ObjectUtils.asMap(
                "resource_type", "raw",  // ✅ Ensure ZIP support
                "folder", "deliverables/",
                "public_id", fileNameWithoutExt,  // ✅ Keep original filename
                "format", "zip"  // ✅ Force .zip extension
        );
    
        Map uploadResult = cloudinary.uploader().upload(file.getBytes(), options);
        return uploadResult.get("secure_url").toString();
    }
    
    
    public String uploadFile(MultipartFile file, String folder) throws IOException {
        Map uploadParams = ObjectUtils.asMap(
            "folder", folder,
            "resource_type", "auto",  // Automatically detect file type
            "type", "upload",  // Ensure file is publicly accessible
            "format", "pdf",   // Force PDF format
            "access_mode", "public"  // Ensure file is public
        );
    
        Map uploadResult = cloudinary.uploader().upload(file.getBytes(), uploadParams);
        System.out.println("Upload Response: " + uploadResult); // Debug response
        return uploadResult.get("secure_url").toString(); // Return public URL
    }
    
    public String uploadFileforProfilePhoto(MultipartFile file) throws IOException {
        Map uploadResult = cloudinary.uploader().upload(file.getBytes(), ObjectUtils.emptyMap());
        return uploadResult.get("url").toString();
    }
    
}
