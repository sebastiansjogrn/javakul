package spaceinv.model;

/*
        This is for testing functionality of model objects
 */

import static java.lang.System.*;

public class Test {

    public static void main(String[] args){
        new Test().test();
    }

    void test(){
       testGun();
       //testNN();

    }

    void testGun(){
        // Create object
        Gun g = new Gun(0, 0);
//        SI si = new SI(g);

        // .. then call methods
        //out.println( g.doSomething() == true);

//        out.println(g.fire());
//        out.println(Gun.class.getTypeName());
//        out.println(si.getPositionables().get(0).getClass().toString());
    }


}
