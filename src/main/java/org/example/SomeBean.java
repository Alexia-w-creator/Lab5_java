package org.example;

public class SomeBean {
    @AutoInjectable
    private SomeInterface field1;
    @AutoInjectable
    private SomeOtherInterface field2;

    public void doSth()
    {
        field1.doSomething();
        field2.doSomeOther();
    }
}