function add(){
    let lastItem = $('.input-group').last();
    let id = lastItem.children("span").attr('id');
    id = id.replace(/[^\d]/g, '');
    console.log(id);
    let incrementedId = (parseInt(id) + 1);
    lastItem.last().after("<div class=\"input-group mb-3\">\n" +
        "            <span class=\"input-group-text\" id=\"basic-addon"+incrementedId+"\">@</span>\n" +
        "            <input name=\"chat\" type=\"text\" class=\"form-control\" placeholder=\"chat name\" aria-label=\"chat name\" aria-describedby=\"basic-addon"+incrementedId+"\">\n" +
        "            <div class=\"form-check ms-3 my-auto\">\n" +
        "                <input class=\"form-check-input\" onchange=\"checkBox(this)\" type=\"checkbox\" value=\"false\" id=\"flexCheckIndeterminate"+incrementedId+"\">\n" +
        "                <label class=\"form-check-label\" for=\"flexCheckIndeterminate"+incrementedId+"\">\n" +
        "                    Вкл. аналитику\n" +
        "                </label>\n" +
        "            </div>\n" +
        "        </div>")
}

function del(){
    $('.input-group').last().remove()
}

function checkBox(name) {
    if (name.checked) {
        name.value = "true";
    } else {
        name.value = "false";
    }
}