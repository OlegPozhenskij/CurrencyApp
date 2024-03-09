var options = {
  chart: {
    type: "candlestick",
    height: 400,
  },
  series: [
    {
      name: "EUR/USD",
      data: [
        [1583184800000, 3.185, 6.1314, 2.1251, 4.1262],
        [1583185800000, 4.128, 7.1446, 1.1274, 6.128],
        [1583186800000, 6.128, 8.1446, 3.1274, 4.128],
        [1583187800000, 4.128, 12.1446, 3.1274, 10.128],
        [1583188800000, 10.128, 18.1446, 9.1274, 17.128],
        [1583189800000, 17.128, 18.1446, 11.1274, 12.128],
        [1583190800000, 12.128, 23.1446, 10.1274, 22.128],
        [1583191800000, 22.128, 26.1446, 17.1274, 18.128],
        [1583192800000, 18.128, 24.1446, 16.1274, 21.128],
      ],
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

var chart = new ApexCharts(document.querySelector("#chart"), options);
chart.render();
