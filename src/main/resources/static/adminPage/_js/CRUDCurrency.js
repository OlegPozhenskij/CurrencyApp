function deleteCurrency(id) {
    fetch(`/admin/currency/delete?id=${id}`, {
                method: 'DELETE'
            })
            .then(response => {
                if (!response.ok) {
                    throw new Error('Ошибка удаления валюты');
                }
                window.location.reload();
            })
            .catch(error => {
                console.error('Ошибка:', error);
            });
}
