let api = [];
api.push({
    alias: 'BusinessLicenseController',
    order: '1',
    link: '营业执照',
    desc: '营业执照',
    list: []
})
api[0].list.push({
    order: '1',
    desc: '获取营业执照列表',
});
api[0].list.push({
    order: '2',
    desc: '获取单个营业执照',
});
api[0].list.push({
    order: '3',
    desc: '创建单个营业执照',
});
api[0].list.push({
    order: '4',
    desc: '修改单个营业执照',
});
api[0].list.push({
    order: '5',
    desc: '删除单个营业执照',
});
api.push({
    alias: 'CustomerController',
    order: '2',
    link: '客户',
    desc: '客户',
    list: []
})
api[1].list.push({
    order: '1',
    desc: '获取客户列表',
});
api[1].list.push({
    order: '2',
    desc: '获取单个客户',
});
api[1].list.push({
    order: '3',
    desc: '创建单个客户',
});
api[1].list.push({
    order: '4',
    desc: '修改单个客户',
});
api[1].list.push({
    order: '5',
    desc: '删除单个客户',
});
api[1].list.push({
    order: '6',
    desc: '客户开户',
});
api[1].list.push({
    order: '7',
    desc: '获取客户联系人列表',
});
api[1].list.push({
    order: '8',
    desc: '创建客户联系人',
});
api[1].list.push({
    order: '9',
    desc: '获取客户营业执照列表',
});
api[1].list.push({
    order: '10',
    desc: '创建客户营业执照',
});
api[1].list.push({
    order: '11',
    desc: '获取客户开户行列表',
});
api[1].list.push({
    order: '12',
    desc: '创建客户开户行',
});
api.push({
    alias: 'OpenBankController',
    order: '3',
    link: '开户行',
    desc: '开户行',
    list: []
})
api[2].list.push({
    order: '1',
    desc: '获取开户行列表',
});
api[2].list.push({
    order: '2',
    desc: '获取单个开户行',
});
api[2].list.push({
    order: '3',
    desc: '创建单个开户行',
});
api[2].list.push({
    order: '4',
    desc: '修改单个开户行',
});
api[2].list.push({
    order: '5',
    desc: '删除单个开户行',
});
api.push({
    alias: 'ContactController',
    order: '4',
    link: '联系人',
    desc: '联系人',
    list: []
})
api[3].list.push({
    order: '1',
    desc: '获取联系人列表',
});
api[3].list.push({
    order: '2',
    desc: '获取单个联系人',
});
api[3].list.push({
    order: '3',
    desc: '创建单个联系人',
});
api[3].list.push({
    order: '4',
    desc: '修改单个联系人',
});
api[3].list.push({
    order: '5',
    desc: '删除单个联系人',
});
api.push({
    alias: 'dict',
    order: '5',
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