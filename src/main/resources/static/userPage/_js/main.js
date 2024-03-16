document.addEventListener("DOMContentLoaded", function () {
  const modeSelect = document.querySelector(".select-mode");
  const dateFields = document.querySelector(".date-fields");

  modeSelect.addEventListener("change", function () {
    const selectedMode = modeSelect.value;
    if (selectedMode === "range") {
      dateFields.innerHTML = `
            <input
            type="datetime-local"
            class="start-date"
            placeholder="Дата и время начала"
            step="1"
          />
          <input
            type="datetime-local"
            class="end-date"
            placeholder="Дата и время конца"
            step="1"
          />
          <select class="period-select">
          <option value="MINUTE">Минута</option>
          <option value="HOUR">Час</option>
          <option value="DAY">День</option>
          <option value="MONTH">Месяц</option>
          <option value="YEAR">Год</option>
      </select>
            `;
    } else if (selectedMode === "count") {
      dateFields.innerHTML = `
                <input type="number" class="count-input" placeholder="Количество статистик">
                <select class="period-select">
          <option value="MINUTE">Минута</option>
          <option value="HOUR">Час</option>
          <option value="DAY">День</option>
          <option value="MONTH">Месяц</option>
          <option value="YEAR">Год</option>
      </select>
            `;
    }
  });
});
