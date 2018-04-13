package com.workspaceit.pos.util;


import com.workspaceit.pos.entity.Supplier;
import com.workspaceit.pos.validation.form.employee.EmployeeForm;
import com.workspaceit.pos.validation.form.supplier.SupplierForm;
import org.apache.commons.text.StringEscapeUtils;
import org.apache.http.message.BasicNameValuePair;

import java.beans.IntrospectionException;
import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class DataFilterUtil {
    /**
     * Trim and Script Escape
     * */
    public static String basicFilter(String value){
        if(value==null)return value;
        value = StringEscapeUtils.escapeEcmaScript(value.trim());
        return value;
    }


    public static void main(String... a){
        EmployeeForm employeeForm = new EmployeeForm();
        employeeForm.setEmployeeId(" 4564654 ");
        employeeForm.setSalary(654f);
        System.out.println(employeeForm.getClass().equals(System.class));
       // employeeForm = DataFilterUtil.getNameValuePair(employeeForm,EmployeeForm.class);
        System.out.println(employeeForm.getEmployeeId());
    }

}
