//export
let dataItems = [{}];

document.getElementById("submit-button").addEventListener("click", async function () {
    const currency = document.querySelector(".select-currency").value;
    const currencyParts = currency.split("/");
    const mode = document.querySelector(".select-mode").value;

    try {
        let response;
        if (mode === "range") {
            const startDate = document.querySelector(".start-date").value;
            const endDate = document.querySelector(".end-date").value;
            const period = document.querySelector(".period-select").value;
            response = await fetch(
                `/api/stats?&currencyFirst=${currencyParts[0]}&currencyLast=${currencyParts[1]}&start=${startDate}&end=${endDate}&period=${period}`
            );
        } else if (mode === "count") {
            const count = document.querySelector(".count-input").value;
            const period = document.querySelector(".period-select").value;
            response = await fetch(
                `/api/stats?&currencyFirst=${currencyParts[0]}&currencyLast=${currencyParts[1]}&num=${count}&period=${period}`
            );
        }

        const data = await response.json();
        dataItems = data.statsList;

        updateChart();
        updateTable();

        console.log("Данные успешно получены:", dataItems);
    } catch (error) {
        console.error("Ошибка при выполнении запроса:", error);
    }
});
