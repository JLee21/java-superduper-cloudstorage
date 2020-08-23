package com.udacity.jwdnd.course1.cloudstorage.services;

import com.udacity.jwdnd.course1.cloudstorage.mappers.CredentialMapper;
import com.udacity.jwdnd.course1.cloudstorage.models.Credential;
import org.springframework.stereotype.Service;

import java.security.SecureRandom;
import java.util.Base64;
import java.util.List;

@Service
public class CredentialService {
    private final CredentialMapper credentialMapper;
    private EncryptionService encryptionService;

    public CredentialService(CredentialMapper credentialMapper, EncryptionService encryptionService) {
        this.credentialMapper = credentialMapper;
        this.encryptionService = encryptionService;
    }

    public List<Credential> listCredentials(Integer userId) {
        return credentialMapper.listCredentials(userId);
    }

    public Integer addCredential(String url,
                                 String username,
                                 String password,
                                 Integer userId) {

        String secreteKey = this.encryptionService.createSecretKey();
        Integer secretKeyLength = secreteKey.length();

        SecureRandom random = new SecureRandom();
        byte[] key = new byte[16];
        random.nextBytes(key);
        String encodedKey = Base64.getEncoder().encodeToString(key);
        EncryptionService encryptionService = new EncryptionService();
        Integer keyLength = encodedKey.length();

        String encryptedPassword = encryptionService.encryptValue(password, secreteKey);


        Credential newCredential = new Credential(
                username,
                encryptedPassword,
                secreteKey,
                url,
                userId
        );

        return this.credentialMapper.insert(newCredential);
    }

    public void deleteCredential(Integer credentialId) {
        this.credentialMapper.delete(credentialId);
    }

    public void updateCredential(Integer credentialId,
                                 String username,
                                 String password,
                                 String url,
                                 Integer userId) {

        String secreteKey = this.encryptionService.createSecretKey();

//        SecureRandom random = new SecureRandom();
//        byte[] key = new byte[16];
//        random.nextBytes(key);
//        String encodedKey = Base64.getEncoder().encodeToString(key);

        String encryptedPassword = encryptionService.encryptValue(password, secreteKey);
        Credential updatedCredential = new Credential(
                credentialId,
                username,
                encryptedPassword,
                secreteKey,
                url
        );
        credentialMapper.update(updatedCredential);
    }

    public Credential getCredentialById(Integer credentialId) {
        return credentialMapper.getCredentialById(credentialId);
    }
}
