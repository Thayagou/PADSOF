package venta.descuentos.tests;

import org.junit.platform.suite.api.SelectClasses;
import org.junit.platform.suite.api.Suite;

@SelectClasses({
    DescuentoTest.class,
    DescuentoSubTest.class
})
@Suite
public class AllDescuentosTest {
}