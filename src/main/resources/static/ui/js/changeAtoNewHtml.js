/**
 * @author sujiming
 * @date 2019-08-05 11:09
 */
function childToNewHtml(ele) {
    if($(ele).attr("name")){
        parent.parentToNewHtmlResponse($(ele).attr("name"));
    }
}