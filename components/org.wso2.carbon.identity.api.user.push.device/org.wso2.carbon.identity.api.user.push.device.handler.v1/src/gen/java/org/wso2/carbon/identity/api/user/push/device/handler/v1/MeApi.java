/*
* Copyright (c) 2020, WSO2 Inc. (http://www.wso2.org) All Rights Reserved.
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

package org.wso2.carbon.identity.api.user.push.device.handler.v1;

import org.springframework.beans.factory.annotation.Autowired;
import org.apache.cxf.jaxrs.ext.multipart.Attachment;
import org.apache.cxf.jaxrs.ext.multipart.Multipart;
import java.io.InputStream;
import java.util.List;

import org.wso2.carbon.identity.api.user.push.device.handler.v1.model.DeviceDTO;
import org.wso2.carbon.identity.api.user.push.device.handler.v1.model.DiscoveryDataDTO;
import org.wso2.carbon.identity.api.user.push.device.handler.v1.model.ErrorDTO;
import org.wso2.carbon.identity.api.user.push.device.handler.v1.model.InlineObject;
import org.wso2.carbon.identity.api.user.push.device.handler.v1.model.InlineResponse201;
import org.wso2.carbon.identity.api.user.push.device.handler.v1.model.PatchDTO;
import org.wso2.carbon.identity.api.user.push.device.handler.v1.model.RegistrationRequestDTO;
import org.wso2.carbon.identity.api.user.push.device.handler.v1.MeApiService;

import javax.validation.Valid;
import javax.ws.rs.*;
import javax.ws.rs.core.Response;
import io.swagger.annotations.*;

import javax.validation.constraints.*;

@Path("/me")
@Api(description = "The me API")

public class MeApi  {

    @Autowired
    private MeApiService delegate;

    @Valid
    @DELETE
    @Path("/push-auth/devices/{deviceId}")
    
    @Produces({ "application/json" })
    @ApiOperation(value = "Remove a registered device ", notes = "This API is used to remove a registered device at My Account<br/> <b>Permission required:</b>  * /permission/admin/login ", response = Void.class, authorizations = {
        @Authorization(value = "BasicAuth"),
        @Authorization(value = "OAuth2", scopes = {
            
        })
    }, tags={ "me", })
    @ApiResponses(value = { 
        @ApiResponse(code = 200, message = "OK", response = Void.class),
        @ApiResponse(code = 204, message = "No content", response = Void.class),
        @ApiResponse(code = 404, message = "Not found", response = ErrorDTO.class),
        @ApiResponse(code = 400, message = "Bad Request", response = ErrorDTO.class),
        @ApiResponse(code = 401, message = "Unauthorized", response = ErrorDTO.class),
        @ApiResponse(code = 403, message = "Forbidden", response = ErrorDTO.class),
        @ApiResponse(code = 500, message = "Server Error", response = ErrorDTO.class)
    })
    public Response mePushAuthDevicesDeviceIdDelete(@ApiParam(value = "Unique Id of the device",required=true) @PathParam("deviceId") String deviceId) {

        return delegate.mePushAuthDevicesDeviceIdDelete(deviceId );
    }

    @Valid
    @GET
    @Path("/push-auth/devices/{deviceId}")
    
    @Produces({ "application/json" })
    @ApiOperation(value = "Returns Specific device ", notes = "This API is used to get a specific device.<br/> <b>Permission required:</b>  * /permission/admin/login ", response = DeviceDTO.class, authorizations = {
        @Authorization(value = "BasicAuth"),
        @Authorization(value = "OAuth2", scopes = {
            
        })
    }, tags={ "me", })
    @ApiResponses(value = { 
        @ApiResponse(code = 200, message = "Details of a specific device.", response = DeviceDTO.class),
        @ApiResponse(code = 400, message = "Bad Request", response = ErrorDTO.class),
        @ApiResponse(code = 401, message = "Unauthorized", response = ErrorDTO.class),
        @ApiResponse(code = 403, message = "Forbidden", response = ErrorDTO.class),
        @ApiResponse(code = 500, message = "Server Error", response = ErrorDTO.class)
    })
    public Response mePushAuthDevicesDeviceIdGet(@ApiParam(value = "ID of device to return",required=true) @PathParam("deviceId") String deviceId) {

        return delegate.mePushAuthDevicesDeviceIdGet(deviceId );
    }

    @Valid
    @PATCH
    @Path("/push-auth/devices/{deviceId}")
    @Consumes({ "application/json" })
    @Produces({ "application/json" })
    @ApiOperation(value = "Update display name of a registered device ", notes = "This API is used to update the display name of a registered device<br/> <b>Permission required:</b>  * /permission/admin/login ", response = Void.class, authorizations = {
        @Authorization(value = "BasicAuth"),
        @Authorization(value = "OAuth2", scopes = {
            
        })
    }, tags={ "me", })
    @ApiResponses(value = { 
        @ApiResponse(code = 200, message = "Display name of device successfully updated", response = Void.class),
        @ApiResponse(code = 400, message = "Bad Request", response = ErrorDTO.class),
        @ApiResponse(code = 401, message = "Unauthorized", response = ErrorDTO.class),
        @ApiResponse(code = 403, message = "Forbidden", response = ErrorDTO.class),
        @ApiResponse(code = 404, message = "Not found", response = ErrorDTO.class),
        @ApiResponse(code = 500, message = "Server Error", response = ErrorDTO.class)
    })
    public Response mePushAuthDevicesDeviceIdPatch(@ApiParam(value = "deviceId",required=true) @PathParam("deviceId") String deviceId, @ApiParam(value = "Optional description in *Markdown*" ,required=true) @Valid PatchDTO patchDTO) {

        return delegate.mePushAuthDevicesDeviceIdPatch(deviceId,  patchDTO );
    }

    @Valid
    @POST
    @Path("/push-auth/devices/{deviceId}/remove")
    @Consumes({ "application/json" })
    @Produces({ "application/json" })
    @ApiOperation(value = "Remove device ", notes = "This API is used to remove a device from a mobile app<br/> <b>Permission required:</b>  * /permission/admin/login ", response = Void.class, authorizations = {
        @Authorization(value = "BasicAuth"),
        @Authorization(value = "OAuth2", scopes = {
            
        })
    }, tags={ "me", })
    @ApiResponses(value = { 
        @ApiResponse(code = 200, message = "Device removed", response = Void.class),
        @ApiResponse(code = 400, message = "Bad Request", response = ErrorDTO.class),
        @ApiResponse(code = 401, message = "Unautharized", response = ErrorDTO.class),
        @ApiResponse(code = 403, message = "Forbidden", response = ErrorDTO.class),
        @ApiResponse(code = 404, message = "Not found", response = ErrorDTO.class),
        @ApiResponse(code = 500, message = "Server Error", response = ErrorDTO.class)
    })
    public Response mePushAuthDevicesDeviceIdRemovePost(@ApiParam(value = "Unique Id of the device",required=true) @PathParam("deviceId") String deviceId, @ApiParam(value = "" ,required=true) @Valid InlineObject inlineObject) {

        return delegate.mePushAuthDevicesDeviceIdRemovePost(deviceId,  inlineObject );
    }

    @Valid
    @GET
    @Path("/push-auth/devices")
    
    @Produces({ "application/json" })
    @ApiOperation(value = "Returns registered devices list of the user ", notes = "This API is used to get a list of the registered devices<br/> <b>Permission required:</b>  * /permission/admin/login ", response = Object.class, responseContainer = "List", authorizations = {
        @Authorization(value = "BasicAuth"),
        @Authorization(value = "OAuth2", scopes = {
            
        })
    }, tags={ "me", })
    @ApiResponses(value = { 
        @ApiResponse(code = 200, message = "All availabe devices of the user.", response = Object.class, responseContainer = "List"),
        @ApiResponse(code = 400, message = "Bad Request", response = ErrorDTO.class),
        @ApiResponse(code = 401, message = "Unautharized", response = ErrorDTO.class),
        @ApiResponse(code = 403, message = "Forbidden", response = ErrorDTO.class),
        @ApiResponse(code = 404, message = "Not found", response = ErrorDTO.class),
        @ApiResponse(code = 500, message = "Server Error", response = ErrorDTO.class)
    })
    public Response mePushAuthDevicesGet() {

        return delegate.mePushAuthDevicesGet();
    }

    @Valid
    @POST
    @Path("/push-auth/devices")
    @Consumes({ "application/json" })
    @Produces({ "application/json" })
    @ApiOperation(value = "Register a device for push-based authentication ", notes = "This API is used to recieve device information from the mobile app and complete the add account flow.<br/> <b>Permission required:</b>  * /permission/admin/login ", response = InlineResponse201.class, authorizations = {
        @Authorization(value = "BasicAuth"),
        @Authorization(value = "OAuth2", scopes = {
            
        })
    }, tags={ "me", })
    @ApiResponses(value = { 
        @ApiResponse(code = 201, message = "Created", response = InlineResponse201.class),
        @ApiResponse(code = 400, message = "Bad Request", response = ErrorDTO.class),
        @ApiResponse(code = 401, message = "Unauthorized", response = ErrorDTO.class),
        @ApiResponse(code = 403, message = "Forbidden", response = ErrorDTO.class),
        @ApiResponse(code = 409, message = "Conflict", response = ErrorDTO.class),
        @ApiResponse(code = 500, message = "Server Error", response = ErrorDTO.class)
    })
    public Response mePushAuthDevicesPost(@ApiParam(value = "Device details sent by mobile application" ,required=true) @Valid RegistrationRequestDTO registrationRequestDTO) {

        return delegate.mePushAuthDevicesPost(registrationRequestDTO );
    }

    @Valid
    @GET
    @Path("/push-auth/discovery-data")
    
    @Produces({ "application/json" })
    @ApiOperation(value = "Retrieve data for the QR code ", notes = "This API is used to retrieve data for the QR Code to trigger the push authentication add account flow.<br/>  <b>Permission required:</b>  * /permission/admin/login ", response = DiscoveryDataDTO.class, authorizations = {
        @Authorization(value = "BasicAuth"),
        @Authorization(value = "OAuth2", scopes = {
            
        })
    }, tags={ "me" })
    @ApiResponses(value = { 
        @ApiResponse(code = 200, message = "Successful response", response = DiscoveryDataDTO.class),
        @ApiResponse(code = 401, message = "Unautharized", response = ErrorDTO.class),
        @ApiResponse(code = 403, message = "Forbidden", response = ErrorDTO.class),
        @ApiResponse(code = 500, message = "Internal Server Error", response = ErrorDTO.class)
    })
    public Response mePushAuthDiscoveryDataGet() {

        return delegate.mePushAuthDiscoveryDataGet();
    }

}
