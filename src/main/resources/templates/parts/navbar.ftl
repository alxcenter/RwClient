<#include "security.ftl">

<nav class="navbar navbar-expand-sm navbar-dark bg-dark">
    <a class="navbar-brand" href="/">RwClient</a>


    <button class="navbar-toggler" type="button" data-toggle="collapse" data-target="#navbarSupportedContent"
            aria-controls="navbarSupportedContent" aria-expanded="false" aria-label="Toggle navigation">
        <span class="navbar-toggler-icon"></span>
    </button>

    <#if isAuthorized>
    <div class="collapse navbar-collapse" id="navbarSupportedContent">
        <ul class="navbar-nav mr-auto">
            <li class="nav-item">
                <a class="nav-link" href="/me/monitoring">Monitoring list</a>
            </li>
            <li class="nav-item">
                <a class="nav-link disabled" href="/plans">Plans</a>
            </li>
        </ul>
        <div class="btn-group">
            <button type="button" class="btn btn-secondary dropdown-toggle" data-toggle="dropdown" aria-haspopup="true"
                    aria-expanded="false">
                ${firstName}
            </button>
            <div class="dropdown-menu dropdown-menu-right">
                <button class="dropdown-item" type="button">Account</button>
                <button class="dropdown-item" type="button">Settings</button>
                <form action="/logout">
                    <button class="dropdown-item" type="submit">Logout</button>
                </form>
            </div>
        </div>
    </#if>
</div>
</nav>