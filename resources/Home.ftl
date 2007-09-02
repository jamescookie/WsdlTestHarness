<html>
<head>
    <title>Home</title>    
</head>
<body>
    <div>
        <form action="<@s.url action='Home' method='doWsdl'/>">
            <p>WSDL URL: <input type="text" name="wsdlUrl" size="100"/></p>
            <p><input type="submit" value="submit"/></p>
        </form>
        <#if hasErrors >
            <p class="error">${error}</p>
        </#if>
        <p>
            In the box above, type in the URL of the WSDL that you are interested in testing.
        </p>
        <p>
            For example: (note: there are still bugs in this software!)
        </p>
        <p>
            http://s3.amazonaws.com/doc/2006-03-01/AmazonS3.wsdl<br/>
            http://api.google.com/GoogleSearch.wsdl<br/>
            http://www.ghettodriveby.com/soap/?wsdl<br/>
            http://saintbook.org/MightyMaxims/MightyMaxims.asmx?WSDL<br/>
            http://mssoapinterop.org/asmx/xsd/round4XSD.wsdl<br/>
            http://samples.gotdotnet.com/quickstart/aspplus/samples/services/MathService/VB/MathService.asmx?WSDL<br/>
            http://developerdays.com/cgi-bin/tempconverter.exe/wsdl/ITempConverter<br/>
            http://eutils.ncbi.nlm.nih.gov/entrez/eutils/soap/eutils.wsdl<br/>
            http://ws.netviagens.com/webservices/AirFares.asmx?wsdl<br/>
            http://ws.strikeiron.com/InnerGears/ForecastByZip2?WSDL<br/>
            http://www.webservicex.net/ValidateEmail.asmx?wsdl<br/>
            http://www.webservicex.net/stockquote.asmx?WSDL (use: IBM)<br/>
            http://www.webservicex.net/icd10.asmx?WSDL<br/>
        </p>
    </div>
</body>
</html>
