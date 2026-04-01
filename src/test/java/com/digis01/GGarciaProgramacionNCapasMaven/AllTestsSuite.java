package com.digis01.GGarciaProgramacionNCapasMaven;

import org.junit.platform.suite.api.SelectClasses;
import org.junit.platform.suite.api.Suite;
import org.junit.platform.suite.api.SuiteDisplayName;

@Suite
@SuiteDisplayName("Suite Completa - GGarciaProgramacionNCapas")
@SelectClasses({CatalogosTest.class, GGarciaProgramacionNCapasMavenApplicationTests.class})
public class AllTestsSuite {

}
