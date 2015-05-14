<input type="hidden" id="cpnHid" value="${articleList[0].articleCategory.pathName}"/>
<#list articleList as art>
    <div aid="${art.id}">
        <h3>
        <a href="${art.articleElement.id+'.html'?default("#")}">${art.name?default("")}</a>
        </h3>

        <p>${art.description?default("")}</p>
        <br>
        <p class="text-right">
            <a class="btn btn-link" href="${art.articleElement.id+'.html'?default("#")}">查看全文</a>
            <small>阅读(<span class="avc">-</span>)</small>&nbsp;&nbsp;
            <span class="glyphicon glyphicon-time"></span>
            <small>&nbsp;${art.generateTime?string("yyyy-MM-dd HH:mm:ss")}</small>
        </p>
    </div>
    <#if art_has_next>
        <hr>
    </#if>
</#list>

<button class="btn btn-primary btn-block ladda-button" data-style="expand-right" id="btnLoadMore" data-size="l">
    <span class="ladda-label">更多文章</span>
</button>

