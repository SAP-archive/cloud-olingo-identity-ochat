Sample OData Web Service
=====

Welcome
-------
This simple web service implements a group messaging API. The source code illustrates how to integrate Apache Olingo into the SAP HANA Cloud Platform's Identity subsystem to provide for integrated authentication and relevance filtering in the OData collections exposed by a service.

The service is designed to be deployed as a Java application in HANA Cloud Platform.

`This service uses Apache Olingo V2 APIs. As of today, the Olingo features required to perform relevance filtering require a pre-release snapshot of Olingo, 2.0.5-SNAPSHOT.  If you don't want to wait for the official release of this version, you will need to download the Olingo V2 git repository and build your own copy of the snapshot for use with the Maven build subsystem.`

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

__Step 7: Install the built OChat.war archive as a Java Application in your HANA Cloud Platform account__

__Step 8: Navigate to the web service URL:__

		https://<HCP_application_hostname>/OChat/api/