<#macro monCard monitorings>

<div class="container">
    <div class="row justify-content-center">
        <div class="col-sm-6 mt-5">
            <div class="accordion" id="accordionExample">
    <#list monitorings as monitor>
        <div class="card">
            <div class="card-header" id="headingOne">
                <h5 class="mb-0">
                    <div class="container">
                        <div class="row">
                            <div class="col">
                                <button class="btn btn-link" type="button" data-toggle="collapse"
                                        data-target="#collapse${monitor?index}"
                                        aria-expanded="true" aria-controls="collapse${monitor?index}">
                                    ${monitor.fromStation} - ${monitor.toStation} ${monitor.date} ${monitor.trainNumber}
                                </button>
                            </div>
                            <div class="col-1">
                                <button class="btn btn-success" type="button">
                                    &#10074;&#10074;
                                </button>
                            </div>
                            <div class="col-1">
                                <button class="btn btn-success" type="button">
                                    &#9655;
                                </button>
                            </div>
                        </div>
                    </div>
                </h5>
            </div>
            <div id="collapse${monitor?index}" class="collapse collapsed" aria-labelledby="headingOne"
                 data-parent="#accordionExample">
                <div class="card-body">
                     <#list monitor.getPassengers() as passenger>
                         ${passenger.name + passenger.surname}
                     </#list>
                </div>
            </div>
        </div>
    </#list>
            </div>
        </div>
    </div>
</div>

</#macro>