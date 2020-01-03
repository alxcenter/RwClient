<#assign isAuthorized = Session.SPRING_SECURITY_CONTEXT??>

<#if isAuthorized>
    <#assign
    user = Session.SPRING_SECURITY_CONTEXT.authentication.principal
    name = user.getUsername()
    firstName = user.getFirstName()
    >
<#else>
    <#assign
    name = "unknown"
    isAdmin = false
    firstName = "unknown"
    >
</#if>