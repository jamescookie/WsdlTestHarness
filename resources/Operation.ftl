<html>
<body>
<p>
    You are about to execute: ${operation}
</p>
<#macro myMacro>
<table cellpadding="0" cellspacing="0">
    <#list myParameters as parameter>
        <!--<#assign margin = 20 + (parameter.depth * 25)>-->
        <!--<span style="margin-left:${margin}px">-->
        <tr>
        <td  valign=top align=left >
            ${parameter.name}
            </td>
        <td valign=top align=left>
            <#if parameter.primitive>
                <input type="text" name="${parameter.formName}" value="${parameter.value}"/>
            <#else>
                <br/>
                <#assign myParameters=parameter>
                <@myMacro/>
            </#if>
         </td>
        </tr>


    </#list>
</table>
</#macro>

<form action="<@s.url action='SetupOperation' method='performOperation'/>">
    <input type="hidden" name="operation" value="${operation}"/>
    <#assign myParameters=parameters>
    <p>

            <@myMacro/>

    </p>
    <input type="submit" value="Execute"/>
</form>

<#if resultGenerated>
    <h1>Result:</h1>
    <p>
        ${result}
    </p>
</#if>
</body>
</html>