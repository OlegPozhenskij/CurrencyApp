//export
let mockItems = [{}];

document.getElementById("submit-button").addEventListener("click", async function () {
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
        mockItems = data;

        updateChart();
        updateTable(); // Вызываем функцию обновления таблицы

        console.log("Данные успешно получены:", mockItems);
    } catch (error) {
        console.error("Ошибка при выполнении запроса:", error);
    }
});
//
//document.addEventListener("DOMContentLoaded", async function () {
//    try {
//        const response = await fetch(`/stats?&currencyFirst=USD&currencyLast=RUB&start=2018-02-16T23:42:55&end=2026-02-16T23:43:06&period=YEAR`);
//        const data = await response.json();
//        mockItems = data;
//
//        updateChart();
//        updateTable(); // Вызываем функцию обновления таблицы
//
//        console.log("Данные успешно получены:", mockItems);
//    } catch (error) {
//        console.error("Ошибка при выполнении запроса:", error);
//    }
//});
