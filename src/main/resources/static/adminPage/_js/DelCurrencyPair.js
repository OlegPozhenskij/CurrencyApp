function deleteCurrencyPair(id) {
    fetch(`/admin/currency_pair/delete?id=${id}`, {
                method: 'DELETE'
            })
            .then(response => {
                if (!response.ok) {
                    throw new Error('Ошибка удаления валютной пары');
                }
                window.location.reload();
            })
            .catch(error => {
                console.error('Ошибка:', error);
            });
}
