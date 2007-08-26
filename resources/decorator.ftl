<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Strict//EN"
   "http://www.w3.org/TR/xhtml1/DTD/xhtml1-strict.dtd">
<html xmlns="http://www.w3.org/1999/xhtml" lang="en" xml:lang="en">
    <head>
        <title>Web Services - ${page.title}</title>
        <link rel="stylesheet" type="text/css" href="css/site.css"/>
        ${page.head}
        <script src="http://www.google-analytics.com/urchin.js" type="text/javascript"></script>
        <script type="text/javascript">
            _uacct = "UA-2101166-1";
            urchinTracker();
        </script>
    </head>

    <body>
        <div class="header">
            <table width="100%">
                <tr>
                    <td valign="top"><span class="header-title">Web Services</span></td>
                </tr>
                <tr>
                    <td valign="top">
                        <#assign webservice = "${url}">
                        You are currently pointing at this Web Service: <a href="${webservice}">${webservice}</a>
                    </td>
                </tr>
            </table>
        </div>

        <p>
            <a href="http://www.jamescookie.com/">Back To Homepage</a>
            |
            <a href="/wsdl/Home.action">WSDL Home</a>
            <#if wsdlDefined >
                |
                <a href="/wsdl/DisplayOperations.action">Display Operations</a>
            </#if>
        </p>

        ${page.body}

    </body>
</html>