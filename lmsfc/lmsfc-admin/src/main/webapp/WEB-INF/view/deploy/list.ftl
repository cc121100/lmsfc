<script type="text/javascript">
    function pic_form_upload_success(data) {
        var json = jQuery.parseJSON(data);
        DWZ.ajaxDone(json);
        if (json.statusCode == DWZ.statusCode.ok) {
            $('#j_form_pic').val(json.navTabId);
            $('#j_form_span_pic').html('<img src="'+ json.navTabId +'" width="100" />');
        }
    }
</script>
<div class="bjui-pageContent">
    <div style="margin:15px auto 0; width:800px;">
        <fieldset>
            <legend>相关操作</legend>
            <table class="table table-hover">
                <thead>
                <tr>
                    <th>操作</th>
                    <th>说明</th>
                </tr>
                </thead>
                <tbody>
                <tr>
                    <td>
                        <a type="button" class="btn btn-default" href="${rc.contextPath}/deploy/init?type=IPR" data-toggle="doajax" data-confirm-msg="确定要初始化吗？">初始化</a>
                    </td>
                    <td>部署lmsfc需要用的静态资源(css,img,js...),初始化Redis数据</td>
                </tr>
                <tr>
                    <td>
                        <a type="button" class="btn btn-default" href="${rc.contextPath}/deploy/init?type=RA" data-toggle="doajax" data-confirm-msg="确定要重新组装Article吗？">重新组装Article</a>
                    </td>
                    <td>重新组装Article</td>
                </tr>
                <tr>
                    <td>
                        <a type="button" class="btn btn-default" href="${rc.contextPath}/deploy/init?type=IR" data-toggle="doajax" data-confirm-msg="确定要重新加载Redis数据吗？">重新加载Redis数据</a>
                    </td>
                    <td>初始化Redis数据</td>
                </tr>
                </tbody>
            </table>
        </fieldset>
    </div>
</div>
<div class="bjui-pageFooter">
    <ul>
        <li><button type="button" class="btn-close" data-icon="close">关闭</button></li>
    </ul>
</div>