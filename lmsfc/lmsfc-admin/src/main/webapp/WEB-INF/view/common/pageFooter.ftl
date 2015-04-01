<div class="bjui-pageFooter">
    <div class="pages">
        <span>每页&nbsp;</span>
        <div class="selectPagesize">
            <select data-toggle="selectpicker" data-toggle-change="changepagesize">
                <option value="20">20</option>
                <option value="30">30</option>
            </select>
        </div>
        <span>&nbsp;条，共 ${pageResult.totalElements} 条</span>
    </div>
    <div class="pagination-box" data-toggle="pagination" data-total="${pageResult.totalElements}" data-page-size="${pageResult.size}" data-page-current="${pageResult.number +1?int}">

    </div>
    <div>
        <ul>
            <li><button type="button" class="btn-close" data-icon="close">取消</button></li>
        <#--<li><button type="submit" class="btn-default" data-icon="save">全部保存</button></li>-->
        </ul>
    </div>
</div>
