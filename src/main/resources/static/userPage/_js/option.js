document.addEventListener("DOMContentLoaded", async function () {
    const selectCurrency = document.querySelector(".select-currency");

    try {
        const response = await fetch("/currency");
        const currencies = await response.json();

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
