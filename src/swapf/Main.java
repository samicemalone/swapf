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

/**
 *
 * @author Sam Malone
 */
public class Main {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        for(String arg : args) {
            if(arg.equals("-h") || arg.equals("--help")) {
                printHelp();
                System.exit(0);
            }
        }
        FileList list = new FileList(args);
        if (!list.isValid()) {
            System.out.println("The input files are not valid");
            System.exit(1);
        }
        System.out.print(list.formatted());
        System.out.println();
        System.out.println("Enter the ID of the filename to swap (blank to ignore):");
        System.out.println();
        Swapper s = new Swapper(list);
        s.promptIds();
        System.out.println();
        if (!s.isSwapsValid()) {
            System.out.println("The IDs entered for swapping do not swap all ID's entered");
            System.exit(1);
        }
        if (s.isSwapListEmpty()) {
            System.exit(0);
        }
        System.out.println("Preview Swaps:");
        System.out.println();
        System.out.println(s.preview());
        System.out.print("Swap Files? y/[n]: ");
        if(s.confirmSwaps()) {
            if(!s.swap()) {
                System.out.println("An error occured when trying to rename the files. Check you have write permission to the input files");
            }
        }
    }
    
    public static void printHelp() {
        System.out.println("Usage: swapf [-h] FILE...");
        System.out.println("Swaps each input FILE names");
        System.out.println("This program is interactive. It will list the input files");
        System.out.println("with an ID and prompt for each FILE which ID's to swap with.");
        System.out.println();
        System.out.println("   -h      Displays this message and exits");
    }
}
