<script type="text/javascript">

    $(document).on('bjui.beforeCloseDialog', function(e) {
        var $dialog = $(e.target)
        // do something...
        $dialog.navtab('reloadForm', true);
    })
</script>

<div class="bjui-pageContent">
    <form action="${rc.contextPath}/articleTask/edit" class="pageForm" data-toggle="validate" method="post">
        <input type="hidden" name="articleTaskJob.id" value="${articleTaskJob.id?default("")}">
        <table class="table table-condensed table-hover">
            <tbody>
                <tr>
                    <td colspan="2" align="center"><h4>Edit Article Task Job</h4></td>
                </tr>
                <tr>
                    <td>
                        <label for="j_dialog_name" class="control-label x90">Name:</label>
                        <input type="text" name="articleTaskJob.name" id="j_dialog_name" value="${articleTaskJob.name?default("")}"
                               data-rule="required;length[2~100];" size="15">
                    </td>
                    <td>
                        <label for="j_dialog_type" class="control-label x90">Type:</label>
                        <select name="articleTaskJob.type" id="j_dialog_type" data-toggle="selectpicker" data-rule="required">
                            <option value=""> -- 请选择 -- </option>
                            <option value=0 <#if articleTaskJob.type==0>selected</#if>>Single</option>
                            <option value=1 <#if articleTaskJob.type==1>selected</#if>>One of Batch</option>
                        </select>
                    </td>
                </tr>
                <tr>
                    <td>
                        <label for="j_dialog_url" class="control-label x90">Article Url:</label>
                        <input type="text" name="articleTaskJob.url" id="j_dialog_url" value="${articleTaskJob.url?default("")}"
                               data-rule="required;length[~200];url" size="30">
                    </td>
                    <td>
                        <label for="j_dialog_isWhole" class="control-label x90">Is Whole:</label>
                        <input type="checkbox" name="articleTaskJob.isWhole" id="j_dialog_isWhole" data-toggle="icheck" value="1" <#if articleTaskJob.isWhole==1>checked</#if>>
                    </td>

                </tr>
                <tr>
                    <td>
                        <label for="j_dialog_filterRule" class="control-label x90">Filter Rule:</label>
                        <select name="articleTaskJob.filterRule.id" id="j_dialog_filterRule" data-toggle="selectpicker" data-rule="required">
                            <option value=""> -- 请选择 -- </option>
                        <#list filterRuleList as filterRule>
                            <option value="${filterRule.id}" <#if articleTaskJob.filterRule.id=="${filterRule.id}">selected</#if>>${filterRule.name}</option>
                        </#list>
                        </select>
                    </td>
                    <td>
                        <label for="j_dialog_isStartNow" class="control-label x90">Start after edit:</label>
                        <input type="checkbox" name="isStartedNow" id="j_dialog_isStartNow" value="Y" data-toggle="icheck">
                    </td>
                </tr>
                <tr>
                    <td colspan="2">
                        <label for="j_dialog_category" class="control-label x90">Target Category:</label>
                        <select name="articleTaskJob.targetCategory" id="j_dialog_category" data-toggle="selectpicker" data-rule="required">
                            <option value=""> -- 请选择 -- </option>
                        <#list artCategoryList as artCategory>
                            <option value="${artCategory.name}" <#if articleTaskJob.targetCategory=="${artCategory.name}">selected</#if>>${artCategory.name}</option>
                        </#list>
                        </select>
                    </td>

                </tr>

            </tbody>
        </table>
    </form>
</div>
<div class="bjui-pageFooter">
    <ul>
        <li><button type="button" class="btn-close">关闭</button></li>
        <li><button type="submit" class="btn-default">保存</button></li>
    </ul>
</div>