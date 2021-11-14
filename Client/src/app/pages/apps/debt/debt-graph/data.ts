import { ChartType } from "./debt-graph.model";

function getDaysInMonth(month, year) {
  const date = new Date(year, month, 1);
  const days = [];
  let idx = 0;
  while (date.getMonth() === month && idx < 15) {
    const d = new Date(date);
    days.push(d.getDate() + ' ' + d.toLocaleString('en-us', { month: 'short' }));
    date.setDate(date.getDate() + 1);
    idx += 1;
  }
  return days;
}

const now = new Date();
const labels = getDaysInMonth(now.getMonth(), now.getFullYear());
const revenueAreaChart: ChartType = {
  series: [
    {
      name: "PRODUCT A",
      data: [44, 55, 41, 67, 22, 43, 0, 0, 0, 0, 0, 0]
    },
    {
      name: "PRODUCT B",
      data: [13, 23, 20, 8, 13, 27, 0, 0, 0, 0, 0, 0]
    },
    {
      name: "PRODUCT C",
      data: [11, 17, 15, 15, 21, 14, 0, 0, 0, 0, 0, 0]
    },
    {
      name: "PRODUCT D",
      data: [21, 7, 25, 13, 22, 8, 0, 0, 0, 0, 0, 0]
    }
  ],
  chart: {
    type: "bar",
    height: 350,
    stacked: true,
    toolbar: {
      show: true
    },
    zoom: {
      enabled: true
    }
  },
  responsive: [
    {
      breakpoint: 480,
      options: {
        legend: {
          position: "bottom",
          offsetX: -10,
          offsetY: 0
        }
      }
    }
  ],
  plotOptions: {
    bar: {
      horizontal: false
    }
  },
  xaxis: {
    type: "category",
    categories: [
      "01/2021",
      "02/2021",
      "03/2021",
      "04/2021",
      "05/2021",
      "06/2021",
      "07/2021",
      "08/2021",
      "09/2021",
      "10/2021",
      "11/2021",
      "12/2021"
    ]
  },
  legend: {
    position: "right",
    offsetY: 40
  },
  fill: {
    opacity: 1
  }
};

export { revenueAreaChart };
