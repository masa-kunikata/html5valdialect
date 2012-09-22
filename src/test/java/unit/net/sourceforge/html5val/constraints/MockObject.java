package unit.net.sourceforge.html5val.constraints;

class MockObject {

    private String property1;

    private String property2;

    private Integer intProperty;

    public static MockObject buildWithIntProperty(String property1, Integer intProperty) {
        MockObject mock = new MockObject();
        mock.property1 = property1;
        mock.intProperty = intProperty;
        return mock;
    }

    public static MockObject build(String property1, String property2) {
        MockObject mock = new MockObject();
        mock.property1 = property1;
        mock.property2 = property2;
        return mock;
    }
}
