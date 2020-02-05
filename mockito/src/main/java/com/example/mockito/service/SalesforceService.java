package com.example.mockito.service;

import com.example.mockito.dto.AuthInfo;
import com.example.mockito.dto.EventLog;
import com.example.mockito.dto.Token;
import com.example.mockito.exception.SalesforceErrorResponse;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;
import org.apache.http.HttpResponse;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.utils.URIBuilder;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.message.BasicNameValuePair;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClientResponseException;

import java.io.IOException;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;


@Slf4j
@Component
public class SalesforceService {

    CloseableHttpClient client = HttpClients.createDefault();
    ObjectMapper mapper = new ObjectMapper();
    URIBuilder builder = new URIBuilder("https://ap15.salesforce.com");

    public SalesforceService() throws URISyntaxException {
    }

    public Token getAccessToken(AuthInfo authInfo) throws IOException {
        Token token;
        try {
            builder.setPath("/services/oauth2/token");
            HttpPost post = new HttpPost(builder.build());
            List<BasicNameValuePair> urlParameters = new ArrayList<>();
            urlParameters.add(new BasicNameValuePair("grant_type", authInfo.getGrant_type()));
            urlParameters.add(new BasicNameValuePair("client_id", authInfo.getClient_id()));
            urlParameters.add(new BasicNameValuePair("client_secret", authInfo.getClient_secret()));
            urlParameters.add(new BasicNameValuePair("username", authInfo.getUsername()));
            urlParameters.add(new BasicNameValuePair("password", authInfo.getPassword()));

            post.setEntity(new UrlEncodedFormEntity(urlParameters));
//            post.addHeader("grant_type", authInfo.getGrant_type());
//            post.addHeader("client_id", authInfo.getClient_id());
//            post.addHeader("client_secret", authInfo.getClient_secret());
//            post.addHeader("username", authInfo.getUsername());
//            post.addHeader("password", authInfo.getPassword());
            HttpResponse queryResponse = client.execute(post);
            System.out.println(queryResponse);
            System.out.println(queryResponse.getEntity().getContent());
            JsonNode jsonNode = mapper.readValue(queryResponse.getEntity().getContent(), JsonNode.class);
            System.out.println(jsonNode);
            token = mapper.reader().forType(Token.class).readValue(jsonNode);
            log.info("New Token SuccessFully addded {} ", token);
        } catch (WebClientResponseException ex) {
            log.error("WebClientResponseException - Error Message is : {} , and the Error Response Body is {}", ex, ex.getResponseBodyAsString());
            throw new SalesforceErrorResponse(ex.getStatusText(), ex);
        } catch (Exception ex) {
            log.error("Exception - The Error Message is {} ", ex.getMessage());
            throw new SalesforceErrorResponse(ex);
        }
        return token;
    }

    public String getEventLogFileById(String accessToken, String id) {
        String eventLogFile;
        try {
            builder.setPath("services/data/v34.0/sobjects/EventLogFile/"+id+"/LogFile");
            HttpGet get = new HttpGet(builder.build());
            get.setHeader("Authorization", accessToken);
            HttpResponse queryResponse = client.execute(get);
            eventLogFile = mapper.readValue(queryResponse.getEntity().getContent(),String.class);
            log.info("Get Events File SuccessFully", eventLogFile);
        } catch (WebClientResponseException ex) {
            log.error("WebClientResponseException - Error Message is : {} , and the Error Response Body is {}", ex, ex.getResponseBodyAsString());
            throw new SalesforceErrorResponse(ex.getStatusText(), ex);
        } catch (Exception ex) {
            log.error("Exception - The Error Message is {} ", ex.getMessage());
            throw new SalesforceErrorResponse(ex);
        }
        return eventLogFile;
    }

    public EventLog getListOfEventLogsByQuery(String accessToken, String query) {
        EventLog eventLogFile;
        try {

            builder.setPath("/services/data/v39.0/query")
                    .setParameter("q", query);

            HttpGet get = new HttpGet(builder.build());
            get.setHeader("Authorization", accessToken);
            HttpResponse queryResponse = client.execute(get);
            System.out.println(queryResponse.getEntity().getContent());
            JsonNode jsonNode = mapper.readValue(queryResponse.getEntity().getContent(),JsonNode.class);
            eventLogFile  = mapper.reader().forType(EventLog.class).readValue(jsonNode);
            log.info("Get Events File SuccessFully {}", eventLogFile);
        } catch (WebClientResponseException ex) {
            log.error("WebClientResponseException - Error Message is : {} , and the Error Response Body is {}", ex, ex.getResponseBodyAsString());
            throw new SalesforceErrorResponse(ex.getStatusText(), ex);
        } catch (Exception ex) {
            log.error("Exception - The Error Message is {} ", ex.getMessage());
            throw new SalesforceErrorResponse(ex);
        }
        return eventLogFile;
    }

}
