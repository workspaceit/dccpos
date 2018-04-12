package com.workspaceit.pos.helper;

import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;

@Component
public class FormToNameValuePair {
    public List<BasicNameValuePair> getNameValuePair(Object obj){
       Method[] methods = obj.getClass().getMethods();
       List<BasicNameValuePair> listOfPair = new ArrayList<>();
       for(Method method:methods){
           String methodName = method.getName();
           if (methodName.startsWith("get") && !methodName.endsWith("Class")){
               try {
                   String fieldName = methodName.substring(3);
                   fieldName = fieldName.substring(0,1).toLowerCase()+fieldName.substring(1);
                   Object fieldVal = method.invoke(obj);

                   BasicNameValuePair pair = new BasicNameValuePair(fieldName, String.valueOf(fieldVal));
                   listOfPair.add(pair);

               } catch (IllegalAccessException e) {
                   e.printStackTrace();
               } catch (InvocationTargetException e) {
                   e.printStackTrace();
               }
           }
       }
        return listOfPair;
    }

    public UrlEncodedFormEntity getUrlEncodedFormEntity(Object obj) throws UnsupportedEncodingException {
      return  new UrlEncodedFormEntity(this.getNameValuePair(obj));
    }
    public String getUrlEncodedFormEntityToString(Object obj) throws IOException {
        return EntityUtils.toString( this.getUrlEncodedFormEntity(obj) );
    }

}
