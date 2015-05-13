<!DOCTYPE html>
<html lang="zh-CN">

<head>

    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
    <meta http-equiv="X-UA-Compatible" content="IE=edge">
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <meta name="keywords" content="码农 Java码农 码农牧场 码农农场 码农田闲 码农驿站 码农博客 码农学习资料">
    <meta name="description" content="码农 Java码农 码农牧场 码农农场 码农田闲 码农驿站 码农博客 码农学习资料">


    <title>码农田闲 - ${title?default("")}</title>
    <link rel="shortcut icon" href="/favicon.ico" />

    <!-- HTML5 Shim and Respond.js IE8 support of HTML5 elements and media queries -->
    <!-- WARNING: Respond.js doesn't work if you view the page via file:// -->
    <!--[if lt IE 9]>
    <script src="https://oss.maxcdn.com/libs/html5shiv/3.7.0/html5shiv.js"></script>
    <script src="https://oss.maxcdn.com/libs/respond.js/1.4.2/respond.min.js"></script>

    <![endif]-->
    <!-- Bootstrap Core CSS -->
    <link rel="stylesheet" href="http://cdn.bootcss.com/bootstrap/3.3.2/css/bootstrap.min.css">
    <link rel="stylesheet" href="/css/scroll-image.css">

    <#if type=="article">
        ${outercss?default("")}
        ${innercss?default("")}
    </#if>


    <!-- Custom CSS -->
    <#if type=="article">
        <link href="/css/blog-post.css" rel="stylesheet">
    <#else>
        <link href="/css/blog-home.css" rel="stylesheet">
    </#if>

    <!-- jQuery文件。务必在bootstrap.min.js 之前引入 -->
    <script src="http://cdn.bootcss.com/jquery/1.11.2/jquery.min.js"></script>
    <script src="/js/jquery.scrollUp.js"></script>
    <script type="text/javascript">
        $(function(){
            $.scrollUp({
                scrollImg: {
                    active: true,
                    type: 'background',
                    src: 'img/top.png'
                }
            });
        });
    </script>

</head>

<body>

<!-- Navigation -->
<nav class="navbar navbar-inverse navbar-fixed-top" role="navigation">
    <div class="container">
        <!-- Brand and toggle get grouped for better mobile display -->
        <div class="navbar-header">
            <button type="button" class="navbar-toggle" data-toggle="collapse" data-target="#bs-example-navbar-collapse-1">
                <span class="sr-only">Toggle navigation</span>
                <span class="icon-bar"></span>
                <span class="icon-bar"></span>
                <span class="icon-bar"></span>
            </button>
            <a class="navbar-brand" href="/default/list.html">码农田闲</a>
        </div>
        <!-- Collect the nav links, forms, and other content for toggling -->
        <div class="collapse navbar-collapse" id="bs-example-navbar-collapse-1">
            <ul class="nav navbar-nav">
                <li>
                    <a href="/default/list.html">庄稼</a>
                </li>
                <li>
                    <a href="/exp.html">收成</a>
                </li>
                <li>
                    <a href="/info.html">老农</a>
                </li>
            </ul>
        </div>
        <!-- /.navbar-collapse -->
    </div>
    <!-- /.container -->
</nav>

<!-- Page Content -->
<div class="container">

    <div class="row">

        <!-- Blog Post Content Column -->
        <div class="col-lg-9" id="artMain">

            <#if type=="article">
                <#include "*/article.ftl">
            <#else >
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
                    <div class="col-lg-12">
                        <ul class="list-unstyled">
                            <#list artCateList as artCate>
                                <li><a href="/${artCate.pathName}/list.html">${artCate.name}</a></li>
                            </#list>
                        </ul>
                    </div>
                </div>
                <!-- /.row -->
            </div>

        </div>

    </div>
    <!-- /.row -->

    <hr>

    <!-- Footer -->
    <footer>
        <div class="row">
            <div class="col-lg-12">
                <p class="text-center">
                    Copyright &copy; 2015 码农田闲 All rights Reserved. <reserved class=""></reserved>
                    <script type="text/javascript">var cnzz_protocol = (("https:" == document.location.protocol) ? " https://" : " http://");document.write(unescape("%3Cspan id='cnzz_stat_icon_1254768322'%3E%3C/span%3E%3Cscript src='" + cnzz_protocol + "s4.cnzz.com/z_stat.php%3Fid%3D1254768322%26show%3Dpic' type='text/javascript'%3E%3C/script%3E"));</script>
                </p>
            </div>
        </div>
        <!-- /.row -->
    </footer>

</div>
<!-- /.container -->

<!-- 最新的 Bootstrap 核心 JavaScript 文件 -->
<script src="http://cdn.bootcss.com/bootstrap/3.3.2/js/bootstrap.min.js"></script>
<script type="text/javascript">
    <#if type=="article">
    $("#artMain img").addClass("img-responsive");
    </#if>
</script>

<script>window._bd_share_config={"common":{"bdSnsKey":{},"bdText":"","bdMini":"2","bdMiniList":false,"bdPic":"","bdStyle":"0","bdSize":"16"},"slide":{"type":"slide","bdImg":"0","bdPos":"right","bdTop":"100"}};with(document)0[(getElementsByTagName('head')[0]||body).appendChild(createElement('script')).src='http://bdimg.share.baidu.com/static/api/js/share.js?v=89860593.js?cdnversion='+~(-new Date()/36e5)];
</script>
</body>

</html>
