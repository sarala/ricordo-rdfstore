package uk.ac.ebi.ricordo.rdfstore.service;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import static junit.framework.Assert.assertEquals;

/**
 * Created by IntelliJ IDEA.
 * User: Sarala Wimalaratne
 * Date: 27/07/12
 * Time: 16:10
 */

@RunWith(SpringJUnit4ClassRunner.class )
@ContextConfiguration(locations={"classpath:ricordo-rdfstore-config.xml"})
public class QualifierServiceTest {

    @Autowired
    private QualifierService qualifierService;

    @Test
    public void testGetQualifierList(){
        assertEquals(13, qualifierService.getQualifierList().size());
    }
}
