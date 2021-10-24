package com.varosa.audit.util;


import com.varosa.audit.pojo.ApiResponse;
import org.springframework.beans.factory.annotation.Autowired;

public class BaseController {

    /**
     * Global API Response Instance
     */
    @Autowired
    protected ApiResponse globalApiResponse;

    /**
     * API Success Status
     */
    protected final int API_SUCCESS_STATUS = 1;

    /**
     * API Error Status
     */
    protected final int API_ERROR_STATUS = 0;

    /**
     * Message Source Instance
     */
    @Autowired
    protected Messages customMessageSource;

    /**
     * Module Name
     */
    protected String moduleName;
    protected String permissionName;

    /**
     * Function that sends successful API Response
     * @param message
     * @param data
     * @return
     */
    protected ApiResponse successResponse(String message, Object data) {
        globalApiResponse.setStatus(API_SUCCESS_STATUS);
        globalApiResponse.setMessage(message);
        globalApiResponse.setData(data);
        return globalApiResponse;
    }

    /**
     * Function that sends error API Response
     * @param message
     * @param errors
     * @return
     */
    protected ApiResponse errorResponse(String message, Object errors) {
        globalApiResponse.setStatus(API_ERROR_STATUS);
        globalApiResponse.setMessage(message);
        return globalApiResponse;
    }

    public String getPermissionName() {
        return permissionName;
    }

}
