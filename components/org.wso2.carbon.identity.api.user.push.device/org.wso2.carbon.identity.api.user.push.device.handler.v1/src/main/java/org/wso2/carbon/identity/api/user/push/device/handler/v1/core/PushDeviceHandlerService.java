/*
 * Copyright (c) 2020, WSO2 Inc. (http://www.wso2.org) All Rights Reserved.
 *
 * WSO2 Inc. licenses this file to you under the Apache License,
 * Version 2.0 (the "License"); you may not use this file except
 * in compliance with the License.
 * You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing,
 * software distributed under the License is distributed on an
 * "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY
 * KIND, either express or implied.  See the License for the
 * specific language governing permissions and limitations
 * under the License.
 *
 */
package org.wso2.carbon.identity.api.user.push.device.handler.v1.core;

import com.fasterxml.jackson.core.JsonProcessingException;
import org.wso2.carbon.context.CarbonContext;
import org.wso2.carbon.identity.api.user.push.device.common.util.PushDeviceApiConstants;
import org.wso2.carbon.identity.api.user.push.device.common.util.PushDeviceApiUtils;
import org.wso2.carbon.identity.api.user.push.device.handler.v1.model.DeviceDTO;
import org.wso2.carbon.identity.api.user.push.device.handler.v1.model.DiscoveryDataDTO;
import org.wso2.carbon.identity.api.user.push.device.handler.v1.model.RegistrationRequestDTO;
import org.wso2.carbon.identity.application.authenticator.push.device.handler.DeviceHandler;
import org.wso2.carbon.identity.application.authenticator.push.device.handler.exception.PushDeviceHandlerClientException;
import org.wso2.carbon.identity.application.authenticator.push.device.handler.exception.PushDeviceHandlerServerException;
import org.wso2.carbon.identity.application.authenticator.push.device.handler.impl.DeviceHandlerImpl;
import org.wso2.carbon.identity.application.authenticator.push.device.handler.model.Device;
import org.wso2.carbon.identity.application.authenticator.push.device.handler.model.DiscoveryData;
import org.wso2.carbon.identity.application.authenticator.push.device.handler.model.RegistrationRequest;
import org.wso2.carbon.identity.application.common.model.User;
import org.wso2.carbon.identity.base.IdentityException;
import org.wso2.carbon.user.api.UserStoreException;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.security.SignatureException;
import java.security.spec.InvalidKeySpecException;
import java.sql.SQLException;
import java.util.ArrayList;

/**
 * Service class of push device handler Rest APIs..
 */
public class PushDeviceHandlerService {
    DeviceHandler deviceHandler;

    public DeviceDTO registerDevice(RegistrationRequestDTO registrationRequestDTO) {
        RegistrationRequest registrationRequest = new RegistrationRequest();
        Device device;
        try {
            deviceHandler = new DeviceHandlerImpl();
            registrationRequest.setDeviceId(registrationRequestDTO.getDeviceId());
            registrationRequest.setDeviceModel(registrationRequestDTO.getModel());
            registrationRequest.setDeviceName(registrationRequestDTO.getName());
            registrationRequest.setPublicKey(registrationRequestDTO.getPublickey());
            registrationRequest.setPushId(registrationRequestDTO.getPushId());
            registrationRequest.setSignature(registrationRequestDTO.getSignature());
            device = deviceHandler.registerDevice(registrationRequest);

        } catch (PushDeviceHandlerClientException e) {
            throw PushDeviceApiUtils.handleException(e,
                    PushDeviceApiConstants.ErrorMessages.ERROR_CODE_REGISTER_DEVICE_CLIENT_ERROR);
        } catch (PushDeviceHandlerServerException e) {
            throw PushDeviceApiUtils.handleException(e,
                    PushDeviceApiConstants.ErrorMessages.ERROR_CODE_REGISTER_DEVICE_SERVER_ERROR);
        } catch (SignatureException e) {
            throw PushDeviceApiUtils.handleException(e,
                    PushDeviceApiConstants.ErrorMessages.ERROR_CODE_INVALID_SIGNATURE);
        } catch (UnsupportedEncodingException e) {
            throw PushDeviceApiUtils.handleException(e,
                    PushDeviceApiConstants.ErrorMessages.ERROR_CODE_INTERNAL_SERVER_ERROR);
        } catch (JsonProcessingException e) {
            throw PushDeviceApiUtils.handleException(e,
                    PushDeviceApiConstants.ErrorMessages.ERROR_CODE__JSON_PROCESSING_EXCEPTION);
        } catch (NoSuchAlgorithmException e) {
            throw PushDeviceApiUtils.handleException(e,
                    PushDeviceApiConstants.ErrorMessages.ERROR_CODE_FAILED_SIGNATURE_VALIDATION);
        } catch (InvalidKeyException e) {
            throw PushDeviceApiUtils.handleException(e,
                    PushDeviceApiConstants.ErrorMessages.ERROR_CODE_INVALID_SIGNATURE);
        } catch (UserStoreException e) {
            throw PushDeviceApiUtils.handleException(e,
                    PushDeviceApiConstants.ErrorMessages.ERROR_CODE_USER_STORE_ERROR);
        } catch (SQLException e) {
            throw PushDeviceApiUtils.handleException(e,
                    PushDeviceApiConstants.ErrorMessages.ERROR_CODE_DEVICE_HANDLER_SQL_EXCEPTION);
        } catch (InvalidKeySpecException e) {
            throw PushDeviceApiUtils.handleException(e,
                    PushDeviceApiConstants.ErrorMessages.ERROR_CODE_INTERNAL_SERVER_ERROR);
        } catch (IdentityException e) {
            throw PushDeviceApiUtils.handleException(e,
                    PushDeviceApiConstants.ErrorMessages.ERROR_CODE_INTERNAL_SERVER_ERROR);
        }

        DeviceDTO deviceDTO = new DeviceDTO();
        deviceDTO.setDeviceId(device.getDeviceId());
        deviceDTO.setName(device.getDeviceName());
        return deviceDTO;
    }

    public void unregisterDevice(String deviceId) {
        try {
            deviceHandler = new DeviceHandlerImpl();
            deviceHandler.unregisterDevice(deviceId);
        } catch (PushDeviceHandlerClientException e) {
            throw PushDeviceApiUtils.handleException(e,
                    PushDeviceApiConstants.ErrorMessages.ERROR_COOE_UNREGISTER_DEVICE_CLIENT_ERROR, deviceId);
        } catch (PushDeviceHandlerServerException e) {
            throw PushDeviceApiUtils.handleException(e,
                    PushDeviceApiConstants.ErrorMessages.ERROR_COOE_UNREGISTER_DEVICE_SERVER_ERROR, deviceId);
        } catch (SQLException e) {
            throw PushDeviceApiUtils.handleException(e,
                    PushDeviceApiConstants.ErrorMessages.ERROR_CODE_DEVICE_HANDLER_SQL_EXCEPTION);
        }

    }

    public void editDeviceName(String deviceId, String newDeviceName) {
        try {
            deviceHandler = new DeviceHandlerImpl();
            deviceHandler.editDeviceName(deviceId, newDeviceName);
        } catch (PushDeviceHandlerClientException e) {
            throw PushDeviceApiUtils.handleException(e,
                    PushDeviceApiConstants.ErrorMessages.ERROR_CODE_EDIT_DEVICE_NAME_CLIENT_ERROR, deviceId);
        } catch (PushDeviceHandlerServerException e) {
            throw PushDeviceApiUtils.handleException(e,
                    PushDeviceApiConstants.ErrorMessages.ERROR_CODE_EDIT_DEVICE_NAME_SERVER_ERROR, deviceId);
        } catch (SQLException e) {
            throw PushDeviceApiUtils.handleException(e,
                    PushDeviceApiConstants.ErrorMessages.ERROR_CODE_DEVICE_HANDLER_SQL_EXCEPTION);
        }
    }

    public DeviceDTO getDevice(String deviceId) {
        deviceHandler = new DeviceHandlerImpl();
        Device device = null;
        try {
            device = deviceHandler.getDevice(deviceId);
        } catch (PushDeviceHandlerClientException e) {
            throw PushDeviceApiUtils.handleException(e,
                    PushDeviceApiConstants.ErrorMessages.ERROR_CODE_GET_DEVICE_CLIENT_ERROR, deviceId);
        } catch (SQLException e) {
            throw PushDeviceApiUtils.handleException(e,
                    PushDeviceApiConstants.ErrorMessages.ERROR_CODE_DEVICE_HANDLER_SQL_EXCEPTION);
        } catch (PushDeviceHandlerServerException e) {
            throw PushDeviceApiUtils.handleException(e,
                    PushDeviceApiConstants.ErrorMessages.ERROR_CODE_GET_DEVICE_SERVER_ERROR, deviceId);
        } catch (JsonProcessingException e) {
            throw PushDeviceApiUtils.handleException(e,
                    PushDeviceApiConstants.ErrorMessages.ERROR_CODE__JSON_PROCESSING_EXCEPTION);
        } catch (IOException e) {
            throw PushDeviceApiUtils.handleException(e,
                    PushDeviceApiConstants.ErrorMessages.ERROR_CODE_IO_ERROR);
        }
        DeviceDTO deviceDTO = new DeviceDTO();
        deviceDTO.setDeviceId(device.getDeviceId());
        deviceDTO.setName(device.getDeviceName());
        deviceDTO.setModel(device.getDeviceModel());
        deviceDTO.setPushId(device.getPushId());
        deviceDTO.setRegistrationTime(device.getRegistrationTime());
        deviceDTO.setLastUsedTime(device.getLastUsedTime());
        return deviceDTO;
    }

    public ArrayList<DeviceDTO> listDevices() {
        deviceHandler = new DeviceHandlerImpl();
        ArrayList<Device> devices = null;
        User user = getAuthenticatedUser();
        try {
            devices = deviceHandler.listDevices(user.getUserName(), user.getUserStoreDomain(), user.getTenantDomain());
        } catch (PushDeviceHandlerClientException e) {
            throw PushDeviceApiUtils.handleException(e,
                    PushDeviceApiConstants.ErrorMessages.ERROR_CODE_LIST_DEVICE_CLIENT_ERROR);
        } catch (PushDeviceHandlerServerException e) {
            throw PushDeviceApiUtils.handleException(e,
                    PushDeviceApiConstants.ErrorMessages.ERROR_CODE_LIST_DEVICE_SERVER_ERROR);
        } catch (JsonProcessingException e) {
            throw PushDeviceApiUtils.handleException(e,
                    PushDeviceApiConstants.ErrorMessages.ERROR_CODE__JSON_PROCESSING_EXCEPTION);
        } catch (SQLException e) {
            throw PushDeviceApiUtils.handleException(e,
                    PushDeviceApiConstants.ErrorMessages.ERROR_CODE_DEVICE_HANDLER_SQL_EXCEPTION);
        } catch (UserStoreException e) {
            throw PushDeviceApiUtils.handleException(e,
                    PushDeviceApiConstants.ErrorMessages.ERROR_CODE_USER_STORE_ERROR);
        } catch (IOException e) {
            throw PushDeviceApiUtils.handleException(e,
                    PushDeviceApiConstants.ErrorMessages.ERROR_CODE_IO_ERROR);
        }
        ArrayList<DeviceDTO> deviceDTOArrayList = new ArrayList<>();
        DeviceDTO deviceDTO;
        if (devices != null) {
            for (Device device : devices) {
                deviceDTO = new DeviceDTO();
                deviceDTO.setDeviceId(device.getDeviceId());
                deviceDTO.setName(device.getDeviceName());
                deviceDTO.setModel(device.getDeviceModel());
                deviceDTO.setRegistrationTime(device.getRegistrationTime());
                deviceDTO.setLastUsedTime(device.getLastUsedTime());
                deviceDTOArrayList.add(deviceDTO);
            }
        }
        return deviceDTOArrayList;
    }

    public DiscoveryDataDTO getDiscoveryData() {
        deviceHandler = new DeviceHandlerImpl();
//        User user = getAuthenticatedUser();
        DiscoveryData discoveryData;
        discoveryData = deviceHandler.getDiscoveryData();
        DiscoveryDataDTO discoveryDataDTO = new DiscoveryDataDTO();
        discoveryDataDTO.setDid(discoveryData.getDeviceId());
        discoveryDataDTO.setUn(discoveryData.getUsername());
        discoveryDataDTO.setTd(discoveryData.getTenantDomain());
        discoveryDataDTO.setFn(discoveryData.getFirstName());
        discoveryDataDTO.setLn(discoveryData.getLastName());
        discoveryDataDTO.setChg(discoveryData.getChallenge());
        discoveryDataDTO.setHst(discoveryData.getHost());
        discoveryDataDTO.setBp(discoveryData.getBasePath());
        discoveryDataDTO.setRe(discoveryData.getRegistrationEndpoint());
        discoveryDataDTO.setRde(discoveryData.getRemoveDeviceEndpoint());
        return discoveryDataDTO;
    }
    private User getAuthenticatedUser() {
        User user = User.getUserFromUserName(CarbonContext.getThreadLocalCarbonContext().getUsername());
        user.setTenantDomain(CarbonContext.getThreadLocalCarbonContext().getTenantDomain());
        return user;
    }
}
