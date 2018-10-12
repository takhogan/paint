package com.tak.test;
import com.tak.webobjects.*;
import com.tak.webobjects.inputobjects.*;

public class Tester {
    public static void main(String[] args) {

        NamedValue nv = new NamedValue("min","max");
        NamedValue nv2 = new NamedValue("min", "mox");
        NamedValue nv3 = new NamedValue("min", "max");
        System.out.println("nv==nv2 = " + (nv.equals(nv2)));
        System.out.println("(nv==nv3) = " + (nv.equals(nv3)));
        System.out.println("(nv2==nv3) = " + (nv2.equals(nv3)));

    }



}
