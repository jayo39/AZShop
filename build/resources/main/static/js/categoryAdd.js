function updateSubCategories() {
  const mainCategorySelect = document.getElementById("main_cate");
  const subCategorySelect = document.getElementById("sub_cate");

  subCategorySelect.innerHTML = '<option value="">Select</option>';

  const selectedMainCategory = mainCategorySelect.value;

  const filteredCategories = categories.filter(category => category.maincode === selectedMainCategory);

  filteredCategories.forEach(category => {
    const option = document.createElement("option");
    option.value = category.subcode;
    option.textContent = category.subname;

    subCategorySelect.appendChild(option);
  });
}
