document.addEventListener("DOMContentLoaded", function () {
  const oilPrices = [
    55.2, 54.8, 54.5, 55.1, 55.6, 56.2, 56.5, 56.8, 57.1, 57.5,
  ];

  const tableBody = document.querySelector(".tableBody");
  const maxPriceSpan = document.querySelector(".maxPrice");
  const minPriceSpan = document.querySelector(".minPrice");
  const averageChangeSpan = document.querySelector(".averageChange");
  const averageIndexSpan = document.querySelector(".averageIndex");
  const positiveChangesSpan = document.querySelector(".positiveChanges");
  const negativeChangesSpan = document.querySelector(".negativeChanges");
  const closeChangesSpan = document.querySelector(".closeChange");

  let maxPrice = Math.max(...oilPrices);
  let minPrice = Math.min(...oilPrices);
  maxPriceSpan.textContent = maxPrice;
  minPriceSpan.textContent = minPrice;

  let totalChange = 0;
  let totalIndex = 0;
  let positiveChanges = 0;
  let negativeChanges = 0;

  tableBody.innerHTML = `
    <tr>
      <td>1</td>
      <td> - </td>
      <td>${oilPrices[0]}</td>
      <td> - </td>
      <td> - </td>
      <td> - </td>
      <td> - </td>
    </tr>
    `;

  for (let i = 1; i < oilPrices.length; i++) {
    let change = oilPrices[i] - oilPrices[i - 1];
    let index = oilPrices[i] / oilPrices[i - 1];
    let changeRate = (index - 1) * 100;

    let trend;
    if (change > 0) {
      trend = '<span class="up-arrow">&#8682;</span>';
      positiveChanges++;
    } else if (change < 0) {
      trend = '<span class="down-arrow">&#8681;</span>';
      negativeChanges++;
    } else {
      trend = "-";
    }
    totalChange += change;
    totalIndex += index;

    let row = `
        <tr>
          <td>${i + 1}</td>
          <td>${trend}</td>
          <td>${oilPrices[i]}</td>
          <td>${change.toFixed(2)}</td>
          <td>${index.toFixed(2)}</td>
          <td>${changeRate.toFixed(2)}%</td>
          <td>${index.toFixed(2)}</td>
        </tr>
      `;

    tableBody.innerHTML += row;
  }

  let tableRows = document.querySelectorAll(".tableBody tr");

  for (let i = 0; i < tableRows.length; i++) {
    if (i % 2 == 0) tableRows[i].classList.add("gray");
  }

  averageChangeSpan.textContent = (
    totalChange /
    (oilPrices.length - 1)
  ).toFixed(2);
  averageIndexSpan.textContent = (totalIndex / (oilPrices.length - 1)).toFixed(
    2
  );
  positiveChangesSpan.textContent = positiveChanges;
  negativeChangesSpan.textContent = negativeChanges;
});
