<html>
<body>
<p>
    You are about to execute: ${operation}
</p>
<#macro myMacro>
    <table cellpadding="0" cellspacing="0">
        <#list fieldDescriptors as fieldDescriptor>
            <tr>
                <td valign="top">
                    ${fieldDescriptor.name}
                </td>
                <td valign="top">
                    <#if fieldDescriptor.primitive>
                        <input type="text" name="${fieldDescriptor.formName}" value="${fieldDescriptor.value}"/>
                        <#if fieldDescriptor.validationMessageRequired>
                            <span class="error">${fieldDescriptor.validationMessage}</span>
                        </#if>
                     <#else>
                        <br/>
                        <#assign fieldDescriptors=fieldDescriptor>
                        <@myMacro/>
                    </#if>
                </td>
            </tr>
        </#list>
    </table>
</#macro>

<form action="<@s.url action='SetupOperation' method='performOperation'/>" method="get">
    <input type="hidden" name="operation" value="${operation}"/>
    <#assign fieldDescriptors=parameters>
    <p>
        <@myMacro/>
    </p>
    <input type="submit" value="Execute"/>
</form>

<#if anyError>
    <p class="error">${exception.message}</p>
</#if>
<#if resultGenerated>
    <h1>Result:</h1>
    <p>
        ${result}
    </p>
</#if>
</body>
</html>