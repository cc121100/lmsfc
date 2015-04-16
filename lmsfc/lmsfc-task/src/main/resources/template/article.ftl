<!-- Blog Post -->
<!-- Title -->
<h1>${title?default("")}</h1>
<p class="text-right">
    <small>文章分类：<a href="/${article.articleCategory.pathName}/list.html">${article.articleCategory.name}</a> </small>&nbsp;&nbsp;
    <span class="glyphicon glyphicon-time"></span><small> 发表时间： ${postDate?string("yyyy-MM-dd HH:mm:ss")}</small>
</p>

<hr>

<!-- <p class="text-right"><span class="glyphicon glyphicon-time"></span><small> Posted on August 24, 2013 at 9:00 PM</small></p> -->
<p>文章转载至：<a href="${from?default("#")}" target="_blank">${from?default("")}</a></p>


<!-- Post Content -->
${content?default("")}


