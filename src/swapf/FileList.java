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
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author Sam Malone
 */
public class FileList {
    
    private List<File> list;
    
    /**
     * Creates a new instance of FileList.
     * Parses the input files and builds the file list
     * @param args list of input files or directories
     */
    public FileList(String[] args) {
        list = new ArrayList<File>();
        File input;
        String[] tmpDir;
        String argPath;
        for(String arg : args) {
            if (arg.startsWith("/cygdrive/")) {
                argPath = arg.charAt(10) + ":\\" + arg.substring(12);
            } else {
                argPath = arg;
            }
            input = new File(argPath);
            if (input.isDirectory()) {
                tmpDir = input.list();
                for(String file : tmpDir) {
                    list.add(new File(input, file));
                }
            } else {
                list.add(input);
            }
        }
    }
    
    /**
     * Checks if the input files are valid (and exist)
     * @return true if valid input files, false otherwise
     */
    public boolean isValid() {
        for(File f : list) {
            if (!f.exists()) {
                return false;
            }
        }
        return !list.isEmpty();
    }
    
    /**
     * Get the file list
     * @return file list
     */
    public List<File> getList() {
        return list;
    }
    
    /**
     * Gets a formatted display string for the given file index (from 0).
     * The ID displayed to the user will start from 1.
     * @param i File index (from 0)
     * @return Formatted display string
     */
    public String formatLine(int i) {
        int padWidth = String.valueOf(list.size()).length();
        return String.format("%" + padWidth + "s) %s", String.valueOf(i+1), list.get(i).getName());
    }
    
    /**
     * Gets a formatted display string to display ID's to the user
     * @return Formatted display string
     */
    public String formatted() {
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < list.size(); i++) {
            sb.append(formatLine(i));
            sb.append('\n');
        }
        return sb.toString();
    }
    
}
