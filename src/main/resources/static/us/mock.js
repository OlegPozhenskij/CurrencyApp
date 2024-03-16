document
  .getElementById("submit-button")
  .addEventListener("click", async function () {
    const currency = document.querySelector(".select-currency").value;
    const currencyParts = currency.split("/");

    const startDate = document.querySelector(".start-date").value;
    const endDate = document.querySelector(".end-date").value;
    const period = document.querySelector(".period-select").value;

    try {
      const response = await fetch(
        `/stats?&currencyFirst=${currencyParts[0]}&currencyLast=${currencyParts[1]}&start=${startDate}&end=${endDate}&period=${period}`
      );
      const data = await response.json();
      // Обновляем данные на странице или делаем что-то еще с полученными данными
      console.log(data);
    } catch (error) {
      console.error("Ошибка при выполнении запроса:", error);
    }
  });

////export
// const mockItems = [
//   {
//     timestamp: 1583184800000,
//     open: 3.185,
//     close: 4.1262,
//     max: 6.1314,
//     min: 2.1251,
//     direction: 0,
//   },
//   {
//     timestamp: 1583185800000,
//     open: 4.128,
//     close: 6.128,
//     max: 7.1446,
//     min: 1.1274,
//     direction: 1,
//   },
//   {
//     timestamp: 1583186800000,
//     open: 6.128,
//     close: 2.128,
//     max: 10.1446,
//     min: 0.1274,
//     direction: -1,
//   },
//   {
//     timestamp: 1583187800000,
//     open: 2.128,
//     close: 10.128,
//     max: 14.1446,
//     min: 1.1274,
//     direction: 1,
//   },
//   {
//     timestamp: 1583188800000,
//     open: 10.185,
//     close: 14.1262,
//     max: 16.1314,
//     min: 6.1251,
//     direction: 1,
//   },
//   {
//     timestamp: 1583189800000,
//     open: 14.128,
//     close: 23.128,
//     max: 26.1446,
//     min: 13.1274,
//     direction: 1,
//   },
//   {
//     timestamp: 1583190800000,
//     open: 23.128,
//     close: 19.128,
//     max: 24.1446,
//     min: 16.1274,
//     direction: -1,
//   },
//   {
//     timestamp: 1583191800000,
//     open: 19.128,
//     close: 16.128,
//     max: 20.1446,
//     min: 13.1274,
//     direction: -1,
//   },
//   {
//     timestamp: 1583192800000,
//     open: 16.185,
//     close: 28.1262,
//     max: 35.1314,
//     min: 15.1251,
//     direction: 1,
//   },
//   {
//     timestamp: 1583193800000,
//     open: 28.128,
//     close: 34.128,
//     max: 36.1446,
//     min: 26.1274,
//     direction: 1,
//   },
//   {
//     timestamp: 1583194800000,
//     open: 34.185,
//     close: 27.1262,
//     max: 37.1314,
//     min: 26.1251,
//     direction: -1,
//   },
//   {
//     timestamp: 1583195800000,
//     open: 27.128,
//     close: 43.128,
//     max: 46.1446,
//     min: 23.1274,
//     direction: 1,
//   },
// ];
