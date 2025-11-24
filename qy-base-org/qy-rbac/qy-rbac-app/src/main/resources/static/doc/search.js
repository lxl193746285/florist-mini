let api = [];
api.push({
    alias: 'RuleScopeController',
    order: '1',
    link: '规则范围',
    desc: '规则范围',
    list: []
})
api[0].list.push({
    order: '1',
    desc: '获取规则范围列表',
});
api[0].list.push({
    order: '2',
    desc: '获取单个规则范围',
});
api[0].list.push({
    order: '3',
    desc: '创建单个规则范围',
});
api[0].list.push({
    order: '4',
    desc: '修改单个规则范围',
});
api[0].list.push({
    order: '5',
    desc: '删除单个规则范围',
});
api.push({
    alias: 'MenuController',
    order: '2',
    link: '菜单',
    desc: '菜单',
    list: []
})
api[1].list.push({
    order: '1',
    desc: '获取菜单列表',
});
api[1].list.push({
    order: '2',
    desc: '获取树形菜单列表',
});
api[1].list.push({
    order: '3',
    desc: '获取单个菜单',
});
api[1].list.push({
    order: '4',
    desc: '创建单个菜单',
});
api[1].list.push({
    order: '5',
    desc: '修改单个菜单',
});
api[1].list.push({
    order: '6',
    desc: '删除单个菜单',
});
api[1].list.push({
    order: '7',
    desc: '授权所有权限给超管',
});
api.push({
    alias: 'ModuleController',
    order: '3',
    link: '模块',
    desc: '模块',
    list: []
})
api[2].list.push({
    order: '1',
    desc: '获取模块列表',
});
api[2].list.push({
    order: '2',
    desc: '获取单个模块',
});
api[2].list.push({
    order: '3',
    desc: '创建单个模块',
});
api[2].list.push({
    order: '4',
    desc: '修改单个模块',
});
api[2].list.push({
    order: '5',
    desc: '删除单个模块',
});
api.push({
    alias: 'PermissionController',
    order: '4',
    link: '菜单',
    desc: '菜单',
    list: []
})
api[3].list.push({
    order: '1',
    desc: '获取菜单列表',
});
api[3].list.push({
    order: '2',
    desc: '获取树形菜单列表',
});
api[3].list.push({
    order: '3',
    desc: '获取单个菜单',
});
api[3].list.push({
    order: '4',
    desc: '创建单个菜单',
});
api[3].list.push({
    order: '5',
    desc: '修改单个菜单',
});
api[3].list.push({
    order: '6',
    desc: '删除单个菜单',
});
api[3].list.push({
    order: '7',
    desc: '授权所有权限给超管',
});
api.push({
    alias: 'MenuApiController',
    order: '5',
    link: '菜单内部服务',
    desc: '菜单内部服务',
    list: []
})
api[4].list.push({
    order: '1',
    desc: '获取指定用户的权限',
});
api[4].list.push({
    order: '2',
    desc: '获取用户指定上下文前端可以访问的菜单',
});
api[4].list.push({
    order: '3',
    desc: '获取用户指定上下文授权的菜单',
});
api.push({
    alias: 'RoleApiController',
    order: '6',
    link: '角色内部服务',
    desc: '角色内部服务',
    list: []
})
api[5].list.push({
    order: '1',
    desc: '获取指定角色的权限',
});
api[5].list.push({
    order: '2',
    desc: '创建一个角色',
});
api[5].list.push({
    order: '3',
    desc: '修改角色`',
});
api[5].list.push({
    order: '4',
    desc: '删除角色',
});
api[5].list.push({
    order: '5',
    desc: '角色授权',
});
api[5].list.push({
    order: '6',
    desc: '分配角色给指定用户',
});
api[5].list.push({
    order: '7',
    desc: '撤销指定用户的角色',
});
api[5].list.push({
    order: '8',
    desc: '创建角色并授权',
});
api[5].list.push({
    order: '9',
    desc: '复制用户权限',
});
api.push({
    alias: 'AuthApiController',
    order: '7',
    link: '授权内部服务',
    desc: '授权内部服务',
    list: []
})
api[6].list.push({
    order: '1',
    desc: '获取指定用户的权限',
});
api[6].list.push({
    order: '2',
    desc: '获取指定用户在指定上下文的权限',
});
api[6].list.push({
    order: '3',
    desc: '指定用户是否有指定的权限',
});
api[6].list.push({
    order: '4',
    desc: '指定用户在指定上下文是否有指定的权限',
});
api[6].list.push({
    order: '5',
    desc: '指定用户拥有指定权限的上下文',
});
api[6].list.push({
    order: '6',
    desc: '获取指定用户有指定权限的上下文以及规则数据',
});
api[6].list.push({
    order: '7',
    desc: '获取指定用户指定的上下文的规则数据',
});
api.push({
    alias: 'dict',
    order: '8',
    link: 'dict_list',
    desc: '数据字典',
    list: []
})
document.onkeydown = keyDownSearch;
function keyDownSearch(e) {
    const theEvent = e;
    const code = theEvent.keyCode || theEvent.which || theEvent.charCode;
    if (code == 13) {
        const search = document.getElementById('search');
        const searchValue = search.value;
        let searchArr = [];
        for (let i = 0; i < api.length; i++) {
            let apiData = api[i];
            const desc = apiData.desc;
            if (desc.indexOf(searchValue) > -1) {
                searchArr.push({
                    order: apiData.order,
                    desc: apiData.desc,
                    link: apiData.link,
                    list: apiData.list
                });
            } else {
                let methodList = apiData.list || [];
                let methodListTemp = [];
                for (let j = 0; j < methodList.length; j++) {
                    const methodData = methodList[j];
                    const methodDesc = methodData.desc;
                    if (methodDesc.indexOf(searchValue) > -1) {
                        methodListTemp.push(methodData);
                        break;
                    }
                }
                if (methodListTemp.length > 0) {
                    const data = {
                        order: apiData.order,
                        desc: apiData.desc,
                        link: apiData.link,
                        list: methodListTemp
                    };
                    searchArr.push(data);
                }
            }
        }
        let html;
        if (searchValue == '') {
            const liClass = "";
            const display = "display: none";
            html = buildAccordion(api,liClass,display);
            document.getElementById('accordion').innerHTML = html;
        } else {
            const liClass = "open";
            const display = "display: block";
            html = buildAccordion(searchArr,liClass,display);
            document.getElementById('accordion').innerHTML = html;
        }
        const Accordion = function (el, multiple) {
            this.el = el || {};
            this.multiple = multiple || false;
            const links = this.el.find('.dd');
            links.on('click', {el: this.el, multiple: this.multiple}, this.dropdown);
        };
        Accordion.prototype.dropdown = function (e) {
            const $el = e.data.el;
            $this = $(this), $next = $this.next();
            $next.slideToggle();
            $this.parent().toggleClass('open');
            if (!e.data.multiple) {
                $el.find('.submenu').not($next).slideUp("20").parent().removeClass('open');
            }
        };
        new Accordion($('#accordion'), false);
    }
}

function buildAccordion(apiData, liClass, display) {
    let html = "";
    let doc;
    if (apiData.length > 0) {
        for (let j = 0; j < apiData.length; j++) {
            html += '<li class="'+liClass+'">';
            html += '<a class="dd" href="#_' + apiData[j].link + '">' + apiData[j].order + '.&nbsp;' + apiData[j].desc + '</a>';
            html += '<ul class="sectlevel2" style="'+display+'">';
            doc = apiData[j].list;
            for (let m = 0; m < doc.length; m++) {
                html += '<li><a href="#_' + apiData[j].order + '_' + doc[m].order + '_' + doc[m].desc + '">' + apiData[j].order + '.' + doc[m].order + '.&nbsp;' + doc[m].desc + '</a> </li>';
            }
            html += '</ul>';
            html += '</li>';
        }
    }
    return html;
}