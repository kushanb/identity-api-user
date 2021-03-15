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

package org.wso2.carbon.identity.api.user.biometric.device.handler.v1.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonCreator;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import javax.validation.constraints.*;


import io.swagger.annotations.*;
import java.util.Objects;
import javax.validation.Valid;
import javax.xml.bind.annotation.*;

public class RegistrationRequestDTO  {
  
    private String id;
    private String name;
    private String model;
    private String pushId;
    private String publickey;
    private String signature;

    /**
    **/
    public RegistrationRequestDTO id(String id) {

        this.id = id;
        return this;
    }
    
    @ApiModelProperty(example = "fgsjhd-sjhfgj-334hdfg", value = "")
    @JsonProperty("id")
    @Valid
    public String getId() {
        return id;
    }
    public void setId(String id) {
        this.id = id;
    }

    /**
    **/
    public RegistrationRequestDTO name(String name) {

        this.name = name;
        return this;
    }
    
    @ApiModelProperty(example = "My Device", value = "")
    @JsonProperty("name")
    @Valid
    public String getName() {
        return name;
    }
    public void setName(String name) {
        this.name = name;
    }

    /**
    **/
    public RegistrationRequestDTO model(String model) {

        this.model = model;
        return this;
    }
    
    @ApiModelProperty(example = "iphone 8", value = "")
    @JsonProperty("model")
    @Valid
    public String getModel() {
        return model;
    }
    public void setModel(String model) {
        this.model = model;
    }

    /**
    **/
    public RegistrationRequestDTO pushId(String pushId) {

        this.pushId = pushId;
        return this;
    }
    
    @ApiModelProperty(example = "arsresdxvfy556565876", value = "")
    @JsonProperty("pushId")
    @Valid
    public String getPushId() {
        return pushId;
    }
    public void setPushId(String pushId) {
        this.pushId = pushId;
    }

    /**
    **/
    public RegistrationRequestDTO publickey(String publickey) {

        this.publickey = publickey;
        return this;
    }
    
    @ApiModelProperty(example = "bhkbvhbhjbh756576gfhvbe", value = "")
    @JsonProperty("publickey")
    @Valid
    public String getPublickey() {
        return publickey;
    }
    public void setPublickey(String publickey) {
        this.publickey = publickey;
    }

    /**
    **/
    public RegistrationRequestDTO signature(String signature) {

        this.signature = signature;
        return this;
    }
    
    @ApiModelProperty(example = "hfcfh576851", value = "")
    @JsonProperty("signature")
    @Valid
    public String getSignature() {
        return signature;
    }
    public void setSignature(String signature) {
        this.signature = signature;
    }



    @Override
    public boolean equals(java.lang.Object o) {

        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        RegistrationRequestDTO registrationRequestDTO = (RegistrationRequestDTO) o;
        return Objects.equals(this.id, registrationRequestDTO.id) &&
            Objects.equals(this.name, registrationRequestDTO.name) &&
            Objects.equals(this.model, registrationRequestDTO.model) &&
            Objects.equals(this.pushId, registrationRequestDTO.pushId) &&
            Objects.equals(this.publickey, registrationRequestDTO.publickey) &&
            Objects.equals(this.signature, registrationRequestDTO.signature);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, name, model, pushId, publickey, signature);
    }

    @Override
    public String toString() {

        StringBuilder sb = new StringBuilder();
        sb.append("class RegistrationRequestDTO {\n");
        
        sb.append("    id: ").append(toIndentedString(id)).append("\n");
        sb.append("    name: ").append(toIndentedString(name)).append("\n");
        sb.append("    model: ").append(toIndentedString(model)).append("\n");
        sb.append("    pushId: ").append(toIndentedString(pushId)).append("\n");
        sb.append("    publickey: ").append(toIndentedString(publickey)).append("\n");
        sb.append("    signature: ").append(toIndentedString(signature)).append("\n");
        sb.append("}");
        return sb.toString();
    }

    /**
    * Convert the given object to string with each line indented by 4 spaces
    * (except the first line).
    */
    private String toIndentedString(java.lang.Object o) {

        if (o == null) {
            return "null";
        }
        return o.toString().replace("\n", "\n");
    }
}

