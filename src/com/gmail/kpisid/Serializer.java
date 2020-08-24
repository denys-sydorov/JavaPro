package com.gmail.kpisid;

import java.io.*;
import java.lang.reflect.*;
import java.security.InvalidParameterException;


public class Serializer {

    public static String serialize(Object o) throws  Exception {

        Class<?> cls = o.getClass();
        StringBuilder sb = new StringBuilder();

        Field[] fields = cls.getDeclaredFields();
        for(Field f : fields){
            if(! f.isAnnotationPresent(Save.class)) {
                continue;
            }
            if(Modifier.isPrivate(f.getModifiers())){
                    f.setAccessible(true);
                }
            sb.append(f.getName() + " = ");

            if (f.getType() == int.class) {
                sb.append(f.getInt(o));
            } else if (f.getType() == String.class) {
                sb.append((String) f.get(o));
            } else if (f.getType() == long.class) {
                sb.append(f.getLong(o));
            }
            sb.append(";");
        }
        return sb.toString();
    }

    public static <T> T deserialize(String s, Class<T> cls) throws Exception {
        T res = (T)cls.newInstance();
        String[] pairs = s.split(";");

        for (String p : pairs) {
            String[] nv = p.split(" = ");
            if (nv.length != 2)
                throw new InvalidParameterException(s);
            String name = nv[0];
            String value = nv[1];
            Field f = cls.getDeclaredField(name);
            if (Modifier.isPrivate(f.getModifiers()))
                f.setAccessible(true);

            if (f.isAnnotationPresent(Save.class)) {
                if (f.getType() == int.class) {
                    f.setInt(res, Integer.parseInt(value));
                } else if (f.getType() == String.class) {
                    f.set(res, value);
                } else if (f.getType() == long.class) {
                    f.setLong(res, Long.parseLong(value));
                }
            }
        }
        return res;
    }
}
