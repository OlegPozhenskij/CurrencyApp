let dataItems = [{}];

document.addEventListener("DOMContentLoaded", async function () {
    const selectCurrency = document.querySelector(".select-currency");

    try {
        const { strList: currencies } = await fetchCurrencyList();
        selectCurrency.innerHTML = "";

        currencies.forEach(currency => {
            const option = document.createElement("option");
            option.value = currency;
            option.textContent = currency;
            selectCurrency.appendChild(option);
        });

        console.log("Список валют успешно загружен:", currencies);
    } catch (error) {
        console.error("Ошибка при загрузке списка валют:", error);
    }
});

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
            response = await fetchStats({ currencyFirst: currencyParts[0], currencyLast: currencyParts[1], start: startDate, end: endDate, period: period });
        } else if (mode === "count") {
            const count = document.querySelector(".count-input").value;
            const period = document.querySelector(".period-select").value;
            response = await fetchStats({ currencyFirst: currencyParts[0], currencyLast: currencyParts[1], num: count, period: period });
        }

        dataItems = response.statsList;

        updateChart();
        displayItems();

        console.log("Данные успешно получены:", dataItems);
    } catch (error) {
        console.error("Ошибка при выполнении запроса:", error);
    }
});

document.addEventListener("DOMContentLoaded", function() {
    var selectMode = document.querySelector(".select-mode");
    var dates = document.querySelector(".dates");
    var counts = document.querySelector(".counts");

    selectMode.addEventListener("change", function() {
        var selectedOption = selectMode.value;

        if (selectedOption === "range") {
            dates.removeAttribute("hidden");
            counts.setAttribute("hidden", "true");
        } else if (selectedOption === "count") {
            dates.setAttribute("hidden", "true");
            counts.removeAttribute("hidden");
        }
    });

    counts.setAttribute("hidden", "true");
});
