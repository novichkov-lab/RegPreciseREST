package com.lbl.regprecise.rest.test;

import org.junit.runner.RunWith;
import org.junit.runners.Suite;

/**
 * @author Elena Novichkova
 *
 */
@RunWith(Suite.class)
@Suite.SuiteClasses({
        GenomesResourceTest.class,
        RegulonResourceTest.class
})
public class AllTests {
}
