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
import java.util.HashMap;
import java.util.Random;

/**
 *
 * @author Sam Malone
 */
public class TempFile {
    
    private HashMap<File, File> tempFiles;
    
    public TempFile() {
        tempFiles = new HashMap<File, File>();
    }
    
    /**
     * Gets a temporary file based on the given file. The temporary file
     * name will contain a random string appended to the file name with
     * .tmp as the suffix. The temporary file will will not exist. The
     * temp file parent will point to the parent file of the input file
     * parameter
     * @param file File to base temporary file on
     * @return Temporary file that does not exist
     */
    public File getTempFile(File file) {
        if(!tempFiles.containsKey(file)) {
            File tmpFile;
            while((tmpFile = new File(file.getParentFile(), generateTempFileName(file))).exists());
            tempFiles.put(file, tmpFile);
        }
        return tempFiles.get(file);
    }
    
    /**
     * Generate a temporary file name for the file. The temporary file
     * name will contain a random string appended to the file name with
     * .tmp as the suffix.
     * @param file File to generate a temporary file name for
     * @return Temporary file name
     */
    private String generateTempFileName(File file) {
        final String alphabet = "0123456789abcdefghijklmnpqrstuvwxyz";
        final String extension = ".tmp";
        final int tmpFileNameLength = 12;
        Random r = new Random();
        StringBuilder sb;
            sb = new StringBuilder(file.getName().length() + tmpFileNameLength + extension.length() + 1);
            sb.append(file.getName());
            for(int i = 0; i < tmpFileNameLength; i++) {
                sb.append(alphabet.charAt(r.nextInt(alphabet.length())));
            }
            sb.append(extension);
        return sb.toString();
    }
    
}
