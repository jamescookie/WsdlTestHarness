<html>
<body>
<p>
    These operations are possible:
</p>
<#list operations as operation>
    <p>

        <a href='/wsdl/DisplayOperations!chooseOperation.action?operation=${operation}'>${operation}</a>
    </p>
</#list>
</body>
</html>