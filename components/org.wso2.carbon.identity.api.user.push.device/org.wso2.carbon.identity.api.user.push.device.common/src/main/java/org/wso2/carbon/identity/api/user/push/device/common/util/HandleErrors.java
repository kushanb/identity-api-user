/*
 * Copyright (c) 2019, WSO2 Inc. (http://www.wso2.org) All Rights Reserved.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package org.wso2.carbon.identity.api.user.push.device.common.util;

import com.fasterxml.jackson.core.JsonProcessingException;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.wso2.carbon.identity.api.user.common.error.APIError;
import org.wso2.carbon.identity.api.user.common.error.ErrorResponse;
import org.wso2.carbon.identity.application.authenticator.push.device.handler.exception.PushDeviceHandlerClientException;
import org.wso2.carbon.identity.application.authenticator.push.device.handler.exception.PushDeviceHandlerServerException;
import org.wso2.carbon.user.api.UserStoreException;
import java.io.UnsupportedEncodingException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.security.SignatureException;
import java.sql.SQLException;
import javax.mail.AuthenticationFailedException;
import javax.ws.rs.core.Response;

/**
 * The class which handles API errors.
 */
public class HandleErrors {
    private static final Log log = LogFactory.getLog(HandleErrors.class);

    public static APIError handleException(Exception e, Constants.ErrorMessages errorEnum,
                                       String... data) {
        ErrorResponse errorResponse;
        if (data != null) {
            errorResponse = getErrorBuilder(errorEnum).build(log, e, String.format(errorEnum.getDescription(),
                    (Object[]) data));
        } else {
            errorResponse = getErrorBuilder(errorEnum).build(log, e, errorEnum.getDescription());
        }
        if (e instanceof AuthenticationFailedException) {
            return handleError(Response.Status.UNAUTHORIZED, Constants.ErrorMessages.
                    ERROR_CODE_GET_DEVICE_SERVER_ERROR);
        } else if (e instanceof UserStoreException) {
            return new APIError(Response.Status.INTERNAL_SERVER_ERROR, errorResponse);
        } else if (e instanceof SignatureException) {
            return new APIError(Response.Status.INTERNAL_SERVER_ERROR, errorResponse);
        } else if (e instanceof UnsupportedEncodingException) {
            return new APIError(Response.Status.INTERNAL_SERVER_ERROR, errorResponse);
        } else if (e instanceof JsonProcessingException) {
            return new APIError(Response.Status.INTERNAL_SERVER_ERROR, errorResponse);
        } else if (e instanceof InvalidKeyException) {
            return new APIError(Response.Status.UNAUTHORIZED, errorResponse);
        } else if (e instanceof NoSuchAlgorithmException) {
            return new APIError(Response.Status.INTERNAL_SERVER_ERROR, errorResponse);
        } else if (e instanceof SQLException) {
            return new APIError(Response.Status.INTERNAL_SERVER_ERROR, errorResponse);
        } else if (e instanceof PushDeviceHandlerClientException) {
            return new APIError(Response.Status.INTERNAL_SERVER_ERROR, errorResponse);
        } else if (e instanceof PushDeviceHandlerServerException) {
            return new APIError(Response.Status.INTERNAL_SERVER_ERROR, errorResponse);
        }  else {
            return new APIError(Response.Status.BAD_REQUEST, errorResponse);
        }
    }
    private static ErrorResponse.Builder getErrorBuilder(Constants.ErrorMessages errorEnum) {

        return new ErrorResponse.Builder().withCode(errorEnum.getCode()).withMessage(errorEnum.getMessage())
                .withDescription(errorEnum.getDescription());
    }
    private static APIError handleError(Response.Status status, Constants.ErrorMessages error) {
        return new APIError(status, getErrorBuilder(error).build());
    }
}