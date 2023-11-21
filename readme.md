# Single pager PRD

## What does this application do?
1. This application connects to your dropbox, scans for all the files across all the folders and indexes
them in an ES index called as "files". This happens in background at a fixed cadence once the app is up.
2. Using the controller end point in the "DBSearchController", user can search for a keyword. Outcome is
collection of documents (which contain the file path) where the keyword is present. You get [] empty result
if that keyword isn't present.

## How to run this locally?
1. In the properties file, replace the below entries accordingly
   dropbox.accessToken = <YOUR_DROP_BOX_APP_TOKEN> (www.dropbox.com/developers/apps)
   spring.data.elasticsearch.cluster-names = <YOUR_ES_CLUSTER_NAME>
   spring.data.elasticsearch.cluster-node = <YOUR_ES_CLUSTER_NODE>
2. Start the spring boot application
3. If the drop box and es is setup and configured as mentioned in the step 1, the background job would
start indexing and cleanup activities. Use the controller end point to search for the keyword

