NAME
   swapf - Lets you swap the filenames of the input files.

SYNOPSIS
   swapf FILE... [-h]

DESCRIPTION
   "swapf" is an interactive console application that lets you swap the file
   names of the input FILEs.
   
   Each input file will be listed with an ID that refers to it.

   Then for each file, the user will be prompted for an ID to swap the current
   file name with.

   Once the ID's to swap have been entered, the user will be shown a preview of
   the swaps that would be made. The user will then be then prompted with a
   choice of whether to accept the swaps or not.

   If an error occurs whilst renaming the files, an attempt will be made to
   rollback the files to their original names.

OPTIONS

   FILE...
      The FILE argument(s) are the file paths for the files that are to have
      their file names swapped. If FILE is a directory, only the files in the
      directory (i.e. no subdirectories) will be added as input files to swap

   -h, --help
      The help message will be output and the program will exit.

COPYRIGHT
   Copyright (c) 2013, Sam Malone. All rights reserved.

LICENSING
   The swapf source code, binaries and documentation are licensed under a BSD
   License. See LICENSE for details.

AUTHOR
   Sam Malone