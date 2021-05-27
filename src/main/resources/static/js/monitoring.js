function add() {
    let lastItem = $('.input-content-chat');
    lastItem.append("<div class=\"input-group mb-3\">\n" +
        "            <span class=\"input-group-text\" id=\"basic-addon1\">@</span>\n" +
        "            <input name=\"chat\" type=\"text\" class=\"form-control\" placeholder=\"chat name\" aria-label=\"chat name\" aria-describedby=\"basic-addon1\">\n" +
        "        </div>")
}

function del() {
    $('.input-group').last().remove()
}

function change(id) {
    document.location="not/"+id;
}

function info(id_channel) {
    document.location="info/"+id_channel;
}
