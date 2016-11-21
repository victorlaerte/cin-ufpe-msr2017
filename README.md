# cin-ufpe-msr2017

This page is used to share scripts to mining [Travis Torrent](https://travistorrent.testroots.org/) repository.

* [How to add script](#adding-scripts)
* [How to add other files](#adding-other-files)
* [Scripts Table](#scripts-table)
* [Files Table](#files-table)

## Adding Scripts

To add script you have to follow the sequence bellow:
  - Write your script with respective path:
  
  cin-ufpe-msr2017/scripts/{{PROGRAMING_LANGUAGE}}/{{SCRIPT_ID}}-script-{{SCRIPT_NAME}}/{{SCRIPT_FILE}}
  
  ```
  cin-ufpe-msr2017
  |- scripts
       |- programming-language
             |- id-script-script_name
                    |- script_file.extension
  
  cin-ufpe-msr2017/scripts/java/01-script-example/Example.java
  ```

  - After push your script you must update README.md with instructions with how to run and obtain results in the [Scripts Table](#scripts-table)

## Adding Other Files
To add files there's not scripts such as documents and others follow the sequence bellow:
  - Create the directory tree like:
  
  cin-ufpe-msr2017/{{DOCUMENT_TYPE}}/{{FILE_NAME}}
  
  ```
  cin-ufpe-msr2017
       |- document-type
                    |- file-name.extension
                    
  cin-ufpe-msr2017/results/output.txt
  ```

  - After push your file you must update README.md with file description [Files Table](#files-table)

## Scripts Table

Name | Link | Description | How To
------------ | ------------ | ------------- | -------------
01-script-example | [Example.java](https://github.com/victorlaerte/cin-ufpe-msr2017/blob/master/scripts/java/01-script-example/src/Example.java) | Script only for example | To run this script first you have to compile java file `javac Example.java` and after that run the code: `java Example arg1 arg2 arg3` and the result will be shown in console output
02-script-extract-new-file-with-custom-variable | [msr-mysql.sql](https://github.com/victorlaerte/cin-ufpe-msr2017/blob/master/scripts/sql/02-script-extract-new-file-with-custom-variable/msr-mysql.sql) | Script to extract only desired variables in a new csv file | To run this script first you have to execute it with Mysql DB

## Files Table

Name | Link | Description
------------ | ------------ | -------------
output.txt | [output.txt](https://github.com/victorlaerte/cin-ufpe-msr2017/blob/master/results/output.txt) | Files showing result of example script

feel free to add more :kissing_heart: 
