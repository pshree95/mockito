package com.example.mockito.service;

import com.example.mockito.dto.AuthInfo;
import com.example.mockito.dto.EventLog;
import com.example.mockito.dto.Records;
import com.example.mockito.dto.Token;
import com.example.mockito.exception.SalesforceErrorResponse;
import org.junit.Assert;
import org.junit.Test;
import org.junit.jupiter.api.Assertions;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;
import java.io.IOException;


import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class SalesforceServiceMockTest {

    @InjectMocks
    private SalesforceService salesforceService;

    @Mock
    AuthInfo info;

    @Mock
    EventLog eventLog;

    @Mock
    Records records;

    @Mock
    Token token;

    @Test
    public void getAccessToken_basic() throws IOException {
        when(info.getGrant_type()).thenReturn("password");
        when(info.getClient_id()).thenReturn("3MVG9G9pzCUSkzZvucD1Q_.bNfx7RyJE1iKS8eWQbw4oHElPb24n79wJ_M8W2M3ByZdme2jWun.VM7O6n2jCt");
        when(info.getClient_secret()).thenReturn("6DB41AF9E52DEF798C7AF8422B9840DDEA7DAE06A84067FC3EB8E99EE6D1A118");
        when(info.getUsername()).thenReturn("parul.shree@sacumen.com");
        when(info.getPassword()).thenReturn("Sacumen@1315");
        Token token = salesforceService.getAccessToken(info);
        Assert.assertTrue(token!=null);
    }

    @Test
    public void getEventLogFileById_basic() {
        when(token.getAccessToken()).thenReturn("Bearer 00D2v000002jvX5!AREAQLfmw2bqigVuZuDpb_YR8GbjSvv1aGim3uRt.QU5gEXp3bHmg23n.NKIMwRWRY7XyXX5PI9zbeFOPZfG7ebg481Xqf18");
        when(records.getId()).thenReturn("0AT2v00000v16uuGAA");
        String eventLog = salesforceService.getEventLogFileById(token.getAccessToken(), records.getId());
        Assertions.assertTrue(eventLog!=null);
    }

    @Test
    public void getListOfEventLogsByQuery_basic() {
        String query = "SELECT Id, EventType from EventLogFile";
        when(token.getAccessToken()).thenReturn("Bearer 00D2v000002jvX5!AREAQLfmw2bqigVuZuDpb_YR8GbjSvv1aGim3uRt.QU5gEXp3bHmg23n.NKIMwRWRY7XyXX5PI9zbeFOPZfG7ebg481Xqf18");
        EventLog eventLog = salesforceService.getListOfEventLogsByQuery(token.getAccessToken(), query);
        Assert.assertTrue(eventLog!=null);
    }

    @Test
    public void getAccessTokenNotFound() {
        when(info.getGrant_type()).thenReturn("password");
        when(info.getClient_id()).thenReturn("null");
        when(info.getClient_secret()).thenReturn("6DB41AF9E52DEF798C7AF8422B9840DDEA7DAE06A84067FC3EB8E99EE6D1A118");
        when(info.getUsername()).thenReturn("parul.shree@sacumen.com");
        when(info.getPassword()).thenReturn("Sacumen@1315");
        Assertions.assertThrows(SalesforceErrorResponse.class, () -> salesforceService.getAccessToken(info));
    }

    @Test
    public void getEventLogFileByIdNotFound() {
        when(token.getAccessToken()).thenReturn("null");
        when(records.getId()).thenReturn("0AT2v00000v16uuGAA");
        Assertions.assertThrows(SalesforceErrorResponse.class, () -> salesforceService.getEventLogFileById(token.getAccessToken(), records.getId()));
    }

    @Test
    public void getListOfEventLogsByQueryNotFound() {
        when(token.getAccessToken()).thenReturn("null");
        String query = "SELECT+Id+,+EventType+from+EventLogFildsfes";
        Assertions.assertThrows(SalesforceErrorResponse.class, () -> salesforceService.getListOfEventLogsByQuery(token.getAccessToken(), query));

    }
}
