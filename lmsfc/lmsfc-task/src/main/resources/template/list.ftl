<#list articleList as art>

    <h3>
    <a href="${art.articleElement.id+'.html'?default("#")}">${art.name?default("")}</a>
    </h3>

    <p>${art.description?default("")}</p>
    <br>
    <p class="text-right">
        <a class="btn btn-link" href="${art.articleElement.id+'.html'?default("#")}">Read More</a>
        <span class="glyphicon glyphicon-time"></span>
        <small> Posted on ${art.generateTime?string("yyyy-MM-dd HH:mm:ss")}</small>
    </p>
    <#if art_has_next>
        <hr>
    </#if>
</#list>

