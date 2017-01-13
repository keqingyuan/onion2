package org.onion.expands.request;

import org.onion.expands.request.email.EmailRequest;
import org.onion.expands.request.ftp.FtpRequest;
import org.onion.expands.request.http.HttpRequest;
import org.onion.expands.request.webservice.WebServiceRequestBuilder;
import org.onion.expands.request.websocket.WebSocketRequest;

import java.io.IOException;

public interface RequestBuilder {
    HttpRequest http(String url);

    HttpRequest https(String url);

    FtpRequest ftp(String host, int port, String username, String password) throws IOException;

    FtpRequest ftp(String host, int port) throws IOException;

    FtpRequest ftp(String host) throws IOException;

    WebServiceRequestBuilder webService() throws Exception;

    EmailRequest email();

    WebSocketRequest webSocket(String url);

}
