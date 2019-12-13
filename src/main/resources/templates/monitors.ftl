<#import "parts/common.ftl" as c>
<#import "parts/monitoringCard.ftl" as card>

<@c.template>
    <div>
            <@card.monCard monitors/>
            <#--<span>${monitor.fromStation}</span><#sep>,-->
            <#--<span>${monitor.toStation}</span>-->
    </div>
</@c.template>
<script src="/static/js/monitorService.js"></script>