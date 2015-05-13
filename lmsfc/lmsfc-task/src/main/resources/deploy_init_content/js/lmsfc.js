function lmAjax(requestUrl,method,param,successCallBack,errorCallBack,comCallBack){
    $.ajax({
        url : requestUrl,
        method : method,
        cache : false,
        data : param,
        dataType : "json",
        timeout : 10000,
        success : successCallBack,
        error : errorCallBack,
        complete : comCallBack
    });
}

function lmGet(requestUrl,param,successCallBack,errorCallBack,comCallBack){
    lmAjax(requestUrl,"GET",param,successCallBack,errorCallBack,comCallBack);
}

function lmPost(requestUrl,param,successCallBack,errorCallBack,comCallBack){
    lmAjax(requestUrl,"POST",param,successCallBack,errorCallBack,comCallBack);
}

function appendArt(response){
    var datas = response.data;
    var appendStr = "<hr>";
    for(var i = 0; i < datas.length; i++){
        appendStr = appendStr + "<div aid=" + datas[i].id + ">";
        appendStr = appendStr + "<h3><a href='" + datas[i].url + "'>" + datas[i].title + "</a></h3>"
                              + "<p>" + datas[i].description + "</p><br>"
                              + "<p class='text-right'>" 
                                    + "<a class='btn btn-link' href='" + datas[i].url + "'>查看全文</a>"
                                    + "<small>阅读(<span class='avc'>" + datas[i].view + "</span>)</small>&nbsp;&nbsp;"
                                    + "<span class='glyphicon glyphicon-time'></span>"
                                    + "<small>&nbsp;" + datas[i].generateTime + "</small>"
                              + "</p>";
        appendStr = appendStr + "</div><hr>";                  
    }
    appendStr = appendStr.substr(0,appendStr.length-4);
//    console.log(appendStr);

    $("#btnLoadMore").before(appendStr);
}

function loadArtViewAndCat(aid, type){
    var param = new Object();
    param.aid = aid;
    param.rType = type;// l:list page, a:article page
    lmGet("/lmsfc-web/loadArtAndCat_do",param,function(response){
        console.log(response);
        var cList = response.cList;
        var avs = response.avs;
        // set category and view 
        var catStr = "<ul class='list-unstyled'>";
        for(var i = 0; i < cList.length; i++){
            catStr = catStr + "<li><a href='" + cList[i].url + "'>" + cList[i].cname + "</a>&nbsp;" + "<small>(" + cList[i].view+ ")</small>" + "</li>"
        }
        catStr += "</ul>";
        $("#divCat").html(catStr);

        if(type == "l"){
            //set art view
            for(var i = 0; i < avs.length;i++){
                var aid = avs[i].aid;
                var sel = "div[aid='" + aid + "'] .avc";
                $(sel).html(avs[i].view);
            }
        }
        

        if(type == "a"){
            //set art view
            var sel = "#artMain .avc";
            $(sel).html(avs[0].view);

            // set pre and next art
            var pre = avs[0].pre;
            var next = avs[0].next;
            var preHtml = "";
            var nextHtml = "";
            if(pre.title != null && pre.url != null){
                preHtml = "<a href='" + pre.url + "'>" + pre.title + "</a>";
                $(".spre").html(preHtml);
            }
            if(next.title != null && next.url != null){
                nextHtml =  "<a href='" + next.url + "'>" + next.title + "</a>";
                $(".snext").html(nextHtml);
            }            
        }

    },function(response){console.log(response);alert("LoadArtViewAndCat Error");},"");
}