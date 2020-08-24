package com.gmail.kpisid;

public class Main {
    public static void main(String[] args) throws Exception{
        Test t = new Test();
        t.setA(7);
        t.setB("Hello");

        String res = Serializer.serialize(t);
        System.out.println("Serialized" + res);

        t = Serializer.deserialize(res, Test.class);
        //System.out.println("Deserialized: " + t.getA() + ", " + t.getB());
        System.out.println("Deserialized: " + t.getA() + ", " + t.getB() + ", " + t.getC());
    }
}
