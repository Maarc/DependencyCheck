/*
 * Copyright 2015 OWASP.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package org.owasp.dependencycheck.analyzer;

import java.io.File;
import java.io.FileFilter;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.*;
import static org.junit.Assume.assumeFalse;
import static org.junit.Assume.assumeNotNull;
import org.owasp.dependencycheck.BaseTest;
import org.owasp.dependencycheck.Engine;
import org.owasp.dependencycheck.dependency.Dependency;
import org.owasp.dependencycheck.utils.Settings;

/**
 *
 * @author jeremy
 */
public class ArchiveAnalyzerTest extends BaseTest {

    @Before
    public void setUp() {
        Settings.setString(Settings.KEYS.ADDITIONAL_ZIP_EXTENSIONS, "z2, z3");
    }

    /**
     * Test of analyzeFileType method, of class ArchiveAnalyzer.
     */
    @Test
    public void testZippableExtensions() throws Exception {
        assumeFalse(isPreviouslyLoaded("org.owasp.dependencycheck.analyzer.ArchiveAnalyzer"));
        ArchiveAnalyzer instance = new ArchiveAnalyzer();
        assertTrue(instance.getFileFilter().accept(new File("c:/test.zip")));
        assertTrue(instance.getFileFilter().accept(new File("c:/test.z2")));
        assertTrue(instance.getFileFilter().accept(new File("c:/test.z3")));
        assertFalse(instance.getFileFilter().accept(new File("c:/test.z4")));
    }

    private boolean isPreviouslyLoaded(String className) {
        try {
            Method m = ClassLoader.class.getDeclaredMethod("findLoadedClass", new Class[]{String.class});
            m.setAccessible(true);
            Object t = m.invoke(Thread.currentThread().getContextClassLoader(), className);
            return t != null;
        } catch (NoSuchMethodException ex) {
            Logger.getLogger(ArchiveAnalyzerTest.class.getName()).log(Level.SEVERE, null, ex);
        } catch (SecurityException ex) {
            Logger.getLogger(ArchiveAnalyzerTest.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            Logger.getLogger(ArchiveAnalyzerTest.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IllegalArgumentException ex) {
            Logger.getLogger(ArchiveAnalyzerTest.class.getName()).log(Level.SEVERE, null, ex);
        } catch (InvocationTargetException ex) {
            Logger.getLogger(ArchiveAnalyzerTest.class.getName()).log(Level.SEVERE, null, ex);
        }
        return false;
    }
}
