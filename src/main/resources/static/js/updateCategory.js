const mainCategorySelect = document.getElementById("main_cate");
const subCategorySelect = document.getElementById("sub_cate");
var num = 0;

function updateSubCategories() {
  num = 1
  selectedMainCategory = mainCategorySelect.value;
  changeFunction(num);
}

$(function() {
  num = 0
  changeFunction(num);
});

function changeFunction(num) {

  subCategorySelect.innerHTML = '<option value="">Select</option>';
  const filteredCategories = categories.filter(category => category.maincode == selectedMainCategory);

  filteredCategories.forEach(category => {
    const option = document.createElement("option");
    option.value = category.subcode;
    option.textContent = category.subname;

    if (num == 0 && (category.subcode === selectedSubCategory)) {
      option.setAttribute("selected", "selected");
    }

    subCategorySelect.appendChild(option);
  });
}