package com.arise.steiner.client;

import com.arise.steiner.names.Routes;
import com.arise.steiner.dto.CreateNodeRequest;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.web.client.RestTemplate;

public class SteinerAPIClient {

    private final String rootApi;

    private final RestTemplate restTemplate;
    private SecurityMode securityMode = SecurityMode.NONE;
    private String user;
    private String password;


    public SteinerAPIClient(String rootUri){
        this.rootApi = rootUri;
        this.restTemplate = new RestTemplate();
    }


    public NodeDTO createNode(CreateNodeRequest createNodeRequest){
        HttpHeaders headers = new HttpHeaders();
        fillAuthorizationHeader(headers);
        HttpEntity<CreateNodeRequest> request = new HttpEntity<>(createNodeRequest, headers);
        return restTemplate.postForObject(rootApi + Routes.NODES, request, NodeDTO.class);
    }


    private void fillAuthorizationHeader(HttpHeaders headers){
        switch (securityMode){
            case NONE:
                headers.add("Authorization", user);
                break;
            case BASIC_AUTH:
                break;
        }
    }


    public ItemDTO uploadDocument(byte [] bytes, Long documentId, String fileName, String userId, String userDomain){
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.MULTIPART_FORM_DATA);
        fillAuthorizationHeader(headers);



        ByteArrayResource fileAsResource = new ByteArrayResource(bytes) {
            @Override
            public String getFilename() {
                return fileName;
            }
        };

        LinkedMultiValueMap<String, Object> map = new LinkedMultiValueMap<>();
        map.add("file", fileAsResource);


        HttpEntity<LinkedMultiValueMap<String, Object>> entity = new HttpEntity<>(map, headers);
        return restTemplate.exchange(rootApi + "/documents/" + documentId + "/upload",
            HttpMethod.POST,
            entity,
            ItemDTO.class).getBody();
    }


    public SteinerAPIClient security(SecurityMode securityMode) {
        this.securityMode = securityMode;
        return this;
    }

    public SteinerAPIClient auth(String user, String password) {
        this.user = user;
        this.password = password;
        return this;
    }
}
