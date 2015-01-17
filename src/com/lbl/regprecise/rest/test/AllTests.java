package com.lbl.regprecise.rest.test;

import org.junit.runner.RunWith;
import org.junit.runners.Suite;

/**
 * @author Elena Novichkova
 *
 */
@RunWith(Suite.class)
@Suite.SuiteClasses({
        DatabaseReleaseResourceTest.class,
        GenesResourceTest.class,
        GenomesResourceTest.class,
        GenomeStatsResourceTest.class,
        RegulatorsResourceTest.class,
        RegulogCollectionsResourceTest.class,
        RegulogCollectionStatsResourceTest.class,
        RegulogResourceTest.class,
        RegulogsResourceTest.class,
        RegulonResourceTest.class,
        SearchExtRegulonResourceTest.class,
        SearchRegulonResourceTest.class,
        SitesResourceTest.class
        
})
public class AllTests {
}
