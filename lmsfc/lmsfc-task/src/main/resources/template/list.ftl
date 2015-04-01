<#list articleList as art>

    <h3>
    <a href="${art.artFileName?default("")}">${art.name?default("")}</a>
    </h3>
    <hr>
    <p>${art.description?default("")}</p>
    <br>
    <p class="text-right"><a class="btn btn-link" href="#">Read More</a><span class="glyphicon glyphicon-time"></span>
        <small> Posted on ${art.generateTime?string("yyyy-MM-dd HH:mm:ss")}</small></p>
    <hr>

</#list>

