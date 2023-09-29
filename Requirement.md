## Problem Statement
Design and build an application that can search documents from a cloud storage service like Dropbox
or Google Drive on the content inside the document.

Please keep these considerations in mind as you go about solving the problem
1. Implement the solution as described below. Do your best to reflect your design and coding ability.
2. Implement clean modular code respecting OO paradigms.
3. Write a simple 1 pager PRD / RFC for the above problem. 

In this project you will develop a basic search service for data stored in online storage service 
like Google Drive, or DropBox.

### Requirements
1. Connect to an online data storage service of your choice (Google Drive, Dropbox, S3, or any 
   service of your choice) and use the provided APIs to fetch the files stored by the service. As a 
   primary implementation do this for Google drive, but make the implementation interface based or 
   whatever applicable using OO design so that it can be extended to other storage services as well.
2. You can have files that are in either .csv, .txt, .pdf or .docx format. (For the files which are 
   not utf-8 encoded, you may choose to extract the text content from them using a library of your 
   choice like Apache Tika
3. Index the content within the files to provide maximum-text search capabilities on either the data 
   of the file or the meta information of the file. Use Elasticsearch for this.
4. You will provide an API that takes a search term/token as input and returns a list of files and 
   their HTTP URLs that contain the term in their content, e.g.
   curl https://<search-service-host>/search?q=”@mail.com”
5. You will provide a basic API which displays the files matching the given query.
   E.g
   files in storage with content below
   FilePath  Content
   X../File1 a,b,c,d,e
   Y../File2 c,d,e
   X../File3 g,h
   input 1 -> curl https://<search-service-host>/search?q=”c”
   output -> X../File1
             Y../File2

   input2 -> curl https://<search-service-host>/search?q=”notfound-term”
   output -> Empty

   Delete file1 at source google drive
   input 3 -> curl https://<search-service-host>/search?q=”c”
   output -> Y../File2

### Task
Provide step by step development doc along with code implemented in Java using Spring boot



----
DropBox

App key
bpjeptavlxxxfkz
App secret
jaf2ra6a1d2dwkk

Acccess token
sl.Bmxk11b9zqk6pEZmgmSku6S4k-qVpUqXqHxII_cRHyFKftkCrMSnKPffsRbF5Q-Asukv05ebbSwUml9R9QokBkWD7YIYH4WQiTxOp89-MKvVKL8N-t6_mOnqHf_tqP2cf4RSATGHJeZD

http://localhost:8080/db-callback


