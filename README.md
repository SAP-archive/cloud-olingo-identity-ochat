Sample OData Web Service
=====

Welcome
-------
This simple web service implements a group messaging API. The source code illustrates how to integrate Apache Olingo into the SAP HANA Cloud Platform's Identity subsystem to provide for integrated authentication and relevance filtering in the OData collections exposed by a service.

The service is designed to be deployed as a Java application in SAP HANA Cloud Platform.

Installation
------------
>Note: This project is referenced in a series of SAP Community Network blog articles ([link](http://scn.sap.com/community/developer-center/mobility-platform/blog/2015/07/31/integrated-identity-for-mobile-in-hana-cloud-platform--hcpms-and-olingo-odata-web-services)).  If you are interested in this code for some other purpose, you can follow the steps outlined below to build and run the project.

__Step 1: Clone this github repository__

__Step 2: Install Maven 3.0.3+__

[Download from here](http://maven.apache.org/download.html)

__Step 3: Ensure maven binaries are on your PATH (ie. you can run `mvn` from anywhere)__

Follow the Maven installation instructions from [here](http://maven.apache.org/download.html#Installation).

__Step 4: CD to the root directory of this project (where this README.md lives)__

__Step 5: Install the third party dependencies into your local repository__

* On all platforms:

		mvn clean install

__Step 6: Install the third party dependencies into your local repository__

__Step 7: Install the built OChat.war archive as a Java Application in your HANA Cloud Platform account. IMPORTANT: the current version of OChat must be installed in a "Java Web Tomcat 7" container.__

*       From the HCP Admin Cockpit, click "Java Applications"
*       Click "Deploy Application"
*       Select the built OChat.war file for upload
*       Select "Java Web Tomcat 7" as the "Runtime Name" -- Apache Olingo V2 by default does not support the JTA elements of the JEE 6 runtime.
*       Click "Deploy"
*       Once the application deploys, select "Start"

__Step 8: Navigate to the web service URL:__

		https://<HCP_application_hostname>/OChat/api/