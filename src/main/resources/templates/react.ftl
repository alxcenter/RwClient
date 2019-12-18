<#import "parts/common.ftl" as c>
<#import "parts/telegaAuth.ftl" as telega>
<#include "parts/security.ftl">

<@c.template>
<#--<#if !isAuthorized>-->
<div id="root"></div>
<#--</#if>-->
</@c.template>
<script src="/static/built/bundle.js"></script>