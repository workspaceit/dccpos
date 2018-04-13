package com.workspaceit.pos.helper;



import com.workspaceit.pos.validation.form.employee.EmployeeForm;
import org.apache.http.message.BasicNameValuePair;

import javax.servlet.http.HttpServletRequest;
import java.beans.IntrospectionException;
import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by MI on 4/24/17.
 */
public class FormFilterHelper {

    public static List<BasicNameValuePair> getNameValuePair(Object obj){
        Method[] methods = obj.getClass().getMethods();
        List<BasicNameValuePair> listOfPair = new ArrayList<>();
        /**
         * Getter
         * */
        for(Method method:methods){
            String methodName = method.getName();
            if (methodName.startsWith("get") && !methodName.endsWith("Class")){
                try {
                    System.out.println(methodName);

                    Object fieldVal = method.invoke(obj);


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
            if (methodName.startsWith("set") && !methodName.endsWith("Class")){
                try {
                    System.out.println(methodName);
                    Class<?>[]  param = method.getParameterTypes();
                    if(param[0].equals(String.class))
                        method.invoke(obj,"456");
                    if(param[0].equals(Float.class))
                        method.invoke(obj,456f);

                } catch (IllegalAccessException e) {
                    e.printStackTrace();
                } catch (InvocationTargetException e) {
                    e.printStackTrace();
                }
            }
        }
        return listOfPair;
    }
    public static void main(String... a){
        EmployeeForm employeeForm = new EmployeeForm();
        employeeForm.setEmployeeId(" 4564654 ");
        employeeForm.setSalary(654f);
        getNameValuePair(employeeForm);

        System.out.println(employeeForm.getEmployeeId());
        System.out.println(employeeForm.getSalary());

    }
}

