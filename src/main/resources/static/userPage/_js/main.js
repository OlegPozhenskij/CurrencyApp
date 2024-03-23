document.addEventListener("DOMContentLoaded", function() {
    var selectMode = document.querySelector(".select-mode");
    var dates = document.querySelector(".dates");
    var counts = document.querySelector(".counts");

    // Слушаем изменения в select элементе
    selectMode.addEventListener("change", function() {
        // Получаем выбранное значение
        var selectedOption = selectMode.value;

        // Проверяем выбранное значение и скрываем/показываем соответствующий блок
        if (selectedOption === "range") {
            // Показываем блок .dates и скрываем блок .counts
            dates.removeAttribute("hidden");
            counts.setAttribute("hidden", "true");
        } else if (selectedOption === "count") {
            // Показываем блок .counts и скрываем блок .dates
            dates.setAttribute("hidden", "true");
            counts.removeAttribute("hidden");
        }
    });

    // Изначально скрываем блок .counts
    counts.setAttribute("hidden", "true");
});
