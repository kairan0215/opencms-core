/*
 * File   : $Source: /alkacon/cvs/opencms/test/org/opencms/file/Attic/TestCmsObjectTouch.java,v $
 * Date   : $Date: 2004/05/26 14:58:36 $
 * Version: $Revision: 1.2 $
 *
 * This library is part of OpenCms -
 * the Open Source Content Mananagement System
 *
 * Copyright (C) 2002 - 2004 Alkacon Software (http://www.alkacon.com)
 *
 * This library is free software; you can redistribute it and/or
 * modify it under the terms of the GNU Lesser General Public
 * License as published by the Free Software Foundation; either
 * version 2.1 of the License, or (at your option) any later version.
 *
 * This library is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the GNU
 * Lesser General Public License for more details.
 *
 * For further information about Alkacon Software, please see the
 * company website: http://www.alkacon.com
 *
 * For further information about OpenCms, please see the
 * project website: http://www.opencms.org
 * 
 * You should have received a copy of the GNU Lesser General Public
 * License along with this library; if not, write to the Free Software
 * Foundation, Inc., 59 Temple Place, Suite 330, Boston, MA  02111-1307  USA
 */
 
package org.opencms.file;

import org.opencms.main.I_CmsConstants;
import org.opencms.test.OpenCmsTestCase;
import org.opencms.test.OpenCmsTestResourceFilter;

/**
 * Unit test for the "touch" method of the CmsObject.<p>
 * 
 * @author Michael Emmerich (m.emmerich@alkacon.com)
 * @version $Revision: 1.2 $
 */
public class TestCmsObjectTouch extends OpenCmsTestCase {
        
    
    /**
     * Default JUnit constructor.<p>
     * 
     * @param arg0 JUnit parameters
     */    
    public TestCmsObjectTouch(String arg0) {
        super(arg0);
    }
    
    
    /**
     * Tests the touch method in the CmsObject.<p>
     * 
     * @throws Throwable if something goes wrong
     */
    public void testCmsObjectTouch() throws Throwable {
        
        // setup OpenCms
        CmsObject cms = setupOpenCms("simpletest", "/sites/default/");
              
        // now do the test itself
        
        String resource1 = "/release/installation.html";            

        storeResources(cms, resource1);
 
        // do the touch operation
        long timestamp = System.currentTimeMillis();
        cms.touch(resource1, timestamp, false);
       
        // now evaluate the result
        assertFilter(cms, resource1, OpenCmsTestResourceFilter.FILTER_TOUCH);
        // project must be current project
        assertProject(cms, resource1, cms.getRequestContext().currentProject());
        // state must be "changed"
        assertState(cms, resource1, I_CmsConstants.C_STATE_CHANGED);
        // date last modified must be the date set in the tough operation
        assertDateLastModified(cms, resource1, timestamp);
        // the user last modified must be the current user
        assertUserLastModified(cms, resource1, cms.getRequestContext().currentUser());
        
        // remove OpenCms
        removeOpenCms();
    }
    
}
