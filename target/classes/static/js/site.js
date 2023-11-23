var SiteController = function () {
    this.initialize = function () {
        registerEvents();
        loadCart();
    }

    function loadCart() {
        $.ajax({  // xử lý khi click thêm Product vào Cart
            type: "GET",
            url: '/Cart/GetListItems',
            success: function (res) {
                $('#lbl_number_of_items_header').text(res.cartItems.length);
            }
        });
    }

    function registerEvents() {
        // Chức năng thêm vào giỏ hàng
        $('body').on('click', '.add-to-cart-btn', function (e) {
            e.preventDefault();
            const id = $(this).data('id');

            $.ajax({  // xử lý khi click thêm Product vào Cart
                type: "POST",
                url: '/cart/addtocart',
                data: {
                    id: id,
                },
                success: function (res) {
                    $('#lbl_number_of_items_header').text(res.cartItems.length);
                    Swal.fire({
                        position: 'top-end',
                        icon: 'success',
                        title: 'Đã thêm vào giỏ hàng',
                        showConfirmButton: false,
                        timer: 1500
                    })
                },
                error: function (err) {
                    console.log(err);
                }
            });
        });
    }
}

function numberWithCommas(x) {
    return x.toString().replace(/\B(?=(\d{3})+(?!\d))/g, ",");
}
function numberWithoutCommas(x) {
    return x.toString().replace(/,/g, "");
}