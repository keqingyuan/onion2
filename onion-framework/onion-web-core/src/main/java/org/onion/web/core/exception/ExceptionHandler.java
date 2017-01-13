package org.onion.web.core.exception;

import org.onion.web.core.message.ResponseMessage;

public interface ExceptionHandler {

   <T extends Throwable> boolean support(Class<T> e);

    ResponseMessage handle(Throwable e);
}