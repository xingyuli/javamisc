package org.swordess.toy.javamisc.proxy;

import org.junit.Assert;
import org.junit.Test;

import java.lang.reflect.Proxy;

public class JdkProxyTest {

    @Test
    public void testSimple() {
        Shape original = new Triangle();

        Object proxied = Proxy.newProxyInstance(original.getClass().getClassLoader(), original.getClass().getInterfaces(), (proxy, method, args) -> {
            if (method.getName().equals("name")) {
                return method.invoke(original, args) + " enhanced";
            }
            return method.invoke(original, args);
        });

        Assert.assertEquals("San Jiao Xing enhanced", ((Shape) proxied).name());
        Assert.assertEquals(0, ((Shape) proxied).version());
    }

    interface Shape {
        String name();
        int version();
    }

    class Triangle implements Shape {

        private int version = 0;

        @Override
        public String name() {
            return "San Jiao Xing";
        }

        @Override
        public int version() {
            return version;
        }

    }

}
