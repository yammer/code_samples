
# Integrating Azure AD into a Java web application

### Overview

This sample demonstrates a Java web application calling a Yammer API that is secured using Azure Active Directory.

1. The Java web application uses the Active Directory Authentication Library for Java(ADAL4J) to obtain a JWT access token from Azure Active Directory (Azure AD):
2. The access token is used as a bearer token to authenticate the user when calling Yammer API

### Scenario

This sample shows how to build a Java web app that uses OpenID Connect to sign-in users from a single Azure Active Directory (Azure AD) tenant using ADAL4J. For more information about how the protocols work in this scenario and other scenarios, see [Authentication Scenarios for Azure AD](https://docs.microsoft.com/en-us/azure/active-directory/develop/active-directory-authentication-scenarios).

## How to run this sample

To run this sample, you'll need:

- Working installation of Java and Maven
- Tomcat or any other J2EE container solution
- An Internet connection
- An Azure Active Directory (Azure AD) tenant. For more information on how to get an Azure AD tenant, see [How to get an Azure AD tenant](https://azure.microsoft.com/en-us/documentation/articles/active-directory-howto-tenant/) 
- A user account in your Azure AD tenant. This sample will not work with a Microsoft account (formerly Windows Live account). Therefore, if you signed in to the [Azure portal](https://portal.azure.com) with a Microsoft account and have never created a user account in your directory before, you need to do that now.

### Step 1: Download Java (8 and above) for your platform

To successfully use this sample, you need a working installation of [Java](http://www.oracle.com/technetwork/java/javase/downloads/index.html) and [Maven](https://maven.apache.org/).

### Step 2:  Clone or download this repository

From your shell or command line:

- `https://github.com/yammer/code_samples.git`

### Step 3:  Register the sample with your Azure Active Directory tenant

Register the Client Application in Azure AD App Platform:

-	Follow the instructions here to setup the client application: https://docs.microsoft.com/en-us/azure/active-directory/develop/quickstart-register-app
-	Once the Client Application has been setup, go to API Permissions tab in the client registration page and add API permissions for Yammer and within Yammer API permission section select delegated permissions (Yammer currently doesn’t support application permission)
-	Go to Certificates & Secrets Section within the Client Application registration page and generate a new client secret.


### Step 4:  Configure the sample to use your Azure AD tenant

Open `web.xml` in the webapp/WEB-INF/ folder. Fill in with your tenant and app registration information noted in registration step. Replace 'YOUR_TENANT_DOMAIN' with the tenant domain name, 'YOUR_CLIENT_ID' with the Application Id and 'YOUR_CLIENT_SECRET' with the key value noted and also the code sample, tests several yammer resources and the id for the resources need to be provided from web.xml file, for example if you want to test yammer message api endpoints, the text '--insert message id--' needs to be replace with actual message id from your tenant.

### Step 5: Package and then deploy the adal4jsample.war file.

- `$ mvn clean package`

This will generate a `adal4jsample.war` file in your /targets directory. Deploy this war file using Tomcat or any other J2EE container solution. 

To deploy on Tomcat container
- Navigate to your Tomcat installation (default installation on windows is `C:\Program Files\Apache Software Foundation\Tomcat`)
- copy the `adal4jsample.war` file to the Tomcat\webapps folder 
- Start the Tomcat server. One way to do this is by opening the Monitor Tomcat app, clicking on `start` in the `General` tab

This WAR will automatically be hosted at `http://<yourserverhost>:<yourserverport>/adal4jsample/`

Example: `http://localhost:8080/adal4jsample/`

### You're done!

Click on "Login and Test Yammer APIs" to start the process of logging in.

## Community Help and Support

Use [Stack Overflow](http://stackoverflow.com/questions/tagged/adal) to get support from the community.
Ask your questions on Stack Overflow first and browse existing issues to see if someone has asked your question before.
Make sure that your questions or comments are tagged with [`adal` `Java`].

## Contributing

If you'd like to contribute to this sample, see [CONTRIBUTING.MD](/CONTRIBUTING.md).

This project has adopted the [Microsoft Open Source Code of Conduct](https://opensource.microsoft.com/codeofconduct/). For more information, see the [Code of Conduct FAQ](https://opensource.microsoft.com/codeofconduct/faq/) or contact [opencode@microsoft.com](mailto:opencode@microsoft.com) with any additional questions or comments.

## More information

For more information, see ADAL4J [conceptual documentation](https://github.com/AzureAD/azure-activedirectory-library-for-java/wiki)

For more information about how OAuth 2.0 protocols work in this scenario and other scenarios, see [Authentication Scenarios for Azure AD](http://go.microsoft.com/fwlink/?LinkId=394414).
