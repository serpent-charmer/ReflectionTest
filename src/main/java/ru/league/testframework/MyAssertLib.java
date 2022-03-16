package ru.league;

import java.lang.annotation.Annotation;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;

public class MyAssertLib {

    public static void assertEquals(Object o1, Object o2) {
        if(!o1.equals(o2)) {
            throw new MyAssertException(String.format("Expected: %s, got: %s", o1, o2));
        }
    }

    public static void assertTrue(boolean statement) {
        if(!statement) {
            throw new MyAssertException("assertTrue failed");
        }
    }

    public static void assertNotNull(Object o) {
        if(o == null) {
            throw new MyAssertException("assertNotNull failed");
        }
    }

    public static void runTests(Class<?> type) {
        Method before = null;
        Method after = null;
        ArrayList<Method> tests = new ArrayList<>();
        for (Method method : type.getMethods()) {
            for (Annotation ann : method.getDeclaredAnnotations()) {
                Class<?> annType = ann.annotationType();
                if (annType.equals(Before.class)) {
                    before = method;
                }
                if (annType.equals(After.class)) {
                    after = method;
                }
                if (annType.equals(Test.class)) {
                    tests.add(method);
                }

            }
        }


        ArrayList<MyAssertException> errs = new ArrayList<>();

        try {
            Object obj = type.newInstance();

            if (before != null) {
                before.invoke(obj);
            }

            for (Method test : tests) {
                try {
                    test.invoke(obj);
                } catch (InvocationTargetException e) {
                    Throwable cause = e.getCause();
                    if (cause instanceof MyAssertException) {
                        errs.add((MyAssertException)cause);
                    }
                }
            }

            if (after != null) {
                after.invoke(obj);
            }

        } catch (InstantiationException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (InvocationTargetException e) {
            e.printStackTrace();
        }

        for (MyAssertException err:
                errs) {
            err.printStackTrace();
        }

    }

}
