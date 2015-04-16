<script type="text/javascript">

    $(document).on('bjui.beforeCloseDialog', function(e) {
        var $dialog = $(e.target)
        // do something...
//        $dialog.navtab('reloadForm', true);
    })
</script>

<div class="bjui-pageContent">
    <table class="table table-condensed table-hover">
        <tbody>
        <tr>
            <td colspan="2" align="center"><h4>View Article Task Job</h4></td>
        </tr>
        <tr>
            <td>
                <label for="j_dialog_name" class="control-label x90">Name:</label>${articleTaskJob.name?default("")}
            </td>
            <td>
                <label for="j_dialog_type" class="control-label x90">Type:</label>
                <#if articleTaskJob.type==0>
                    Single
                <#elseif articleTaskJob.type==0>
                    One of Batch
                </#if>
            </td>
        </tr>
        <tr>
            <td>
                <label for="j_dialog_url" class="control-label x90">Article Url:</label>${articleTaskJob.url?default("")}
            </td>
            <td>
                <label for="j_dialog_isWhole" class="control-label x90">Is Whole:</label>${articleTaskJob.isWhole}
            </td>

        </tr>
        <tr>
            <td>
                <label for="j_dialog_filterRule" class="control-label x90">Filter Rule:</label>${articleTaskJob.filterRule.name}
            </td>
            <td>
                <label for="" class="control-label x90">Batch Task:</label>${articleTaskJob.batchArticleTaskJob.name?default("ddddddddddddddddddd")}
            </td>
        </tr>
        <tr>
            <td>
                <label for="" class="control-label x90">State:</label><#assign key=c.state/>${artStateMap[key?string]}
            </td>
            <td>
                <label for="" class="control-label x90">Finished Time:</label>${articleTaskJob.finishTime}
            </td>
        </tr>
        <#--<tr>-->
            <#--<td colspan="2">-->
            <#--<#assign seasons = ["wintwerrrrrer", "spewwwwwwwwwwwwing", "eeeeeeeesummer", "autumn"]/>-->
                <#--<label for="j_custom_log" class="control-label x85">备注：</label>-->
                <#--<textarea id="j_custom_note" class="disabled" data-toggle="autoheight" cols="60" rows="5">23423423345435354<br>2342423424324<br>23423423423<br>23423423423432423423432</textarea>-->

            <#--</td>-->

        <#--</tr>-->

        </tbody>
    </table>
    <table class="table table-condensed">
        <tbody>
            <tr>
                <td width="20px"><label for="j_custom_log" class="control-label x90">Task Log:</label></td>
                <td>
                    <#list articleTaskJob.taskJobRunLogs as atjLog>
                        <p>
                            ${atjLog.createdDt?string("yyyy-MM-dd HH:mm:ss")} -
                            ${atjLog.artTaskStep?default("")} -
                            <#if atjLog.state == 0>
                                <span>Success</span> -
                            <#elseif atjLog.state == 1>
                                <span style="color: red;">Fail</span> -
                            </#if>

                            Log:${atjLog.description}
                        </p>

                    </#list>
                </td>
            </tr>
        </tbody>
    </table>
</div>
<div class="bjui-pageFooter">
    <ul>
        <li><button type="button" class="btn-close">关闭</button></li>
        <#--<li><button type="submit" class="btn-default">保存</button></li>-->
    </ul>
</div>