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

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author Sam Malone
 */
public class Swapper {
    
    public static final int SWAP_ID_INVALID = -1;
    public static final int EMPTY_INPUT = -2;
    public static final int DUPLICATE_SWAP_ID = -3;
    
    private List<File> fileList;
    private List<Integer> swapIds;
    
    public Swapper(List<File> list) {
        this.fileList = list;
        swapIds = new ArrayList<Integer>(list.size());
    }
    
    /**
     * Swaps all the files in list with the user entered swapIds
     * @return true if all the files were renamed. false otherwise
     */
    public boolean swap() {
        if(renameTemp()) {
            swapFiles();
            return true;
        }
        return false;
    }
    
    /**
     * Swaps the files in list with the user entered swapIds
     * @return true if all the files were renamed. false otherwise
     */
    private boolean swapFiles() {
        File tmp;
        boolean renamedAll = true;
        for(int i = 0; i < swapIds.size(); i++) {
            if(swapIds.get(i) != EMPTY_INPUT) {
                tmp = getTempFile(fileList.get(i));
                if(tmp.renameTo(fileList.get(swapIds.get(i)))) {
                    renamedAll &= true;
                } else {
                    renamedAll = false;
                }
            }
        }
        return renamedAll;
    }
    
    /**
     * Renames each file in list to a temporary name.
     * Each file is checked for write permissions before attempting renaming
     * @return true if successful, false if error writing or renaming
     */
    private boolean renameTemp() {
        // check all files are writable before renaming
        for(int i = 0; i < swapIds.size(); i++) {
            if(swapIds.get(i) != EMPTY_INPUT) {
                if(!fileList.get(i).canWrite()) {
                    return false;
                }
            }
        }
        // all files are writable here
        for(int i = 0; i < swapIds.size(); i++) {
            if(swapIds.get(i) != EMPTY_INPUT) {
                if(!fileList.get(i).renameTo(getTempFile(fileList.get(i)))) {
                    return false;
                }
            }
        }
        return true;
    }
    
    private File getTempFile(File f) {
        return new File(f.getParentFile(), f.getName() + ".tmp");
    }
    
    /**
     * Checks if the ID's the user has entered are valid swaps
     * @return true if the swaps are valid, false otherwise
     */
    public boolean isSwapsValid() {
        boolean valid = true;
        for (int i = 0; i < swapIds.size(); i++) {
            if (swapIds.get(i) != EMPTY_INPUT) {
                // (1) swapIds.get(i) is the index of the filename to be swapped to.
                // (2) swapIds.get(swapIds.get(i)) is the index of the filename at the position
                // where the filename at (1) would be renamed to.
                // If (2) has no index to rename to, then (2) filename would stay the same
                // and (1) filename cant be renamed because (2) already exists.
                if (swapIds.get(swapIds.get(i)) == EMPTY_INPUT) {
                    valid = false;
                }
            }
        }
        return valid;
    }
    
    /**
     * Checks if the user has entered any swaps.
     * @return true if swap list is empty, false otherwise
     */
    public boolean isSwapListEmpty() {
        boolean empty = true;
        for (int i = 0; i < swapIds.size(); i++) {
            if (swapIds.get(i) != EMPTY_INPUT) {
                empty = false;
                break;
            }
        }
        return empty;
    }
    
    /**
     * Gets the formatted text previews of the files to be swapped
     * @return formatted preview string
     */
    public String preview() {
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < swapIds.size(); i++) {
            if (swapIds.get(i) != EMPTY_INPUT) {
                sb.append(fileList.get(i).getName());
                sb.append("\n => ");
                sb.append(fileList.get(swapIds.get(i)).getName());
                sb.append('\n');
            }
        }
        return sb.toString();
    }
    
    /**
     * Prompts the user for the input ID's to be swapped
     */
    public void promptIds() {
        int tmpInput;
        for (int i = 0; i < fileList.size(); i++) {
            System.out.print(Display.numberedFileName(fileList, i));
            System.out.print(" => ");
            while ((tmpInput = readUserInput()) < 0) {
                if(tmpInput == SWAP_ID_INVALID) {
                    displayPromptIdsError(i, "Invalid filename ID");
                } else if (tmpInput == DUPLICATE_SWAP_ID) {
                    displayPromptIdsError(i, "You can only use each ID once");
                } else {
                    break;
                }
            }
            // only add if input id is not same as current file
            if (tmpInput == i) {
                swapIds.add(EMPTY_INPUT);
            } else {
                swapIds.add(tmpInput);
            }
        }
    }
    
    /**
     * Displays an error message for the previous user input and redisplays
     * the same prompt for the given index
     * @param index file index
     * @param message error message
     */
    private void displayPromptIdsError(int index, String message) {
        System.out.println(message);
        System.out.print(Display.numberedFileName(fileList, index));
        System.out.print(" => ");
    }
    
    /**
     * Reads a list index from user input filename ID
     * @return list index or error as SWAP_ID_INVALID, DUPLICATE_SWAP_ID, EMPTY_INPUT
     */
    public int readUserInput() {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        int i = SWAP_ID_INVALID;
        try {
            String input = br.readLine();
            if (input.isEmpty()) {
                return EMPTY_INPUT;
            }
            // user display index start from 1, so decrement for real index
            i = Integer.parseInt(input) - 1;
            if (i < 0 || i >= fileList.size()) {
                return SWAP_ID_INVALID;
            }
            if (swapIds.contains(i)) {
                return DUPLICATE_SWAP_ID;
            }
        } catch (NumberFormatException e) {

        } catch (IOException e) {
            return EMPTY_INPUT;
        }
        return i;
    }
    
    /**
     * Prompts the user for confirmation
     * @return true if the user chose yes, false otherwise
     */
    public boolean confirmSwaps() {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        try {
            if(br.readLine().equalsIgnoreCase("y")) {
                return true;
            }
        } catch(IOException ex) {
            
        }
        return false;
    }
    
}
