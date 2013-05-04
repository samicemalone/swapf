/*
 * Copyright (c) 2013, Sam Malone. All rights reserved.
 * 
 * Redistribution and use of this software in source and binary forms, with or
 * without modification, are permitted provided that the following conditions
 * are met:
 * 
 *  - Redistributions of source code must retain the above copyright notice,
 *    this list of conditions and the following disclaimer.
 *  - Redistributions in binary form must reproduce the above copyright notice,
 *    this list of conditions and the following disclaimer in the documentation
 *    and/or other materials provided with the distribution.
 *  - Neither the name of Sam Malone nor the names of its contributors may be
 *    used to endorse or promote products derived from this software without
 *    specific prior written permission.
 * 
 * THIS SOFTWARE IS PROVIDED BY THE COPYRIGHT HOLDERS AND CONTRIBUTORS "AS IS"
 * AND ANY EXPRESS OR IMPLIED WARRANTIES, INCLUDING, BUT NOT LIMITED TO, THE
 * IMPLIED WARRANTIES OF MERCHANTABILITY AND FITNESS FOR A PARTICULAR PURPOSE
 * ARE DISCLAIMED. IN NO EVENT SHALL THE COPYRIGHT OWNER OR CONTRIBUTORS BE
 * LIABLE FOR ANY DIRECT, INDIRECT, INCIDENTAL, SPECIAL, EXEMPLARY, OR
 * CONSEQUENTIAL DAMAGES (INCLUDING, BUT NOT LIMITED TO, PROCUREMENT OF
 * SUBSTITUTE GOODS OR SERVICES; LOSS OF USE, DATA, OR PROFITS; OR BUSINESS
 * INTERRUPTION) HOWEVER CAUSED AND ON ANY THEORY OF LIABILITY, WHETHER IN 
 * CONTRACT, STRICT LIABILITY, OR TORT (INCLUDING NEGLIGENCE OR OTHERWISE)
 * ARISING IN ANY WAY OUT OF THE USE OF THIS SOFTWARE, EVEN IF ADVISED OF THE
 * POSSIBILITY OF SUCH DAMAGE.
 */
package swapf;

import java.io.File;
import java.util.List;

/**
 *
 * @author Sam Malone
 */
public class Display {
    
    /**
     * Gets a formatted display string to display ID's to the user
     * with the file name
     * @param list File list
     * @return Formatted display string e.g. 1) File.Name.ext
     */
    public static String formattedFileList(List<File> list) {
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < list.size(); i++) {
            sb.append(numberedFileName(list, i));
            sb.append('\n');
        }
        return sb.toString();
    }
    
    /**
     * Gets a formatted display string to show the user the display ID and 
     * filename for the given file list with index (from 0). The ID displayed 
     * to the user will start from 1.
     * @param list File list
     * @param index File index to display in list (from 0)
     * @return String with display ID and file name e.g. 1) File.Name.ext
     */
    public static String numberedFileName(List<File> list, int index) {
        int padWidth = String.valueOf(list.size()).length();
        return String.format("%" + padWidth + "s) %s", String.valueOf(index+1), list.get(index).getName());
    }
    
    /**
     * Print the help message
     */
    public static void printHelp() {
        System.out.println("Usage: swapf [-h] FILE...");
        System.out.println("Swaps each input FILE names");
        System.out.println("This program is interactive. It will list the input files");
        System.out.println("with an ID and prompt for each FILE which ID's to swap with.");
        System.out.println();
        System.out.println("   -h      Displays this message and exits");
    }
    
}
