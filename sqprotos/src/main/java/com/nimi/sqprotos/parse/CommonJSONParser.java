package com.wangteng.sigleshopping.parse;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

/**
 * 数据解析工具类  ，因为怕数据结构变化，所以没有 写实体类
 */
public class CommonJSONParser {
    public Map<String, Object> parse(String jsonStr) {
        Map<String, Object> result = null;

        if (null != jsonStr) {
            try {

                JSONObject jsonObject = new JSONObject(jsonStr);
                result = parseJSONObject(jsonObject);

            } catch (JSONException e) {
                // TODO Auto-generated catch block 
                e.printStackTrace();
            }
        } // if (null != jsonStr) 

        return result;
    }


    public Object parseValue(Object inputObject) throws JSONException {
        Object outputObject = null;

        if (null != inputObject) {
            if (inputObject instanceof JSONArray) {
                outputObject = parseJSONArray((JSONArray) inputObject);
            } else if (inputObject instanceof JSONObject) {
                outputObject = parseJSONObject((JSONObject) inputObject);
            } else if (inputObject instanceof String || inputObject instanceof Boolean || inputObject instanceof Integer || inputObject instanceof Float || inputObject instanceof Double) {
                outputObject = inputObject;
            }

        }

        return outputObject;
    }

    public List<Object> parseJSONArray(JSONArray jsonArray) throws JSONException {

        List<Object> valueList = null;

        if (null != jsonArray) {
            valueList = new ArrayList<Object>();

            for (int i = 0; i < jsonArray.length(); i++) {
                Object itemObject = jsonArray.get(i);
                if (null != itemObject) {
                    valueList.add(parseValue(itemObject));
                }
            } // for (int i = 0; i < jsonArray.length(); i++) 
        } // if (null != valueStr) 

        return valueList;
    }

    public Map<String, Object> parseJSONObject(JSONObject jsonObject) throws JSONException {

        Map<String, Object> valueObject = null;
        if (null != jsonObject) {
            valueObject = new HashMap<String, Object>();

            Iterator<String> keyIter = jsonObject.keys();
            while (keyIter.hasNext()) {
                String keyStr = keyIter.next();
                Object itemObject = jsonObject.opt(keyStr);
                if (null != itemObject) {
                    valueObject.put(keyStr, parseValue(itemObject));
                } // if (null != itemValueStr) 

            } // while (keyIter.hasNext()) 
        } // if (null != valueStr) 

        return valueObject;
    }


    /**
     * json对象转换成Bean对象
     *
     * @param jso   json对象
     * @param clazz 需要转成的bean的.class对象
     * @param <T>   转化成的bean类型
     * @return 转化成的bean
     * @throws Exception
     */
    public static <T> T parseJsonToJavaBean(JSONObject jso, Class<T> clazz) throws Exception {
        T t = clazz.newInstance();
        Field[] fs = clazz.getDeclaredFields();
        for (Field field : fs) {
            String fieldName = field.getName();
            //_id根据自定义的bean做相应修改  
            if ("_id".equals(fieldName)) {
                continue;
            }
            Method m = clazz.getDeclaredMethod("set" + toUpperCaseFirstOne(fieldName), field.getType());
            Object arg = jso.opt(fieldName);
            if (m != null && m.getName() != null && arg != null) {
                if (!arg.toString().equals("null") && !arg.toString().equals("")) {
                    if (field.getType().getName().equals("int")) {
                        m.invoke(t, Integer.valueOf(arg.toString().trim()));
                    } else if (field.getType().getName().equals("long")) {
                        m.invoke(t, Long.valueOf(arg.toString().trim()));
                    } else if (field.getType().getName().equals("short")) {
                        m.invoke(t, Short.valueOf(arg.toString().trim()));
                    } else {
                        m.invoke(t, arg);
                    }
                }
            }
        }
        return t;
    }

    public static <T> List<T> parseJsonToBeanList(String jsonStr, Class<T> clazz) throws Exception {

        List<T> list = null; //包含的实体列表  

        list = new ArrayList<T>();
        JSONArray jArray = new JSONArray(jsonStr);

        for (int i = 0; i < jArray.length(); i++) {
            JSONObject jso = (JSONObject) jArray.opt(i);

            Field[] fs = clazz.getDeclaredFields();

            T t = clazz.newInstance();

            for (Field field : fs) {
                String fieldName = field.getName();
                if ("_id".equals(fieldName)) {
                    //_id根据自定义的bean做相应修改  
                    continue;
                }
                Method m = clazz.getDeclaredMethod("set" + toUpperCaseFirstOne(fieldName), field.getType());
                Object arg = jso.opt(fieldName);
                if (m != null && m.getName() != null && arg != null) {
                    if (!arg.toString().equals("null") && !arg.toString().equals("")) {

                        if (field.getType().getName().equals("int")) {
                            m.invoke(t, Integer.valueOf(arg.toString()));
                        } else if (field.getType().getName().equals("long")) {
                            m.invoke(t, Long.valueOf(arg.toString()));
                        } else if (field.getType().getName().equals("short")) {
                            m.invoke(t, Short.valueOf(arg.toString()));
                        } else {
                            m.invoke(t, arg);
                        }
                    }
                }
            }
            list.add(t);
        }
        return list;
    }

    public static String toUpperCaseFirstOne(String s) {
        if (Character.isUpperCase(s.charAt(0)))
            return s;
        else
            return (new StringBuilder()).append(Character.toUpperCase(s.charAt(0))).append(s.substring(1)).toString();
    }

} 

