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
03-script-create-csv-to-logist-regression | [MsrMain.java](https://github.com/victorlaerte/cin-ufpe-msr2017/tree/master/scripts/java) | Script to exctract csv to be used in R run logistic regression. Some of variables are classified (1 or 0, based in metric used) | This script is very specific to our problem, probability it will not accept arguments
04-script-append-file | [MsrMain.java](https://github.com/victorlaerte/cin-ufpe-msr2017/blob/master/scripts/java/04-script-append-file/MsrMain.java) | Script to append second file into the first  | To run this script first you have to compile java file `javac MsrMain.java` and after that run the code: `java Example filePath1 filePath2` file of the second argument will be appended into the first

## Files Table

Name | Link | Description
------------ | ------------ | -------------
output.txt | [output.txt](https://github.com/victorlaerte/cin-ufpe-msr2017/blob/master/results/output.txt) | Files showing result of example script
msr-final-5.csv | [msr-final-5.csv](https://github.com/victorlaerte/cin-ufpe-msr2017/blob/master/results/msr-final-5.csv) | Files adapted to run logistic regression with R - Two samples together (5% used to categorize variables)
msr-final-1.csv | [msr-final-1.csv](https://github.com/victorlaerte/cin-ufpe-msr2017/blob/master/results/msr-final-1.csv) | Files adapted to run logistic regression with R - Two samples together (1% used to categorize variables)
tr_status_PASSED-tr_tests_failed_TRUE-60947-mined-5.csv | [BPTF-60947-5](https://github.com/victorlaerte/cin-ufpe-msr2017/blob/master/results/tr_status_PASSED-tr_tests_failed_TRUE-60947-mined-5.csv) | File adapted to run logistic regression with R - Sample BPTF (5% used to categorize variables)
tr_status_PASSED-tr_tests_failed_TRUE-60947-mined-1.csv | [BPTF-60947-1](https://github.com/victorlaerte/cin-ufpe-msr2017/blob/master/results/tr_status_PASSED-tr_tests_failed_TRUE-60947-mined-1.csv) | File adapted to run logistic regression with R - Sample BPTF (1% used to categorize variables)
tr_status_FAILED-tr_tests_failed_TRUE-103350-mined-5.csv | [BFTF-103350-5](https://github.com/victorlaerte/cin-ufpe-msr2017/blob/master/results/tr_status_FAILED-tr_tests_failed_TRUE-103350-mined-5.csv) | File adapted to run logistic regression with R - Sample BFTF (5% used to categorize variables)
tr_status_FAILED-tr_tests_failed_TRUE-103350-mined-1.csv | [BFTF-103350-1](https://github.com/victorlaerte/cin-ufpe-msr2017/blob/master/results/tr_status_FAILED-tr_tests_failed_TRUE-103350-mined-1.csv) | File adapted to run logistic regression with R - Sample BFTF (1% used to categorize variables)
R Console - 5.txt | [R Console - 5.txt](https://github.com/victorlaerte/cin-ufpe-msr2017/blob/master/results/R%20Console%20-%205.txt) | File with the console output of logist regression statistic tests for caracterized by 5%
R Console - 1.txt | [R Console - 1.txt](https://github.com/victorlaerte/cin-ufpe-msr2017/blob/master/results/R%20Console%20-%201.txt) | File with the console output of logistic statistic tests for caracterized by 1%
NorTest - 5.txt | [NorTest - 5.txt](https://github.com/victorlaerte/cin-ufpe-msr2017/blob/master/results/NorTest%20-%205.txt) | File with the console output of Anderson-Darling's normality test for sample caracterized by 5%
NorTest - 1.txt | [NorTest - 1.txt](https://github.com/victorlaerte/cin-ufpe-msr2017/blob/master/results/NorTest%20-%201.txt) | File with the console output of Anderson-Darling's normality test for sample caracterized by 1%
