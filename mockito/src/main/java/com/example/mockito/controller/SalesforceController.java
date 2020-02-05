package com.example.mockito.controller;

import com.example.mockito.dto.Attributes;
import com.example.mockito.dto.EventLog;
import com.example.mockito.dto.Records;
import com.example.mockito.dto.Token;
import com.example.mockito.service.SalesforceService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.net.UnknownHostException;
import java.util.ArrayList;
import java.util.List;

@RestController
public class SalesforceController {

    private static final Logger logger = LoggerFactory.getLogger(SalesforceService.class);

    @RequestMapping(value = "/services/oauth2/token", method = RequestMethod.POST)
    public Token getToken(){
        Token token = new Token(
                "00D2v000002jvX5!AREAQLfmw2bqigVuZuDpb_YR8GbjSvv1aGim3uRtQU5gEXp3bHmg23nNKIMwRWRY7XyXX5PI9zbeFOPZfG7ebg481Xqf18",
                "https://ap15.salesforce.com",
                "https://login.salesforce.com/id/00D2v000002jvX5EAI/0052v00000fwXsGAAU",
                "Bearer", "1580888145994", "DtIYnDVhv8ybCH8OQiXyHYTkz7sZVboVPtSCYPiEkYI=");

        logger.info("sending access token response...");
        return token;
    }

    @RequestMapping(value = "/services/data/v46.0/query", method = RequestMethod.GET)
    public EventLog getEventLogFile(){
        List<Records> list = new ArrayList<>();
        Records record1 = new Records(new Attributes("EventLogFile", "/services/data/v39.0/sobjects/EventLogFile/0AT2v00000v16uuGAA"),
                "0AT2v00000v16uuGAA", "Login");
        Records record2 = new Records(new Attributes("EventLogFile", "/services/data/v39.0/sobjects/EventLogFile/0AT2v00000v16vFGAQ"),
                "0AT2v00000v16vFGAQ", "Logout");
        list.add(record1);
        list.add(record2);
        EventLog eventLog = new EventLog(2, true, list);

        logger.info("Sending File Response...");
        return eventLog;
    }
}
