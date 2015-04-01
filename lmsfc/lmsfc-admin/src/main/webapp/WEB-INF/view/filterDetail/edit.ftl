<script type="text/javascript">
    $(document).on('bjui.beforeCloseDialog', function(e) {
        var $dialog = $(e.target)
        $dialog.navtab('reloadForm', true);
    })
</script>

<div class="bjui-pageContent">
    <form action="${rc.contextPath}/filterDetail/edit" class="pageForm" data-toggle="validate" method="post">
    <#--<input type="hidden" name="dialog.id" value="edce142bc2ed4ec6b623aacaf602a4de">-->
        <input type="hidden" name="filterDetail.id" value="${filterDetail.id?default("")}">
        <table class="table table-condensed table-hover">
            <tbody>
            <tr>
                <td colspan="2" align="center"><h4>Edit Filter Rule Detail</h4></td>
            </tr>
            <tr>
                <td>
                    <label for="j_dialog_filterDetail" class="control-label x90">Category:</label>
                    <select name="filterDetail.category" id="j_dialog_category" data-toggle="selectpicker" data-rule="required">
                        <option value=""> -- 请选择 -- </option>
                        <option value="title" <#if filterDetail.category=="title">selected</#if>>Title</option>
                        <option value="content" <#if filterDetail.category=="content">selected</#if>>Content</option>
                        <option value="innercss" <#if filterDetail.category=="innercss">selected</#if>>InnerCss</option>
                        <option value="outercss" <#if filterDetail.category=="outercss">selected</#if>>OuterCss</option>
                    </select>
                </td>
                <td>
                    <label for="j_dialog_filter" class="control-label x90">Filter:</label>
                    <select name="filterDetail.filter.id" id="j_dialog_filter" data-toggle="selectpicker" data-rule="required">
                        <option value=""> -- 请选择 -- </option>
                        <#list filterList as filter>
                            <option value="${filter.id}" <#if filterDetail.filter.id=="${filter.id}">selected</#if>>${filter.filterName}</option>
                        </#list>
                    </select>
                </td>
            </tr>
            <tr>
                <td>
                    <label for="j_dialog_param1" class="control-label x90">Param Value 1:</label>
                    <input type="text" name="filterDetail.paramValue1" id="j_dialog_param1" value="${filterDetail.paramValue1?default("")}"
                           data-rule="length[1~50];" size="20">
                </td>
                <td>
                    <label for="j_dialog_param2" class="control-label x90">Param Value 2:</label>
                    <input type="text" name="filterDetail.paramValue2" id="j_dialog_param2" value="${filterDetail.paramValue2?default("")}"
                           data-rule="length[1~50];" size="20">
                </td>
            </tr>
            <tr>
                <td>
                    <label for="j_dialog_subNum" class="control-label x90">Sub No:</label>
                    <input type="text" name="filterDetail.subNum" id="j_custom_subNum" value="1" size="5"
                           data-toggle="spinner" data-min="0" data-max="100" data-step="1" data-rule="integer">
                </td>
                <td>
                    <label for="j_dialog_filterRule" class="control-label x90">Filter Rule:</label>
                    <select name="filterDetail.filterRule.id" id="j_dialog_filterRule" data-toggle="selectpicker" >
                        <option value=""> -- 请选择 -- </option>
                        <#list filterRuleList as filterRule>
                            <option value="${filterRule.id}" <#if filterDetail.filterRule.id=="${filterRule.id}">selected</#if>>${filterRule.name}</option>
                        </#list>
                    </select>
                </td>
            </tr>
            <tr>
                <td colspan="2">
                    <label for="j_dialog_parentNode" class="control-label x90">ParentNode:</label>
                    <select name="filterDetail.parentNode.id" id="j_dialog_parentNode" data-toggle="selectpicker">
                        <option value=""> -- 请选择 -- </option>
                    <#list parentNodeList as parentNode>
                        <option value="${parentNode.id}" <#if filterDetail.parentNode.id=="${parentNode.id}">selected</#if>>
                            ${parentNode.filterRule.name}  -  ${parentNode.category} - ${parentNode.filter.filterName}
                        </option>
                    </#list>
                    </select>
                </td>
            </tr>

            <#--<tr>-->
                <#--<td colspan="2" align="right"><div id="multipleLocations2"></div></td>-->
            <#--</tr>-->

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

<#--<div id="templates_container" class="hidden">-->
    <#--<div id="multipleLocationsTemplate" style="text-align: center; padding:4px 0px 4px 0px;">-->
        <#--<label for="startDateMultiLocations_0">From</label>-->
        <#--<input type="date" id="startDateMultiLocations_0" name="startDateMultiLocations_0" title="Start date in new location" onkeyup="" />-->
        <#--<label for="multiLocation_0">Location</label>-->
        <#--<select id="multiLocation_0" title="Where do you live?" onchange="" name="multiLocation_0">-->
            <#--<option value="locationEngland" selected="selected">England</option>-->
            <#--<option value="locationScotland">Scotland</option>-->
        <#--</select>-->
        <#--<input type="button" value="&nbsp;-&nbsp;" id="deleteLocation_0" title="Delete this location"-->
               <#--onclick="$(document).dynamicForm('remove', '_anchor_', this);" />-->
        <#--<input type="button" value="&nbsp;+&nbsp;" id="addLocation_0" title="Add a location"-->
               <#--onclick="$(document).dynamicForm('add', '_anchor_', this);" />-->
        <#--<br />-->
    <#--</div>-->
<#--</div>-->

<#--<script type="text/javascript">-->
    <#--$(document).ready(function() {-->
        <#--$(document).dynamicForm('set', '#multipleLocations2', '#multipleLocationsTemplate')-->
                <#--.dynamicForm('init');-->
    <#--});-->

<#--</script>-->