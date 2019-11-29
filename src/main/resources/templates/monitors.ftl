<#import "parts/common.ftl" as c>

<@c.template>
    <div>
        <#list monitors as monitor>
            <span>${monitor.fromStation}</span><#sep>,
            <span>${monitor.toStation}</span>
        </#list>
    </div>
</@c.template>