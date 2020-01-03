<#macro monCard monitorings>

<div class="container">
    <div class="row justify-content-center">
        <div class="col-xl-8 mt-5">
            <div class="accordion" id="monitoringAccord">
    <#list monitorings as monitor>
        <#assign monStatusId = monitor.status.statusId>
        <#assign monStatus = monitor.status.toString()>
        <#assign monStatusId = monitor.status.statusId>
        <div class="card">
            <div class="card-header" id="heading${monitor?index}">
                <h5 class="mb-0">
                    <div class="container">
                        <div class="row justify-content-center">
                            <div class="col">
                                <button class="btn btn-link" type="button" data-toggle="collapse"
                                        data-target="#collapse${monitor?index}"
                                        aria-expanded="true" aria-controls="collapse${monitor?index}">
                                    ${monitor.fromStation.stationName}
                                    - ${monitor.toStation.stationName} ${monitor.date} ${monitor.trainNumber}
                                </button>
                            </div>
                            <div class="col-3">
                            <#--<#if monStatusId != 0>-->
                                <button class="btn btn-link ${(monStatusId==0)?string("invisible", "")}" type="button"
                                        data-toggle="tooltip"
                                        id="status_${monitor.id}"
                                        data-placement="top" title="Pause monitoring">
                                    <span class="badge badge-info justify-content-end ml-3">${monStatus}</span>
                                </button>
                            <#--</#if>-->
                            </div>
                            <div class="col-1">
                                <button class="btn ${(monStatusId==0)?string("btn-success", "btn-secondary disabled")}"
                                    ${(monStatusId==0)?string("", "disabled")}
                                        type="button" data-toggle="tooltip" data-placement="top"
                                        id="pause_${monitor.id}"
                                        onclick="disableMonitoring(${monitor.id})"
                                        title="Pause monitoring">
                                    &#10074;&#10074;
                                </button>
                            </div>
                            <div class="col-1">
                                <button class="btn ${(monStatusId==0)?string("btn-secondary disabled", "btn-success")}"
                                    ${(monStatusId==0)?string("disabled", "")}
                                        type="button" data-toggle="tooltip" data-placement="top"
                                        id="resume_${monitor.id}"
                                        onclick="enableMonitoring(${monitor.id})"
                                        title="Resume monitoring">
                                    &#9655;
                                </button>
                            </div>
                        </div>
                    </div>
                </h5>
            </div>
            <div id="collapse${monitor?index}" class="collapse collapsed" aria-labelledby="heading${monitor?index}"
                 data-parent="#monitoringAccord">
                <div class="card-body">
                    <div class="list-group">
                     <#list monitor.getPassengers() as passenger>
                         <div class="list-group-item">
                             <div class="container">
                                 <div class="row">
                                     <div class="col-sm m-2">
                                         ${passenger.name + " " +  passenger.surname}
                                     </div>
                                     <div class="col-sm-2 mx-2">
                                         <button class="btn btn-danger" type="button" data-toggle="tooltip"
                                                 data-placement="top"
                                                 title="Delete from monitoring" onclick="alert('Функционал не реализован')">
                                             Delete
                                         </button>
                                     </div>
                                 </div>
                             </div>
                         </div>
                     </#list>
                    </div>
                </div>
            </div>
        </div>
    </#list>
            </div>
        </div>
    </div>
</div>

</#macro>