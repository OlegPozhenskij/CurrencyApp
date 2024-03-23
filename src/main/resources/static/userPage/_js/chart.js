
var options = {
  chart: {
    type: "candlestick",
    height: 400,
  },
  series: [
    {
      name: "EUR/USD",
      data: dataItems.map((item) => [
        item.timestamp,
        item.open,
        item.max,
        item.min,
        item.close,
      ]),
    },
  ],
  xaxis: {
    type: "datetime",
    labels: {
      formatter: function (val) {
        var date = new Date(val);
        var options = {
          year: "numeric",
          month: "short",
          day: "numeric",
          hour: "numeric",
          minute: "numeric",
        };
        return new Intl.DateTimeFormat("en-US", options).format(date);
      },
    },
  },
  plotOptions: {
    candlestick: {
      wick: {
        useFillColor: true,
      },
    },
  },
};

function updateChart() {
// Обновляем данные на графике
    chart.updateSeries([
        {
            name: "EUR/USD",
            data: dataItems.map((item) => [
                item.timestamp,
                item.open,
                item.max,
                item.min,
                item.close,
            ]),
        },
    ]);
}

var chart = new ApexCharts(document.querySelector("#chart"), options);
chart.render();
