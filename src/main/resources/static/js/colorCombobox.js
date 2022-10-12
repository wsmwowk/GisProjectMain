// سكربت للاداة مال اختيار الالوان الموجودة بصفحة تسجيل مستخدم جديد

$("#inputColor").on("click", ".init", function() {
    $(this).closest("ul").children('li:not(.init)').toggle();
});

var allOptions = $("#inputColor").children('li:not(.init)');
$("#inputColor").on("click", "li:not(.init)", function() {
    allOptions.removeClass('selected');
    $(this).addClass('selected');
    $("ul").children('.init').html($(this).html());
    allOptions.toggle();
    console.log("clicked 2");
    document.getElementById("inputColorLabel").setAttribute('value',this.innerText.toString().slice(1,this.innerText.toString().length).toLowerCase().trim());
});