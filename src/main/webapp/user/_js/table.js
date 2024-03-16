document.addEventListener("DOMContentLoaded", function () {
  const tableBody = document.querySelector(".tableBody");
  const pageNumberSpan = document.getElementById("page-number"); // Select the span element for page number
  const pageSize = 10; // Количество элементов на странице
  let currentPage = 1; // Текущая страница

  // Функция для отображения данных на странице в соответствии с текущей страницей
  function displayItems(page) {
    tableBody.innerHTML = `
      <tr>
        <th>Date</th>
        <th>Open</th>
        <th>Close</th>
        <th>Max</th>
        <th>Min</th>
        <th>Direction</th>
      </tr>
    `;

    const startIndex = (page - 1) * pageSize;
    const endIndex = startIndex + pageSize;

    mockItems.slice(startIndex, endIndex).forEach((item, index) => {
      let trend;
      if (item.direction > 0) {
        trend = '<span class="up-arrow">&#8682;</span>';
      } else if (item.direction < 0) {
        trend = '<span class="down-arrow">&#8681;</span>';
      } else {
        trend =
          '<span class="yellow-dash" style="color: blue; font-weight: bold;">&mdash;</span>';
      }

      let row = `
        <tr>
          <td>${new Date(item.timestamp)}</td>
          <td>${item.open}</td>
          <td>${item.close}</td>
          <td>${item.max}</td>
          <td>${item.min}</td>
          <td>${trend}</td>
        </tr>
      `;
      tableBody.innerHTML += row;
    });

    // Update the page number display
    pageNumberSpan.textContent = `Страница: ${page}`;
  }

  // Инициализация отображения первой страницы
  displayItems(currentPage);

  // Обработчики событий для кнопок переключения страниц
  const prevPageButton = document.getElementById("prev-page");
  const nextPageButton = document.getElementById("next-page");

  prevPageButton.addEventListener("click", function () {
    if (currentPage > 1) {
      currentPage--;
      displayItems(currentPage);
    }
  });

  nextPageButton.addEventListener("click", function () {
    const maxPage = Math.ceil(mockItems.length / pageSize);
    if (currentPage < maxPage) {
      currentPage++;
      displayItems(currentPage);
    }
  });
});
