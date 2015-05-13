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
<link href="/css/ladda-themeless.min.css" rel="stylesheet">

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