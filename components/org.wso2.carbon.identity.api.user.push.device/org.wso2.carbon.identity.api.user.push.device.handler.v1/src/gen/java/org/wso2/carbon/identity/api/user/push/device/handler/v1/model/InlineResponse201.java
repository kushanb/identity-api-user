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

package org.wso2.carbon.identity.api.user.push.device.handler.v1.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonCreator;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import java.util.UUID;
import javax.validation.constraints.*;


import io.swagger.annotations.*;
import java.util.Objects;
import javax.validation.Valid;
import javax.xml.bind.annotation.*;

public class InlineResponse201  {
  
    private UUID id;
    private String status;

    /**
    * ID of the added device
    **/
    public InlineResponse201 id(UUID id) {

        this.id = id;
        return this;
    }
    
    @ApiModelProperty(example = "b03f90c9-6723-48f6-863b-a35f1ac77f57", value = "ID of the added device")
    @JsonProperty("id")
    @Valid
    public UUID getId() {
        return id;
    }
    public void setId(UUID id) {
        this.id = id;
    }

    /**
    * Status of the device registration process
    **/
    public InlineResponse201 status(String status) {

        this.status = status;
        return this;
    }
    
    @ApiModelProperty(example = "Success", value = "Status of the device registration process")
    @JsonProperty("status")
    @Valid
    public String getStatus() {
        return status;
    }
    public void setStatus(String status) {
        this.status = status;
    }



    @Override
    public boolean equals(java.lang.Object o) {

        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        InlineResponse201 inlineResponse201 = (InlineResponse201) o;
        return Objects.equals(this.id, inlineResponse201.id) &&
            Objects.equals(this.status, inlineResponse201.status);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, status);
    }

    @Override
    public String toString() {

        StringBuilder sb = new StringBuilder();
        sb.append("class InlineResponse201 {\n");
        
        sb.append("    id: ").append(toIndentedString(id)).append("\n");
        sb.append("    status: ").append(toIndentedString(status)).append("\n");
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

