package com.workspaceit.pos.helper;



import com.workspaceit.pos.validation.form.employee.EmployeeForm;
import org.apache.commons.text.StringEscapeUtils;
import org.apache.http.message.BasicNameValuePair;

import javax.servlet.http.HttpServletRequest;
import java.beans.IntrospectionException;
import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import java.util.*;

/**
 * Created by MI on 4/24/17.
 */
public class FormFilterHelper {

    public static List<BasicNameValuePair> doBasicFiler(Object obj){

        Method[] methods = obj.getClass().getMethods();
        Map<String,Object> methodValueMap = new HashMap<>();
        List<BasicNameValuePair> listOfPair = new ArrayList<>();

        /**
         * Getter
         * */
        for(Method method:methods){
            String methodName = method.getName();
            String methodNameWithOutSetGet = method.getName().replaceFirst("get","");

            if (methodName.startsWith("get") && !methodName.endsWith("Class")){
                try {
                    Object fieldVal = method.invoke(obj);
                    methodValueMap.put(methodNameWithOutSetGet,fieldVal);
                } catch (IllegalAccessException e) {
                    e.printStackTrace();
                } catch (InvocationTargetException e) {
                    e.printStackTrace();
                }
            }
        }
        /**
         * Setter
         * */
        for(Method method:methods){
            String methodName = method.getName();
            String methodNameWithOutSetGet = method.getName().replaceFirst("set","");
            if (methodName.startsWith("set") && !methodName.endsWith("Class")){
                try {
                    Class<?>[]  param = method.getParameterTypes();
                    if(param[0].equals(String.class)) {
                        String val = (String)methodValueMap.get(methodNameWithOutSetGet);
                        if(val==null)continue;
                        val = processString(val);
                        method.invoke(obj, val);
                    }else {
                        Object val = methodValueMap.get(methodNameWithOutSetGet);
                        method.invoke(obj, val);
                    }

                } catch (IllegalAccessException e) {
                    e.printStackTrace();
                } catch (InvocationTargetException e) {
                    e.printStackTrace();
                }
            }
        }
        return listOfPair;
    }
    private static String processString(String val){
        val = val.trim();
        val = StringEscapeUtils.escapeEcmaScript(val);
       return val;
    }
    public static void main(String... a){
        EmployeeForm employeeForm = new EmployeeForm();
        employeeForm.setEmployeeId("                         S                         </>");
        employeeForm.setSalary(654f);
        doBasicFiler(employeeForm);

        System.out.println(employeeForm.getEmployeeId());
        System.out.println(employeeForm.getSalary());


    }
}

