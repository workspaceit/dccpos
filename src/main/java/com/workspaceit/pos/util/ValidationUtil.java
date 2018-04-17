package com.workspaceit.pos.util;

import org.springframework.stereotype.Component;

@Component
public class ValidationUtil {
    public ServiceResponse limitOffsetValidation(int limit,int offset,final int _MAX_LIMIT){
        ServiceResponse serviceResponse = ServiceResponse.getInstance();
        if(limit==0 || limit<0){
            serviceResponse.setValidationError("limit","Limit can not be equal or less then zero");
        }
        if(offset<=0){
            serviceResponse.setValidationError("offset","Offset can not be less equal zero");
        }


        if(limit>_MAX_LIMIT){
            serviceResponse.setValidationError("limit","Limit exceeds Max limit :"+_MAX_LIMIT);
        }

        return serviceResponse;
    }
}
