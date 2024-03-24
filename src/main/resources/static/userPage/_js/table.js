
function displayItems(currentPage = 1, pageSize = 5) {
        const pageNumberSpan = document.getElementById("page-number");
        const tbody = document.querySelector("tbody");
        tbody.innerHTML = "";

        const startIndex = (currentPage - 1) * pageSize;
        const endIndex = startIndex + pageSize;

        dataItems.slice(startIndex, endIndex).forEach((item, index) => {
            let trend;
            if (item.direction == 'UP') {
                trend = '<span class="up-arrow">&#8682;</span>';
            } else if (item.direction == 'DOWN') {
                trend = '<span class="down-arrow">&#8681;</span>';
            } else {
                trend = '<span class="yellow-dash" style="color: blue; font-weight: bold;">&mdash;</span>';
            }

            let row = `
                <tr>
                    <td>${new Date(item.timestamp).toLocaleString()}</td>
                    <td>${item.open}</td>
                    <td>${item.close}</td>
                    <td>${item.max}</td>
                    <td>${item.min}</td>
                    <td>${trend}</td>
                </tr>
            `;
            tbody.innerHTML += row;
        });

        pageNumberSpan.textContent = `Страница: ${currentPage}`;

        const prevPageButton = document.getElementById("prev-page");
        const nextPageButton = document.getElementById("next-page");

        prevPageButton.addEventListener("click", function () {
            if (currentPage > 1) {
                currentPage--;
                displayItems(currentPage);
            }
        });

        nextPageButton.addEventListener("click", function () {
            const maxPage = Math.ceil(dataItems.length / pageSize);
            if (currentPage < maxPage) {
                currentPage++;
                displayItems(currentPage);
            }
        });
}