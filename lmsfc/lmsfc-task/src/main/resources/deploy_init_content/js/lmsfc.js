function lmAjax(requestUrl,method,param,successCallBack,errorCallBack,comCallBack){
    $.ajax({
        url : requestUrl,
        method : method,
        cache : false,
        data : param,
        dataType : "json",
        //timeout : 10000,
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
    if(datas == null || datas.length < 1){
        if(response.isLast == 1){
            alert("没有更多文章了");
        }else{
            alert("Error");
        }
        return false;
    }else{
        for(var i = 0; i < datas.length; i++){
            var art = datas[i];
            appendStr = appendStr + "<div aid=" + art.aid + ">";
            appendStr = appendStr + "<h3><a href='" + art.url + "'>" + art.title + "</a></h3>"
                + "<p>" + art.des + "</p><br>"
                + "<p class='text-right'>"
                + "<a class='btn btn-link' href='" + art.url + "'>查看全文</a>"
                + "<small>阅读(<span class='avc'>" + art.viewCount + "</span>)</small>&nbsp;&nbsp;"
                + "<span class='glyphicon glyphicon-time'></span>"
                + "<small>&nbsp;" + art.postTime + "</small>"
                + "</p>";
            appendStr = appendStr + "</div><hr>";
        }
        appendStr = appendStr.substr(0,appendStr.length-4);
        $("#btnLoadMore").before(appendStr);
        return true;
    }
}

function loadArtViewAndCat(aid, rType, cpn){
    var param = new Object();
    param.aid = aid;
    param.rType = rType;// l:list page, a:article page
    param.cpn = cpn;
    lmGet("/lmsfc-web/loadArtAndCat_do",{"aid":aid,"rType":rType,"cpn":cpn},function(response){
        //console.log(response);
        var cList = response.cList;
        var avs = response.avs;
        // set category and view 
        var catStr = "<ul class='list-unstyled'>";
        for(var i = 0; i < cList.length; i++){
            catStr = catStr + "<li><a href='/" + cList[i].cName + "/list.html'>" + cList[i].name + "</a>&nbsp;" + "<small>(" + cList[i].artCount+ ")</small>" + "</li>"
        }
        catStr += "</ul>";
        $("#divCat").html(catStr);

        if(rType == "l"){
            //set art view
            for(var i = 0; i < avs.length;i++){
                var aid = avs[i].aid;
                var sel = "div[aid='" + aid + "'] .avc";
                $(sel).html(avs[i].viewCount);
            }
        }


        if(rType == "a"){
            //set art view
            var sel = "#artMain .avc";
            $(sel).html(avs[0].viewCount);

            // set pre and next art
            var preStr = avs[0].pre;
            var nextStr = avs[0].next;

            var preHtml = "";
            var nextHtml = "";
            if(preStr != null && preStr != ""){
                preHtml = "<a href='" + preStr.substr(0,32) + ".html'>" + preStr.substr(33) + "</a>";
                $(".spre").html(preHtml);
            }
            if(nextStr != null && nextStr != ""){
                nextHtml = "<a href='" + nextStr.substr(0,32) + ".html'>" + nextStr.substr(33) + "</a>";
                $(".snext").html(nextHtml);
            }
        }

    },function(response){console.log(response);alert("LoadArtViewAndCat Error");},"");
}