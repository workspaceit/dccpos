package com.workspaceit.dccpos.util;


import com.workspaceit.dccpos.validation.form.employee.EmployeeForm;
import org.apache.commons.text.StringEscapeUtils;

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
