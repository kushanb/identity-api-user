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

import org.json.JSONObject;
import org.wso2.carbon.context.CarbonContext;
import org.wso2.carbon.identity.api.user.push.device.common.util.PushDeviceApiConstants;
import org.wso2.carbon.identity.api.user.push.device.common.util.PushDeviceApiUtils;
import org.wso2.carbon.identity.api.user.push.device.handler.v1.model.DeviceDTO;
import org.wso2.carbon.identity.api.user.push.device.handler.v1.model.DiscoveryDataDTO;
import org.wso2.carbon.identity.api.user.push.device.handler.v1.model.RegistrationRequestDTO;
import org.wso2.carbon.identity.api.user.push.device.handler.v1.model.StatusDTO;
import org.wso2.carbon.identity.application.authenticator.push.device.handler.DeviceHandler;
import org.wso2.carbon.identity.application.authenticator.push.device.handler.exception.PushDeviceHandlerClientException;
import org.wso2.carbon.identity.application.authenticator.push.device.handler.exception.PushDeviceHandlerServerException;
import org.wso2.carbon.identity.application.authenticator.push.device.handler.impl.DeviceHandlerImpl;
import org.wso2.carbon.identity.application.authenticator.push.device.handler.model.Device;
import org.wso2.carbon.identity.application.authenticator.push.device.handler.model.RegistrationDiscoveryData;
import org.wso2.carbon.identity.application.authenticator.push.device.handler.model.RegistrationRequest;
import org.wso2.carbon.identity.application.authenticator.push.validator.IdentityPushException;
import org.wso2.carbon.identity.application.authenticator.push.validator.PushJWTValidator;
import org.wso2.carbon.identity.application.common.model.User;
import org.wso2.carbon.user.api.UserStoreException;
import org.wso2.carbon.user.core.common.AbstractUserStoreManager;

import java.util.ArrayList;
import java.util.List;

/**
 * Service class of push device handler Rest APIs..
 */
public class PushDeviceHandlerService {

    DeviceHandler deviceHandler;

    /**
     * Register a new device
     *
     * @param registrationRequestDTO Registration request
     * @return Status of registration request
     */
    public StatusDTO registerDevice(RegistrationRequestDTO registrationRequestDTO) {

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
        }

        StatusDTO statusDTO = new StatusDTO();
        statusDTO.setDeviceId(device.getDeviceId());
        statusDTO.setOperation("REGISTER");
        statusDTO.setStatus("SUCCESSFUL");
        return statusDTO;
    }

    /**
     * Remove a registered device via MyAccount
     *
     * @param deviceId Unique ID for the device to be removed
     */
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
        }

    }

    /**
     * Remove a registered device via mobile app
     *
     * @param deviceId Unique ID for the device
     * @param token    JWT containing device removal information
     * @return Result of the request
     */
    public StatusDTO unregisterDeviceMobile(String deviceId, String token) {

        PushJWTValidator validator = new PushJWTValidator();
        deviceHandler = new DeviceHandlerImpl();
        StatusDTO status = new StatusDTO();
        status.setDeviceId(deviceId);
        status.setOperation("DELETE");
        try {
            String publicKey = deviceHandler.getPublicKey(deviceId);
            if (validator.validate(token, publicKey, null)) {
                deviceHandler.unregisterDevice(deviceId);
                status.setStatus("SUCCESSFUL");
                return status;
            }
        } catch (PushDeviceHandlerServerException e) {
            throw PushDeviceApiUtils.handleException(e,
                    PushDeviceApiConstants.ErrorMessages.ERROR_COOE_UNREGISTER_DEVICE_SERVER_ERROR, deviceId);
        } catch (PushDeviceHandlerClientException e) {
            throw PushDeviceApiUtils.handleException(e,
                    PushDeviceApiConstants.ErrorMessages.ERROR_COOE_UNREGISTER_DEVICE_CLIENT_ERROR, deviceId);
        } catch (IdentityPushException e) {
            throw PushDeviceApiUtils.handleException(e,
                    PushDeviceApiConstants.ErrorMessages.ERROR_COOE_UNREGISTER_DEVICE_CLIENT_ERROR, deviceId);
        }
        status.setStatus("FAILED");
        return status;

    }

    /**
     * Update attributes of a registered device
     *
     * @param deviceId      Unique ID of the device
     * @param updatedDevice Device object with the updated attributes
     */
    public void editDeviceName(String deviceId, String updatedDevice) {

        try {
            deviceHandler = new DeviceHandlerImpl();
            JSONObject deviceObject = new JSONObject(updatedDevice);
            Device device = new Device();
            device.setDeviceId(deviceId);
            device.setDeviceName(deviceObject.getString("name"));
            device.setPushId(deviceObject.getString("pushId"));
            deviceHandler.editDevice(deviceId, device);
        } catch (PushDeviceHandlerClientException e) {
            throw PushDeviceApiUtils.handleException(e,
                    PushDeviceApiConstants.ErrorMessages.ERROR_CODE_EDIT_DEVICE_NAME_CLIENT_ERROR, deviceId);
        } catch (PushDeviceHandlerServerException e) {
            throw PushDeviceApiUtils.handleException(e,
                    PushDeviceApiConstants.ErrorMessages.ERROR_CODE_EDIT_DEVICE_NAME_SERVER_ERROR, deviceId);
        }
    }

    /**
     * Get a registered device
     *
     * @param deviceId Unique ID of the required device
     * @return Device object
     */
    public DeviceDTO getDevice(String deviceId) {

        deviceHandler = new DeviceHandlerImpl();
        Device device = null;
        try {
            device = deviceHandler.getDevice(deviceId);
        } catch (PushDeviceHandlerClientException e) {
            throw PushDeviceApiUtils.handleException(e,
                    PushDeviceApiConstants.ErrorMessages.ERROR_CODE_GET_DEVICE_CLIENT_ERROR, deviceId);
        } catch (PushDeviceHandlerServerException e) {
            throw PushDeviceApiUtils.handleException(e,
                    PushDeviceApiConstants.ErrorMessages.ERROR_CODE_GET_DEVICE_SERVER_ERROR, deviceId);
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

    /**
     * Get a list of all the devices for the authenticated in user
     *
     * @return List of registered devices of the user
     */
    public ArrayList<DeviceDTO> listDevices() {

        deviceHandler = new DeviceHandlerImpl();
        List<Device> devices = null;
        User user = getAuthenticatedUser();

        try {
            String userId = getUserIdFromUsername(user.getUserName());
            devices = deviceHandler.listDevices(userId);
        } catch (PushDeviceHandlerServerException e) {
            throw PushDeviceApiUtils.handleException(e,
                    PushDeviceApiConstants.ErrorMessages.ERROR_CODE_LIST_DEVICE_SERVER_ERROR);
        } catch (UserStoreException e) {
            throw PushDeviceApiUtils.handleException(e,
                    PushDeviceApiConstants.ErrorMessages.ERROR_CODE_USER_STORE_ERROR);
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

    /**
     * Get discovery data for registering a new device
     *
     * @return Discovery data
     */
    public DiscoveryDataDTO getDiscoveryData() {

        deviceHandler = new DeviceHandlerImpl();
        RegistrationDiscoveryData discoveryData;
        try {
            discoveryData = deviceHandler.getRegistrationDiscoveryData();
        } catch (PushDeviceHandlerServerException e) {
            throw PushDeviceApiUtils.handleException(e,
                    PushDeviceApiConstants.ErrorMessages.ERROR_CODE_REGISTER_DEVICE_SERVER_ERROR);
        }
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

    /**
     * Get the authenticated user
     *
     * @return Authenticated user
     */
    private User getAuthenticatedUser() {

        User user = User.getUserFromUserName(CarbonContext.getThreadLocalCarbonContext().getUsername());
        user.setTenantDomain(CarbonContext.getThreadLocalCarbonContext().getTenantDomain());
        return user;
    }

    /**
     * Get the unique ID for a user by the username
     *
     * @param username Username of the user
     * @return UserID for the user
     * @throws UserStoreException
     */
    private String getUserIdFromUsername(String username) throws UserStoreException {

        AbstractUserStoreManager userStoreManager = (AbstractUserStoreManager) CarbonContext.
                getThreadLocalCarbonContext().getUserRealm().getUserStoreManager();
        return userStoreManager.getUserIDFromUserName(username);

    }
}