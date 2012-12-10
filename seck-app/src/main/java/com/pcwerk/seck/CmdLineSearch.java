package com.pcwerk.seck;

import java.io.File;
import java.util.ArrayList;

import com.pcwerk.seck.file.FileSearch;
import com.pcwerk.seck.file.FileWalker;

public class CmdLineSearch 
{

  public void run(String path, String needle) {
    ArrayList<File> fileList = new ArrayList<File>();

    FileWalker fileWalker = new FileWalker();
    fileWalker.walk(path, fileList);

    FileSearch search = new FileSearch();

    for (File haystack : fileList) {
      int count = search.count(needle, haystack);
      if (count > 0)
        System.out.printf("%5d '%s' %s\n", count, needle, haystack.getAbsoluteFile());
    }
  }
}
