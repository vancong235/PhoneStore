const dateFromInput = document.getElementById('dateFrom');
const dateToInput = document.getElementById('dateTo');
let defaultFromDate = '2023-01-01';
let defaultToDate = '2023-12-31';
function saveStatistical(event) {
  event.preventDefault();
  if (dateFromInput.value === '' || dateToInput.value === '') {
     alert('Vui lòng chọn cả hai ngày.');
     return;
  }

  if (dateFromInput.value > dateToInput.value) {
      alert('Ngày bắt đầu không được lớn hơn ngày kết thúc.');
      return;
  }
  document.getElementById("editForm").submit();
}

document.addEventListener('DOMContentLoaded', function() {
    var selectOption = localStorage.getItem("selectedChartType") || "top5Customers";
    document.getElementById("chartType").value = selectOption; // Đặt giá trị mặc định cho select

    displayChart();

    var form = document.querySelector("form");
    form.addEventListener("submit", function(event) {
        event.preventDefault(); // Ngăn chặn trình duyệt load lại trang
        displayChart();
    });

    hideAllDivs(); // Ẩn tất cả các div lúc tải trang

    // Kiểm tra nếu có giá trị lưu trong localStorage, thì khôi phục giá trị của select
    var savedChartType = localStorage.getItem("selectedChartType");
    if (savedChartType) {
        document.getElementById("chartType").value = savedChartType;
        displayChart();
    }

});

function displayChart() {
    var selectOption = document.getElementById("chartType").value;
    localStorage.setItem("selectedChartType", selectOption); // Lưu giá trị mới vào localStorage

    hideAllDivs();

    if (selectOption === "top5Customers") {
        showDiv("top5CustomersDiv");
        // Hiển thị dữ liệu và cập nhật div "top5CustomersDiv"
    } else if (selectOption === "top10Products") {
        showDiv("top10ProductsDiv");
        // Hiển thị dữ liệu và cập nhật div "top10ProductsDiv"
    } else if (selectOption === "top5Employees") {
        showDiv("top5EmployeesDiv");
        // Hiển thị dữ liệu và cập nhật div "monthlyRevenueDiv"
    } else if (selectOption === "weeklyRevenue") {
        showDiv("weeklyRevenueDiv");
        // Hiển thị dữ liệu và cập nhật div "weeklyRevenueDiv"
    }else if (selectOption === "monthlyRevenue") {
        showDiv("monthlyRevenueDiv");
        // Hiển thị dữ liệu và cập nhật div "weeklyRevenueDiv"
    }
}

function hideAllDivs() {
    var div1 = document.getElementById("top5CustomersDiv");
    var div2 = document.getElementById("top10ProductsDiv");
    var div3 = document.getElementById("monthlyRevenueDiv");
    var div4 = document.getElementById("weeklyRevenueDiv");
    var div5 = document.getElementById("top5EmployeesDiv");
    div1.style.display = "none";
    div2.style.display = "none";
    div3.style.display = "none";
    div4.style.display = "none";
    div5.style.display = "none";
}

function showDiv(divId) {
    var div = document.getElementById(divId);
    div.style.display = "block";
}