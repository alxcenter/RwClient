<#import "parts/common.ftl" as c>
<#import "parts/telegaAuth.ftl" as telega>
<#include "parts/security.ftl">

<@c.template>
<#if !isAuthorized>
<div class="container">
    <div class="container">
        <div class="row justify-content-sm-center">
            <div class="col col-sm-4 mt-5">
                <div class="card mt-3 border-0 text-center" style="max-width: 540px;">
                    <div class="row mt-5 no-gutters">
                        <div class="col-md-4">
                            <img src="/static/img/auth.svg" class="card-img mt-2" alt="Logo">
                        </div>
                        <div class="col-md-8">
                            <div class="card-body">
                                <h5 class="card-title">Sign In</h5>
                                <p class="card-text"></p>
                                <@telega.telegaAuth/>
                                <p class="card-text"><small class="text-muted">Sign in please</small></p>
                            </div>
                        </div>
                    </div>
                </div>
            </div>
        </div>
    </div>
</#if>
</@c.template>