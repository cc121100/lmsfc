<!-- Page Content -->
<div class="container">

    <div class="row">

        <!-- Blog Post Content Column -->


            <#if type=="article">
                <div class="col-lg-9" id="artMain" aid="${article.id}">
                <#include "*/article.ftl">
            <#else >
                <div class="col-lg-9" id="artMain">
                <#include "*/list.ftl">
            </#if>
        </div>

        <!-- Blog Sidebar Widgets Column -->
        <div class="col-md-3">

            <!-- Side Widget Well -->
            <div class="well">
                <h4>码农田闲</h4>
                <p>当个码农，赚点钱，结个婚。<br>生两娃，一女，一男。<br>等女娃出嫁，男娃能撸码，退休。<br>之后，每天看看妹子...</p>
            </div>

            <div class="well">
                <img src="/img/wx_small.jpg" class="img-responsive">
            </div>

            <!-- Blog Categories Well -->
            <div class="well">
                <h4>分类目录</h4>
                <div class="row">
                    <div class="col-lg-12" id="divCat">
                        Loading...
                        <#--<ul class="list-unstyled">-->
                        <#--<#list artCateList as artCate>-->
                            <#--<li><a href="/${artCate.pathName}/list.html">${artCate.name}</a></li>-->
                        <#--</#list>-->
                        <#--</ul>-->
                    </div>
                </div>
                <!-- /.row -->
            </div>

        </div>

    </div>
    <!-- /.row -->

    <hr>
    <#include "*/footer.ftl">

</div>
<!-- /.container -->

<!-- 最新的 Bootstrap 核心 JavaScript 文件 -->
<script src="http://cdn.bootcss.com/bootstrap/3.3.2/js/bootstrap.min.js"></script>
<#if type="list">
    <script src="/js/spin.min.js"></script>
    <script src="/js/ladda.min.js"></script>
</#if>
<script src="/js/lmsfc.js"></script>
<script type="text/javascript">

    var page = 2;
    var rType = "l";
    var aid = "";
    var cpn = $("#cpnHid").val();

    <#if type = "article">
        rType = "a";
        aid = $("#artMain").attr("aid");
    </#if>

    $(function(){
        loadArtViewAndCat(aid,rType,cpn);
    });

    <#if type=="article">
        $("#artMain img").addClass("img-responsive");
    <#elseif type=="list">
        Ladda.bind('#btnLoadMore');
        $(function() {
            $('#btnLoadMore').click(function(e){
                e.preventDefault();
                var l = Ladda.create(this);
                l.start();
                lmPost("/lmsfc-web/loadmore_do",{"page":page,"cpn":cpn},function(response){
//                    console.log(response);
                    appendArt(response);
                    page = page + 1;
                },function(response){alert("Error");},function() {console.log("Complete"); l.stop(); });
//                return false;
            });
        });
    </#if>
</script>

<script>window._bd_share_config={"common":{"bdSnsKey":{},"bdText":"","bdMini":"2","bdMiniList":false,"bdPic":"","bdStyle":"0","bdSize":"16"},"slide":{"type":"slide","bdImg":"0","bdPos":"right","bdTop":"100"}};with(document)0[(getElementsByTagName('head')[0]||body).appendChild(createElement('script')).src='http://bdimg.share.baidu.com/static/api/js/share.js?v=89860593.js?cdnversion='+~(-new Date()/36e5)];
</script>