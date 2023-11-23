$('document').ready(function (){
        $('table #editButton').on('click', function (event){
        event.preventDefault();
        var href = $(this).attr('href');
        $.ajax({
            url: href,
            type: 'GET',
            success: function (category, status) {
                $('#idEdit').val(category.id);
                $('#nameEdit').val(category.name);
                 $('#supplierEdit').val(category.supplier);
                $('#editModal').modal('show');
            },
            error: function (xhr, status, error) {
                console.log(error);
            }
        });
    });
});

function saveCategory(event) {
  event.preventDefault();
  var name = document.getElementById("recipient-name").value;
  if (name === "") {
    alert("Tên Danh Mục không được để trống");
    return;
  }
   document.getElementById("categoryForm").submit();
}

function saveCategoryEdit(event) {
  event.preventDefault();
  var name = document.getElementById("nameEdit").value;
  if (name === "") {
    alert("Tên Danh Mục không được để trống");
    return;
  }
   document.getElementById("categoryFormEdit").submit();
}