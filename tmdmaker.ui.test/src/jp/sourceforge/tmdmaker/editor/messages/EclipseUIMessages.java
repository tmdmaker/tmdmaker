/*
 * Copyright 2009-2015 TMD-Maker Project <http://tmdmaker.sourceforge.jp/>
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package jp.sourceforge.tmdmaker.editor.messages;

import org.eclipse.osgi.util.NLS;

/**
 * @author nakag
 *
 */
public class EclipseUIMessages {
	private static final String BUNDLE_NAME = "jp.sourceforge.tmdmaker.editor.messages.eclipseUIMessages"; //$NON-NLS-1$

    static {
        // initialize resource bundle
//        NLS.initializeMessages(BUNDLE_NAME, EclipseUIMessages.class);
    }
    
    public static String menuFile;
    
    public static String menuFile_New;
    
    public static String menuFile_New_Project;
    
    public static String menuFile_New_Other;
    
    public static String wizardNext;
    
    public static String wizardFinish;
}
