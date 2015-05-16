<!DOCTYPE html>
<html lang="zh">
<head>
<meta charset="utf-8">
<meta http-equiv="X-UA-Compatible" content="IE=edge">
<title>B-JUI 客户端框架</title>
<meta name="Keywords" content="B-JUI,Bootstrap,DWZ,jquery,ui,前端,框架,开源,OSC,开源框架,knaan"/>
<meta name="Description" content="B-JUI, Bootstrap for DWZ富客户端框架，基于DWZ富客户端框架修改。主要针对皮肤，编辑器，表单验证等方面进行了大量修改，引入了Bootstrap，Font Awesome，KindEditor，jquery.validationEngine，iCheck等众多开源项目。交流QQ群：232781006。"/>
    <#--<#assign ctx=getContextPath()/>-->

<!-- bootstrap - css -->
<link href="${rc.contextPath}/themes/css/bootstrap.css" rel="stylesheet">
<!-- core - css -->
<link href="${rc.contextPath}/themes/css/style.css" rel="stylesheet">
<link href="${rc.contextPath}/themes/purple/core.css" id="bjui-link-theme" rel="stylesheet">
<!-- plug - css -->
<link href="${rc.contextPath}/plugins/kindeditor_4.1.10/themes/default/default.css" rel="stylesheet">
<link href="${rc.contextPath}/plugins/colorpicker/css/bootstrap-colorpicker.min.css" rel="stylesheet">
<link href="${rc.contextPath}/plugins/niceValidator/jquery.validator.css" rel="stylesheet">
<link href="${rc.contextPath}/plugins/bootstrapSelect/bootstrap-select.css" rel="stylesheet">
<link href="${rc.contextPath}/themes/css/FA/css/font-awesome.min.css" rel="stylesheet">
<!--[if lte IE 7]>
<link href="${rc.contextPath}/themes/css/ie7.css" rel="stylesheet">
<![endif]-->
<!-- HTML5 shim and Respond.js IE8 support of HTML5 elements and media queries -->
<!--[if lte IE 9]>
    <script src="${rc.contextPath}/other/html5shiv.min.js"></script>
    <script src="${rc.contextPath}/other/respond.min.js"></script>
<![endif]-->
<!-- jquery -->
<script src="${rc.contextPath}/js/jquery-1.7.2.min.js"></script>
<script src="${rc.contextPath}/js/jquery.cookie.js"></script>
<!--[if lte IE 9]>
<script src="${rc.contextPath}/other/jquery.iframe-transport.js"></script>
<![endif]-->
<!-- BJUI.all 分模块压缩版 -->
<script src="${rc.contextPath}/js/bjui-all.js"></script>
<!-- 以下是B-JUI的分模块未压缩版，建议开发调试阶段使用下面的版本 -->

<!-- plugins -->
<!-- swfupload for uploadify && kindeditor -->
<script src="${rc.contextPath}/plugins/swfupload/swfupload.js"></script>
<!-- kindeditor -->
<script src="${rc.contextPath}/plugins/kindeditor_4.1.10/kindeditor-all.min.js"></script>
<script src="${rc.contextPath}/plugins/kindeditor_4.1.10/lang/zh_CN.js"></script>
<!-- colorpicker -->
<script src="${rc.contextPath}/plugins/colorpicker/js/bootstrap-colorpicker.min.js"></script>
<!-- ztree -->
<script src="${rc.contextPath}/plugins/ztree/jquery.ztree.all-3.5.js"></script>
<!-- nice validate -->
<script src="${rc.contextPath}/plugins/niceValidator/jquery.validator.js"></script>
<script src="${rc.contextPath}/plugins/niceValidator/jquery.validator.themes.js"></script>
<!-- bootstrap plugins -->
<script src="${rc.contextPath}/plugins/bootstrap.min.js"></script>
<script src="${rc.contextPath}/plugins/bootstrapSelect/bootstrap-select.min.js"></script>
<!-- icheck -->
<script src="${rc.contextPath}/plugins/icheck/icheck.min.js"></script>
<!-- dragsort -->
<script src="${rc.contextPath}/plugins/dragsort/jquery.dragsort-0.5.1.min.js"></script>
<!-- HighCharts -->
<script src="${rc.contextPath}/plugins/highcharts/highcharts.js"></script>
<script src="${rc.contextPath}/plugins/highcharts/highcharts-3d.js"></script>
<script src="${rc.contextPath}/plugins/highcharts/themes/gray.js"></script>
<!-- ECharts -->
<script src="${rc.contextPath}/plugins/echarts/echarts.js"></script>
<!-- other plugins -->
<script src="${rc.contextPath}/plugins/other/jquery.autosize.js"></script>
<link href="${rc.contextPath}/plugins/uploadify/css/uploadify.css" rel="stylesheet">
<script src="${rc.contextPath}/plugins/uploadify/scripts/jquery.uploadify.min.js"></script>
<script src="${rc.contextPath}/plugins/dynamicForm.js"></script>
<!-- init -->
<script type="text/javascript">
$(function() {
    BJUI.init({
        JSPATH       : '/',         //[可选]框架路径
        PLUGINPATH   : '/plugins/', //[可选]插件路径
        loginInfo    : {url:'login_timeout.html', title:'登录', width:400, height:200}, // 会话超时后弹出登录对话框
        statusCode   : {ok:200, error:300, timeout:301}, //[可选]
        ajaxTimeout  : 70000, //[可选]全局Ajax请求超时时间(毫秒)
        alertTimeout : 3000,  //[可选]信息提示[info/correct]自动关闭延时(毫秒)
        pageInfo     : {pageCurrent:'pageCurrent', pageSize:'pageSize', orderField:'orderField', orderDirection:'orderDirection'}, //[可选]分页参数
        keys         : {statusCode:'statusCode', message:'message'}, //[可选]
        ui           : {
                         showSlidebar     : true, //[可选]左侧导航栏锁定/隐藏
                         clientPaging     : true, //[可选]是否在客户端响应分页及排序参数
                         overwriteHomeTab : false //[可选]当打开一个未定义id的navtab时，是否可以覆盖主navtab(我的主页)
                       },
        debug        : true,    // [可选]调试模式 [true|false，默认false]
        theme        : 'purple' // 若有Cookie['bjui_theme'],优先选择Cookie['bjui_theme']。皮肤[五种皮肤:default, orange, purple, blue, red, green]
    })
    //时钟
    var today = new Date(), time = today.getTime()
    $('#bjui-date').html(today.formatDate('yyyy/MM/dd'))
    setInterval(function() {
        today = new Date(today.setSeconds(today.getSeconds() + 1))
        $('#bjui-clock').html(today.formatDate('HH:mm:ss'))
    }, 1000)
})

//console.log('IE:'+ (!$.support.leadingWhitespace))
//菜单-事件
function MainMenuClick(event, treeId, treeNode) {
    if (treeNode.isParent) {
        var zTree = $.fn.zTree.getZTreeObj(treeId)

        zTree.expandNode(treeNode)
        return
    }

    if (treeNode.target && treeNode.target == 'dialog')
        $(event.target).dialog({id:treeNode.tabid, url:treeNode.url, title:treeNode.name})
    else
        $(event.target).navtab({id:treeNode.tabid, url:treeNode.url, title:treeNode.name, fresh:treeNode.fresh, external:treeNode.external})
    event.preventDefault()
}
</script>
<!-- for doc begin -->
<#--<link type="text/css" rel="stylesheet" href="${rc.contextPath}/styles/shCore.css"/>-->
<#--<link type="text/css" rel="stylesheet" href="${rc.contextPath}/styles/shThemeEclipse.css"/>-->
<#--<script type="text/javascript" src="${rc.contextPath}/js/brush.js"></script>-->
<#--<link href="${rc.contextPath}doc/doc.css" rel="stylesheet">-->
<script type="text/javascript">
//$(function(){
//    SyntaxHighlighter.config.clipboardSwf = '/js/clipboard.swf'
//    $(document).on(BJUI.eventType.initUI, function(e) {
//        SyntaxHighlighter.highlight();
//    })
//})
</script>
<!-- for doc end -->
</head>
<body>
    <!--[if lte IE 7]>
        <div id="errorie"><div>您还在使用老掉牙的IE，正常使用系统前请升级您的浏览器到 IE8以上版本 <a target="_blank" href="${rc.contextPath}http://windows.microsoft.com/zh-cn/internet-explorer/ie-8-worldwide-languages">点击升级</a>&nbsp;&nbsp;强烈建议您更改换浏览器：<a href="${rc.contextPath}http://down.tech.sina.com.cn/content/40975.html" target="_blank">谷歌 Chrome</a></div></div>
    <![endif]-->
    <header id="bjui-header">
        <div class="bjui-navbar-header">
            <button type="button" class="bjui-navbar-toggle btn-default" data-toggle="collapse" data-target="#bjui-navbar-collapse">
                <i class="fa fa-bars"></i>
            </button>
            <a class="bjui-navbar-logo" href="#"><img src="${rc.contextPath}/images/logo.png"></a>
        </div>
        <nav id="bjui-navbar-collapse">
            <ul class="bjui-navbar-right">
                <li class="datetime"><div><span id="bjui-date"></span><br><i class="fa fa-clock-o"></i> <span id="bjui-clock"></span></div></li>
                <li><a href="http://www.bootcss.com/" target="_blank">Bootstrap中文网</a></li>
                <li><a href="http://www.j-ui.com/" target="_blank">DWZ(j-ui)官网</a></li>
                <li><a href="#">消息 <span class="badge">4</span></a></li>
                <li class="dropdown"><a href="#" class="dropdown-toggle" data-toggle="dropdown">我的账户 <span class="caret"></span></a>
                    <ul class="dropdown-menu" role="menu">
                        <li><a href="${rc.contextPath}/changepwd.html" data-toggle="dialog" data-id="changepwd_page" data-mask="true" data-width="400" data-height="260">&nbsp;<span class="glyphicon glyphicon-lock"></span> 修改密码&nbsp;</a></li>
                        <li><a href="#">&nbsp;<span class="glyphicon glyphicon-user"></span> 我的资料</a></li>
                        <li class="divider"></li>
                        <li><a href="${rc.contextPath}/logout" class="red">&nbsp;<span class="glyphicon glyphicon-off"></span> 注销登陆</a></li>
                    </ul>
                </li>
                <li class="dropdown"><a href="#" class="dropdown-toggle theme purple" data-toggle="dropdown"><i class="fa fa-tree"></i></a>
                    <ul class="dropdown-menu" role="menu" id="bjui-themes">
                        <li><a href="javascript:;" class="theme_default" data-toggle="theme" data-theme="default">&nbsp;<i class="fa fa-tree"></i> 黑白分明&nbsp;&nbsp;</a></li>
                        <li><a href="javascript:;" class="theme_orange" data-toggle="theme" data-theme="orange">&nbsp;<i class="fa fa-tree"></i> 橘子红了</a></li>
                        <li class="active"><a href="javascript:;" class="theme_purple" data-toggle="theme" data-theme="purple">&nbsp;<i class="fa fa-tree"></i> 紫罗兰</a></li>
                        <li><a href="javascript:;" class="theme_blue" data-toggle="theme" data-theme="blue">&nbsp;<i class="fa fa-tree"></i> 青出于蓝</a></li>
                        <li><a href="javascript:;" class="theme_red" data-toggle="theme" data-theme="red">&nbsp;<i class="fa fa-tree"></i> 红红火火</a></li>
                        <li><a href="javascript:;" class="theme_green" data-toggle="theme" data-theme="green">&nbsp;<i class="fa fa-tree"></i> 绿草如茵</a></li>
                    </ul>
                </li>
            </ul>
        </nav>
    </header>

    <div id="bjui-container" class="clearfix">
        <div id="bjui-leftside">
            <div id="bjui-sidebar-s">
                <div class="collapse"></div>
            </div>
            <div id="bjui-sidebar">
                <div class="toggleCollapse"><h2><i class="fa fa-bars"></i> 导航栏 <i class="fa fa-bars"></i></h2><a href="javascript:;" class="lock"><i class="fa fa-lock"></i></a></div>
                <div class="panel-group panel-main" data-toggle="accordion" id="bjui-accordionmenu" data-heightbox="#bjui-sidebar" data-offsety="26">
                    <div class="panel panel-default">
                        <div class="panel-heading panelContent">
                            <h4 class="panel-title"><a data-toggle="collapse" data-parent="#bjui-accordionmenu" href="#bjui-collapse0" class="active"><i class="fa fa-caret-square-o-down"></i>&nbsp;Demo演示</a></h4>
                        </div>
                        <div id="bjui-collapse0" class="panel-collapse panelContent collapse in">
                            <div class="panel-body" >
                                <ul id="bjui-tree0" class="ztree ztree_main" data-toggle="ztree" data-on-click="MainMenuClick" data-expand-all="true">
                                <#list menus as menu>
                                    <#if menu.type=="menu">
                                        <li data-id="${menu.id}"
                                            data-pid="${menu.parentResource.id?default("")}">${menu.resourceName?default("")}</li>

                                    <#elseif menu.type=="subMenu">
                                        <li data-id="${menu.id}"
                                            data-pid="${menu.parentResource.id?default("")}"
                                            data-url="${rc.contextPath}/${menu.url?default("")}"
                                            data-tabid="${menu.id}">${menu.resourceName?default("")}</li>

                                    </#if>

                                </#list>
                                    <#--<li data-id="10" data-pid="1" data-url="${rc.contextPath}/article/index" data-tabid="form-button">按钮</li>-->
                                    <#--<li data-id="11" data-pid="1" data-url="form-input.html" data-tabid="form-input">文本框</li>-->
                                    <#--<li data-id="1" data-pid="0">表单元素</li>-->
                                    <#--<li data-id="12" data-pid="1" data-url="form-select.html" data-tabid="form-select">下拉选择框</li>-->
                                    <#--<li data-id="13" data-pid="1" data-url="form-checkbox.html" data-tabid="table">复选、单选框</li>-->
                                    <#--<li data-id="14" data-pid="1" data-url="form.html" data-tabid="form">表单综合演示</li>-->
                                    <#--<li data-id="2" data-pid="0">表格</li>-->
                                    <#--<li data-id="20" data-pid="2" data-url="table.html" data-tabid="table">普通表格</li>-->
                                    <#--<li data-id="21" data-pid="2" data-url="table-fixed.html" data-tabid="table-fixed">固定表头表格</li>-->
                                    <#--<li data-id="22" data-pid="2" data-url="table-edit.html" data-tabid="table-edit">可编辑表格</li>-->
                                    <#--<li data-id="3" data-pid="0">弹出窗口</li>-->
                                    <#--<li data-id="30" data-pid="3" data-url="dialog.html" data-tabid="dialog">弹出窗口</li>-->
                                    <#--<li data-id="31" data-pid="3" data-url="alert.html" data-tabid="alert">信息提示</li>-->
                                    <#--<li data-id="4" data-pid="0">图形报表</li>-->
                                    <#--<li data-id="40" data-pid="4" data-url="highcharts.html" data-tabid="chart">Highcharts图表</li>-->
                                    <#--<li data-id="40" data-pid="4" data-url="echarts.html" data-tabid="echarts">ECharts图表</li>-->
                                    <#--<li data-id="5" data-pid="0">框架组件</li>-->
                                    <#--<li data-id="51" data-pid="5" data-url="tabs.html" data-tabid="tabs">选项卡</li>-->
                                    <#--<li data-id="6" data-pid="0">其他插件</li>-->
                                    <#--<li data-id="61" data-pid="6" data-url="ztree.html" data-tabid="ztree">zTree</li>-->
                                    <#--<li data-id="62" data-pid="6" data-url="ztree-select.html" data-tabid="ztree-select">zTree下拉选择</li>-->
                                    <#--<li data-id="8" data-pid="0">综合应用</li>-->
                                    <#--<li data-id="80" data-pid="8" data-url="table-layout.html" data-tabid="table-layout">局部刷新1</li>-->
                                </ul>
                            </div>
                        </div>
                        <div class="panelFooter"><div class="panelFooterContent"></div></div>
                    </div>
                    <!-- 左侧顶级菜单 -->
                    <#--<div class="panel panel-default">-->
                        <#--<div class="panel-heading panelContent">-->
                            <#--<h4 class="panel-title"><a data-toggle="collapse" data-parent="#bjui-accordionmenu" href="#bjui-collapse1" class="" style="color:#FF1100;"><i class="fa fa-caret-square-o-right"></i>&nbsp;简要文档</a></h4>-->
                        <#--</div>-->
                        <#--<div id="bjui-collapse1" class="panel-collapse panelContent collapse">-->
                            <#--<div class="panel-body">-->
                                <#--<ul id="bjui-tree1" class="ztree ztree_main" data-toggle="ztree" data-on-click="MainMenuClick" data-expand-all="false">-->
                                    <#--<li data-id="99" data-pid="0">文件列表</li>-->
                                    <#--<li data-id="100" data-pid="99" data-url="doc/base/filelist.html" data-tabid="doc-file">文件详解</li>-->
                                    <#--<li data-id="1" data-pid="0">框架介绍</li>-->
                                    <#--<li data-id="10" data-pid="1" data-url="doc/base/structure.html" data-tabid="doc-base">页面结构</li>-->
                                    <#--<li data-id="11" data-pid="1" data-url="doc/base/init.html" data-tabid="doc-base">框架初始化</li>-->
                                    <#--<li data-id="12" data-pid="1" data-url="doc/base/navtab.html" data-tabid="doc-base">标签工作区(navtab)</li>-->
                                    <#--<li data-id="13" data-pid="1" data-url="doc/base/dialog.html" data-tabid="doc-base">弹窗工作区(dialog)</li>-->
                                    <#--<li data-id="14" data-pid="1" data-url="doc/base/idname.html" data-tabid="doc-base">元素ID命名规范</li>-->
                                    <#--<li data-id="15" data-pid="1" data-url="doc/base/data.html" data-tabid="doc-base">data属性</li>-->
                                    <#--<li data-id="16" data-pid="1" data-url="doc/base/api.html" data-tabid="doc-base">jQuery API调用</li>-->
                                    <#--<li data-id="17" data-pid="1" data-url="doc/base/event.html" data-tabid="doc-base">事件</li>-->
                                    <#--<li data-id="18" data-pid="1" data-url="doc/base/url.html" data-tabid="doc-base">URL动态赋值</li>-->
                                    <#--<li data-id="2" data-pid="0">标签navtab</li>-->
                                    <#--<li data-id="20" data-pid="2" data-url="doc/navtab/navtab.html" data-tabid="doc-navtab">创建navtab</li>-->
                                    <#--<li data-id="21" data-pid="2" data-url="doc/navtab/navtab-op.html" data-tabid="doc-navtab">参数及方法</li>-->
                                    <#--<li data-id="3" data-pid="0">弹窗dialog</li>-->
                                    <#--<li data-id="30" data-pid="3" data-url="doc/dialog/dialog.html" data-tabid="doc-dialog">创建dialog</li>-->
                                    <#--<li data-id="31" data-pid="3" data-url="doc/dialog/dialog-op.html" data-tabid="doc-dialog">参数及方法</li>-->
                                    <#--<li data-id="4" data-pid="0">Ajax</li>-->
                                    <#--<li data-id="40" data-pid="4" data-url="doc/ajax/callback.html" data-tabid="doc-ajax">回调函数</li>-->
                                    <#--<li data-id="41" data-pid="4" data-url="doc/ajax/form.html" data-tabid="doc-ajax">提交表单</li>-->
                                    <#--<li data-id="42" data-pid="4" data-url="doc/ajax/search.html" data-tabid="doc-ajax">搜索表单</li>-->
                                    <#--<li data-id="43" data-pid="4" data-url="doc/ajax/load.html" data-tabid="doc-ajax">加载(局部刷新)</li>-->
                                    <#--<li data-id="44" data-pid="4" data-url="doc/ajax/action.html" data-tabid="doc-ajax">执行动作</li>-->
                                    <#--<li data-id="5" data-pid="0">图形报表</li>-->
                                    <#--<li data-id="50" data-pid="5" data-url="doc/chart/highcharts.html" data-tabid="doc-highcharts">Highcharts图表</li>-->
                                    <#--<li data-id="50" data-pid="5" data-url="doc/chart/echarts.html" data-tabid="doc-echarts">ECharts图表</li>-->
                                    <#--<li data-id="6" data-pid="0">表单相关</li>-->
                                    <#--<li data-id="60" data-pid="6" data-url="doc/form/datepicker.html" data-tabid="doc-form">日期选择器</li>-->
                                    <#--<li data-id="61" data-pid="6" data-url="doc/form/spinner.html" data-tabid="doc-form">微调器</li>-->
                                    <#--<li data-id="62" data-pid="6" data-url="doc/form/lookup.html" data-tabid="doc-form">查找带回</li>-->
                                    <#--<li data-id="63" data-pid="6" data-url="doc/form/tags.html" data-tabid="doc-form">自动完成标签</li>-->
                                    <#--<li data-id="64" data-pid="6" data-url="doc/form/upload.html" data-tabid="doc-form">上传组件</li>-->
                                    <#--<li data-id="66" data-pid="6" data-url="doc/form/checkbox.html" data-tabid="doc-form">plugin:复选/单选</li>-->
                                    <#--<li data-id="66" data-pid="6" data-url="doc/form/select.html" data-tabid="doc-form">plugin:下拉选择框</li>-->
                                    <#--<li data-id="67" data-pid="6" data-url="doc/form/validate.html" data-tabid="doc-form">plugin:表单验证</li>-->
                                    <#--<li data-id="68" data-pid="6" data-url="doc/form/kindeditor.html" data-tabid="doc-form">plugin:编辑器</li>-->
                                    <#--<li data-id="7" data-pid="0">表格相关</li>-->
                                    <#--<li data-id="70" data-pid="7" data-url="doc/table/style.html" data-tabid="doc-table">表格样式</li>-->
                                    <#--<li data-id="71" data-pid="7" data-url="doc/table/order.html" data-tabid="doc-table">字段排序</li>-->
                                    <#--<li data-id="72" data-pid="7" data-url="doc/table/paging.html" data-tabid="doc-table">分页组件</li>-->
                                    <#--<li data-id="73" data-pid="7" data-url="doc/table/selected.html" data-tabid="doc-table">行选中操作</li>-->
                                    <#--<li data-id="74" data-pid="7" data-url="doc/table/fixed.html" data-tabid="doc-table">固定表头</li>-->
                                    <#--<li data-id="75" data-pid="7" data-url="doc/table/edit.html" data-tabid="doc-table">可编辑表格</li>-->
                                    <#--<li data-id="8" data-pid="0">其他</li>-->
                                    <#--<li data-id="80" data-pid="8" data-url="doc/other/contextmenu.html" data-tabid="doc-other">右键菜单</li>-->
                                    <#--<li data-id="9" data-pid="0">框架皮肤</li>-->
                                    <#--<li data-id="90" data-pid="9" data-url="doc/theme/color.html" data-tabid="doc-theme">颜色值</li>-->
                                <#--</ul>-->
                            <#--</div>-->
                        <#--</div>-->
                        <#--<div class="panelFooter"><div class="panelFooterContent"></div></div>-->
                    <#--</div>-->
                </div>
            </div>
        </div>
        <div id="bjui-navtab" class="tabsPage">
            <div class="tabsPageHeader">
                <div class="tabsPageHeaderContent">
                    <ul class="navtab-tab nav nav-tabs">
                        <li data-url="${rc.contextPath}/other/index_layout.html"><a href="javascript:;"><span><i class="fa fa-home"></i> #maintab#</span></a></li>
                    </ul>
                </div>
                <div class="tabsLeft"><i class="fa fa-angle-double-left"></i></div>
                <div class="tabsRight"><i class="fa fa-angle-double-right"></i></div>
                <div class="tabsMore"><i class="fa fa-angle-double-down"></i></div>
            </div>
            <ul class="tabsMoreList">
                <li><a href="javascript:;">#maintab#</a></li>
            </ul>
            <div class="navtab-panel tabsPageContent">
                <div class="navtabPage unitBox">
                    <div class="bjui-pageHeader" style="background:#FFF;">
                        <#--<div style="padding: 0 15px;">-->
                            <#--<h4>-->
                                <#--B-JUI 前端框架　-->
                                <#--<a target="_blank" href="${rc.contextPath}http://shang.qq.com/wpa/qunwpa?idkey=0047455f3845597286edd381d54076b1e10a45c0c735115f0ee74961f70880af"><img border="0" src="${rc.contextPath}/images/group.png" alt="B-JUI前端框架-群1" title="B-JUI前端框架-群1" style="vertical-align:top;"></a>-->
                                <#--<span class="label label-default" style="vertical-align:middle;">(1群已满)</span>　-->
                                <#--<a target="_blank" href="${rc.contextPath}http://shang.qq.com/wpa/qunwpa?idkey=96974f9b311cb8566e371703e2e4c2abb23c4835f4ec6c2893652f7a3b920c32"><img border="0" src="${rc.contextPath}/images/group.png" alt="B-JUI前端框架-群2" title="B-JUI前端框架-群2" style="vertical-align:top;"></a>-->
                            <#--</h4>-->
                            <#--<div class="alert alert-success" role="alert" style="margin:0 0 5px; padding:5px 15px 0;">-->
                                <#--<strong>B-JUI开发团队欢迎你!</strong> <a href="${rc.contextPath}http://weibo.com/xknaan" target="_blank">@萧克南</a>　<a href="${rc.contextPath}http://www.topstack.cn" target="_blank">@小策一喋</a>-->
                            <#--</div>-->
                            <#--<div class="row">-->
                                <#--<div class="col-md-6">-->
                                    <#--<h5>官方论坛：<a href="${rc.contextPath}http://www.b-jui.com/" target="_blank">http://www.b-jui.com/</a></h5>-->
                                    <#--<h5>项目地址：<a href="${rc.contextPath}http://git.oschina.net/xknaan/B-JUI" target="_blank">GIT</a>　<a href="${rc.contextPath}http://www.oschina.net/p/bootstrap-for-DWZ" target="_blank">OSCHINA</a></h5>-->
                                    <#--<h5>微博地址：<a href="${rc.contextPath}http://weibo.com/xknaan" target="_blank">http://weibo.com/xknaan</a></h5>-->
                                <#--</div>-->
                                <#--<div class="col-md-6">-->
                                    <#--<h5>框架演示：<a href="${rc.contextPath}http://b-jui.com/" target="_blank">http://b-jui.com/</a></h5>-->
                                    <#--<h5>DWZ(J-UI)官网：<a href="${rc.contextPath}http://www.j-ui.com/" target="_blank">http://www.j-ui.com/</a></h5>-->
                                    <#--<h5>Bootstrap中文网：<a href="${rc.contextPath}http://www.bootcss.com/" target="_blank">http://www.bootcss.com/</a></h5>-->
                                <#--</div>-->
                            <#--</div>-->
                        <#--</div>-->
                    </div>
                </div>
            </div>
        </div>
    </div>
    <footer id="bjui-footer">Copyright &copy; 2013 - 2015　<a href="${rc.contextPath}http://b-jui.com/" target="_blank">B-JUI 前端框架</a>　
        <script type="text/javascript">var cnzz_protocol = (("https:" == document.location.protocol) ? " https://" : " http://");document.write(unescape("%3Cspan id='cnzz_stat_icon_1252983288'%3E%3C/span%3E%3Cscript src='" + cnzz_protocol + "s23.cnzz.com/stat.php%3Fid%3D1252983288%26show%3Dpic' type='text/javascript'%3E%3C/script%3E"));</script>
    </footer>
</body>

<script type="text/javascript">


</script>
</html>