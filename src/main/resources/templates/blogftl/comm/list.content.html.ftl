
<#-- 博客列表和分页导航 -->
<#if blogPage.list??>
<#list blogPage.list as row>
<div class="row">
    <#list row as blogMeta>
    <div class="blogmetaList col-md-6">
        <ul class="list-group ">
            <li class="list-group-item list-group-item-info">
                <div>
                    <span class="badge badge-info">${statMap.getPeople(blogMeta.uid)}</span>
                    <span class="badge badge-danger">${comments.getCommentCount(blogMeta.uid)}</span>
                    <a href="index.html?zblogurl=blogdetail.jsp%3fuid%3d${blogMeta.uid}">${blogMeta.title}</a> <br>
                    <small>by ${blogMeta.author}</small><br>
                    <small>${blogMeta.dtStr}</small>

                </div>
            </li>
        </ul>
    </div>
    </#list>
</div>
</#list>
</#if>



<div class="row ">
    <div class="blogmetaList col-md-12 justify-content-between">
        <nav aria-label="Page navigation example">
            <ul class="pagination justify-content-center">
                <li class="page-item">
                    <a class="page-link" href="index.html?zblogurl=bloglist.jsp%3fpage%3d${blogPage.last}" aria-label="Previous">
                        <span aria-hidden="true">&laquo;</span>
                        <span class="sr-only">Previous</span>
                    </a>
                </li>
                <li class="page-item">
                    <form id="jumpBlogPageForm" class="form-inline">
                        <input  type="number" style="width: 80px" class="form-control" id="jumpBlogPageInput" value="${blogPage.page}" placeholder="${blogPage.page}">
                        /${blogPage.pageCount} &nbsp;
                        <button type="submit" class="btn btn-primary ">Go</button>
                    </form>
                </li>
                <li class="page-item">
                    <a class="page-link" href="index.html?zblogurl=bloglist.jsp%3fpage%3d${blogPage.next}" aria-label="Next">
                        <span aria-hidden="true">&raquo;</span>
                        <span class="sr-only">Next</span>
                    </a>
                </li>
            </ul>
        </nav>
    </div>

</div> <!-- pagination row -->