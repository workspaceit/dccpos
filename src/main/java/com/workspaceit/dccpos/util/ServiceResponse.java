package com.workspaceit.dccpos.util;

//import com.fasterxml.jackson.annotation.JsonInclude;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.validation.ObjectError;

import java.util.*;

/**
 * Created by mi on 8/1/16.
 */

//@JsonInclude(JsonInclude.Include.NON_NULL)
public class ServiceResponse {

    private List<Map<String,String>> formError;
    private Map<String,Object> msgMap;
    private ServiceResponse() {
        formError = new ArrayList<>();
        msgMap = new HashMap<>();
    }
    public static ServiceResponse getInstance(){
        return new ServiceResponse();
    }
    public static HashMap<String,String> getMsgInMap(String msg){
        return new HashMap<String, String>(){{put("msg",msg);}};
    }
    public Map<String,Object> getMsg(){
        return this.msgMap;
    }
    public ServiceResponse setMsg(String param,Object val){
        this.msgMap.put(param,val);
        return this;
    }
    public boolean hasErrors(){
        return this.formError.size()>0;
    }
    public void bindValidationError(BindingResult result){

        if(result.hasErrors()) {
            /**
             * To generate unique error result
             * May not work :( ... remove if needed
             * */
            Set<ObjectError> orSet = new HashSet<>(result.getAllErrors());
            for (ObjectError object : orSet) {


                if(object instanceof FieldError) {
                    FieldError fieldError = (FieldError) object;
                    String errorMsg = (fieldError.getDefaultMessage()==null)?fieldError.getCode():fieldError.getDefaultMessage();
                    this.setValidationError(fieldError.getField(), errorMsg);
                }

                if(object instanceof ObjectError) {
                    ObjectError objectError = (ObjectError) object;

                    //requestError.setParams(objectError.get());
                    //requestError.setMsg(objectError.getCode());
                    // requestError.setParams(objectError.getCode());
                }
            }
        }
    }
    public void bindValidationError(BindingResult result,String... params){
        if(result.hasErrors()) {
            List<String> paramList = Arrays.asList(params);
            for (ObjectError object : result.getAllErrors()) {


                if(object instanceof FieldError) {
                    FieldError fieldError = (FieldError) object;
                    if(!paramList.contains(fieldError.getField())){
                        continue;
                    }

                    String errorMsg = (fieldError.getDefaultMessage()==null)?fieldError.getCode():fieldError.getDefaultMessage();
                    this.setValidationError(fieldError.getField(), errorMsg);
                }

                if(object instanceof ObjectError) {
                    ObjectError objectError = (ObjectError) object;

                    //requestError.setParams(objectError.get());
                    //requestError.setMsg(objectError.getCode());
                    // requestError.setParams(objectError.getCode());
                }
            }
        }
    }
    public List<Map<String, String>> getFormError() {
        return formError;
    }
    public Map<String, Object> getResult(long totalResult,Object result){
        Map<String, Object> map = new HashMap<>();
        map.put("totalResult",totalResult);
        map.put("result",result);
        return map;
    }
    public ServiceResponse setValidationError(String params, String msg){
        Map<String,String> errorObj = new HashMap<>();
        errorObj.put("params",params);
        errorObj.put("msg",msg);
        this.formError.add(errorObj);

        return this;
    }


}