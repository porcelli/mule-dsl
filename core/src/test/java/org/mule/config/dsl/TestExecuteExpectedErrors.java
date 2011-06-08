/*
 * ---------------------------------------------------------------------------
 * Copyright (c) MuleSoft, Inc.  All rights reserved.  http://www.mulesoft.com
 *
 * The software in this package is published under the terms of the CPAL v1.0
 * license, a copy of which has been included with this distribution in the
 * LICENSE.txt file.
 */

package org.mule.config.dsl;

import com.google.inject.name.Named;
import com.google.inject.name.Names;
import org.junit.Test;
import org.mule.api.lifecycle.Callable;

import java.lang.annotation.*;

import static org.mule.config.dsl.expression.CoreExpr.payload;

public class TestExecuteExpectedErrors {

    @Test(expected = RuntimeException.class)
    public void nullCallable() {
        Mule.newMuleContext(new AbstractModule() {
            @Override
            public void configure() {
                flow("MyFlow")
                        .from("file:///Users/porcelli/test")
                        .execute((Callable) null);
            }
        });
    }

    @Test(expected = RuntimeException.class)
    public void nullObject() {
        Mule.newMuleContext(new AbstractModule() {
            @Override
            public void configure() {
                flow("MyFlow")
                        .from("file:///Users/porcelli/test")
                        .execute((Object) null);
            }
        });
    }

    @Test(expected = RuntimeException.class)
    public void nullType() {
        Mule.newMuleContext(new AbstractModule() {
            @Override
            public void configure() {
                flow("MyFlow")
                        .from("file:///Users/porcelli/test")
                        .execute((Class<?>) null);
            }
        });
    }

    @Test(expected = RuntimeException.class)
    public void methodArgNull() {
        Mule.newMuleContext(new AbstractModule() {
            @Override
            public void configure() {
                flow("MyFlow")
                        .from("file:///Users/porcelli/test")
                        .execute(Complex.class).methodAnnotatedWith((Class<? extends Annotation>) null);
            }
        });
    }

    @Test(expected = RuntimeException.class)
    public void methodArgNull2() {
        Mule.newMuleContext(new AbstractModule() {
            @Override
            public void configure() {
                flow("MyFlow")
                        .from("file:///Users/porcelli/test")
                        .execute(Complex.class).methodAnnotatedWith((Annotation) null);
            }
        });
    }

    @Test(expected = RuntimeException.class)
    public void methodArgsExpected() {
        Mule.newMuleContext(new AbstractModule() {
            @Override
            public void configure() {
                flow("MyFlow")
                        .from("file:///Users/porcelli/test")
                        .execute(Complex.class).methodAnnotatedWith(Names.named("ToExecute")).withoutArgs();
            }
        });
    }

    @Test(expected = RuntimeException.class)
    public void methodArgsExpectedTyped() {
        Mule.newMuleContext(new AbstractModule() {
            @Override
            public void configure() {
                flow("MyFlow")
                        .from("file:///Users/porcelli/test")
                        .execute(Complex2.class).methodAnnotatedWith(ToExecute.class).withoutArgs();
            }
        });
    }

    @Test(expected = RuntimeException.class)
    public void cantFindAnnotationTyped() {
        Mule.newMuleContext(new AbstractModule() {
            @Override
            public void configure() {
                flow("MyFlow")
                        .from("file:///Users/porcelli/test")
                        .execute(Complex2.class).methodAnnotatedWith(ToExecute2.class).withoutArgs();
            }
        });
    }

    @Test(expected = RuntimeException.class)
    public void cantFindAnnotation() {
        Mule.newMuleContext(new AbstractModule() {
            @Override
            public void configure() {
                flow("MyFlow")
                        .from("file:///Users/porcelli/test")
                        .execute(Complex2.class).methodAnnotatedWith(Names.named("XXX")).withoutArgs();
            }
        });
    }

    @Test(expected = RuntimeException.class)
    public void methodAndArgsDontMatch() {
        Mule.newMuleContext(new AbstractModule() {
            @Override
            public void configure() {
                flow("MyFlow")
                        .from("file:///Users/porcelli/test")
                        .execute(Complex2.class).methodAnnotatedWith(ToExecute2.class).args(payload());
            }
        });
    }

    @Test(expected = RuntimeException.class)
    public void methodAndArgsDontMatch2() {
        Mule.newMuleContext(new AbstractModule() {
            @Override
            public void configure() {
                flow("MyFlow")
                        .from("file:///Users/porcelli/test")
                        .execute(Complex.class).methodAnnotatedWith(Names.named("ToExecute2")).args(payload());
            }
        });
    }


    public static class Complex {
        @Named("ToExecute")
        void execute(String string) {

        }

        @Named("ToExecute2")
        void execute2() {

        }

    }

    public static class Complex2 {
        @ToExecute
        void execute(String string) {

        }

        @ToExecute2
        void execute2() {

        }

    }

    @Target({ElementType.METHOD})
    @Retention(RetentionPolicy.RUNTIME)
    public @interface ToExecute2 {
    }

    @Target({ElementType.METHOD})
    @Retention(RetentionPolicy.RUNTIME)
    public @interface ToExecute {
    }

}
