package com.opl.retail.client.dms;

import com.opl.retail.api.exceptions.dms.DocumentException;
import com.opl.retail.api.model.dms.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.multipart.MultipartFile;

/**
 * Created by dhaval on 29-Apr-17.
 */
public class DMSClient {

    private static final Logger logger = LoggerFactory.getLogger(DMSClient.class);

    private static final String UPLOAD_FILE = "/uploadFile";
    private static final String UPLOAD_MULTIPLE_FILE = "/uploadMultipleFile";
    private static final String UPLOAD_FILE_BY_PROPOSAL_ID = "/uploadFileByProposalId";
    private static final String PRODUCT_UPLOAD_IMAGE = "/productImage";
    private static final String PRODUCT_LIST_DOCUMENT = "/listProductDocument";
    private static final String PRODUCT_LIST_DOCUMENT_PROPOSAL_ID = "/listProductDocumentByProposalId";
    private static final String PRODUCT_LIST_DOCUMENT_BANK_USER = "/listProductDocument/BankUser";
    private static final String PRODUCT_LIST_DOCUMENT_BY_MULTI_PRODOCMAP_ID = "/listProDocByMultiProMapId";
    private static final String PRODUCT_DELETE_DOCUMENT = "/deleteProduct";
    private static final String PRODUCT_DELETE_DOCUMENT_ALL = "/deleteAllProduct";
    private static final String PRODUCT_LIST_UPLOAD = "/listProductUpload";
    private static final String PRODUCT_READ_EXCEL = "/readExcel";
    private static final String PRODUCT_READ_EXCEL_BY_PROPOSAL_ID = "/readExcel";
    private static final String PRODUCT_STORAGE_PATH = "/getStoragePath";
    private static final String PRODUCT_DOWNLOAD_DOCUMENT = "/productDownloadDocument";

    private static final String USER_UPLOAD_IMAGE = "/uploadImage";
    private static final String USER_UPLOAD_FILE = "/uploadUserFile";
    private static final String USER_LIST_UPLOAD = "/listUserUpload";
    private static final String USER_LIST_DOCUMENT = "/listUserDocument";
    private static final String USER_DELETE_DOCUMENT = "/deleteUser";
    private static final String USER_DOWNLOAD_DOCUMENT = "/userDownloadDocument";


    private static final String UPLOAD_IRR_DOCUMENT = "/uploadIrrDocument";
    private static final String LIST_IRR_DOCUMENT = "/listIrrDocument";
    private static final String DELETE_IRR_DOCUMENT = "/deleteIrrDocument";
    private static final String IMPORT_DOCUMENT = "/importDocument";
    private static final String PRODUCT_LOCAL_PATH = "/getLocalPath";
    private static final String ZIP_RESPONSE = "/generateZipFile";
    private static final String MOVE_OBJECT = "/moveObject";

    private static final String UPLOAD_REQUEST = "uploadRequest";
    private static final String DMS_SERVICE_IS_NOT_AVAILABLE = "Error While calling DMS Service";


    private static final String PRODUCT_DOWNLOAD_DOCUMENT_FOR_MCA = "/productDownloadDocumentForMCA";
    private static final String REQ_AUTH = "req_auth";

    private String BASE_URL;
    private RestTemplate restTemplate;

    public DMSClient(String url) {
        this.BASE_URL = url;
        restTemplate = new RestTemplate();
    }

    /*Irr Client Methods*/
    public DocumentResponse uploadIrrDocument(String jsonString, MultipartFile multipartFiles) throws DocumentException {
        String url = BASE_URL.concat(UPLOAD_IRR_DOCUMENT);
        try {
            HttpHeaders headers = new HttpHeaders();
            headers.set(REQ_AUTH, "true");
            headers.setContentType(MediaType.MULTIPART_FORM_DATA);
            MultiValueMap<String, Object> map = new LinkedMultiValueMap<>();
            ByteArrayResource contentsAsResource = new ByteArrayResource(multipartFiles.getBytes()) {
                @Override
                public String getFilename() {
                    return "";
                }
            };
            map.add("file", contentsAsResource);
            map.add(UPLOAD_REQUEST, jsonString);
            HttpEntity<MultiValueMap<String, Object>> request = new HttpEntity<>(map, headers);
            return restTemplate.postForObject(url, request, DocumentResponse.class);
        } catch (Exception e) {
            logger.error("Throw Exception While listIrrDocument exception {}", e);
            throw new DocumentException(DMS_SERVICE_IS_NOT_AVAILABLE);
        }
    }

    public DocumentResponse listIrrDocument(DocumentRequest documentRequest) throws DocumentException {
        String url = BASE_URL.concat(LIST_IRR_DOCUMENT);
        try {
            HttpHeaders headers = new HttpHeaders();
            headers.setContentType(MediaType.APPLICATION_JSON);
            headers.set(REQ_AUTH, "true");
            HttpEntity<DocumentRequest> entity = new HttpEntity<>(documentRequest, headers);
            return restTemplate.exchange(url, HttpMethod.POST, entity, DocumentResponse.class).getBody();
        } catch (Exception e) {
            logger.error("Throw Exception While uploadIrrDocument exception {}", e);
            throw new DocumentException(DMS_SERVICE_IS_NOT_AVAILABLE);
        }
    }

    public DocumentResponse deleteIrrDocument(String jsonString) throws DocumentException {
        String url = BASE_URL.concat(DELETE_IRR_DOCUMENT);
        try {
            HttpHeaders headers = new HttpHeaders();
            headers.setContentType(MediaType.APPLICATION_JSON);
            headers.set(REQ_AUTH, "true");
            HttpEntity<String> entity = new HttpEntity<>(jsonString, headers);
            return restTemplate.exchange(url, HttpMethod.POST, entity, DocumentResponse.class).getBody();
        } catch (Exception e) {
            logger.error("Throw Exception While deleteIrrDocument exception {}", e);
            throw new DocumentException(DMS_SERVICE_IS_NOT_AVAILABLE);
        }
    }
   /*Irr Client Methods*/

    /*User Client Methods*/
    public DocumentResponse uploadImage(String jsonString, MultipartFile multipartFiles) throws DocumentException {
        String url = BASE_URL.concat(USER_UPLOAD_IMAGE);
        try {
            HttpHeaders headers = new HttpHeaders();
            headers.set(REQ_AUTH, "true");
            headers.setContentType(MediaType.MULTIPART_FORM_DATA);
            MultiValueMap<String, Object> map = new LinkedMultiValueMap<>();
            ByteArrayResource contentsAsResource = new ByteArrayResource(multipartFiles.getBytes()) {
                @Override
                public String getFilename() {
                    return "";
                }
            };
            map.add("file", contentsAsResource);
            map.add(UPLOAD_REQUEST, jsonString);
            HttpEntity<MultiValueMap<String, Object>> request = new HttpEntity<>(map, headers);
            return restTemplate.postForObject(url, request, DocumentResponse.class);
        } catch (Exception e) {
            logger.error("Throw Exception While uploadImage exception {}", e);
            throw new DocumentException(DMS_SERVICE_IS_NOT_AVAILABLE);
        }
    }

    public DocumentResponse uploadUserFile(String jsonString, MultipartFile multipartFiles) throws DocumentException {
        String url = BASE_URL.concat(USER_UPLOAD_FILE);
        try {
            HttpHeaders headers = new HttpHeaders();
            headers.set(REQ_AUTH, "true");
            headers.setContentType(MediaType.MULTIPART_FORM_DATA);
            MultiValueMap<String, Object> map = new LinkedMultiValueMap<>();
            ByteArrayResource contentsAsResource = new ByteArrayResource(multipartFiles.getBytes()) {
                @Override
                public String getFilename() {
                    return "";
                }
            };
            map.add("file", contentsAsResource);
            map.add(UPLOAD_REQUEST, jsonString);
            HttpEntity<MultiValueMap<String, Object>> request = new HttpEntity<>(map, headers);
            return restTemplate.postForObject(url, request, DocumentResponse.class);
        } catch (Exception e) {
            logger.error("Throw Exception While uploadUserFile exception {}", e);
            throw new DocumentException(DMS_SERVICE_IS_NOT_AVAILABLE);
        }
    }

    public DocumentResponse listUserUpload(String jsonString) throws DocumentException {
        String url = BASE_URL.concat(USER_LIST_UPLOAD);
        try {
            HttpHeaders headers = new HttpHeaders();
            headers.set(REQ_AUTH, "true");
            headers.setContentType(MediaType.TEXT_PLAIN);
            HttpEntity<String> entity = new HttpEntity<>(jsonString, headers);
            return restTemplate.exchange(url, HttpMethod.POST, entity, DocumentResponse.class).getBody();
        } catch (Exception e) {
            logger.error("Throw Exception While listUserUpload exception {}", e);
            throw new DocumentException(DMS_SERVICE_IS_NOT_AVAILABLE);
        }
    }

    public DocumentResponse listUserDocument(DocumentRequest documentRequest) throws DocumentException {
        String url = BASE_URL.concat(USER_LIST_DOCUMENT);
        try {
            HttpHeaders headers = new HttpHeaders();
            headers.set(REQ_AUTH, "true");
            headers.setContentType(MediaType.APPLICATION_JSON);
            HttpEntity<DocumentRequest> entity = new HttpEntity<>(documentRequest, headers);
            return restTemplate.exchange(url, HttpMethod.POST, entity, DocumentResponse.class).getBody();
        } catch (Exception e) {
            logger.error("Throw Exception While listUserDocument exception {}", e);
            throw new DocumentException(DMS_SERVICE_IS_NOT_AVAILABLE);
        }
    }

    public DocumentResponse deleteUserDocument(String jsonString) throws DocumentException {
        String url = BASE_URL.concat(USER_DELETE_DOCUMENT);
        try {
            HttpHeaders headers = new HttpHeaders();
            headers.set(REQ_AUTH, "true");
            headers.setContentType(MediaType.TEXT_PLAIN);
            HttpEntity<String> entity = new HttpEntity<>(jsonString, headers);
            return restTemplate.exchange(url, HttpMethod.POST, entity, DocumentResponse.class).getBody();
        } catch (Exception e) {
            logger.error("Throw Exception While deleteUser exception {}", e);
            throw new DocumentException(DMS_SERVICE_IS_NOT_AVAILABLE);
        }
    }

    public DocumentResponse uploadFileByProposalId(String jsonString, MultipartFile multipartFiles) throws DocumentException {
        String url = BASE_URL.concat(UPLOAD_FILE_BY_PROPOSAL_ID);
        try {
            HttpHeaders headers = new HttpHeaders();
            headers.set(REQ_AUTH, "true");
            headers.setContentType(MediaType.MULTIPART_FORM_DATA);
            MultiValueMap<String, Object> map = new LinkedMultiValueMap<String, Object>();
            ByteArrayResource contentsAsResource = new ByteArrayResource(multipartFiles.getBytes()) {
                @Override
                public String getFilename() {
                    return "";
                }
            };
            map.add("file", contentsAsResource);
            map.add("uploadRequest", jsonString);
            HttpEntity<MultiValueMap<String, Object>> request = new HttpEntity<MultiValueMap<String, Object>>(map, headers);
            DocumentResponse documentResponse = restTemplate.postForObject(url, request, DocumentResponse.class);
            return documentResponse;
        } catch (Exception e) {
            logger.error("Throw Exception While uploadFileByProposalId exception {}", e);
            throw new DocumentException("DMS Service is not available");
        }
    }

    public DocumentResponse uploadFile(String jsonString, MultipartFile multipartFiles) throws DocumentException {
        String url = BASE_URL.concat(UPLOAD_FILE);
        try {
            HttpHeaders headers = new HttpHeaders();
            headers.set(REQ_AUTH, "true");
            headers.setContentType(MediaType.MULTIPART_FORM_DATA);
            headers.setContentLength(multipartFiles.getSize());
            MultiValueMap<String, Object> map = new LinkedMultiValueMap<>();
            ByteArrayResource contentsAsResource = new ByteArrayResource(multipartFiles.getBytes()) {
                @Override
                public String getFilename() {
                    return "";
                }
            };
            map.add("file", contentsAsResource);
            map.add(UPLOAD_REQUEST, jsonString);
            HttpEntity<MultiValueMap<String, Object>> request = new HttpEntity<MultiValueMap<String, Object>>(map, headers);
            return restTemplate.postForObject(url, request, DocumentResponse.class);
        } catch (Exception e) {
            logger.error("Throw Exception While uploadFile exception {}", e);
            throw new DocumentException(DMS_SERVICE_IS_NOT_AVAILABLE);
        }
    }

    /*Product Client Methods*/
    public DocumentResponse uploadMultipleFile(String jsonString, MultipartFile[] multipartFiles) throws DocumentException {
        String url = BASE_URL.concat(UPLOAD_MULTIPLE_FILE);
        try {
            HttpHeaders headers = new HttpHeaders();
            headers.set(REQ_AUTH, "true");
            headers.setContentType(MediaType.MULTIPART_FORM_DATA);
            LinkedMultiValueMap<String, Object> map = new LinkedMultiValueMap<String, Object>();
            for (final MultipartFile multipartfile : multipartFiles) {
                LinkedMultiValueMap<String, String> pdfHeaderMap = new LinkedMultiValueMap<>();
                pdfHeaderMap.add("Content-disposition", "form-data; name=file; filename=" + multipartfile.getOriginalFilename());
                HttpEntity<byte[]> doc = new HttpEntity<byte[]>(multipartfile.getBytes(), pdfHeaderMap);
                map.add("file", doc);
            }
            map.add(UPLOAD_REQUEST, jsonString);
            HttpEntity<LinkedMultiValueMap<String, Object>> request = new HttpEntity<>(map, headers);
            return restTemplate.postForObject(url, request, DocumentResponse.class);
        } catch (Exception e) {
            logger.error("Throw Exception WHile Upload File" + e.getMessage(), e);
            throw new DocumentException(DMS_SERVICE_IS_NOT_AVAILABLE);
        }
    }


    public DocumentResponse readExcel(String jsonString, MultipartFile multipartFiles) throws DocumentException {
        String url = BASE_URL.concat(PRODUCT_READ_EXCEL);
        try {
            HttpHeaders headers = new HttpHeaders();
            headers.set(REQ_AUTH, "true");
            headers.setContentType(MediaType.MULTIPART_FORM_DATA);
            MultiValueMap<String, Object> map = new LinkedMultiValueMap<>();
            ByteArrayResource contentsAsResource = new ByteArrayResource(multipartFiles.getBytes()) {
                @Override
                public String getFilename() {
                    return "";
                }
            };
            map.add("file", contentsAsResource);
            map.add(UPLOAD_REQUEST, jsonString);
            HttpEntity<MultiValueMap<String, Object>> request = new HttpEntity<MultiValueMap<String, Object>>(map, headers);
            return restTemplate.postForObject(url, request, DocumentResponse.class);
        } catch (Exception e) {
            logger.error("Throw Exception While Upload File exception {}", e);
            throw new DocumentException("DMS Service is not available");
        }
    }

    public DocumentResponse readExcelByProposalId(String jsonString, MultipartFile multipartFiles) throws DocumentException {
        String url = BASE_URL.concat(PRODUCT_READ_EXCEL_BY_PROPOSAL_ID);
        try {
            HttpHeaders headers = new HttpHeaders();
            headers.set(REQ_AUTH, "true");
            headers.setContentType(MediaType.MULTIPART_FORM_DATA);
            MultiValueMap<String, Object> map = new LinkedMultiValueMap<String, Object>();
            ByteArrayResource contentsAsResource = new ByteArrayResource(multipartFiles.getBytes()) {
                @Override
                public String getFilename() {
                    return "";
                }
            };
            map.add("file", contentsAsResource);
            map.add("uploadRequest", jsonString);
            HttpEntity<MultiValueMap<String, Object>> request = new HttpEntity<MultiValueMap<String, Object>>(map, headers);
            DocumentResponse documentResponse = restTemplate.postForObject(url, request, DocumentResponse.class);
            return documentResponse;
        } catch (Exception e) {
            logger.error("Throw Exception While readExcel exception {}", e);
            throw new DocumentException("DMS Service is not available");
        }
    }

    public DocumentResponse productImage(String jsonString, MultipartFile multipartFiles) throws DocumentException {
        String url = BASE_URL.concat(PRODUCT_UPLOAD_IMAGE);
        try {
            HttpHeaders headers = new HttpHeaders();
            headers.set(REQ_AUTH, "true");
            headers.setContentType(MediaType.MULTIPART_FORM_DATA);
            MultiValueMap<String, Object> map = new LinkedMultiValueMap<>();
            ByteArrayResource contentsAsResource = new ByteArrayResource(multipartFiles.getBytes()) {
                @Override
                public String getFilename() {
                    return "";
                }
            };
            map.add("file", contentsAsResource);
            map.add(UPLOAD_REQUEST, jsonString);
            HttpEntity<MultiValueMap<String, Object>> request = new HttpEntity<MultiValueMap<String, Object>>(map, headers);
            return restTemplate.postForObject(url, request, DocumentResponse.class);
        } catch (Exception e) {
            logger.error("Throw Exception While productImage exception {}", e);
            throw new DocumentException(DMS_SERVICE_IS_NOT_AVAILABLE);
        }
    }

    public DocumentResponse listProductUpload(String jsonString) throws DocumentException {
        String url = BASE_URL.concat(PRODUCT_LIST_UPLOAD);
        try {
            HttpHeaders headers = new HttpHeaders();
            headers.set(REQ_AUTH, "true");
            headers.setContentType(MediaType.TEXT_PLAIN);
            HttpEntity<String> entity = new HttpEntity<>(jsonString, headers);
            return restTemplate.exchange(url, HttpMethod.POST, entity, DocumentResponse.class).getBody();
        } catch (Exception e) {
            logger.error("Throw Exception While listProductUpload exception {}", e);
            throw new DocumentException(DMS_SERVICE_IS_NOT_AVAILABLE);
        }
    }

    public DocumentResponse listProductDocumentByProposalId(DocumentRequest documentRequest) throws DocumentException {
        String url = BASE_URL.concat(PRODUCT_LIST_DOCUMENT_PROPOSAL_ID);
        try {
            HttpHeaders headers = new HttpHeaders();
            headers.set(REQ_AUTH, "true");
            headers.setContentType(MediaType.APPLICATION_JSON);
            HttpEntity<DocumentRequest> entity = new HttpEntity<DocumentRequest>(documentRequest, headers);
            return restTemplate.exchange(url, HttpMethod.POST, entity, DocumentResponse.class).getBody();
        } catch (Exception e) {
            logger.error("Throw Exception While listProductDocumentByProposalId exception {}", e);
            throw new DocumentException("DMS Service is not available");
        }
    }

    public DocumentResponse listProductDocument(DocumentRequest documentRequest) throws DocumentException {
        String url = BASE_URL.concat(PRODUCT_LIST_DOCUMENT);
        try {
            HttpHeaders headers = new HttpHeaders();
            headers.set(REQ_AUTH, "true");
            headers.setContentType(MediaType.APPLICATION_JSON);
            HttpEntity<DocumentRequest> entity = new HttpEntity<>(documentRequest, headers);
            return restTemplate.exchange(url, HttpMethod.POST, entity, DocumentResponse.class).getBody();
        } catch (Exception e) {
            logger.error("Throw Exception While listProductDocument exception {}", e);
            throw new DocumentException(DMS_SERVICE_IS_NOT_AVAILABLE);
        }
    }

    public DocumentResponse listProductDocumentCAMReport(DocumentRequest documentRequest) throws DocumentException {
        String url = BASE_URL.concat(PRODUCT_LIST_DOCUMENT);
        try {
            HttpHeaders headers = new HttpHeaders();
            headers.set(REQ_AUTH, "true");
            headers.setContentType(MediaType.APPLICATION_JSON);
            HttpEntity<DocumentRequest> entity = new HttpEntity<>(documentRequest, headers);
            return restTemplate.exchange(url, HttpMethod.POST, entity, DocumentResponse.class).getBody();
        } catch (Exception e) {
            logger.error("Throw Exception While listProductDocument exception {}", e);
            throw new DocumentException(DMS_SERVICE_IS_NOT_AVAILABLE);
        }
    }


    public DocumentResponse listProductDocumentBankUser(DocumentRequest documentRequest) throws DocumentException {
        String url = BASE_URL.concat(PRODUCT_LIST_DOCUMENT_BANK_USER);
        try {
            HttpHeaders headers = new HttpHeaders();
            headers.set(REQ_AUTH, "true");
            headers.setContentType(MediaType.APPLICATION_JSON);
            HttpEntity<DocumentRequest> entity = new HttpEntity<>(documentRequest, headers);
            return restTemplate.exchange(url, HttpMethod.POST, entity, DocumentResponse.class).getBody();
        } catch (Exception e) {
            logger.error("Throw Exception While listProductDocument/BankUser exception {}", e);
            throw new DocumentException(DMS_SERVICE_IS_NOT_AVAILABLE);
        }
    }

    public DocumentResponse listProDocByMultiProMapId(DocumentRequest documentRequest) throws DocumentException {
        String url = BASE_URL.concat(PRODUCT_LIST_DOCUMENT_BY_MULTI_PRODOCMAP_ID);
        try {
            HttpHeaders headers = new HttpHeaders();
            headers.set(REQ_AUTH, "true");
            headers.setContentType(MediaType.APPLICATION_JSON);
            HttpEntity<DocumentRequest> entity = new HttpEntity<>(documentRequest, headers);
            return restTemplate.exchange(url, HttpMethod.POST, entity, DocumentResponse.class).getBody();
        } catch (Exception e) {
            logger.error("Throw Exception While listProDocByMultiProMapId exception {}", e);
            throw new DocumentException(DMS_SERVICE_IS_NOT_AVAILABLE);
        }
    }

    public DocumentResponse deleteProductDocument(String jsonString) throws DocumentException {
        String url = BASE_URL.concat(PRODUCT_DELETE_DOCUMENT);
        try {
            HttpHeaders headers = new HttpHeaders();
            headers.set(REQ_AUTH, "true");
            headers.setContentType(MediaType.TEXT_PLAIN);
            HttpEntity<String> entity = new HttpEntity<>(jsonString, headers);
            return restTemplate.exchange(url, HttpMethod.POST, entity, DocumentResponse.class).getBody();
        } catch (Exception e) {
            logger.error("Throw Exception While deleteProduct exception {}", e);
            throw new DocumentException(DMS_SERVICE_IS_NOT_AVAILABLE);
        }
    }

    public DocumentResponse deleteProductDocumentFromApplicationId(String jsonString) throws DocumentException {
        String url = BASE_URL.concat(PRODUCT_DELETE_DOCUMENT_ALL);
        try {
            HttpHeaders headers = new HttpHeaders();
            headers.set(REQ_AUTH, "true");
            headers.setContentType(MediaType.TEXT_PLAIN);
            HttpEntity<String> entity = new HttpEntity<>(jsonString, headers);
            return restTemplate.exchange(url, HttpMethod.POST, entity, DocumentResponse.class).getBody();
        } catch (Exception e) {
            logger.error("Throw Exception While deleteAllProduct exception {}", e);
            throw new DocumentException("Can't Delete " + e);
        }
    }


    public DocumentResponse getStoragePath(String jsonString) throws DocumentException {
        String url = BASE_URL.concat(PRODUCT_STORAGE_PATH);
        try {
            HttpHeaders headers = new HttpHeaders();
            headers.set(REQ_AUTH, "true");
            headers.setContentType(MediaType.TEXT_PLAIN);
            HttpEntity<String> entity = new HttpEntity<>(jsonString, headers);
            return restTemplate.exchange(url, HttpMethod.POST, entity, DocumentResponse.class).getBody();
        } catch (Exception e) {
            logger.error("Throw Exception While getStoragePath exception {}", e);
            throw new DocumentException(DMS_SERVICE_IS_NOT_AVAILABLE);
        }
    }

    /*Product Client Methods*/
    public Resource productDownloadDocument(Long storageId) throws DocumentException {
        String url = BASE_URL.concat(PRODUCT_DOWNLOAD_DOCUMENT + "/" + storageId);
        try {
            HttpHeaders headers = new HttpHeaders();
            headers.set(REQ_AUTH, "true");
            HttpEntity<?> entity = new HttpEntity<Object>(headers);
            return restTemplate.exchange(url, HttpMethod.GET, entity, Resource.class).getBody();
        } catch (Exception e) {
            logger.error("Throw Exception While productDownloadDocument exception {}", e);
            throw new DocumentException(DMS_SERVICE_IS_NOT_AVAILABLE);
        }
    }

    public Resource userDownloadDocument(Long storageId) throws DocumentException {
        String url = BASE_URL.concat(USER_DOWNLOAD_DOCUMENT + "/" + storageId);
        try {
            HttpHeaders headers = new HttpHeaders();
            headers.set(REQ_AUTH, "true");
            HttpEntity<?> entity = new HttpEntity<Object>(headers);
            return restTemplate.exchange(url, HttpMethod.GET, entity, Resource.class).getBody();
        } catch (Exception e) {
            logger.error("Throw Exception While userDownloadDocument exception {}", e);
            throw new DocumentException(DMS_SERVICE_IS_NOT_AVAILABLE);
        }
    }

    public DocumentResponse importDocument(DocumentImportRequest documentImportRequest) throws DocumentException {
        String url = BASE_URL.concat(IMPORT_DOCUMENT);
        try {
            HttpHeaders headers = new HttpHeaders();
            headers.set(REQ_AUTH, "true");
            headers.setContentType(MediaType.APPLICATION_JSON);
            HttpEntity<DocumentImportRequest> entity = new HttpEntity<>(documentImportRequest, headers);
            return restTemplate.exchange(url, HttpMethod.POST, entity, DocumentResponse.class).getBody();
        } catch (Exception e) {
            logger.error("Throw Exception While importDocument exception {}", e);
            throw new DocumentException(DMS_SERVICE_IS_NOT_AVAILABLE);
        }
    }

    public DocumentResponse getLocalPath(DocumentImportRequest documentImportRequest) throws DocumentException {
        String url = BASE_URL.concat(PRODUCT_LOCAL_PATH);
        try {
            HttpHeaders headers = new HttpHeaders();
            headers.set(REQ_AUTH, "true");
            headers.setContentType(MediaType.APPLICATION_JSON);
            HttpEntity<DocumentImportRequest> entity = new HttpEntity<>(documentImportRequest, headers);
            return restTemplate.exchange(url, HttpMethod.POST, entity, DocumentResponse.class).getBody();
        } catch (Exception e) {
            logger.error("Throw Exception While getLocalPath exception {}", e);
            throw new DocumentException(DMS_SERVICE_IS_NOT_AVAILABLE);
        }
    }

    public DocumentResponse getGenerateZip(ZipRequest zipReq) throws DocumentException {
        String url = BASE_URL.concat(ZIP_RESPONSE);
        try {
            HttpHeaders headers = new HttpHeaders();
            headers.set(REQ_AUTH, "true");
            headers.setContentType(MediaType.APPLICATION_JSON);
            HttpEntity<ZipRequest> entity = new HttpEntity<>(zipReq, headers);
            return restTemplate.exchange(url, HttpMethod.POST, entity, DocumentResponse.class).getBody();
        } catch (Exception e) {
            logger.error("Throw Exception While generateZipFile exception {}", e);
            throw new DocumentException(DMS_SERVICE_IS_NOT_AVAILABLE);
        }
    }

    /*MCA Download File Client Methods*/
    public DmsMcaResponse downloadDocumentForMCA(DmsMcaRequest mcaRequest) throws DocumentException {
        String url = BASE_URL.concat(PRODUCT_DOWNLOAD_DOCUMENT_FOR_MCA);
        try {

            HttpHeaders headers = new HttpHeaders();
            headers.set(REQ_AUTH, "true");
            headers.setContentType(MediaType.APPLICATION_JSON);
            HttpEntity<DmsMcaRequest> entity = new HttpEntity<>(mcaRequest, headers);
            return restTemplate.exchange(url, HttpMethod.POST, entity, DmsMcaResponse.class).getBody();
        } catch (Exception e) {
            logger.error("Throw Exception While downloadDocumentForMCA exception {}", e);
            throw new DocumentException(DMS_SERVICE_IS_NOT_AVAILABLE);
        }
    }

    /**
     * <p>Move file from SFTP server to our main bucket and return true if file successfully moved.</p>
     *
     * @param DmsSftpFileMoveRequest
     * @return boolean in response data
     * @throws DocumentException
     * @author nilay.darji
     */
    public DocumentResponse moveObjectFromSftpToBucket(DmsSftpFileMoveRequest dmsSftpFileMoveRequest) throws DocumentException {
        String url = BASE_URL.concat(MOVE_OBJECT);
        try {
            HttpHeaders headers = new HttpHeaders();
            headers.set(REQ_AUTH, "true");
            headers.setContentType(MediaType.APPLICATION_JSON);
            HttpEntity<DmsSftpFileMoveRequest> entity = new HttpEntity<>(dmsSftpFileMoveRequest, headers);
            return restTemplate.exchange(url, HttpMethod.POST, entity, DocumentResponse.class).getBody();
        } catch (Exception e) {
            logger.error("Throw Exception While moveObjectFromSftpToBucket exception {}", e);
            throw new DocumentException(DMS_SERVICE_IS_NOT_AVAILABLE);
        }
    }
}
