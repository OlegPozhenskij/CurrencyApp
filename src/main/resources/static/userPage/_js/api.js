const BASE_URL = 'http://localhost:8080/api';

async function fetchData(url, options = {}) {
  try {
    const response = await fetch(url, options);
    if (!response.ok) {
      throw new Error('Network response was not ok');
    }
    return await response.json();
  } catch (error) {
    throw new Error('Fetch failed: ' + error.message);
  }
}

async function fetchCurrencyList() {
  const url = `${BASE_URL}/currencyList`;
  return await fetchData(url);
}

async function fetchStats(params) {
  const { currencyFirst, currencyLast, start, end, num, period } = params;
  let url = `${BASE_URL}/stats?currencyFirst=${currencyFirst}&currencyLast=${currencyLast}&period=${period}`;
  if (start && end) {
    url += `&start=${start}&end=${end}`;
  }
  if (num) {
    url += `&num=${num}`;
  }
  console.log("url: " + url);
  return await fetchData(url);
}

